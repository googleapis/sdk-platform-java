#!/usr/bin/env bash

set -eo pipefail

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
  --enable_postprocessing)
    enable_postprocessing="$2"
    shift
    ;;
  --repo_metadata_json_path)
    repo_metadata_json_path="$2"
    shift
    ;;
  --owlbot_sha)
    owlbot_sha="$2"
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
api_version=$(extract_api_version "${proto_path}")

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

if [ -z "$enable_postprocessing" ]; then
  enable_postprocessing="false"
fi

if [ -z "$os_architecture" ]; then
  os_architecture="linux-x86_64"
fi

destination_path="${script_dir}/$destination_path"
mkdir -p "$destination_path"
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
"${protoc_path}"/protoc "--plugin=protoc-gen-rpc-plugin=protoc-gen-grpc-java-${grpc_version}-${os_architecture}.exe" \
"--rpc-plugin_out=:${destination_path}/java_grpc.jar" \
${proto_files} # Do not quote because this variable should not be treated as one long string.
# unzip java_grpc.jar to grpc-*/src/main/java
unzip_src_files "grpc" "${api_version}"
# remove empty files in grpc-*/src/main/java
remove_empty_files "grpc" "${api_version}"
# remove grpc version in *ServiceGrpc.java file so the content is identical with bazel build.
remove_grpc_version
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
mv_src_files "gapic" "main" "${api_version}"
# remove empty files in gapic-*/src/main/java
remove_empty_files "gapic" "${api_version}"
# move java_gapic_srcjar/src/test to gapic-*/src
mv_src_files "gapic" "test" "${api_version}"
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
mv_src_files "proto" "main" "${api_version}"
# unzip java_proto.jar to proto-*/src/main/java
unzip_src_files "proto" "${api_version}"
# remove empty files in proto-*/src/main/java
remove_empty_files "proto" "${api_version}"
# copy proto files to proto-*/src/main/proto
for proto_src in ${proto_files}; do
  mkdir -p "${destination_path}/proto-${folder_name}-${api_version}/src/main/proto"
  rsync -R "${proto_src}" "${destination_path}/proto-${folder_name}-${api_version}/src/main/proto"
done
##################### Section 4 #####################
# rm tar files
#####################################################
cd "${destination_path}"
rm -rf java_gapic_srcjar java_gapic_srcjar_raw.srcjar.zip java_grpc.jar java_proto.jar temp-codegen.srcjar
##################### Section 5 #####################
# post-processing
#####################################################
exit 0
source "${script_dir}/post-processing/postprocessing_utilities.sh"
if [ "${enable_postprocessing}" != "true" ];
then
  echo "post processing is disabled"
  exit 0
elif [ -z "${repo_metadata_json_path}" ];
then
  echo "no repo_metadata.json provided. This is necessary for post-processing the generated library" >&2
  exit 1
elif [ -z "${owlbot_sha}" ];
then
  if [ ! -d "${script_dir}"/google-cloud-java ];
  then
    echo 'no owlbot_sha provided and no monorepo to infer it from. This is necessary for post-processing' >&2
    exit 1
  fi
  echo "no owlbot_sha provided. Will compute from monorepo's head"
  owlbot_sha=$(grep 'sha256' "${script_dir}/google-cloud-java/.github/.OwlBot.lock.yaml" | cut -d: -f3)
fi
workspace="${destination_path}/workspace"
mkdir -p "${workspace}"

run_owlbot_postprocessor "${workspace}" "${owlbot_sha}" "${repo_metadata_json_path}" "${include_samples}" \
  "${script_dir}" "${destination_path}"

other_post_processing_scripts "${script_dir}" "${workspace}" "${repo_metadata_json_path}"
set +x
