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
  version="$(grep -m 1 "$version_key_word"  "$workspace" | sed 's/\"\(.*\)\".*/\1/' | cut -d "$delimiter" -f2 | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')"
  echo "$version"
}

working_directory=$(dirname "$(readlink -f "$0")")
cd "$working_directory"
# checkout the master branch of googleapis/google (proto files) and WORKSPACE
echo "Checking out googlapis repository..."
git clone --branch=master --depth 1 -q https://github.com/googleapis/googleapis.git
cp -r googleapis/google .
cp googleapis/WORKSPACE .
rm -rf googleapis
# parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
gapic_generator_version=$(get_version_from_WORKSPACE "_gapic_generator_java_version" WORKSPACE "=")
echo "The version of gapic-generator-java is $gapic_generator_version."
protobuf_version=$(get_version_from_WORKSPACE "protobuf-" WORKSPACE "-")
echo "The version of protobuf is $protobuf_version"
grpc_version=$(get_version_from_WORKSPACE "_grpc_version" WORKSPACE "=")
echo "The version of protoc-gen-grpc-java plugin is $gapic_generator_version."
# parse GAPIC options from proto_path/BUILD.bazel
cd $"$working_directory"
transport="grpc"
if grep -A 15 "java_gapic_library(" "$proto_path/BUILD.bazel" | grep -q "grpc+rest"; then
  transport="grpc+rest"
fi
rest_numeric_enums="true"
if grep -A 15 "java_gapic_library(" "$proto_path/BUILD.bazel" | grep -q "rest_numeric_enums = False"; then
  rest_numeric_enums="false"
fi
include_samples="false"
if grep -A 15 "java_gapic_assembly_gradle_pkg(" "$proto_path/BUILD.bazel" | grep -q "include_samples = True"; then
  include_samples="true"
fi
echo "GAPIC options are transport=$transport, rest_numeric_enums=$rest_numeric_enums, include_samples=$include_samples."
# generate GAPIC client library
echo "Generating library from $proto_path, to $destination_path..."
"$working_directory"/generate_library.sh \
-p "$proto_path" \
-d "$destination_path" \
--gapic_generator_version "$gapic_generator_version" \
--protobuf_version "$protobuf_version" \
--grpc_version "$grpc_version" \
--transport "$transport" \
--rest_numeric_enums "$rest_numeric_enums" \
--include_samples "$include_samples"

echo "Generate library finished."
echo "Checking out googleapis-gen repository..."
#git clone --branch=master --depth 1 -q "$googleapis_gen_url"
git clone --branch=master --depth 1 -q "https://github.com/googleapis/google-cloud-java"

echo "Compare generation result..."
cd "$working_directory"
diff -r "google-cloud-java/java-bigtable" "$destination_path" -x "*gradle*"
echo "Comparison finished, no difference is found."
# clean up
cd "$working_directory"
rm -rf WORKSPACE googleapis-gen "$destination_path"
