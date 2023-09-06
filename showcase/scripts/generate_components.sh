#!/bin/bash
#
# Generates the three components of the showcase library:
# If $1 (replace) is "true" then the output of the generation will be written as
# the new showcase source code
# If $2 (perform_cleanup) is "true" then the generated library will be deleted
# along with the necessary tools to generate it
set -xe
replace=$1
perform_cleanup=$2
if [ -z "${replace}" ]; then
  replace="false"
fi
readonly SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

if [ ! -d "${SCRIPT_DIR}/showcase-output" ]; then
  bash "${SCRIPT_DIR}/generate_showcase.sh"
fi

if [ "${replace}" == "true" ]; then
  showcase_folder="${SCRIPT_DIR}/.."

  # replace contents of gapic library
  gapic_component_dir="${showcase_folder}/gapic-showcase/src/main"
  rm -rdf "${gapic_component_dir}/*"
  cp -r "${SCRIPT_DIR}/showcase-output/gapic-showcase-output/src/main"/* "${gapic_component_dir}/"

  # replace contents of proto library
  proto_component_dir="${showcase_folder}/proto-gapic-showcase-v1beta1/src/main"
  rm -rdf "${proto_component_dir}/*"
  cp -r "${SCRIPT_DIR}/showcase-output/proto-showcase-output/src/main"/* "${proto_component_dir}/"

  # replace contents of grpc library
  grpc_component_dir="${showcase_folder}/grpc-gapic-showcase-v1beta1/src/main"
  rm -rdf "${grpc_component_dir}/*"
  cp -r "${SCRIPT_DIR}/showcase-output/grpc-showcase-output/src/main"/* "${grpc_component_dir}/"
fi

# cleanup
if [ "${perform_cleanup}" == 'true' ]; then
  cd "${SCRIPT_DIR}"
  rm -rdf gapic-generator-java* google schema protobuf-* protoc-gen-grpc-java* showcase-output out
fi
set +xe
