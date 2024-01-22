"""
Parses a config yaml and generates libraries via generate_composed_library.py
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
    "--generation-config-yaml",
    required=True,
    type=str,
    help="""
    Path to generation_config.yaml that contains the metadata about library generation
    """
)
@click.option(
    "--enable-postprocessing",
    required=False,
    type=str,
    help="""
    Path to repository where generated files will be merged into, via owlbot copy-code.
    Specifying this option enables postprocessing
    """
)
@click.option(
    "--target-library-api-shortname",
    required=False,
    type=str,
    help="""
    If specified, only the `library` with api_shortname = target-library-api-shortname will
    be generated. If not specified, all libraries in the configuration yaml will be generated
    """
)
def generate_from_yaml(
    generation_config_yaml,
    enable_postprocessing,
    target_library_api_shortname
):
  config = GenerationConfig.from_yaml(generation_config_yaml)
  target_libraries = config.libraries
  if target_library_api_shortname is not None:
    target_libraries = [library for library in config.libraries
                              if library.api_shortname == target_library_api_shortname]
  for library in target_libraries:
    print(f'generating library {library.api_shortname}')
    generate_composed_library(
        config, library, enable_postprocessing
    )
    pass
  pass





if __name__ == "__main__":
  main()
