"""
Class that represents the root of a generation_config.yaml
"""
import yaml
import json
from typing import List, Optional, Dict
from .Library import Library
from .GAPIC import GAPIC


class GenerationConfig:
  def __init__(
      self,
      gapic_generator_version: str,
      grpc_version: Optional[str],
      protobuf_version: Optional[str],
      googleapis_commitish: str,
      owlbot_cli_image: str,
      synthtool_commitish: str,
      python_version: str,
      destination_path: Optional[str],
      libraries: List[Library],
  ):
    self.gapic_generator_version = gapic_generator_version
    self.grpc_version = grpc_version
    self.protobuf_version = protobuf_version
    self.googleapis_commitish = googleapis_commitish
    self.owlbot_cli_image = owlbot_cli_image
    self.synthtool_commitish = synthtool_commitish
    self.python_version = python_version
    self.destination_path = destination_path
    self.libraries = libraries

  @staticmethod
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

def _required(config: Dict, key: str):
  if key not in config:
    raise ValueError(f'required key {key} not found in yaml')
  return config[key]

def _optional(config: Dict, key: str, default: any):
  if key not in config:
    return default 
  return config[key]
