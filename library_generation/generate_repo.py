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
import utilities as util
from generate_composed_library import generate_composed_library
from model.generation_config import from_yaml


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
    library generation
    """,
)
@click.option(
    "--target-library-api-shortname",
    required=False,
    type=str,
    help="""
    If specified, only the `library` whose api_shortname equals to 
    target-library-api-shortname will be generated. 
    If not specified, all libraries in the configuration yaml will be generated.
    """,
)
@click.option(
    "--repository-path",
    required=False,
    default=".",
    type=str,
    help="""
    If specified, the generated files will be sent to this location.
    If not specified, the repository will be generated to the current working
    directory.
    """,
)
def generate_from_yaml(
    generation_config_yaml: str,
    target_library_api_shortname: str,
    repository_path: str,
) -> None:
    """
    Parses a config yaml and generates libraries via
    generate_composed_library.py
    """
    config = from_yaml(generation_config_yaml)
    target_libraries = config.libraries
    if target_library_api_shortname is not None:
        target_libraries = [
            library
            for library in config.libraries
            if library.api_shortname == target_library_api_shortname
        ]

    repo_config = util.prepare_repo(
        gen_config=config,
        library_config=target_libraries,
        repo_path=repository_path
    )

    for library_path, library in repo_config.libraries.items():
        print(f"generating library {library.api_shortname}")

        generate_composed_library(
            config=config,
            library_path=library_path,
            library=library,
            output_folder=repo_config.output_folder,
            versions_file=repo_config.versions_file,
        )

    util.repo_level_post_process(
        repository_path=repository_path,
        versions_file=repo_config.versions_file
    )


if __name__ == "__main__":
    main()
