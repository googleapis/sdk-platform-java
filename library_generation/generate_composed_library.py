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

import click
import utilities as util
import os
import sys
import subprocess
import json
from model.GenerationConfig import GenerationConfig
from model.LibraryConfig import LibraryConfig
from model.ClientInputs import parse as parse_build_file

script_dir = os.path.dirname(os.path.realpath(__file__))

"""
Main function in charge of generating libraries composed of more than one
service or service version.
Arguments
 - config: a GenerationConfig object representing a parsed configuration
 yaml
 - library: a LibraryConfig object contained inside config, passed here for
   convenience and to prevent all libraries to be processed
 - enable_postprocessing: true if postprocessing should be done on the generated
   libraries
 - repository_path: path to the repository where the generated files will be
   sent. If not specified, it will default to the one defined in the configuration yaml
   and will be downloaded. The versions file will be inferred from this folder
"""
def generate_composed_library(
    config: GenerationConfig,
    library: LibraryConfig,
    repository_path: str,
    enable_postprocessing: bool = True,
) -> None:
  output_folder = util.sh_util('get_output_folder')

  print(f'output_folder: {output_folder}')
  print('library: ', library)
  os.makedirs(output_folder, exist_ok=True)

  googleapis_commitish = config.googleapis_commitish
  if library.googleapis_commitish is not None:
    googleapis_commitish = library.googleapis_commitish
    print('using library-specific googleapis commitish: ' + googleapis_commitish)
  else:
    print('using common googleapis_commitish')

  print('removing old googleapis folders and files')
  util.delete_if_exists(f'{output_folder}/google')
  util.delete_if_exists(f'{output_folder}/grafeas')

  print('downloading googleapis')
  util.sh_util(f'download_googleapis_files_and_folders "{output_folder}" "{googleapis_commitish}"')

  is_monorepo = len(config.libraries) > 1

  base_arguments = []
  base_arguments += util.create_argument('gapic_generator_version', config)
  base_arguments += util.create_argument('grpc_version', config)
  base_arguments += util.create_argument('protobuf_version', config)

  library_name = f'java-{library.api_shortname}'
  library_path = None

  versions_file = ''
  if is_monorepo:
    print('this is a monorepo library')
    destination_path = config.destination_path + '/' + library_name
    library_folder = destination_path.split('/')[-1]
    if repository_path is None:
      print(f'sparse_cloning monorepo with {library_name}')
      repository_path = f'{output_folder}/{config.destination_path}'
      clone_out = util.sh_util(f'sparse_clone "https://github.com/googleapis/{MONOREPO_NAME}.git" "{library_folder} google-cloud-pom-parent google-cloud-jar-parent versions.txt .github"', cwd=output_folder)
      print(clone_out)
    library_path = f'{repository_path}/{library_name}'
    versions_file = f'{repository_path}/versions.txt'
  else:
    print('this is a HW library')
    destination_path = library_name
    if repository_path is None:
      repository_path = f'{output_folder}/{destination_path}'
      util.delete_if_exists(f'{output_folder}/{destination_path}')
      clone_out = util.sh_util(f'git clone "https://github.com/googleapis/{destination_path}.git"', cwd=output_folder)
      print(clone_out)
    library_path = f'{repository_path}'
    versions_file = f'{repository_path}/versions.txt'

  owlbot_cli_source_folder = util.sh_util('mktemp -d')
  for gapic in library.gapic_configs:

    effective_arguments = list(base_arguments)
    effective_arguments += util.create_argument('proto_path', gapic)

    build_file_folder = f'{output_folder}/{gapic.proto_path}'
    print(f'build_file_folder: {build_file_folder}')
    client_inputs = parse_build_file(build_file_folder, gapic.proto_path)
    effective_arguments += [
        '--proto_only', client_inputs.proto_only,
        '--gapic_additional_protos', client_inputs.additional_protos,
        '--transport', client_inputs.transport,
        '--rest_numeric_enums', client_inputs.rest_numeric_enum,
        '--gapic_yaml', client_inputs.gapic_yaml,
        '--service_config', client_inputs.service_config,
        '--service_yaml', client_inputs.service_yaml,
        '--include_samples', client_inputs.include_samples,
    ]
    service_version = gapic.proto_path.split('/')[-1]
    temp_destination_path = f'java-{library.api_shortname}-{service_version}'
    effective_arguments += [ '--destination_path', temp_destination_path ]
    print('arguments: ')
    print(effective_arguments)
    print(f'Generating library from {gapic.proto_path} to {destination_path}...')
    util.run_process_and_print_output(['bash', '-x', f'{script_dir}/generate_library.sh',
      *effective_arguments], 'Library generation')


    if enable_postprocessing:
      util.sh_util(f'build_owlbot_cli_source_folder "{library_path}"'
                   + f' "{owlbot_cli_source_folder}" "{output_folder}/{temp_destination_path}"'
                   + f' "{gapic.proto_path}"',
                   cwd=output_folder)

  if enable_postprocessing:
    # call postprocess library
    util.run_process_and_print_output([f'{script_dir}/postprocess_library.sh',
              f'{library_path}', '', versions_file, owlbot_cli_source_folder,
                                       config.owlbot_cli_image, config.synthtool_commitish, str(is_monorepo).lower()], 'Library postprocessing')

