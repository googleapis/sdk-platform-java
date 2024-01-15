"""
Class that represents a library in a generation_config.yaml file
"""
from typing import Dict, List, Optional
from enum import Enum
from . import GAPIC

class _LibraryType(Enum):
  GAPIC_AUTO = 1
  GAPIC_COMBO = 2

class Library:
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
      GAPICs: List[GAPIC],
      group_id: Optional[str] = 'com.google.cloud',
      requires_billing: Optional[bool] = True,
  ):
      self.api_shortname = api_shortname
      self.name_pretty = name_pretty
      self.library_type = library_type
      self.group_id = group_id
      self.artifact_id = artifact_id
      self.requires_billing  = requires_billing
      self.api_description = api_description
      self.product_documentation = product_documentation
      self.client_documentation = client_documentation
      self.rest_documentation = rest_documentation
      self.rpc_documentation = rpc_documentation
      self.GAPICs = GAPICs
