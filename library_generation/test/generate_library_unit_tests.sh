#!/usr/bin/env bash

set -xeo pipefail

# Variables used to generate final result
total_num=0
succeed_num=0
failed_num=0
# Unit tests against ./utilities.sh
script_dir=$(dirname "$(readlink -f "$0")")
source "${script_dir}"/../utilities.sh

# Helper functions, they shouldn't be called outside this file.
__assertEquals() {
  expected=$1
  actual=$2
  if [[ "${expected}" == "${actual}" ]]; then
    return 0
  fi

  echo "Error: expected ${expected}, got ${actual} instead."
  return 1
}

__assertFileExists() {
  expected_file=$1
  if [ -e "${expected_file}" ]; then
    return 0
  fi

  echo "Error: ${expected_file} does not exist."
  return 1
}

__assertFileDoesNotExist() {
  expected_file=$1
  if [ ! -f "${expected_file}" ]; then
    return 0
  fi

  echo "Error: ${expected_file} exists."
  return 1
}

__test_executed() {
  total_num=$((1 + total_num))
}

__test_succeed() {
  succeed_num=$((1 + succeed_num))
}

__test_failed() {
  failed_num=$((1 + failed_num))
}

# Unit tests
extract_folder_name_test() {
  path="google/cloud/aiplatform/v1/google-cloud-aiplatform-v1-java"
  folder_name=$(extract_folder_name "${path}")
  __assertEquals "google-cloud-aiplatform-v1-java" "${folder_name}"
}

get_grpc_version_succeed_with_valid_generator_version_test() {
  actual_version=$(get_grpc_version "2.24.0")
  __assertEquals "1.56.1" "${actual_version}"
  rm "gapic-generator-java-pom-parent-2.24.0.pom"
}

get_grpc_version_failed_with_invalid_generator_version_test() {
  actual_version=$(get_grpc_version "1.99.0")
  __assertEquals "" "${actual_version}"
}

get_protobuf_version_succeed_with_valid_generator_version_test() {
  actual_version=$(get_protobuf_version "2.24.0")
  __assertEquals "23.2" "${actual_version}"
  rm "gapic-generator-java-pom-parent-2.24.0.pom"
}

get_protobuf_version_failed_with_invalid_generator_version_test() {
  actual_version=$(get_protobuf_version "1.99.0")
  __assertEquals "" "${actual_version}"
}

search_additional_protos_common_resources_test() {
  proto_path="${script_dir}/resources/monitoring"
  addition_protos=$(search_additional_protos)
  __assertEquals "google/cloud/common_resources.proto" "${addition_protos}"
}

search_additional_protos_iam_test() {
  proto_path="${script_dir}/resources/pubsub"
  addition_protos=$(search_additional_protos)
  __assertEquals \
  "google/cloud/common_resources.proto google/iam/v1/iam_policy.proto" \
  "${addition_protos}"
}

search_additional_protos_location_test() {
  proto_path="{$script_dir}/resources/firestore"
  addition_protos=$(search_additional_protos)
  __assertEquals \
  "google/cloud/common_resources.proto google/cloud/location/locations.proto" \
  "${addition_protos}"
}

search_additional_protos_iam_location_test() {
  proto_path="${script_dir}/resources/alloydb"
  addition_protos=$(search_additional_protos)
  __assertEquals \
  "google/cloud/common_resources.proto google/iam/v1/iam_policy.proto google/cloud/location/locations.proto" \
  "${addition_protos}"
}

get_gapic_opts_with_rest_test() {
  proto_path="${script_dir}/resources/monitoring"
  transport="grpc"
  rest_numeric_enums="true"
  gapic_opts="$(get_gapic_opts)"
  __assertEquals \
  "transport=grpc,rest-numeric-enums,grpc-service-config=${proto_path}/monitoring_grpc_service_config.json,gapic-config=${proto_path}/monitoring_gapic.yaml,api-service-config=${proto_path}/monitoring.yaml" \
  "${gapic_opts}"
}

get_gapic_opts_without_rest_test() {
  proto_path="${script_dir}/resources/monitoring"
  transport="grpc"
  rest_numeric_enums="false"
  gapic_opts="$(get_gapic_opts)"
  __assertEquals \
  "transport=grpc,grpc-service-config=${proto_path}/monitoring_grpc_service_config.json,gapic-config=${proto_path}/monitoring_gapic.yaml,api-service-config=${proto_path}/monitoring.yaml" \
  "$gapic_opts"
}

remove_grpc_version_test() {
  destination_path="${script_dir}/resources/monitoring"
  cp "${destination_path}/QueryServiceGrpc_copy.java" "${destination_path}/QueryServiceGrpc.java"
  remove_grpc_version
  return_code=0
  if grep -q 'value = "by gRPC proto compiler",' "${destination_path}/QueryServiceGrpc.java"; then
    echo "grpc version is removed."
  else
    echo "Error: grpc version is not removed."
    return_code=1
  fi

  rm "${destination_path}/QueryServiceGrpc.java"
  return "${return_code}"
}

download_generator_success_with_valid_version_test() {
  download_generator "2.24.0"
  __assertFileExists "gapic-generator-java-2.24.0.jar"
  rm "gapic-generator-java-2.24.0.jar"
}

download_generator_failed_with_invalid_version_test() {
  # The download function will exit the shell
  # if download failed.
  # Use $() to execute the function in subshell so that
  # the other tests can continue executing in the current
  # shell.
  $(download_generator "1.99.0")
  __assertFileDoesNotExist "gapic-generator-java-1.99.0.jar"
}

download_protobuf_succeed_with_valid_version_linux_test() {
  download_protobuf "23.2" "linux-x86_64"
  __assertFileExists "protobuf-23.2"
  rm -rf "protobuf-23.2"
}

download_protobuf_succeed_with_valid_version_macos_test() {
  download_protobuf "23.2" "osx-x86_64"
  __assertFileExists "protobuf-23.2"
  rm -rf "protobuf-23.2" "google"
}

download_protobuf_failed_with_invalid_version_linux_test() {
  $(download_protobuf "22.99" "linux-x86_64")
  __assertFileDoesNotExist "protobuf-22.99"
}

download_protobuf_failed_with_invalid_arch_test() {
  $(download_protobuf "23.2" "customized-x86_64")
  __assertFileDoesNotExist "protobuf-23.2"
}

download_grpc_plugin_succeed_with_valid_version_linux_test() {
  download_grpc_plugin "1.55.1" "linux-x86_64"
  __assertFileExists "protoc-gen-grpc-java-1.55.1-linux-x86_64.exe"
  rm "protoc-gen-grpc-java-1.55.1-linux-x86_64.exe"
}

download_grpc_plugin_succeed_with_valid_version_macos_test() {
  download_grpc_plugin "1.55.1" "osx-x86_64"
  __assertFileExists "protoc-gen-grpc-java-1.55.1-osx-x86_64.exe"
  rm "protoc-gen-grpc-java-1.55.1-osx-x86_64.exe"
}

download_grpc_plugin_failed_with_invalid_version_linux_test() {
  $(download_grpc_plugin "0.99.0" "linux-x86_64")
  __assertFileDoesNotExist "protoc-gen-grpc-java-0.99.0-linux-x86_64.exe"
}

download_grpc_plugin_failed_with_invalid_arch_test() {
  $(download_grpc_plugin "1.55.1" "customized-x86_64")
  __assertFileDoesNotExist "protoc-gen-grpc-java-1.55.1-customized-x86_64.exe"
}

# Execute tests.
# One line per test.
test_list=(
  extract_folder_name_test
  get_grpc_version_succeed_with_valid_generator_version_test
  get_grpc_version_failed_with_invalid_generator_version_test
  get_protobuf_version_succeed_with_valid_generator_version_test
  get_protobuf_version_failed_with_invalid_generator_version_test
  search_additional_protos_common_resources_test
  search_additional_protos_iam_test
  search_additional_protos_location_test
  search_additional_protos_iam_location_test
  get_gapic_opts_with_rest_test
  get_gapic_opts_without_rest_test
  remove_grpc_version_test
  download_protobuf_succeed_with_valid_version_linux_test
  download_protobuf_succeed_with_valid_version_macos_test
  download_protobuf_failed_with_invalid_version_linux_test
  download_protobuf_failed_with_invalid_arch_test
  download_grpc_plugin_succeed_with_valid_version_linux_test
  download_grpc_plugin_succeed_with_valid_version_macos_test
  download_grpc_plugin_failed_with_invalid_version_linux_test
  download_grpc_plugin_failed_with_invalid_arch_test
)

for ut in "${test_list[@]}"; do
  pushd "${script_dir}"
  __test_executed
  result=0
  "$ut" || result=$?
  if [[ "${result}" == 0 ]]; then
    __test_succeed
  else
    __test_failed
  fi
  popd
done

echo "Test result: ${total_num} tests executed, ${succeed_num} succeed, ${failed_num} failed."
if [[ "${total_num}" == "${succeed_num}" ]]; then
  echo "All tests passed."
  exit
fi

echo "Test failed."
exit 1
