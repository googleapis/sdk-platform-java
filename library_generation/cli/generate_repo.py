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
import click
from library_generation.generate_repo import generate_from_yaml


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
    "--target-library-names",
    required=False,
    default=None,
    type=str,
    help="""
    The input string will be parsed to a list of string with comma as the
    separator.
    
    For example, apigeeconnect,alloydb-connectors will be parsed as a
    list of two strings, apigeeconnect and alloydb-connectors.
    
    If specified, only the `library` whose library_name is in
    target-library-names will be generated.
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
def generate(
    generation_config_yaml: str,
    target_library_names: str,
    repository_path: str,
):
    generate_from_yaml(
        generation_config_yaml=generation_config_yaml,
        repository_path=repository_path,
        target_library_names=target_library_names.split(",")
        if target_library_names is not None
        else target_library_names,
    )


if __name__ == "__main__":
    main()
