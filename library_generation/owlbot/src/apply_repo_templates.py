"""
This script parses an owlbot.py file, specifically the call to `java.common_templates` in
order to extract the excluded files so it can be called with a custom template path
pointing to the templates hosted in `sdk-platform-java/library_generation/owlbot/templates`.
Briefly, this wraps the call to synthtool's common templates using a custom template folder.
"""

import os
from collections.abc import Sequence
from absl import app
from synthtool.languages.java import common_templates
from pathlib import Path
import re

script_dir = os.path.dirname(os.path.realpath(__file__))
repo_templates_path = os.path.join(script_dir, '..', 'templates', 'java_library')

def apply_repo_templates(owlbot_py_path: str, monorepo: bool) -> None:
  excludes = []
  with open(owlbot_py_path) as contents:
    excludes += _parse_template_excludes(contents.read())
  print(f'repo_templates_path: {repo_templates_path}')
  print(f'excludes: {excludes}')
  common_templates(
      excludes=excludes,
      template_path=Path(repo_templates_path),
      monorepo=monorepo
  )


def parse_template_excludes(owlbot_py_contents: str) -> str:
  print(f'owlbot_py_contents: {owlbot_py_contents}')
  excludes = re.search(
      'java\.common_templates\(.*excludes=\[(.*)\].*\)',
      owlbot_py_contents,
      re.MULTILINE | re.DOTALL
  )
  if excludes is None:
    raise ValueError('Could not parse owlbot.py exclusions')
  raw_excludes = excludes.group(1).split(',')
  result = []
  for raw_exc in raw_excludes:
    match = re.search('["\'](.*)["\']', raw_exc)
    if match:
      result.append(match.group(1))
  return result

def main(argv: Sequence[str]) -> None:
  if len(argv) != 3:
    raise app.UsageError("Usage: python apply-repo-templates.py owlbot_py_path monorepo")

  owlbot_py_path = argv[1]
  monorepo = argv[2]
  apply_repo_templates(owlbot_py_path, monorepo.lower() == 'true')



if __name__ == "__main__":
  app.run(main)
