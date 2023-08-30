#!/usr/bin/env bash

set -xeo pipefail

# Variables used to generate final result
total_num=0
succeed_num=0
failed_num=0
# Unit tests against ./utilities.sh
script_dir=$(dirname "$(readlink -f "$0")")
source "$script_dir"/../utilities.sh

assertEquals() {
  expected=$1
  actual=$2
  if [[ "$expected" == "$actual" ]]; then
    return 0
  fi

  echo "Error: expected $expected, got $actual instead."
  return 1
}

test_executed() {
  total_num=$((1 + total_num))
}

test_succeed() {
  succeed_num=$((1 + succeed_num))
}

test_failed() {
  failed_num=$((1 + failed_num))
}

extract_folder_name_test() {
  path="google/cloud/aiplatform/v1/google-cloud-aiplatform-v1-java"
  folder_name=$(extract_folder_name "$path")
  assertEquals "google-cloud-aiplatform-v1-java" "$folder_name"
}

get_grpc_version_test() {
  actual_version=$(get_grpc_version "2.24.0")
  assertEquals "1.56.1" "$actual_version"
}

get_protobuf_version_test() {
  actual_version=$(get_protobuf_version "2.24.0")
  assertEquals "23.2" "$actual_version"
}

search_additional_protos_common_resources_test() {
  proto_path="$script_dir/resources/monitoring"
  addition_protos=$(search_additional_protos)
  assertEquals "google/cloud/common_resources.proto" "$addition_protos"
}

search_additional_protos_iam_test() {
  proto_path="$script_dir/resources/pubsub"
  addition_protos=$(search_additional_protos)
  assertEquals \
  "google/cloud/common_resources.proto google/iam/v1/iam_policy.proto" \
  "$addition_protos"
}

search_additional_protos_location_test() {
  proto_path="$script_dir/resources/firestore"
  addition_protos=$(search_additional_protos)
  assertEquals \
  "google/cloud/common_resources.proto google/cloud/location/locations.proto" \
  "$addition_protos"
}

search_additional_protos_iam_location_test() {
  proto_path="$script_dir/resources/alloydb"
  addition_protos=$(search_additional_protos)
  assertEquals \
  "google/cloud/common_resources.proto google/iam/v1/iam_policy.proto google/cloud/location/locations.proto" \
  "$addition_protos"
}

# Execute tests.
# One line per test.
test_list=(
  extract_folder_name_test
  get_grpc_version_test
  get_protobuf_version_test
  search_additional_protos_common_resources_test
  search_additional_protos_iam_test
  search_additional_protos_location_test
  search_additional_protos_iam_location_test
)

for ut in "${test_list[@]}"; do
  test_executed
  result=0
  "$ut" || result=$?
  if [[ "$result" == 0 ]]; then
    test_succeed
  else
    test_failed
  fi
done

echo "Test result: $total_num tests executed, $succeed_num succeed, $failed_num failed."
if [[ "$total_num" == "$succeed_num" ]]; then
  echo "All tests passed."
  exit
fi

echo "Test failed."
exit 1
