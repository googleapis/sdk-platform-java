#!/bin/bash

set -o errexit
set -o pipefail

API_NAME=$1

RAW_SRCJAR=$( find . -name '*_java_gapic_srcjar_raw.srcjar' )

mkdir unpacked src
cd unpacked
unzip -q -c "../${RAW_SRCJAR}" temp-codegen.srcjar | jar x
cp -rf src/main/java/* ../src
cp -rf src/test/java/* ../src
[ -d proto ] && cp -rf proto/src/main/java/* ../src
cd ..

# Remove unneeded non-Java files, like MANIFEST
find src -type f ! -name '*.java' -a ! -name '*gapic_metadata.json' -delete
find src -type f -name 'PlaceholderFile.java' -delete
find src -type d -empty -delete

tree src
tree test/integration/goldens/asset
# This will not print diff_output to the console unless `--test_output=all` option
# is enabled, it only emits the comparison results to the test.log.
diff -ru src test/integration/goldens/${API_NAME}
