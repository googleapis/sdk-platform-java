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

### Round 1
# Publish this repo's modules to local maven to make them available for downstream libraries
mvn -B -ntp install --projects '!gapic-generator-java' \
  -Dcheckstyle.skip -Dfmt.skip -DskipTests

# Read current BOM version
GAPIC_BOM_VERSION=$(sed -e 's/xmlns=".*"//' gapic-generator-java-bom/pom.xml | xmllint --xpath '/project/version/text()' -)

### Round 2
# Run the updated GAPIC BOM against HEAD of java-shared-dependencies
git clone "https://github.com/googleapis/java-shared-dependencies.git" --depth=1
pushd java-shared-dependencies/first-party-dependencies

# Replace GAPIC BOM version
xmllint --shell pom.xml <<EOF
setns x=http://maven.apache.org/POM/4.0.0
cd .//x:artifactId[text()="gapic-generator-java-bom"]
cd ../x:version
set ${GAPIC_BOM_VERSION}
save pom.xml
EOF

echo "Modifications to java-shared-dependencies:"
git diff
echo

cd ..
mvn verify install -B -V -ntp -fae \
  -DskipTests=true \
  -Dmaven.javadoc.skip=true \
  -Dgcloud.download.skip=true \
  -Denforcer.skip=true

# Namespace (xmlns) prevents xmllint from specifying tag names in XPath
SHARED_DEPS_VERSION=$(sed -e 's/xmlns=".*"//' pom.xml | xmllint --xpath '/project/version/text()' -)

if [ -z "${SHARED_DEPS_VERSION}" ]; then
  echo "Shared dependencies version is not found in pom.xml"
  exit 1
fi
popd

### Round 3
# Run the updated java-shared-dependencies BOM against google-cloud-java
git clone "https://github.com/googleapis/google-cloud-java.git" --depth=1
pushd google-cloud-java/google-cloud-jar-parent

# Replace java-shared-dependencies version
xmllint --shell pom.xml <<EOF
setns x=http://maven.apache.org/POM/4.0.0
cd .//x:artifactId[text()="google-cloud-shared-dependencies"]
cd ../x:version
set ${SHARED_DEPS_VERSION}
save pom.xml
EOF

echo "Modifications to google-cloud-java:"
git diff
echo

cd ..
source ./.kokoro/common.sh
RETURN_CODE=0
setup_application_credentials
setup_cloud "$MODULES_UNDER_TEST"
run_graalvm_tests "$MODULES_UNDER_TEST"
exit $RETURN_CODE
