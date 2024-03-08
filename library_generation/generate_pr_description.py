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
from typing import Dict

import click
from git import Commit, Repo
from library_generation.model.generation_config import from_yaml
from library_generation.utilities import find_versioned_proto_path
from library_generation.utils.commit_message_formatter import format_commit_message
from library_generation.utilities import get_file_paths
from library_generation.utils.commit_message_formatter import wrap_nested_commit


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
    The googleapis commit in the configuration is the latest commit,
    inclusively, from which the commit message is considered.
    """,
)
@click.option(
    "--baseline-commit",
    required=True,
    type=str,
    help="""
    The baseline (oldest) commit, exclusively, from which the commit message is
    considered.
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
    description = generate_pr_descriptions(
        generation_config_yaml=generation_config_yaml,
        repo_url=repo_url,
        baseline_commit=baseline_commit,
    )
    idx = generation_config_yaml.rfind("/")
    config_path = generation_config_yaml[:idx]
    with open(f"{config_path}/pr_description.txt", "w+") as f:
        f.write(description)
    return description


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
        generator_version=config.gapic_generator_version,
        is_monorepo=config.is_monorepo,
    )


def __get_commit_messages(
    repo_url: str,
    latest_commit: str,
    baseline_commit: str,
    paths: Dict[str, str],
    generator_version: str,
    is_monorepo: bool,
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
    :param paths: a mapping from file paths to library_name.
    :param generator_version: the version of the generator.
    :param is_monorepo: whether to generate commit messages in a monorepo.
    :return: commit messages.
    """
    tmp_dir = "/tmp/repo"
    shutil.rmtree(tmp_dir, ignore_errors=True)
    os.mkdir(tmp_dir)
    repo = Repo.clone_from(repo_url, tmp_dir)
    commit = repo.commit(latest_commit)
    qualified_commits = {}
    while str(commit.hexsha) != baseline_commit:
        commit_and_name = __filter_qualified_commit(paths=paths, commit=commit)
        if commit_and_name != ():
            qualified_commits[commit_and_name[0]] = commit_and_name[1]
        commit_parents = commit.parents
        if len(commit_parents) == 0:
            break
        commit = commit_parents[0]
    shutil.rmtree(tmp_dir, ignore_errors=True)
    return __combine_commit_messages(
        latest_commit=latest_commit,
        baseline_commit=baseline_commit,
        commits=qualified_commits,
        generator_version=generator_version,
        is_monorepo=is_monorepo,
    )


def __filter_qualified_commit(paths: Dict[str, str], commit: Commit) -> (Commit, str):
    """
    Returns a tuple of a commit and libray_name.
    A qualified commit means at least one file changes in that commit is
    within the versioned proto_path in paths.

    :param paths: a mapping from versioned proto_path to library_name.
    :param commit: a commit under consideration.
    :return: a tuple of a commit and library_name if the commit is
    qualified; otherwise an empty tuple.
    """
    for file in commit.stats.files.keys():
        versioned_proto_path = find_versioned_proto_path(file)
        if versioned_proto_path in paths:
            return commit, paths[versioned_proto_path]
    return ()


def __combine_commit_messages(
    latest_commit: str,
    baseline_commit: str,
    commits: Dict[Commit, str],
    generator_version: str,
    is_monorepo: bool,
) -> str:
    messages = [
        f"This pull request is generated with proto changes between googleapis commit {baseline_commit} (exclusive) and {latest_commit} (inclusive).",
        "Qualified commits are:",
    ]
    for commit in commits:
        short_sha = commit.hexsha[:7]
        messages.append(
            f"[googleapis/googleapis@{short_sha}](https://github.com/googleapis/googleapis/commit/{commit.hexsha})"
        )

    messages.extend(format_commit_message(commits=commits, is_monorepo=is_monorepo))
    messages.extend(
        wrap_nested_commit(
            [
                f"feat: Regenerate with the Java code generator (gapic-generator-java) v{generator_version}"
            ]
        )
    )

    return "\n".join(messages)


if __name__ == "__main__":
    main()
