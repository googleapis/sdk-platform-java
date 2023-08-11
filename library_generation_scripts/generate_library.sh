#!/usr/bin/env bash

set -xe

proto_path=$1
destination_path=$2
gapic_generator_version=$3
protobuf_version=$4
grpc_version=$5
transport=$6 # grpc+rest or grpc
rest_numeric_enums=$7 # true or false
include_samples=$8 # true or false
if [ -z "${include_samples}" ]; then
  include_samples="true"
fi

working_directory=$(dirname "$(readlink -f "$0")")
library_gen_out="$working_directory"/../library_gen_out
mkdir -p "$library_gen_out/$destination_path"
repo_root="${library_gen_out}"/..

##################### Section 0 #####################
# prepare tooling
#####################################################
cd "${repo_root}"
# the order of services entries in gapic_metadata.json is relevant to the
# order of proto file, sort the proto files with respect to their name to
# get a fixed order.
proto_files=$(find "$proto_path" -type f  -name "*.proto" | sort)
# pull proto files and protoc from protobuf repository
# maven central doesn't have proto files
protoc_path=$library_gen_out/protobuf/bin
cd "$library_gen_out"
if [ ! -d protobuf ]; then
  curl -LJ -o protobuf.zip https://github.com/protocolbuffers/protobuf/releases/download/v"$protobuf_version"/protoc-"$protobuf_version"-linux-x86_64.zip
  unzip -o -q protobuf.zip -d protobuf/
  cp -r protobuf/include/google "$repo_root"/googleapis
  echo "protoc version: $("$protoc_path"/protoc --version)"
fi
# pull protoc-gen-grpc-java plugin from maven central
cd "$library_gen_out"
if [ ! -f protoc-gen-grpc-java ]; then
  curl -LJ -o protoc-gen-grpc-java https://repo1.maven.org/maven2/io/grpc/protoc-gen-grpc-java/"$grpc_version"/protoc-gen-grpc-java-"$grpc_version"-linux-x86_64.exe
  chmod +x protoc-gen-grpc-java
fi
# gapic-generator-java
if [ ! -f gapic-generator-java.jar ]; then
  curl -LJ -o gapic-generator-java.jar https://repo1.maven.org/maven2/com/google/api/gapic-generator-java/"$gapic_generator_version"/gapic-generator-java-"$gapic_generator_version".jar
fi

cd "$working_directory"
source ./utilities.sh

folder_name=$(extract_folder_name "$destination_path")
##################### Section 1 #####################
# generate grpc-*/
#####################################################
cd "$repo_root"
"$protoc_path"/protoc "--plugin=protoc-gen-rpc-plugin=$library_gen_out/protoc-gen-grpc-java" \
"--rpc-plugin_out=:$library_gen_out/$destination_path/java_grpc.jar" \
$proto_files

unzip_src_files "grpc"
remove_empty_files "grpc"
###################### Section 2 #####################
## generate gapic-*/, proto-*/, samples/
######################################################
cd "$repo_root"
"$protoc_path"/protoc --experimental_allow_proto3_optional \
"--plugin=protoc-gen-java_gapic=$working_directory/gapic-generator-java-wrapper" \
"--java_gapic_out=metadata:$library_gen_out/$destination_path/java_gapic_srcjar_raw.srcjar.zip" \
"--java_gapic_opt=$(get_gapic_opts)" \
${proto_files} $(search_additional_protos)

unzip -o -q "$library_gen_out/$destination_path/java_gapic_srcjar_raw.srcjar.zip" -d "$library_gen_out/$destination_path"
# Sync'\''d to the output file name in Writer.java.
unzip -o -q "$library_gen_out/$destination_path/temp-codegen.srcjar" -d "$library_gen_out/$destination_path/java_gapic_srcjar"
# Resource name source files.
proto_dir=$library_gen_out/$destination_path/java_gapic_srcjar/proto/src/main/java
if [ ! -d "$proto_dir" ]
then
  # Some APIs don'\''t have resource name helpers, like BigQuery v2.
  # Create an empty file so we can finish building. Gating the resource name rule definition
  # on file existences go against Bazel'\''s design patterns, so we'\''ll simply delete all empty
  # files during the final packaging process (see java_gapic_pkg.bzl)
  mkdir -p "$proto_dir"
  touch "$proto_dir"/PlaceholderFile.java
fi

cd "$library_gen_out"
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
cd "$repo_root"
"$protoc_path"/protoc "--java_out=$library_gen_out/$destination_path/java_proto.jar" $proto_files
mv_src_files "proto" "main"
unzip_src_files "proto"
remove_empty_files "proto"

for proto_src in $proto_files; do
    mkdir -p "$library_gen_out/$destination_path/proto-$folder_name/src/main/proto"
    cp -f --parents "$proto_src" "$library_gen_out/$destination_path/proto-$folder_name/src/main/proto"
done
##################### Section 4 #####################
# rm tar files
#####################################################
cd "$library_gen_out/$destination_path"
rm -rf java_gapic_srcjar java_gapic_srcjar_raw.srcjar.zip java_grpc.jar java_proto.jar temp-codegen.srcjar