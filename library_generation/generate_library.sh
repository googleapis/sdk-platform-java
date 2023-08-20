#!/usr/bin/env bash

set -eo pipefail

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
    --gapic_generator_version)
    gapic_generator_version="$2"
    # export this variable so that it can be used in gapic-generator-java-wrapper.sh
    export gapic_generator_version
    shift
    ;;
    --protobuf_version)
    protobuf_version="$2"
    shift
    ;;
    --grpc_version)
    grpc_version="$2"
    shift
    ;;
    --transport)
    transport="$2"
    shift
    ;;
    --rest_numeric_enums)
    rest_numeric_enums="$2"
    shift
    ;;
    --include_samples)
    include_samples="$2"
    shift
    ;;
    *)
    echo "Invalid option: [$1]"
    exit 1
    ;;
esac
shift # past argument or value
done

working_directory=$(dirname "$(readlink -f "$0")")
cd "$working_directory"
source ./utilities.sh

if [ -z "$protobuf_version" ]; then
  protobuf_version=$(get_protobuf_version "$gapic_generator_version")
fi

if [ -z "$grpc_version" ]; then
  grpc_version=$(get_grpc_version "$gapic_generator_version")
fi

if [ -z "$transport" ]; then
  transport="grpc"
fi

if [ -z "$rest_numeric_enums" ]; then
  rest_numeric_enums="true"
fi

if [ -z "$include_samples" ]; then
  include_samples="true"
fi

cd "$working_directory"
mkdir -p "$destination_path"
destination_path="$working_directory/$destination_path"
##################### Section 0 #####################
# prepare tooling
#####################################################
cd "$working_directory"
# the order of services entries in gapic_metadata.json is relevant to the
# order of proto file, sort the proto files with respect to their name to
# get a fixed order.
proto_files=$(find "$proto_path" -type f  -name "*.proto" | sort)
folder_name=$(extract_folder_name "$destination_path")

download_tools "$gapic_generator_version" "$protobuf_version" "$grpc_version"
##################### Section 1 #####################
# generate grpc-*/
#####################################################
cd "$working_directory"
"$protoc_path"/protoc "--plugin=protoc-gen-rpc-plugin=$working_directory/protoc-gen-grpc-java-$grpc_version-linux-x86_64.exe" \
"--rpc-plugin_out=:$destination_path/java_grpc.jar" \
$proto_files

unzip_src_files "grpc"
remove_empty_files "grpc"
# remove grpc version in *ServiceGrpc.java file so the content is identical with bazel build.
remove_grpc_version
###################### Section 2 #####################
## generate gapic-*/, proto-*/, samples/
######################################################
cd "$working_directory"
"$protoc_path"/protoc --experimental_allow_proto3_optional \
"--plugin=protoc-gen-java_gapic=$working_directory/gapic-generator-java-wrapper" \
"--java_gapic_out=metadata:$destination_path/java_gapic_srcjar_raw.srcjar.zip" \
"--java_gapic_opt=$(get_gapic_opts)" \
${proto_files} $(search_additional_protos)

unzip -o -q "$destination_path/java_gapic_srcjar_raw.srcjar.zip" -d "$destination_path"
# Sync'\''d to the output file name in Writer.java.
unzip -o -q "$destination_path/temp-codegen.srcjar" -d "$destination_path/java_gapic_srcjar"
# Resource name source files.
proto_dir=$destination_path/java_gapic_srcjar/proto/src/main/java
if [ ! -d "$proto_dir" ]; then
  # Some APIs don'\''t have resource name helpers, like BigQuery v2.
  # Create an empty file so we can finish building. Gating the resource name rule definition
  # on file existences go against Bazel'\''s design patterns, so we'\''ll simply delete all empty
  # files during the final packaging process (see java_gapic_pkg.bzl)
  mkdir -p "$proto_dir"
  touch "$proto_dir"/PlaceholderFile.java
fi

cd "$working_directory"
# Main source files.
mv_src_files "gapic" "main"
remove_empty_files "gapic"
# Test source files.
mv_src_files "gapic" "test"
if [ "$include_samples" == "true" ]; then
  # Sample source files.
  mv_src_files "samples" "main"
fi
##################### Section 3 #####################
# generate proto-*/
#####################################################
cd "$working_directory"
"$protoc_path"/protoc "--java_out=$destination_path/java_proto.jar" $proto_files
mv_src_files "proto" "main"
unzip_src_files "proto"
remove_empty_files "proto"

for proto_src in $proto_files; do
    mkdir -p "$destination_path/proto-$folder_name/src/main/proto"
    cp -f --parents "$proto_src" "$destination_path/proto-$folder_name/src/main/proto"
done
##################### Section 4 #####################
# rm tar files
#####################################################
cd "$destination_path"
rm -rf java_gapic_srcjar java_gapic_srcjar_raw.srcjar.zip java_grpc.jar java_proto.jar temp-codegen.srcjar