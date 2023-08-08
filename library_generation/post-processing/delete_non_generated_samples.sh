#!/bin/bash

set -ex

pushd $1

if [[ -d "samples/snippets/generated" ]]; then
  mkdir -p _TMP_/snippets
  cp -R samples/snippets/generated _TMP_/snippets
  rm -rf samples
  mv _TMP_ samples
else
  rm -rf samples
fi

popd
