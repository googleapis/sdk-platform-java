"""
Class that represents the root of a generation_config.yaml
"""
import yaml
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
      destination_path: Optional[str],
      libraries: List[Library],
  ):
    self.gapic_generator_version = gapic_generator_version
    self.grpc_version = grpc_version
    self.protobuf_version = protobuf_version
    self.googleapis_commitish = googleapis_commitish
    self.owlbot_cli_image = owlbot_cli_image
    self.synthtool_commitish = synthtool_commitish
    self.destination_path = destination_path
    self.libraries = libraries

  """
  Parses a yaml located in path_to_yaml. Returns the parsed configuration represented
  by the "model" classes
  """
  @staticmethod
  def from_yaml(path_to_yaml: str):
    config = None
    with open(path_to_yaml, 'r') as file_stream:
      config = yaml.load(file_stream, yaml.Loader)

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
        _optional(library, 'artifact_id', None),
        _optional(library, 'api_description', None),
        _optional(library, 'product_documentation', None),
        _optional(library, 'client_documentation', None),
        _optional(library, 'rest_documentation', None),
        _optional(library, 'rpc_documentation', None),
        parsed_gapics,
        _optional(library, 'googleapis_commitish', None),
        _optional(library, 'group_id', 'com.google.cloud'),
        _optional(library, 'requires_billing', None),
      )
      parsed_libraries.append(new_library)

    parsed_config = GenerationConfig(
      _required(config, 'gapic_generator_version'),
      _optional(config, 'grpc_version', None),
      _optional(config, 'protobuf_version', None),
      _required(config, 'googleapis_commitish'),
      _required(config, 'owlbot_cli_image'),
      _required(config, 'synthtool_commitish'),
      _optional(config, 'destination_path', None),
      parsed_libraries
    )

    return parsed_config

def _required(config: Dict, key: str):
  if key not in config:
    raise ValueError(f'required key {key} not found in yaml')
  return config[key]

def _optional(config: Dict, key: str, default: any):
  if key not in config:
    return default 
  return config[key]
