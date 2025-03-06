#!/bin/bash
# Copyright 2023 Google LLC
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

pwd

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

for repo in ${REPOS_UNDER_TEST//,/ }; do # Split on comma
  if [ "${REPOS_INSTALLED}" != "true" ]; then
    # Perform source-compatibility testing on main (latest changes)
    git clone "https://github.com/googleapis/$repo.git" --depth=1
    pushd "$repo"
  fi

  # Compile the Handwritten Library with the Protobuf-Java version to test source compatibility
  # Run unit tests to help check for any behavior differences (dependant on coverage)
  mvn test -B -V -ntp \
      -Dclirr.skip=true \
      -Denforcer.skip=true \
      -Dmaven.javadoc.skip=true \
      -Dprotobuf.version=${PROTOBUF_RUNTIME_VERSION} \
      -T 1C
done