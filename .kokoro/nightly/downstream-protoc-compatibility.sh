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
set -x

# Comma-delimited list of repos to test with the local java-shared-dependencies
if [ -z "${REPOS_UNDER_TEST}" ]; then
  echo "REPOS_UNDER_TEST must be set to run downstream-protoc-compatibility.sh"
  echo "Expects a comma-delimited list: i.e REPOS_UNDER_TEST=\"java-bigtable,java-bigquery\""
  exit 1
fi

# Version of Protoc to use
if [ -z "${PROTOC_VERSION}" ]; then
  echo "PROTOC_VERSION must be set to run downstream-protoc-compatibility.sh"
  echo "Expects a single Protoc version i.e. PROTOC_VERSION=\"29.0\""
  exit 1
fi

# Version of Protobuf-Java runtime to compile with
if [ -z "${PROTOBUF_RUNTIME_VERSION}" ]; then
  echo "PROTOBUF_RUNTIME_VERSION must be set to run downstream-protoc-compatibility.sh"
  echo "Expects a single Protobuf-Java runtime version i.e. PROTOBUF_RUNTIME_VERSION=\"4.28.3\""
  exit 1
fi

root_path=$(pwd)

sed -i "s/ARG PROTOC_VERSION=[0-9.]*/ARG PROTOC_VERSION=${PROTOC_VERSION}/g" .cloudbuild/library_generation/library_generation.Dockerfile
cat .cloudbuild/library_generation/library_generation.Dockerfile

DOCKER_BUILDKIT=1 docker build \
  -f .cloudbuild/library_generation/library_generation.Dockerfile \
  -t local:image-tag \
  .

LOCAL_GENERATOR_VERSION=$(mvn \
  org.apache.maven.plugins:maven-help-plugin:evaluate \
  -Dexpression=project.version \
  -pl gapic-generator-java \
  -DforceStdout \
  -q)

git clone https://github.com/googleapis/googleapis.git

git clone https://github.com/lqiu96/cloud-opensource-java.git
pushd cloud-opensource-java
git checkout source-filter
mvn -B -ntp clean compile
linkage_path="$(pwd)/dependencies"
popd

for repo in ${REPOS_UNDER_TEST//,/ }; do # Split on comma
  if [[ "$repo" == "google-cloud-java" ]]; then
    continue
  fi

  git clone "https://github.com/googleapis/$repo.git" --depth=1

  if [ ! -d "${repo}/generation_config.yaml" ]; then
      continue
  fi

  pushd "${repo}"

  docker run \
    --rm \
    -u "$(id -u):$(id -g)" \
    -v "$(pwd):/workspace" \
    -e GENERATOR_VERSION="${LOCAL_GENERATOR_VERSION}" \
    -v "${root_path}/googleapis":/workspace/apis \
    local:image-tag \
    --generation-config-path=/workspace/generation_config.yaml \
    --repository-path=/workspace \
    --api-definitions-path=/workspace/apis

  # Compile the Handwritten Library with the Protobuf-Java version to test source compatibility
  mvn clean compile -B -V -ntp \
      -Dclirr.skip=true \
      -Denforcer.skip=true \
      -Dmaven.javadoc.skip=true \
      -Dprotobuf.version=${PROTOBUF_RUNTIME_VERSION} \
      -T 1C

  mvn -B -ntp install -T 1C -DskipTests -Dclirr.skip

  # Match all artifacts that start with google-cloud (rules out proto and grpc modules)
  # Exclude any matches to BOM artifacts or emulators
  ARTIFACT_LIST=$(cat "versions.txt" | grep "^google-cloud" | grep -vE "(bom|emulator)" | tr '\n' ',')
  ARTIFACT_LIST=${ARTIFACT_LIST%,}

  echo "Found artifacts ${ARTIFACT_LIST}"
  popd

  for artifact in ${ARTIFACT_LIST//,/ }; do
    artifact_id=$(echo "${artifact}" | tr ':' '\n' | head -n 1)
    version=$(echo "${artifact}" | tr ':' '\n' | tail -n 1)

    maven_coordinates="com.google.cloud:${artifact_id}:${version}"
    echo "Using ${maven_coordinates}"

    # The `-s` argument filters the linkage check problems that stem from the artifact
    program_args="-r --artifacts ${maven_coordinates},com.google.protobuf:protobuf-java:${PROTOBUF_RUNTIME_VERSION} -s ${maven_coordinates}"
    echo "Linkage Checker Program Arguments: ${program_args}"
    pushd "${linkage_path}"
    mvn -B -ntp exec:java -Dexec.mainClass="com.google.cloud.tools.opensource.classpath.LinkageCheckerMain" -Dexec.args="${program_args}"
    popd
  done
done
