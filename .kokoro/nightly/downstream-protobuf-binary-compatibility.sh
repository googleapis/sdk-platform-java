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

# Create a Map of Non-Cloud APIs (Key: Maven Artifact ID Prefix, Value: Maven Group ID)
declare -A nonCloudAPIs
nonCloudAPIs["grafeas"]="com.google.cloud"
nonCloudAPIs["google-maps"]="com.google.maps"
nonCloudAPIs["google-shopping"]="com.google.shopping"

# cloud-opensource-java contains the Linkage Checker tool
git clone https://github.com/GoogleCloudPlatform/cloud-opensource-java.git
pushd cloud-opensource-java
mvn -B -ntp clean compile
# Linkage Checker tool resides in the /dependencies subfolder
pushd dependencies

for repo in ${REPOS_UNDER_TEST//,/ }; do # Split on comma
  # Perform source-compatibility testing on main (latest changes)
  git clone "https://github.com/googleapis/$repo.git" --depth=1
  pushd "$repo"
  # Install all modules to ~/.m2 (there can be multiple relevant artifacts to test i.e. core, admin, control)
  mvn -B -ntp clean install -T 1C -DskipTests -Dclirr.skip -Denforcer.skip

  ARTIFACT_LIST=""
  # Match all artifacts that start with google-cloud to exclude any proto and grpc modules.
  # Additionally, exclude any matches to BOM artifacts or emulators
  # The artifact list will look like "com.google.cloud:google-cloud-accessapproval:2.60.0-SNAPSHOT,com.google.cloud:google-cloud-aiplatform:3.60.0-SNAPSHOT,"
  CLOUD_ARTIFACT_LIST=$(cat "versions.txt" | grep "^google-cloud" | grep -vE "(bom|emulator|google-cloud-java)" | awk -F: "{\$1=\"com.google.cloud:\"\$1; \$2=\"\"; print}" OFS=: | sed 's/::/:/' | tr '\n' ',')
  # Remove the trailing comma after the last entry
  CLOUD_ARTIFACT_LIST=${CLOUD_ARTIFACT_LIST%,}
  if [ -n "${CLOUD_ARTIFACT_LIST}" ]; then
    ARTIFACT_LIST="${ARTIFACT_LIST},${CLOUD_ARTIFACT_LIST}"
  fi

  # This logic is only for google-cloud-java as there are non-cloud APIs included
  if [ "${repo}" == "google-cloud-java" ]; then
    for key in "${!nonCloudAPIs[@]}"; do
      value="${nonCloudAPIs[$key]}"
      NON_CLOUD_ARTIFACT_LIST=$(cat "versions.txt" | grep "^${key}" | grep -vE "(bom|emulator|google-cloud-java)" | awk -F: "{\$1=\"${value}:\"\$1; \$2=\"\"; print}" OFS=: | sed 's/::/:/' | tr '\n' ',')
      NON_CLOUD_ARTIFACT_LIST=${NON_CLOUD_ARTIFACT_LIST%,}
      if [ -n "${NON_CLOUD_ARTIFACT_LIST}" ]; then
        ARTIFACT_LIST="${ARTIFACT_LIST},${NON_CLOUD_ARTIFACT_LIST}"
      fi
    done
  fi

  # Linkage Checker /dependencies
  popd

  if [ -n "${ARTIFACT_LIST}" ]; then
    echo "Found artifacts ${ARTIFACT_LIST}"
    # The `-s` argument filters the linkage check problems that stem from the artifact
    program_args="-r --artifacts ${ARTIFACT_LIST},com.google.protobuf:protobuf-java:${PROTOBUF_RUNTIME_VERSION},com.google.protobuf:protobuf-java-util:${PROTOBUF_RUNTIME_VERSION} -s ${ARTIFACT_LIST}"
    echo "Linkage Checker Program Arguments: ${program_args}"
    mvn -B -ntp exec:java -Dexec.mainClass="com.google.cloud.tools.opensource.classpath.LinkageCheckerMain" -Dexec.args="${program_args}"
  fi
done
popd
popd
