import os
import json
from dotenv import load_dotenv
from analyzer.reader import CodebaseReader
from analyzer.llm import LLMCodeAnalyzer

def main():
    # Load environment variables
    load_dotenv()
    
    # Load configuration
    repo_path = os.getenv("REPO_PATH", "./spring_repo")
    
    # Resolve absolute path
    if not os.path.isabs(repo_path):
        repo_path = os.path.abspath(os.path.join(os.path.dirname(__file__), repo_path))
    
    print(f"Analyzing codebase at: {repo_path}")
    
    reader = CodebaseReader(repo_path)
    code_files = reader.get_files()
    
    if not code_files:
        print("No files found or directory validation failed.")
        return

    print(f"Found {len(code_files)} relevant files.")
    
    # Initialize RAG Engine
    import sys
    from analyzer.rag import RAGEngine
    
    try:
        rag = RAGEngine()
        # Smart Indexing (checks hash internally)
        force_index = "--force-index" in sys.argv
        rag.index_codebase(code_files, force=force_index)
        
    except ValueError as e:
        print(f"RAG Configuration Error: {e}")
        return

    # Check for chat mode
    if "--chat" in sys.argv:
        print("\n" + "="*50)
        print("Chat Mode Enabled. Type 'exit' to quit.")
        print("="*50)
        
        while True:
            query = input("\nAsk about the codebase: ")
            if query.lower() in ["exit", "quit"]:
                break
            
            try:
                answer = rag.chat(query)
                print(f"\n>> {answer}")
            except Exception as e:
                print(f"Error: {e}")
        return

    try:
        analyzer = LLMCodeAnalyzer()
    except ValueError as e:
        print(f"Configuration Error: {e}")
        return
    
    # Use RAG-based analysis
    analysis_result = analyzer.analyze_with_rag(code_files, rag)
    
    output_path = os.path.join(os.path.dirname(__file__), "output.json")
    with open(output_path, "w") as f:
        json.dump(analysis_result, f, indent=2)
    
    print(f"Analysis complete. Results saved to {output_path}")

if __name__ == "__main__":
    main()
