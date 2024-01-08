#!/bin/bash

set -ef

GENERATION_DIR=$1
PARENT_POM_PATH=$2

# For each library module in current working directory, this script
# sets the parent to the root pom.xml

# Run this script at the root of google-cloud-java repository

function replaceParent {
  parent_pom=$1
  # First, read the values from the parent pom.xml
  parent_version=$(perl -nle 'print $1 if m|<version>(.+)</version>|' "$parent_pom"|head -1)
  parent_group_id=$(perl -nle 'print $1 if m|<groupId>(.+)</groupId>|' "$parent_pom" |head -1)
  parent_artifact_id=$(perl -nle 'print $1 if m|<artifactId>(.+)</artifactId>|' "$parent_pom"|head -1)
  relativePath=$(echo "$parent_pom" | sed 's/\//\\\//g')

  # Search for <parent> tag in module pom and replace the next three lines -- groupId, artifcatId, and version
  perl_command="s/\s*<parent>.*?<\/parent>/\n\n  <parent>\n    <groupId>${parent_group_id}<\/groupId>\n    <artifactId>${parent_artifact_id}<\/artifactId>\n    <version>${parent_version}<\/version><!-- {x-version-update:google-cloud-java:current} -->\n    <relativePath>${relativePath}<\/relativePath>\n  <\/parent>/s"
  # execute the replacement in pom.xml
  perl -i -0pe "$perl_command" pom.xml
}

pushd "${GENERATION_DIR}"
# Then, apply the values as the parent pom of each module
for module in $(find . -maxdepth 2 -name pom.xml | sort --dictionary-order | xargs dirname); do
  # example value of module is "./java-accessapproval"
  if [[ "${module}" = *gapic-libraries-bom ]] || \
      [[ "${module}" = *google-cloud-jar-parent ]] || \
      [[ "${module}" = *google-cloud-pom-parent ]] || \
      [[ "${module}" = *java-shared-dependencies ]]; then
    continue
  fi
  echo "Processing module $module"
  pushd "${module}"

  replaceParent "${PARENT_POM_PATH}"

  # update the bom projects as well by removing parent
  if ls -1 | grep 'bom'; then
    BOM=$(ls -1 | grep 'bom')
    cd "$BOM"
    replaceParent ../../google-cloud-pom-parent/pom.xml
  fi

  popd
done
popd # GENERATION_DIR
