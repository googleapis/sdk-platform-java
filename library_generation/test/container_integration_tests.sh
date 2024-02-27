#!/bin/bash
# This is a wrapper of integration_tests.py that sets up the environment to run
# the script in a docker container

set -xe
if [[ -z "${TEST_IMAGE_ID}" ]]; then
  echo "required environemnt variable TEST_IMAGE_ID is not set"
  exit 1
fi

if [[ ! -d google-cloud-java ]]; then
  git clone https://github.com/googleapis/google-cloud-java
fi
pushd google-cloud-java
git reset --hard main
popd

# We use a volume to hold the google-cloud-java repository used in the
# integration tests. This is because the test container creates a child
# container using the host machine's docker socket, meaning that we can only
# reference volumes created from within the host machine (i.e. the machine
# running this script)
#
# To summarize, we create a special volume that can be referenced both in the
# main container and in any child containers created by this one.
if [[ $(docker volume inspect repo) != '[]' ]]; then
  docker volume rm repo
fi
docker volume create --name "repo" --opt "type=none" --opt "device=$(pwd)/google-cloud-java" --opt "o=bind"

docker run --rm \
  -v repo:/workspace \
  -v /tmp:/tmp \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -e "RUNNING_IN_DOCKER=true" \
  -e "REPO_BINDING_VOLUME=repo" \
  -w "/src" \
  "${TEST_IMAGE_ID}" \
  python -m unittest /src/test/integration_tests.py
