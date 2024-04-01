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
import os
import shutil
from enum import Enum
from typing import Optional
from git import Commit, Repo
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.library_config import LibraryConfig
from library_generation.utilities import sh_util
from library_generation.utils.proto_path_utils import find_versioned_proto_path


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
    def __init__(self, commit: Commit, libraries: set[str]):
        self.commit = commit
        self.libraries = libraries


class ConfigChange:
    ALL_LIBRARIES_CHANGED = None

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
        Returns a unique, sorted list of library name of changed libraries.
        None if there is a repository level change, which means all libraries
        in the latest_config will be generated.
        :return: library names of change libraries.
        """
        if ChangeType.REPO_LEVEL_CHANGE in self.change_to_libraries:
            return ConfigChange.ALL_LIBRARIES_CHANGED
        library_names = set()
        for change_type, library_changes in self.change_to_libraries.items():
            if change_type == ChangeType.GOOGLEAPIS_COMMIT:
                library_names.update(self.__get_library_names_from_qualified_commits())
            else:
                library_names.update(
                    [library_change.library_name for library_change in library_changes]
                )
        return sorted(list(library_names))

    def get_qualified_commits(
        self,
        repo_url: str = "https://github.com/googleapis/googleapis.git",
    ) -> list[QualifiedCommit]:
        """
        Returns qualified commits from configuration change.

        A qualified commit is a commit that changes at least one file (excluding
        BUILD.bazel) within a versioned proto path in the given proto_paths.
        :param repo_url: the repository contains the commit history.
        :return: QualifiedCommit objects.
        """
        tmp_dir = sh_util("get_output_folder")
        shutil.rmtree(tmp_dir, ignore_errors=True)
        os.mkdir(tmp_dir)
        # we only need commit history, thus shadow clone is enough.
        repo = Repo.clone_from(url=repo_url, to_path=tmp_dir, filter=["blob:none"])
        commit = repo.commit(self.latest_config.googleapis_commitish)
        proto_paths = self.latest_config.get_proto_path_to_library_name()
        qualified_commits = []
        while str(commit.hexsha) != self.baseline_config.googleapis_commitish:
            qualified_commit = ConfigChange.__create_qualified_commit(
                proto_paths=proto_paths, commit=commit
            )
            if qualified_commit is not None:
                qualified_commits.append(qualified_commit)
            commit_parents = commit.parents
            if len(commit_parents) == 0:
                break
            commit = commit_parents[0]
        shutil.rmtree(tmp_dir, ignore_errors=True)
        return qualified_commits

    def __get_library_names_from_qualified_commits(self) -> list[str]:
        qualified_commits = self.get_qualified_commits()
        library_names = []
        for qualified_commit in qualified_commits:
            library_names.extend(qualified_commit.libraries)
        return library_names

    @staticmethod
    def __create_qualified_commit(
        proto_paths: dict[str, str], commit: Commit
    ) -> Optional[QualifiedCommit]:
        """
        Returns a qualified commit from the given Commit object; otherwise None.

        :param proto_paths: a mapping from versioned proto_path to library_name
        :param commit: a GitHub commit object.
        :return: qualified commits.
        """
        libraries = set()
        for file in commit.stats.files.keys():
            if file.endswith("BUILD.bazel"):
                continue
            versioned_proto_path = find_versioned_proto_path(file)
            if versioned_proto_path in proto_paths:
                # Even though a commit usually only changes one
                # library, we don't want to miss generating a
                # library because the commit may change multiple
                # libraries.
                libraries.add(proto_paths[versioned_proto_path])
        if len(libraries) == 0:
            return None
        return QualifiedCommit(commit=commit, libraries=libraries)
