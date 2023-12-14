#!/bin/bash
# This script allows generation of libraries that are composed of more than one
# service version. It is achieved by calling `generate_library.sh` without
# postprocessing for all service versions and then calling
# postprocess_library.sh at the end, once all libraries are ready
#
# Arguments
# --proto_path_list: comma separated list of proto paths to be combined
# --versions_file: list of versions to be applied to the pom.xml and readmes
#
# Note: googleapis repo is found in https://github.com/googleapis/googleapis
#
# Prerequisites
# - Needs an `output` folder at the location of the calling shell
# - the `output` folder needs to have the following dirs/files. If any of them
#   is not found, then googleapis will be downloaded and "unpacked"
#   - A "google" folder found in the googleapis repository
#   - A "grafeas" folder found in the googleapis repository
#   - A "WORKSPACE" file belonging to googleapis

set -xeo pipefail


script_dir=$(dirname "$(readlink -f "$0")")
library_generation_dir="${script_dir}"
source "${script_dir}/utilities.sh"
output_folder="$(pwd)/output"

while [[ $# -gt 0 ]]; do
key="$1"
case $key in
  -p|--proto_path_list)
    proto_path_list="$2"
    shift
    ;;
  -r|--repository_path)
    repository_path="$2"
    shift
    ;;
  -v|--versions_file)
    versions_file="$2"
    shift
    ;;
  -p|--final_postprocessing)
    final_postprocessing="$2"
    shift
    ;;
  *)
    echo "Invalid option: [$1]"
    exit 1
    ;;
esac
shift # past argument or value
done

mkdir -p "${output_folder}"
pushd "${output_folder}"
if [[ ! -f "WORKSPACE" ]] || [[ ! -d "google" ]] || [[ ! -d "grafeas" ]]; then
  echo "necessary files/folders from googleapis not found in ${output_folder}"
  echo "will now manually download googleapis"
  download_googleapis_files_and_folders "${output_folder}"
fi
gapic_generator_version=$(get_version_from_WORKSPACE "_gapic_generator_java_version" WORKSPACE "=")
protobuf_version=$(get_version_from_WORKSPACE "protobuf-" WORKSPACE "-")
owlbot_cli_source_folder=$(mktemp -d)
popd # output_folder
IFS=, # Set IFS to comma
for proto_path in $variable; do
  proto_path=$(echo "$line" | cut -d " " -f 1)
  is_monorepo="false"
  # parse destination_path
  pushd "${output_folder}"
  destination_path=$(compute_destination_path "${proto_path}" "${output_folder}")
  # parse GAPIC options from proto_path/BUILD.bazel
  proto_build_file_path="${proto_path}/BUILD.bazel"
  if [ ! -f "${proto_build_file_path}" ]; then
    echo "provided googleapis 'google' folder does not contain a BUILD.bazel file expected"
    echo "to be in ${proto_path}"
    exit 1
  fi
  proto_only=$(get_proto_only_from_BUILD "${proto_build_file_path}")
  gapic_additional_protos=$(get_gapic_additional_protos_from_BUILD "${proto_build_file_path}")
  transport=$(get_transport_from_BUILD "${proto_build_file_path}")
  rest_numeric_enums=$(get_rest_numeric_enums_from_BUILD "${proto_build_file_path}")
  gapic_yaml=$(get_gapic_yaml_from_BUILD "${proto_build_file_path}")
  service_config=$(get_service_config_from_BUILD "${proto_build_file_path}")
  service_yaml=$(get_service_yaml_from_BUILD "${proto_build_file_path}")
  include_samples=$(get_include_samples_from_BUILD "${proto_build_file_path}")
  destination_path=$(mktemp -d)

  popd # output_folder
  # generate GAPIC client library
  echo "Generating library from ${proto_path}, to ${destination_path}..."
  "${library_generation_dir}"/generate_library.sh \
    -p "${proto_path}" \
    -d "${destination_path}" \
    --gapic_generator_version "${gapic_generator_version}" \
    --protobuf_version "${protobuf_version}" \
    --proto_only "${proto_only}" \
    --gapic_additional_protos "${gapic_additional_protos}" \
    --transport "${transport}" \
    --rest_numeric_enums "${rest_numeric_enums}" \
    --gapic_yaml "${gapic_yaml}" \
    --service_config "${service_config}" \
    --service_yaml "${service_yaml}" \
    --include_samples "${include_samples}" \
    --enable_postprocessing "false" \
    --versions_file "${output_folder}/google-cloud-java/versions.txt"
  pushd "${output_folder}"
  echo "${proto_path} ${generation_duration_seconds}" >> generation_times

  echo "Generate library finished."

  build_owlbot_cli_source_folder "${owlbot_cli_source_folder}" "${destination_path}"


  popd # output_folder
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

"${library_generation_dir}"/postprocess_library.sh \
  "${output_folder}/${repository_path}" \
  "" \
  "${versions_file}" \
  "${owlbot_cli_source_folder}"

popd # output_folder
