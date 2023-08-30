#!/bin/bash

function run_owlbot_postprocessor {
  workspace=$1
  owlbot_sha=$2
  repo_metadata_json_path=$3
  include_samples=$4
  scripts_root=$5
  destination_path=$6
  cp $repo_metadata_json_path $workspace/.repo-metadata.json

  # call owl-bot-copy
  owlbot_staging_folder="$workspace/owl-bot-staging"
  owlbot_image=gcr.io/cloud-devrel-public-resources/owlbot-java@sha256:$owlbot_sha

  distribution_name=$(cat $repo_metadata_json_path | jq -r '.distribution_name // empty' | rev | cut -d: -f1 | rev)
  api_shortname=$(cat $repo_metadata_json_path | jq -r '.api_shortname // empty')

  # render owlbot.py template
  owlbot_py_content=$(cat "$scripts_root/post-processing/templates/owlbot.py.template")

  #cp -r $(find $destination_path -not -wholename './workspace*') $owlbot_staging_folder
  versions_file="$scripts_root/google-cloud-java/versions.txt"
  #cp $versions_file $workspace
  staging_suffix="java-$module_name"
  mkdir -p $owlbot_staging_folder/$staging_suffix
  gapic_folder_name=$(echo "$folder_name" | sed 's/\(.*\)-.*/\1/')
  cp -r $destination_path/gapic-$folder_name $owlbot_staging_folder/$staging_suffix/$gapic_folder_name
  cp -r $destination_path/grpc-$folder_name $owlbot_staging_folder/$staging_suffix
  cp -r $destination_path/proto-$folder_name $owlbot_staging_folder/$staging_suffix
  if [ $include_samples == 'true' ]; then
    generated_snippets_staging="$owlbot_staging_folder/$staging_suffix/samples/snippets/generated"
    mkdir -p $generated_snippets_staging
    cp -r $destination_path/samples/snippets/generated/* $generated_snippets_staging
  fi

  echo "$owlbot_py_content" > $workspace/owlbot.py

  docker run --rm -v $workspace:/workspace --user $(id -u):$(id -g) $owlbot_image
}


function other_post_processing_scripts {
  scripts_root=$1
  workspace=$2
  repo_metadata_json_path=$3
  # postprocessor cleanup
  bash $scripts_root/post-processing/update_owlbot_postprocessor_config.sh $workspace
  bash $scripts_root/post-processing/delete_non_generated_samples.sh $workspace
  bash $scripts_root/post-processing/consolidate_config.sh $workspace
  bash $scripts_root/post-processing/readme_update.sh $workspace

  pushd $scripts_root
  [ ! -d google-cloud-java ] && git clone https://github.com/googleapis/google-cloud-java
  pushd google-cloud-java
  parent_pom="$(pwd)/google-cloud-jar-parent/pom.xml"
  popd
  popd
  bash $scripts_root/post-processing/set_parent_pom.sh $workspace $parent_pom

  # get existing versions.txt from downloaded monorepo
  repo_short=$(cat $repo_metadata_json_path | jq -r '.repo_short // empty')
  cp "$scripts_root/google-cloud-java/versions.txt" $workspace
  pushd $workspace
  bash $scripts_root/post-processing/apply_current_versions.sh
  rm versions.txt
  popd
}
