#!/bin/bash
set -e

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
source $SCRIPT_DIR/utilities.sh
SDK_WORKSPACE=$SCRIPT_DIR/../WORKSPACE
GAX_PROPERTIES=$SCRIPT_DIR/../gax-java/dependencies.properties
SHOWCASE_BUILD_FILE=$SCRIPT_DIR/../showcase/BUILD.bazel

cd $SCRIPT_DIR
rm -rdf output

# clone gapic-showcase
if [ ! -d schema ]; then
  git clone https://github.com/googleapis/gapic-showcase
  pushd gapic-showcase
  showcase_version=$(get_version_from_WORKSPACE "_showcase_version" $SDK_WORKSPACE)
  git checkout "v$showcase_version"
  mv schema ..
  popd
  rm -rdf gapic-showcase
fi
if [ ! -d google ];then
  git clone https://github.com/googleapis/googleapis
  mv googleapis/google .
  mv googleapis/WORKSPACE ..
  rm -rdf googleapis
fi

GOOGLEAPIS_WORKSPACE=$SCRIPT_DIR/WORKSPACE

ggj_version=$(get_version_from_WORKSPACE _gapic_generator_java_version $SDK_WORKSPACE)
if [ $(echo $ggj_version | grep 'SNAPSHOT' | wc -l) -gt 0 ]; then
  echo 'This repo is at a snapshot version. Installing locally...'
  pushd $SCRIPT_DIR/..
  mvn clean install -DskipTests -Dclirr.skip
  popd
fi


grpc_version=$(get_version_from_properties "version.io_grpc" $GAX_PROPERTIES)
rest_numeric_enums=$(get_config_from_BUILD \
  "$SHOWCASE_BUILD_FILE" \
  "java_gapic_library(" \
  "rest_numeric_enums = False" \
  "true" \
  "false"
)
transport=$(get_config_from_BUILD \
  "$SHOWCASE_BUILD_FILE" \
  "java_gapic_library(" \
  "grpc+rest" \
  "grpc" \
  "grpc+rest"
)
include_samples=$(get_config_from_BUILD \
  "$SHOWCASE_BUILD_FILE" \
  "java_gapic_assembly_gradle_pkg(" \
  "include_samples = True" \
  "false" \
  "true"
)

mkdir output
bash generate_library.sh \
  --proto_path "schema/google/showcase/v1beta1" \
  --destination_path "output" \
  --gapic_generator_version $ggj_version \
  --grpc_version $grpc_version \
  --rest_numeric_enums $rest_numeric_enums \
  --include_samples $include_samples \
  --transport $transport &> output/out

SHOWCASE_FOLDER=$SCRIPT_DIR/../showcase
SHOWCASE_GAPIC_DIR=$SHOWCASE_FOLDER/gapic-showcase/src/main
SHOWCASE_PROTO_DIR=$SHOWCASE_FOLDER/proto-gapic-showcase-v1beta1/src
SHOWCASE_GRPC_DIR=$SHOWCASE_FOLDER/grpc-gapic-showcase-v1beta1/src
rm -rdf $SHOWCASE_GAPIC_DIR $SHOWCASE_PROTO_DIR $SHOWCASE_GRPC_DIR
cp -r $SCRIPT_DIR/output/gapic-output/src/main $SHOWCASE_GAPIC_DIR
cp -r $SCRIPT_DIR/output/proto-output/src $SHOWCASE_PROTO_DIR
cp -r $SCRIPT_DIR/output/grpc-output/src $SHOWCASE_GRPC_DIR

echo "Compare generation result..."
if [[ $(git diff --numstat $SCRIPT_DIR/../showcase | wc -l) -gt 0 ]]; then
  echo 'found differences in showcase folder'
  exit -1
fi

set +e
