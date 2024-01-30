#!/usr/bin/env python3
#  Copyright 2024 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from typing import List, Optional
from enum import Enum
from gapic_config import GapicConfig


class _LibraryType(Enum):
    """
    Two possible library types:
      - GAPIC_AUTO: pure generated library
      - GAPIC_COMBO: generated library with a handwritten layer
    """

    GAPIC_AUTO = 1
    GAPIC_COMBO = 2


class _ReleaseLevel(Enum):
    stable = 1
    preview = 2


class LibraryConfig:
    """
    Class that represents a library in a generation_config.yaml file
    """

    def __init__(
        self,
        api_shortname: str,
        name_pretty: str,
        product_documentation: str,
        api_description: str,
        gapic_configs: List[GapicConfig],
        library_name: Optional[str],
        artifact_id: Optional[str],
        client_documentation: Optional[str],
        rest_documentation: Optional[str],
        rpc_documentation: Optional[str],
        googleapis_commitish: Optional[str],
        distribution_name: Optional[str],
        api_id: Optional[str],
        library_type: Optional[_LibraryType] = _LibraryType.GAPIC_AUTO,
        release_level: Optional[_ReleaseLevel] = _ReleaseLevel.preview,
        group_id: Optional[str] = "com.google.cloud",
        requires_billing: Optional[bool] = True,
        cloud_api: Optional[bool] = True,
    ):
        self.api_shortname = api_shortname
        self.name_pretty = name_pretty
        self.product_documentation = product_documentation
        self.api_description = api_description
        self.gapic_configs = gapic_configs
        self.library_name = library_name
        self.artifact_id = artifact_id
        self.client_documentation = client_documentation
        self.rest_documentation = rest_documentation
        self.rpc_documentation = rpc_documentation
        self.googleapis_commitish = googleapis_commitish
        self.distribution_name = distribution_name
        self.api_id = api_id
        self.library_type = library_type
        self.release_level = release_level
        self.group_id = group_id
        self.requires_billing = requires_billing
        self.cloud_api = cloud_api
