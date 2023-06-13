#!/bin/bash

# Script to generate client library using the WORKSPACE in this directory and
# googleapis_commit for the commit of the proto service definition.

# Example invocation
# REPO=$HOME/java-bigtable generate.sh //google/bigtable/v2
set -e

bazel_package=$1

if [ -z "${bazel_package}" ]; then
  echo "Please specify Bazel package to build. E.g., //google/bigtable/v2"
  exit 1
fi

if [ -z "${REPO}" ]; then
  echo "Please specify repository location as REPO environment variable"
  exit 1
fi

basedir=$(dirname "$(readlink -f "$0")")

if [ -d target/library-gen-workspace ]; then
  rm -rf target/library-gen-workspace
fi
mkdir -p target/library-gen-workspace
workspace=$(realpath target/library-gen-workspace)
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

bazelisk query  "filter("-java$', kind('rule", ${bazel_package}:*))" | bazelisk build

# The latest as of June 13th 2023
OWLBOT_VERSION=sha256:8d01aceb509d6da986ca4ddd8f7acb11dc780997f57a231018574849b0fdc686
docker run --rm --user "$(id -u):$(id -g)" --env HOME=/workspace --env USER=$(id -u -n) \
    -v "${workspace}:/workspace" -v "${REPO}:/repo"\
    gcr.io/cloud-devrel-public-resources/owlbot-cli@${OWLBOT_VERSION} copy-bazel-bin \
    --config-file=.github/.OwlBot.yaml \
    --source-dir "/workspace/googleapis/bazel-bin" --dest "/repo"

echo "Check out ${workspace} and ${REPO}"