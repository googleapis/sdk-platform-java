#!/bin/bash
set -ex

script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
lib_gen_scripts_dir=$script_dir/../../library_generation/
source $lib_gen_scripts_dir/utilities.sh

cd $script_dir

# clone gapic-showcase
if [ ! -d schema ]; then
  if [ -d gapic-showcase ]; then
    rm -rdf gapic-showcase
  fi
  showcase_version=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="properties"]/*[local-name()="gapic-showcase.version"]/text()' $script_dir/../gapic-showcase/pom.xml)
  sparse_clone https://github.com/googleapis/gapic-showcase.git "schema/google/showcase/v1beta1" "v$showcase_version"
  cd gapic-showcase
  mv schema ..
  cd ..
  rm -rdf gapic-showcase
fi
if [ ! -d google ];then
  if [ -d googleapis ]; then
    rm -rdf googleapis
  fi
  sparse_clone https://github.com/googleapis/googleapis.git "WORKSPACE google/api google/rpc google/cloud/common_resources.proto google/longrunning google/iam/v1 google/cloud/location google/type"
  mv googleapis/google .
  rm -rdf googleapis
fi


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
set +e
bash $script_dir/../../library_generation/generate_library.sh \
  --proto_path "schema/google/showcase/v1beta1" \
  --destination_path "showcase-output" \
  --gapic_generator_version $ggj_version \
  --rest_numeric_enums $rest_numeric_enums \
  --include_samples $include_samples \
  --transport $transport &> out

exit_code=$?
if [ $exit_code -ne 0 ]; then
  rm -rdf showcase-output
  exit $exit_code
fi
set +x
