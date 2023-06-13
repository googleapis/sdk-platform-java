#!/bin/bash

# Script to generate client library using the WORKSPACE in this directory and
# googleapis_commit for the commit of the proto service definition.

set -e

bazel_target=$1

if [ -z "${bazel_target}" ]; then
  echo "Please specify Bazel target to build. E.g., //google/monitoring/v3:google-cloud-monitoring-v3-java"
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

echo "Checking out googleapis repo in ${workspace} at ${googleapis_commit}."
git clone https://github.com/googleapis/googleapis
cd googleapis
git checkout "${googleapis_commit}"
cp "${basedir}/WORKSPACE" ./WORKSPACE

bazelisk build "${bazel_target}"

