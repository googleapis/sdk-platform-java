#!/usr/bin/env bash

set -eo pipefail
script_dir=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# parse input parameters
while [[ $# -gt 0 ]]
do
key="$1"
case $key in
    -p|--proto_path)
    proto_path="$2"
    shift
    ;;
    -d|--destination_path)
    destination_path="$2"
    shift
    ;;
    --gapic_generator_version)
    gapic_generator_version="$2"
    # export this variable so that it can be used in gapic-generator-java-wrapper.sh
    export gapic_generator_version
    shift
    ;;
    --protobuf_version)
    protobuf_version="$2"
    shift
    ;;
    --grpc_version)
    grpc_version="$2"
    shift
    ;;
    --transport)
    transport="$2"
    shift
    ;;
    --rest_numeric_enums)
    rest_numeric_enums="$2"
    shift
    ;;
    --include_samples)
    include_samples="$2"
    shift
    ;;
    --enable_postprocessing)
    enable_postprocessing="$2"
    shift
    ;;
    --repo_metadata_json_path)
    repo_metadata_json_path="$2"
    shift
    ;;
    --owlbot_sha)
    owlbot_sha="$2"
    shift
    ;;
    *)
    echo "Invalid option: [$1]"
    exit 1
    ;;
esac
shift # past argument or value
done

working_directory=$(dirname "$(readlink -f "$0")")
# source utility functions
cd "$working_directory"
source ./utilities.sh

if [ -z "$protobuf_version" ]; then
  protobuf_version=$(get_protobuf_version "$gapic_generator_version")
fi

if [ -z "$grpc_version" ]; then
  grpc_version=$(get_grpc_version "$gapic_generator_version")
fi

if [ -z "$transport" ]; then
  transport="grpc"
fi

if [ -z "$rest_numeric_enums" ]; then
  rest_numeric_enums="true"
fi

if [ -z "$include_samples" ]; then
  include_samples="true"
fi

if [ -z "$enable_postprocessing" ]; then
  enable_postprocessing="false"
fi

cd "$working_directory"
mkdir -p "$destination_path"
destination_path="$working_directory/$destination_path"
##################### Section 0 #####################
# prepare tooling
#####################################################
cd "$working_directory"
# the order of services entries in gapic_metadata.json is relevant to the
# order of proto file, sort the proto files with respect to their name to
# get a fixed order.
proto_files=$(find "$proto_path" -type f  -name "*.proto" | sort)
folder_name=$(extract_folder_name "$destination_path")
# download gapic-generator-java, protobuf and grpc plugin.
download_tools "$gapic_generator_version" "$protobuf_version" "$grpc_version"
##################### Section 1 #####################
# generate grpc-*/
#####################################################
cd "$working_directory"
"$protoc_path"/protoc "--plugin=protoc-gen-rpc-plugin=$working_directory/protoc-gen-grpc-java-$grpc_version-linux-x86_64.exe" \
"--rpc-plugin_out=:$destination_path/java_grpc.jar" \
$proto_files
# unzip java_grpc.jar to grpc-*/src/main/java
unzip_src_files "grpc"
# remove empty files in grpc-*/src/main/java
remove_empty_files "grpc"
# remove grpc version in *ServiceGrpc.java file so the content is identical with bazel build.
remove_grpc_version
###################### Section 2 #####################
## generate gapic-*/, part of proto-*/, samples/
######################################################
cd "$working_directory"
"$protoc_path"/protoc --experimental_allow_proto3_optional \
"--plugin=protoc-gen-java_gapic=$working_directory/gapic-generator-java-wrapper" \
"--java_gapic_out=metadata:$destination_path/java_gapic_srcjar_raw.srcjar.zip" \
"--java_gapic_opt=$(get_gapic_opts)" \
${proto_files} $(search_additional_protos)

unzip -o -q "$destination_path/java_gapic_srcjar_raw.srcjar.zip" -d "$destination_path"
# Sync'\''d to the output file name in Writer.java.
unzip -o -q "$destination_path/temp-codegen.srcjar" -d "$destination_path/java_gapic_srcjar"
# Resource name source files.
proto_dir=$destination_path/java_gapic_srcjar/proto/src/main/java
if [ ! -d "$proto_dir" ]; then
  # Some APIs don'\''t have resource name helpers, like BigQuery v2.
  # Create an empty file so we can finish building. Gating the resource name rule definition
  # on file existences go against Bazel'\''s design patterns, so we'\''ll simply delete all empty
  # files during the final packaging process (see java_gapic_pkg.bzl)
  mkdir -p "$proto_dir"
  touch "$proto_dir"/PlaceholderFile.java
fi

cd "$working_directory"
# move java_gapic_srcjar/src/main to gapic-*/src.
mv_src_files "gapic" "main"
# remove empty files in gapic-*/src/main/java
remove_empty_files "gapic"
# move java_gapic_srcjar/src/test to gapic-*/src
mv_src_files "gapic" "test"
if [ "$include_samples" == "true" ]; then
  # move java_gapic_srcjar/samples/snippets to samples/snippets
  mv_src_files "samples" "main"
fi
##################### Section 3 #####################
# generate proto-*/
#####################################################
cd "$working_directory"
"$protoc_path"/protoc "--java_out=$destination_path/java_proto.jar" $proto_files
# move java_gapic_srcjar/proto/src/main/java (generated resource name helper class)
# to proto-*/src/main
mv_src_files "proto" "main"
# unzip java_proto.jar to proto-*/src/main/java
unzip_src_files "proto"
# remove empty files in proto-*/src/main/java
remove_empty_files "proto"
# copy proto files to proto-*/src/main/proto
for proto_src in $proto_files; do
    mkdir -p "$destination_path/proto-$folder_name/src/main/proto"
    cp -f --parents "$proto_src" "$destination_path/proto-$folder_name/src/main/proto"
done
##################### Section 4 #####################
# rm tar files
#####################################################
cd "$destination_path"
rm -rf java_gapic_srcjar java_gapic_srcjar_raw.srcjar.zip java_grpc.jar java_proto.jar temp-codegen.srcjar
##################### Section 5 #####################
# post-processing
#####################################################
set -x
if [ $enable_postprocessing != "true" ];
then
  echo "post processing is disabled"
  exit 0
fi
if [ -z $repo_metadata_json_path ];
then
  echo "no repo_metadata.json provided. This is necessary for post-processing the generated library"
  exit 1
fi
if [ -z $owlbot_sha ];
then
  echo "no owlbot_sha provided. This is necessary for post-processing the generated library"
  exit 1
fi
# copy repo metadata to destination library folder
workspace=$destination_path/workspace
mkdir -p $workspace
cp $repo_metadata_json_path $workspace/.repo-metadata.json

# call owl-bot-copy
owlbot_staging_folder="$workspace/owl-bot-staging"
owlbot_image=gcr.io/cloud-devrel-public-resources/owlbot-java@sha256:$owlbot_sha

distribution_name=$(cat $repo_metadata_json_path | jq -r '.distribution_name // empty' | rev | cut -d: -f1 | rev)
api_shortname=$(cat $repo_metadata_json_path | jq -r '.api_shortname // empty')

# render owlbot.py template
owlbot_py_content=$(cat "$script_dir/templates/owlbot.py.template")

#cp -r $(find $destination_path -not -wholename './workspace*') $owlbot_staging_folder
versions_file="$script_dir/google-cloud-java/versions.txt"
cp $versions_file $workspace
staging_suffix="java-$module_name"
mkdir -p $owlbot_staging_folder/$staging_suffix
cp -r $destination_path/gapic-$folder_name $owlbot_staging_folder/$staging_suffix
cp -r $destination_path/grpc-$folder_name $owlbot_staging_folder/$staging_suffix
cp -r $destination_path/proto-$folder_name $owlbot_staging_folder/$staging_suffix
if [ $include_samples == 'true' ]; then
  generated_snippets_staging="$owlbot_staging_folder/$staging_suffix/samples/snippets/generated"
  mkdir -p $generated_snippets_staging
  cp -r $destination_path/samples $generated_snippets_staging
fi

echo "$owlbot_py_content" > $workspace/owlbot.py

docker run --rm -v $workspace:/workspace --user $(id -u):$(id -g) $owlbot_image

# postprocessor cleanup
bash $script_dir/post-processing/update_owlbot_postprocessor_config.sh $workspace
bash $script_dir/post-processing/delete_non_generated_samples.sh $workspace
bash $script_dir/post-processing/consolidate_config.sh $workspace
bash $script_dir/post-processing/readme_update.sh $workspace
#rm $workspace/versions.txt

if [ -z ${monorepo_tag+x} ]; then
  echo "Will not add parent project to pom"
else
  pushd $script_dir
  [ ! -d google-cloud-java ] && git clone https://github.com/googleapis/google-cloud-java
  pushd google-cloud-java
  git reset --hard
  git checkout $monorepo_tag
  parent_pom="$(pwd)/google-cloud-pom-parent/pom.xml"
  popd
  # rm -rdf google-cloud-java
  popd
  bash $script_dir/post-processing/set_parent_pom.sh $workspace $parent_pom

  # get existing versions.txt from downloaded monorepo
  repo_short=$(cat $repo_metadata_json_path | jq -r '.repo_short // empty')
  cp "$script_dir/google-cloud-java/versions.txt" $workspace
  pushd $workspace
  bash $script_dir/post-processing/apply_current_versions.sh
  popd
fi

# rename folders properly (may not be necessary after all)
exit 0
#pushd $workspace
#gapic_lib_original_name=$(find . -name 'gapic-*' | sed "s/\.\///")
#gapic_lib_new_name=$(echo "$gapic_lib_original_name" |\
  #sed 's/cloud-cloud/cloud/' | sed 's/-v[0-9a-za-z]-java//' | sed 's/gapic-//')
#proto_lib_original_name=$(find . -name 'proto-*' | sed "s/\.\///")
#proto_lib_new_name=$(echo "$proto_lib_original_name" |\
  #sed 's/cloud-cloud/cloud/' | sed 's/-java//')
#grpc_lib_original_name=$(find . -name 'grpc-*' | sed "s/\.\///")
#grpc_lib_new_name=$(echo "$grpc_lib_original_name" |\
  #sed 's/cloud-cloud/cloud/' | sed 's/-java//')
## two folders exist, move the contents of one to the other
#mv $gapic_lib_original_name/* $gapic_lib_new_name
#rm -rdf $gapic_lib_original_name
## just rename these two
#mv $proto_lib_original_name $proto_lib_new_name
#mv $grpc_lib_original_name $grpc_lib_new_name
#
#for pom_file in $(find . -mindepth 0 -maxdepth 2 -name pom.xml \
    #|sort --dictionary-order); do
  #sed -i "s/$grpc_lib_original_name/$grpc_lib_new_name/" $pom_file
  #sed -i "s/$proto_lib_original_name/$proto_lib_new_name/" $pom_file
  #sed -i "s/$grpc_lib_original_name/$grpc_lib_new_name/" $pom_file
#done
#popd
#
#
