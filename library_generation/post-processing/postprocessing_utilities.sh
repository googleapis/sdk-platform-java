#!/bin/bash
#
# Main functions to interact with owlbot post-processor and postprocessing
# scripts

# returns the metadata json path if given, or defaults to the one found in
# $repository_path
get_repo_metadata_json_or_default() {
  local initial_metadata_json_path=$1
  local repository_path=$2
  local output_folder=$3
  if [ -z "${initial_metadata_json_path}" ]; then
    >&2 echo 'no .repo_metadata.json provided. Attempting to obtain it from repository_path'
    local default_metadata_json_path="${output_folder}/${repository_path}/.repo-metadata.json"
    if [ -f "${default_metadata_json_path}" ]; then
      echo "${default_metadata_json_path}"
    else
      >&2 echo 'failed to obtain json from repository_path'
      exit 1
    fi
  else
    echo "${initial_metadata_json_path}"
  fi
}

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
# 7 - api_version: version string of the library (e.g. v1beta1)
# 8 - transport: used to decide if the grpc library should be processed
# 9 - repository_path: path from output_folder to the location of the source of
# truth/pre-existing poms. This can either be a folder in google-cloud-java or
# the root of a HW library
# 10 - more_versions_coming: some libraries (e.g. bitable) require other
# libraries to be present in the workspace before running post-processing. This
# flag disables the postprocessor when it's 'true', in order to run it only at
# the final version.
function run_owlbot_postprocessor {
  workspace=$1
  owlbot_sha=$2
  repo_metadata_json_path=$3
  include_samples=$4
  scripts_root=$5
  destination_path=$6
  transport=$7
  repository_path=$8
  more_versions_coming=$9
  custom_gapic_name=${10}
  proto_path=${11}

  repository_root=$(echo "${repository_path}" | cut -d/ -f1)

  # read or infer owlbot sha
  if [ -z "${owlbot_sha}" ]; then
    if [ ! -d "${output_folder}/${repository_root}" ];
    then
      echo 'no owlbot_sha provided and no repository to infer it from. This is necessary for post-processing' >&2
      exit 1
    fi
    echo "no owlbot_sha provided. Will compute from monorepo's head"
    owlbot_sha=$(grep 'sha256' "${output_folder}/${repository_root}/.github/.OwlBot.lock.yaml" | cut -d: -f3)
  fi

  cp "${repo_metadata_json_path}" "${workspace}"/.repo-metadata.json

  # call owl-bot-copy
  owlbot_staging_folder="${workspace}/owl-bot-staging"
  owlbot_image="gcr.io/cloud-devrel-public-resources/owlbot-java@sha256:${owlbot_sha}"
  distribution_name=$(cat "${repo_metadata_json_path}" | jq -r '.distribution_name // empty' | rev | cut -d: -f1 | rev)
  api_shortname=$(cat "${repo_metadata_json_path}" | jq -r '.api_shortname // empty')
  # render default owlbot.py template
  owlbot_py_content=$(cat ""${scripts_root}"/post-processing/templates/owlbot.py.template")
  # used with owl-bot-staging/
  staging_suffix="${api_version}"


  echo "${owlbot_py_content}" > "${workspace}/owlbot.py"

  # copy existing pom, owlbot and version files if the source of truth repo is present
  if [[ -n "${output_folder}/${repository_path}" ]]; then
    rsync -avm \
      --include='*/' \
      --include='*.xml' \
      --include='package-info.java' \
      --include='owlbot.py' \
      --include='versions.txt' \
      --include='.OwlBot.yaml' \
      --exclude='*' \
      "${output_folder}/${repository_path}/" \
      "${workspace}"

  fi

  echo 'Running owl-bot-copy'
  pre_processed_libs_folder="${destination_path}/pre-processed"
  mkdir -p "${pre_processed_libs_folder}/${proto_path}/$(basename "${destination_path}")"
  find "${destination_path}" -maxdepth 1 -type d \
    -exec cp -pr {} "${pre_processed_libs_folder}/${proto_path}/$(basename "${destination_path}")" \;
  pushd "${pre_processed_libs_folder}"
  # create an empty repository so owl-bot-copy can process this as a repo
  # (cannot process non-git-repositories)
  git init
  git commit --allow-empty -m 'empty commit'
  popd # pre_processed_libs_folder

   docker run --rm \
     --user $(id -u):$(id -g) \
     -v "${workspace}:/repo" \
     -v "${pre_processed_libs_folder}:/pre-processed-libraries" \
     -w /repo \
     --env HOME=/tmp \
     gcr.io/cloud-devrel-public-resources/owlbot-cli:latest \
     copy-code \
     --source-repo-commit-hash=none \
     --source-repo=/pre-processed-libraries \
     --config-file=.OwlBot.yaml


  echo 'running owl-bot post-processor'
  # run the postprocessor once all api versions have been pre-processed
  # if [[ "${more_versions_coming}" == "false" ]]; then
    docker run --rm -v "${workspace}:/workspace" --user $(id -u):$(id -g) "${owlbot_image}"
  # fi

  # get existing versions.txt from downloaded repository
  if [ -d "${output_folder}/google-cloud-java" ];then
    cp "${output_folder}/google-cloud-java/versions.txt" "${workspace}"
    pushd "${workspace}"
    bash "${scripts_root}/post-processing/apply_current_versions.sh"
    rm versions.txt
    popd # workspace
  fi
}
