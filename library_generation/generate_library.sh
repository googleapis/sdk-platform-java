#!/usr/bin/env bash

set -eox pipefail

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
  --gapic_yaml)
    gapic_yaml="$2"
    shift
    ;;
  --service_config)
    service_config="$2"
    shift
    ;;
  --service_yaml)
    service_yaml="$2"
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
output_folder="$(get_output_folder)"

if [ -z "${gapic_generator_version}" ]; then
  echo 'missing required argument --gapic_generator_version'
  exit 1
fi

if [ -z "${protobuf_version}" ]; then
  protobuf_version=$(get_protobuf_version "${gapic_generator_version}")
fi

if [ -z "${grpc_version}" ]; then
  grpc_version=$(get_grpc_version "${gapic_generator_version}")
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

if [ -z "${gapic_yaml}" ]; then
  gapic_yaml=""
fi

if [ -z "${service_config}" ]; then
  service_config=""
fi

if [ -z "${service_yaml}" ]; then
  service_yaml=""
fi

if [ -z "${include_samples}" ]; then
  include_samples="true"
fi

if [ -z "${os_architecture}" ]; then
  os_architecture=$(detect_os_architecture)
fi

temp_destination_path="${output_folder}/temp_preprocessed"
mkdir -p "${output_folder}/${destination_path}"
if [ -d "${temp_destination_path}" ]; then
  # we don't want the preprocessed sources of a previous run
  rm -rd "${temp_destination_path}"
fi
mkdir -p "${temp_destination_path}"
##################### Section 0 #####################
# prepare tooling
#####################################################
# the order of services entries in gapic_metadata.json is relevant to the
# order of proto file, sort the proto files with respect to their bytes to
# get a fixed order.
folder_name=$(extract_folder_name "${destination_path}")
pushd "${output_folder}"
find_depth=""
case "${proto_path}" in
  "google/api" | "google/cloud" | "google/iam/v1" | "google/rpc")
    find_depth="-maxdepth 1"
    ;;
esac
proto_files=$(find "${proto_path}" ${find_depth} -type f  -name "*.proto" | LC_COLLATE=C sort)
# include or exclude certain protos in grpc plugin and gapic generator java.
case "${proto_path}" in
  "google/cloud")
    # this proto is excluded from //google/cloud:google-apps-script-type-java
    removed_proto="google/cloud/common_resources.proto"
    proto_files="${proto_files//${removed_proto}/}"
    ;;
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
  "google/rpc")
    # this proto is excluded from //google/rpc:google-rpc-java
    removed_proto="google/rpc/http.proto"
    proto_files="${proto_files//${removed_proto}/}"
    ;;
  "google/shopping"*)
    # this proto is included in //google/shopping/css/v1:google-cloud-shopping-css-v1-java
    # and //google/shopping/merchant/inventories/v1beta:google-cloud-merchant-inventories-v1beta-java
    # and //google/shopping/merchant/reports/v1beta:google-cloud-merchant-reports-v1beta-java
    proto_files="${proto_files} google/shopping/type/types.proto"
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
  "--rpc-plugin_out=:${temp_destination_path}/java_grpc.jar" \
  ${proto_files} # Do not quote because this variable should not be treated as one long string.
  # unzip java_grpc.jar to grpc-*/src/main/java
  unzip_src_files "grpc" "${temp_destination_path}"
  # remove empty files in grpc-*/src/main/java
  remove_empty_files "grpc" "${temp_destination_path}"
  # remove grpc version in *ServiceGrpc.java file so the content is identical with bazel build.
  remove_grpc_version "${temp_destination_path}"
fi
###################### Section 2 #####################
## generate gapic-*/, part of proto-*/, samples/
######################################################
"$protoc_path"/protoc --experimental_allow_proto3_optional \
"--plugin=protoc-gen-java_gapic=${script_dir}/gapic-generator-java-wrapper" \
"--java_gapic_out=metadata:${temp_destination_path}/java_gapic_srcjar_raw.srcjar.zip" \
"--java_gapic_opt=$(get_gapic_opts "${transport}" "${rest_numeric_enums}" "${gapic_yaml}" "${service_config}" "${service_yaml}")" \
${proto_files} ${gapic_additional_protos}

# check if the generator produced any files into the srcjar
did_generate_gapic="true"
zipinfo -t "${temp_destination_path}/java_gapic_srcjar_raw.srcjar.zip" || did_generate_gapic="false"
if [[ "${did_generate_gapic}" == "true" ]];
then
  unzip -o -q "${temp_destination_path}/java_gapic_srcjar_raw.srcjar.zip" -d "${temp_destination_path}"
  # Sync'\''d to the output file name in Writer.java.
  unzip -o -q "${temp_destination_path}/temp-codegen.srcjar" -d "${temp_destination_path}/java_gapic_srcjar"
  # Resource name source files.
  proto_dir=${temp_destination_path}/java_gapic_srcjar/proto/src/main/java
  if [ ! -d "${proto_dir}" ]; then
    # Some APIs don't have resource name helpers, like BigQuery v2.
    # Create an empty file so we can finish building. Gating the resource name rule definition
    # on file existences go against Bazel's design patterns, so we'll simply delete all empty
    # files during the final packaging process (see java_gapic_pkg.bzl)
    mkdir -p "${proto_dir}"
    touch "${proto_dir}"/PlaceholderFile.java
  fi
  # move java_gapic_srcjar/src/main to gapic-*/src.
  mv_src_files "gapic" "main" "${temp_destination_path}"
  # remove empty files in gapic-*/src/main/java
  remove_empty_files "gapic" "${temp_destination_path}"
  # move java_gapic_srcjar/src/test to gapic-*/src
  mv_src_files "gapic" "test" "${temp_destination_path}"
  if [ "${include_samples}" == "true" ]; then
    # move java_gapic_srcjar/samples/snippets to samples/snippets
    mv_src_files "samples" "main" "${temp_destination_path}"
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
"$protoc_path"/protoc "--java_out=${temp_destination_path}/java_proto.jar" ${proto_files}
if [[ "${did_generate_gapic}" == "true" ]]; then
  # move java_gapic_srcjar/proto/src/main/java (generated resource name helper class)
  # to proto-*/src/main
  mv_src_files "proto" "main" "${temp_destination_path}"
fi
# unzip java_proto.jar to proto-*/src/main/java
unzip_src_files "proto" "${temp_destination_path}"
# remove empty files in proto-*/src/main/java
remove_empty_files "proto" "${temp_destination_path}"
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
  "google/iam/v1")
    # these protos are excluded from //google/iam/v1:google-iam-v1-java
    prefix="google/iam/v1"
    protos="${prefix}/options.proto ${prefix}/policy.proto"
    for removed_proto in ${protos}; do
      proto_files="${proto_files//${removed_proto}/}"
    done
    ;;
esac
# copy proto files to proto-*/src/main/proto
for proto_src in ${proto_files}; do
  if [[ "${proto_src}" == "google/cloud/common/operation_metadata.proto" ]] ||
     [[ "${proto_src}" == "google/shopping/type/types.proto" ]]; then
    continue
  fi
  mkdir -p "${temp_destination_path}/proto-${folder_name}/src/main/proto"
  rsync -R "${proto_src}" "${temp_destination_path}/proto-${folder_name}/src/main/proto"
done
popd # output_folder
##################### Section 4 #####################
# rm tar files
#####################################################
pushd "${temp_destination_path}"
rm -rf java_gapic_srcjar java_gapic_srcjar_raw.srcjar.zip java_grpc.jar java_proto.jar temp-codegen.srcjar
popd # destination path

cp -r ${temp_destination_path}/* "${output_folder}/${destination_path}"
rm -rdf "${temp_destination_path}"
exit 0
