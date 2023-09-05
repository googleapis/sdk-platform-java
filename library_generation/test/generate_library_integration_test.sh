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

while [[ $# -gt 0 ]]
do
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

get_version_from_WORKSPACE() {
  version_key_word=$1
  workspace=$2
  delimiter=$3
  version="$(grep -m 1 "${version_key_word}"  "${workspace}" | sed 's/\"\(.*\)\".*/\1/' | cut -d "${delimiter}" -f2 | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')"
  echo "${version}"
}

sparse_clone() {
  repo_url=$1
  paths=$2
  clone_dir=$(basename "${repo_url%.*}")
  rm -rf "${clone_dir}"
  git clone -n --depth=1 --filter=tree:0 "${repo_url}"
  cd "${clone_dir}"
  git sparse-checkout set --no-cone ${paths}
  git checkout
  cd ..
}

script_dir=$(dirname "$(readlink -f "$0")")
library_generation_dir="${script_dir}"/..
cd "${library_generation_dir}"
# checkout the master branch of googleapis/google (proto files) and WORKSPACE
echo "Checking out googlapis repository..."
sparse_clone https://github.com/googleapis/googleapis.git "${proto_path} WORKSPACE google/api google/rpc google/cloud/common_resources.proto"
cd googleapis
# parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
gapic_generator_version=$(get_version_from_WORKSPACE "_gapic_generator_java_version" WORKSPACE "=")
echo "The version of gapic-generator-java is ${gapic_generator_version}."
protobuf_version=$(get_version_from_WORKSPACE "protobuf-" WORKSPACE "-")
echo "The version of protobuf is ${protobuf_version}"
grpc_version=$(get_version_from_WORKSPACE "_grpc_version" WORKSPACE "=")
echo "The version of protoc-gen-grpc-java plugin is ${gapic_generator_version}."
# parse GAPIC options from proto_path/BUILD.bazel
transport="grpc"
if grep -A 15 "java_gapic_library(" "${proto_path}/BUILD.bazel" | grep -q "grpc+rest"; then
  transport="grpc+rest"
fi
rest_numeric_enums="true"
if grep -A 15 "java_gapic_library(" "${proto_path}/BUILD.bazel" | grep -q "rest_numeric_enums = False"; then
  rest_numeric_enums="false"
fi
include_samples="false"
if grep -A 15 "java_gapic_assembly_gradle_pkg(" "${proto_path}/BUILD.bazel" | grep -q "include_samples = True"; then
  include_samples="true"
fi
echo "GAPIC options are transport=${transport}, rest_numeric_enums=${rest_numeric_enums}, include_samples=${include_samples}."
os_architecture="linux-x86_64"
if [[ "$os_type" == *"macos"* ]]; then
 os_architecture="osx-x86_64"
fi
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
