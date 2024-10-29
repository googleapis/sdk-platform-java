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
import sys
from typing import Optional
import click as click
from library_generation.generate_repo import generate_from_yaml
from library_generation.model.config_change import ConfigChange
from common.model.generation_config import from_yaml
from library_generation.utils.generation_config_comparator import compare_config


@click.group(invoke_without_command=False)
@click.pass_context
@click.version_option(message="%(version)s")
def main(ctx):
    pass


@main.command()
@click.option(
    "--baseline-generation-config-path",
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
    "--current-generation-config-path",
    required=False,
    default=None,
    type=str,
    help="""
    Absolute or relative path to a generation_config.yaml that contains the
    metadata about library generation.
    """,
)
@click.option(
    "--library-names",
    type=str,
    default=None,
    show_default=True,
    help="""
    A list of library names that will be generated, separated by comma.
    The library name of a library is the value of library_name or api_shortname,
    if library_name is not specified, in the generation configuration.
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
@click.option(
    "--api-definitions-path",
    type=str,
    default=".",
    show_default=True,
    help="""
    The path to which the api definition (proto and service yaml) and its
    dependencies resides.
    If not specified, the path is the current working directory.
    """,
)
def generate(
    baseline_generation_config_path: str,
    current_generation_config_path: str,
    library_names: Optional[str],
    repository_path: str,
    api_definitions_path: str,
):
    """
    Compare baseline generation config and current generation config and
    generate changed libraries based on current generation config.

    If baseline generation config is not specified but current generation
    config is specified, generate all libraries if `library_names` is not
    specified, based on current generation config.

    If current generation config is not specified but baseline generation
    config is specified, raise FileNotFoundError because current generation
    config should be the source of truth of library generation.

    If both baseline generation config and current generation config are not
    specified, generate all libraries based on the default generation config,
    which is generation_config.yaml in the current working directory.

    If `library_names` is specified, only libraries whose name can be found in
    the current generation config or default generation config, if current
    generation config is not specified, will be generated. Changed libraries
    will be ignored even if baseline and current generation config are
    specified.

    Raise FileNotFoundError if the default config does not exist.
    """
    __generate_repo_and_pr_description_impl(
        baseline_generation_config_path=baseline_generation_config_path,
        current_generation_config_path=current_generation_config_path,
        library_names=library_names,
        repository_path=repository_path,
        api_definitions_path=api_definitions_path,
    )


def __generate_repo_and_pr_description_impl(
    baseline_generation_config_path: str,
    current_generation_config_path: str,
    library_names: Optional[str],
    repository_path: str,
    api_definitions_path: str,
):
    """
    Implementation method for generate().
    The decoupling of generate and __generate_repo_and_pr_description_impl is
    meant to allow testing of this implementation function.
    """

    default_generation_config_path = f"{os.getcwd()}/generation_config.yaml"

    if (
        baseline_generation_config_path is None
        and current_generation_config_path is None
    ):
        if not os.path.isfile(default_generation_config_path):
            raise FileNotFoundError(
                f"{default_generation_config_path} does not exist. "
                "A valid generation config has to be passed in as "
                "current_generation_config or exist in the current working "
                "directory."
            )
        current_generation_config_path = default_generation_config_path
    elif current_generation_config_path is None:
        raise FileNotFoundError(
            "current_generation_config is not specified when "
            "baseline_generation_config is specified. "
            "current_generation_config should be the source of truth of "
            "library generation."
        )

    current_generation_config_path = os.path.abspath(current_generation_config_path)
    repository_path = os.path.abspath(repository_path)
    api_definitions_path = os.path.abspath(api_definitions_path)
    include_library_names = _parse_library_name_from(library_names)

    if not baseline_generation_config_path:
        # Execute selective generation based on current_generation_config if
        # baseline_generation_config is not specified.
        generate_from_yaml(
            config=from_yaml(current_generation_config_path),
            repository_path=repository_path,
            api_definitions_path=api_definitions_path,
            target_library_names=include_library_names,
        )
        return

    # Compare two generation configs to get changed libraries.
    baseline_generation_config_path = os.path.abspath(baseline_generation_config_path)
    config_change = compare_config(
        baseline_config=from_yaml(baseline_generation_config_path),
        current_config=from_yaml(current_generation_config_path),
    )
    # Pass None if we want to fully generate the repository.
    changed_library_names = (
        config_change.get_changed_libraries()
        if not _needs_full_repo_generation(config_change=config_change)
        else None
    )
    # Include library names takes preference if specified.
    target_library_names = (
        include_library_names
        if include_library_names is not None
        else changed_library_names
    )
    generate_from_yaml(
        config=config_change.current_config,
        repository_path=repository_path,
        api_definitions_path=api_definitions_path,
        target_library_names=target_library_names,
    )


def _needs_full_repo_generation(config_change: ConfigChange) -> bool:
    """
    Whether you should need a full repo generation, i.e., generate all
    libraries in the generation configuration.
    """
    current_config = config_change.current_config
    return not current_config.is_monorepo() or current_config.contains_common_protos()


def _parse_library_name_from(includes: str) -> Optional[list[str]]:
    if includes is None:
        return None
    return [library_name.strip() for library_name in includes.split(",")]


@main.command()
@click.option(
    "--generation-config-path",
    required=False,
    type=str,
    help="""
    Absolute or relative path to a generation_config.yaml.
    Default to generation_config.yaml in the current working directory.
    """,
)
def validate_generation_config(generation_config_path: str) -> None:
    """
    Validate the given generation configuration.
    """
    if generation_config_path is None:
        generation_config_path = "generation_config.yaml"
    try:
        from_yaml(os.path.abspath(generation_config_path))
        print(f"{generation_config_path} is validated without any errors.")
    except ValueError as err:
        print(err)
        sys.exit(1)


if __name__ == "__main__":
    main()
