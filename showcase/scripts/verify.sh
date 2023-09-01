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
    diff -ru "$SHOWCASE_DIR/$PROTO_PROJECT_DIR"/src/main/java "$script_dir"/showcase-output/proto-showcase-output/src/main/java
    ;;

  grpc)
    GRPC_PROJECT_DIR='grpc-gapic-showcase-v1beta1'
    bash $script_dir/generate_component.sh grpc
    diff -ru "$SHOWCASE_DIR/$GRPC_PROJECT_DIR"/src/main/java/com "$script_dir"/showcase-output/grpc-showcase-output/src/main/java/com
    ;;

  gapic)
    GAPIC_PROJECT_DIR=gapic-showcase
    GAPIC_JAR=$(find . -name 'showcase_java_gapic_srcjar_raw.srcjar')
    create_unpack_dir gapic_unpacked
    GAPIC_UNPACK_DIR=$PWD

    unzip -q -c "$BAZEL_ROOT/$GAPIC_JAR" temp-codegen.srcjar | jar x
    delete_unneeded

    # "diff --exclude" matches against file and directory basenames, not pathnames.
    # To exclude "test/resources" without excluding "main/resources", we copy the showcase project
    # to a temporary directory, and delete what we want to exclude.
    cp -r "$SHOWCASE_DIR/$GAPIC_PROJECT_DIR"/src "$GAPIC_UNPACK_DIR"/golden
    rm -r "$GAPIC_UNPACK_DIR"/golden/test/java/com/google/showcase/v1beta1/it
    rm -r "$GAPIC_UNPACK_DIR"/golden/test/resources
    diff -ru "$GAPIC_UNPACK_DIR"/golden "$GAPIC_UNPACK_DIR"/src --exclude=*.iml
    ;;
esac
