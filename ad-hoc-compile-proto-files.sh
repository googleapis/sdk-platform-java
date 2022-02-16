#!/bin/sh

set -o errexit

# Some auto-generated Java proto library code for .proto files are not published
# anywhere, so currently, we need to build them using `java_proto_library()` to
# generate .jar files.
#
# gapic_metadata.proto
# common_resources.proto
# pubsub.proto (v2)
# logging.proto (v1)

bazel --batch build \
    @com_google_googleapis//gapic/metadata:metadata_java_proto \
    @com_google_googleapis//google/logging/v2:logging_java_proto \
    @com_google_googleapis//google/pubsub/v1:pubsub_java_proto

install_jar() {
  mvn install:install-file -Dfile=bazel-bin/external/$1 -DgroupId=$2 -DartifactId=$3 -Dversion=0.0.0 -Dpackaging=jar \
    --batch-mode --no-transfer-progress
}

install_jar com_google_googleapis/gapic/metadata/libmetadata_proto-speed.jar com.google.api metadata-proto
install_jar com_google_googleapis/google/cloud/libcommon_resources_proto-speed.jar com.google.api common-resources-proto
install_jar com_google_googleapis/google/pubsub/v1/libpubsub_proto-speed.jar com.google.api pubsub-proto
install_jar com_google_googleapis/google/logging/v2/liblogging_proto-speed.jar com.google.api logging-proto
