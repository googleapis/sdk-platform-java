#!/bin/bash

# This script generates a maven command to test unit and integration tests for
# the repo. The outputted maven command will be in the rough following format
# `mvn verify ... -D{dependency.name}.version={dependency.version]`. The variables
# ${dependency.name} and ${dependency.version} come from the upper-bound dependencies
# file called `dependencies.txt` located in the root of sdk-platform-java.
#
# The upper-bound dependencies file will be in the format of:
# ${dependency.name}=${dependency.version}

set -ex

# Check if a filename was provided as an argument
if [ -z "$1" ]; then
  echo "Usage: $0 <dependency_file>"
  exit 1
fi

UPPER_BOUND_DEPENDENCY_FILE="$1"

if [ ! -e "${UPPER_BOUND_DEPENDENCY_FILE}" ]; then
    echo "The inputted upper-bound dependency file '$FILE' does not exist"
    exit 1
fi

MAVEN_COMMAND="mvn verify -Penable-integration-tests -Dclirr.skip -Dcheckstyle.skip -Dfmt.skip "

# Read the file line by line
while IFS= read -r line; do
  # Ignore comments and blank lines
  if [[ "${line}" =~ ^[[:space:]]*# ]] || [[ -z "${line}" ]]; then
    continue
  fi

  # Extract the dependency name and version
  # We use 'cut' to split the line by '=' and trim whitespace
  # The sed command is used to remove potential trailing whitespace from the version number
  dependency=$(echo "${line}" | cut -d'=' -f1 | tr -d '[:space:]')
  version=$(echo "${line}" | cut -d'=' -f2 | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')

  # Append the formatted property to the Maven command
  MAVEN_COMMAND+=" -D${dependency}=${version}"
done < "${UPPER_BOUND_DEPENDENCY_FILE}"

# Run the generated maven command to test with the dependency versions
$MAVEN_COMMAND