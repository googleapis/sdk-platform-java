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
import os
from pathlib import Path
from typing import List
import library_generation.utils.utilities as util
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.gapic_config import GapicConfig
from library_generation.model.gapic_inputs import GapicInputs
from library_generation.model.library_config import LibraryConfig
from library_generation.model.gapic_inputs import parse as parse_build_file

script_dir = os.path.dirname(os.path.realpath(__file__))


def generate_composed_library(
    config_path: str,
    config: GenerationConfig,
    library_path: str,
    library: LibraryConfig,
    output_folder: str,
    versions_file: str,
) -> None:
    """
    Generate libraries composed of more than one service or service version

    :param config_path: Path to generation configuration.
    :param config: a GenerationConfig object representing a parsed configuration
    yaml
    :param library_path: the path to which the generated file goes
    :param library: a LibraryConfig object contained inside config, passed here
    for convenience and to prevent all libraries to be processed
    :param output_folder: the folder to where tools go
    :param versions_file: the file containing version of libraries
    :return None
    """
    util.pull_api_definition(
        config=config, library=library, output_folder=output_folder
    )

    base_arguments = __construct_tooling_arg(config=config)
    owlbot_cli_source_folder = util.sh_util("mktemp -d")
    os.makedirs(f"{library_path}", exist_ok=True)
    for gapic in library.gapic_configs:
        build_file_folder = Path(f"{output_folder}/{gapic.proto_path}").resolve()
        print(f"build_file_folder: {build_file_folder}")
        gapic_inputs = parse_build_file(build_file_folder, gapic.proto_path)
        # generate prerequisite files (.repo-metadata.json, .OwlBot-hermetic.yaml,
        # owlbot.py) here because transport is parsed from BUILD.bazel,
        # which lives in a versioned proto_path.
        util.generate_prerequisite_files(
            config=config,
            library=library,
            proto_path=util.remove_version_from(gapic.proto_path),
            transport=gapic_inputs.transport,
            library_path=library_path,
        )
        temp_destination_path = f"java-{gapic.proto_path.replace('/','-')}"
        effective_arguments = __construct_effective_arg(
            base_arguments=base_arguments,
            gapic=gapic,
            gapic_inputs=gapic_inputs,
            temp_destination_path=temp_destination_path,
        )
        print("arguments: ")
        print(effective_arguments)
        print(f"Generating library from {gapic.proto_path} to {library_path}")
        util.run_process_and_print_output(
            ["bash", f"{script_dir}/generate_library.sh", *effective_arguments],
            "Library generation",
        )

        util.sh_util(
            f'build_owlbot_cli_source_folder "{library_path}"'
            + f' "{owlbot_cli_source_folder}" "{output_folder}/{temp_destination_path}"'
            + f' "{gapic.proto_path}"',
            cwd=output_folder,
        )

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
            str(config.is_monorepo()).lower(),
            config_path,
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


def __construct_effective_arg(
    base_arguments: List[str],
    gapic: GapicConfig,
    gapic_inputs: GapicInputs,
    temp_destination_path: str,
) -> List[str]:
    """
    Construct arguments consist attributes of a GAPIC library which used in
    generate_library.sh
    :param base_arguments: arguments consist of tooling versions
    :param gapic: an object of GapicConfig
    :param gapic_inputs: an object of GapicInput
    :param temp_destination_path: the path to which the generated library goes
    :return: arguments containing attributes to generate a GAPIC library
    """
    arguments = list(base_arguments)
    arguments += util.create_argument("proto_path", gapic)
    arguments += [
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
    arguments += ["--destination_path", temp_destination_path]

    return arguments
