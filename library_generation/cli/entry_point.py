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
    required=False,
    default=None,
    type=str,
    help="""
    Absolute or relative path to a generation_config.yaml.
    This config file is used for commit history generation, not library
    generation.
    """,
)
@click.option(
    "--current-generation-config",
    required=False,
    default=None,
    type=str,
    help="""
    Absolute or relative path to a generation_config.yaml that contains the
    metadata about library generation.
    """,
)
@click.option(
    "--repository-path",
    type=str,
    default=".",
    show_default=True,
    help="""
    The repository path to which the generated files
    will be sent.
    If not specified, the repository will be generated to the current working
    directory.
    """,
)
def generate(
    baseline_generation_config: str,
    current_generation_config: str,
    repository_path: str,
):
    """
    Compare baseline generation config and current generation config and
    generate changed libraries based on current generation config with commit
    history.

    If baseline generation config is not specified but current generation
    config is specified, generate all libraries based on current generation
    config without commit history.

    If current generation config is not specified but baseline generation
    config is specified, raise FileNotFoundError because current generation
    config should be the source of truth of library generation.

    If both baseline generation config and current generation config are not
    specified, generate all libraries based on the default generation config,
    which is generation_config.yaml in the current working directory. Raise
    FileNotFoundError if the default config does not exist.

    The commit history, if generated, will be available in
    repository_path/pr_description.txt.
    """
    default_generation_config = f"{os.getcwd()}/generation_config.yaml"

    if baseline_generation_config is None and current_generation_config is None:
        if not os.path.isfile(default_generation_config):
            raise FileNotFoundError(
                f"{default_generation_config} does not exist. "
                "A valid generation config has to be passed in as "
                "current_generation_config or exist in the current working "
                "directory."
            )
        current_generation_config = default_generation_config
    elif current_generation_config is None:
        raise FileNotFoundError(
            "current_generation_config is not specified when "
            "baseline_generation_config is specified. "
            "current_generation_config should be the source of truth of "
            "library generation."
        )

    current_generation_config = os.path.abspath(current_generation_config)
    repository_path = os.path.abspath(repository_path)
    if not baseline_generation_config:
        # Execute full generation based on current_generation_config if
        # baseline_generation_config is not specified.
        # Do not generate pull request description.
        generate_from_yaml(
            config=from_yaml(current_generation_config),
            repository_path=repository_path,
        )
        return

    # Compare two generation configs and only generate changed libraries.
    # Generate pull request description.
    baseline_generation_config = os.path.abspath(baseline_generation_config)
    config_change = compare_config(
        baseline_config=from_yaml(baseline_generation_config),
        current_config=from_yaml(current_generation_config),
    )
    generate_from_yaml(
        config=config_change.current_config,
        repository_path=repository_path,
        target_library_names=config_change.get_changed_libraries(),
    )
    generate_pr_descriptions(
        config=config_change.current_config,
        baseline_commit=config_change.baseline_config.googleapis_commitish,
        description_path=repository_path,
    )


if __name__ == "__main__":
    main()
