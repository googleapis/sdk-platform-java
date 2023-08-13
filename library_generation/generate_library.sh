#!/usr/bin/env bash

set -ex
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

PROTO_PATH=$1
DESTINATION_PATH=$2
GAPIC_GENERATOR_VERSION=$3
PROTOBUF_VERSION=$4
GRPC_VERSION=$5
OWLBOT_SHA=$6
TRANSPORT=$7 # grpc+rest or grpc
REST_NUMERIC_ENUMS=$8 # true or false
INCLUDE_SAMPLES=$9 # true or false
OWLBOT_PY_PATH=${10}
REPO_METADATA_PATH=${11}
ENABLE_POSTPROCESSING=${12}

# commented out to keep input variables as in design
if [ -z "${IS_GAPIC_LIBRARY}" ]; then
  IS_GAPIC_LIBRARY="true"
fi
if [ -z "${INCLUDE_SAMPLES}" ]; then
  INCLUDE_SAMPLES="true"
fi
# commented out to keep input variables as in design
#if [ "${CONTAINS_CLOUD}" == true ]; then
#  OUT_LAYER_FOLDER="${OUT_LAYER_FOLDER//google/google-cloud}"
#fi

LIBRARY_GEN_OUT=$(dirname "$(readlink -f "$0")")/../library_gen_out
OUT_LAYER_FOLDER="out-layer"
REPO_ROOT="${LIBRARY_GEN_OUT}"/..
BUILD_FOLDER="${LIBRARY_GEN_OUT}/build"
mkdir -p $BUILD_FOLDER


echo "PROTO_PATH=$1"
echo "DESTINATION_PATH=$2"
echo "GAPIC_GENERATOR_VERSION=$3"
echo "PROTOBUF_VERSION=$4"
echo "GRPC_VERSION=$5"
echo "OWLBOT_SHA=$6"
echo "TRANSPORT=$7"
echo "REST_NUMERIC_ENUMS=$8"
echo "INCLUDE_SAMPLES=$9"
echo "OWLBOT_PY_PATH=${10}"
echo "REPO_METADATA_PATH=${11}"
echo "ENABLE_POSTPROCESSING=${12}"

##################### Section 0 #####################
# prepare tooling
#####################################################
# proto files from googleapis repository
cd "${REPO_ROOT}"

if [ ! -d googleapis ]; then
  git clone https://github.com/googleapis/googleapis.git
fi

GOOGLEAPIS_ROOT=${REPO_ROOT}/googleapis
PROTOS_COPY_FOLDER=${GOOGLEAPIS_ROOT}/protos-copy
mkdir -p $PROTOS_COPY_FOLDER

# we need some files referenced by the input protos (e.g.
# googleapis/google/api/annotations.proto). We can either supply them manually
# or using a specific commit. For now, this is defaulting to HEAD (not hermetic)
mkdir -p "$PROTOS_COPY_FOLDER/google/api"
cd $GOOGLEAPIS_ROOT
#
# v[\d] folders are also required
#for common_proto_path in $(find . -name '*.proto' -type f); do
  ## create path if not existing
  #mkdir -p "$PROTOS_COPY_FOLDER/$(dirname $common_proto_path)"
  #cp $common_proto_path "$PROTOS_COPY_FOLDER/$common_proto_path"
#done



cd $PROTOS_COPY_FOLDER
# the order of services entries in gapic_metadata.json is relevant to the
# order of proto file, sort the proto files with respect to their name to
# get a fixed order.
cp -r $PROTO_PATH/* $PROTOS_COPY_FOLDER
pushd $GOOGLEAPIS_ROOT
PROTO_FILES=$(find "./protos-copy" -type f  -name "*.proto" | sort)
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
  curl -LJ -o gapic-generator-java.jar https://repo1.maven.org/maven2/com/google/api/gapic-generator-java/"${GAPIC_GENERATOR_VERSION}"/gapic-generator-java-"${GAPIC_GENERATOR_VERSION}".jar
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
  FIND_RESULT=$(grep --include=\*.yaml -rw "${PROTO_PATH}" -e "${PATTERN}")
  if [ -n "${FIND_RESULT}" ]; then
    echo "${FIND_RESULT}"
  fi
}

search_additional_protos() {
  ADDITIONAL_PROTOS="google/cloud/common_resources.proto" # used by every library
  IAM_POLICY=$(find_additional_protos_in_yaml "name: google.iam.v1.IAMPolicy")
  if [ -n "${IAM_POLICY}" ]; then
    ADDITIONAL_PROTOS="${ADDITIONAL_PROTOS} google/iam/v1/iam_policy.proto"
  fi
  LOCATIONS=$(find_additional_protos_in_yaml "name: google.cloud.location.Locations")
  if [ -n "${LOCATIONS}" ]; then
    ADDITIONAL_PROTOS="${ADDITIONAL_PROTOS} google/cloud/location/locations.proto"
  fi
  echo "${ADDITIONAL_PROTOS}"
}

get_gapic_opts() {
  GAPIC_CONFIG=$(find "${PROTO_PATH}" -type f -name "*gapic.yaml")
  if [ -z "${GAPIC_CONFIG}" ]; then
    GAPIC_CONFIG=""
  else
    GAPIC_CONFIG="gapic-config=${GAPIC_CONFIG},"
  fi
  GRPC_SERVICE_CONFIG=$(find "${PROTO_PATH}" -type f -name "*service_config.json")
  API_SERVICE_CONFIG=$(find "${PROTO_PATH}" -maxdepth 1 -type f \( -name "*.yaml" ! -name "*gapic.yaml" \))
  if [ "${REST_NUMERIC_ENUMS}" == "true" ]; then
    REST_NUMERIC_ENUMS="rest-numeric-enums,"
  else
    REST_NUMERIC_ENUMS=""
  fi
  echo "transport=${TRANSPORT},${REST_NUMERIC_ENUMS}grpc-service-config=${GRPC_SERVICE_CONFIG},${GAPIC_CONFIG}api-service-config=${API_SERVICE_CONFIG}"
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
if [ "${IS_GAPIC_LIBRARY}" == "true" ]; then
  "${PROTOC_ROOT}"/protoc --experimental_allow_proto3_optional \
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

##################### Section 4 #####################
# post-processing
#####################################################
if [ -z $ENABLE_POSTPROCESSING ];
then
  echo "post processing is disabled"
  exit 0
fi
# copy repo metadata to destination library folder
WORKSPACE=$LIBRARY_GEN_OUT/workspace
mkdir $WORKSPACE
cp $REPO_METADATA_PATH $WORKSPACE/.repo-metadata.json

# call owl-bot-copy
OWLBOT_STAGING_FOLDER="$WORKSPACE/owl-bot-staging"
OWLBOT_IMAGE=gcr.io/cloud-devrel-public-resources/owlbot-java@sha256:$OWLBOT_SHA

# render owlbot yaml template
DISTRIBUTION_NAME=$(cat $REPO_METADATA_PATH | jq -r '.distribution_name // empty' | rev | cut -d: -f1 | rev)
API_SHORTNAME=$(cat $REPO_METADATA_PATH | jq -r '.api_shortname // empty')

if [ -z ${DISTRIBUTION_NAME+x} ]; then
  echo "owlbot will not use copy regex"
else
  MODULE_NAME=$PROTO_PATH
  OWLBOT_COPY_REGEX=$(cat <<-_EOT_
deep-remove-regex:
- "/${MODULE_NAME}/grpc-google-.*/src"
- "/${MODULE_NAME}/proto-google-.*/src"
- "/${MODULE_NAME}/google-.*/src"
- "/${MODULE_NAME}/samples/snippets/generated"

deep-preserve-regex:
- "/${MODULE_NAME}/google-.*/src/test/java/com/google/cloud/.*/v.*/it/IT.*Test.java"

deep-copy-regex:
- source: "/{{ proto_path }}/(v.*)/.*-java/proto-google-.*/src"
  dest: "/owl-bot-staging/${MODULE_NAME}/\$1/proto-${DISTRIBUTION_NAME}-\$1/src"
- source: "/{{ proto_path }}/(v.*)/.*-java/grpc-google-.*/src"
  dest: "/owl-bot-staging/${MODULE_NAME}/\$1/grpc-${DISTRIBUTION_NAME}-\$1/src"
- source: "/{{ proto_path }}/(v.*)/.*-java/gapic-google-.*/src"
  dest: "/owl-bot-staging/${MODULE_NAME}/\$1/${DISTRIBUTION_NAME}/src"
- source: "/{{ proto_path }}/(v.*)/.*-java/samples/snippets/generated"
  dest: "/owl-bot-staging/${MODULE_NAME}/\$1/samples/snippets/generated"
_EOT_
)
fi
OWLBOT_YAML_CONTENT=$(cat <<-_EOT_
${OWLBOT_COPY_REGEX}

api-name: ${API_SHORTNAME}
_EOT_
)

# render owlbot.py template
OWLBOT_PY_CONTENT=$(cat "$SCRIPT_DIR/post-processing/templates/owlbot.py.template")

cp -r $BUILD_FOLDER $OWLBOT_STAGING_FOLDER
echo "$OWLBOT_YAML_CONTENT" > $OWLBOT_STAGING_FOLDER/.OwlBot.yaml
echo "$OWLBOT_PY_CONTENT" > $WORKSPACE/owlbot.py
docker run --rm -v $WORKSPACE:/workspace --user $(id -u):$(id -g) $OWLBOT_IMAGE

# postprocessor cleanup
bash $SCRIPT_DIR/post-processing/update_owlbot_postprocessor_config.sh $WORKSPACE
bash $SCRIPT_DIR/post-processing/delete_non_generated_samples.sh $WORKSPACE
bash $SCRIPT_DIR/post-processing/consolidate_config.sh $WORKSPACE
bash $SCRIPT_DIR/post-processing/readme_update.sh $WORKSPACE
rm $WORKSPACE/versions.txt
if [ -z ${MONOREPO_TAG+x} ]; then
  echo "Will not add parent project to pom"
else
  pushd $SCRIPT_DIR
  [ ! -d google-cloud-java ] && git clone https://github.com/googleapis/google-cloud-java
  pushd google-cloud-java
  git reset --hard
  git checkout $MONOREPO_TAG
  PARENT_POM="$(pwd)/google-cloud-pom-parent/pom.xml"
  popd
  # rm -rdf google-cloud-java
  popd
  bash $SCRIPT_DIR/post-processing/set_parent_pom.sh $WORKSPACE $PARENT_POM

  # get existing versions.txt from downloaded monorepo
  REPO_SHORT=$(cat $REPO_METADATA_PATH | jq -r '.repo_short // empty')
  cp "$SCRIPT_DIR/google-cloud-java/versions.txt" $WORKSPACE
  pushd $WORKSPACE
  bash $SCRIPT_DIR/post-processing/apply_current_versions.sh
  popd
fi

# rename folders properly (may not be necessary after all)
pushd $WORKSPACE
GAPIC_LIB_ORIGINAL_NAME=$(find . -name 'gapic-*' | sed "s/\.\///")
GAPIC_LIB_NEW_NAME=$(echo "$GAPIC_LIB_ORIGINAL_NAME" |\
  sed 's/cloud-cloud/cloud/' | sed 's/-v[0-9a-zA-Z]-java//' | sed 's/gapic-//')
PROTO_LIB_ORIGINAL_NAME=$(find . -name 'proto-*' | sed "s/\.\///")
PROTO_LIB_NEW_NAME=$(echo "$PROTO_LIB_ORIGINAL_NAME" |\
  sed 's/cloud-cloud/cloud/' | sed 's/-java//')
GRPC_LIB_ORIGINAL_NAME=$(find . -name 'grpc-*' | sed "s/\.\///")
GRPC_LIB_NEW_NAME=$(echo "$GRPC_LIB_ORIGINAL_NAME" |\
  sed 's/cloud-cloud/cloud/' | sed 's/-java//')
# two folders exist, move the contents of one to the other
mv $GAPIC_LIB_ORIGINAL_NAME/* $GAPIC_LIB_NEW_NAME
rm -rdf $GAPIC_LIB_ORIGINAL_NAME
# just rename these two
mv $PROTO_LIB_ORIGINAL_NAME $PROTO_LIB_NEW_NAME
mv $GRPC_LIB_ORIGINAL_NAME $GRPC_LIB_NEW_NAME

for pom_file in $(find . -mindepth 0 -maxdepth 2 -name pom.xml \
    |sort --dictionary-order); do
  sed -i "s/$GRPC_LIB_ORIGINAL_NAME/$GRPC_LIB_NEW_NAME/" $pom_file
  sed -i "s/$PROTO_LIB_ORIGINAL_NAME/$PROTO_LIB_NEW_NAME/" $pom_file
  sed -i "s/$GRPC_LIB_ORIGINAL_NAME/$GRPC_LIB_NEW_NAME/" $pom_file
done
popd





