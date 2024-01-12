"""
Class that represents the root of a generation_config.yaml
"""
from typing import Dict, List, Optional

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
      libraries: list[Library],
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


