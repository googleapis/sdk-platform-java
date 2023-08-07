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

# In the given directory ($1),
#   update the pom.xml's dependency on the given artifact ($2) to the given version ($3)
# ex: update_dependency google-cloud-java/google-cloud-jar-parent google-cloud-shared-dependencies 1.2.3
function update_pom_dependency {
  pushd "$1" || exit 1
  xmllint --shell pom.xml <<EOF
setns x=http://maven.apache.org/POM/4.0.0
cd .//x:artifactId[text()="$2"]
cd ../x:version
set $3
save pom.xml
EOF
  popd || exit 1
}

# In the given directory ($1),
#   find and update all pom.xmls' dependencies on the given artifact ($2) to the given version ($3)
# ex: update_all_poms_dependency google-cloud-java google-cloud-shared-dependencies 1.2.3
function update_all_poms_dependency {
  pushd "$1" || exit 1
  # Recursively find all pom.xmls with a dependency on the given artifact.
  POMS=$(grep -Rl --include="pom.xml" "<artifactId>$2" .)
  for pom in $POMS; do
    update_pom_dependency "$(dirname "$pom")" "$2" "$3"
  done
  git diff
  popd || exit 1
}

# Parse the version of the pom.xml file in the given directory ($1)
# ex: VERSION=$(parse_pom_version java-shared-dependencies)
function parse_pom_version {
  # Namespace (xmlns) prevents xmllint from specifying tag names in XPath
  result=$(sed -e 's/xmlns=".*"//' "$1/pom.xml" | xmllint --xpath '/project/version/text()' -)

  if [ -z "${result}" ]; then
    echo "Version is not found in $1"
    exit 1
  fi
  echo "$result"
}
