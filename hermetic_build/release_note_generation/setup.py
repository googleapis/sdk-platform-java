"""
Package information of library_generation python scripts
"""

from setuptools import setup

setup(
    name="common",
    version="0.1",
    python_requires=">=3.12",
    package_dir={
        "common": ".",
    },
    install_requires=[
        "PyYAML==6.0.2",
    ],
)
