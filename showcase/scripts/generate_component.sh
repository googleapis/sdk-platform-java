#!/bin/bash
set -x
set +e
component=$1 #one of gapic, proto, grpc
script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
source $script_dir/showcase_utilities.sh

generateShowcaseIfMissing
showcase_folder=$script_dir/..
case $component in
    gapic )
        showcase_component_dir=$showcase_folder/gapic-showcase/src/main ;;
    proto )
        showcase_component_dir=$showcase_folder/proto-gapic-showcase-v1beta1/src/main ;;
    grpc )
        showcase_component_dir=$showcase_folder/grpc-gapic-showcase-v1beta1/src/main ;;
esac
rm -rdf $showcase_component_dir/*
cp -r $script_dir/showcase-output/$component-showcase-output/src/main/* $showcase_component_dir/
set +x
