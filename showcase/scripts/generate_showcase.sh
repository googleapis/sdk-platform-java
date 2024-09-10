#!/bin/bash
# Generates showcase without post processing (i.e. raw gapic, grpc and proto
# libraries). It will compute the showcase version from
# `sdk-platform-java/showcase/gapic-showcase/pom.xml`. The generator version is
# inferred from versions.txt
set -ex

readonly SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
readonly LIB_GEN_SCRIPTS_DIR="${SCRIPT_DIR}/../../library_generation/"
source "${LIB_GEN_SCRIPTS_DIR}/test/test_utilities.sh"
source "${LIB_GEN_SCRIPTS_DIR}/utils/utilities.sh"
readonly perform_cleanup=$1

cd "${SCRIPT_DIR}"
mkdir -p "${SCRIPT_DIR}/output"

get_version_from_pom() {
  target_pom="$1"
  key="$2"
  # prints the result to stdout
  grep -e "<${key}>" "${target_pom}" | cut -d'>' -f2 | cut -d'<' -f1
}

# gets the latest version of the specified artifact from versions.txt
get_version_from_versions_txt() {
  readonly VERSIONS_TXT_PATH="${SCRIPT_DIR}/../../versions.txt"
  target_artifact="$1"
  # prints the result to stdout
  grep -e "${target_artifact}" "${VERSIONS_TXT_PATH}" | cut -d: -f3
}

# clone gapic-showcase
if [ ! -d schema ]; then
  if [ -d gapic-showcase ]; then
    rm -rdf gapic-showcase
  fi
  # looks at sdk-platform-java/showcase/gapic-showcase/pom.xml to extract the
  # version of gapic-showcase
  # see https://github.com/googleapis/gapic-showcase/releases
  showcase_version=$(get_version_from_pom \
    "${SCRIPT_DIR}/../gapic-showcase/pom.xml" "gapic-showcase.version"
  )
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

# copy the generator into its well-known location. For more details,
# refer to library_generation/DEVELOPMENT.md
well_known_folder="${HOME}/.library_generation"
well_known_generator_jar_location="${well_known_folder}/gapic-generator-java.jar"
if [[ ! -d "${well_known_folder}" ]]; then
  mkdir "${well_known_folder}"
fi
if [[ -f "${well_known_generator_jar_location}" ]]; then
  echo "replacing well-known generator jar with the latest one"
  rm "${well_known_generator_jar_location}"
fi
maven_repository="$(mvn help:evaluate -Dexpression=settings.localRepository -q -DforceStdout)"
generator_version=$(get_version_from_versions_txt "gapic-generator-java")
source_jar_path="${maven_repository}/com/google/api/gapic-generator-java/${generator_version}/gapic-generator-java-${generator_version}.jar"

if [[ ! -f "${source_jar_path}" ]]; then
  echo "generator jar not found in its assumed location"
  echo "in the local repository: ${source_jar_path}"
  echo "(did you run mvn install in this repository's root?)"
  exit 1
fi
# transfer the snapshot jar into its well-known location
cp "${source_jar_path}" "${well_known_generator_jar_location}"

gapic_additional_protos="google/iam/v1/iam_policy.proto google/cloud/location/locations.proto"

path_to_generator_parent_pom="${SCRIPT_DIR}/../../gapic-generator-java-pom-parent/pom.xml"
protoc_version=$(get_version_from_pom "${path_to_generator_parent_pom}" "protobuf.version" \
  | cut -d. -f2-)
grpc_version=$(get_version_from_pom "${path_to_generator_parent_pom}" "grpc.version")
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
  --protoc_version "${protoc_version}" \
  --grpc_version "${grpc_version}" \
  --proto_path "schema/google/showcase/v1beta1" \
  --destination_path "showcase-output" \
  --gapic_additional_protos "${gapic_additional_protos}" \
  --rest_numeric_enums "${rest_numeric_enums}" \
  --gapic_yaml "${gapic_yaml}" \
  --service_config "${service_config}" \
  --service_yaml "${service_yaml}" \
  --include_samples "${include_samples}" \
  --transport "${transport}" \
  --protoc_version "25.4"

exit_code=$?
if [ "${exit_code}" -ne 0 ]; then
  rm -rdf output
  exit "${exit_code}"
fi
set +x
