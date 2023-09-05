#!/bin/bash

set -efx

# For the specified library module, this script
# sets the parent to the root pom.xml

# Arguments:
# 1 - target pom to be modified
# 2 - filesystem path to the actual parent pom
# 3 - relative path to the parent pom. Used in monorepo

target_pom=$1
parent_pom_path=$2
parent_pom_relative_path=$3
# First, read the values from the parent pom.xml
parent_version=$(perl -nle 'print $1 if m|<version>(.+)</version>|' "$parent_pom_path"|head -1)
parent_group_id=$(perl -nle 'print $1 if m|<groupId>(.+)</groupId>|' "$parent_pom_path" |head -1)
parent_artifact_id=$(perl -nle 'print $1 if m|<artifactId>(.+)</artifactId>|' "$parent_pom_path"|head -1)
relativePath=$(echo "$parent_pom_relative_path" | sed 's/\//\\\//g')

# Search for <parent> tag in module pom and replace the next three lines -- groupId, artifcatId, and version
perl_command="s/\s*<parent>.*?<\/parent>/\n\n  <parent>\n    <groupId>${parent_group_id}<\/groupId>\n    <artifactId>${parent_artifact_id}<\/artifactId>\n    <version>${parent_version}<\/version><!-- {x-version-update:google-cloud-java:current} -->\n    <relativePath>${relativePath}<\/relativePath>\n  <\/parent>/s"
# execute the replacement in pom.xml
perl -i -0pe "$perl_command" $target_pom
