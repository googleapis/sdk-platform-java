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
from common.model.generation_config import from_yaml, GenerationConfig


@click.group(invoke_without_command=False)
@click.pass_context
@click.version_option(message="%(version)s")
def main(ctx):
    pass


@main.command()
@click.option(
    "--generation-config-path",
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
    generation_config_path: Optional[str],
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
    __generate_repo_impl(
        generation_config_path=generation_config_path,
        library_names=library_names,
        repository_path=repository_path,
        api_definitions_path=api_definitions_path,
    )


def __generate_repo_impl(
    generation_config_path: Optional[str],
    library_names: Optional[str],
    repository_path: str,
    api_definitions_path: str,
):
    """
    Implementation method for generate().
    The decoupling of generate and __generate_repo_impl is
    meant to allow testing of this implementation function.
    """

    default_generation_config_path = f"{os.getcwd()}/generation_config.yaml"
    if generation_config_path is None:
        generation_config_path = default_generation_config_path
    generation_config_path = os.path.abspath(generation_config_path)
    if not os.path.isfile(generation_config_path):
        raise FileNotFoundError(
            f"Generation config {generation_config_path} does not exist."
        )
    repository_path = os.path.abspath(repository_path)
    api_definitions_path = os.path.abspath(api_definitions_path)
    generation_config = from_yaml(generation_config_path)
    include_library_names = _parse_library_name_from(
        includes=library_names, generation_config=generation_config
    )
    generate_from_yaml(
        config=generation_config,
        repository_path=repository_path,
        api_definitions_path=api_definitions_path,
        target_library_names=include_library_names,
    )


def _needs_full_repo_generation(generation_config: GenerationConfig) -> bool:
    """
    Whether you should need a full repo generation, i.e., generate all
    libraries in the generation configuration.
    """
    return (
        not generation_config.is_monorepo()
        or generation_config.contains_common_protos()
    )


def _parse_library_name_from(
    includes: str, generation_config: GenerationConfig
) -> Optional[list[str]]:
    if includes is None or _needs_full_repo_generation(generation_config):
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
