
import re
import networkx as nx
from typing import Dict, List, Set

class DependencyGraph:
    def __init__(self):
        self.graph = nx.DiGraph()

        from .parser import JavaParser
        self.parser = JavaParser()

    def build_graph(self, code_files: Dict[str, str]):
        """
        Builds a directed graph where edge A -> B means A depends on B.
        """
        # 1. Map fully qualified ClassNames to their FilePaths
        class_to_file = {}
        
        # Store imports for second pass
        file_imports = {}

        for file_path, content in code_files.items():
            if not file_path.endswith(".java"):
                continue
            
            pkg, imports = self.parser.parse(content)
            
            # Fallback to regex if parser failed (or javalang missing)
            if pkg is None:
                # Basic regex fallback
                matches = re.findall(r'package\s+([\w\.]+);', content)
                pkg = matches[0] if matches else ""
                
                matches_imp = re.findall(r'import\s+([\w\.]+);', content)
                imports = set(matches_imp)

            # Assume class name matches filename
            class_name = file_path.split("/")[-1].replace(".java", "")
            full_name = f"{pkg}.{class_name}" if pkg else class_name
            
            class_to_file[full_name] = file_path
            self.graph.add_node(file_path)
            file_imports[file_path] = imports

        # 2. Add edges based on imports
        for file_path, imports in file_imports.items():
            for imp in imports:
                # If the imported class is in our codebase, add an edge
                if imp in class_to_file:
                    target_file = class_to_file[imp]
                    # FilePath depends on TargetFile
                    self.graph.add_edge(file_path, target_file)

    def get_topological_sort(self) -> List[str]:
        """
        Returns list of file paths sorted such that dependencies come *after* dependants?
        Actually for LLM context, usually we want definition BEFORE usage?
        
        Topological Sort: If A depends on B (A -> B), B comes after A in standard topological sort?
        No, standard sort: for edge u->v, u comes before v.
        A depends on B -> Edge A->B.
        Topological sort: A, B.
        
        Wait, for code reading:
        If A uses B, we probably want to read B first to understand what B does before A uses it?
        Or read A first (high level) then B (details)?
        
        Let's go with "Definitions first" (B then A). 
        So if A -> B (A depends on B), we want reverse topological order? 
        Or just standard sort on B -> A dependency?
        
        Let's assume "Dependencies First" (Bottom-up).
        If A imports B. A -> B.
        We want B, then A.
        In DAG A->B, topological sort gives A then B.
        So we want the REVERSE of topological sort of (A->B).
        
        Let's try:
        Graph: Node A, Node B. Edge A->B (A imports B).
        Topo sorts: [A, B] (because A has no incoming edges? No A has outgoing).
        Source is A (indegree 0). Sink is B (outdegree 0). 
        Topo sort visits A, then B.
        
        If we want B (Dependency) first, we need [B, A].
        So we reverse the topological sort.
        """
        try:
            # Reverse topological sort to get dependencies first (Leaves -> Roots roughly)
            return list(reversed(list(nx.topological_sort(self.graph))))
        except nx.NetworkXUnfeasible:
            # Cycle detected
            # print("Warning: Cyclic dependencies detected. Falling back to simple sort.")
            return sorted(list(self.graph.nodes()))
