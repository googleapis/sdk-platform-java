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

import click as click
from library_generation.generate_pr_description import generate_pr_descriptions
from library_generation.generate_repo import generate_from_yaml
from library_generation.model.generation_config import from_yaml
from library_generation.utils.generation_config_comparator import compare_config


@click.group(invoke_without_command=False)
@click.pass_context
@click.version_option(message="%(version)s")
def main(ctx):
    pass


@main.command()
@click.option(
    "--baseline-generation-config",
    required=True,
    type=str,
    help="""
    
    """,
)
@click.option(
    "--latest-generation-config",
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
    "--repository-path",
    type=str,
    default="https://github.com/googleapis/googleapis.git",
    show_default=True,
    help="""
    
    """,
)
def generate(
    baseline_generation_config: str,
    latest_generation_config: str,
    repository_path: str,
):
    # convert paths to absolute paths, so they can be correctly referenced in
    # downstream scripts
    baseline_generation_config = os.path.abspath(baseline_generation_config)
    latest_generation_config = os.path.abspath(latest_generation_config)
    repository_path = os.path.abspath(repository_path)
    config_change = compare_config(
        baseline_config=from_yaml(baseline_generation_config),
        latest_config=from_yaml(latest_generation_config),
    )
    # generate libraries
    generate_from_yaml(
        config=config_change.latest_config,
        repository_path=repository_path,
        target_library_names=config_change.get_changed_libraries(),
    )
    # generate pull request description
    generate_pr_descriptions(
        config=config_change.latest_config,
        baseline_commit=config_change.baseline_config.googleapis_commitish,
    )


if __name__ == "__main__":
    main()
