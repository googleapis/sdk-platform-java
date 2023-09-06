#!/bin/sh
# This script generates showcase in a temporary/untracked folder and compares
# its contents with the actual showcase libraries. 

set -o errexit
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
SHOWCASE_DIR="${SCRIPT_DIR}/.."

# generate sources
readonly REPLACE_SOURCE='false'
readonly PERFORM_CLEANUP='false'
exit 0
bash "${SCRIPT_DIR}/generate_components.sh" "${REPLACE_SOURCE}" "${PERFORM_CLEANUP}"

# compare with proto library
PROTO_PROJECT_DIR='proto-gapic-showcase-v1beta1'
diff -ru "${SHOWCASE_DIR}/${PROTO_PROJECT_DIR}/src/main/java" "${SCRIPT_DIR}/showcase-output/proto-showcase-output/src/main/java"

# compare with grpc library
GRPC_PROJECT_DIR='grpc-gapic-showcase-v1beta1'
diff -ru "${SHOWCASE_DIR}/${GRPC_PROJECT_DIR}/src/main/java/com" "${SCRIPT_DIR}/showcase-output/grpc-showcase-output/src/main/java/com"

# compare with gapic library
GAPIC_PROJECT_DIR='gapic-showcase'
diff -ru "${SHOWCASE_DIR}/${GAPIC_PROJECT_DIR}/src/main/java" "${SCRIPT_DIR}/showcase-output/gapic-showcase-output/src/main/java"
