#!/bin/bash
#
# Main functions to interact with owlbot post-processor and postprocessing
# scripts

# Runs the owlbot post-processor docker image.
# Arguments
# 1 - workspace: the location of the grpc,proto and gapic libraries to be
# processed
# 2 - owlbot_sha: docker image sha that specifies the postprocessor version to
# be used
# 3 - repo_metadata_json_path: contains metadata about the library, used by
# owlbot
# 4 - include_samples: used to tell if samples are being generated/processed
# 5 - scripts_root: location of the generation scripts
# 6 - destination_path: used to transfer the raw grpc, proto and gapic libraries
function run_owlbot_postprocessor {
  workspace=$1
  owlbot_sha=$2
  repo_metadata_json_path=$3
  include_samples=$4
  scripts_root=$5
  destination_path=$6
  cp "${repo_metadata_json_path}" "${workspace}"/.repo-metadata.json

  # call owl-bot-copy
  owlbot_staging_folder="${workspace}/owl-bot-staging"
  owlbot_image="gcr.io/cloud-devrel-public-resources/owlbot-java@sha256:${owlbot_sha}"

  distribution_name=$(cat "${repo_metadata_json_path}" | jq -r '.distribution_name // empty' | rev | cut -d: -f1 | rev)
  api_shortname=$(cat "${repo_metadata_json_path}" | jq -r '.api_shortname // empty')

  # render owlbot.py template
  owlbot_py_content=$(cat ""${scripts_root}"/post-processing/templates/owlbot.py.template")

  staging_suffix="java-${api_shortname}"
  mkdir -p "${owlbot_staging_folder}/${staging_suffix}"
  gapic_folder_name=$(echo "${folder_name}" | sed 's/\(.*\)-.*/\1/')
  cp -r "${destination_path}/gapic-${folder_name}" "${owlbot_staging_folder}/${staging_suffix}/${gapic_folder_name}"
  cp -r "${destination_path}/grpc-${folder_name}" "${owlbot_staging_folder}/${staging_suffix}"
  cp -r "${destination_path}/proto-${folder_name}" "${owlbot_staging_folder}/${staging_suffix}"
  if [ "${include_samples}" == 'true' ]; then
    cp -r "${destination_path}/samples" "${owlbot_staging_folder}/${staging_suffix}"
  fi

  echo "${owlbot_py_content}" > "${workspace}/owlbot.py"

  docker run --rm -v "${workspace}:/workspace" --user $(id -u):$(id -g) "${owlbot_image}"
}


# calls several scripts to perform additional post processing after owlbot is
# used. If google-cloud-java is downloaded in the root script location, then
# more processing is performed.
function other_post_processing_scripts {
  scripts_root=$1
  workspace=$2
  repo_metadata_json_path=$3
  # postprocessor cleanup
  bash "${scripts_root}/post-processing/update_owlbot_postprocessor_config.sh" "${workspace}"
  bash "${scripts_root}/post-processing/delete_non_generated_samples.sh" "${workspace}"
  bash "${scripts_root}/post-processing/consolidate_config.sh" "${workspace}"

  pushd "${scripts_root}"
  if [ -d google-cloud-java ]; then
    pushd google-cloud-java
    jar_parent_pom="$(pwd)/google-cloud-jar-parent/pom.xml"
    pom_parent_pom="$(pwd)/google-cloud-pom-parent/pom.xml"
    popd
    popd
    bash "${scripts_root}/post-processing/set_parent_pom.sh" "${workspace}/pom.xml" "${jar_parent_pom}" '../google-cloud-jar-parent/pom.xml'
    workspace_bom=$(find -wholename '*-bom/pom.xml')
    bash "${scripts_root}/post-processing/set_parent_pom.sh" "${workspace_bom}" "${pom_parent_pom}" '../../google-cloud-pom-parent/pom.xml'

    # get existing versions.txt from downloaded monorepo
    repo_short=$(cat ${repo_metadata_json_path} | jq -r '.repo_short // empty')
    cp "${scripts_root}/google-cloud-java/versions.txt" "${workspace}"
    pushd "${workspace}"
    bash "${scripts_root}/post-processing/apply_current_versions.sh"
    rm versions.txt
    popd
  else
    echo 'google-cloud-java not found. Will not update parent poms nor update versions'
    popd
  fi
}
