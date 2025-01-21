#!/bin/bash

function cleanup {
  script_dir=$1
  cd "${script_dir}"
  rm -rf output gapic-generator-java*
  rm -rf "${script_dir}/../../generated-showcase-location"
}
