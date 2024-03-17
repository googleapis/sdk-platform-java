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
from hashlib import sha1

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
        extra_versioned_modules: Optional[str] = None,
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
        self.extra_versioned_modules = extra_versioned_modules

    def __eq__(self, other):
        return (
            self.api_shortname == other.api_shortname
            and self.api_description == other.api_description
            and self.name_pretty == other.name_pretty
            and self.product_documentation == other.product_documentation
            and self.gapic_configs == other.gapic_configs
            and self.library_type == other.library_type
            and self.release_level == other.release_level
            and self.api_id == other.api_id
            and self.api_reference == other.api_reference
            and self.codeowner_team == other.codeowner_team
            and self.excluded_dependencies == other.excluded_dependencies
            and self.excluded_poms == other.excluded_poms
            and self.client_documentation == other.client_documentation
            and self.distribution_name == other.distribution_name
            and self.googleapis_commitish == other.googleapis_commitish
            and self.group_id == other.group_id
            and self.issue_tracker == other.issue_tracker
            and self.library_name == other.library_name
            and self.rest_documentation == other.rest_documentation
            and self.rpc_documentation == other.rpc_documentation
            and self.cloud_api == other.cloud_api
            and self.requires_billing == other.requires_billing
            and self.extra_versioned_modules == other.extra_versioned_modules
        )

    def __hash__(self):
        m = sha1()
        m.update(
            str(
                [
                    self.api_shortname,
                    self.api_description,
                    self.name_pretty,
                    self.product_documentation,
                    self.library_type,
                    self.release_level,
                    self.api_id,
                    self.api_reference,
                    self.codeowner_team,
                    self.excluded_dependencies,
                    self.excluded_poms,
                    self.client_documentation,
                    self.distribution_name,
                    self.googleapis_commitish,
                    self.group_id,
                    self.issue_tracker,
                    self.library_name,
                    self.rest_documentation,
                    self.rpc_documentation,
                    self.cloud_api,
                    self.requires_billing,
                    self.extra_versioned_modules,
                ]
                + [config.proto_path for config in self.gapic_configs]
            ).encode("utf-8")
        )
        return int(m.hexdigest(), 16)
