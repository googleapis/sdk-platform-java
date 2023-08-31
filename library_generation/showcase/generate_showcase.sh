#!/bin/bash
set -e

script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
source $script_dir/../utilities.sh
sdk_workspace=$script_dir/../../WORKSPACE
gax_properties=$script_dir/../../gax-java/dependencies.properties
showcase_build_file=$script_dir/../../showcase/BUILD.bazel

cd $script_dir
rm -rdf output

# clone gapic-showcase
if [ ! -d schema ]; then
  git clone https://github.com/googleapis/gapic-showcase
  pushd gapic-showcase
  showcase_version=$(get_version_from_WORKSPACE "_showcase_version" $sdk_workspace)
  git checkout "v$showcase_version"
  mv schema ..
  popd
  rm -rdf gapic-showcase
fi
if [ ! -d google ];then
  git clone https://github.com/googleapis/googleapis
  mv googleapis/google .
  mv googleapis/WORKSPACE ..
  rm -rdf googleapis
fi

GOOGLEAPIS_WORKSPACE=$script_dir/WORKSPACE

ggj_version=$(get_version_from_WORKSPACE _gapic_generator_java_version $sdk_workspace)
if [ $(echo $ggj_version | grep 'SNAPSHOT' | wc -l) -gt 0 ]; then
  echo 'This repo is at a snapshot version. Installing locally...'
  pushd $script_dir/../..
  #mvn clean install -DskipTests -Dclirr.skip
  popd
fi


rest_numeric_enums=$(get_config_from_BUILD \
  "$showcase_build_file" \
  "java_gapic_library(" \
  "rest_numeric_enums = False" \
  "true" \
  "false"
)
transport=$(get_config_from_BUILD \
  "$showcase_build_file" \
  "java_gapic_library(" \
  "grpc+rest" \
  "grpc" \
  "grpc+rest"
)
include_samples=$(get_config_from_BUILD \
  "$showcase_build_file" \
  "java_gapic_assembly_gradle_pkg(" \
  "include_samples = True" \
  "false" \
  "true"
)

mkdir showcase-output
bash ../generate_library.sh \
  --proto_path "schema/google/showcase/v1beta1" \
  --destination_path "showcase-output" \
  --gapic_generator_version $ggj_version \
  --rest_numeric_enums $rest_numeric_enums \
  --include_samples $include_samples \
  --transport $transport &> out

set +e
