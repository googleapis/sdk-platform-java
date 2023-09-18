#!/usr/bin/env bash

set -xeo pipefail

# Utility functions commonly used in test cases.

# Variables used to generate final result
total_num=0
succeed_num=0
failed_num=0
failed_tests=""

# Helper functions, they shouldn't be called outside this file.
__test_executed() {
  total_num=$((1 + total_num))
}

__test_succeed() {
  succeed_num=$((1 + succeed_num))
}

__test_failed() {
  failed_test=$1
  failed_num=$((1 + failed_num))
  failed_tests="${failed_tests} ${failed_test}"
}

assertEquals() {
  local expected=$1
  local actual=$2
  if [[ "${expected}" == "${actual}" ]]; then
    __test_succeed
    return
  fi

  echo "Error: expected ${expected}, got ${actual} instead."
  __test_failed "${ut}"
}

assertFileOrDirectoryExists() {
  local expected_file=$1
  if [ -d "${expected_file}" ]|| [ -f "${expected_file}" ]; then
    __test_succeed
    return
  fi

  echo "Error: ${expected_file} does not exist."
  __test_failed "${ut}"
}

# Clean up generated files, tooling when testing `generate_library.sh`.
cleanup() {
  local library_directory=$1
  rm -rf ../"${library_directory}" \
   google/protobuf \
   protobuf-* \
   gapic-generator-java-*.jar \
   gapic-generator-java-pom-parent-*.pom \
   protoc-gen-grpc-*.exe
}

execute_tests() {
  local test_list=("$@")
  for ut in "${test_list[@]}"; do
    echo "========== Execute ${ut} =========="
    __test_executed
    "${ut}"
  done

  echo "Test result: ${total_num} tests executed, ${succeed_num} succeed, ${failed_num} failed."
  if [[ "${total_num}" == "${succeed_num}" ]]; then
    echo "All tests passed."
    exit
  fi

  echo "Test failed."
  echo "Failed test(s): ${failed_tests}."
  exit 1
}
