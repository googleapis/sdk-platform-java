#!/bin/bash
# convenience script to test locally - remove when ready for merge
set -x

PROTO_LOCATION=~/Desktop/gapic-showcase/schema/google/showcase/v1beta1/
PROTO_PATH=schema/google/showcase/v1beta1
DESTINATION_PATH=~/Desktop/sdk-platform-java/showcase
GAPIC_GENERATOR_VERSION=2.24.0
PROTOBUF_VERSION=23.2
GRPC_VERSION=1.56.1
OWLBOT_SHA=3a95f1b9b1102865ca551b76be51d2bdb850900c4db2f6d79269e7af81ac8f84
TRANSPORT=grpc+rest # grpc+rest or grpc
REST_NUMERIC_ENUMS=false # true or false
INCLUDE_SAMPLES=false # true or false
OWLBOT_PY_PATH="null"
REPO_METADATA_PATH='null'
ENABLE_POSTPROCESSING='false'

bash generate_library.sh \
    --proto-location $PROTO_LOCATION \
    --destination-location $DESTINATION_PATH \
    --gapic-generator-java-version $GAPIC_GENERATOR_VERSION \
    --protobuf-version $PROTOBUF_VERSION \
    --grpc-version $GRPC_VERSION \
    --transport $TRANSPORT 

# manual folder renaming
cp -r ~/Desktop/sdk-platform-java/library_gen_out/build/out-layer/gapic-out-layer/* ~/Desktop/sdk-platform-java/showcase/gapic-showcase
cp -r ~/Desktop/sdk-platform-java/library_gen_out/build/out-layer/grpc-out-layer/* ~/Desktop/sdk-platform-java/showcase/grpc-gapic-showcase-v1beta1
cp -r ~/Desktop/sdk-platform-java/library_gen_out/build/out-layer/proto-out-layer/* ~/Desktop/sdk-platform-java/showcase/proto-gapic-showcase-v1beta1
set +x
