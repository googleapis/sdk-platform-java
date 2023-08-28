#!/bin/bash
rm -rdf google-cloud-channel-v1-java
bash generate_library_integration_test.sh \
  -p google/cloud/channel/v1 \
  -d google-cloud-channel-v1-java
