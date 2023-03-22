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

## Get the directory of the build script
scriptDir=$(realpath "$(dirname "${BASH_SOURCE[0]}")")
## cd to the parent directory, i.e. the root of the git repo
cd "${scriptDir}/../.."

if [ -z "${MODULES_UNDER_TEST}" ]; then
  echo "MODULES_UNDER_TEST must be set to run downstream-build.sh"
  exit 1
fi

# Use GCP Maven Mirror
mkdir -p "${HOME}/.m2"
cp settings.xml "${HOME}/.m2"

# Publish this repo's modules to local maven to make them available for downstream libraries
mvn -B -ntp install --projects '!gapic-generator-java' \
  -Dcheckstyle.skip -Dfmt.skip -DskipTests

# Namespace (xmlns) prevents xmllint from specifying tag names in XPath
SHARED_DEPS_VERSION=$(sed -e 's/xmlns=".*"//' java-shared-dependencies/pom.xml | xmllint --xpath '/project/version/text()' -)

if [ -z "${SHARED_DEPS_VERSION}" ]; then
  echo "Shared dependencies version is not found in pom.xml"
  exit 1
fi

# Clone google-cloud-java
git clone "https://github.com/googleapis/google-cloud-java.git"

# Checkout branch to remove java-core and java-shared-dependencies
# TODO: remove this before making PR ready for review.
pushd google-cloud-java
git fetch origin
git checkout -b remove_shared_dep origin/remove_java-core_and_java-shared-dependencies
git checkout remove_shared_dep
popd

# Replace shared-dependencies version
pushd google-cloud-java/google-cloud-jar-parent
xmllint --shell pom.xml <<EOF
setns x=http://maven.apache.org/POM/4.0.0
cd .//x:artifactId[text()="google-cloud-shared-dependencies"]
cd ../x:version
set ${SHARED_DEPS_VERSION}
save pom.xml
EOF

echo "Modifications to java-shared-dependencies:"
git diff
echo

# Run GraalVM tests against updated shared-dependencies version in google-cloud-java
popd
pushd google-cloud-java
source ./.kokoro/common.sh
RETURN_CODE=0
setup_application_credentials
setup_cloud "$MODULES_UNDER_TEST"
run_graalvm_tests "$MODULES_UNDER_TEST"
exit $RETURN_CODE
