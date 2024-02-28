#!/bin/bash
# This is a wrapper of integration_tests.py that sets up the environment to run
# the script in a docker container

set -xe
if [[ -z "${TEST_IMAGE_ID}" ]]; then
  echo "required environemnt variable TEST_IMAGE_ID is not set"
  exit 1
fi

repo_volumes=""
for repo in google-cloud-java java-bigtable; do
  if [[ ! -d "${repo}" ]]; then
    git clone "https://github.com/googleapis/${repo}"
  fi
  pushd "${repo}"
  git reset --hard main
  popd

  # We use a volume to hold the repositories used in the
  # integration tests. This is because the test container creates a child
  # container using the host machine's docker socket, meaning that we can only
  # reference volumes created from within the host machine (i.e. the machine
  # running this script)
  #
  # To summarize, we create a special volume that can be referenced both in the
  # main container and in any child containers created by this one.
  volume_name="repo-${repo}"
  if [[ $(docker volume inspect "${volume_name}") != '[]' ]]; then
    docker volume rm "${volume_name}"
  fi
  docker volume create \
    --name "${volume_name}" \
    --opt "type=none" \
    --opt "device=$(pwd)/${repo}" \
    --opt "o=bind"

  repo_volumes="${repo_volumes} -v ${volume_name}:/workspace/${repo}"
done

docker run --rm \
  ${repo_volumes} \
  -v /tmp:/tmp \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -e "RUNNING_IN_DOCKER=true" \
  -e "REPO_BINDING_VOLUMES=${repo_volumes}" \
  -w "/src" \
  "${TEST_IMAGE_ID}" \
  python -m unittest /src/test/integration_tests.py
