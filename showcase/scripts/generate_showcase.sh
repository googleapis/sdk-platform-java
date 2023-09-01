#!/bin/bash
set -e

script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
source $script_dir/../../library_generation/utilities.sh

cd $script_dir

# clone gapic-showcase
if [ ! -d schema ]; then
  git clone https://github.com/googleapis/gapic-showcase
  pushd gapic-showcase
  showcase_version=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="properties"]/*[local-name()="gapic-showcase.version"]/text()' $script_dir/../../showcase/gapic-showcase/pom.xml)
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

ggj_version=$(get_version_from_versions_txt ../../versions.txt "gapic-generator-java")
if [ $(echo $ggj_version | grep 'SNAPSHOT' | wc -l) -gt 0 ]; then
  echo 'This repo is at a snapshot version. Installing locally...'
  pushd $script_dir/../..
  #mvn clean install -DskipTests -Dclirr.skip
  popd
fi


rest_numeric_enums="false"
transport="grpc+rest"
include_samples="false"
rm -rdf showcase-output
mkdir showcase-output
bash $script_dir/../../library_generation/generate_library.sh \
  --proto_path "schema/google/showcase/v1beta1" \
  --destination_path "showcase-output" \
  --gapic_generator_version $ggj_version \
  --rest_numeric_enums $rest_numeric_enums \
  --include_samples $include_samples \
  --transport $transport &> out

set +e
