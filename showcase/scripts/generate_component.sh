#!/bin/bash
#
# Generates one of the three possible components of a [showcase] library:
# gapic, proto or grpc libraries. The underlying generation script generates all
# three possibilities, so its results are reused for convenience.
# If $2 replace is "true" then the output of the generation will be written as
# the new showcase source code
set -x
set +e
component=$1 #one of gapic, proto, grpc
replace=$2
if [ -z $replace ]; then
  replace="false"
fi
script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
source $script_dir/showcase_utilities.sh

if [ ! -d $script_dir/showcase-output ]; then
  bash $script_dir/generate_showcase.sh
fi
showcase_folder=$script_dir/..
case $component in
    gapic )
        showcase_component_dir=$showcase_folder/gapic-showcase/src/main ;;
    proto )
        showcase_component_dir=$showcase_folder/proto-gapic-showcase-v1beta1/src/main ;;
    grpc )
        showcase_component_dir=$showcase_folder/grpc-gapic-showcase-v1beta1/src/main ;;
esac

if [ $replace == "true" ]; then
  rm -rdf $showcase_component_dir/*
  cp -r $script_dir/showcase-output/$component-showcase-output/src/main/* $showcase_component_dir/
fi
set +x
