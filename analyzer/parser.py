
try:
    import javalang
except ImportError:
    javalang = None

class JavaParser:
    def __init__(self):
        if not javalang:
            # print("Warning: javalang not installed. Parsing will be limited.")
            pass

    def parse(self, content: str):
        if not javalang:
            return None, set()
        
        try:
            tree = javalang.parse.parse(content)
            package_name = tree.package.name if tree.package else ""
            imports = set()
            for imp in tree.imports:
                imports.add(imp.path)
            return package_name, imports
        except Exception:
            # Fallback for syntax errors or partial files
            return None, set()
