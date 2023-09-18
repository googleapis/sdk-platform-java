#!/usr/bin/env bash

set -xeo pipefail

# This script is used to test the result of `generate_library.sh` against generated
# source code in googleapis-gen repository.
# Specifically, this script will do
# 1. checkout the master branch of googleapis/google and WORKSPACE
# 2. parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
# 3. generate a library with proto_path and destination_path in a proto_path
#    list by invoking `generate_library.sh`. GAPIC options to generate a library
#    will be parsed from proto_path/BUILD.bazel.
# 4. checkout the master branch googleapis-gen repository and compare the result.

# defaults
googleapis_gen_url="git@github.com:googleapis/googleapis-gen.git"
script_dir=$(dirname "$(readlink -f "$0")")
proto_path_list="${script_dir}/resources/proto_path_list.txt"
source "${script_dir}/../utilities.sh"
library_generation_dir="${script_dir}"/..
output_folder="$(get_output_folder)"

while [[ $# -gt 0 ]]; do
key="$1"
case $key in
  --proto_path_list)
    proto_path_list="$2"
    shift
    ;;
  --googleapis_gen_url)
    googleapis_gen_url="$2"
    shift
    ;;
  *)
    echo "Invalid option: [$1]"
    exit 1
    ;;
esac
shift # past argument or value
done

script_dir=$(dirname "$(readlink -f "$0")")
source "${script_dir}/../utilities.sh"
library_generation_dir="${script_dir}"/..
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
grpc_version=$(get_version_from_WORKSPACE "_grpc_version" WORKSPACE "=")
echo "The version of protoc-gen-grpc-java plugin is ${gapic_generator_version}."
popd # googleapis
popd # output_folder

grep -v '^ *#' < "${proto_path_list}" | while IFS= read -r line; do
  proto_path=$(echo "$line" | cut -d " " -f 1)
  destination_path=$(echo "$line" | cut -d " " -f 2)
  # parse GAPIC options from proto_path/BUILD.bazel
  pushd "${output_folder}"
  proto_build_file_path="${proto_path}/BUILD.bazel"
  transport=$(get_transport_from_BUILD "${proto_build_file_path}")
  rest_numeric_enums=$(get_rest_numeric_enums_from_BUILD "${proto_build_file_path}")
  include_samples=$(get_include_samples_from_BUILD "${proto_build_file_path}")
  popd # output_folder
  echo "GAPIC options are transport=${transport}, rest_numeric_enums=${rest_numeric_enums}, include_samples=${include_samples}."
  # generate GAPIC client library
  echo "Generating library from ${proto_path}, to ${destination_path}..."
  "${library_generation_dir}"/generate_library.sh \
  -p "${proto_path}" \
  -d "${destination_path}" \
  --gapic_generator_version "${gapic_generator_version}" \
  --protobuf_version "${protobuf_version}" \
  --grpc_version "${grpc_version}" \
  --transport "${transport}" \
  --rest_numeric_enums "${rest_numeric_enums}" \
  --include_samples "${include_samples}"
  echo "Generate library finished."
  echo "Checking out googleapis-gen repository..."

  echo "Compare generation result..."
  pushd "${output_folder}"
  sparse_clone "${googleapis_gen_url}" "${proto_path}/${destination_path}"
  RESULT=0
  # include gapic_metadata.json and package-info.java after
  # resolving https://github.com/googleapis/sdk-platform-java/issues/1986
  diff -r "googleapis-gen/${proto_path}/${destination_path}" "${output_folder}/${destination_path}" -x "*gradle*" -x "gapic_metadata.json" -x "package-info.java" || RESULT=$?

  if [ ${RESULT} == 0 ] ; then
    echo "SUCCESS: Comparison finished, no difference is found."
  else
    echo "FAILURE: Differences found in proto path: ${proto_path}."
    exit "${RESULT}"
  fi
  popd # output_folder
done

rm -rf "${output_folder}"
