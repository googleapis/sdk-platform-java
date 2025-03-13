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

scriptDir=$(realpath "$(dirname "${BASH_SOURCE[0]}")")
source "${scriptDir}/common.sh"

validate_protobuf_compatibility_script_inputs

# REPOS_UNDER_TEST Env Var accepts a comma separated list of googleapis repos to test. For Github CI,
# this will be a single repo as Github will build a matrix of repos with each repo being tested in parallel.
# For local invocation, you can pass a list of repos to test multiple repos together.
for repo in ${REPOS_UNDER_TEST//,/ }; do # Split on comma
  # Perform source-compatibility testing on main (latest changes)
  git clone "https://github.com/googleapis/$repo.git" --depth=1
  pushd "$repo"

  # Compile the Handwritten Library with the Protobuf-Java version to test source compatibility
  # Run unit tests to help check for any behavior differences (dependant on coverage)
  if [ "${repo}" == "google-cloud-java" ]; then
    # For google-cloud-java, only test specific handwritten libraries
    # -am command also builds anything these libraries depend on (i.e. proto-* and grpc-* sub modules)
    # Specify the nested `google-cloud-*` path (except for grafeas as it doesn't have one) because maven -pl
    # will only build the specified folder (i.e. parent folder) and ignore all the sub-modules inside
    mvn clean test -B -V -ntp \
      -Dclirr.skip=true \
      -Denforcer.skip=true \
      -Dmaven.javadoc.skip=true \
      -Dprotobuf.version=${PROTOBUF_RUNTIME_VERSION} \
      -pl java-grafeas,java-vertexai/google-cloud-vertexai,java-resourcemanager/google-cloud-resourcemanager -am \
      -T 1C
  else
    mvn clean test -B -V -ntp \
      -Dclirr.skip=true \
      -Denforcer.skip=true \
      -Dmaven.javadoc.skip=true \
      -Dprotobuf.version=${PROTOBUF_RUNTIME_VERSION} \
      -T 1C
  fi

  popd
done