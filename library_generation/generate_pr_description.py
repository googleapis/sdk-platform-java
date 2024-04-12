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
import calendar
import os
import shutil
import click as click
from typing import Dict
from git import Commit, Repo
from library_generation.model.generation_config import GenerationConfig, from_yaml
from library_generation.utils.proto_path_utils import find_versioned_proto_path
from library_generation.utils.commit_message_formatter import format_commit_message
from library_generation.utils.commit_message_formatter import wrap_override_commit


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
def generate(
    generation_config_yaml: str,
    baseline_commit: str,
) -> None:
    idx = generation_config_yaml.rfind("/")
    config_path = generation_config_yaml[:idx]
    generate_pr_descriptions(
        config=from_yaml(generation_config_yaml),
        baseline_commit=baseline_commit,
        description_path=config_path,
    )


def generate_pr_descriptions(
    config: GenerationConfig,
    baseline_commit: str,
    description_path: str,
    repo_url: str = "https://github.com/googleapis/googleapis.git",
) -> None:
    """
    Generate pull request description from baseline_commit (exclusive) to the
    googleapis commit (inclusive) in the given generation config.

    The pull request description will be generated into
    description_path/pr_description.txt.

    :param config: a GenerationConfig object. The googleapis commit in this
    configuration is the latest commit, inclusively, from which the commit
    message is considered.
    :param baseline_commit: The baseline (oldest) commit, exclusively, from
    which the commit message is considered. This commit should be an ancestor
    of googleapis commit in configuration.
    :param description_path: the path to which the pull request description
    file goes.
    :param repo_url: the GitHub repository from which retrieves the commit
    history.
    """
    paths = config.get_proto_path_to_library_name()
    description = get_commit_messages(
        repo_url=repo_url,
        current_commit=config.googleapis_commitish,
        baseline_commit=baseline_commit,
        paths=paths,
        is_monorepo=config.is_monorepo,
    )

    description_file = f"{description_path}/pr_description.txt"
    print(f"Writing pull request description to {description_file}")
    with open(description_file, "w+") as f:
        f.write(description)


def get_commit_messages(
    repo_url: str,
    current_commit: str,
    baseline_commit: str,
    paths: Dict[str, str],
    is_monorepo: bool,
) -> str:
    """
    Combine commit messages of a repository from latest_commit to
    baseline_commit. Only commits which change files in a pre-defined
    file paths will be considered.
    Note that baseline_commit should be an ancestor of latest_commit.

    :param repo_url: the url of the repository.
    :param current_commit: the newest commit to be considered in
    selecting commit message.
    :param baseline_commit: the oldest commit to be considered in
    selecting commit message. This commit should be an ancestor of
    :param paths: a mapping from file paths to library_name.
    :param is_monorepo: whether to generate commit messages in a monorepo.
    :return: commit messages.
    """
    tmp_dir = "/tmp/repo"
    shutil.rmtree(tmp_dir, ignore_errors=True)
    os.mkdir(tmp_dir)
    repo = Repo.clone_from(repo_url, tmp_dir)
    commit = repo.commit(current_commit)
    current_commit_time = __get_commit_timestamp(commit)
    baseline_commit_time = __get_commit_timestamp(repo.commit(baseline_commit))
    if current_commit_time <= baseline_commit_time:
        raise ValueError(
            f"current_commit ({current_commit[:7]}, committed on "
            f"{current_commit_time}) should be newer than baseline_commit "
            f"({baseline_commit[:7]}, committed on {baseline_commit_time})."
        )
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
        current_commit=current_commit,
        baseline_commit=baseline_commit,
        commits=qualified_commits,
        is_monorepo=is_monorepo,
    )


def __filter_qualified_commit(paths: Dict[str, str], commit: Commit) -> (Commit, str):
    """
    Returns a tuple of a commit and libray_name.
    A qualified commit means at least one file, excluding BUILD.bazel, changes
    in that commit is within the versioned proto_path in paths.

    :param paths: a mapping from versioned proto_path to library_name.
    :param commit: a commit under consideration.
    :return: a tuple of a commit and library_name if the commit is
    qualified; otherwise an empty tuple.
    """
    for file in commit.stats.files.keys():
        versioned_proto_path = find_versioned_proto_path(file)
        if versioned_proto_path in paths and (not file.endswith("BUILD.bazel")):
            return commit, paths[versioned_proto_path]
    return ()


def __combine_commit_messages(
    current_commit: str,
    baseline_commit: str,
    commits: Dict[Commit, str],
    is_monorepo: bool,
) -> str:
    messages = [
        f"This pull request is generated with proto changes between googleapis commit {baseline_commit} (exclusive) and {current_commit} (inclusive).",
        "Qualified commits are:",
    ]
    for commit in commits:
        short_sha = commit.hexsha[:7]
        messages.append(
            f"[googleapis/googleapis@{short_sha}](https://github.com/googleapis/googleapis/commit/{commit.hexsha})"
        )

    messages.extend(
        wrap_override_commit(
            format_commit_message(commits=commits, is_monorepo=is_monorepo)
        )
    )

    return "\n".join(messages)


def __get_commit_timestamp(commit: Commit) -> int:
    """
    # Convert datetime to UTC timestamp. For more info:
    # https://stackoverflow.com/questions/5067218/get-utc-timestamp-in-python-with-datetime

    :param commit: a Commit object
    :return: the timestamp of the commit
    """
    return calendar.timegm(commit.committed_datetime.utctimetuple())


if __name__ == "__main__":
    main()
