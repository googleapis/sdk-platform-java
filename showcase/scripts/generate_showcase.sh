#!/bin/bash
# Generates showcase without post processing (i.e. raw gapic, grpc and proto
# libraries). It will compute the showcase version from
# `sdk-platform-java/showcase/gapic-showcase/pom.xml`. The generator version is
# inferred from versions.txt
set -ex

readonly SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
lib_gen_scripts_dir="${SCRIPT_DIR}/../../library_generation/"
source "${lib_gen_scripts_dir}/utilities.sh"
readonly perform_cleanup=$1

cd "${SCRIPT_DIR}"

# clone gapic-showcase
if [ ! -d schema ]; then
  if [ -d gapic-showcase ]; then
    rm -rdf gapic-showcase
  fi
  # looks at sdk-platform-java/showcase/gapic-showcase/pom.xml to extract the
  # version of gapic-showcase
  # see https://github.com/googleapis/gapic-showcase/releases
  showcase_version=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="properties"]/*[local-name()="gapic-showcase.version"]/text()' "${SCRIPT_DIR}/../gapic-showcase/pom.xml")
  sparse_clone https://github.com/googleapis/gapic-showcase.git "schema/google/showcase/v1beta1" "v${showcase_version}"
  cd gapic-showcase
  mv schema ..
  cd ..
  rm -rdf gapic-showcase
fi
if [ ! -d google ];then
  if [ -d googleapis ]; then
    rm -rdf googleapis
  fi
  sparse_clone https://github.com/googleapis/googleapis.git "WORKSPACE google/api google/rpc google/cloud/common_resources.proto google/longrunning google/iam/v1 google/cloud/location google/type"
  mv googleapis/google .
  rm -rdf googleapis
fi


ggj_version=$(get_version_from_versions_txt ../../versions.txt "gapic-generator-java")
rest_numeric_enums="false"
transport="grpc+rest"
include_samples="false"
rm -rdf showcase-output
mkdir showcase-output
set +e
bash "${SCRIPT_DIR}/../../library_generation/generate_library.sh" \
  --proto_path "schema/google/showcase/v1beta1" \
  --destination_path "showcase-output" \
  --gapic_generator_version "${ggj_version}" \
  --rest_numeric_enums "${rest_numeric_enums}" \
  --include_samples "${include_samples}" \
  --transport "${transport}" &> out

exit_code=$?
if [ "${exit_code}" -ne 0 ]; then
  rm -rdf showcase-output
  exit "${exit_code}"
fi
set +x
