#!/bin/sh
# This script is executed by ../BUILD.bazel as a final post-generation step.

set -o errexit
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
SHOWCASE_DIR="$SCRIPT_DIR/.."

clear_existing() {
  cd "$SHOWCASE_DIR/$1"
  find . -name '*.java' -not -path '*/it/*' -delete
  find . -name 'gapic_metadata.json' -delete
  find . -name 'reflect-config.json' -delete
  cd -
}
create_unpack_dir() {
  cd "$BAZEL_ROOT"
  rm -rf "$1"
  mkdir "$1"
  cd "$1"
}
delete_unneeded() {
  find . -name '.DS_Store' -delete
  find . -name 'PlaceholderFile.java' -delete
  find . -type d -empty -delete
}

case $1 in
  proto)
    PROTO_PROJECT_DIR='proto-gapic-showcase-v1beta1'
    bash $SCRIPT_DIR/generate_component.sh proto
    diff -ru "$SHOWCASE_DIR/$PROTO_PROJECT_DIR"/src/main/java "$SCRIPT_DIR"/showcase-output/proto-showcase-output/src/main/java
    ;;

  grpc)
    GRPC_PROJECT_DIR='grpc-gapic-showcase-v1beta1'
    bash $SCRIPT_DIR/generate_component.sh grpc
    diff -ru "$SHOWCASE_DIR/$GRPC_PROJECT_DIR"/src/main/java/com "$SCRIPT_DIR"/showcase-output/grpc-showcase-output/src/main/java/com
    ;;

  gapic)
    GAPIC_PROJECT_DIR='gapic-showcase'
    bash $SCRIPT_DIR/generate_component.sh gapic
    diff -ru "$SHOWCASE_DIR/$GAPIC_PROJECT_DIR"/src/main/java "$SCRIPT_DIR"/showcase-output/gapic-showcase-output/src/main/java
    ;;
esac
