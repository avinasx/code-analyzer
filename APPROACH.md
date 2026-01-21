# Approach & Evolution

This document chronicles the step-by-step evolution of the `llm_analyzer`, from a simple concept to a modular, intelligent tool.



## Phase 1: The Monolith (Initial Proof of Concept)

**Goal**: Create a script to read code and send it to an LLM.

-   **Structure**: A single script `analyzer.py`.
-   **Logic**:
    -   Walk the directory.
    -   Concatenate all text files.
    -   Send to OpenAI (GPT-4o) using LangChain.
-   **Outcome**: Effective for small demos but rigid. Hard to configure and tied to one provider.

## Phase 2: Restructuring & Gemini Integration

**Goal**: Make the tool local-friendly, cost-effective, and organized.

-   **Environment**: Switch from `requirements.txt` to `Pipenv` for deterministic builds.
-   **Model Swap**: Switched from OpenAI to **Google Gemini** (via `langchain-google-genai`) to leverage the massive context window (2M tokens) crucial for codebase analysis.
-   **Organization**:
    -   Moved the target Java code into a dedicated `spring_repo/` folder to separate "Data" from "Tool".
    -   Moved `analyzer.py` to the root for easier execution.
    -   Introduced `.env` support for API keys and configuration.

## Phase 3: Modularization

**Goal**: Clean code architecture (Separation of Concerns).

-   **Refactoring**: Split the monolithic `analyzer.py` into a Python package `analyzer/`:
    -   `main.py`: Entry point and configuration loading.
    -   `analyzer/models.py`: Pydantic definitions for the JSON output schema.
    -   `analyzer/reader.py`: File system traversal logic.
    -   `analyzer/llm.py`: LLM interaction logic.
-   **Benefit**: This made it significantly easier to test components individually and add new features without breaking the core flow.

## Phase 4: Intelligence & Robustness

**Goal**: Improve the quality of analysis and handling of edge cases.

-   **Dependency Graph**:
    -   *Challenge*: Reading files in alphabetical order leads to incoherent context (reading a `Service` before the `Entity` it uses).
    -   *Solution*: Implemented `analyzer/graph.py` using `networkx`. It parses `import` statements and topologically sorts the files. This ensures the LLM reads "definitions" before "usages".
    -   *Solution*: Added token estimation in `llm.py`. The tool now warns and truncates context if it approaches the 2M token limit, preventing API crashes.

## Phase 5: RAG & Multi-Provider Evolution (The "Enterprise" Refactor)

**Goal**: Scale beyond simple prompts to handle large repos, strict rate limits, and provider flexibility.

### 5.1. The "Resource Exhausted" Crisis
-   **Challenge**: Sending 120k+ tokens (entire codebase) to Gemini caused `429 RESOURCE_EXHAUSTED` errors. Retries helped, but it was fundamentally unscalable.
-   **Solution (Smart RAG)**:
    -   Stopped sending the full code.
    -   Implemented `RAGEngine` using **Pinecone**.
    -   **Smart Indexing**: Added a hash check (`md5`) to skip re-indexing if the codebase hasn't changed.
    -   **Workflow**: The LLM now sees *only* the file tree structure and queries Pinecone for specific details ("List controllers", "Analyze quality"), requesting ~8k tokens instead of 120k.

### 5.2. Multi-Provider Support (The "Master Switch")
-   **Challenge**: Hardcoded dependency on Google's `ChatGoogleGenerativeAI`.
-   **Solution (Factory Pattern)**:
    -   Created `analyzer/llm_factory.py`.
    -   Implemented a "Master Switch" (`LLM_PROVIDER` env var) to toggle between:
        -   `google` (Gemini 1.5)
        -   `groq` (Llama 3)
        -   `anthropic` (Claude 3.5)
        -   `openai` (GPT-4o)
    -   This allows `RAGEngine` and `LLMCodeAnalyzer` to be agnostic of the underlying model.

### 5.3. Nitty-Gritty Runtime Fixes (The "Battle_Scars")

#### A. The "Missing Parser" Warning
-   **Issue**: Logs spammed `Warning: javalang not installed`. The fallback regex parser was prone to errors.
-   **Fix**: Installed `javalang` and silenced the warning check in `parser.py`.

#### B. Cyclic Dependencies
-   **Issue**: `analyzer/dependency_graph.py` threw warnings about cyclic imports (A imports B, B imports A).
-   **Fix**: Updated the graph logic to catch `nx.NetworkXUnfeasible` and gracefully fallback to a simple sort, suppressing the noisy warning.

#### C. The "Embedding Trap" (Google vs OpenAI)
-   **Issue**: Running Groq failed because the RAG engine tried to initialize Google Embeddings without a Google API Key.
-   **Fix**: Implemented **Dynamic Embedding Selection**.
    -   If `LLM_PROVIDER=openai` OR (Google Key is missing but OpenAI Key exists) -> Use `OpenAIEmbeddings`.
    -   This enables a true "No Google" mode.

#### D. Vector Dimension Mismatch (768 vs 1536)
-   **Issue**: Switching from Google (768 dim) to OpenAI (1536 dim) caused Pinecone to crash with `Vector dimension mismatch`.
-   **Fix**: Added **Auto-Recreation Logic** in `rag.py`.
    -   The tool inspects the existing index.
    -   If the dimensions mismatch the current provider, it **deletes** the old index and creates a new one automatically.

#### E. Groq Rate Limits (413 Request Too Large)
-   **Issue**: Groq's Free Tier has a strict 8000 Tokens/Minute limit. Our default RAG context (`k=5` chunks) generated requests of ~8000+ tokens, causing immediate failure.
-   **Fix**: **Dynamic Context Sizing**.
    -   In `llm.py`, checked for `provider == "groq"`.
    -   If Groq, reduced chunks to `k=2` (~3000 tokens).
    -   Truncated the file tree context to 500 lines.
    -   **Result**: Requests dropped to safe levels, enabling analysis on free-tier Groq.

## Conclusion

The final solution is a result of iterative improvements:
1.  **Simple** (Get it working)
2.  **Configurable** (Get it right for the user's environment)
3.  **Modular** (Make it maintainable)
4.  **Intelligent** (Make it better at the specific task of code analysis)
