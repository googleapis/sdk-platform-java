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
from typing import List

from library_generation.model.generation_config import from_yaml, GenerationConfig
from library_generation.utils.commit_filter import get_qualified_commit
from library_generation.utils.generation_config_comparator import (
    compare_config,
    ChangeType,
)


def generate_pr_body(
    baseline_generation_config_yaml: str,
    latest_generation_config_yaml: str,
) -> List[str]:
    baseline_config = from_yaml(baseline_generation_config_yaml)
    latest_config = from_yaml(latest_generation_config_yaml)
    compare_result = compare_config(
        baseline_config=baseline_config, latest_config=latest_config
    )

    return __get_changed_libraries(
        compare_result=compare_result,
        baseline_config=baseline_config,
        latest_config=latest_config,
    )


def __get_changed_libraries(
    compare_result: dict[ChangeType, list[str]],
    baseline_config: GenerationConfig,
    latest_config: GenerationConfig,
) -> List[str]:
    affected_libraries = []
    for changeType in compare_result:
        if __is_full_generation_type(changeType):
            return []
        library_names = compare_result[changeType]
        if changeType == ChangeType.GOOGLEAPIS_COMMIT:
            library_names = __get_library_name_from_qualified_commits(
                baseline_config=baseline_config, latest_config=latest_config
            )
        affected_libraries.extend(library_names)
    return affected_libraries


def __is_full_generation_type(change_type: ChangeType) -> bool:
    return (
        change_type == ChangeType.GENERATOR
        or change_type == ChangeType.OWLBOT_CLI
        or change_type == ChangeType.SYNTHTOOL
        or change_type == ChangeType.PROTOBUF
        or change_type == ChangeType.GRPC
        or change_type == ChangeType.TEMPLATE_EXCLUDES
    )


def __get_library_name_from_qualified_commits(
    baseline_config: GenerationConfig,
    latest_config: GenerationConfig,
) -> List[str]:
    qualified_commits = get_qualified_commit(
        baseline_config=baseline_config, latest_config=latest_config
    )
    library_names = []
    for qualified_commit in qualified_commits:
        library_names.extend(qualified_commit.libraries)
    return library_names
