"""
Class that represents a GAPICs single entry, inside a `LibraryConfig` in a generation_config.yaml
"""
class GapicConfig:
  def __init__(
      self,
      proto_path: str,
  ):
    self.proto_path = proto_path
