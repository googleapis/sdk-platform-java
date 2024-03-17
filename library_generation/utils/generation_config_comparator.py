# Copyright 2024 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
from enum import Enum
from typing import Dict
from typing import List

from library_generation.model.gapic_config import GapicConfig
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.generation_config import from_yaml
from library_generation.model.library_config import LibraryConfig
from library_generation.utilities import get_library_name


class ChangeType(Enum):
    GOOGLEAPIS_COMMIT = 1
    GENERATOR = 2
    OWLBOT_CLI = 3
    SYNTHTOOL = 4
    PROTOBUF = 5
    GRPC = 6
    TEMPLATE_EXCLUDES = 7
    LIBRARIES_ADDITION = 8
    LIBRARIES_REMOVAL = 9
    API_DESCRIPTION = 10
    NAME_PRETTY = 11
    PRODUCT_DOCS = 12
    LIBRARY_TYPE = 13
    RELEASE_LEVEL = 14
    API_ID = 15
    API_REFERENCE = 16
    CODEOWNER_TEAM = 17
    EXCLUDED_DEPENDENCIES = 18
    EXCLUDED_POMS = 19
    CLIENT_DOCS = 20
    ISSUE_TRACKER = 21
    REST_DOCS = 22
    RPC_DOCS = 23
    REQUIRES_BILLING = 24
    EXTRA_VERSIONED_MODULES = 25
    VERSION_ADDITION = 26
    VERSION_REMOVAL = 27


class HashLibrary:
    def __init__(self, hash_value: int, library: LibraryConfig):
        self.hash_value = hash_value
        self.library = library


def compare_config(
    baseline_config: GenerationConfig, latest_config: GenerationConfig
) -> Dict[ChangeType, List[str]]:
    """
    Compare two GenerationConfig object and output a mapping from DiffType
    to a list of library_name of affected libraries.
    All libraries in the latest configuration will be affected if the library
    list is empty.

    :param baseline_config:
    :param latest_config:
    :return:
    """
    diff = {}
    if baseline_config.googleapis_commitish != latest_config.googleapis_commitish:
        diff[ChangeType.GOOGLEAPIS_COMMIT] = []
    if baseline_config.gapic_generator_version != latest_config.gapic_generator_version:
        diff[ChangeType.GENERATOR] = []
    if baseline_config.owlbot_cli_image != latest_config.owlbot_cli_image:
        diff[ChangeType.OWLBOT_CLI] = []
    if baseline_config.synthtool_commitish != latest_config.synthtool_commitish:
        diff[ChangeType.SYNTHTOOL] = []
    if baseline_config.protobuf_version != latest_config.protobuf_version:
        diff[ChangeType.PROTOBUF] = []
    if baseline_config.grpc_version != latest_config.grpc_version:
        diff[ChangeType.GRPC] = []
    if baseline_config.template_excludes != latest_config.template_excludes:
        diff[ChangeType.TEMPLATE_EXCLUDES] = []

    __compare_libraries(
        diff=diff,
        baseline_library_configs=baseline_config.libraries,
        latest_library_configs=latest_config.libraries,
    )
    return diff


def __compare_libraries(
    diff: Dict[ChangeType, List[str]],
    baseline_library_configs: List[LibraryConfig],
    latest_library_configs: List[LibraryConfig],
) -> None:
    baseline_libraries = __convert(baseline_library_configs)
    latest_libraries = __convert(latest_library_configs)
    changed_libraries = []
    # 1st round comparison.
    for library_name, hash_library in baseline_libraries.items():
        # 1. find any library removed from baseline_libraries.
        # a library is removed from baseline_libraries if the library_name
        # is not in latest_libraries.
        if library_name not in latest_libraries:
            if ChangeType.LIBRARIES_REMOVAL not in diff:
                diff[ChangeType.LIBRARIES_REMOVAL] = []
            diff[ChangeType.LIBRARIES_REMOVAL].append(library_name)
        # 2. find any library that exists in both configs but at least one
        # parameter is changed, which means the hash value is different.
        if (
            library_name in latest_libraries
            and hash_library.hash_value != latest_libraries[library_name].hash_value
        ):
            changed_libraries.append(library_name)
    # 2nd round comparison.
    for library_name in latest_libraries:
        # find any library added to latest_libraries.
        # a library is added to latest_libraries if the library_name
        # is not in baseline_libraries.
        if library_name not in baseline_libraries:
            if ChangeType.LIBRARIES_ADDITION not in diff:
                diff[ChangeType.LIBRARIES_ADDITION] = []
            diff[ChangeType.LIBRARIES_ADDITION].append(library_name)
    # 3rd round comparison.
    __compare_changed_libraries(
        diff=diff,
        baseline_libraries=baseline_libraries,
        latest_libraries=latest_libraries,
        changed_libraries=changed_libraries,
    )


def __convert(libraries: List[LibraryConfig]) -> Dict[str, HashLibrary]:
    """
    Convert a list of LibraryConfig objects to a Dict.
    For each library object, the key is the library_name of the object, the
    value is a tuple, which contains the hash value of the object and the object
    itself.
    :param libraries:
    :return:
    """
    return {
        get_library_name(library): HashLibrary(hash(library), library)
        for library in libraries
    }


def __compare_changed_libraries(
    diff: Dict[ChangeType, List[str]],
    baseline_libraries: Dict[str, HashLibrary],
    latest_libraries: Dict[str, HashLibrary],
    changed_libraries: List[str],
) -> None:
    """
    Compare each library with the same library_name to find what parameters are
    changed.

    Some parameters should not change:

    - api_shortname
    - library_name
    - distribution_name
    - group_id
    - cloud_api

    :param diff:
    :param baseline_libraries:
    :param latest_libraries:
    :param changed_libraries:
    :return:
    """
    for library_name in changed_libraries:
        baseline_library = baseline_libraries[library_name].library
        latest_library = latest_libraries[library_name].library
        if (
            baseline_library.api_description
            != latest_libraries[library_name].library.api_description
        ):
            if ChangeType.API_DESCRIPTION not in diff:
                diff[ChangeType.API_DESCRIPTION] = []
            diff[ChangeType.API_DESCRIPTION].append(library_name)
        if (
            baseline_library.name_pretty
            != latest_libraries[library_name].library.name_pretty
        ):
            if ChangeType.NAME_PRETTY not in diff:
                diff[ChangeType.NAME_PRETTY] = []
            diff[ChangeType.NAME_PRETTY].append(library_name)
        if (
            baseline_library.product_documentation
            != latest_library.product_documentation
        ):
            if ChangeType.PRODUCT_DOCS not in diff:
                diff[ChangeType.PRODUCT_DOCS] = []
            diff[ChangeType.PRODUCT_DOCS].append(library_name)

        if baseline_library.library_type != latest_library.library_type:
            if ChangeType.LIBRARY_TYPE not in diff:
                diff[ChangeType.LIBRARY_TYPE] = []
            diff[ChangeType.LIBRARY_TYPE].append(library_name)

        if baseline_library.release_level != latest_library.release_level:
            if ChangeType.RELEASE_LEVEL not in diff:
                diff[ChangeType.RELEASE_LEVEL] = []
            diff[ChangeType.RELEASE_LEVEL].append(library_name)

        if baseline_library.api_id != latest_library.api_id:
            if ChangeType.API_ID not in diff:
                diff[ChangeType.API_ID] = []
            diff[ChangeType.API_ID].append(library_name)

        if baseline_library.api_reference != latest_library.api_reference:
            if ChangeType.API_REFERENCE not in diff:
                diff[ChangeType.API_REFERENCE] = []
            diff[ChangeType.API_REFERENCE].append(library_name)

        if baseline_library.codeowner_team != latest_library.codeowner_team:
            if ChangeType.CODEOWNER_TEAM not in diff:
                diff[ChangeType.CODEOWNER_TEAM] = []
            diff[ChangeType.CODEOWNER_TEAM].append(library_name)
        if (
            baseline_library.excluded_dependencies
            != latest_library.excluded_dependencies
        ):
            if ChangeType.EXCLUDED_DEPENDENCIES not in diff:
                diff[ChangeType.EXCLUDED_DEPENDENCIES] = []
            diff[ChangeType.EXCLUDED_DEPENDENCIES].append(library_name)
        if baseline_library.excluded_poms != latest_library.excluded_poms:
            if ChangeType.EXCLUDED_POMS not in diff:
                diff[ChangeType.EXCLUDED_POMS] = []
            diff[ChangeType.EXCLUDED_POMS].append(library_name)

        if baseline_library.client_documentation != latest_library.client_documentation:
            if ChangeType.CLIENT_DOCS not in diff:
                diff[ChangeType.CLIENT_DOCS] = []
            diff[ChangeType.CLIENT_DOCS].append(library_name)

        if baseline_library.issue_tracker != latest_library.issue_tracker:
            if ChangeType.ISSUE_TRACKER not in diff:
                diff[ChangeType.ISSUE_TRACKER] = []
            diff[ChangeType.ISSUE_TRACKER].append(library_name)

        if baseline_library.rest_documentation != latest_library.rest_documentation:
            if ChangeType.REST_DOCS not in diff:
                diff[ChangeType.REST_DOCS] = []
            diff[ChangeType.REST_DOCS].append(library_name)

        if baseline_library.rpc_documentation != latest_library.rpc_documentation:
            if ChangeType.RPC_DOCS not in diff:
                diff[ChangeType.RPC_DOCS] = []
            diff[ChangeType.RPC_DOCS].append(library_name)

        if baseline_library.requires_billing != latest_library.requires_billing:
            if ChangeType.REQUIRES_BILLING not in diff:
                diff[ChangeType.REQUIRES_BILLING] = []
            diff[ChangeType.REQUIRES_BILLING].append(library_name)

        if (
            baseline_library.extra_versioned_modules
            != latest_library.extra_versioned_modules
        ):
            if ChangeType.EXTRA_VERSIONED_MODULES not in diff:
                diff[ChangeType.EXTRA_VERSIONED_MODULES] = []
            diff[ChangeType.EXTRA_VERSIONED_MODULES].append(library_name)
        # compare gapic_configs
        baseline_gapic_configs = baseline_library.gapic_configs
        latest_gapic_configs = latest_library.gapic_configs
        __compare_gapic_configs(
            diff=diff,
            library_name=library_name,
            baseline_gapic_configs=baseline_gapic_configs,
            latest_gapic_configs=latest_gapic_configs,
        )


def __compare_gapic_configs(
    diff: Dict[ChangeType, List[str]],
    library_name: str,
    baseline_gapic_configs: List[GapicConfig],
    latest_gapic_configs: List[GapicConfig],
) -> None:
    baseline_proto_paths = {config.proto_path for config in baseline_gapic_configs}
    latest_proto_paths = {config.proto_path for config in latest_gapic_configs}
    # 1st round of comparison, find any versioned proto_path is removed
    # from baseline gapic configs.
    for proto_path in baseline_proto_paths:
        if proto_path in latest_proto_paths:
            continue
        if ChangeType.VERSION_REMOVAL not in diff:
            diff[ChangeType.VERSION_REMOVAL] = []
        diff[ChangeType.VERSION_REMOVAL].append(library_name)
    # 2nd round of comparison, find any versioned proto_path is added
    # to latest gapic configs.
    for proto_path in latest_proto_paths:
        if proto_path in baseline_proto_paths:
            continue
        if ChangeType.VERSION_ADDITION not in diff:
            diff[ChangeType.VERSION_ADDITION] = []
        diff[ChangeType.VERSION_ADDITION].append(library_name)
