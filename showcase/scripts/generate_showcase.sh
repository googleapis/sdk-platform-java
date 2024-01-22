#!/bin/bash
# Generates showcase without post processing (i.e. raw gapic, grpc and proto
# libraries). It will compute the showcase version from
# `sdk-platform-java/showcase/gapic-showcase/pom.xml`. The generator version is
# inferred from versions.txt
set -ex

readonly SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
lib_gen_scripts_dir="${SCRIPT_DIR}/../../library_generation/"
source "${lib_gen_scripts_dir}/test/test_utilities.sh"
source "${lib_gen_scripts_dir}/utilities.sh"
readonly perform_cleanup=$1

cd "${SCRIPT_DIR}"
mkdir -p "${SCRIPT_DIR}/output"

# takes a versions.txt file and returns its version
get_version_from_versions_txt() {
  versions=$1
  key=$2
  version=$(grep "$key:" "${versions}" | cut -d: -f3) # 3rd field is snapshot
  echo "${version}"
}

# clone gapic-showcase
if [ ! -d schema ]; then
  if [ -d gapic-showcase ]; then
    rm -rdf gapic-showcase
  fi
  # looks at sdk-platform-java/showcase/gapic-showcase/pom.xml to extract the
  # version of gapic-showcase
  # see https://github.com/googleapis/gapic-showcase/releases
  showcase_version=$(grep -e '<gapic-showcase.version>' "${SCRIPT_DIR}/../gapic-showcase/pom.xml" | cut -d'>' -f2 | cut -d'<' -f1)
    sparse_clone https://github.com/googleapis/gapic-showcase.git "schema/google/showcase/v1beta1" "v${showcase_version}"
  cd gapic-showcase
  mv schema ../output
  cd ..
  rm -rdf gapic-showcase
fi
if [ ! -d google ];then
  if [ -d googleapis ]; then
    rm -rdf googleapis
  fi
  sparse_clone https://github.com/googleapis/googleapis.git "WORKSPACE google/api google/rpc google/cloud/common_resources.proto google/longrunning google/iam/v1 google/cloud/location google/type"
  mv googleapis/google output
  rm -rdf googleapis
fi

ggj_version=$(get_version_from_versions_txt ../../versions.txt "gapic-generator-java")
gapic_additional_protos="google/iam/v1/iam_policy.proto google/cloud/location/locations.proto"
rest_numeric_enums="false"
transport="grpc+rest"
gapic_yaml=""
service_config="schema/google/showcase/v1beta1/showcase_grpc_service_config.json"
service_yaml="schema/google/showcase/v1beta1/showcase_v1beta1.yaml"
include_samples="false"
rm -rdf output/showcase-output
mkdir output/showcase-output
set +e
bash "${SCRIPT_DIR}/../../library_generation/generate_library.sh" \
  --proto_path "schema/google/showcase/v1beta1" \
  --destination_path "showcase-output" \
  --gapic_generator_version "${ggj_version}" \
  --gapic_additional_protos "${gapic_additional_protos}" \
  --rest_numeric_enums "${rest_numeric_enums}" \
  --gapic_yaml "${gapic_yaml}" \
  --service_config "${service_config}" \
  --service_yaml "${service_yaml}" \
  --include_samples "${include_samples}" \
  --transport "${transport}" 

exit_code=$?
if [ "${exit_code}" -ne 0 ]; then
  rm -rdf output
  exit "${exit_code}"
fi
set +x
