#!/bin/bash
rm -rdf google-cloud-channel-v1
pushd google-cloud-java
git reset --hard
git clean -d -f
popd
workspace=$(pwd)
bash generate_library_integration_test.sh \
  -p google/cloud/channel/v1 \
  -m java-channel \
  -d google-cloud-channel-v1
cd $workspace
cp -r $workspace/google-cloud-channel-v1/workspace/* $workspace/google-cloud-java/java-channel

