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
import library_generation.utils.utilities as util
from library_generation.generate_composed_library import generate_composed_library
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.library_config import LibraryConfig
from library_generation.utils.monorepo_postprocessor import monorepo_postprocessing

PROTO_ONLY_LIBRARIES = ["common-protos"]


def generate_from_yaml(
    config_path: str,
    config: GenerationConfig,
    repository_path: str,
    target_library_names: list[str] = None,
) -> None:
    """
    Based on the generation config, generates libraries via
    generate_composed_library.py

    :param config_path: Path to generation configuration.
    :param config: a GenerationConfig object.
    :param repository_path: The repository path to which the generated files
    will be sent.
    :param target_library_names: a list of libraries to be generated.
    If specified, only the library whose library_name is in target_library_names
    will be generated.
    If specified with an empty list, then no library will be generated.
    If not specified, all libraries in the configuration yaml will be generated.
    """
    target_libraries = get_target_libraries(
        config=config, target_library_names=target_library_names
    )
    repo_config = util.prepare_repo(
        gen_config=config, library_config=target_libraries, repo_path=repository_path
    )

    for library_path, library in repo_config.libraries.items():
        print(f"generating library {library.get_library_name()}")
        generate_composed_library(
            config_path=config_path,
            config=config,
            library_path=library_path,
            library=library,
            output_folder=repo_config.output_folder,
            versions_file=repo_config.versions_file,
            gapic_repo=not config.contains_common_protos(),
        )

    if not config.is_monorepo() or config.contains_common_protos():
        return

    monorepo_postprocessing(
        repository_path=repository_path, versions_file=repo_config.versions_file
    )


def get_target_libraries(
    config: GenerationConfig, target_library_names: list[str] = None
) -> list[LibraryConfig]:
    """
    Returns LibraryConfig objects whose library_name is in target_library_names.

    :param config: a GenerationConfig object.
    :param target_library_names: library_name of target libraries.
    If not specified, all libraries in the given config will be returned.
    :return: LibraryConfig objects.
    """
    if target_library_names is None:
        return config.libraries
    target_libraries = set(target_library_names)
    return [
        library
        for library in config.libraries
        if library.get_library_name() in target_libraries
    ]
