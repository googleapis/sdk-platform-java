#!/bin/bash

# Check if a filename was provided as an argument
if [ -z "$1" ]; then
  echo "Usage: $0 <dependency_file>"
  exit 1
fi

DEPENDENCY_FILE="$1"
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
  MAVEN_COMMAND+=" -D${dependency.version}=${version}"
done < "${DEPENDENCY_FILE}"

# Run the generated maven command
$MAVEN_COMMAND