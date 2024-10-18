"""
Package information of library_generation python scripts
"""

from setuptools import setup

setup(
    name="release_note_generation",
    version="0.1",
    python_requires=">=3.12",
    package_dir={
        "release_note_generation": ".",
    },
    install_requires=[
        "click==8.1.7",
        "common==0.1",  # local package
        "GitPython==3.1.43",
        "PyYAML==6.0.2",
    ],
)
