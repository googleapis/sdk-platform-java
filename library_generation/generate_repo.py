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

"""
Parses a config yaml and generates libraries via generate_composed_library.py
"""

import click
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
    Path to generation_config.yaml that contains the metadata about library generation
    """,
)
@click.option(
    "--enable-postprocessing",
    required=False,
    default=True,
    type=bool,
    help="""
    Path to repository where generated files will be merged into, via owlbot copy-code.
    Specifying this option enables postprocessing
    """,
)
@click.option(
    "--target-library-api-shortname",
    required=False,
    type=str,
    help="""
    If specified, only the `library` with api_shortname = target-library-api-shortname will
    be generated. If not specified, all libraries in the configuration yaml will be generated
    """,
)
@click.option(
    "--repository-path",
    required=False,
    type=str,
    help="""
    If specified, the generated files will be sent to this location. If not specified, the
    repository will be pulled into output_folder and move the generated files there
    """,
)
def generate_from_yaml(
    generation_config_yaml: str,
    enable_postprocessing: bool,
    target_library_api_shortname: str,
    repository_path: str,
) -> None:
    config = from_yaml(generation_config_yaml)
    target_libraries = config.libraries
    if target_library_api_shortname is not None:
        target_libraries = [
            library
            for library in config.libraries
            if library.api_shortname == target_library_api_shortname
        ]
    for library in target_libraries:
        print(f"generating library {library.api_shortname}")
        generate_composed_library(
            config, library, repository_path, enable_postprocessing
        )


if __name__ == "__main__":
    main()
