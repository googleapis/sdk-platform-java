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
  test_executed
  expected=$1
  actual=$2
  if [[ "$expected" == "$actual" ]]; then
    test_succeed
    return
  fi

  test_failed
  echo "Error: expected $expected, got $actual instead."
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

# Execute tests
extract_folder_name_test
get_grpc_version_test
get_protobuf_version_test

echo "Test result: $total_num tests executed, $succeed_num succeed, $failed_num failed."
if [[ "$total_num" == "$succeed_num" ]]; then
  echo "All tests passed."
  exit
fi

echo "Test failed."
exit 1
