"""
Package information of library_generation python scripts
"""

from setuptools import setup

setup(
    name="library_generation",
    version="0.1",
    package_dir={
        "library_generation": ".",
    },
    package_data={
        "library_generation": [
            "*.sh",
            "templates/*.j2",
            "gapic-generator-java-wrapper",
            "requirements.*",
            "owlbot/bin/*.sh",
            "owlbot/templates/clirr/*.j2",
            "owlbot/templates/poms/*.j2",
            "owlbot/templates/java_library/**/*",
        ],
    },
)
