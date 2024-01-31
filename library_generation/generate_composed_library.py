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

script_dir = os.path.dirname(os.path.realpath(__file__))


def generate_composed_library(
    config: GenerationConfig,
    library_path: str,
    library: LibraryConfig,
    output_folder: str,
    versions_file: str,
    enable_postprocessing: bool = True,
    language: str = "java"
) -> None:
    """
    Generate libraries composed of more than one service or service version.
    :param config: a GenerationConfig object representing a parsed configuration
    yaml
    :param library_path:
    :param library: a LibraryConfig object contained inside config, passed here
    for convenience and to prevent all libraries to be processed
    :param output_folder:
    :param versions_file:
    :param enable_postprocessing: true if postprocessing should be done on the
    generated libraries
    :param language:
    """
    __pull_api_definition(
        config=config,
        library=library,
        output_folder=output_folder
    )

    is_monorepo = util.check_monorepo(config=config)

    base_arguments = __construct_tooling_arg(config=config)

    owlbot_cli_source_folder = util.sh_util("mktemp -d")
    for gapic in library.gapic_configs:
        effective_arguments = list(base_arguments)
        effective_arguments += util.create_argument("proto_path", gapic)
        build_file_folder = Path(f"{output_folder}/{gapic.proto_path}").resolve()
        print(f"build_file_folder: {build_file_folder}")
        gapic_inputs = parse_build_file(build_file_folder, gapic.proto_path)
        if not os.path.exists(f"{library_path}/.repo-meta.json"):
            # generate .repo-metadata.json here because transport is parsed from
            # BUILD, which lives in a versioned proto_path.
            print("generating .repo-metadata.json")
            __generate_repo_metadata(
                library=library,
                transport=gapic_inputs.transport,
                dest_path=library_path,
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
        temp_destination_path = f"{language}-{library.api_shortname}-{service_version}"
        effective_arguments += ["--destination_path", temp_destination_path]
        print("arguments: ")
        print(effective_arguments)
        print(
            f"Generating library from {gapic.proto_path} to {library_path}"
        )
        util.run_process_and_print_output(
            ["bash", f"{script_dir}/generate_library.sh", *effective_arguments],
            "Library generation",
        )

        if enable_postprocessing:
            util.sh_util(
                f'build_owlbot_cli_source_folder "{library_path}"'
                + f' "{owlbot_cli_source_folder}" "{output_folder}/{temp_destination_path}"'
                + f' "{gapic.proto_path}"',
                cwd=output_folder,
            )

    if enable_postprocessing:
        # call postprocess library
        util.run_process_and_print_output(
            [
                f"{script_dir}/postprocess_library.sh",
                f"{library_path}",
                "",
                versions_file,
                owlbot_cli_source_folder,
                config.owlbot_cli_image,
                config.synthtool_commitish,
                str(is_monorepo).lower(),
            ],
            "Library postprocessing",
        )


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
        "library_type": library.library_type.name,
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
