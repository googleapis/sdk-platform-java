#!/bin/bash
set -ex

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR
rm -rdf output

# clone gapic-showcase
if [ ! -d schema ]; then
  git clone https://github.com/googleapis/gapic-showcase
  pushd gapic-showcase
  git checkout "v0.28.2"
  mv schema ..
  popd
  rm -rdf gapic-showcase
fi
if [ ! -d google ];then
  git clone https://github.com/googleapis/googleapis
  mv googleapis/google .
  rm -rdf googleapis
fi


mkdir output
bash generate_library.sh \
  --proto_path "schema/google/showcase/v1beta1" \
  --destination_path "output" \
  --gapic_generator_version "2.24.0" \
  --protobuf_version "23.2" \
  --grpc_version "1.56.1" \
  --rest_numeric_enums "false" \
  --transport "grpc+rest" &> output/out

SHOWCASE_FOLDER=$SCRIPT_DIR/../showcase
SHOWCASE_GAPIC_DIR=$SHOWCASE_FOLDER/gapic-showcase/src/main
SHOWCASE_PROTO_DIR=$SHOWCASE_FOLDER/proto-gapic-showcase-v1beta1/src
SHOWCASE_GRPC_DIR=$SHOWCASE_FOLDER/grpc-gapic-showcase-v1beta1/src
rm -rdf $SHOWCASE_GAPIC_DIR $SHOWCASE_PROTO_DIR $SHOWCASE_GRPC_DIR
cp -r $SCRIPT_DIR/output/gapic-output/src/main $SHOWCASE_GAPIC_DIR
cp -r $SCRIPT_DIR/output/proto-output/src $SHOWCASE_PROTO_DIR
cp -r $SCRIPT_DIR/output/grpc-output/src $SHOWCASE_GRPC_DIR

set +ex
