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
import os
import shutil
from pathlib import Path

from typing import List

import click
import utilities as util
from generate_composed_library import generate_composed_library
from library_generation.model.repo_config import RepoConfig
from model.generation_config import from_yaml
from model.generation_config import GenerationConfig
from model.library_config import LibraryConfig


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

    repo_config = __prepare_repo(
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
            enable_postprocessing=enable_postprocessing,
        )


def __prepare_repo(
    gen_config: GenerationConfig,
    library_config: List[LibraryConfig],
    repo_path: str,
    exemptions: List[str] = None,
    language: str = "java"
) -> RepoConfig:
    """

    :param gen_config:
    :param library_config:
    :param repo_path:
    :param exemptions:
    :param language:
    :return:
    """
    output_folder = util.sh_util("get_output_folder")
    print(f"output_folder: {output_folder}")
    os.makedirs(output_folder, exist_ok=True)
    is_monorepo = util.check_monorepo(gen_config)
    libraries = {}
    for library in library_config:
        library_name = f"{language}-{library.api_shortname}"
        if library.library_name is not None:
            library_name = f"{language}-{library.library_name}"
        if is_monorepo:
            library_path = f"{repo_path}/{library_name}"
        else:
            library_path = f"{repo_path}"
        # use absolute path because docker requires absolute path
        # in volume name.
        absolute_library_path = str(Path(library_path).resolve())
        libraries[absolute_library_path] = library

    if is_monorepo:
        print("this is a monorepo library")
        if repo_path is None:
            repo_path = f"{output_folder}/{gen_config.destination_path}"
            print(f"sparse_cloning monorepo with {libraries.keys()}")
            clone_out = util.sh_util(
                f'sparse_clone "https://github.com/googleapis/google-cloud-java.git" "{" ".join(libraries.keys())} google-cloud-pom-parent google-cloud-jar-parent versions.txt .github"',
                cwd=output_folder,
            )
            print(clone_out)
    else:
        print("this is a standalone library")
        if repo_path is None:
            destination_path = libraries.keys()[0]
            repo_path = f"{output_folder}/{destination_path}"
            clone_out = util.sh_util(
                f'git clone "https://github.com/googleapis/{"".join(libraries)}.git"',
                cwd=output_folder,
            )
            print(clone_out)

    if not exemptions:
        exemptions = [
            ".OwlBot.yaml",
            "owlbot.py",
            "CHANGELOG.md",
            ".readme-partials.yaml"
        ]
    for library_path in libraries.keys():
        print(f"deleting {library_path} before generating, excluding {exemptions}")
        __delete_files_in(path=library_path, exemptions=exemptions)

    versions_file = f"{repo_path}/versions.txt"

    return RepoConfig(
        output_folder=output_folder,
        libraries=libraries,
        versions_file=str(Path(versions_file).resolve())
    )


def __delete_files_in(path: str, exemptions: List[str] = None):
    """

    :param path:
    :param exemptions:
    :return:
    """
    target_folder = Path(path).resolve()
    for file_path in target_folder.iterdir():
        if file_path.name not in exemptions:
            try:
                if file_path.is_dir():
                    shutil.rmtree(file_path)
                else:
                    file_path.unlink()
                print(f"Deleted : {file_path.name}")
            except OSError as e:
                print(f"Error deleting {file_path.name}: {e}")
        else:
            print(f"Skipping file: {file_path.name} (exempted)")


if __name__ == "__main__":
    main()
