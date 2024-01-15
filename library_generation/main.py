"""
parses a config yaml and generates libraries via generate_composed_library.py
"""

import click
from generate_composed_library import generate_composed_library
from typing import Dict
from model.GenerationConfig import GenerationConfig
from model.Library import Library
from model.GAPIC import GAPIC
from collections.abc import Sequence
from absl import app

@click.group(invoke_without_command=False)
@click.pass_context
@click.version_option(message="%(version)s")
def main(ctx):
    pass

@main.command()
@click.option(
    "--generation_config_yaml",
    required=True,
    type=str,
    help="""
    Path to generation_config.yaml that contains the metadata about library generation
    """
)
@click.option(
    "--repository_location",
    required=False,
    type=str,
    help="""
    Path to repository where generated files will be merged into, via owlbot copy-code.
    Specifying this option enables postprocessing
    """
)
def generate_from_yaml(
    generation_config_yaml,
    repository_location
):
  config = GenerationConfig.from_yaml(generation_config_yaml)
  for library in config.libraries:





if __name__ == "__main__":
  main()
