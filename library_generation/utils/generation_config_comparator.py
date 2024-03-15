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


class DiffType(Enum):
    GOOGLEAPIS_COMMIT_UPDATE = 1
    GENERATOR_UPDATE = 2
    OWLBOT_CLI_UPDATE = 3
    SYNTHTOOL_UPDATE = 4
    PROTOBUF_UPDATE = 5
    GRPC_UPDATE = 6
    TEMPLATE_EXCLUDES_UPDATE = 7
    LIBRARIES_UPDATE = 8


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
        diff[DiffType.GOOGLEAPIS_COMMIT_UPDATE] = []
    if baseline_config.gapic_generator_version != latest_config.gapic_generator_version:
        diff[DiffType.GENERATOR_UPDATE] = []
    if baseline_config.owlbot_cli_image != latest_config.owlbot_cli_image:
        diff[DiffType.OWLBOT_CLI_UPDATE] = []
    if baseline_config.synthtool_commitish != latest_config.synthtool_commitish:
        diff[DiffType.SYNTHTOOL_UPDATE] = []
    if baseline_config.protobuf_version != latest_config.protobuf_version:
        diff[DiffType.PROTOBUF_UPDATE] = []
    if baseline_config.grpc_version != latest_config.grpc_version:
        diff[DiffType.GRPC_UPDATE] = []
    if baseline_config.template_excludes != latest_config.template_excludes:
        diff[DiffType.TEMPLATE_EXCLUDES_UPDATE] = []

    __compare_libraries(
        diff=diff,
        baseline_libraries=baseline_config.libraries,
        latest_libraries=latest_config.libraries,
    )
    return diff


def __compare_libraries(
    diff: Dict[DiffType, List[str]],
    baseline_libraries: List[LibraryConfig],
    latest_libraries: List[LibraryConfig],
) -> None:
    pass
