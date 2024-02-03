#!/bin/bash
# Copyright 2023 Google LLC
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

# This is the entrypoint script for java owlbot. This is not intended to be
# called directly but rather be called from postproces_library.sh
# For reference, the positional arguments are
# 1: scripts_root: location of postprocess_library.sh
# 2: versions_file: points to a versions.txt containing versions to be applied
# both to README and pom.xml files

# The scripts assumes the CWD is the folder where postprocessing is going to be
# applied

set -ex
scripts_root=$1
versions_file=$2

# Runs template and etc in current working directory
function processModule() {
  # templates as well as retrieving files from owl-bot-staging
  echo "Generating templates and retrieving files from owl-bot-staging directory..."
  if [ -f "owlbot.py" ]
  then
    # defaults to run owlbot.py
    python3 owlbot.py
  fi
  echo "...done"

  # write or restore pom.xml files
  echo "Generating missing pom.xml..."
  python3 "${scripts_root}/owlbot/src/fix-poms.py" "${versions_file}" "true"
  echo "...done"

  # write or restore clirr-ignored-differences.xml
  echo "Generating clirr-ignored-differences.xml..."
  ${scripts_root}/owlbot/bin/write_clirr_ignore.sh "${scripts_root}"
  echo "...done"

  # fix license headers
  echo "Fixing missing license headers..."
  python3 "${scripts_root}/owlbot/src/fix-license-headers.py"
  echo "...done"

  # TODO: re-enable this once we resolve thrashing
  # restore license headers years
  # echo "Restoring copyright years..."
  # /owlbot/bin/restore_license_headers.sh
  # echo "...done"

  # ensure formatting on all .java files in the repository
  echo "Reformatting source..."
  mvn fmt:format
  echo "...done"
}

# This script can be used to process HW libraries and monorepo
# (google-cloud-java) libraries, which require a slightly different treatment
# monorepo folders have an .OwlBot.yaml file in the module folder (e.g.
# java-asset/.OwlBot.yaml), whereas HW libraries have the yaml in
# `.github/.OwlBot.yaml`
if [[ -f "$(pwd)/.OwlBot.yaml" ]]; then
  monorepo="true"
fi

if [[ "${monorepo}" == "true" ]]; then
  mv owl-bot-staging/* temp
  rm -rd owl-bot-staging/
  mv temp owl-bot-staging
fi

processModule
