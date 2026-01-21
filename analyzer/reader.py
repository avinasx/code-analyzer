import os
from typing import List, Dict

class CodebaseReader:
    def __init__(self, root_dir: str, ignore_patterns: List[str] = None):
        self.root_dir = root_dir
        self.ignore_patterns = ignore_patterns or [
            ".git", ".gradle", "build", "gradle", ".idea", 
            "llm_analyzer", "__pycache__", "node_modules", 
            ".DS_Store", "target", ".vscode", ".venv", ".env",
            "analyzer" # Ignore self if inside root
        ]
        self.extensions = [".java", ".xml", ".md", ".properties"]

    def _should_ignore(self, path: str) -> bool:
        for pattern in self.ignore_patterns:
            if pattern in path:
                return True
        return False

    def get_files(self) -> Dict[str, str]:
        code_files = {}
        if not os.path.exists(self.root_dir):
            print(f"Error: Repository path '{self.root_dir}' does not exist.")
            return {}

        for root, dirs, files in os.walk(self.root_dir):
            # Modify dirs in-place to skip ignored directories
            dirs[:] = [d for d in dirs if not self._should_ignore(os.path.join(root, d))]
            
            for file in files:
                if self._should_ignore(file):
                    continue
                
                _, ext = os.path.splitext(file)
                if ext in self.extensions or file == "Dockerfile":
                    full_path = os.path.join(root, file)
                    try:
                        with open(full_path, "r", encoding="utf-8") as f:
                            content = f.read()
                            # Basic check to avoid reading huge files or binaries that pretend to be text
                            if len(content) < 100000: 
                                relative_path = os.path.relpath(full_path, self.root_dir)
                                code_files[relative_path] = content
                    except Exception as e:
                        print(f"Skipping {full_path}: {e}")
        # Sort files using Dependency Graph
        # This puts dependencies before dependent files (e.g. Entity before Service)
        from .graph import DependencyGraph
        graph = DependencyGraph()
        graph.build_graph(code_files)
        sorted_paths = graph.get_topological_sort()
        
        # Reconstruct dict in sorted order, appending non-analyzed files (xml, md) at the end
        sorted_files = {}
        
        # Add sorted Java files
        for path in sorted_paths:
            if path in code_files:
                sorted_files[path] = code_files[path]
                
        # Add remaining files (non-java or disconnected)
        for path, content in code_files.items():
            if path not in sorted_files:
                sorted_files[path] = content
                
        return sorted_files
