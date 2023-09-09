#!/usr/bin/env bash

set -eo pipefail

# Golden tests against ../utilities.sh
script_dir=$(dirname "$(readlink -f "$0")")
source "${script_dir}"/test_utilities.sh

# Convert OS type to OS architecture, the default OS architecture is
# osx-aarch_64 (used in local development).
__get_os_architecture() {
  local os_type
  local os_architecture
  os_type=$(uname -sm)
  case "${os_type}" in
    *"Linux x86_64"*)
      os_architecture="linux-x86_64"
      ;;
    *"Darwin x86_64"*)
      os_architecture="osx-x86_64"
      ;;
    *)
      os_architecture="osx-aarch_64"
      ;;
  esac
  echo "${os_architecture}"
}

__diff_and_cleanup() {
  destination=$1
  customized_diff "${destination}"
  cleanup "${destination}"
}

generate_library_success_with_valid_versions() {
  local destination="google-cloud-gkeconnect-gateway-v1beta1-java"
  cd "${script_dir}/resources/protos"
  "${script_dir}"/../generate_library.sh \
    -p google/cloud/gkeconnect/gateway/v1beta1 \
    -d ../"${destination}" \
    --gapic_generator_version 2.24.0 \
    --protobuf_version 23.2 \
    --grpc_version 1.55.1 \
    --transport grpc \
    --rest_numeric_enums false \
    --os_architecture "$(__get_os_architecture)"
  __diff_and_cleanup "${destination}"
}

generate_library_success_without_protobuf_version() {
  local destination="google-cloud-gkeconnect-gateway-v1beta1-java"
  cd "${script_dir}/resources/protos"
  "${script_dir}"/../generate_library.sh \
    -p google/cloud/gkeconnect/gateway/v1beta1 \
    -d ../"${destination}" \
    --gapic_generator_version 2.24.0 \
    --grpc_version 1.55.1 \
    --transport grpc \
    --rest_numeric_enums false \
    --os_architecture "$(__get_os_architecture)"
  __diff_and_cleanup "${destination}"
}

generate_library_success_without_grpc_version() {
  local destination="google-cloud-gkeconnect-gateway-v1beta1-java"
  cd "${script_dir}/resources/protos"
  "${script_dir}"/../generate_library.sh \
    -p google/cloud/gkeconnect/gateway/v1beta1 \
    -d ../"${destination}" \
    --gapic_generator_version 2.24.0 \
    --protobuf_version 23.2 \
    --transport grpc \
    --rest_numeric_enums false \
    --os_architecture "$(__get_os_architecture)"
  __diff_and_cleanup "${destination}"
}

generate_library_success_without_protobuf_and_grpc_version() {
  local destination="google-cloud-gkeconnect-gateway-v1beta1-java"
  cd "${script_dir}/resources/protos"
  "${script_dir}"/../generate_library.sh \
    -p google/cloud/gkeconnect/gateway/v1beta1 \
    -d ../"${destination}" \
    --gapic_generator_version 2.24.0 \
    --transport grpc \
    --rest_numeric_enums false \
    --os_architecture "$(__get_os_architecture)"
  __diff_and_cleanup "${destination}"
}

# Execute tests.
# One line per test.
test_list=(
  generate_library_success_with_valid_versions
  generate_library_success_without_protobuf_version
  generate_library_success_without_grpc_version
  generate_library_success_without_protobuf_and_grpc_version
)

pushd "${script_dir}"
execute_tests  "${test_list[@]}"
popd