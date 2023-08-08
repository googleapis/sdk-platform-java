#!/bin/bash
# Copyright 2019 Google LLC
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

## Get the directory of the build script
scriptDir=$(realpath "$(dirname "${BASH_SOURCE[0]}")")
## cd to the parent directory, i.e. the root of the git repo
cd "${scriptDir}/../.."
source "$scriptDir/common.sh"

# Use GCP Maven Mirror
mkdir -p "${HOME}/.m2"
cp settings.xml "${HOME}/.m2"

# Publish this repo's modules to local maven to make them available for downstream libraries
mvn -B -ntp install --projects '!gapic-generator-java' \
  -Dcheckstyle.skip -Dfmt.skip -DskipTests -T 1C

SHARED_DEPS_VERSION=$(parse_pom_version java-shared-dependencies)

pushd java-shared-dependencies/target
for repo in ${REPOS_UNDER_TEST//,/ }; do # Split on comma
  git clone "https://github.com/googleapis/$repo.git" --depth=1
  update_all_poms_dependency "$repo" google-cloud-shared-dependencies "$SHARED_DEPS_VERSION"
  pushd "$repo"
  JOB_TYPE="test" ./.kokoro/build.sh
  popd
done
popd
