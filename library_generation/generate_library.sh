#!/usr/bin/env bash

set -eo pipefail
set -x

# parse input parameters
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
  --os_architecture)
    os_architecture="$2"
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
# source utility functions
source "${script_dir}"/utilities.sh

if [ -z "${protobuf_version}" ]; then
  protobuf_version=$(get_protobuf_version "${gapic_generator_version}")
fi

if [ -z "${grpc_version}" ]; then
  grpc_version=$(get_grpc_version "${gapic_generator_version}")
fi

if [ -z "${transport}" ]; then
  transport="grpc"
fi

if [ -z "${rest_numeric_enums}" ]; then
  rest_numeric_enums="true"
fi

if [ -z "${include_samples}" ]; then
  include_samples="true"
fi

if [ -z "${os_architecture}" ]; then
  os_architecture=$(detect_os_architecture)
fi

mkdir -p "${destination_path}"
##################### Section 0 #####################
# prepare tooling
#####################################################
# the order of services entries in gapic_metadata.json is relevant to the
# order of proto file, sort the proto files with respect to their name to
# get a fixed order.
proto_files=$(find "${proto_path}" -type f  -name "*.proto" | sort)
folder_name=$(extract_folder_name "${destination_path}")
# download gapic-generator-java, protobuf and grpc plugin.
download_tools "${gapic_generator_version}" "${protobuf_version}" "${grpc_version}" "${os_architecture}"
##################### Section 1 #####################
# generate grpc-*/
#####################################################
if [[ ! "${transport}" == "rest" ]]; then
  # do not need to generate grpc-* if the transport is `rest`.
  "${protoc_path}"/protoc "--plugin=protoc-gen-rpc-plugin=protoc-gen-grpc-java-${grpc_version}-${os_architecture}.exe" \
  "--rpc-plugin_out=:${destination_path}/java_grpc.jar" \
  ${proto_files} # Do not quote because this variable should not be treated as one long string.
  # unzip java_grpc.jar to grpc-*/src/main/java
  unzip_src_files "grpc"
  # remove empty files in grpc-*/src/main/java
  remove_empty_files "grpc"
  # remove grpc version in *ServiceGrpc.java file so the content is identical with bazel build.
  remove_grpc_version
fi
###################### Section 2 #####################
## generate gapic-*/, part of proto-*/, samples/
######################################################
"$protoc_path"/protoc --experimental_allow_proto3_optional \
"--plugin=protoc-gen-java_gapic=${script_dir}/gapic-generator-java-wrapper" \
"--java_gapic_out=metadata:${destination_path}/java_gapic_srcjar_raw.srcjar.zip" \
"--java_gapic_opt=$(get_gapic_opts)" \
${proto_files} $(search_additional_protos)

unzip -o -q "${destination_path}/java_gapic_srcjar_raw.srcjar.zip" -d "${destination_path}"
# Sync'\''d to the output file name in Writer.java.
unzip -o -q "${destination_path}/temp-codegen.srcjar" -d "${destination_path}/java_gapic_srcjar"
# Resource name source files.
proto_dir=${destination_path}/java_gapic_srcjar/proto/src/main/java
if [ ! -d "${proto_dir}" ]; then
  # Some APIs don'\''t have resource name helpers, like BigQuery v2.
  # Create an empty file so we can finish building. Gating the resource name rule definition
  # on file existences go against Bazel'\''s design patterns, so we'\''ll simply delete all empty
  # files during the final packaging process (see java_gapic_pkg.bzl)
  mkdir -p "${proto_dir}"
  touch "${proto_dir}"/PlaceholderFile.java
fi

# move java_gapic_srcjar/src/main to gapic-*/src.
mv_src_files "gapic" "main"
# remove empty files in gapic-*/src/main/java
remove_empty_files "gapic"
# move java_gapic_srcjar/src/test to gapic-*/src
mv_src_files "gapic" "test"
if [ "${include_samples}" == "true" ]; then
  # move java_gapic_srcjar/samples/snippets to samples/snippets
  mv_src_files "samples" "main"
fi
##################### Section 3 #####################
# generate proto-*/
#####################################################
"$protoc_path"/protoc "--java_out=${destination_path}/java_proto.jar" ${proto_files}
# move java_gapic_srcjar/proto/src/main/java (generated resource name helper class)
# to proto-*/src/main
mv_src_files "proto" "main"
# unzip java_proto.jar to proto-*/src/main/java
unzip_src_files "proto"
# remove empty files in proto-*/src/main/java
remove_empty_files "proto"
# copy proto files to proto-*/src/main/proto
for proto_src in ${proto_files}; do
  mkdir -p "${destination_path}/proto-${folder_name}/src/main/proto"
  rsync -R "${proto_src}" "${destination_path}/proto-${folder_name}/src/main/proto"
done
##################### Section 4 #####################
# rm tar files
#####################################################
cd "${destination_path}"
rm -rf java_gapic_srcjar java_gapic_srcjar_raw.srcjar.zip java_grpc.jar java_proto.jar temp-codegen.srcjar
set +x
