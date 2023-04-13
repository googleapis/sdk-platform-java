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

SHOWCASE_VERSION=0.26.1

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

### Round 1
# Publish this repo's modules to local maven to make them available for downstream libraries
mvn -B -ntp install --projects '!gapic-generator-java' \
  -Dcheckstyle.skip -Dfmt.skip -DskipTests

# Read the shared dependencies version
# Namespace (xmlns) prevents xmllint from specifying tag names in XPath
SHARED_DEPS_VERSION=$(sed -e 's/xmlns=".*"//' java-shared-dependencies/pom.xml | xmllint --xpath '/project/version/text()' -)

if [ -z "${SHARED_DEPS_VERSION}" ]; then
  echo "Shared dependencies version is not found in pom.xml"
  exit 1
fi

### Round 2
# Update the shared-dependencies version in google-cloud-jar-parent
git clone "https://github.com/googleapis/google-cloud-java.git" --depth=1
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
popd

### Round 3
# Run the updated java-shared-dependencies BOM against google-cloud-java
pushd google-cloud-java
source ./.kokoro/common.sh
RETURN_CODE=0
setup_application_credentials
setup_cloud "$MODULES_UNDER_TEST"
run_graalvm_tests "$MODULES_UNDER_TEST"


# Round 4
# Run showcase tests in GraalVM
popd
pushd showcase

# Start showcase server
mkdir -p /usr/src/showcase
#sudo chown -R ${USER} /usr/src/
curl --location https://github.com/googleapis/gapic-showcase/releases/download/v"${SHOWCASE_VERSION}"/gapic-showcase-"${SHOWCASE_VERSION}"-linux-amd64.tar.gz --output /usr/src/showcase/showcase-"${SHOWCASE_VERSION}"-linux-amd64.tar.gz
cd /usr/src/showcase/
tar -xf showcase-*
./gapic-showcase run

# Run showcase tests with `native` profile
apt update -y
apt install libxml2-utils
mvn test -Pnative,-showcase -Denforcer.skip=true -ntp -B

exit $RETURN_CODE
