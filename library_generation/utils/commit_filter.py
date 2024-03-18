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
import os
import shutil

from typing import List, Optional, Dict

from git import Commit, Repo
from library_generation.model.generation_config import GenerationConfig
from library_generation.utils.proto_path_utils import (
    get_proto_paths,
    find_versioned_proto_path,
)


class QualifiedCommit:
    def __init__(self, commit: Commit, libraries: List[str]):
        self.commit = commit
        self.libraries = libraries


def get_qualified_commit(
    baseline_config: GenerationConfig,
    latest_config: GenerationConfig,
    repo_url: str = "https://github.com/googleapis/googleapis.git",
) -> List[QualifiedCommit]:
    tmp_dir = "/tmp/repo"
    shutil.rmtree(tmp_dir, ignore_errors=True)
    os.mkdir(tmp_dir)
    repo = Repo.clone_from(repo_url, tmp_dir)
    commit = repo.commit(latest_config.googleapis_commitish)
    proto_paths = get_proto_paths(latest_config)
    qualified_commits = []
    while str(commit.hexsha) != baseline_config.googleapis_commitish:
        qualified_commit = __create_qualified_commit(
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


def __create_qualified_commit(
    proto_paths: Dict[str, str], commit: Commit
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
