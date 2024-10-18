"""
Package information of library_generation python scripts
"""

from setuptools import setup

setup(
    name="library_generation",
    version="0.1",
    python_requires=">=3.12",
    package_dir={
        "library_generation": ".",
        "synthtool": "owlbot/synthtool",
    },
    install_requires=[
        "attrs==24.2.0",
        "click==8.1.7",
        "common==0.1",  # local package
        "GitPython==3.1.43",
        "jinja2==3.1.4",
        "lxml==5.3.0",
        "PyYAML==6.0.2",
        "requests==2.32.3",
        "requests-mock==1.12.1",
    ],
    package_data={
        "library_generation": [
            "generate_library.sh",
            "postprocess_library.sh",
            "utils/utilities.sh",
            "templates/*.j2",
            "gapic-generator-java-wrapper",
            "owlbot/bin/*.sh",
            "owlbot/src/*.py",
            "owlbot/src/poms/*.py",
            "owlbot/templates/clirr/*.j2",
            "owlbot/templates/poms/*.j2",
            "owlbot/templates/java_library/**/*",
        ],
        "synthtool": ["owlbot/synthtool/**/*"],
    },
)
