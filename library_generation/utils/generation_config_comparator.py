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
from collections import defaultdict
from enum import Enum
from typing import Dict
from typing import List

from library_generation.model.gapic_config import GapicConfig
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.library_config import LibraryConfig


class ChangeType(Enum):
    GOOGLEAPIS_COMMIT = 1
    REPO_LEVEL_CHANGE = 2
    LIBRARIES_ADDITION = 3
    # As of Mar. 2024, we decide not to produce this type of change because we
    # still need to manually remove the libray.
    # LIBRARIES_REMOVAL = 4
    LIBRARY_LEVEL_CHANGE = 5
    GAPIC_ADDITION = 6
    # As of Mar. 2024, we decide not to produce this type of change because we
    # still need to manually remove the libray.
    # GAPIC_REMOVAL = 7


class HashLibrary:
    """
    Data class to group a LibraryConfig object and its hash value together.
    """

    def __init__(self, hash_value: int, library: LibraryConfig):
        self.hash_value = hash_value
        self.library = library


class ConfigChange:
    def __init__(self, changed_param: str, latest_value: str, library_name: str = ""):
        self.changed_param = changed_param
        self.latest_value = latest_value
        self.library_name = library_name


def compare_config(
    baseline_config: GenerationConfig, latest_config: GenerationConfig
) -> Dict[ChangeType, list[ConfigChange]]:
    """
    Compare two GenerationConfig object and output a mapping from ConfigChange
    to a list of ConfigChange objects.
    All libraries in the latest configuration will be affected if one of the
    repository level parameters is changed.

    :param baseline_config: the baseline GenerationConfig object
    :param latest_config: the latest GenerationConfig object
    :return: a mapping from ConfigChange to a list of ConfigChange objects.
    """
    diff = defaultdict(list[ConfigChange])
    if baseline_config.googleapis_commitish != latest_config.googleapis_commitish:
        diff[ChangeType.GOOGLEAPIS_COMMIT] = []
    if baseline_config.gapic_generator_version != latest_config.gapic_generator_version:
        config_change = ConfigChange(
            changed_param="GAPIC generator version",
            latest_value=latest_config.gapic_generator_version,
        )
        diff[ChangeType.REPO_LEVEL_CHANGE].append(config_change)
    if baseline_config.owlbot_cli_image != latest_config.owlbot_cli_image:
        config_change = ConfigChange(
            changed_param="OwlBot cli commit",
            latest_value=latest_config.owlbot_cli_image,
        )
        diff[ChangeType.REPO_LEVEL_CHANGE].append(config_change)
    if baseline_config.synthtool_commitish != latest_config.synthtool_commitish:
        config_change = ConfigChange(
            changed_param="Synthtool commit",
            latest_value=latest_config.synthtool_commitish,
        )
        diff[ChangeType.REPO_LEVEL_CHANGE].append(config_change)
    if baseline_config.protobuf_version != latest_config.protobuf_version:
        config_change = ConfigChange(
            changed_param="Protobuf version",
            latest_value=latest_config.protobuf_version,
        )
        diff[ChangeType.REPO_LEVEL_CHANGE].append(config_change)
    if baseline_config.grpc_version != latest_config.grpc_version:
        config_change = ConfigChange(
            changed_param="Grpc version", latest_value=latest_config.grpc_version
        )
        diff[ChangeType.REPO_LEVEL_CHANGE].append(config_change)
    if baseline_config.template_excludes != latest_config.template_excludes:
        config_change = ConfigChange(
            changed_param="Template excludes",
            latest_value=str(latest_config.template_excludes),
        )
        diff[ChangeType.REPO_LEVEL_CHANGE].append(config_change)

    __compare_libraries(
        diff=diff,
        baseline_library_configs=baseline_config.libraries,
        latest_library_configs=latest_config.libraries,
    )
    return diff


def __compare_libraries(
    diff: Dict[ChangeType, list[ConfigChange]],
    baseline_library_configs: List[LibraryConfig],
    latest_library_configs: List[LibraryConfig],
) -> None:
    """
    Compare two lists of LibraryConfig and put the difference into a
    given Dict.

    :param diff: a mapping from ConfigChange to a list of ConfigChange objects.
    :param baseline_library_configs: a list of LibraryConfig object.
    :param latest_library_configs: a list of LibraryConfig object.
    """
    baseline_libraries = __convert_to_hashed_library_dict(baseline_library_configs)
    latest_libraries = __convert_to_hashed_library_dict(latest_library_configs)
    changed_libraries = []
    # 1st round comparison.
    for library_name, hash_library in baseline_libraries.items():
        # 1. find any library removed from baseline_libraries.
        # a library is removed from baseline_libraries if the library_name
        # is not in latest_libraries.
        # please see the reason of comment out these lines of code in the
        # comment of ChangeType.LIBRARIES_REMOVAL.
        # if library_name not in latest_libraries:
        #     config_change = ConfigChange(
        #         changed_param="", latest_value="", library_name=library_name
        #     )
        #     diff[ChangeType.LIBRARIES_REMOVAL].append(config_change)

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
            config_change = ConfigChange(
                changed_param="", latest_value="", library_name=library_name
            )
            diff[ChangeType.LIBRARIES_ADDITION].append(config_change)
    # 3rd round comparison.
    __compare_changed_libraries(
        diff=diff,
        baseline_libraries=baseline_libraries,
        latest_libraries=latest_libraries,
        changed_libraries=changed_libraries,
    )


def __convert_to_hashed_library_dict(
    libraries: List[LibraryConfig],
) -> Dict[str, HashLibrary]:
    """
    Convert a list of LibraryConfig objects to a Dict.
    For each library object, the key is the library_name of the object, the
    value is a HashLibrary object.

    :param libraries: a list of LibraryConfig object.
    :return: a mapping from library_name to HashLibrary object.
    """
    return {
        library.get_library_name(): HashLibrary(hash(library), library)
        for library in libraries
    }


def __compare_changed_libraries(
    diff: Dict[ChangeType, list[ConfigChange]],
    baseline_libraries: Dict[str, HashLibrary],
    latest_libraries: Dict[str, HashLibrary],
    changed_libraries: List[str],
) -> None:
    """
    Compare each library with the same library_name to find what parameters are
    changed.

    :param diff: a mapping from ConfigChange to a list of ConfigChange objects.
    :param baseline_libraries: a mapping from library_name to HashLibrary
    object.
    :param latest_libraries: a mapping from library_name to HashLibrary object.
    :param changed_libraries: a list of library_name of changed libraries.
    """
    for library_name in changed_libraries:
        baseline_library = baseline_libraries[library_name].library
        latest_library = latest_libraries[library_name].library
        if baseline_library.api_description != latest_library.api_description:
            config_change = ConfigChange(
                changed_param="api description",
                latest_value=latest_library.api_description,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.name_pretty != latest_library.name_pretty:
            config_change = ConfigChange(
                changed_param="name pretty",
                latest_value=latest_library.name_pretty,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if (
            baseline_library.product_documentation
            != latest_library.product_documentation
        ):
            config_change = ConfigChange(
                changed_param="product documentation",
                latest_value=latest_library.product_documentation,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.library_type != latest_library.library_type:
            config_change = ConfigChange(
                changed_param="library type",
                latest_value=latest_library.library_type,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.release_level != latest_library.release_level:
            config_change = ConfigChange(
                changed_param="release level",
                latest_value=latest_library.release_level,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.api_id != latest_library.api_id:
            config_change = ConfigChange(
                changed_param="api id",
                latest_value=latest_library.api_id,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.api_reference != latest_library.api_reference:
            config_change = ConfigChange(
                changed_param="api reference",
                latest_value=latest_library.api_reference,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.codeowner_team != latest_library.codeowner_team:
            config_change = ConfigChange(
                changed_param="code owner team",
                latest_value=latest_library.codeowner_team,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if (
            baseline_library.excluded_dependencies
            != latest_library.excluded_dependencies
        ):
            config_change = ConfigChange(
                changed_param="excluded dependencies",
                latest_value=latest_library.excluded_dependencies,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.excluded_poms != latest_library.excluded_poms:
            config_change = ConfigChange(
                changed_param="excluded poms",
                latest_value=latest_library.excluded_poms,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.client_documentation != latest_library.client_documentation:
            config_change = ConfigChange(
                changed_param="client documentation",
                latest_value=latest_library.client_documentation,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.distribution_name != latest_library.distribution_name:
            config_change = ConfigChange(
                changed_param="distribution name",
                latest_value=latest_library.distribution_name,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.group_id != latest_library.group_id:
            config_change = ConfigChange(
                changed_param="group id",
                latest_value=latest_library.group_id,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.issue_tracker != latest_library.issue_tracker:
            config_change = ConfigChange(
                changed_param="issue tracker",
                latest_value=latest_library.issue_tracker,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.rest_documentation != latest_library.rest_documentation:
            config_change = ConfigChange(
                changed_param="rest documentation",
                latest_value=latest_library.rest_documentation,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.rpc_documentation != latest_library.rpc_documentation:
            config_change = ConfigChange(
                changed_param="rpc documentation",
                latest_value=latest_library.rpc_documentation,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.cloud_api != latest_library.cloud_api:
            config_change = ConfigChange(
                changed_param="cloud api",
                latest_value=str(latest_library.cloud_api),
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
        if baseline_library.requires_billing != latest_library.requires_billing:
            config_change = ConfigChange(
                changed_param="requires billing",
                latest_value=str(latest_library.requires_billing),
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)

        if (
            baseline_library.extra_versioned_modules
            != latest_library.extra_versioned_modules
        ):
            config_change = ConfigChange(
                changed_param="extra versioned modules",
                latest_value=latest_library.extra_versioned_modules,
                library_name=library_name,
            )
            diff[ChangeType.LIBRARY_LEVEL_CHANGE].append(config_change)
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
    diff: Dict[ChangeType, list[ConfigChange]],
    library_name: str,
    baseline_gapic_configs: List[GapicConfig],
    latest_gapic_configs: List[GapicConfig],
) -> None:
    baseline_proto_paths = {config.proto_path for config in baseline_gapic_configs}
    latest_proto_paths = {config.proto_path for config in latest_gapic_configs}
    # 1st round of comparison, find any versioned proto_path is removed
    # from baseline gapic configs.
    # please see the reason of comment out these lines of code in the
    # comment of ChangeType.GAPIC_REMOVAL.
    # for proto_path in baseline_proto_paths:
    #     if proto_path in latest_proto_paths:
    #         continue
    #     config_change = ConfigChange(
    #         changed_param="", latest_value=proto_path, library_name=library_name
    #     )
    #     diff[ChangeType.GAPIC_REMOVAL].append(config_change)

    # 2nd round of comparison, find any versioned proto_path is added
    # to latest gapic configs.
    for proto_path in latest_proto_paths:
        if proto_path in baseline_proto_paths:
            continue
        config_change = ConfigChange(
            changed_param="", latest_value=proto_path, library_name=library_name
        )
        diff[ChangeType.GAPIC_ADDITION].append(config_change)
