#!/usr/bin/env bash

set -xeo pipefail

# This script is used to test the result of `generate_library.sh` against generated
# source code in google-cloud-java repository.
# Specifically, this script will do
# 1. checkout the master branch of googleapis/google and WORKSPACE
# 2. parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
# 3. generate a library with given proto_path and destination_path by invoking
#    `generate_library.sh`. GAPIC options to generate a library will be parsed
#    from proto_path/BUILD.bazel.
# 4. checkout the master branch google-cloud-java repository and compare the result.

# defaults
googleapis_gen_url="git@github.com:googleapis/googleapis-gen.git"
script_dir=$(dirname "$(readlink -f "$0")")
source "${script_dir}/../utilities.sh"
library_generation_dir="${script_dir}"/..
output_folder="$(get_output_folder)"

while [[ $# -gt 0 ]]; do
key="$1"
case $key in
  -p|--proto_path)
    proto_path="$2"
    shift
    ;;
  -d|--destination_path)
    destination_path="$2"
    shift
    ;;
  -m|--monorepo_folder)
    monorepo_folder="$2"
    shift
    ;;
  -s|--owlbot_sha)
    owlbot_sha="$2"
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
sparse_clone https://github.com/googleapis/googleapis.git "${proto_path} WORKSPACE google/api google/type google/rpc google/longrunning google/cloud/common_resources.proto google/iam/v1 google/cloud/location"
pushd googleapis
cp -r google "${output_folder}"
# parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
gapic_generator_version=$(get_version_from_WORKSPACE "_gapic_generator_java_version" WORKSPACE "=")
echo "The version of gapic-generator-java is ${gapic_generator_version}."
protobuf_version=$(get_version_from_WORKSPACE "protobuf-" WORKSPACE "-")
echo "The version of protobuf is ${protobuf_version}"
grpc_version=$(get_version_from_WORKSPACE "_grpc_version" WORKSPACE "=")
echo "The version of protoc-gen-grpc-java plugin is ${gapic_generator_version}."
# parse GAPIC options from proto_path/BUILD.bazel
proto_build_file_path="${proto_path}/BUILD.bazel"
transport=$(get_transport_from_BUILD "${proto_build_file_path}")
rest_numeric_enums=$(get_rest_numeric_enums_from_BUILD "${proto_build_file_path}")
include_samples=$(get_include_samples_from_BUILD "${proto_build_file_path}")
echo "GAPIC options are transport=${transport}, rest_numeric_enums=${rest_numeric_enums}, include_samples=${include_samples}."
# clone monorepo
if [ ! -d "${output_folder}/google-cloud-java" ];
then
  pushd "${output_folder}"
  sparse_clone "https://github.com/googleapis/google-cloud-java.git" "${monorepo_folder} google-cloud-pom-parent google-cloud-jar-parent versions.txt .github"
  popd
fi
target_folder="${output_folder}/google-cloud-java/${monorepo_folder}"
repo_metadata_json_path="${target_folder}/.repo-metadata.json"
# generate GAPIC client library
popd
popd
echo "Generating library from ${proto_path}, to ${destination_path}..."
"${library_generation_dir}"/generate_library.sh \
-p "${proto_path}" \
-d "${destination_path}" \
--gapic_generator_version "${gapic_generator_version}" \
--protobuf_version "${protobuf_version}" \
--grpc_version "${grpc_version}" \
--transport "${transport}" \
--rest_numeric_enums "${rest_numeric_enums}" \
--enable_postprocessing "true" \
--repo_metadata_json_path "${repo_metadata_json_path}" \
--owlbot_sha "${owlbot_sha}" \
--include_samples "${include_samples}"

echo "Generate library finished."
echo "Compare generation result..."
pushd "${output_folder}"

RESULT=0
diff -r "google-cloud-java/${monorepo_folder}" "${destination_path}/workspace" \
  -x "*gradle*" \
  -x "README.md" \
  -x "CHANGELOG.md" \
  -x ".OwlBot.yaml" \
  || RESULT=$?
if [ "${RESULT}" == 0 ] ; then
 echo "SUCCESS: Comparison finished, no difference is found."
else
  echo "FAILURE: Differences found."
fi
# clean up
cd "${script_dir}"
rm -rf WORKSPACE googleapis-gen
exit "${RESULT}"

popd # output_folder

exit ${RESULT}
