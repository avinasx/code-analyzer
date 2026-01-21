# LLM Codebase Analyzer

A Python tool that analyses a Codebase using fundamental LLM models(groq, gemini, anthropic, openai) via LangChain.

> [!NOTE]
> **Status**: This application has been fully tested and verified with **Groq** as the LLM provider and **Google** as embedding providers.
> While other providers (Anthropic, Gemini) are supported in the code, they have not been extensively battle-tested in this specific configuration.

## Prerequisites

- Python 3.12+
- Pipenv (for dependency management)
- Google Gemini API Key

## Configuration

1. **Install Dependencies**:
   ```bash
   pipenv install
   ```

2. **Environment Setup**:
   Copy the example environment file and configure it:
   ```bash
   cp .env.example .env
   ```
   
   Edit `.env` with your details:
   - `GOOGLE_API_KEY`: Your Google Gemini API Key.
   - `REPO_PATH`: Path to the repository you want to analyze (default: `./spring_repo`).
   - `GEMINI_MODEL`: The Gemini model to use (default: `gemini-1.5-pro`).
   - `PINECONE_API_KEY`: Required for RAG-based analysis.
   - `PINECONE_INDEX`: Name of the index (default: `codebase-index`).

   **Multi-Provider Support**:
   You can switch between LLM providers using the `LLM_PROVIDER` variable.
   
   - **Start Switch**:
     ```bash
     LLM_PROVIDER=google # Options: google, groq, anthropic
     ```
   
   - **Groq Config**:
     ```bash
     GROQ_API_KEY=gsk_...
     GROQ_MODEL=llama3-70b-8192
     ```
   
   - **Anthropic Config**:
     ```bash
     ANTHROPIC_API_KEY=sk-ant...
     ANTHROPIC_MODEL=claude-3-5-sonnet-20240620
     ```

   - **OpenAI Config**:
     ```bash
     OPENAI_API_KEY=sk-...
     OPENAI_MODEL=gpt-4o
     ```

   > **Note on Embeddings**: If you use Groq or Anthropic, you **MUST** provide either:
   > 1. `GOOGLE_API_KEY` (Uses Google Embeddings - Cheapest)
   > 2. `OPENAI_API_KEY` (Uses OpenAI Embeddings - Best fallback if no Google Key)

## Usage

Run the analyzer from the root directory.

```bash
pipenv run python main.py
```

To force re-indexing (ignoring smart hash check):
```bash
pipenv run python main.py --force-index
```

## Structure

- `main.py`: Entry point for the analyzer.
- `analyzer/`: Python package containing the logic.
    - `models.py`: Data structures.
    - `reader.py`: File traversing logic.
    - `llm_factory.py`: Handles provider selection (Google, Groq, OpenAI, etc).
    - `llm.py`: Interaction with the chosen LLM.
    - `rag.py`: Pinecone vector store management (The Brain).
- `spring_repo/`: The example Java Spring Boot codebase being analyzed.
- `output.json`: The generated analysis result.

## Methodology (The "Smart RAG" System)

### 1. Smart Indexing
The tool calculates a hash of your codebase.
-   **Changed?**: It chunks and embeds the code into **Pinecone**.
-   **Unchanged?**: It skips indexing to save time and money.
-   **Dimension Safe**: Automatically handles 768-dim (Google) vs 1536-dim (OpenAI) indices.

### 2. Structural RAG Analysis
We no longer send 100% of the code to the LLM. Instead, we use a 2-step process:
1.  **Structure**: The LLM sees the file tree to understand the "Map".
2.  **Retrieval**: It queries Pinecone for specific details ("List controllers", "Analyze quality").
    -   *Optimization*: If using Groq, we reduce context size dynamically to fit strict rate limits.

### 3. Knowledge Extraction
A structured prompt ensures the LLM returns a valid JSON object adhering to the `CodebaseAnalysis` schema.

