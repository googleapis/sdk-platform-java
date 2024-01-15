"""
This script allows generation of libraries that are composed of more than one
service version. It is achieved by calling `generate_library.sh` without
postprocessing for all service versions and then calling
postprocess_library.sh at the end, once all libraries are ready.

Arguments
--generation_queries: a single string of comma-separated key-value groups separated by a
pipe | (i.e. the groups are spearated by pipe, while a group's key-values are
separated by comma). They key-value groups are in the form of `key=value` and will
be converted to an argument to generate_library.sh (`--key value`).
  example: "proto_path=google/cloud/asset/v1,destination_path=google-cloud-asset-v1-java,(...)|proto_path=google/cloud/asset/v1p2beta5,destination_path=google-cloud-asset-v1-java,(...)"
  In this case, generate_library.sh will be called once with
    generate_library.sh --proto_path google/cloud/asset/v1 --destination_path google-cloud-asset-v1-java
    and once with
    generate_library.sh --proto_path google/cloud/asset/v1p2bet5 --destination_path google-cloud-asset-v1p2beta5-java
--versions_file: a file of newline-separated version strings in the form "module:released-version:current-version". The versions will be applied to the pom.xml files and readmes

Prerequisites
- Needs an `output` folder at the location of the calling shell. This folder
is automatically detected by `generate_library.sh` and this script ensures it
contains the necessary folders and files, specifically:
  - A "google" folder found in the googleapis repository
  - A "grafeas" folder found in the googleapis repository
Note: googleapis repo is found in https://github.com/googleapis/googleapis.
"""

import click
import utilities as util
import os
import subprocess

script_dir = os.path.dirname(os.path.realpath(__file__))

def main(ctx):
    pass

def generate_composed_library(
    config,
    api_shortname,
    repository_path,
    enable_postprocessing
):
  output_folder = util.sh_util('get_output_folder')
  print(f'output_folder: {output_folder}')
  os.makedirs(output_folder, exist_ok=True)


  base_arguments = []
  base_arguments += util.create_argument('gapic_generator_version', config)
  base_arguments += util.create_argument('grpc_version', config)
  base_arguments += util.create_argument('protobuf_version', config)
  base_arguments += util.create_argument('googleapis_commitish', config)
  base_arguments += util.create_argument('owlbot_cli_image', config)
  base_arguments += util.create_argument('python_version', config)

  destination_path = f'java-{api_shortname}'
  if config.destination_path is not None:
    destination_path = config.destination_path + '/' + destination_path
  base_arguments += ['--destination_path', destination_path]
  versions_file = ''
  if 'google-cloud-java' in destination_path:
    print('this is a monorepo library')
    library = destination_path.split('/')[-1]
    clone_out = util.sh_util(f'sparse_clone "https://github.com/googleapis/google-cloud-java.git" "{library} google-cloud-pom-parent google-cloud-jar-parent versions.txt .github"', cwd=output_folder)
    print(clone_out)
    versions_file = f'{output_folder}/google-cloud-java/versions.txt'
  else:
    print('this is a HW library')
    clone_out = util.sh_util(f'git clone "https://github.com/googleapis/{destination_path}.git"', cwd=output_folder)
    print(clone_out)
    versions_file = f'{output_folder}/{destination_path}/versions.txt'

  # we use the whole config yaml but filter it to work with a single library
  target_library = next(library for library in config.libraries if library.api_shortname == api_shortname)
  if target_library is None:
    raise ValueError(f'{api_shortname} not found in configuration yaml')

  owlbot_cli_source_folder = util.sh_util('mktemp -d')
  for gapic in target_library.GAPICs:
    print(f'query: {query}')
    effective_arguments = list(base_arguments)
    effective_arguments += util.create_argument('proto_path', gapic)

    print(f'Generating library from {proto_path} to {destination_path}...')
    with subprocess.Popen([f'{script_dir}/generate_library.sh', *arguments],
      stdout=subprocess.PIPE, stderr=subprocess.STDOUT) as generation_process:
      for line in generation_process.stdout:
        print(line.decode(), end='', flush=True)
    print('Generate library finished')

    if enable_postprocessing:
      util.sh_util(f'build_owlbot_cli_source_folder "{output_folder}/{destination_path}"'
                   + f' "{owlbot_cli_source_folder}" "{output_folder}/{destination_path}"',
                   cwd=output_folder)

  if enable_postprocessing:
    # call postprocess library
    with subprocess.Popen([f'{script_dir}/postprocess_library.sh', f'{output_folder}/{destination_path}',
      '', versions_file, owlbot_cli_source_folder],
      stdout=subprocess.PIPE, stderr=subprocess.STDOUT) as postprocessing_process:
      for line in postprocessing_process.stdout:
        print(line.decode(), end='', flush=True)

if __name__ == "__main__":
  main()
