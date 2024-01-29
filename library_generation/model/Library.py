"""
Class that represents a library in a generation_config.yaml file
"""
from typing import Dict, List, Optional
from enum import Enum
from .GapicConfig import GapicConfig

"""
Two possible library types:
  - GAPIC_AUTO: pure generated library
  - GAPIC_COMBO: generated library with a handwritten layer
"""
class _LibraryType(Enum):
  GAPIC_AUTO = 1
  GAPIC_COMBO = 2

class LibraryConfig:
  def __init__(
      self,
      api_shortname: str,
      name_pretty: Optional[str],
      library_type: _LibraryType,
      artifact_id: Optional[str],
      api_description: Optional[str],
      product_documentation: Optional[str],
      client_documentation: Optional[str],
      rest_documentation: Optional[str],
      rpc_documentation: Optional[str],
      gapicConfigs: List[GapicConfig],
      googleapis_commitish: Optional[str],
      group_id: Optional[str] = 'com.google.cloud',
      requires_billing: Optional[bool] = True,
  ):
      self.api_shortname = api_shortname
      self.name_pretty = name_pretty
      self.library_type = library_type
      self.artifact_id = artifact_id
      self.requires_billing  = requires_billing
      self.api_description = api_description
      self.product_documentation = product_documentation
      self.client_documentation = client_documentation
      self.rest_documentation = rest_documentation
      self.rpc_documentation = rpc_documentation
      self.group_id = group_id
      self.gapicConfigs = gapicConfigs
      self.googleapis_commitish = googleapis_commitish
