#!/usr/bin/env bash
set -ex

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
# load utility functions
source $SCRIPT_DIR/util.sh

# parse arguments
VALID_ARGS=$(getopt -o l:p:d:g:f:r:t:ni \
  --long proto-location:,proto-path:,destination-location:,gapic-generator-java-version:,protobuf-version:,grpc-version:,\
transport:,use-rest-numeric-enums,include-samples -- "$@")
if [[ $? -ne 0 ]]; then
    exit 1;
fi

eval set -- "$VALID_ARGS"
while [ : ]; do
  case "$1" in
    -l | --proto-location)
        PROTO_LOCATION=$(validate_arg "--proto-(l)ocation" $2)
        shift 2
        ;;
    -p | --proto-path)
        PROTO_PATH=$(validate_arg "--proto-(p)ath" $2)
        shift 2
        ;;
    -d | --destination-location)
        DESTINATION_LOCATION=$(validate_arg "--(d)estination-path" $2)
        shift 2
        ;;
    -g | --gapic-generator-java-version)
        GAPIC_GENERATOR_JAVA_VERSION=$(validate_arg "--(g)gapic-generator-java-version" $2)
        shift 2
        ;;
    -f | --protobuf-version)
        PROTOBUF_VERSION=$(validate_arg "--protobu(f)-version" $2)
        shift 2
        ;;
    -r | --grpc-version)
        GRPC_VERSION=$(validate_arg "--g(r)pc-version" $2)
        shift 2
        ;;
    -t | --transport)
        TRANSPORT=$(validate_arg "--(t)ransport" $2)
        shift 2
        ;;
    -n | --use-rest-numeric-enums)
        USE_REST_NUMERIC_ENUMS="true"
        shift
        ;;
    -i | --include-samples)
        INCLUDE_SAMPLES="true"
        shift
        ;;
    --) shift;
        break
        ;;
  esac
done


LIBRARY_GEN_OUT=$(dirname "$(readlink -f "$0")")/../library_gen_out
OUT_LAYER_FOLDER="out-layer"
REPO_ROOT="${LIBRARY_GEN_OUT}"/..
BUILD_FOLDER="${LIBRARY_GEN_OUT}/build"
mkdir -p $BUILD_FOLDER


echo "PROTO_LOCATION=$PROTO_LOCATION"
echo "DESTINATION_LOCATION=$DESTINATION_LOCATION"
echo "GAPIC_GENERATOR_JAVA_VERSION=$GAPIC_GENERATOR_JAVA_VERSION"
echo "PROTOBUF_VERSION=$PROTOBUF_VERSION"
echo "GRPC_VERSION=$GRPC_VERSION"
echo "OWLBOT_SHA=$OWLBOT_SHA"
echo "TRANSPORT=$TRANSPORT"
echo "REST_NUMERIC_ENUMS=$REST_NUMERIC_ENUMS"
echo "INCLUDE_SAMPLES=$INCLUDE_SAMPLES"
echo "OWLBOT_PY_PATH=$OWLBOT_PY_PATH"
echo "REPO_METADATA_PATH=$REPO_METADATA_PATH"
echo "ENABLE_POSTPROCESSING=$ENABLE_POSTPROCESSING"
echo "LIBRARY_GEN_OUT=$LIBRARY_GEN_OUT"
echo "OUT_LAYER_FOLDER=$OUT_LAYER_FOLDER"
echo "REPO_ROOT=$REPO_ROOT"
echo "BUILD_FOLDER=$BUILD_FOLDER"

##################### Section 0 #####################
# prepare tooling
#####################################################
# proto files from googleapis repository
cd "${REPO_ROOT}"

if [ ! -d googleapis ]; then
  git clone https://github.com/googleapis/googleapis.git
fi

GOOGLEAPIS_ROOT=${REPO_ROOT}/googleapis
PROTOS_COPY_FOLDER=${GOOGLEAPIS_ROOT}/$PROTO_PATH/
mkdir -p $PROTOS_COPY_FOLDER

cd $PROTOS_COPY_FOLDER
# the order of services entries in gapic_metadata.json is relevant to the
# order of proto file, sort the proto files with respect to their name to
# get a fixed order.
cp -r $PROTO_LOCATION/* $PROTOS_COPY_FOLDER
pushd $GOOGLEAPIS_ROOT
PROTO_FILES=$(find "./$PROTO_PATH" -type f  -name "*.proto" | sort)
popd
# pull proto files and protoc from protobuf repository
# maven central doesn't have proto files
PROTOC_ROOT=${LIBRARY_GEN_OUT}/protobuf/bin
cd "${LIBRARY_GEN_OUT}"
if [ ! -d protobuf ]; then
  curl -LJ -o protobuf.zip https://github.com/protocolbuffers/protobuf/releases/download/v"${PROTOBUF_VERSION}"/protoc-"${PROTOBUF_VERSION}"-linux-x86_64.zip
  unzip -o -q protobuf.zip -d protobuf/
  #COMMON_PROTOBUF_DESTINATION="${PROTOS_COPY_FOLDER}/google/protobuf"
  COMMON_PROTOBUF_DESTINATION="${GOOGLEAPIS_ROOT}/google/protobuf"
  mkdir -p $COMMON_PROTOBUF_DESTINATION
  cp -r protobuf/include/google/protobuf $COMMON_PROTOBUF_DESTINATION
  ls $COMMON_PROTOBUF_DESTINATION
  echo "protoc version: $("${PROTOC_ROOT}"/protoc --version)"
fi
# pull protoc-gen-grpc-java plugin from maven central
cd "${LIBRARY_GEN_OUT}"
if [ ! -f protoc-gen-grpc-java ]; then
  curl -LJ -o protoc-gen-grpc-java https://repo1.maven.org/maven2/io/grpc/protoc-gen-grpc-java/"${GRPC_VERSION}"/protoc-gen-grpc-java-"${GRPC_VERSION}"-linux-x86_64.exe
  chmod +x protoc-gen-grpc-java
fi
# gapic-generator-java
if [ ! -f gapic-generator-java.jar ]; then
  curl -LJ -o gapic-generator-java.jar https://repo1.maven.org/maven2/com/google/api/gapic-generator-java/"${GAPIC_GENERATOR_JAVA_VERSION}"/gapic-generator-java-"${GAPIC_GENERATOR_JAVA_VERSION}".jar
fi
# define utility functions
remove_empty_files() {
  FOLDER=$1
  LIBRARY_ROOT="${BUILD_FOLDER}/${OUT_LAYER_FOLDER}/${FOLDER}-${OUT_LAYER_FOLDER}"
  find $LIBRARY_ROOT/src/main/java -type f -size 0 | while read -r f; do rm -f "${f}"; done
  if [ -d $LIBRARY_ROOT/src/main/java/samples ]; then
      mv $LIBRARY_ROOT/src/main/java/samples $LIBRARY_ROOT
  fi
}


##################### Section 1 #####################
# generate grpc-*/
#####################################################
cd "${GOOGLEAPIS_ROOT}"
"${PROTOC_ROOT}"/protoc "--plugin=protoc-gen-rpc-plugin=${LIBRARY_GEN_OUT}/protoc-gen-grpc-java" \
"--rpc-plugin_out=:${BUILD_FOLDER}/java_grpc.jar" \
${PROTO_FILES}

unzip_src_files "grpc"
remove_empty_files "grpc"
##################### Section 2 #####################
# generate gapic-*/, samples/
#####################################################
"${PROTOC_ROOT}"/protoc --experimental_allow_proto3_optional --include_imports --include_source_info \
"--plugin=protoc-gen-java_gapic=${REPO_ROOT}/library_generation/gapic-generator-java-wrapper" \
"--java_gapic_out=metadata:${BUILD_FOLDER}/java_gapic_srcjar_raw.srcjar.zip" \
"--java_gapic_opt=$(get_gapic_opts)" \
${PROTO_FILES} $(search_additional_protos)

unzip -o -q "${BUILD_FOLDER}"/java_gapic_srcjar_raw.srcjar.zip -d $BUILD_FOLDER/$OUT_LAYER_DIR
# Sync'\''d to the output file name in Writer.java.
unzip -o -q "${BUILD_FOLDER}"/temp-codegen.srcjar -d "${BUILD_FOLDER}"/java_gapic_srcjar
# Resource name source files.
PROTO_DIR=${BUILD_FOLDER}/java_gapic_srcjar/proto/src/main/java
if [ ! -d "${PROTO_DIR}" ]
then
  # Some APIs don'\''t have resource name helpers, like BigQuery v2.
  # Create an empty file so we can finish building. Gating the resource name rule definition
  # on file existences go against Bazel'\''s design patterns, so we'\''ll simply delete all empty
  # files during the final packaging process (see java_gapic_pkg.bzl)
  mkdir -p "${PROTO_DIR}"
  touch "${PROTO_DIR}"/PlaceholderFile.java
fi

cd "${LIBRARY_GEN_OUT}"
# Main source files.
mv_src_files "gapic" "main"
remove_empty_files "gapic"
# Test source files.
mv_src_files "gapic" "test"
if [ "${INCLUDE_SAMPLES}" == "true" ]; then
  # Sample source files.
  mv_src_files "samples" "main"
fi
##################### Section 3 #####################
# generate proto-*/
#####################################################
cd "${GOOGLEAPIS_ROOT}"
"${PROTOC_ROOT}"/protoc "--java_out=${BUILD_FOLDER}/java_proto.jar" ${PROTO_FILES}
mv_src_files "proto" "main"
unzip_src_files "proto"
remove_empty_files "proto"

PROTO_TRANSFER_TARGET="$BUILD_FOLDER/$OUT_LAYER_FOLDER/proto-$OUT_LAYER_FOLDER/src/main/proto"
mkdir -p $PROTO_TRANSFER_TARGET
for proto_src in ${PROTO_FILES}; do
    cp -f --parents "${proto_src}" $PROTO_TRANSFER_TARGET
done
##################### Section 4 #####################
# rm tar files
#####################################################
cd "$BUILD_FOLDER"
rm -rf java_gapic_srcjar java_gapic_srcjar_raw.srcjar.zip java_grpc.jar java_proto.jar temp-codegen.srcjar

##################### Section 5 #####################
# transfer to destination path
#####################################################

