#!/usr/bin/env bash

set -xeo pipefail

# This script is used to test the result of `generate_library.sh` against generated
# source code in the specified repository.
# Specifically, this script will do
# 1. checkout the master branch of googleapis/google and WORKSPACE
# 2. parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
# 3. generate a library with proto_path and destination_path in a proto_path
#    list by invoking `generate_library.sh`. GAPIC options to generate a library
#    will be parsed from proto_path/BUILD.bazel.
# 4. depending on whether postprocessing is enabled,
#   4.1 checkout the master branch of googleapis-gen repository and compare the result, or
#   4.2 checkout the master branch of google-cloud-java or HW library repository and compare the result

# defaults
googleapis_gen_url="git@github.com:googleapis/googleapis-gen.git"
enable_postprocessing="true"

script_dir=$(dirname "$(readlink -f "$0")")
proto_path_list="${script_dir}/resources/proto_path_list.txt"
library_generation_dir="${script_dir}"/..
source "${script_dir}/test_utilities.sh"
source "${script_dir}/../utilities.sh"
output_folder="$(pwd)/output"

while [[ $# -gt 0 ]]; do
key="$1"
case $key in
  -p|--proto_path_list)
    proto_path_list="$2"
    shift
    ;;
  -e|--enable_postprocessing)
    enable_postprocessing="$2"
    shift
    ;;
  -g|--googleapis_gen_url)
    googleapis_gen_url="$2"
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
# checkout the master branch of googleapis/google (proto files) and WORKSPACE
echo "Checking out googlapis repository..."
# sparse_clone will remove folder contents first, so we have to checkout googleapis
# only once.
sparse_clone https://github.com/googleapis/googleapis.git "google grafeas WORKSPACE"
pushd googleapis
cp -r google "${output_folder}"
cp -r grafeas "${output_folder}"
# parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
gapic_generator_version=$(get_version_from_WORKSPACE "_gapic_generator_java_version" WORKSPACE "=")
echo "The version of gapic-generator-java is ${gapic_generator_version}."
protobuf_version=$(get_version_from_WORKSPACE "protobuf-" WORKSPACE "-")
echo "The version of protobuf is ${protobuf_version}"
popd # googleapis
popd # output_folder
if [ -f "${output_folder}/generation_times" ];then
  rm "${output_folder}/generation_times"
fi
if [ -z "${versions_file}" ]; then
  # google-cloud-java will be downloaded before each call of
  # `generate_library.sh`
  versions_file="${output_folder}/google-cloud-java/versions.txt"
fi

grep -v '^ *#' < "${proto_path_list}" | while IFS= read -r line; do
  proto_path=$(echo "$line" | cut -d " " -f 1)
  repository_path=$(echo "$line" | cut -d " " -f 2)
  skip_postprocessing=$(echo "$line" | cut -d " " -f 3)
  # parse destination_path
  pushd "${output_folder}"
  echo "Checking out googleapis-gen repository..."
  sparse_clone "${googleapis_gen_url}" "${proto_path}"
  destination_path=$(compute_destination_path "${proto_path}" "${output_folder}")
  # parse GAPIC options from proto_path/BUILD.bazel
  proto_build_file_path="${proto_path}/BUILD.bazel"
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
  if [ "${skip_postprocessing}" == "true" ]; then
    echo 'this library is not intended for postprocessing test'
    popd # output folder
    continue
  else
    echo 'this is a monorepo library'
    sparse_clone "https://github.com/googleapis/google-cloud-java.git" "${repository_path} google-cloud-pom-parent google-cloud-jar-parent versions.txt .github"

    # compute path from output_folder to source of truth library location
    # (e.g. google-cloud-java/java-compute)
    repository_path="google-cloud-java/${repository_path}"
    target_folder="${output_folder}/${repository_path}"
    popd # output_folder
  fi
  # generate GAPIC client library
  echo "Generating library from ${proto_path}, to ${destination_path}..."
  generation_start=$(date "+%s")
  if [ "${enable_postprocessing}" == "true" ]; then
    if [[ "${repository_path}" == "null" ]]; then
      # we need a repository to compare the generated results with. Skip this
      # library
      continue
    fi
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
      --enable_postprocessing "true" \
      --versions_file "${output_folder}/google-cloud-java/versions.txt"
  else
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
      --enable_postprocessing "false"
  fi
  generation_end=$(date "+%s")
  # some generations are less than 1 second (0 produces exit code 1 in `expr`)
  generation_duration_seconds=$(expr "${generation_end}" - "${generation_start}" || true)
  echo "Generation time for ${repository_path} was ${generation_duration_seconds} seconds."
  pushd "${output_folder}"
  echo "${proto_path} ${generation_duration_seconds}" >> generation_times

  echo "Generate library finished."
  echo "Compare generation result..."
  if [ $enable_postprocessing == "true" ]; then
    echo "Checking out repository..."
    pushd "${target_folder}"
    source_diff_result=0
    git diff \
      --ignore-space-at-eol \
      -r \
      --exit-code \
      -- \
      ':!*pom.xml' \
      ':!*README.md' \
      ':!*package-info.java' \
      || source_diff_result=$?

    pom_diff_result=$(compare_poms "${target_folder}")
    popd # target_folder
    if [[ ${source_diff_result} == 0 ]] && [[ ${pom_diff_result} == 0 ]] ; then
      echo "SUCCESS: Comparison finished, no difference is found."
      # Delete google-cloud-java to allow a sparse clone of the next library
      rm -rdf google-cloud-java
    elif [ ${source_diff_result} != 0 ]; then
      echo "FAILURE: Differences found in proto path: ${proto_path}."
      exit "${source_diff_result}"
    elif [ ${pom_diff_result} != 0 ]; then
      echo "FAILURE: Differences found in generated poms"
      exit "${pom_diff_result}"
    fi
  elif [ "${enable_postprocessing}" == "false" ]; then
    # include gapic_metadata.json and package-info.java after
    # resolving https://github.com/googleapis/sdk-platform-java/issues/1986
    source_diff_result=0
    diff --strip-trailing-cr -r "googleapis-gen/${proto_path}/${destination_path}" "${output_folder}/${destination_path}" \
      -x "*gradle*" \
      -x "gapic_metadata.json" \
      -x "package-info.java" || source_diff_result=$?
    if [ ${source_diff_result} == 0 ] ; then
      echo "SUCCESS: Comparison finished, no difference is found."
    else
      echo "FAILURE: Differences found in proto path: ${proto_path}." 
      exit "${source_diff_result}"
    fi
  fi

  popd # output_folder
done
echo "ALL TESTS SUCCEEDED"
echo "generation times in seconds (does not consider repo checkout):"
cat "${output_folder}/generation_times"
