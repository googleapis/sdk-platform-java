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
  echo "REPOS_UNDER_TEST must be set to run downstream-protobuf-source-compatibility.sh"
  echo "Expects a comma-delimited list: i.e REPOS_UNDER_TEST=\"java-bigtable,java-bigquery\""
  exit 1
fi

# Version of Protobuf-Java runtime to compile with
if [ -z "${PROTOBUF_RUNTIME_VERSION}" ]; then
  echo "PROTOBUF_RUNTIME_VERSION must be set to run downstream-protobuf-source-compatibility.sh"
  echo "Expects a single Protobuf-Java runtime version i.e. PROTOBUF_RUNTIME_VERSION=\"4.28.3\""
  exit 1
fi

# Create a Map of possible API names (Key: Maven Artifact ID Prefix, Value: Maven Group ID)
# Non-Cloud APIs have a different Group ID
declare -A api_maven_map
api_maven_map["google-cloud"]="com.google.cloud"
api_maven_map["grafeas"]="io.grafeas"
api_maven_map["google-maps"]="com.google.maps"
api_maven_map["google-shopping"]="com.google.shopping"

# cloud-opensource-java contains the Linkage Checker tool
git clone https://github.com/GoogleCloudPlatform/cloud-opensource-java.git
pushd cloud-opensource-java
mvn -B -ntp clean compile -T 1C
# Linkage Checker tool resides in the /dependencies subfolder
pushd dependencies

for repo in ${REPOS_UNDER_TEST//,/ }; do # Split on comma
  # Perform binary-compatibility testing on main (latest changes) and do not pull history
  git clone "https://github.com/googleapis/$repo.git" --depth=1
  pushd "$repo"
  # Install all repo modules to ~/.m2 (there can be multiple relevant artifacts to test i.e. core, admin, control)
  mvn -B -ntp install -T 1C -DskipTests -Dclirr.skip -Denforcer.skip

  artifact_list=""
  for artifact_id_prefix in "${!api_maven_map[@]}"; do
    group_id="${api_maven_map[${artifact_id_prefix}]}"

    # Match all artifacts that start with the artifact_id_prefix to exclude any proto and grpc modules.
    repo_artifact_list=$(cat "versions.txt" | grep "^${artifact_id_prefix}" || true)
    # If there are no matching artifacts, then skip. Only google-cloud-java has non-cloud APIs
    if [ -z "${repo_artifact_list}" ]; then
      continue
    fi

    # Exclude any matches to BOM artifacts or emulators. The repo artifact list will look like:
    # "com.google.cloud:google-cloud-accessapproval:2.60.0-SNAPSHOT,com.google.cloud:google-cloud-aiplatform:3.60.0-SNAPSHOT,"
    repo_artifact_list=$(echo "${repo_artifact_list}" | grep -vE "(bom|emulator|google-cloud-java)" | awk -F: "{\$1=\"${group_id}:\"\$1; \$2=\"\"; print}" OFS=: | sed 's/::/:/' | tr '\n' ',')
    # Remove the trailing comma after the last entry
    repo_artifact_list=${repo_artifact_list%,}
    artifact_list="${artifact_list},${repo_artifact_list}"
  done

  # Linkage Checker /dependencies
  popd

  if [ -n "${artifact_list}" ]; then
    echo "Found artifacts ${artifact_list}"
    # The `-s` argument filters the linkage check problems that stem from the artifact
    program_args="-r --artifacts ${artifact_list},com.google.protobuf:protobuf-java:${PROTOBUF_RUNTIME_VERSION},com.google.protobuf:protobuf-java-util:${PROTOBUF_RUNTIME_VERSION} -s ${artifact_list}"
    echo "Linkage Checker Program Arguments: ${program_args}"
    mvn -B -ntp exec:java -Dexec.mainClass="com.google.cloud.tools.opensource.classpath.LinkageCheckerMain" -Dexec.args="${program_args}"
  fi
done
popd
popd
