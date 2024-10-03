#!/usr/bin/env bash

set -xeo pipefail

# Unit tests against ../utilities.sh
script_dir=$(dirname "$(readlink -f "$0")")
source "${script_dir}"/test_utilities.sh

# we simulate a properly prepared environment (i.e. generator jar in its
# well-known location). Tests confirming the opposite case will make sure this
# environment is restored
readonly SIMULATED_HOME=$(mktemp -d)
mkdir "${SIMULATED_HOME}/.library_generation"
touch "${SIMULATED_HOME}/.library_generation/gapic-generator-java.jar"
touch "${SIMULATED_HOME}/.library_generation/google-java-format.jar"
HOME="${SIMULATED_HOME}" source "${script_dir}"/../utils/utilities.sh

# Unit tests
extract_folder_name_test() {
  local path="google/cloud/aiplatform/v1/google-cloud-aiplatform-v1-java"
  local folder_name
  folder_name=$(extract_folder_name "${path}")
  assertEquals "google-cloud-aiplatform-v1-java" "${folder_name}"
}

get_grpc_version_succeed_docker_env_var_test() {
  local version_with_docker
  local version_without_docker
  export DOCKER_GRPC_VERSION="9.9.9"
  # get_grpc_version should prioritize DOCKER_GRPC_VERSION
  version_with_docker=$(get_grpc_version)
  assertEquals "${DOCKER_GRPC_VERSION}" "${version_with_docker}"
  unset DOCKER_GRPC_VERSION
}

get_protoc_version_succeed_docker_env_var_test() {
  local version_with_docker
  local version_without_docker
  export DOCKER_PROTOC_VERSION="9.9.9"
  # get_protoc_version should prioritize DOCKER_PROTOC_VERSION
  version_with_docker=$(get_protoc_version)
  assertEquals "${DOCKER_PROTOC_VERSION}" "${version_with_docker}"
  unset DOCKER_PROTOC_VERSION
}

get_gapic_opts_with_rest_test() {
  local proto_path="${script_dir}/resources/gapic_options"
  local transport="grpc"
  local rest_numeric_enums="true"
  local gapic_opts
  gapic_opts="$(get_gapic_opts "${transport}" "${rest_numeric_enums}" "" "" "")"
  assertEquals \
  "transport=grpc,rest-numeric-enums,grpc-service-config=${proto_path}/example_grpc_service_config.json,gapic-config=${proto_path}/example_gapic.yaml,api-service-config=${proto_path}/example.yaml" \
  "${gapic_opts}"
}

get_gapic_opts_without_rest_test() {
  local proto_path="${script_dir}/resources/gapic_options"
  local transport="grpc"
  local rest_numeric_enums="false"
  local gapic_opts
  gapic_opts="$(get_gapic_opts "${transport}" "${rest_numeric_enums}" "" "" "")"
  assertEquals \
  "transport=grpc,,grpc-service-config=${proto_path}/example_grpc_service_config.json,gapic-config=${proto_path}/example_gapic.yaml,api-service-config=${proto_path}/example.yaml" \
  "$gapic_opts"
}

get_gapic_opts_with_non_default_test() {
  local proto_path="${script_dir}/resources/gapic_options"
  local transport="grpc"
  local rest_numeric_enums="false"
  local gapic_opts
  gapic_opts="$(get_gapic_opts "${transport}" "${rest_numeric_enums}" "${proto_path}/example_gapic.yaml" "${proto_path}/example_grpc_service_config.json" "${proto_path}/example.yaml")"
  assertEquals \
  "transport=grpc,,grpc-service-config=${proto_path}/example_grpc_service_config.json,gapic-config=${proto_path}/example_gapic.yaml,api-service-config=${proto_path}/example.yaml" \
  "$gapic_opts"
}

remove_grpc_version_test() {
  local destination_path="${script_dir}/resources/gapic_options"
  cp "${destination_path}/QueryServiceGrpc_copy.java" "${destination_path}/QueryServiceGrpc.java"
  remove_grpc_version "${destination_path}"
  local res=0
  if ! grep -q 'value = "by gRPC proto compiler",' "${destination_path}/QueryServiceGrpc.java"; then
    echo "Error: grpc version is not removed."
    res=1
  fi

  assertEquals 0 $((res))
  rm "${destination_path}/QueryServiceGrpc.java"
}

download_protoc_succeed_with_valid_version_linux_test() {
  download_protoc "23.2" "linux-x86_64"
  assertFileOrDirectoryExists "protoc-23.2"
  rm -rf "protoc-23.2"
}

download_protoc_succeed_with_valid_version_macos_test() {
  download_protoc "23.2" "osx-x86_64"
  assertFileOrDirectoryExists "protoc-23.2"
  rm -rf "protoc-23.2" "google"
}

download_protoc_failed_with_invalid_version_linux_test() {
  local res=0
  $(download_protoc "22.99" "linux-x86_64") || res=$?
  assertEquals 1 $((res))
}

download_protoc_failed_with_invalid_arch_test() {
  local res=0
  $(download_protoc "23.2" "customized-x86_64") || res=$?
  assertEquals 1 $((res))
}

download_tools_succeed_with_baked_protoc() {
  # This mimics a docker container scenario.
  # This test consists of creating an empty /tmp/.../protoc-99.99/bin folder and map
  # it to the DOCKER_PROTOC_LOCATION env var (which is treated specially in the
  # `download_tools` function). If `DOCKER_PROTOC_VERSION` matches exactly as
  # the version passed to `download_protoc`, then we will not download protoc
  # but simply have the variable `protoc_path` pointing to DOCKER_PROTOC_LOCATION 
  # (which we manually created in this test)
  export DOCKER_PROTOC_LOCATION=$(mktemp -d)
  export DOCKER_PROTOC_VERSION="99.99"
  export output_folder=$(get_output_folder)
  mkdir "${output_folder}"
  local protoc_bin_folder="${DOCKER_PROTOC_LOCATION}/protoc-99.99/bin"
  mkdir -p "${protoc_bin_folder}"

  local test_grpc_version="1.64.0"
  # we expect download_tools to decide to use DOCKER_PROTOC_LOCATION because
  # the protoc version we want to download is the same as DOCKER_PROTOC_VERSION.
  # Note that `protoc_bin_folder` is just the expected formatted value that
  # download_tools will format using DOCKER_PROTOC_VERSION (via
  # download_protoc).
  download_tools "99.99" "${test_grpc_version}" "linux-x86_64"
  assertEquals "${protoc_bin_folder}" "${protoc_path}"

  rm -rdf "${output_folder}"
  unset DOCKER_PROTOC_LOCATION
  unset DOCKER_PROTOC_VERSION
  unset output_folder
  unset protoc_path
}

download_tools_succeed_with_baked_grpc() {
  # This test has the same structure as
  # download_tools_succeed_with_baked_protoc, but meant for the grpc plugin.
  export DOCKER_GRPC_LOCATION=$(mktemp -d)
  export DOCKER_GRPC_VERSION="99.99"
  export output_folder=$(get_output_folder)
  mkdir "${output_folder}"

  local test_protoc_version="1.64.0"
  # we expect download_tools to decide to use DOCKER_GRPC_LOCATION because
  # the protoc version we want to download is the same as DOCKER_GRPC_VERSION
  download_tools "${test_protoc_version}" "99.99" "linux-x86_64"
  assertEquals "${DOCKER_GRPC_LOCATION}" "${grpc_path}"

  rm -rdf "${output_folder}"
  unset DOCKER_GRPC_LOCATION
  unset DOCKER_GRPC_VERSION
  unset output_folder
  unset grpc_path
}

download_grpc_plugin_succeed_with_valid_version_linux_test() {
  download_grpc_plugin "1.55.1" "linux-x86_64"
  assertFileOrDirectoryExists "protoc-gen-grpc-java-1.55.1-linux-x86_64.exe"
  rm "protoc-gen-grpc-java-1.55.1-linux-x86_64.exe"
}

download_grpc_plugin_succeed_with_valid_version_macos_test() {
  download_grpc_plugin "1.55.1" "osx-x86_64"
  assertFileOrDirectoryExists "protoc-gen-grpc-java-1.55.1-osx-x86_64.exe"
  rm "protoc-gen-grpc-java-1.55.1-osx-x86_64.exe"
}

download_grpc_plugin_failed_with_invalid_version_linux_test() {
  local res=0
  $(download_grpc_plugin "0.99.0" "linux-x86_64") || res=$?
  assertEquals 1 $((res))
}

download_grpc_plugin_failed_with_invalid_arch_test() {
  local res=0
  $(download_grpc_plugin "1.55.1" "customized-x86_64") || res=$?
  assertEquals 1 $((res))
}

generate_library_failed_with_invalid_generator_version() {
  local destination="google-cloud-alloydb-v1-java"
  local res=0
  cd "${script_dir}/resources"
  bash "${script_dir}"/../generate_library.sh \
    -p google/cloud/alloydb/v1 \
    -d ../"${destination}" \
    --protoc_version 23.2 \
    --grpc_version 1.55.1 \
    --transport grpc+rest \
    --rest_numeric_enums true || res=$?
  assertEquals 1 $((res))
  # still need to clean up potential downloaded tooling.
  cleanup "${destination}"
}

generate_library_failed_with_invalid_protoc_version() {
  local destination="google-cloud-alloydb-v1-java"
  local res=0
  cd "${script_dir}/resources"
  bash "${script_dir}"/../generate_library.sh \
    -p google/cloud/alloydb/v1 \
    -d ../"${destination}" \
    --protoc_version 22.99 \
    --grpc_version 1.55.1 \
    --transport grpc+rest \
    --rest_numeric_enums true || res=$?
  assertEquals 1 $((res))
  # still need to clean up potential downloaded tooling.
  cleanup "${destination}"
}

generate_library_failed_with_invalid_grpc_version() {
  local destination="google-cloud-alloydb-v1-java"
  local res=0
  cd "${script_dir}/resources"
  bash "${script_dir}"/../generate_library.sh \
    -p google/cloud/alloydb/v1 \
    -d ../output/"${destination}" \
    --grpc_version 0.99.0 \
    --transport grpc+rest \
    --rest_numeric_enums true || res=$?
  assertEquals 1 $((res))
  # still need to clean up potential downloaded tooling.
  cleanup "${destination}"
}

copy_directory_if_exists_valid_folder_succeeds() {
  local source_folder="${script_dir}/resources"
  local destination="${script_dir}/test_destination_folder"
  mkdir -p "${destination}"
  copy_directory_if_exists "${source_folder}" "gapic" "${destination}/copied-folder"
  n_matching_folders=$(ls "${destination}" | grep -e 'copied-folder' | wc -l)
  rm -rdf "${destination}"
  assertEquals 1 ${n_matching_folders}
}

copy_directory_if_exists_invalid_folder_does_not_copy() {
  local source_folder="${script_dir}/non-existent"
  local destination="${script_dir}/test_destination_folder"
  mkdir -p "${destination}"
  copy_directory_if_exists "${source_folder}" "gapic" "${destination}/copied-folder"
  n_matching_folders=$(ls "${destination}" | grep -e 'copied-folder' | wc -l) || res=$?
  rm -rdf "${destination}"
  assertEquals 0 ${n_matching_folders}
}

get_proto_path_from_preprocessed_sources_valid_library_succeeds() {
  local sources="${script_dir}/resources/proto_path_library"
  local proto_path=$(get_proto_path_from_preprocessed_sources "${sources}")
  assertEquals "google/cloud/test/v1" ${proto_path}
}

get_proto_path_from_preprocessed_sources_empty_library_fails() {
  local sources=$(mktemp -d)
  (
    get_proto_path_from_preprocessed_sources "${sources}"
  ) || res=$?
  assertEquals 1 ${res}
}

get_proto_path_from_preprocessed_sources_multiple_proto_dirs_fails() {
  local sources="${script_dir}/resources/proto_path_library_multiple_protos"
  (
    get_proto_path_from_preprocessed_sources "${sources}"
  ) || res=$?
  assertEquals 1 ${res}
}

# Execute tests.
# One line per test.
test_list=(
  extract_folder_name_test
  get_grpc_version_succeed_docker_env_var_test
  get_protoc_version_succeed_docker_env_var_test
  get_gapic_opts_with_rest_test
  get_gapic_opts_without_rest_test
  get_gapic_opts_with_non_default_test
  remove_grpc_version_test
  download_protoc_succeed_with_valid_version_linux_test
  download_protoc_succeed_with_valid_version_macos_test
  download_protoc_failed_with_invalid_version_linux_test
  download_protoc_failed_with_invalid_arch_test
  download_tools_succeed_with_baked_protoc
  download_tools_succeed_with_baked_grpc
  download_grpc_plugin_succeed_with_valid_version_linux_test
  download_grpc_plugin_succeed_with_valid_version_macos_test
  download_grpc_plugin_failed_with_invalid_version_linux_test
  download_grpc_plugin_failed_with_invalid_arch_test
  generate_library_failed_with_invalid_generator_version
  generate_library_failed_with_invalid_protoc_version
  generate_library_failed_with_invalid_grpc_version
  copy_directory_if_exists_valid_folder_succeeds
  copy_directory_if_exists_invalid_folder_does_not_copy
  get_proto_path_from_preprocessed_sources_valid_library_succeeds
  get_proto_path_from_preprocessed_sources_empty_library_fails
  get_proto_path_from_preprocessed_sources_multiple_proto_dirs_fails
)

pushd "${script_dir}"
execute_tests  "${test_list[@]}"
popd
