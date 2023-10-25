#!/usr/bin/env bash

set -xeo pipefail

# Utility functions commonly used in test cases.

# Variables used to generate final result
total_num=0
succeed_num=0
failed_num=0
failed_tests=""

############# Helper functions, they shouldn't be called outside this file #############
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

# Used to obtain configuration values from a bazel BUILD file
#
# inspects a $build_file for a certain $rule (e.g. java_gapic_library). If the
# first 15 lines after the declaration of the rule contain $pattern, then
# it will return $if_match if $pattern is found, otherwise $default
__get_config_from_BUILD() {
  build_file=$1
  rule=$2
  pattern=$3
  default=$4
  if_match=$5

  result="${default}"
  if grep -A 20 "${rule}" "${build_file}" | grep -q "${pattern}"; then
    result="${if_match}"
  fi
  echo "${result}"
}

__get_gapic_option_from_BUILD() {
  local build_file=$1
  local pattern=$2
  local gapic_option
  local file_path
  gapic_option=$(grep "${pattern}" "${build_file}" |\
    head -1 |\
    sed 's/.*\"\([^]]*\)\".*/\1/g' |\
    sed 's/^[[:space:]]*//;s/[[:space:]]*$//'
  )
  if [ -z "${gapic_option}" ] || [[ "${gapic_option}" == *"None"* ]]; then
    echo ""
    return
  fi

  if [[ "${gapic_option}" == ":"* ]] || [[ "${gapic_option}" == "*"* ]]; then
    # if gapic_option starts with : or *, remove the first character.
    gapic_option="${gapic_option:1}"
  elif [[ "${gapic_option}" == "//"* ]]; then
    # gapic option is a bazel target, use the file path and name directly.
    # remove the leading "//".
    gapic_option="${gapic_option:2}"
    # replace ":" with "/"
    gapic_option="${gapic_option//://}"
    echo "${gapic_option}"
    return
  fi

  file_path="${build_file%/*}"
  # Make sure gapic option (*.yaml or *.json) exists in proto_path; otherwise
  # reset gapic option to empty string.
  if [ -f "${file_path}/${gapic_option}" ]; then
    gapic_option="${file_path}/${gapic_option}"
  else
    echo "WARNING: file ${file_path}/${gapic_option} does not exist, reset gapic option to empty string." >&2
    gapic_option=""
  fi
  echo "${gapic_option}"
}

__get_iam_policy_from_BUILD() {
  local build_file=$1
  local contains_iam_policy
  contains_iam_policy=$(__get_config_from_BUILD \
    "${build_file}" \
    "proto_library_with_info(" \
    "//google/iam/v1:iam_policy_proto" \
    "false" \
    "true"
  )
  echo "${contains_iam_policy}"
}

__get_locations_from_BUILD() {
  local build_file=$1
  local contains_locations
  contains_locations=$(__get_config_from_BUILD \
    "${build_file}" \
    "proto_library_with_info(" \
    "//google/cloud/location:location_proto" \
    "false" \
    "true"
  )
  echo "${contains_locations}"
}

############# Functions used in test execution #############

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

############# Utility functions used in `generate_library_integration_tests.sh` #############
get_proto_only_from_BUILD() {
  local build_file=$1
  local proto_only
  proto_only=$(__get_config_from_BUILD \
    "${build_file}" \
    "java_gapic_library(" \
    "java_gapic_library" \
    "true" \
    "false"
  )
  echo "${proto_only}"
}

# Apart from proto files in proto_path, additional protos are needed in order
# to generate GAPIC client libraries.
# In most cases, these protos should be within google/ directory, which is
# pulled from googleapis as a prerequisite.
# Get additional protos in BUILD.bazel.
get_gapic_additional_protos_from_BUILD() {
  local build_file=$1
  local gapic_additional_protos="google/cloud/common_resources.proto"
  if [[ $(__get_iam_policy_from_BUILD "${build_file}") == "true" ]]; then
    gapic_additional_protos="${gapic_additional_protos} google/iam/v1/iam_policy.proto"
  fi
  if [[ $(__get_locations_from_BUILD "${build_file}") == "true" ]]; then
    gapic_additional_protos="${gapic_additional_protos} google/cloud/location/locations.proto"
  fi
  echo "${gapic_additional_protos}"
}

get_transport_from_BUILD() {
  local build_file=$1
  local transport
  transport=$(__get_config_from_BUILD \
    "${build_file}" \
    "java_gapic_library(" \
    "grpc+rest" \
    "grpc" \
    "grpc+rest"
  )
  # search again because the transport maybe `rest`.
  transport=$(__get_config_from_BUILD \
    "${build_file}" \
    "java_gapic_library(" \
    "transport = \"rest\"" \
    "${transport}" \
    "rest"
  )
  echo "${transport}"
}

get_rest_numeric_enums_from_BUILD() {
  local build_file=$1
  local rest_numeric_enums
  rest_numeric_enums=$(__get_config_from_BUILD \
    "${build_file}" \
    "java_gapic_library(" \
    "rest_numeric_enums = True" \
    "false" \
    "true"
  )
  echo "${rest_numeric_enums}"
}

get_gapic_yaml_from_BUILD() {
  local build_file=$1
  local gapic_yaml
  gapic_yaml=$(__get_gapic_option_from_BUILD "${build_file}" "gapic_yaml = ")
  echo "${gapic_yaml}"
}

get_service_config_from_BUILD() {
  local build_file=$1
  local service_config
  service_config=$(__get_gapic_option_from_BUILD "${build_file}" "grpc_service_config = ")
  echo "${service_config}"
}

get_service_yaml_from_BUILD() {
  local build_file=$1
  local service_yaml
  service_yaml=$(__get_gapic_option_from_BUILD "${build_file}" "service_yaml")
  echo "${service_yaml}"
}

get_include_samples_from_BUILD() {
  local build_file=$1
  local include_samples
  include_samples=$(__get_config_from_BUILD \
    "${build_file}" \
    "java_gapic_assembly_gradle_pkg(" \
    "include_samples = True" \
    "false" \
    "true"
  )
  echo "${include_samples}"
}

# Obtains a version from a bazel WORKSPACE file
#
# versions look like "_ggj_version="1.2.3"
# It will return 1.2.3 for such example
get_version_from_WORKSPACE() {
  version_key_word=$1
  workspace=$2
  version=$(\
    grep "${version_key_word}" "${workspace}" |\
    head -n 1 |\
    sed 's/\(.*\) = "\(.*\)"\(.*\)/\2/' |\
    sed 's/[a-zA-Z-]*//'
  )
  echo "${version}"
}

# Convenience function to clone only the necessary folders from a git repository
sparse_clone() {
  repo_url=$1
  paths=$2
  commitish=$3
  clone_dir=$(basename "${repo_url%.*}")
  rm -rf "${clone_dir}"
  git clone -n --depth=1 --no-single-branch --filter=tree:0 "${repo_url}"
  pushd "${clone_dir}"
  if [ -n "${commitish}" ]; then
    git checkout "${commitish}"
  fi
  git sparse-checkout set --no-cone ${paths}
  git checkout
  popd
}
