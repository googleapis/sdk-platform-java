"""
This script parses an owlbot.py file, specifically the call to `java.common_templates` in
order to extract the excluded files so it can be called with a custom template path
pointing to the templates hosted in `sdk-platform-java/library_generation/owlbot/templates`.
Briefly, this wraps the call to synthtool's common templates using a custom template folder.
"""

import os
import sys
from collections.abc import Sequence
from synthtool.languages.java import common_templates
from pathlib import Path
from library_generation.model.generation_config import from_yaml

script_dir = os.path.dirname(os.path.realpath(__file__))
repo_templates_path = os.path.normpath(os.path.join(script_dir, "..", "templates"))


def apply_repo_templates(configuration_yaml_path: str, monorepo: bool) -> None:
    config = from_yaml(configuration_yaml_path)
    print(f"repo_templates_path: {repo_templates_path}")
    print(f"excludes: {config.template_excludes}")
    common_templates(
        excludes=config.template_excludes,
        template_path=Path(repo_templates_path),
        monorepo=monorepo,
    )


def main(argv: Sequence[str]) -> None:
    if len(argv) != 3:
        raise ValueError(
            "Usage: python apply-repo-templates.py configuration_yaml_path monorepo"
        )

    configuration_yaml_path = argv[1]
    monorepo = argv[2]
    apply_repo_templates(configuration_yaml_path, monorepo.lower() == "true")


if __name__ == "__main__":
    main(sys.argv)
