#!/bin/bash
# Copyright 2024 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# This is the entrypoint script for post processor.
# This script is used to post processing a non-GAPIC library.
# This script should NOT be used to post processing a monorepo.

set -e

function get_library_version() {
  versions_file=$1
  artifact_id=$(jq -r '.distribution_name' .repo-metadata.json | cut -d ":" -f 2)
  library_version=$(grep "${artifact_id}" "${versions_file}" | cut -d ":" -f 2)
  echo "${library_version}"
}

function get_latest_libaries_bom_version() {
  scripts_root=$1
  save_as="${scripts_root}/owlbot/downloaded/maven-metadata.xml"
  url="https://repo1.maven.org/maven2/com/google/cloud/libraries-bom/maven-metadata.xml"
  curl -LJ -o "${save_as}" --fail -m 30 --retry 2 "$url"
  version=$(xmllint --xpath "//metadata/versioning/latest/text()" "${save_as}")
  echo "${version}"
}

scripts_root=/src
versions_file=versions.txt
is_monorepo=false
library_version=$(get_library_version "${versions_file}")
libraries_bom_version=$(get_latest_libaries_bom_version "${scripts_root}")

if [ -f "owlbot.py" ]
then
  echo "Running owlbot.py..."
  # we copy the templates to a temp folder because we need to do a special
  # modification regarding libraries_bom_version that can't be handled by the
  # synthtool library considering the way owlbot.py files are written
  export SYNTHTOOL_TEMPLATES="${scripts_root}/owlbot/templates"
  export SYNTHTOOL_LIBRARIES_BOM_VERSION="${libraries_bom_version}"
  export SYNTHTOOL_LIBRARY_VERSION="${library_version}"
  # defaults to run owlbot.py
  python3 owlbot.py
  unset SYNTHTOOL_TEMPLATES
  unset SYNTHTOOL_LIBRARIES_BOM_VERSION
  unset SYNTHTOOL_LIBRARY_VERSION
  echo "...done"
fi

# write or restore pom.xml files
echo "Generating missing pom.xml..."
python3 "${scripts_root}/owlbot/src/fix_poms.py" "${versions_file}" "${is_monorepo}"
echo "...done"

# write or restore clirr-ignored-differences.xml
echo "Generating clirr-ignored-differences.xml..."
"${scripts_root}"/owlbot/bin/write_clirr_ignore.sh "${scripts_root}"
echo "...done"

# fix license headers
echo "Fixing missing license headers..."
python3 "${scripts_root}/owlbot/src/fix-license-headers.py"
echo "...done"

echo "Reformatting source..."
"${scripts_root}"/owlbot/bin/format_source.sh "${scripts_root}"
echo "...done"
