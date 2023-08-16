#!/bin/bash

# Prints information on how to use this script
print_usage() {
  echo "usage $(basename $0) \\
    -l|--proto-location LOCATION \\
    -d|--destination-location PATH \\
    -g|--gapic-generator-java-version VERSION \\
    -p|--protobuf-version VERSION \\
    -r|--grpc-version VERSION \\
    -t|--transport \"grpc\" or \"grpc+rest\" \\
    [-p|--proto-path PATH] \\
    [-n|--use-rest-numeric-enums] \\
    [-i|--include-samples]
  "
}

# Used to obtain the argument for a certain option
# e.g. --proto-path /path/to/proto will return "/path/to/proto"
# or will print a help message and exit if it wasn't passed
validate_arg() {
  OPTION_NAME=$1
  ARGUMENT=$2
  if [[ -z $ARGUMENT ]]; then
    print_usage
    echo "missing argument for $OPTION_NAME"
    exit 1
  fi
  echo $ARGUMENT
}

mv_src_files() {
  FOLDER=$1 # one of gapic, proto, samples
  TYPE=$2 # one of main, test
  if [ "${FOLDER}" == "samples" ]; then
    FOLDER_SUFFIX="samples/snippets/generated"
    SRC_SUFFIX="samples/snippets/generated/src/main/java/com"
  elif [ "${FOLDER}" == "proto" ]; then
    FOLDER_SUFFIX="${FOLDER}"-"${OUT_LAYER_FOLDER}"/src/"${TYPE}"
    SRC_SUFFIX="${FOLDER}/src/${TYPE}/java"
  else
    FOLDER_SUFFIX="${FOLDER}"-"${OUT_LAYER_FOLDER}"/src/"${TYPE}"
    SRC_SUFFIX="src/${TYPE}/java"
  fi
  TARGET_FOLDER="$BUILD_FOLDER/$OUT_LAYER_FOLDER/$FOLDER_SUFFIX"
  if [ "${IS_GAPIC_LIBRARY}" == "true" ]; then
    mkdir -p $TARGET_FOLDER
    cp -r "$BUILD_FOLDER/java_gapic_srcjar/$SRC_SUFFIX" $TARGET_FOLDER
  fi
  if [ "${FOLDER}" != "samples" ]; then
    rm -r -f $TARGET_FOLDER/java/META-INF
  fi
}

unzip_src_files() {
  FOLDER=$1
  JAR_FILE=java_"${FOLDER}".jar
  UNZIP_TARGET="$BUILD_FOLDER/$JAR_FILE"
  UNZIP_OUTPUT_FOLDER="${BUILD_FOLDER}/${OUT_LAYER_FOLDER}/${FOLDER}-${OUT_LAYER_FOLDER}"
  mkdir -p "$UNZIP_OUTPUT_FOLDER/src/main/java"
  unzip -q -o $UNZIP_TARGET -d "$UNZIP_OUTPUT_FOLDER/src/main/java"
  rm -r -f "$UNZIP_OUTPUT_FOLDER/src/main/java/META-INF"
}

find_additional_protos_in_yaml() {
  PATTERN=$1
  FIND_RESULT=$(grep --include=\*.yaml -rw "${PROTO_LOCATION}" -e "${PATTERN}")
  if [ -n "${FIND_RESULT}" ]; then
    echo "${FIND_RESULT}"
  fi
}

search_additional_protos() {
  ADDITIONAL_PROTOS="google/cloud/common_resources.proto" # used by every library
  IAM_POLICY=$(find_additional_protos_in_yaml "name: '*google.iam.v1.IAMPolicy'*")
  if [ -n "${IAM_POLICY}" ]; then
    ADDITIONAL_PROTOS="${ADDITIONAL_PROTOS} google/iam/v1/iam_policy.proto"
  fi
  LOCATIONS=$(find_additional_protos_in_yaml "name: '*google.cloud.location.Locations'*")
  if [ -n "${LOCATIONS}" ]; then
    ADDITIONAL_PROTOS="${ADDITIONAL_PROTOS} google/cloud/location/locations.proto"
  fi
  echo "${ADDITIONAL_PROTOS}"
}

get_gapic_opts() {
  GAPIC_CONFIG=$(find "${PROTO_LOCATION}" -type f -name "*gapic.yaml")
  if [ -z "${GAPIC_CONFIG}" ]; then
    GAPIC_CONFIG=""
  else
    GAPIC_CONFIG="gapic-config=${GAPIC_CONFIG},"
  fi
  GRPC_SERVICE_CONFIG=$(find "${PROTO_LOCATION}" -type f -name "*service_config.json")
  API_SERVICE_CONFIG=$(find "${PROTO_LOCATION}" -maxdepth 1 -type f \( -name "*.yaml" ! -name "*gapic.yaml" \))
  if [ "${REST_NUMERIC_ENUMS}" == "true" ]; then
    REST_NUMERIC_ENUMS="rest-numeric-enums,"
  else
    REST_NUMERIC_ENUMS=""
  fi
  echo "transport=${TRANSPORT},${REST_NUMERIC_ENUMS}grpc-service-config=${GRPC_SERVICE_CONFIG},${GAPIC_CONFIG}api-service-config=${API_SERVICE_CONFIG}"
}
