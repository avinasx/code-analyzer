
import os
import time
from typing import Dict, List
from langchain_pinecone import PineconeVectorStore
from pinecone import Pinecone, ServerlessSpec
from langchain_core.documents import Document
from langchain_text_splitters import RecursiveCharacterTextSplitter
from .llm_factory import get_llm
from langchain_google_genai import GoogleGenerativeAIEmbeddings

class RAGEngine:
    def __init__(self, api_key: str = None):
        self.api_key = api_key or os.getenv("GOOGLE_API_KEY")
        self.pinecone_api_key = os.getenv("PINECONE_API_KEY")
        self.pinecone_index_name = os.getenv("PINECONE_INDEX", "codebase-index")
        
        # Determine Embedding Provider
        self.llm_provider = os.getenv("LLM_PROVIDER", "google").lower()
        self.openai_key = os.getenv("OPENAI_API_KEY")
        
        # Logic: Usage OpenAI embeddings if:
        # 1. Provider is explicitly 'openai'
        # 2. OR Google Key is missing BUT OpenAI Key is present (Fallback/Mixed mode)
        use_openai_embeddings = (self.llm_provider == "openai") or (self.openai_key and not self.api_key)

        if use_openai_embeddings:
            if not self.openai_key:
                 raise ValueError("OPENAI_API_KEY not found (required for embeddings).")
            try:
                from langchain_openai import OpenAIEmbeddings
            except ImportError:
                 raise ImportError("langchain-openai not installed.")
                 
            self.embeddings = OpenAIEmbeddings(api_key=self.openai_key, model="text-embedding-3-small")
            print("Using Embeddings: OpenAI (text-embedding-3-small)")
        else:
            if not self.api_key:
                raise ValueError("GOOGLE_API_KEY not found (required for default embeddings).")
            
            self.embeddings = GoogleGenerativeAIEmbeddings(
                model="models/embedding-001",
                google_api_key=self.api_key
            )
            print("Using Embeddings: Google (embedding-001)")
            
        self.vector_store = None

    def calculate_hash(self, code_files: Dict[str, str]) -> str:
        """Calculates an MD5 hash of the codebase content."""
        import hashlib
        # Sort by path to ensure consistent ordering
        sorted_paths = sorted(code_files.keys())
        hasher = hashlib.md5()
        for path in sorted_paths:
            content = code_files[path]
            hasher.update(path.encode('utf-8'))
            hasher.update(content.encode('utf-8'))
        return hasher.hexdigest()

    def index_codebase(self, code_files: Dict[str, str], force: bool = False):
        """Splits code into chunks and indexes them in Pinecone. Skips if hash matches."""
        current_hash = self.calculate_hash(code_files)
        hash_file_path = ".codebase_hash"
        
        if not force and os.path.exists(hash_file_path):
            with open(hash_file_path, "r") as f:
                saved_hash = f.read().strip()
            if saved_hash == current_hash:
                print(f"Codebase unchanged (Hash: {saved_hash[:8]}). Skipping indexing.")
                # Initialize store for querying
                self.vector_store = PineconeVectorStore(
                    index_name=self.pinecone_index_name, 
                    embedding=self.embeddings
                )
                return

        print("Initializing Pinecone...")
        pc = Pinecone(api_key=self.pinecone_api_key)
        
        target_dimension = 1536 if "openai" in str(type(self.embeddings)).lower() else 768

        # Check if index exists and validate dimension
        existing_indexes = [i.name for i in pc.list_indexes()]
        if self.pinecone_index_name in existing_indexes:
            index_info = pc.describe_index(self.pinecone_index_name)
            if index_info.dimension != target_dimension:
                print(f"Dimension Mismatch! Index: {index_info.dimension}, Embeddings: {target_dimension}.")
                print("Deleting incompatible index...")
                pc.delete_index(self.pinecone_index_name)
                while self.pinecone_index_name in [i.name for i in pc.list_indexes()]:
                    time.sleep(1)
                print("Old index deleted.")
                existing_indexes = [i.name for i in pc.list_indexes()] # Refresh list

        # Create index if not exists (or was just deleted)
        if self.pinecone_index_name not in existing_indexes:
            print(f"Creating index: {self.pinecone_index_name} (Dimension: {target_dimension})")
            pc.create_index(
                name=self.pinecone_index_name,
                dimension=target_dimension, 
                metric="cosine",
                spec=ServerlessSpec(
                    cloud="aws",
                    region="us-east-1"
                )
            )
            while not pc.describe_index(self.pinecone_index_name).status['ready']:
                time.sleep(1)

        print("Indexing codebase...")
        documents = []
        for path, content in code_files.items():
            doc = Document(page_content=content, metadata={"source": path})
            documents.append(doc)

        text_splitter = RecursiveCharacterTextSplitter(
            chunk_size=2000,
            chunk_overlap=200,
            add_start_index=True,
        )
        all_splits = text_splitter.split_documents(documents)
        print(f"Created {len(all_splits)} chunks. Uploading to Pinecone...")

        self.vector_store = PineconeVectorStore.from_documents(
            documents=all_splits,
            embedding=self.embeddings,
            index_name=self.pinecone_index_name
        )
        
        # Save new hash
        with open(hash_file_path, "w") as f:
            f.write(current_hash)
            
        print("Indexing complete. Hash saved.")

    def query(self, query: str, k: int = 5) -> str:
        """Returns the answer and the retrieved context chunks."""
        if not self.vector_store:
             self.vector_store = PineconeVectorStore(
                index_name=self.pinecone_index_name, 
                embedding=self.embeddings
            )

        retriever = self.vector_store.as_retriever(search_type="similarity", search_kwargs={"k": k})
        docs = retriever.invoke(query)
        
        # Format context for the LLM
        context_str = ""
        for i, doc in enumerate(docs):
            context_str += f"\n--- Chunk {i+1} (Source: {doc.metadata.get('source', 'Unknown')}) ---\n{doc.page_content}\n"
            
        return context_str

    def chat(self, query: str):
        # Kept for backward compatibility with --chat mode
        if not self.vector_store:
             self.vector_store = PineconeVectorStore(
                index_name=self.pinecone_index_name, 
                embedding=self.embeddings
            )
            
        llm = get_llm()
        
        retriever = self.vector_store.as_retriever(search_type="similarity", search_kwargs={"k": 6})
        
        system_prompt = (
            "You are an assistant for question-answering tasks on a specific codebase. "
            "Use the following pieces of retrieved context to answer the question. "
            "If you don't know the answer, say that you don't know. "
            "Use three sentences maximum and keep the answer concise."
            "\n\n"
            "{context}"
        )

        prompt = ChatPromptTemplate.from_messages(
            [
                ("system", system_prompt),
                ("human", "{input}"),
            ]
        )

        question_answer_chain = create_stuff_documents_chain(llm, prompt)
        rag_chain = create_retrieval_chain(retriever, question_answer_chain)
        
        response = rag_chain.invoke({"input": query})
        return response["answer"]
