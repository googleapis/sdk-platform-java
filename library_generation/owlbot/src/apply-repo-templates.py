"""TODO(diegomarquezp): DO NOT SUBMIT without one-line documentation for apply-repo-templates.

TODO(diegomarquezp): DO NOT SUBMIT without a detailed description of apply-repo-templates.
"""

import os
from collections.abc import Sequence
from absl import app
from synthtool.languages.java import common_templates
from pathlib import Path

script_dir = os.path.dirname(os.path.realpath(__file__))
repo_templates_path = os.path.join(script_dir, '..', 'templates', 'java_library')

def apply_repo_templates(owlbot_py_path: str) -> None:
  excludes = []
  with open(owlbot_py_path) as contents:
    excludes += _parse_template_excludes(contents)
  print(f'repo_templates_path: {repo_templates_path}')
  print(f'excludes: {excludes}')
  common_templates(excludes=excludes, template_path=Path(repo_templates_path))


def _parse_template_excludes(owlbot_py_contents: str) -> str:
  return [ 'README.md' ]
  pass

def main(argv: Sequence[str]) -> None:
  if len(argv) != 2:
    raise app.UsageError("Usage: python apply-repo-templates.py [owlbot_py_path]")

  owlbot_py_path = argv[1]
  apply_repo_templates(owlbot_py_path)



if __name__ == "__main__":
  app.run(main)
