"""
Class that represents a GAPIC entry, inside a `Library` in a generation_config.yaml
"""
class GAPIC:
  def __init__(
      self,
      proto_path: str,
  ):
    self.proto_path = proto_path
