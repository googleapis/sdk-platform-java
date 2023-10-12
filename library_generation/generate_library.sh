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
  --proto_only)
    proto_only="$2"
    shift
    ;;
  --gapic_additional_protos)
    gapic_additional_protos="$2"
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
  --repository_path)
    repository_path="$2"
    shift
    ;;
  --more_versions_coming)
    more_versions_coming="$2"
    shift
    ;;
  --custom_gapic_name)
    more_versions_coming="$2"
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
output_folder="$(get_output_folder)"

if [ -z "${protobuf_version}" ]; then
  protobuf_version=$(get_protobuf_version "${gapic_generator_version}")
fi

if [ -z "${grpc_version}" ]; then
  grpc_version=$(get_grpc_version "${gapic_generator_version}")
fi

if [ -z "${proto_only}" ]; then
  proto_only="false"
fi

if [ -z "${gapic_additional_protos}" ]; then
  gapic_additional_protos="google/cloud/common_resources.proto"
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
  enable_postprocessing="true"
fi

if [ -z "${os_architecture}" ]; then
  os_architecture=$(detect_os_architecture)
fi

if [ -z "${more_versions_coming}" ]; then
  more_versions_coming="false"
fi

if [ -z "${custom_gapic_name}" ]; then
  # default to null in order to use the default name (i.e.
  # gapic-google-cloud-library-name-v1-java)
  custom_gapic_name="null"
fi


mkdir -p "${output_folder}/${destination_path}"
##################### Section 0 #####################
# prepare tooling
#####################################################
# the order of services entries in gapic_metadata.json is relevant to the
# order of proto file, sort the proto files with respect to their bytes to
# get a fixed order.
folder_name=$(extract_folder_name "${destination_path}")
pushd "${output_folder}"
proto_files=$(find ${proto_path} -type f  -name "*.proto" | LC_COLLATE=C sort)
# include or exclude certain protos in grpc plugin and gapic generator java.
case "${proto_path}" in
  "google/cloud/aiplatform/v1beta1"*)
    # this proto is excluded from //google/cloud/aiplatform/v1beta1/schema:schema_proto
    removed_proto="google/cloud/aiplatform/v1beta1/schema/io_format.proto"
    proto_files="${proto_files//${removed_proto}/}"
    ;;
  "google/cloud/filestore"*)
    # this proto is included in //google/cloud/filestore/v1:google-cloud-filestore-v1-java
    # and //google/cloud/filestore/v1beta1:google-cloud-filestore-v1-java
    proto_files="${proto_files} google/cloud/common/operation_metadata.proto"
    ;;
  "google/cloud/oslogin"*)
    # this proto is included in //google/cloud/oslogin/v1:google-cloud-oslogin-v1-java
    # and //google/cloud/oslogin/v1beta1:google-cloud-oslogin-v1-java
    proto_files="${proto_files} google/cloud/oslogin/common/common.proto"
    ;;
esac
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
  unzip_src_files "grpc" "${api_version}"
  # remove empty files in grpc-*/src/main/java
  remove_empty_files "grpc" "${api_version}"
  # remove grpc version in *ServiceGrpc.java file so the content is identical with bazel build.
  remove_grpc_version
fi
###################### Section 2 #####################
## generate gapic-*/, part of proto-*/, samples/
######################################################
if [[ "${proto_only}" == "false" ]]; then
  "$protoc_path"/protoc --experimental_allow_proto3_optional \
  "--plugin=protoc-gen-java_gapic=${script_dir}/gapic-generator-java-wrapper" \
  "--java_gapic_out=metadata:${destination_path}/java_gapic_srcjar_raw.srcjar.zip" \
  "--java_gapic_opt=$(get_gapic_opts)" \
  ${proto_files} ${gapic_additional_protos}

  unzip -o -q "${destination_path}/java_gapic_srcjar_raw.srcjar.zip" -d "${destination_path}"
  # Sync'\''d to the output file name in Writer.java.
  unzip -o -q "${destination_path}/temp-codegen.srcjar" -d "${destination_path}/java_gapic_srcjar"
  # Resource name source files.
  proto_dir=${destination_path}/java_gapic_srcjar/proto/src/main/java
  if [ ! -d "${proto_dir}" ]; then
    # Some APIs don't have resource name helpers, like BigQuery v2.
    # Create an empty file so we can finish building. Gating the resource name rule definition
    # on file existences go against Bazel's design patterns, so we'll simply delete all empty
    # files during the final packaging process (see java_gapic_pkg.bzl)
    mkdir -p "${proto_dir}"
    touch "${proto_dir}"/PlaceholderFile.java
  fi
  # move java_gapic_srcjar/src/main to gapic-*/src.
  mv_src_files "gapic" "main" "${api_version}" "${custom_gapic_name}"
  # remove empty files in gapic-*/src/main/java
  remove_empty_files "gapic" "${api_version}"
  # move java_gapic_srcjar/src/test to gapic-*/src
  mv_src_files "gapic" "test" "${api_version}" "${custom_gapic_name}"
  if [ "${include_samples}" == "true" ]; then
    # move java_gapic_srcjar/samples/snippets to samples/snippets
    mv_src_files "samples" "main"
  fi
fi
##################### Section 3 #####################
# generate proto-*/
#####################################################
# exclude certain protos to java compiler.
case "${proto_path}" in
  "google/cloud/aiplatform/v1beta1"*)
    # these protos are excluded from //google/cloud/aiplatform/v1beta1:google-cloud-aiplatform-v1beta1-java
    prefix="google/cloud/aiplatform/v1beta1/schema"
    protos="${prefix}/annotation_payload.proto ${prefix}/annotation_spec_color.proto ${prefix}/data_item_payload.proto ${prefix}/dataset_metadata.proto ${prefix}/geometry.proto"
    for removed_proto in ${protos}; do
      proto_files="${proto_files//${removed_proto}/}"
    done
    ;;
  "google/devtools/containeranalysis/v1beta1"*)
    # this proto is excluded from //google/devtools/containeranalysis/v1beta1:google-cloud-devtools-containeranalysis-v1beta1-java
    removed_proto="google/devtools/containeranalysis/v1beta1/cvss/cvss.proto"
    proto_files="${proto_files//${removed_proto}/}"
    ;;
esac
"$protoc_path"/protoc "--java_out=${destination_path}/java_proto.jar" ${proto_files}
if [[ "${proto_only}" == "false" ]]; then
  # move java_gapic_srcjar/proto/src/main/java (generated resource name helper class)
  # to proto-*/src/main
  mv_src_files "proto" "main" "${api_version}"
fi
# unzip java_proto.jar to proto-*/src/main/java
unzip_src_files "proto" "${api_version}"
# remove empty files in proto-*/src/main/java
remove_empty_files "proto" "${api_version}"
# include certain protos in generated library.
case "${proto_path}" in
  "google/cloud/aiplatform/v1beta1"*)
    prefix="google/cloud/aiplatform/v1beta1/schema"
    protos="${prefix}/annotation_payload.proto ${prefix}/annotation_spec_color.proto ${prefix}/data_item_payload.proto ${prefix}/dataset_metadata.proto ${prefix}/geometry.proto"
    for added_proto in ${protos}; do
      proto_files="${proto_files} ${added_proto}"
    done
    ;;
  "google/devtools/containeranalysis/v1beta1"*)
    proto_files="${proto_files} google/devtools/containeranalysis/v1beta1/cvss/cvss.proto"
    ;;
esac
# copy proto files to proto-*/src/main/proto
for proto_src in ${proto_files}; do
  if [[ "${proto_src}" == "google/cloud/common/operation_metadata.proto" ]]; then
    continue
  fi
  mkdir -p "${destination_path}/proto-${folder_name}-${api_version}/src/main/proto"
  rsync -R "${proto_src}" "${destination_path}/proto-${folder_name}-${api_version}/src/main/proto"
done
popd # output_folder
##################### Section 4 #####################
# rm tar files
#####################################################
pushd "${output_folder}/${destination_path}"
rm -rf java_gapic_srcjar java_gapic_srcjar_raw.srcjar.zip java_grpc.jar java_proto.jar temp-codegen.srcjar
popd # destination path
##################### Section 5 #####################
# post-processing
#####################################################
source "${script_dir}/post-processing/postprocessing_utilities.sh"
if [ "${enable_postprocessing}" != "true" ];
then
  # arrange files for non-post-processing integration test
  echo "post processing is disabled"
  pushd "${output_folder}/${destination_path}"
  mv "proto-${folder_name}-${api_version}" "proto-${folder_name}-${api_version}-java"
  mv "gapic-${folder_name}-${api_version}" "gapic-${folder_name}-${api_version}-java"
  if [[ ! "${transport}" == "rest" ]]; then
    mv "grpc-${folder_name}-${api_version}" "grpc-${folder_name}-${api_version}-java"
  fi
  exit 0
fi
repo_metadata_json_path=$(get_repo_metadata_json_or_default \
  "${repo_metadata_json_path}" \
  "${repository_path}" \
  "${output_folder}"
)
# various versions can use the same workspace, for example for bigtable +
# bigtable-admin
workspace="${output_folder}/workspace"
is_new_library="false" #always

mkdir -p "${workspace}"

run_owlbot_postprocessor "${workspace}" "${owlbot_sha}" "${repo_metadata_json_path}" "${include_samples}" \
  "${script_dir}" "${output_folder}/${destination_path}" "${api_version}" "${transport}" "${repository_path}" "${more_versions_coming}"

