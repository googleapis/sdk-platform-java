#!/bin/bash
rm -rdf google-cloud-channel-v1
bash generate_library_integration_test.sh \
  -p google/cloud/channel/v1 \
  -d google-cloud-channel-v1
