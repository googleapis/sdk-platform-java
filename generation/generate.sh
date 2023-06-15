#!/bin/bash

# Script to generate client library using the WORKSPACE in this directory and
# googleapis_commit for the commit of the proto service definition.

# Example invocation
# REPO=$HOME/java-bigtable generate.sh //google/bigtable/v2
set -e

bazel_packages=$@

if [ -z "${bazel_packages}" ]; then
  echo "Please specify Bazel packages to build. E.g., //google/bigtable/v2"
  exit 1
fi

if [ -z "${REPO}" ]; then
  echo "Please specify repository location as REPO environment variable"
  exit 1
fi

basedir=$(dirname "$(readlink -f "$0")")

if [ -d target/workspace ]; then
  rm -rf target/workspace
fi
mkdir -p target/workspace
mkdir -p target/home

# The directory Bazel saves cache and bazel-bin
bazel_home=$(realpath target/home)
workspace=$(realpath target/workspace)
googleapis_commit=$(cat "${basedir}/googleapis_commit" | tr -d '\n')

cd "${workspace}"

# Create a directory for a repository
mkdir -p repo

echo "Checking out googleapis repo in ${workspace} at ${googleapis_commit}."
git clone https://github.com/googleapis/googleapis
cd googleapis
git checkout "${googleapis_commit}"

# We fix Protobuf, gRPC, and GAPIC Generator Java version
cp "${basedir}/WORKSPACE" ./WORKSPACE

docker run -t --rm  --user "$(id -u):$(id -g)" --env HOME="/bazel_home" --env USER=$(id -u -n) \
    --env BAZEL_PACKAGES="${bazel_packages}" \
    -v "${workspace}:/workspace" -w /workspace/googleapis \
    -v "${basedir}:/generation" \
    -v "${bazel_home}:/bazel_home" \
    --entrypoint "/generation/bazel_build_command.sh" \
    -it gcr.io/gapic-images/googleapis:20230301 ${bazel_packages}

# The latest as of June 13th 2023
OWLBOT_VERSION=sha256:8d01aceb509d6da986ca4ddd8f7acb11dc780997f57a231018574849b0fdc686
docker run -t --rm --user "$(id -u):$(id -g)" --env HOME=/bazel_home --env USER=$(id -u -n) \
    -v "${workspace}:/workspace" -v "${REPO}:/repo"\
    -v "${bazel_home}:/bazel_home" \
    gcr.io/cloud-devrel-public-resources/owlbot-cli@${OWLBOT_VERSION} copy-bazel-bin \
    --config-file=.github/.OwlBot.yaml \
    --source-dir "/workspace/googleapis/bazel-bin" --dest "/repo"

OWLBOT_JAVA_POSTPROCESSOR_VERSION=$(grep digest "${REPO}/.github/.OwlBot.lock.yaml" |awk '{print $2}')
docker run --rm --user "$(id -u):$(id -g)" --env USER=$(id -u -n) \
    -v "${REPO}:/repo"   -w /repo \
    "gcr.io/cloud-devrel-public-resources/owlbot-java@${OWLBOT_JAVA_POSTPROCESSOR_VERSION}"

