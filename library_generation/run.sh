#!/bin/bash
# convenience script to test locally - remove when ready for merge
set -x

PROTO_PATH=~/Desktop/sdk-platform-java/showcase/proto-gapic-showcase-v1beta1/src/main/proto/external/com_google_gapic_showcase/schema/google/showcase/v1beta1
DESTINATION_PATH=~/Desktop/sdk-platform-java/showcase
GAPIC_GENERATOR_VERSION=2.24.0
PROTOBUF_VERSION=23.2
GRPC_VERSION=1.56.1
OWLBOT_SHA=3a95f1b9b1102865ca551b76be51d2bdb850900c4db2f6d79269e7af81ac8f84
TRANSPORT=grpc+rest # grpc+rest or grpc
REST_NUMERIC_ENUMS=true # true or false
INCLUDE_SAMPLES=false # true or false
OWLBOT_PY_PATH="null"
REPO_METADATA_PATH=${12}
ENABLE_POSTPROCESSING=${13}

bash generate_library.sh \
$PROTO_PATH \
$DESTINATION_PATH \
$GAPIC_GENERATOR_VERSION \
$PROTOBUF_VERSION \
$GRPC_VERSION \
$OWLBOT_SHA \
$TRANSPORT \
$REST_NUMERIC_ENUMS \
$INCLUDE_SAMPLES \
$OWLBOT_PY_PATH \
$REPO_METADATA_PATH \
$ENABLE_POSTPROCESSING

#cd ~/Desktop/sdk-platform-java/showcase
#cp -r ~/Desktop/sdk-platform-java/library_gen_out/workspace/* ~/Desktop/sdk-platform-java/showcase
#cd ~/Desktop/sdk-platform-java/showcase
set +x
