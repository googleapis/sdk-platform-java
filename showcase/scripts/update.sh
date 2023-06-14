#!/bin/sh
# This script is executed by ../BUILD.bazel as a final post-generation step.

set -o errexit
BAZEL_ROOT=$PWD

clear_existing() {
  cd "$BUILD_WORKSPACE_DIRECTORY/showcase/$1"
  find . -name '*.java' -not -path '*/it/*' -delete
  find . -name 'gapic_metadata.json' -delete
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
    PROTO_ARCHIVE=$(find . -name 'proto-google-cloud-showcase-v1beta1-java.tar.gz')
    clear_existing $PROTO_PROJECT_DIR
    create_unpack_dir proto_unpacked
    PROTO_UNPACK_DIR=$PWD

    tar -xzf "$BAZEL_ROOT/$PROTO_ARCHIVE"
    delete_unneeded
    cd "$BUILD_WORKSPACE_DIRECTORY/showcase/$PROTO_PROJECT_DIR"
    mkdir -p ./src
    cp -r "$PROTO_UNPACK_DIR"/proto-google-cloud-showcase-v1beta1-java/src/* ./src
    ;;

  grpc)
    GRPC_PROJECT_DIR=grpc-gapic-showcase-v1beta1
    GRPC_JAR=$(find . -name 'libshowcase_java_grpc-src.jar')
#    GRPC_JAR_EXTENDED=$(find . -name 'libshowcase_java_grpc_extended-src.jar')
    clear_existing $GRPC_PROJECT_DIR
    create_unpack_dir grpc_unpacked
    GRPC_UNPACK_DIR=$PWD

    jar xf "$BAZEL_ROOT/$GRPC_JAR"
#    jar xf "$BAZEL_ROOT/$GRPC_JAR_EXTENDED"
    cd "$BUILD_WORKSPACE_DIRECTORY/showcase/$GRPC_PROJECT_DIR"
    mkdir -p ./src/main/java/com
    cp -r "$GRPC_UNPACK_DIR"/com/* ./src/main/java/com
    ;;

  gapic)
    GAPIC_PROJECT_DIR=gapic-showcase
    GAPIC_JAR=$(find . -name 'showcase_java_gapic_srcjar_raw.srcjar')
    clear_existing $GAPIC_PROJECT_DIR
    create_unpack_dir gapic_unpacked
    GAPIC_UNPACK_DIR=$PWD

    unzip -q -c "$BAZEL_ROOT/$GAPIC_JAR" temp-codegen.srcjar | jar x
    cd "$BUILD_WORKSPACE_DIRECTORY/showcase/$GAPIC_PROJECT_DIR"
    cp -r "$GAPIC_UNPACK_DIR"/* ./
    ;;
esac

cd "${BUILD_WORKSPACE_DIRECTORY}/showcase"
delete_unneeded
