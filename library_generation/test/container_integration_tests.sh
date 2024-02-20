#!/bin/bash
# This is a wrapper of integration_tests.py that sets up the environment to run
# the script in a docker container

set -xe
if [[ -z "${SHARED_DEPENDENCIES_VERSION}" ]]; then
  echo "required environemnt variable SHARED_DEPENDENCIES_VERSION is not set"
  exit 1
fi

if [[ ! -d google-cloud-java ]]; then
  git clone https://github.com/googleapis/google-cloud-java
fi
pushd google-cloud-java
git reset --hard main
popd
if [[ $(docker volume inspect repo) != '[]' ]]; then
  docker volume rm repo
fi
docker volume create --name "repo" --opt "type=none" --opt "device=$(pwd)/google-cloud-java" --opt "o=bind"

image_id="gcr.io/cloud-devrel-public-resources/java-library-generation:${SHARED_DEPENDENCIES_VERSION}"
docker run --rm \
  -v repo:/workspace \
  -v /tmp:/tmp \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -e "RUNNING_IN_DOCKER=true" \
  -e "REPO_BINDING_VOLUME=repo" \
  -w "/src" \
  "${image_id}" \
  python -m unittest /src/test/integration_tests.py
