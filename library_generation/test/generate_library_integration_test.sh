#!/usr/bin/env bash

set -xeo pipefail

# This script is used to test the result of `generate_library.sh` against generated
# source code in googleapis-gen repository.
# Specifically, this script will do
# 1. checkout the master branch of googleapis/google and WORKSPACE
# 2. parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
# 3. generate a library with given proto_path and destination_path by invoking
#    `generate_library.sh`. GAPIC options to generate a library will be parsed
#    from proto_path/BUILD.bazel.
# 4. checkout the master branch googleapis-gen repository and compare the result.

# defaults
googleapis_gen_url="git@github.com:googleapis/googleapis-gen.git"

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
  --googleapis_gen_url)
    googleapis_gen_url="$2"
    shift
    ;;
  --os_type)
    os_type="$2"
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
cd "${library_generation_dir}"
# checkout the master branch of googleapis/google (proto files) and WORKSPACE
echo "Checking out googlapis repository..."
sparse_clone https://github.com/googleapis/googleapis.git "${proto_path} WORKSPACE google/api google/cloud/location google/longrunning google/iam/v1 google/rpc google/type google/cloud/common_resources.proto google/cloud/extended_operations.proto"
cd googleapis
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
os_architecture=$(get_os_architecture)
echo "OS Architecture is ${os_architecture}."
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
--include_samples "${include_samples}" \
--os_architecture "${os_architecture}"

echo "Generate library finished."
echo "Checking out googleapis-gen repository..."

sparse_clone "${googleapis_gen_url}" "${proto_path}"

echo "Compare generation result..."
RESULT=0
diff -r "googleapis-gen/${proto_path}/${destination_path}" "${destination_path}" -x "*gradle*" || RESULT=$?

if [ ${RESULT} == 0 ] ; then
  echo "SUCCESS: Comparison finished, no difference is found."
else
  echo "FAILURE: Differences found."
fi

cd ..
rm -rf googleapis

exit ${RESULT}
