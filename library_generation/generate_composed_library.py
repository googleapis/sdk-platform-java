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
This script allows generation of libraries that are composed of more than one
service version. It is achieved by calling `generate_library.sh` without
postprocessing for all service versions and then calling
postprocess_library.sh at the end, once all libraries are ready.

Prerequisites
- Needs a folder named `output` in current working directory. This folder
is automatically detected by `generate_library.sh` and this script ensures it
contains the necessary folders and files, specifically:
  - A "google" folder found in the googleapis/googleapis repository
  - A "grafeas" folder found in the googleapis/googleapis repository
Note: googleapis repo is found in https://github.com/googleapis/googleapis.
"""
from pathlib import Path
from typing import List

import utilities as util
import json
import re
import os
from model.generation_config import GenerationConfig
from model.library_config import LibraryConfig
from model.gapic_inputs import parse as parse_build_file
from model.output_config import OutputConfig

script_dir = os.path.dirname(os.path.realpath(__file__))


def generate_composed_library(
    config: GenerationConfig,
    library: LibraryConfig,
    repository_path: str,
    enable_postprocessing: bool = True,
) -> None:
    """
    Generate libraries composed of more than one service or service version.
    :param config: a GenerationConfig object representing a parsed configuration
    yaml
    :param library: a LibraryConfig object contained inside config, passed here
    for convenience and to prevent all libraries to be processed
    :param repository_path: path to the repository where the generated files
    will be sent. If not specified, it will default to the one defined in the
    configuration yaml and will be downloaded. The versions file will be
    inferred from this folder
    :param enable_postprocessing: true if postprocessing should be done on the
    generated libraries
    """
    output_folder = util.sh_util("get_output_folder")

    print(f"output_folder: {output_folder}")
    print("library: ", library)
    os.makedirs(output_folder, exist_ok=True)

    __pull_api_definition(
        config=config,
        library=library,
        output_folder=output_folder
    )

    is_monorepo = __check_monorepo(config=config)

    output_config = __prepare_destination(
        config=config,
        library=library,
        repository_path=repository_path,
        output_folder=output_folder,
        is_monorepo=is_monorepo
    )

    base_arguments = __construct_tooling_arg(config=config)

    owlbot_cli_source_folder = util.sh_util("mktemp -d")
    for gapic in library.gapic_configs:
        effective_arguments = list(base_arguments)
        effective_arguments += util.create_argument("proto_path", gapic)
        build_file_folder = Path(f"{output_folder}/{gapic.proto_path}").resolve()
        print(f"build_file_folder: {build_file_folder}")
        gapic_inputs = parse_build_file(build_file_folder, gapic.proto_path)
        if not os.path.exists(f"{output_config.library_path}/.repo-meta.json"):
            # generate .repo-metadata.json here because transport is parsed from
            # BUILD, which lives in a versioned proto_path.
            print("generating .repo-metadata.json")
            __generate_repo_metadata(
                library=library,
                transport=gapic_inputs.transport,
                dest_path=output_config.library_path,
            )
        effective_arguments += [
            "--proto_only",
            gapic_inputs.proto_only,
            "--gapic_additional_protos",
            gapic_inputs.additional_protos,
            "--transport",
            gapic_inputs.transport,
            "--rest_numeric_enums",
            gapic_inputs.rest_numeric_enum,
            "--gapic_yaml",
            gapic_inputs.gapic_yaml,
            "--service_config",
            gapic_inputs.service_config,
            "--service_yaml",
            gapic_inputs.service_yaml,
            "--include_samples",
            gapic_inputs.include_samples,
        ]
        service_version = gapic.proto_path.split("/")[-1]
        temp_destination_path = f"java-{library.api_shortname}-{service_version}"
        effective_arguments += ["--destination_path", temp_destination_path]
        print("arguments: ")
        print(effective_arguments)
        print(
            f"Generating library from {gapic.proto_path} " f"to {destination_path}..."
        )
        util.run_process_and_print_output(
            ["bash", "-x", f"{script_dir}/generate_library.sh", *effective_arguments],
            "Library generation",
        )

        if enable_postprocessing:
            util.sh_util(
                f'build_owlbot_cli_source_folder "{output_config.library_path}"'
                + f' "{owlbot_cli_source_folder}" "{output_folder}/{temp_destination_path}"'
                + f' "{gapic.proto_path}"',
                cwd=output_folder,
            )

    if enable_postprocessing:
        # call postprocess library
        util.run_process_and_print_output(
            [
                f"{script_dir}/postprocess_library.sh",
                f"{output_config.library_path}",
                "",
                output_config.versions_file,
                owlbot_cli_source_folder,
                config.owlbot_cli_image,
                config.synthtool_commitish,
                str(is_monorepo).lower(),
            ],
            "Library postprocessing",
        )

        print("run repo-level post-processing.")


def __construct_tooling_arg(config: GenerationConfig) -> List[str]:
    """
    Construct arguments of tooling versions used in generate_library.sh
    :param config: the generation config
    :return: arguments containing tooling versions
    """
    arguments = []
    arguments += util.create_argument("gapic_generator_version", config)
    arguments += util.create_argument("grpc_version", config)
    arguments += util.create_argument("protobuf_version", config)

    return arguments


def __prepare_destination(
    config: GenerationConfig,
    library: LibraryConfig,
    repository_path: str,
    output_folder: str,
    is_monorepo: bool,
    exemptions: List[str] = None
) -> OutputConfig:
    """
    Prepare the destination path for the generated library. All files in the
    library_path (defined in the returned object) will be deleted before the
    generation, excepts for files listed in exemptions.
    :param config: the generation config
    :param library: the library config
    :param repository_path:
    :param output_folder:
    :param is_monorepo: whether to generate a monorepo
    :param exemptions: a list of files should be retained
    :return: an OutputConfig object containing the output attributes
    """
    library_name = f"java-{library.api_shortname}"
    if library.library_name is not None:
        library_name = f"java-{library.library_name}"

    if is_monorepo:
        print("this is a monorepo library")
        destination_path = f"{config.destination_path}/{library_name}"
        library_folder = destination_path.split("/")[-1]
        if repository_path is None:
            print(f"sparse_cloning monorepo with {library_name}")
            repository_path = f"{output_folder}/{config.destination_path}"
            clone_out = util.sh_util(
                f'sparse_clone "https://github.com/googleapis/{MONOREPO_NAME}.git" "{library_folder} google-cloud-pom-parent google-cloud-jar-parent versions.txt .github"',
                cwd=output_folder,
            )
            print(clone_out)
        library_path = f"{repository_path}/{library_name}"
        versions_file = f"{repository_path}/versions.txt"
    else:
        print("this is a standalone library")
        destination_path = library_name
        if repository_path is None:
            repository_path = f"{output_folder}/{destination_path}"
            util.delete_if_exists(f"{output_folder}/{destination_path}")
            clone_out = util.sh_util(
                f'git clone "https://github.com/googleapis/{destination_path}.git"',
                cwd=output_folder,
            )
            print(clone_out)
        library_path = f"{repository_path}"
        versions_file = f"{repository_path}/versions.txt"
    if not exemptions:
        exemptions = [".OwlBot.yaml", "owlbot.py"]
    print(f"deleting {library_path} before generating, excluding {exemptions}")

    return OutputConfig(library_path=library_path, versions_file=versions_file)


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
                file_path.unlink()
                print(f"Deleted file: {file_path.name}")
            except OSError as e:
                print(f"Error deleting file {file_path.name}: {e}")
        else:
            print(f"Skipping file: {file_path.name} (exempted)")


def __check_monorepo(config: GenerationConfig) -> bool:
    """
    Check whether to generate a monorepo according to the
    generation config.
    :param config: the generation configuration
    :return: True if it's to generate a monorepo
    """
    return len(config.libraries) > 1


def __pull_api_definition(
    config: GenerationConfig,
    library: LibraryConfig,
    output_folder: str
) -> None:
    """
    Pull APIs definition from googleapis/googleapis repository.
    To avoid duplicated pulling, only perform pulling if the library uses
    a different commitish than in generation config.
    :param config:
    :param library:
    :param output_folder:
    :return: None
    """
    googleapis_commitish = config.googleapis_commitish
    if library.googleapis_commitish:
        googleapis_commitish = library.googleapis_commitish
        print(f"using library-specific googleapis commitish: {googleapis_commitish}")
    else:
        print(f"using common googleapis_commitish: {config.googleapis_commitish}")

    if googleapis_commitish != config.googleapis_commitish:
        print("removing existing APIs definition")
        util.delete_if_exists(f"{output_folder}/google")
        util.delete_if_exists(f"{output_folder}/grafeas")

    if not (
        os.path.exists(f"{output_folder}/google")
        and os.path.exists(f"{output_folder}/grafeas")
    ):
        print("downloading googleapis")
        util.sh_util(
            f"download_googleapis_files_and_folders {output_folder} {googleapis_commitish}"
        )


def __generate_repo_metadata(
    library: LibraryConfig,
    transport: str,
    dest_path: str,
    language: str = "java",
    is_monorepo: bool = True,
) -> None:
    """
    Generate .repo-metadata.json for a library
    :param library: the library configuration
    :param transport: transport supported by the library
    :param language: programming language of the library
    :param is_monorepo: whether the library is in a monorepo
    :return: None
    """
    cloud_prefix = "cloud-" if library.cloud_api else ""
    library_name = (
        library.library_name if library.library_name else library.api_shortname
    )
    distribution_name = (
        library.distribution_name
        if library.distribution_name
        else f"{library.group_id}:google-{cloud_prefix}{library_name}"
    )
    distribution_name_short = re.split(r"[:/]", distribution_name)[-1]
    repo = (
        "googleapis/google-cloud-java" if is_monorepo else f"{language}-{library_name}"
    )
    api_id = (
        library.api_id if library.api_id else f"{library.api_shortname}.googleapis.com"
    )
    client_documentation = (
        library.client_documentation
        if library.client_documentation
        else f"https://cloud.google.com/{language}/docs/reference/{distribution_name_short}/latest/overview"
    )

    repo_metadata = {
        "api_shortname": library.api_shortname,
        "name_pretty": library.name_pretty,
        "product_documentation": library.product_documentation,
        "api_description": library.api_description,
        "client_documentation": client_documentation,
        "release_level": library.release_level,
        "transport": transport,
        "language": language,
        "repo": repo,
        "repo_short": f"{language}-{library_name}",
        "distribution_name": distribution_name,
        "api_id": api_id,
        "library_type": library.library_type,
    }

    if library.requires_billing:
        repo_metadata["requires_billing"] = True
    if library.rest_documentation:
        repo_metadata["rest_documentation"] = library.rest_documentation
    if library.rpc_documentation:
        repo_metadata["rpc_documentation"] = library.rpc_documentation
    dest = Path(dest_path).resolve()
    with open(f"{dest}/.repo-metadata.json", "w") as fp:
        json.dump(repo_metadata, fp, indent=2)
