#!/usr/bin/env bash

set -xeo pipefail

# Unit tests against ../utilities.sh
script_dir=$(dirname "$(readlink -f "$0")")
source "${script_dir}"/test_utilities.sh
source "${script_dir}"/../utilities.sh

# Unit tests
extract_folder_name_test() {
  local path="google/cloud/aiplatform/v1/google-cloud-aiplatform-v1-java"
  local folder_name
  folder_name=$(extract_folder_name "${path}")
  assertEquals "google-cloud-aiplatform-v1-java" "${folder_name}"
}

get_grpc_version_succeed_with_valid_generator_version_test() {
  local actual_version
  actual_version=$(get_grpc_version "2.24.0")
  rm "gapic-generator-java-pom-parent-2.24.0.pom"
  assertEquals "1.56.1" "${actual_version}"
}

get_grpc_version_failed_with_invalid_generator_version_test() {
  local res=0
  $(get_grpc_version "1.99.0") || res=$?
  assertEquals 1 $((res))
}

get_protobuf_version_succeed_with_valid_generator_version_test() {
  local actual_version
  actual_version=$(get_protobuf_version "2.24.0")
  assertEquals "23.2" "${actual_version}"
  rm "gapic-generator-java-pom-parent-2.24.0.pom"
}

get_protobuf_version_failed_with_invalid_generator_version_test() {
  local res=0
  $(get_protobuf_version "1.99.0") || res=$?
  assertEquals 1 $((res))
}

search_additional_protos_common_resources_test() {
  local proto_path="${script_dir}/resources/search_additional_proto/common_resources"
  local addition_protos
  addition_protos=$(search_additional_protos)
  assertEquals "google/cloud/common_resources.proto" "${addition_protos}"
}

search_additional_protos_iam_test() {
  local proto_path="${script_dir}/resources/search_additional_protos/iam"
  local addition_protos
  addition_protos=$(search_additional_protos)
  assertEquals \
  "google/cloud/common_resources.proto google/iam/v1/iam_policy.proto" \
  "${addition_protos}"
}

search_additional_protos_location_test() {
  local proto_path="${script_dir}/resources/search_additional_protos/location"
  local addition_protos
  addition_protos=$(search_additional_protos)
  assertEquals \
  "google/cloud/common_resources.proto google/cloud/location/locations.proto" \
  "${addition_protos}"
}

search_additional_protos_iam_location_test() {
  local proto_path="${script_dir}/resources/search_additional_protos/iam_location"
  local addition_protos
  addition_protos=$(search_additional_protos)
  assertEquals \
  "google/cloud/common_resources.proto google/iam/v1/iam_policy.proto google/cloud/location/locations.proto" \
  "${addition_protos}"
}

get_gapic_opts_with_rest_test() {
  local proto_path="${script_dir}/resources/gapic_options"
  local transport="grpc"
  local rest_numeric_enums="true"
  local gapic_opts
  gapic_opts="$(get_gapic_opts)"
  assertEquals \
  "transport=grpc,rest-numeric-enums,grpc-service-config=${proto_path}/example_grpc_service_config.json,gapic-config=${proto_path}/example_gapic.yaml,api-service-config=${proto_path}/example.yaml" \
  "${gapic_opts}"
}

get_gapic_opts_without_rest_test() {
  local proto_path="${script_dir}/resources/gapic_options"
  local transport="grpc"
  local rest_numeric_enums="false"
  local gapic_opts
  gapic_opts="$(get_gapic_opts)"
  assertEquals \
  "transport=grpc,grpc-service-config=${proto_path}/example_grpc_service_config.json,gapic-config=${proto_path}/example_gapic.yaml,api-service-config=${proto_path}/example.yaml" \
  "$gapic_opts"
}

remove_grpc_version_test() {
  local destination_path="${script_dir}/resources/gapic_options"
  cp "${destination_path}/QueryServiceGrpc_copy.java" "${destination_path}/QueryServiceGrpc.java"
  remove_grpc_version
  local res=0
  if ! grep -q 'value = "by gRPC proto compiler",' "${destination_path}/QueryServiceGrpc.java"; then
    echo "Error: grpc version is not removed."
    res=1
  fi

  assertEquals 0 $((res))
  rm "${destination_path}/QueryServiceGrpc.java"
}

download_generator_success_with_valid_version_test() {
  download_generator "2.24.0"
  assertFileOrDirectoryExists "gapic-generator-java-2.24.0.jar"
  rm "gapic-generator-java-2.24.0.jar"
}

download_generator_failed_with_invalid_version_test() {
  # The download function will exit the shell
  # if download failed. Test the exit code instead of
  # downloaded file (there will be no downloaded file).
  # Use $() to execute the function in subshell so that
  # the other tests can continue executing in the current
  # shell.
  local res=0
  $(download_generator "1.99.0") || res=$?
  assertEquals 1 $((res))
}

download_protobuf_succeed_with_valid_version_linux_test() {
  download_protobuf "23.2" "linux-x86_64"
  assertFileOrDirectoryExists "protobuf-23.2"
  rm -rf "protobuf-23.2"
}

download_protobuf_succeed_with_valid_version_macos_test() {
  download_protobuf "23.2" "osx-x86_64"
  assertFileOrDirectoryExists "protobuf-23.2"
  rm -rf "protobuf-23.2" "google"
}

download_protobuf_failed_with_invalid_version_linux_test() {
  local res=0
  $(download_protobuf "22.99" "linux-x86_64") || res=$?
  assertEquals 1 $((res))
}

download_protobuf_failed_with_invalid_arch_test() {
  local res=0
  $(download_protobuf "23.2" "customized-x86_64") || res=$?
  assertEquals 1 $((res))
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
  "${script_dir}"/../generate_library.sh \
    -p google/cloud/alloydb/v1 \
    -d ../"${destination}" \
    --gapic_generator_version 1.99.0 \
    --protobuf_version 23.2 \
    --grpc_version 1.55.1 \
    --transport grpc+rest \
    --rest_numeric_enums true || res=$?
  assertEquals 1 $((res))
  # still need to clean up potential downloaded tooling.
  cleanup "${destination}"
}

generate_library_failed_with_invalid_protobuf_version() {
  local destination="google-cloud-alloydb-v1-java"
  local res=0
  cd "${script_dir}/resources"
  "${script_dir}"/../generate_library.sh \
    -p google/cloud/alloydb/v1 \
    -d ../"${destination}" \
    --gapic_generator_version 2.24.0 \
    --protobuf_version 22.99 \
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
  "${script_dir}"/../generate_library.sh \
    -p google/cloud/alloydb/v1 \
    -d ../output/"${destination}" \
    --gapic_generator_version 2.24.0 \
    --grpc_version 0.99.0 \
    --transport grpc+rest \
    --rest_numeric_enums true || res=$?
  assertEquals 1 $((res))
  # still need to clean up potential downloaded tooling.
  cleanup "${destination}"
}

get_config_from_valid_BUILD_matched_test() {
  build_file="${script_dir}/resources/misc/TESTBUILD.bazel"
  rule="java_gapic_library("
  # the pattern we expect to find in the BUILD file
  pattern_should_match="name"
  # default value if the pattern was not found
  if_matched_return="got-a-match"
  if_not_matched_return="no-match"
  pattern_matched_result=$(get_config_from_BUILD \
    "${build_file}" \
    "${rule}" \
    "${pattern_should_match}" \
    "${if_not_matched_return}" \
    "${if_matched_return}"
  )
  assertEquals "${if_matched_return}" "${pattern_matched_result}"
}

get_config_from_valid_BUILD_not_match_test() {
  build_file="${script_dir}/resources/misc/TESTBUILD.bazel"
  rule="java_gapic_library("
  # the pattern that we should not find in the BUILD file
  pattern_should_not_match="should-not-match"
  # default value if the pattern was not found
  if_matched_return="got-a-match"
  if_not_matched_return="no-match"
  pattern_not_matched_result=$(get_config_from_BUILD \
    "${build_file}" \
    "${rule}" \
    "${pattern_should_not_match}" \
    "${if_not_matched_return}" \
    "${if_matched_return}"
  )
  assertEquals "${if_not_matched_return}" "${pattern_not_matched_result}"
}

get_version_from_valid_WORKSPACE_test() {
  workspace_file="${script_dir}/resources/misc/TESTWORKSPACE"
  obtained_ggj_version=$(get_version_from_WORKSPACE "_gapic_generator_java_version" "${workspace_file}")
  assertEquals '2.25.1-SNAPSHOT' "${obtained_ggj_version}"
}

get_generator_version_from_valid_versions_txt_test() {
  versions_file="${script_dir}/resources/misc/testversions.txt"
  obtained_ggj_version=$(get_version_from_versions_txt "${versions_file}" "gapic-generator-java")
  assertEquals '2.25.1-SNAPSHOT' "${obtained_ggj_version}"
}

get_gax_version_from_valid_versions_txt_test() {
  versions_file="${script_dir}/resources/misc/testversions.txt"
  obtained_gax_version=$(get_version_from_versions_txt "${versions_file}" "gax")
  assertEquals '2.33.1-SNAPSHOT' "${obtained_gax_version}"
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
  download_generator_success_with_valid_version_test
  download_generator_failed_with_invalid_version_test
  download_protobuf_succeed_with_valid_version_linux_test
  download_protobuf_succeed_with_valid_version_macos_test
  download_protobuf_failed_with_invalid_version_linux_test
  download_protobuf_failed_with_invalid_arch_test
  download_grpc_plugin_succeed_with_valid_version_linux_test
  download_grpc_plugin_succeed_with_valid_version_macos_test
  download_grpc_plugin_failed_with_invalid_version_linux_test
  download_grpc_plugin_failed_with_invalid_arch_test
  generate_library_failed_with_invalid_generator_version
  generate_library_failed_with_invalid_protobuf_version
  generate_library_failed_with_invalid_grpc_version
  get_config_from_valid_BUILD_matched_test
  get_config_from_valid_BUILD_not_match_test
  get_version_from_valid_WORKSPACE_test
  get_generator_version_from_valid_versions_txt_test
  get_gax_version_from_valid_versions_txt_test
)

pushd "${script_dir}"
execute_tests  "${test_list[@]}"
popd
