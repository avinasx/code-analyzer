import os
import time
from typing import Dict
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import PydanticOutputParser
from .models import CodebaseAnalysis
from .llm_factory import get_llm

class LLMCodeAnalyzer:
    def __init__(self, api_key: str = None):
        # API key handling is now done in factory or env checks
        self.llm = get_llm()
        self.parser = PydanticOutputParser(pydantic_object=CodebaseAnalysis)

    def analyze_with_rag(self, code_files: Dict[str, str], rag_engine) -> Dict:
        # Determine Context Size based on Provider
        provider = os.getenv("LLM_PROVIDER", "google").lower()
        # Groq has strict TPM limits (often 6k-15k/min), so we reduce context aggressivey
        k_chunks = 2 if provider == "groq" else 5
        print(f"Using Context Size: k={k_chunks} chunks (Provider: {provider})")

        # 1. Provide Structural Context (File tree only)
        # Limit tree size to prevent token explosion
        sorted_files = sorted(code_files.keys())
        if len(sorted_files) > 500:
            file_tree = "\n".join(sorted_files[:500]) + "\n... (truncated)"
        else:
            file_tree = "\n".join(sorted_files)
        
        # 2. Targeted RAG Queries to gather info
        print("Querying RAG for Application Purpose...")
        purpose_context = rag_engine.query("What is the high-level purpose of this application? What problem does it solve?", k=k_chunks)
        
        print("Querying RAG for Architecture & Controllers...")
        arch_context = rag_engine.query("List the key controllers in this project and their main responsibilities. Also list the repositories.", k=k_chunks)
        
        print("Querying RAG for Services & Methods...")
        methods_context = rag_engine.query("What are the key Service classes and their public methods? What is their intent?", k=k_chunks)
        
        print("Querying RAG for Quality Analysis...")
        quality_context = rag_engine.query("Analyze the code quality, complexity, and potential improvements or issues.", k=k_chunks)

        # 3. Final Synthesis
        combined_context = (
            f"FILE STRUCTURE:\n{file_tree}\n\n"
            f"PURPOSE CONTEXT:\n{purpose_context}\n\n"
            f"ARCHITECTURE CONTEXT:\n{arch_context}\n\n"
            f"METHODS CONTEXT:\n{methods_context}\n\n"
            f"QUALITY CONTEXT:\n{quality_context}\n"
        )
        
        prompt = ChatPromptTemplate.from_template(
            """
            You are an expert Senior Software Engineer and Code Auditor.
            Your task is to analyze the Codebase using the provided RAG context and File Structure.
            
            Synthesize the information from the context sections to produce a comprehensive report.
            
            Focus on:
            1. The overall purpose of the application.
            2. Identifying key controllers, services, and repositories.
            3. Extracting public method signatures and their intent.
            4. Assessing code complexity and quality.

            Output the result in a strict JSON format matching the following structure:
            {format_instructions}
            
            RETRIEVED CONTEXT:
            {context}
            """
        )

        messages = prompt.format_messages(
            format_instructions=self.parser.get_format_instructions(),
            context=combined_context
        )
        
        max_retries = 3
        retry_delay = 5

        for attempt in range(max_retries + 1):
            try:
                print(f"Synthesizing Report... (Attempt {attempt + 1})")
                response = self.llm.invoke(messages)
                result = self.parser.parse(response.content)
                return result.dict()
            except Exception as e:
                error_str = str(e)
                if "RESOURCE_EXHAUSTED" in error_str or "429" in error_str:
                    print(f"⚠️ Rate limit hit: {e}")
                    if attempt < max_retries:
                        wait = retry_delay * (2 ** attempt)
                        print(f"Waiting {wait} seconds before retrying...")
                        time.sleep(wait)
                    else:
                        print(f"❌ Max retries ({max_retries}) exceeded.")
                        return {"error": f"Rate limit exceeded: {str(e)}"}
                else:
                    print(f"Error during synthesis: {e}")
                    return {"error": str(e)}
