#!/bin/bash
# temporary location of post-processing logic

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
  MODULE_NAME=$PROTO_LOCATION
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
