#!/usr/bin/env bash

set -xeo pipefail
test_utilities_script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

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

download_googleapis_files_and_folders() {
  local output_folder=$1
  # checkout the master branch of googleapis/google (proto files) and WORKSPACE
  echo "Checking out googlapis repository..."
  # sparse_clone will remove folder contents first, so we have to checkout googleapis
  # only once.
  sparse_clone https://github.com/googleapis/googleapis.git "google grafeas WORKSPACE"
  pushd googleapis
  cp -r google "${output_folder}"
  cp -r grafeas "${output_folder}"
  cp -r WORKSPACE "${output_folder}"
}


# performs a deep structural comparison between the current pom in a git 
# folder and the one at HEAD.
# This function is OS-dependent, so it sources the main utilities script to
# perform detection
compare_poms() {
  target_dir=$1
  source "${test_utilities_script_dir}/../utilities.sh"
  os_architecture=$(detect_os_architecture)
  pushd "${target_dir}" &> /dev/null
  find . -name 'pom.xml' -exec cp {} {}.new \;
  find . -name 'pom.xml' -exec git checkout HEAD -- {} \;
  # compare_poms.py exits with non-zero if diffs are found
  set -e
  result=0
  if [ "${os_architecture}" == "linux-x86_64" ]; then
    find . -name 'pom.xml' -print0 | xargs -i -0 python "${test_utilities_script_dir}/compare_poms.py" {} {}.new false || result=$?
  else
    find . -name 'pom.xml' -print0 | xargs -I{} -0 python "${test_utilities_script_dir}/compare_poms.py" {} {}.new false || result=$?
  fi
  popd &> /dev/null # target_dir
  echo ${result}
}

# computes the `destination_path` variable by inspecting the contents of the
# googleapis-gen at $proto_path. 
compute_destination_path() {
  local proto_path=$1
  local output_folder=$2
  pushd "${output_folder}" &> /dev/null
  local destination_path=$(find "googleapis-gen/${proto_path}" -maxdepth 1 -name 'google-*-java' \
    | rev \
    | cut -d'/' -f1 \
    | rev
  )
  popd &> /dev/null # output_folder
  echo "${destination_path}"
}

