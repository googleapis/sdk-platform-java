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

file=''
dependency_list=''

function print_help() {
  echo "Unexpected input argument for this script."
  echo "Use -f for the directory of the upper-bound dependencies file."
  echo "Use -l for a comma-separate list of dependencies to test (Format: dep1=1.0,dep2=2.0)"
}

while getopts 'f:l:' flag; do
  case "${flag}" in
    f) file="${OPTARG}" ;;
    l) dependency_list="${OPTARG}" ;;
    *) print_help && exit 1
  esac
done

if [[ -z "${file}" && -z "${dependency_list}" ]]; then
  print_help && exit 1
fi

MAVEN_COMMAND="mvn verify -Penable-integration-tests -Dclirr.skip -Dcheckstyle.skip -Dfmt.skip "

# Check if a list of dependencies was provided as an argument. If the list of dependency inputted
# is empty, then run with the upper-bound dependencies file
if [ -z "${dependency_list}" ]; then
  UPPER_BOUND_DEPENDENCY_FILE=$file

  if [ ! -e "${UPPER_BOUND_DEPENDENCY_FILE}" ]; then
    echo "The inputted upper-bound dependency file '${UPPER_BOUND_DEPENDENCY_FILE}' does not exist"
    exit 1
  fi

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
else # List of dependencies was inputted
  # Set the Internal Field Separator (IFS) to a comma.
  # This tells 'read' to split the string by commas into an array named DEPS.
  # The 'read -ra' command reads the input into an array.
  IFS=',' read -ra DEPS <<< "${dependency_list}"

  # Loop through each item in the DEPS array.
  for DEP_PAIR in "${DEPS[@]}"; do
    # Skip any empty items that might result from trailing commas.
    if [ -z "$DEP_PAIR" ]; then
      continue
    fi

    # Extract the dependency name and version
    # We use 'cut' to split the line by '=' and trim whitespace
    # The sed command is used to remove potential trailing whitespace from the version number
    dependency=$(echo "${DEP_PAIR}" | cut -d'=' -f1 | tr -d '[:space:]')
    version=$(echo "${DEP_PAIR}" | cut -d'=' -f2 | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')

    # Append the formatted property to the PROPERTIES string.
    MAVEN_COMMAND+=" -D${dependency}.version=${version}"
  done
fi

# Run the generated maven command to test with the dependency versions
$MAVEN_COMMAND