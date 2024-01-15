"""
Class that represents the root of a generation_config.yaml
"""
from typing import List, Optional, Dict
import yaml
from . import Library


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

def from_yaml(path_to_yaml):
  file_stream = open(path_to_yaml, 'r')
  config = yaml.load(file_stream, yaml.Loader)

  libraries = _required(config, 'libraries')

  parsed_libraries = list()
  for library in libraries:
    gapics = library['GAPICs']

    parsed_gapics = list()
    for gapic in gapics:
      proto_path = _required(gapic, 'proto_path')
      new_gapic = GAPIC(proto_path)
      parsed_gapics.append(new_gapic)

    new_library = Library(
      _required(library, 'api_shortname'),
      library['name_pretty'],
      _required(library, 'library_type'),
      library['group_id'],
      library['artifact_id'],
      library['requires_billing'],
      library['api_description'],
      library['product_documentation'],
      library['client_documentation'],
      library['rest_documentation'],
      library['rpc_documentation'],
      parsed_gapics,
    )
    parsed_libraries.append(new_library)

  parsed_config = GenerationConfig(
    _required(config, 'gapic_generator_version'),
    config['grpc_version'],
    config['protobuf_version'],
    _required(config, 'googleapis_commitish'),
    _required(config, 'owlbot_cli_image'),
    _required(config, 'synthtool_commitish'),
    _required(config, 'python_version'),
    config['destination_path'],
    parsed_libraries
  )

  print(parsed_config)



def _required(config: Dict, key: str):
  if key not in config:
    raise ValueError(f'required key {key} not found in yaml')
  return config[key]


