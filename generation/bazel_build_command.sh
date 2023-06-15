#!/bin/bash

bazel_packages=$@

if [ -z "${bazel_packages}" ]; then
  echo 'The arguments bazel_packages are empty'
  exit 1
fi

# Query from BazelBot
# https://github.com/googleapis/repo-automation-bots/blob/main/packages/bazel-bot/docker-image/generate-googleapis-gen.sh

targets=""
for package in ${bazel_packages}; do
  targets+=$(bazelisk query "filter('-java$', kind('rule', ${package}:*))" \
    | grep -v -E ":(proto|grpc|gapic)-.*-java$")
  targets+=" "
done

echo "Building ${targets}"
bazelisk build ${targets}

echo "Finished building the targets."
