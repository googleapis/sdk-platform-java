#!/bin/bash

set -e

while IFS= read -r -d '' samples_dir; do
  pushd "${samples_dir}/.."
  if [[ -d "samples/snippets/generated" ]]; then
    mkdir -p _TMP_/snippets
    cp -R samples/snippets/generated _TMP_/snippets
    rm -rf samples
    mv _TMP_ samples
  else
    rm -rf samples
  fi
  popd
done < <(find . -maxdepth 2 -mindepth 3 -type d -name 'samples')