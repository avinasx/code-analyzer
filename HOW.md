# How It Works: LLM Codebase Analyzer

This document explains the inner workings, assumptions, and technology choices behind the `llm_analyzer` tool.

## Methodology

The analyzer follows a linear pipeline:

1.  **Smart Discovery & Indexing (`rag.py`)**:
    -   The tool calculates a content hash of the codebase.
    -   **Hashing**: If the hash matches the last run, it **skips expensive indexing**.
    -   **Indexing**: If code changed, it chunks files and uploads them to **Pinecone**.
    -   **Failover**: It automatically selects 768-dim (Google) or 1536-dim (OpenAI) embeddings and recreates the index if dimensions mismatch.

3.  **RAG-Based Analysis (`llm.py`)**:
    -   **Step 1: Structural Context**: The LLM first sees the **Directory Tree**. This gives it a high-level map (Classes, Packages) for < 2k tokens.
    -   **Step 2: Targeted Retrieval**: The Analyzer queries Pinecone for specific sections:
        -   "What is the purpose?"
        -   "List controllers and repositories."
    -   **Groq Optimization**: If using Groq (8k TPM limit), it requests fewer chunks (`k=2`) to prevent rate limiting.

4.  **Output**:
    -   The validated JSON is saved to `output.json`.

## Assumptions & Limitations

1.  **RAG Context**: We assume the relevant code for a query resides within the top `k` retrieved chunks.
    -   *Mitigation*: We use a multi-step query approach (Purpose -> Architecture -> Methods -> Quality) to cover different aspects of the code.
2.  **File Types**: The tool assumes the project is primarily text-based. Binary assets or compiled code are ignored.
3.  **Single-Pass Analysis**: The tool performs a holistic "read-all" analysis. It does not currently support iterators or map-reduce strategies.

## Requirements

-   **Python 3.12+**: Required for modern typing and Pydantic features.
-   **API Keys**: 
    -   `GEMINI_API_KEY` (Default Generation)
    -   `PINECONE_API_KEY` (Required for Vector Store)
    -   `OPENAI_API_KEY` (Optional: For Generation or Embeddings Fallback)
-   **Pipenv**: Configured for deterministic dependency management.

## Tech Stack & Decisions

### 1. Language Models: Multi-Provider Support
-   **Primary**: Google Gemini 1.5 Pro (Best for Reasoning/Cost).
-   **Alternatives**:
    -   **Groq**: Extremely fast inference (Llama 3), optimized for RAG.
    -   **Anthropic**: Claude 3.5 Sonnet, strong reasoning.
    -   **OpenAI**: GPT-4o, industry standard.
-   **Design**: A Factory Pattern (`analyzer/llm_factory.py`) abstracts the provider complexity. The rest of the app just calls `get_llm()`.

### 2. Framework: LangChain
-   **Why?** Provides a standard interface for Model I/O across different providers (Google, Groq, Anthropic).
-   **RAG Integration**: We use LangChain's `PineconeVectorStore` and `RecursiveCharacterTextSplitter` for the RAG pipeline.

### 3. Validation: Pydantic
-   **Why?** Ensures the output is always machine-readable JSON.
-   **Comparison**:
    -   *vs regex/manual parsing*: LLMs can "hallucinate" formats. Pydantic enforces strict types (e.g., ensuring `complexity_score` is an integer).

### 4. Graph Analysis: NetworkX
-   **Why?** Code isn't linear; it's a web of dependencies.
-   **Function**: We use `networkx` to build a dependency graph. If `javalang` is installed, we parse imports more accurately. If a cycle is detected, we gracefully fallback to a simple sort.

### 5. Context Safety
-   **Old Approach**: Token counting for full-context.
-   **New Approach**: RAG. We act conservatively, sending only ~3k-8k tokens per request, rendering the 2M limit of Gemini a "nice to have" rather than a hard requirement. This allows models like **Groq (Llama3)** to work flawlessly.

### 6. Vector Search: Pinecone (RAG)
-   **Function**: Indexes the codebase into chunked vectors.
-   **Dynamic Embeddings**: 
    -   Default: `GoogleGenerativeAIEmbeddings` (Free-ish).
    -   Fallback: `OpenAIEmbeddings` (If Google Key is missing).
-   **Why Pinecone?**: Serverless. We don't want users managing a local Chroma/FAISS DB file.

### 7. AST Parsing: Javalang
-   **Why?** Regex is brittle.
-   **Function**: We use `javalang` to parse Java files into Abstract Syntax Trees (AST). This allows reliable extraction of `package` declarations and `import` statements for the Dependency Graph, even if code formatting varies.

## Future Improvements

1.  **Web Interface**:
    -   *Idea*: A Streamlit or React UI for the analyzer.

2.  **CI/CD Integration**:
    -   *Idea*: Run the analyzer automatically as a GitHub Action on every commit to catch architectural regressions early.

3.  **Custom Rule Engine**:
    -   *Idea*: Allow users to define specific architectural constraints (e.g., "Controllers must never call Repositories directly").

4.  **Multi-Agent Architecture**:
    -   *Idea*: Decompose the analysis into specialized agents (Security Expert, Performance Optimizer, Clean Code Auditor) and aggregate their findings.

5.  **IDE Plugin**:
    -   *Idea*: Package the tool as a VS Code or IntelliJ extension for real-time feedback.

6.  **Language Support**:
    -   *Idea*: Add more language support, other than JAVA.
