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

import click

from library_generation.model.generation_config import from_yaml
from library_generation.utilities import get_commit_messages
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
    return get_commit_messages(
        repo_url=repo_url,
        latest_commit=config.googleapis_commitish,
        baseline_commit=baseline_commit,
        paths=paths,
    )


if __name__ == "__main__":
    main()
