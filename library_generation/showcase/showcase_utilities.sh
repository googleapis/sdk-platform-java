#!/bin/bash
set -ex
script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

function generateShowcaseIfMissing {
  if [ ! -d showcase-output ]; then
    bash $script_dir/generate_showcase.sh
  fi
}
