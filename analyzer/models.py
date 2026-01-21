from typing import List, Dict, Optional
from pydantic import BaseModel, Field

class MethodInfo(BaseModel):
    signature: str = Field(description="The method signature")
    description: str = Field(description="Brief description of what the method does")

class FileAnalysis(BaseModel):
    filename: str = Field(description="Name of the file")
    purpose: str = Field(description="Main purpose of this file/class")
    methods: List[MethodInfo] = Field(description="List of key methods in this file")
    complexity_score: int = Field(description="Estimated complexity 1-10")
    notes: str = Field(description="Any other interesting observations")

class CodebaseAnalysis(BaseModel):
    project_overview: str = Field(description="High-level overview of the project based on the analysis")
    files: List[FileAnalysis] = Field(description="Detailed analysis of each significant file")
