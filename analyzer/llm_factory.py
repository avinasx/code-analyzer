import os
from langchain_google_genai import ChatGoogleGenerativeAI

def get_llm(provider=None):
    """
    Factory function to return the configured LLM provider.
    Priority:
    1. provider argument
    2. LLM_PROVIDER env variable
    3. Defaults to 'google'
    """
    provider = provider or os.getenv("LLM_PROVIDER", "google").lower()
    
    if provider == "groq":
        try:
            from langchain_groq import ChatGroq
        except ImportError:
            raise ImportError("langchain-groq is not installed. Please run: pipenv install langchain-groq")
            
        api_key = os.getenv("GROQ_API_KEY")
        if not api_key:
            raise ValueError("GROQ_API_KEY not found in environment.")
            
        model_name = os.getenv("GROQ_MODEL", "llama3-70b-8192")
        print(f"Using Provider: Groq (Model: {model_name})")
        return ChatGroq(api_key=api_key, model=model_name, temperature=0)
        
    elif provider == "anthropic":
        try:
            from langchain_anthropic import ChatAnthropic
        except ImportError:
            raise ImportError("langchain-anthropic is not installed. Please run: pipenv install langchain-anthropic")
            
        api_key = os.getenv("ANTHROPIC_API_KEY")
        if not api_key:
            raise ValueError("ANTHROPIC_API_KEY not found in environment.")
            
        model_name = os.getenv("ANTHROPIC_MODEL", "claude-3-5-sonnet-20240620")
        print(f"Using Provider: Anthropic (Model: {model_name})")
        return ChatAnthropic(api_key=api_key, model=model_name, temperature=0)

    elif provider == "openai":
        try:
            from langchain_openai import ChatOpenAI
        except ImportError:
            raise ImportError("langchain-openai is not installed. Please run: pipenv install langchain-openai")

        api_key = os.getenv("OPENAI_API_KEY")
        if not api_key:
            raise ValueError("OPENAI_API_KEY not found in environment.")

        model_name = os.getenv("OPENAI_MODEL", "gpt-4o")
        print(f"Using Provider: OpenAI (Model: {model_name})")
        return ChatOpenAI(api_key=api_key, model=model_name, temperature=0)
        
    else: # Default to Google
        api_key = os.getenv("GOOGLE_API_KEY")
        if not api_key:
            # Fallback check, though main.py usually handles this
            pass
            
        model_name = os.getenv("GEMINI_MODEL", "gemini-1.5-pro")
        print(f"Using Provider: Google (Model: {model_name})")
        return ChatGoogleGenerativeAI(
            model=model_name,
            temperature=0,
            google_api_key=api_key
        )
