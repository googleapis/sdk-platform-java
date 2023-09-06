#!/bin/bash
#
# Generates one of the three possible components of a [showcase] library:
# gapic, proto or grpc libraries. The underlying generation script generates all
# three possibilities, so its results are reused for convenience.
# If $2 replace is "true" then the output of the generation will be written as
# the new showcase source code
set -x
set +e
component=$1 #one of gapic, proto, grpc
replace=$2
if [ -z "${replace}" ]; then
  replace="false"
fi
readonly SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
source "${SCRIPT_DIR}/showcase_utilities.sh"

if [ ! -d "${SCRIPT_DIR}/showcase-output" ]; then
  bash "${SCRIPT_DIR}/generate_showcase.sh"
fi
showcase_folder="${SCRIPT_DIR}/.."
case "${component}" in
    gapic )
        showcase_component_dir="${showcase_folder}/gapic-showcase/src/main" ;;
    proto )
        showcase_component_dir="${showcase_folder}/proto-gapic-showcase-v1beta1/src/main" ;;
    grpc )
        showcase_component_dir="${showcase_folder}/grpc-gapic-showcase-v1beta1/src/main" ;;
esac

if [ "${replace}" == "true" ]; then
  rm -rdf "${showcase_component_dir}/*"
  cp -r "${SCRIPT_DIR}/showcase-output/${component}-showcase-output/src/main/*" "${showcase_component_dir}/"
fi
set +x
