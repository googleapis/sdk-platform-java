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

if [ -z "${MODULES_UNDER_TEST}" ]; then
  echo "MODULES_UNDER_TEST must be set to run downstream-build.sh"
  exit 1
fi
# Use default value for REPOS_UNDER_TEST if unset. If set to empty string, maintain empty string.
#
# java-storage is currently the only downstream repo that doesn't require cloud resources for its
#   GraalVM integration tests.
REPOS_UNDER_TEST=${REPOS_UNDER_TEST-"java-storage"}

## Get the directory of the build script
scriptDir=$(realpath "$(dirname "${BASH_SOURCE[0]}")")
cd "${scriptDir}/../.." # git repo root
source "$scriptDir/common.sh"

# Use GCP Maven Mirror
mkdir -p "${HOME}/.m2"
cp settings.xml "${HOME}/.m2"

### Round 1
# Publish this repo's modules to local maven to make them available for downstream libraries
mvn -B -ntp install --projects '!gapic-generator-java' \
  -Dcheckstyle.skip -Dfmt.skip -DskipTests

SHARED_DEPS_VERSION=$(parse_pom_version java-shared-dependencies)

### Round 2 : Run showcase integration tests in GraalVM
pushd showcase/gapic-showcase
SHOWCASE_VERSION=$(mvn help:evaluate -Dexpression=gapic-showcase.version -q -DforceStdout)
popd
# Start showcase server
mkdir -p /usr/src/showcase
curl --location https://github.com/googleapis/gapic-showcase/releases/download/v"${SHOWCASE_VERSION}"/gapic-showcase-"${SHOWCASE_VERSION}"-linux-amd64.tar.gz --output /usr/src/showcase/showcase-"${SHOWCASE_VERSION}"-linux-amd64.tar.gz
pushd /usr/src/showcase/
tar -xf showcase-*
./gapic-showcase run &
popd
# Run showcase tests with `native` profile
pushd showcase
mvn test -Pnative,-showcase -Denforcer.skip=true -ntp -B
popd

### Round 3
# Update the shared-dependencies version in google-cloud-jar-parent
git clone "https://github.com/googleapis/google-cloud-java.git" --depth=1
update_all_poms_dependency google-cloud-java google-cloud-shared-dependencies "$SHARED_DEPS_VERSION"

### Round 4
# Run the updated java-shared-dependencies BOM against google-cloud-java integration tests
cd google-cloud-java
source ./.kokoro/common.sh
RETURN_CODE=0
setup_application_credentials
setup_cloud "$MODULES_UNDER_TEST"
run_graalvm_tests "$MODULES_UNDER_TEST"
# Exit must occur in google-cloud-java directory to correctly destroy IT resources
exit "$RETURN_CODE"
