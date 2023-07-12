#!/bin/sh
# This script is executed by ../BUILD.bazel as a final post-generation step.

set -o errexit
BAZEL_ROOT=$PWD
SHOWCASE_DIR="$BUILD_WORKSPACE_DIRECTORY/showcase"

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
    PROTO_PROJECT_DIR=proto-gapic-showcase-v1beta1
    PROTO_ARCHIVE_NAME=proto-google-cloud-showcase-v1beta1-java
    PROTO_ARCHIVE=$(find . -name "$PROTO_ARCHIVE_NAME.tar.gz")
    create_unpack_dir proto_unpacked
    PROTO_UNPACK_DIR=$PWD

    tar -xzf "$BAZEL_ROOT/$PROTO_ARCHIVE"
    delete_unneeded
    diff -ru "$SHOWCASE_DIR/$PROTO_PROJECT_DIR"/src/main/java "$PROTO_UNPACK_DIR/$PROTO_ARCHIVE_NAME"/src/main/java
    ;;

  grpc)
    GRPC_PROJECT_DIR=grpc-gapic-showcase-v1beta1
    GRPC_JAR=$(find . -name 'libshowcase_java_grpc-src.jar')
#    GRPC_JAR_EXTENDED=$(find . -name 'libshowcase_java_grpc_extended-src.jar')
    create_unpack_dir grpc_unpacked
    GRPC_UNPACK_DIR=$PWD

    jar xf "$BAZEL_ROOT/$GRPC_JAR"
#    jar xf "$BAZEL_ROOT/$GRPC_JAR_EXTENDED"
    delete_unneeded
    diff -ru "$SHOWCASE_DIR/$GRPC_PROJECT_DIR"/src/main/java/com "$GRPC_UNPACK_DIR"/com
    ;;

  gapic)
    GAPIC_PROJECT_DIR=gapic-showcase
    GAPIC_JAR=$(find . -name 'showcase_java_gapic_srcjar_raw.srcjar')
    create_unpack_dir gapic_unpacked
    GAPIC_UNPACK_DIR=$PWD

    unzip -q -c "$BAZEL_ROOT/$GAPIC_JAR" temp-codegen.srcjar | jar x
    delete_unneeded
    diff -ru "$SHOWCASE_DIR/$GAPIC_PROJECT_DIR"/src "$GAPIC_UNPACK_DIR"/src --exclude=it --exclude=test/resources --exclude=*.iml
    ;;
esac
