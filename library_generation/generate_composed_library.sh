#!/bin/bash
# This script allows generation of libraries that are composed of more than one
# service version. It is achieved by calling `generate_library.sh` without
# postprocessing for all service versions and then calling
# postprocess_library.sh at the end, once all libraries are ready
#
# Arguments
# --generation_queries: a single string of key-value groups separated by a
# pipe |. They key-value groups are in the form of `key=value` and will
# be converted to an argument to generate_library.sh (`--key value`).
#   example: "key1=value1 key2=value2,,,key1=value3 key2=value4"
#   In this case, generate_library.sh will be called once with value1 and value2
#   and once with value3 and value4.
# --versions_file: list of versions to be applied to the pom.xml and readmes
#
# Note: googleapis repo is found in https://github.com/googleapis/googleapis
#
# Prerequisites
# - Needs an `output` folder at the location of the calling shell
# - the `output` folder needs to have the following dirs/files. 
#   - A "google" folder found in the googleapis repository
#   - A "grafeas" folder found in the googleapis repository
#   - A "WORKSPACE" file belonging to googleapis

set -xeo pipefail


script_dir=$(dirname "$(readlink -f "$0")")
library_generation_dir="${script_dir}"
source "${script_dir}/utilities.sh"
output_folder=$(get_output_folder)
mkdir -p "${output_folder}"

while [[ $# -gt 0 ]]; do
key="$1"
case $key in
  -g|--generation_queries)
    generation_queries="$2"
    ;;
  -r|--repository_path)
    repository_path="$2"
    ;;
  -v|--versions_file)
    versions_file="$2"
    ;;
  -p|--enable_postprocessing)
    enable_postprocessing="$2"
    ;;
  *)
    echo "Invalid option: [$1]"
    exit 1
    ;;
esac
shift
shift # past argument or value
done


pushd "${output_folder}"
if [[ "${repository_path}" == google-cloud-java/* ]]; then
  echo 'this is a monorepo library'
  library=$(echo "${repository_path}" | cut -d'/' -f2)
  sparse_clone "https://github.com/googleapis/google-cloud-java.git" "${library} google-cloud-pom-parent google-cloud-jar-parent versions.txt .github"
  versions_file="${versions_file:-"${output_folder}/google-cloud-java/versions.txt"}"
else
  echo 'this is a HW library'
  git clone "https://github.com/googleapis/${repository_path}.git"
  versions_file="${versions_file:-"${output_folder}/${repository_path}/versions.txt"}"
fi

owlbot_cli_source_folder=$(mktemp -d)
popd # output_folder

IFS="|"
for query in $generation_queries; do
  pushd "${output_folder}"
  arguments=$(py_util get_generate_library_arguments "${query}")
  arguments=$(py_util add_argument "${arguments}" "versions_file" "${versions_file}")
  # we postprocess only once after all versions were processed
  arguments=$(py_util add_argument "${arguments}" "enable_postprocessing" "false")
  proto_path=$(py_util get_argument_value_from_query "${query}" "proto_path")
  destination_path=$(py_util get_argument_value_from_query "${query}" "destination_path")

  # generate GAPIC client library
  echo "Generating library from ${proto_path}, to ${destination_path}..."
  echo "${arguments}" | xargs "${library_generation_dir}/generate_library.sh"
  echo "Generate library finished."

  build_owlbot_cli_source_folder "${output_folder}/${repository_path}" "${owlbot_cli_source_folder}" "${output_folder}/${destination_path}"

  popd # output_folder
done

pushd "${output_folder}"

if [[ "${enable_postprocessing}" == "true" ]]; then
  "${library_generation_dir}"/postprocess_library.sh \
    "${output_folder}/${repository_path}" \
    "" \
    "${versions_file}" \
    "${owlbot_cli_source_folder}"
fi

popd # output_folder
