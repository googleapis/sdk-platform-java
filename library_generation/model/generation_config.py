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


import yaml
from typing import List, Optional, Dict
from library_generation.model.library_config import LibraryConfig
from library_generation.model.gapic_config import GapicConfig


class GenerationConfig:
    """
    Class that represents the root of a generation_config.yaml
    """

    def __init__(
        self,
        gapic_generator_version: str,
        grpc_version: Optional[str],
        protobuf_version: Optional[str],
        googleapis_commitish: str,
        owlbot_cli_image: str,
        synthtool_commitish: str,
        destination_path: Optional[str],
        libraries: List[LibraryConfig],
    ):
        self.gapic_generator_version = gapic_generator_version
        self.grpc_version = grpc_version
        self.protobuf_version = protobuf_version
        self.googleapis_commitish = googleapis_commitish
        self.owlbot_cli_image = owlbot_cli_image
        self.synthtool_commitish = synthtool_commitish
        self.destination_path = destination_path
        self.libraries = libraries


def from_yaml(path_to_yaml: str):
    """
    Parses a yaml located in path_to_yaml. Returns the parsed configuration
    represented by the "model" classes
    """
    with open(path_to_yaml, "r") as file_stream:
        config = yaml.load(file_stream, yaml.Loader)

    libraries = __required(config, "libraries")

    parsed_libraries = list()
    for library in libraries:
        gapics = __required(library, "GAPICs")

        parsed_gapics = list()
        for gapic in gapics:
            proto_path = __required(gapic, "proto_path")
            new_gapic = GapicConfig(proto_path)
            parsed_gapics.append(new_gapic)

        new_library = LibraryConfig(
            api_shortname=__required(library, "api_shortname"),
            name_pretty=__required(library, "name_pretty"),
            product_documentation=__required(library, "product_documentation"),
            api_description=__required(library, "api_description"),
            gapic_configs=parsed_gapics,
            library_name=__optional(library, "library_name", None),
            client_documentation=__optional(library, "client_documentation", None),
            rest_documentation=__optional(library, "rest_documentation", None),
            rpc_documentation=__optional(library, "rpc_documentation", None),
            googleapis_commitish=__optional(library, "googleapis_commitish", None),
            distribution_name=__optional(library, "distribution_name", None),
            api_id=__optional(library, "api_id", None),
            library_type=__optional(library, "library_type", None),
            release_level=__optional(library, "release_level", None),
            group_id=__optional(library, "group_id", "com.google.cloud"),
            requires_billing=__optional(library, "requires_billing", None),
            cloud_api=__optional(library, "cloud_api", None)
        )
        parsed_libraries.append(new_library)

    parsed_config = GenerationConfig(
        gapic_generator_version=__required(config, "gapic_generator_version"),
        grpc_version=__optional(config, "grpc_version", None),
        protobuf_version=__optional(config, "protobuf_version", None),
        googleapis_commitish=__required(config, "googleapis_commitish"),
        owlbot_cli_image=__required(config, "owlbot_cli_image"),
        synthtool_commitish=__required(config, "synthtool_commitish"),
        destination_path=__optional(config, "destination_path", None),
        libraries=parsed_libraries,
    )

    return parsed_config


def __required(config: Dict, key: str):
    if key not in config:
        raise ValueError(f"required key {key} not found in yaml")
    return config[key]


def __optional(config: Dict, key: str, default: any):
    if key not in config:
        return default
    return config[key]
