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
from typing import Optional
from library_generation.model.library_config import LibraryConfig
from library_generation.model.gapic_config import GapicConfig

REPO_LEVEL_PARAMETER = "Repo level parameter"
LIBRARY_LEVEL_PARAMETER = "Library level parameter"
GAPIC_LEVEL_PARAMETER = "GAPIC level parameter"
COMMON_PROTOS = "common-protos"


class GenerationConfig:
    """
    Class that represents the root of a generation_config.yaml
    """

    def __init__(
        self,
        gapic_generator_version: str,
        googleapis_commitish: str,
        template_excludes: list[str],
        libraries: list[LibraryConfig],
        libraries_bom_version: Optional[str] = None,
        grpc_version: Optional[str] = None,
        protoc_version: Optional[str] = None,
    ):
        self.gapic_generator_version = gapic_generator_version
        self.googleapis_commitish = googleapis_commitish
        self.libraris_bom_version = libraries_bom_version
        self.template_excludes = template_excludes
        self.libraries = libraries
        self.grpc_version = grpc_version
        self.protoc_version = protoc_version
        # explicit set to None so that we can compute the
        # value in getter.
        self.__contains_common_protos = None
        self.__validate()

    def get_proto_path_to_library_name(self) -> dict[str, str]:
        """
        Get versioned proto_path to library_name mapping from configuration.

        :return: versioned proto_path to library_name mapping
        """
        paths = {}
        for library in self.libraries:
            for gapic_config in library.gapic_configs:
                paths[gapic_config.proto_path] = library.get_library_name()
        return paths

    def is_monorepo(self) -> bool:
        return len(self.libraries) > 1

    def contains_common_protos(self) -> bool:
        if self.__contains_common_protos is None:
            self.__contains_common_protos = False
            for library in self.libraries:
                if library.get_library_name() == COMMON_PROTOS:
                    self.__contains_common_protos = True
        return self.__contains_common_protos

    def __validate(self) -> None:
        seen_library_names = dict()
        for library in self.libraries:
            library_name = library.get_library_name()
            if library_name in seen_library_names:
                raise ValueError(
                    f"Both {library.name_pretty} and "
                    f"{seen_library_names.get(library_name)} have the same "
                    f"library name: {library_name}, please update one of the "
                    f"library to have a different library name."
                )
            seen_library_names[library_name] = library.name_pretty


def from_yaml(path_to_yaml: str) -> GenerationConfig:
    """
    Parses a yaml located in path_to_yaml.
    :param path_to_yaml: the path to the configuration file
    :return the parsed configuration represented by the "model" classes
    """
    with open(path_to_yaml, "r") as file_stream:
        config = yaml.safe_load(file_stream)

    libraries = __required(config, "libraries", REPO_LEVEL_PARAMETER)
    if not libraries:
        raise ValueError(f"Library is None in {path_to_yaml}.")

    parsed_libraries = list()
    for library in libraries:
        gapics = __required(library, "GAPICs")

        parsed_gapics = list()
        if not gapics:
            raise ValueError(f"GAPICs is None in {library}.")
        for gapic in gapics:
            proto_path = __required(gapic, "proto_path", GAPIC_LEVEL_PARAMETER)
            new_gapic = GapicConfig(proto_path)
            parsed_gapics.append(new_gapic)

        new_library = LibraryConfig(
            api_shortname=__required(library, "api_shortname"),
            api_description=__required(library, "api_description"),
            name_pretty=__required(library, "name_pretty"),
            product_documentation=__required(library, "product_documentation"),
            gapic_configs=parsed_gapics,
            library_type=__optional(library, "library_type", "GAPIC_AUTO"),
            release_level=__optional(library, "release_level", "preview"),
            api_id=__optional(library, "api_id", None),
            api_reference=__optional(library, "api_reference", None),
            codeowner_team=__optional(library, "codeowner_team", None),
            excluded_poms=__optional(library, "excluded_poms", None),
            excluded_dependencies=__optional(library, "excluded_dependencies", None),
            client_documentation=__optional(library, "client_documentation", None),
            distribution_name=__optional(library, "distribution_name", None),
            googleapis_commitish=__optional(library, "googleapis_commitish", None),
            group_id=__optional(library, "group_id", "com.google.cloud"),
            issue_tracker=__optional(library, "issue_tracker", None),
            library_name=__optional(library, "library_name", None),
            rest_documentation=__optional(library, "rest_documentation", None),
            rpc_documentation=__optional(library, "rpc_documentation", None),
            cloud_api=__optional(library, "cloud_api", True),
            requires_billing=__optional(library, "requires_billing", True),
            extra_versioned_modules=__optional(
                library, "extra_versioned_modules", None
            ),
        )
        parsed_libraries.append(new_library)

    parsed_config = GenerationConfig(
        gapic_generator_version=__required(
            config, "gapic_generator_version", REPO_LEVEL_PARAMETER
        ),
        googleapis_commitish=__required(
            config, "googleapis_commitish", REPO_LEVEL_PARAMETER
        ),
        template_excludes=__required(config, "template_excludes", REPO_LEVEL_PARAMETER),
        grpc_version=__optional(config, "grpc_version", None),
        protoc_version=__optional(config, "protoc_version", None),
        libraries_bom_version=__optional(config, "libraries_bom_version", None),
        libraries=parsed_libraries,
    )

    return parsed_config


def __required(config: dict, key: str, level: str = LIBRARY_LEVEL_PARAMETER):
    if key not in config:
        message = (
            f"{level}, {key}, is not found in {config} in yaml."
            if level != REPO_LEVEL_PARAMETER
            else f"{level}, {key}, is not found in yaml."
        )
        raise ValueError(message)
    return config[key]


def __optional(config: dict, key: str, default: any):
    if key not in config:
        return default
    return config[key]
