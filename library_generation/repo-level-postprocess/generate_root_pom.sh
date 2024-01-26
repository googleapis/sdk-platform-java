#!/bin/bash

set -e

GENERATION_DIR=$1;

# Find all Maven modules (a directory that contains pom.xml)
find . -mindepth 2 -maxdepth 2 -name pom.xml |sort --dictionary-order | xargs dirname \
    |sed -e 's|./||' | xargs -I '{}' echo "    <module>{}</module>" > /tmp/repo-modules.txt

perl -0pe 's/<modules>.*<\/modules>/<modules>\n  <\/modules>/s' ${GENERATION_DIR}/../pom.xml > ${GENERATION_DIR}/parent.pom.xml
awk -v MODULES="`awk -v ORS='\\\\n' '1' /tmp/repo-modules.txt`" '1;/<modules>/{print MODULES}' ${GENERATION_DIR}/parent.pom.xml > pom.xml
rm ${GENERATION_DIR}/parent.pom.xml