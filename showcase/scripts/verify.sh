#!/bin/sh
# This script generates showcase in a temporary/untracked folder and compares
# its contents with the actual showcase libraries. 

set -o errexit
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
SHOWCASE_DIR="${SCRIPT_DIR}/.."

case $1 in
  proto)
    PROTO_PROJECT_DIR='proto-gapic-showcase-v1beta1'
    bash "${SCRIPT_DIR}/generate_component.sh" 'proto'
    diff -ru "${SHOWCASE_DIR}/${PROTO_PROJECT_DIR}/src/main/java" "${SCRIPT_DIR}/showcase-output/proto-showcase-output/src/main/java"
    ;;

  grpc)
    GRPC_PROJECT_DIR='grpc-gapic-showcase-v1beta1'
    bash "${SCRIPT_DIR}/generate_component.sh" 'grpc'
    diff -ru "${SHOWCASE_DIR}/${GRPC_PROJECT_DIR}/src/main/java/com" "${SCRIPT_DIR}/showcase-output/grpc-showcase-output/src/main/java/com"
    ;;

  gapic)
    GAPIC_PROJECT_DIR='gapic-showcase'
    bash "${SCRIPT_DIR}/generate_component.sh" 'gapic'
    diff -ru "${SHOWCASE_DIR}/${GAPIC_PROJECT_DIR}/src/main/java" "${SCRIPT_DIR}/showcase-output/gapic-showcase-output/src/main/java"
    ;;
esac
