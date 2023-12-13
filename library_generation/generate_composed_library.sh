#!/bin/bash
# This script allows generation of libraries that are composed of more than one
# service version. It is achieved by calling `generate_library.sh` without
# postprocessing for all service versions and then calling
# postprocess_library.sh at the end, once all libraries are ready

set -xeo pipefail

# defaults
enable_postprocessing="true"

script_dir=$(dirname "$(readlink -f "$0")")
library_generation_dir="${script_dir}"/..
source "${script_dir}/utilities.sh"
output_folder="$(pwd)/output"

while [[ $# -gt 0 ]]; do
key="$1"
case $key in
  -p|--proto_path_list)
    proto_path_list="$2"
    shift
    ;;
  -v|--versions_file)
    versions_file="$2"
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
if [ ! -d "googleapis" ]; then
  echo "necessary directory 'googleapis' not found in ${output_folder}"
  echo "this folder is needed to obtain configuration from WORKSPACE"
  exit 1
fi
for folder in google grafeas; do
  if [ ! -d "${folder}" ]; then
    echo "necessary directory 'google' not found in ${output_folder}"
    echo "this folder belongs to the googleapis repository. It contains necessary proto files"
    echo "for library generation"
    exit 1
  fi
done
pushd googleapis
gapic_generator_version=$(get_version_from_WORKSPACE "_gapic_generator_java_version" WORKSPACE "=")
echo "The version of gapic-generator-java is ${gapic_generator_version}."
protobuf_version=$(get_version_from_WORKSPACE "protobuf-" WORKSPACE "-")
echo "The version of protobuf is ${protobuf_version}"
popd # googleapis
popd # output_folder

grep -v '^ *#' < "${proto_path_list}" | while IFS= read -r line; do
  proto_path=$(echo "$line" | cut -d " " -f 1)
  repository_path=$(echo "$line" | cut -d " " -f 2)
  skip_postprocessing=$(echo "$line" | cut -d " " -f 3)
  is_monorepo="false"
  if [[ "${repository_path}" == google-cloud-java/* ]]; then
    echo 'this is a monorepo library'
    is_monorepo="true"
  fi
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
  popd # output_folder
  echo "GAPIC options are
    transport=${transport},
    rest_numeric_enums=${rest_numeric_enums},
    gapic_yaml=${gapic_yaml},
    service_config=${service_config},
    service_yaml=${service_yaml},
    include_samples=${include_samples}."
  pushd "${output_folder}"
  if [[ "${is_monorepo}" == "true" ]]; then
    library=$(echo "${repository_path}" | cut -d'/' -f2)
    sparse_clone "https://github.com/googleapis/google-cloud-java.git" "${library} google-cloud-pom-parent google-cloud-jar-parent versions.txt .github"
  else
    git clone "https://github.com/googleapis/${repository_path}.git"
  fi

  target_folder="${output_folder}/${repository_path}"
  popd # output_folder
  # generate GAPIC client library
  echo "Generating library from ${proto_path}, to ${destination_path}..."
  generation_start=$(date "+%s")
  if [ "${enable_postprocessing}" == "true" ]; then
    "${library_generation_dir}"/generate_library.sh \
      -p "${proto_path}" \
      -d "${repository_path}" \
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
      --enable_postprocessing "${enable_postprocessing}" \
      --versions_file "${output_folder}/google-cloud-java/versions.txt"
  fi
  generation_end=$(date "+%s")
  # some generations are less than 1 second (0 produces exit code 1 in `expr`)
  generation_duration_seconds=$(expr "${generation_end}" - "${generation_start}" || true)
  echo "Generation time for ${repository_path} was ${generation_duration_seconds} seconds."
  pushd "${output_folder}"
  echo "${proto_path} ${generation_duration_seconds}" >> generation_times

  echo "Generate library finished."

  popd # output_folder
done
