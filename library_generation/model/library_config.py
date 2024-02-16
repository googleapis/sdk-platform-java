#!/usr/bin/env python
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
from library_generation.model.gapic_config import GapicConfig


class LibraryConfig:
    """
    Class that represents a library in a generation_config.yaml file
    """

    def __init__(
        self,
        api_shortname: str,
        api_description: str,
        name_pretty: str,
        product_documentation: str,
        gapic_configs: List[GapicConfig],
        library_type: Optional[str] = None,
        release_level: Optional[str] = None,
        api_id: Optional[str] = None,
        api_reference: Optional[str] = None,
        codeowner_team: Optional[str] = None,
        client_documentation: Optional[str] = None,
        distribution_name: Optional[str] = None,
        excluded_dependencies: Optional[str] = None,
        excluded_poms: Optional[str] = None,
        googleapis_commitish: Optional[str] = None,
        group_id: Optional[str] = "com.google.cloud",
        issue_tracker: Optional[str] = None,
        library_name: Optional[str] = None,
        rest_documentation: Optional[str] = None,
        rpc_documentation: Optional[str] = None,
        cloud_api: Optional[bool] = True,
        requires_billing: Optional[bool] = True,
    ):
        self.api_shortname = api_shortname
        self.api_description = api_description
        self.name_pretty = name_pretty
        self.product_documentation = product_documentation
        self.gapic_configs = gapic_configs
        self.library_type = library_type if library_type else "GAPIC_AUTO"
        self.release_level = release_level if release_level else "preview"
        self.api_id = api_id
        self.api_reference = api_reference
        self.codeowner_team = codeowner_team
        self.excluded_dependencies = excluded_dependencies
        self.excluded_poms = excluded_poms
        self.client_documentation = client_documentation
        self.distribution_name = distribution_name
        self.googleapis_commitish = googleapis_commitish
        self.group_id = group_id
        self.issue_tracker = issue_tracker
        self.library_name = library_name
        self.rest_documentation = rest_documentation
        self.rpc_documentation = rpc_documentation
        self.cloud_api = cloud_api
        self.requires_billing = requires_billing
