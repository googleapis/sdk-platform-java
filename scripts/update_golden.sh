#!/bin/sh

set -o errexit

API_NAME=$1
RAW_SRCJAR=$( find . -name '*_java_gapic_srcjar_raw.srcjar' )

rm -rf srcjar_unpacked
mkdir srcjar_unpacked
cd srcjar_unpacked
UNPACK_DIR=$PWD
unzip -q -c "../${RAW_SRCJAR}" temp-codegen.srcjar | jar x

cd ${BUILD_WORKSPACE_DIRECTORY}/test/integration/goldens/${API_NAME}

# clear out existing Java and JSON files.
find . -name '*.java' -delete
find . -name 'gapic_metadata.json' -delete

cp -r ${UNPACK_DIR}/src/main/java/* ./src
cp -r ${UNPACK_DIR}/src/test/java/* ./src

mkdir -p ./samples/generated/src
mkdir -p ./src
cp -r ${UNPACK_DIR}/samples/generated/src/main/java/* ./samples/generated/src
[ -d ${UNPACK_DIR}/proto ] && cp -r ${UNPACK_DIR}/proto/src/main/java/* ./src

find . -name 'PlaceholderFile.java' -delete
find . -type d -empty -delete
