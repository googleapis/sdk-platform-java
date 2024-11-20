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

# Comma-delimited list of repos to test with the local java-shared-dependencies
if [ -z "${REPOS_UNDER_TEST}" ]; then
  echo "REPOS_UNDER_TEST must be set to run downstream-compatibility.sh"
  exit 1
fi


# Get the directory of the build script
scriptDir=$(realpath "$(dirname "${BASH_SOURCE[0]}")")
cd "${scriptDir}/../.." # cd to the root of this repo
source "$scriptDir/common.sh"

setup_maven_mirror

git clone https://github.com/lqiu96/cloud-opensource-java.git
pushd cloud-opensource-java
mvn clean compile -T 1C
pushd dependencies

pushd java-shared-dependencies/target
for repo in ${REPOS_UNDER_TEST//,/ }; do # Split on comma
  # Perform testing on last release, not HEAD
  last_release=$(find_last_release_version "$repo")
  repo_name=$("$repo" | cut -d '-' -f 2)
  mvn exec:java -Dexec.mainClass="com.google.cloud.tools.opensource.classpath.LinkageCheckerMain" -Dexec.args="-r --artifacts com.google.cloud:google-cloud-${repo_name}:${last_release}"  popd
done
popd
