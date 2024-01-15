"""
parses a config yaml and generates libraries via generate_composed_library.py
"""

import click
import yaml
import json
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
  config = from_yaml(generation_config_yaml)

def from_yaml(path_to_yaml):
  file_stream = open(path_to_yaml, 'r')
  config = yaml.load(file_stream, yaml.Loader)
  print(json.dumps(config, indent=2))

  libraries = _required(config, 'libraries')

  parsed_libraries = list()
  for library in libraries:
    gapics = _required(library, 'GAPICs')

    parsed_gapics = list()
    for gapic in gapics:
      proto_path = _required(gapic, 'proto_path')
      new_gapic = GAPIC(proto_path)
      parsed_gapics.append(new_gapic)

    new_library = Library(
      _required(library, 'api_shortname'),
      _optional(library, 'name_pretty', None),
      _required(library, 'library_type'),
      _optional(library, 'group_id', 'com.google.cloud'),
      _optional(library, 'artifact_id', None),
      _optional(library, 'requires_billing', None),
      _optional(library, 'api_description', None),
      _optional(library, 'product_documentation', None),
      _optional(library, 'client_documentation', None),
      _optional(library, 'rest_documentation', None),
      _optional(library, 'rpc_documentation', None),
      parsed_gapics,
    )
    parsed_libraries.append(new_library)

  parsed_config = GenerationConfig(
    _required(config, 'gapic_generator_version'),
    _optional(config, 'grpc_version', None),
    _optional(config, 'protobuf_version', None),
    _required(config, 'googleapis_commitish'),
    _required(config, 'owlbot_cli_image'),
    _required(config, 'synthtool_commitish'),
    _required(config, 'python_version'),
    _optional(config, 'destination_path', None),
    parsed_libraries
  )

  print(parsed_config)



def _required(config: Dict, key: str):
  if key not in config:
    raise ValueError(f'required key {key} not found in yaml')
  return config[key]

def _optional(config: Dict, key: str, default: any):
  if key not in config:
    return default 
  return config[key]



if __name__ == "__main__":
  main()
