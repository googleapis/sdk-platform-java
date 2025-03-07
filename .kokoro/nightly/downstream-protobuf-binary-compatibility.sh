#!/bin/bash
# Copyright 2025 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

set -eo pipefail

# Comma-delimited list of repos to test with the local java-shared-dependencies
if [ -z "${REPOS_UNDER_TEST}" ]; then
  echo "REPOS_UNDER_TEST must be set to run downstream-protobuf-binary-compatibility.sh"
  echo "Expects a comma-delimited list: i.e REPOS_UNDER_TEST=\"java-bigtable,java-bigquery\""
  exit 1
fi

# Version of Protobuf-Java runtime to compile with
if [ -z "${PROTOBUF_RUNTIME_VERSION}" ]; then
  echo "PROTOBUF_RUNTIME_VERSION must be set to run downstream-protobuf-binary-compatibility.sh"
  echo "Expects a single Protobuf-Java runtime version i.e. PROTOBUF_RUNTIME_VERSION=\"4.28.3\""
  exit 1
fi

# Create two mappings of possible API names (Key: Maven Artifact ID Prefix, Value: Maven Group ID)
# for the libraries that should be tested.
# 1. These are special handwritten libraries in google-cloud-java that should be tested
declare -A monorepo_handwritten_libraries
monorepo_handwritten_libraries["grafeas"]="io.grafeas"
monorepo_handwritten_libraries["google-cloud-vertexai"]="com.google.cloud"
monorepo_handwritten_libraries["google-cloud-resourcemanager"]="com.google.cloud"

# 2. These are the mappings of all the downstream handwritten libraries' artifacts
declare -A downstream_handwritten_libraries
downstream_handwritten_libraries["google-cloud"]="com.google.cloud"

# Builds a string output to `artifact_list`. It contains a comma separate list of Maven GAV coordinates. Parses
# the `versions.txt` file by searching for the matching artifact_id_prefix to get the corresponding version.
function build_artifact_list() {
  local -n api_maven_mapping=$1
  for artifact_id_prefix in "${!api_maven_mapping[@]}"; do
    group_id="${api_maven_mapping[${artifact_id_prefix}]}"

    # Match all artifacts that start with the artifact_id_prefix to exclude any proto and grpc modules.
    repo_artifact_list=$(cat "versions.txt" | grep "^${artifact_id_prefix}" || true)

    # Only proceed if there are matching elements
    if [ -n "${repo_artifact_list}" ]; then
      # Exclude any matches to BOM artifacts or emulators. The repo artifact list will look like:
      # "com.google.cloud:google-cloud-accessapproval:2.60.0-SNAPSHOT,com.google.cloud:google-cloud-aiplatform:3.60.0-SNAPSHOT,"
      repo_artifact_list=$(echo "${repo_artifact_list}" | grep -vE "(bom|emulator|google-cloud-java)" | awk -F: "{\$1=\"${group_id}:\"\$1; \$2=\"\"; print}" OFS=: | sed 's/::/:/' | tr '\n' ',')
      # Remove the trailing comma after the last entry
      repo_artifact_list=${repo_artifact_list%,}

      # The first entry added is not separated with a comma. Avoids generating `,{ARTIFACT_LIST}`
      if [ -z "${artifact_list}" ]; then
        artifact_list="${repo_artifact_list}"
      else
        artifact_list="${artifact_list},${repo_artifact_list}"
      fi
    fi
  done
}

# cloud-opensource-java contains the Linkage Checker tool
git clone https://github.com/GoogleCloudPlatform/cloud-opensource-java.git
pushd cloud-opensource-java
mvn -B -ntp clean compile -T 1C
# Linkage Checker tool resides in the /dependencies subfolder
pushd dependencies

for repo in ${REPOS_UNDER_TEST//,/ }; do # Split on comma
  # Perform testing on main (with latest changes). Shallow copy as history is not important
  git clone "https://github.com/googleapis/${repo}.git" --depth=1
  pushd "${repo}"
  # Install all repo modules to ~/.m2 (there can be multiple relevant artifacts to test i.e. core, admin, control)
  mvn -B -ntp install -T 1C -DskipTests -Dclirr.skip -Denforcer.skip

  artifact_list=""
  if [ "${repo}" == "google-cloud-java" ]; then
    build_artifact_list monorepo_handwritten_libraries
  else
    build_artifact_list downstream_handwritten_libraries
  fi

  # Linkage Checker /dependencies
  popd

  echo "Artifact List: ${artifact_list}"
  # Only run Linkage Checker if the repo has any relevant artifacts to test for
  if [ -n "${artifact_list}" ]; then
    # The `-s` argument filters the linkage check problems that stem from the artifact
    program_args="-r --artifacts ${artifact_list},com.google.protobuf:protobuf-java:${PROTOBUF_RUNTIME_VERSION},com.google.protobuf:protobuf-java-util:${PROTOBUF_RUNTIME_VERSION} -s ${artifact_list}"
    echo "Linkage Checker Program Arguments: ${program_args}"
    mvn -B -ntp exec:java -Dexec.args="${program_args}" -P exec-linkage-checker
  fi
  echo "done"
done
popd
popd
