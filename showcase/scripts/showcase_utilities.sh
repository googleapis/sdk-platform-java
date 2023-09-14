#!/bin/bash

function cleanup {
  script_dir=$1
  cd "${script_dir}"
  rm -rdf output gapic-generator-java*
}
