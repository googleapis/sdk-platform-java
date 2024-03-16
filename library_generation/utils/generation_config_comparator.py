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
from library_generation.model.generation_config import from_yaml
from library_generation.model.library_config import LibraryConfig
from library_generation.utilities import get_library_name


class DiffType(Enum):
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


def compare_config(
    path_to_baseline_config_yaml: str, path_to_latest_config_yaml: str
) -> Dict[DiffType, List[str]]:
    """
    Compare two GenerationConfig object and output a mapping from DiffType
    to a list of library_name of affected libraries.
    All libraries in the latest configuration will be affected if the library
    list is empty.

    :param path_to_baseline_config_yaml: the path to the baseline configuration
    file.
    :param path_to_latest_config_yaml: the path to the latest configuration
    file.
    :return:
    """
    diff = {}
    baseline_config = from_yaml(path_to_baseline_config_yaml)
    latest_config = from_yaml(path_to_latest_config_yaml)
    if baseline_config.googleapis_commitish != latest_config.googleapis_commitish:
        diff[DiffType.GOOGLEAPIS_COMMIT] = []
    if baseline_config.gapic_generator_version != latest_config.gapic_generator_version:
        diff[DiffType.GENERATOR] = []
    if baseline_config.owlbot_cli_image != latest_config.owlbot_cli_image:
        diff[DiffType.OWLBOT_CLI] = []
    if baseline_config.synthtool_commitish != latest_config.synthtool_commitish:
        diff[DiffType.SYNTHTOOL] = []
    if baseline_config.protobuf_version != latest_config.protobuf_version:
        diff[DiffType.PROTOBUF] = []
    if baseline_config.grpc_version != latest_config.grpc_version:
        diff[DiffType.GRPC] = []
    if baseline_config.template_excludes != latest_config.template_excludes:
        diff[DiffType.TEMPLATE_EXCLUDES] = []

    compare_libraries(
        diff=diff,
        baseline_library_configs=baseline_config.libraries,
        latest_library_configs=latest_config.libraries,
    )
    return diff


def compare_libraries(
    diff: Dict[DiffType, List[str]],
    baseline_library_configs: List[LibraryConfig],
    latest_library_configs: List[LibraryConfig],
) -> None:
    baseline_libraries = convert(baseline_library_configs)
    latest_libraries = convert(latest_library_configs)
    changed_libraries = []
    # 1st round comparison.
    for library_name, (hash_value, _) in baseline_libraries:
        # 1. find any library removed from baseline_libraries.
        # a library is removed from baseline_libraries if the library_name
        # is not in latest_libraries.
        if library_name not in latest_libraries:
            if DiffType.LIBRARIES_REMOVAL not in diff:
                diff[DiffType.LIBRARIES_REMOVAL] = []
            diff[DiffType.LIBRARIES_REMOVAL].append(library_name)
        # 2. find any library that exists in both configs but at least one
        # parameter is changed, which means the hash value is different.
        if (
            library_name in latest_libraries
            and hash_value != latest_libraries[library_name][0]
        ):
            changed_libraries.append(library_name)
    # 2nd round comparison.
    for library_name, (hash_value, _) in latest_libraries:
        # find any library added to latest_libraries.
        # a library is added to latest_libraries if the library_name
        # is not in baseline_libraries.
        if library_name not in baseline_libraries:
            if DiffType.LIBRARIES_ADDITION not in diff:
                diff[DiffType.LIBRARIES_ADDITION] = []
            diff[DiffType.LIBRARIES_ADDITION].append(library_name)
    # 3rd round comparison.
    compare_changed_libraries(
        diff=diff,
        baseline_libraries=baseline_libraries,
        latest_libraries=latest_libraries,
        changed_libraries=changed_libraries,
    )


def convert(libraries: List[LibraryConfig]) -> Dict[str, (int, LibraryConfig)]:
    """
    Convert a list of LibraryConfig objects to a Dict.
    For each library object, the key is the library_name of the object, the
    value is a tuple, which contains the hash value of the object and the object
    itself.
    :param libraries:
    :return:
    """
    return {
        get_library_name(library): (hash(library), library) for library in libraries
    }


def compare_changed_libraries(
    diff: Dict[DiffType, List[str]],
    baseline_libraries: Dict[str, (int, LibraryConfig)],
    latest_libraries: Dict[str, (int, LibraryConfig)],
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
        if (
            baseline_libraries[library_name][1].api_description
            != latest_libraries[library_name][1].api_description
        ):
            if DiffType.API_DESCRIPTION not in diff:
                diff[DiffType.API_DESCRIPTION] = []
            diff[DiffType.API_DESCRIPTION].append(library_name)
        if (
            baseline_libraries[library_name][1].name_pretty
            != latest_libraries[library_name][1].name_pretty
        ):
            if DiffType.NAME_PRETTY not in diff:
                diff[DiffType.NAME_PRETTY] = []
            diff[DiffType.NAME_PRETTY].append(library_name)
        if (
            baseline_libraries[library_name][1].product_documentation
            != latest_libraries[library_name][1].product_documentation
        ):
            if DiffType.PRODUCT_DOCS not in diff:
                diff[DiffType.PRODUCT_DOCS] = []
            diff[DiffType.PRODUCT_DOCS].append(library_name)

        if (
            baseline_libraries[library_name][1].library_type
            != latest_libraries[library_name][1].library_type
        ):
            if DiffType.LIBRARY_TYPE not in diff:
                diff[DiffType.LIBRARY_TYPE] = []
            diff[DiffType.LIBRARY_TYPE].append(library_name)

        if (
            baseline_libraries[library_name][1].release_level
            != latest_libraries[library_name][1].release_level
        ):
            if DiffType.RELEASE_LEVEL not in diff:
                diff[DiffType.RELEASE_LEVEL] = []
            diff[DiffType.RELEASE_LEVEL].append(library_name)

        if (
            baseline_libraries[library_name][1].api_id
            != latest_libraries[library_name][1].api_id
        ):
            if DiffType.API_ID not in diff:
                diff[DiffType.API_ID] = []
            diff[DiffType.API_ID].append(library_name)

        if (
            baseline_libraries[library_name][1].api_reference
            != latest_libraries[library_name][1].api_reference
        ):
            if DiffType.API_REFERENCE not in diff:
                diff[DiffType.API_REFERENCE] = []
            diff[DiffType.API_REFERENCE].append(library_name)

        if (
            baseline_libraries[library_name][1].codeowner_team
            != latest_libraries[library_name][1].codeowner_team
        ):
            if DiffType.CODEOWNER_TEAM not in diff:
                diff[DiffType.CODEOWNER_TEAM] = []
            diff[DiffType.CODEOWNER_TEAM].append(library_name)
        if (
            baseline_libraries[library_name][1].excluded_dependencies
            != latest_libraries[library_name][1].excluded_dependencies
        ):
            if DiffType.EXCLUDED_DEPENDENCIES not in diff:
                diff[DiffType.EXCLUDED_DEPENDENCIES] = []
            diff[DiffType.EXCLUDED_DEPENDENCIES].append(library_name)
        if (
            baseline_libraries[library_name][1].excluded_poms
            != latest_libraries[library_name][1].excluded_poms
        ):
            if DiffType.EXCLUDED_POMS not in diff:
                diff[DiffType.EXCLUDED_POMS] = []
            diff[DiffType.EXCLUDED_POMS].append(library_name)

        if (
            baseline_libraries[library_name][1].client_documentation
            != latest_libraries[library_name][1].client_documentation
        ):
            if DiffType.CLIENT_DOCS not in diff:
                diff[DiffType.CLIENT_DOCS] = []
            diff[DiffType.CLIENT_DOCS].append(library_name)

        if (
            baseline_libraries[library_name][1].issue_tracker
            != latest_libraries[library_name][1].issue_tracker
        ):
            if DiffType.ISSUE_TRACKER not in diff:
                diff[DiffType.ISSUE_TRACKER] = []
            diff[DiffType.ISSUE_TRACKER].append(library_name)

        if (
            baseline_libraries[library_name][1].rest_documentation
            != latest_libraries[library_name][1].rest_documentation
        ):
            if DiffType.REST_DOCS not in diff:
                diff[DiffType.REST_DOCS] = []
            diff[DiffType.REST_DOCS].append(library_name)

        if (
            baseline_libraries[library_name][1].rpc_documentation
            != latest_libraries[library_name][1].rpc_documentation
        ):
            if DiffType.RPC_DOCS not in diff:
                diff[DiffType.RPC_DOCS] = []
            diff[DiffType.RPC_DOCS].append(library_name)

        if (
            baseline_libraries[library_name][1].requires_billing
            != latest_libraries[library_name][1].requires_billing
        ):
            if DiffType.REQUIRES_BILLING not in diff:
                diff[DiffType.REQUIRES_BILLING] = []
            diff[DiffType.REQUIRES_BILLING].append(library_name)

        if (
            baseline_libraries[library_name][1].extra_versioned_modules
            != latest_libraries[library_name][1].extra_versioned_modules
        ):
            if DiffType.EXTRA_VERSIONED_MODULES not in diff:
                diff[DiffType.EXTRA_VERSIONED_MODULES] = []
            diff[DiffType.EXTRA_VERSIONED_MODULES].append(library_name)
        # compare gapic_configs
