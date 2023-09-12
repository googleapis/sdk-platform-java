#!/bin/bash

function cleanup {
  script_dir=$1
  cd "${script_dir}"
  rm -rdf gapic-generator-java* google schema protobuf-* protoc-gen-grpc-java* showcase-output out
}
