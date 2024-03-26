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
from typing import Optional
from git import Commit
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


class LibraryChange:
    def __init__(self, changed_param: str, latest_value: str, library_name: str = ""):
        self.changed_param = changed_param
        self.latest_value = latest_value
        self.library_name = library_name


class QualifiedCommit:
    def __init__(self, commit: Commit, libraries: list[str]):
        self.commit = commit
        self.libraries = libraries


class ConfigChange:
    def __init__(
        self,
        change_to_libraries: dict[ChangeType, list[LibraryChange]],
        baseline_config: GenerationConfig,
        latest_config: GenerationConfig,
    ):
        self.change_to_libraries = change_to_libraries
        self.baseline_config = baseline_config
        self.latest_config = latest_config

    def get_changed_libraries(self) -> Optional[list[str]]:
        """
        Returns library name of changed libraries.
        None if there is a repository level change.
        :return: library names of change libraries.
        """
        if ChangeType.REPO_LEVEL_CHANGE in self.change_to_libraries:
            return None
        library_names = []
        for change_type, library_changes in self.change_to_libraries.items():
            if change_type == ChangeType.GOOGLEAPIS_COMMIT:
                library_names.extend(
                    self.__get_changed_libraries_in_googleapis_commit()
                )
            else:
                library_names.extend(
                    [library_change.library_name for library_change in library_changes]
                )
        return library_names

    def get_qualified_commits(self) -> list[QualifiedCommit]:
        pass

    def __get_changed_libraries_in_googleapis_commit(self) -> list[str]:
        pass

    def __create_qualified_commit(
        self, proto_paths: dict[str, str], commit: Commit
    ) -> Optional[QualifiedCommit]:
        libraries = []
        for file in commit.stats.files.keys():
            if file.endswith("BUILD.bazel"):
                continue
            versioned_proto_path = find_versioned_proto_path(file)
            if versioned_proto_path in proto_paths:
                # Even though a commit usually only changes one
                # library, we don't want to miss generating a
                # library because the commit may change multiple
                # libraries.
                # Therefore, we keep a list of library_name to
                # avoid missing changed libraries.
                libraries.append(proto_paths[versioned_proto_path])
        if len(libraries) == 0:
            return None
        return QualifiedCommit(commit=commit, libraries=libraries)
