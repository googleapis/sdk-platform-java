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
from typing import List

import click
from git import Commit, Repo
from library_generation.model.generation_config import from_yaml
from library_generation.utilities import get_file_paths


@click.group(invoke_without_command=False)
@click.pass_context
@click.version_option(message="%(version)s")
def main(ctx):
    pass


@main.command()
@click.option(
    "--generation-config-yaml",
    required=True,
    type=str,
    help="""
    Path to generation_config.yaml that contains the metadata about
    library generation.
    """,
)
@click.option(
    "--baseline-commit",
    required=True,
    type=str,
    help="""
    The baseline commit from which the commit message is considered.
    This commit should be an ancestor of googleapis commit in configuration.
    """,
)
@click.option(
    "--repo-url",
    type=str,
    default="https://github.com/googleapis/googleapis.git",
    show_default=True,
    help="""
    GitHub repository URL.
    """,
)
def generate(
    generation_config_yaml: str,
    repo_url: str,
    baseline_commit: str,
) -> str:
    return generate_pr_descriptions(
        generation_config_yaml=generation_config_yaml,
        repo_url=repo_url,
        baseline_commit=baseline_commit,
    )


def generate_pr_descriptions(
    generation_config_yaml: str,
    repo_url: str,
    baseline_commit: str,
) -> str:
    config = from_yaml(generation_config_yaml)
    paths = get_file_paths(config)
    return __get_commit_messages(
        repo_url=repo_url,
        latest_commit=config.googleapis_commitish,
        baseline_commit=baseline_commit,
        paths=paths,
    )


def __get_commit_messages(
    repo_url: str, latest_commit: str, baseline_commit: str, paths: set[str]
) -> str:
    """
    Combine commit messages of a repository from latest_commit to
    baseline_commit. Only commits which change files in a pre-defined
    file paths will be considered.
    Note that baseline_commit should be an ancestor of latest_commit.

    :param repo_url: the url of the repository.
    :param latest_commit: the newest commit to be considered in
    selecting commit message.
    :param baseline_commit: the oldest commit to be considered in
    selecting commit message. This commit should be an ancestor of
    :param paths: a set of file paths
    :return: commit messages.
    """
    tmp_dir = "/tmp/repo"
    shutil.rmtree(tmp_dir, ignore_errors=True)
    os.mkdir(tmp_dir)
    repo = Repo.clone_from(repo_url, tmp_dir)
    commit = repo.commit(latest_commit)
    qualified_commits = []
    while str(commit.hexsha) != baseline_commit:
        if __is_qualified_commit(paths, commit):
            qualified_commits.append(commit)
        commit_parents = commit.parents
        if len(commit_parents) == 0:
            break
        commit = commit_parents[0]
    shutil.rmtree(tmp_dir, ignore_errors=True)
    return __combine_commit_messages(
        latest_commit=latest_commit,
        baseline_commit=baseline_commit,
        commits=qualified_commits,
    )


def __is_qualified_commit(paths: set[str], commit: Commit) -> bool:
    for file in commit.stats.files.keys():
        idx = file.rfind("/")
        if file[:idx] in paths:
            return True
    return False


def __combine_commit_messages(
    latest_commit: str, baseline_commit: str, commits: List[Commit]
) -> str:
    messages = [
        f"This pull request is generated with proto changes between googleapis commit {baseline_commit} and {latest_commit}.",
        "BEGIN_COMMIT_OVERRIDE",
    ]
    for commit in commits:
        short_sha = commit.hexsha[:7]
        messages.append(
            f"[googleapis/googleapis@{short_sha}](https://github.com/googleapis/googleapis/commit/{commit.hexsha})\n{commit.message}"
        )
    messages.append("END_COMMIT_OVERRIDE")

    return "\n\n".join(messages)


if __name__ == "__main__":
    main()
