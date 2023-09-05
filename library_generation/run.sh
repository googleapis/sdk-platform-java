#!/bin/bash
rm -rdf google-cloud-policysimulator
if [ -d google-cloud-java ]; then
  pushd google-cloud-java
  git reset --hard
  git clean -d -f
  popd
fi 
workspace=$(pwd)
bash generate_library_integration_test.sh \
  -p google/cloud/policysimulator/v1 \
  -m java-policysimulator \
  -d google-cloud-policysimulator-v1 \
  -s 31c8276a1bfb43766597d32645721c029cb94571f1b8d996cb2c290744fe52f9
cd $workspace
cp -r $workspace/google-cloud-policysimulator-v1/workspace/* $workspace/google-cloud-java/java-policysimulator

