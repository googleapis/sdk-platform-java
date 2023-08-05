#!/bin/bash
# convenience script to test locally - remove when ready for merge

GOOGLEAPIS_COMMIT=7f2c9d1
PROTOBUF_VERSION=23.2
GRPC_VERSION=1.56.1
GAPIC_GENERATOR_VERSION=2.24.0
PROTO_PATH=google/cloud/channel/v1
CONTAINS_CLOUD=true
TRANSPORT=grpc+rest
REST_NUMERIC_ENUMS=true
IS_GAPIC_LIBRARY=true
INCLUDE_SAMPLES=true
DESTINATION_PATH="null"
OWLBOT_SHA=3a95f1b9b1102865ca551b76be51d2bdb850900c4db2f6d79269e7af81ac8f84
OWLBOT_PY_PATH="null"
REPO_METADATA_PATH=/usr/local/google/home/diegomarquezp/Desktop/google-cloud-java/java-channel/.repo-metadata.json
MONOREPO_TAG=v1.16.0

bash generate_library.sh \
$GOOGLEAPIS_COMMIT \
$PROTOBUF_VERSION \
$GRPC_VERSION \
$GAPIC_GENERATOR_VERSION \
$PROTO_PATH \
$CONTAINS_CLOUD \
$TRANSPORT \
$REST_NUMERIC_ENUMS \
$IS_GAPIC_LIBRARY \
$INCLUDE_SAMPLES \
$DESTINATION_PATH \
$OWLBOT_SHA \
$OWLBOT_PY_PATH \
$REPO_METADATA_PATH \
$MONOREPO_TAG

cd google-cloud-java
rm -rdf java-channel/*
cp -r ../../library_gen_out/workspace/* java-channel/


