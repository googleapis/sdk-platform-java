#!/bin/bash
#
# Main functions to interact with owlbot post-processor and postprocessing
# scripts

# returns the metadata json path if given, or defaults to the one found in
# $repository_path
# Arguments
# 1 - repository_path: path from output_folder to the location of the library
# containing .repo-metadata. It assumes the existence of google-cloud-java in
# the output folder
# 2 - output_folder: root for the generated libraries, used in conjunction with
get_repo_metadata_json() {
  local repository_path=$1
  local output_folder=$2
  >&2 echo 'Attempting to obtain .repo-metadata.json from repository_path'
  local default_metadata_json_path="${output_folder}/${repository_path}/.repo-metadata.json"
  if [ -f "${default_metadata_json_path}" ]; then
    echo "${default_metadata_json_path}"
  else
    >&2 echo 'failed to obtain json from repository_path'
    exit 1
  fi
}

# returns the owlbot image sha contained in google-cloud-java. This is default
# behavior that may be overriden by a custom value in the future.
# Arguments
# 1 - output_folder: root for the generated libraries, used in conjunction with
# 2 - repository_root: usually "google-cloud-java". The .OwlBot.yaml
# file is looked into its .github folder
get_owlbot_sha() {
  local output_folder=$1
  local repository_root=$2
  if [ ! -d "${output_folder}/${repository_root}" ];
  then
    >&2 echo 'No repository to infer owlbot_sha was provided. This is necessary for post-processing' >&2
    exit 1
  fi
  >&2 echo "Attempting to obtain owlbot_sha from monorepo folder"
  owlbot_sha=$(grep 'sha256' "${output_folder}/${repository_root}/.github/.OwlBot.lock.yaml" | cut -d: -f3)
  echo "${owlbot_sha}"
}

# Runs the owlbot post-processor docker image.
# Arguments
# 1 - workspace: the location of the grpc,proto and gapic libraries to be
# processed
# owlbot
# 4 - scripts_root: location of the generation scripts
# 5 - destination_path: used to transfer the raw grpc, proto and gapic libraries
# 6 - repository_path: path from output_folder to the location of the source of
# truth/pre-existing poms. This can either be a folder in google-cloud-java or
# the root of a HW library
# 7 - proto_path: googleapis path of the library. This is used to prepare the
# folder structure to run `owlbot-cli copy-code`
function run_owlbot_postprocessor {
  workspace=$1
  scripts_root=$2
  destination_path=$3
  repository_path=$4
  proto_path=$5
  versions_file=$6

  repository_root=$(echo "${repository_path}" | cut -d/ -f1)
  repo_metadata_json_path=$(get_repo_metadata_json "${repository_path}" "${output_folder}")
  owlbot_sha=$(get_owlbot_sha "${output_folder}" "${repository_root}")

  # read or infer owlbot sha

  cp "${repo_metadata_json_path}" "${workspace}"/.repo-metadata.json

  # call owl-bot-copy
  owlbot_staging_folder="${workspace}/owl-bot-staging"
  mkdir -p "${owlbot_staging_folder}"
  owlbot_postprocessor_image="gcr.io/cloud-devrel-public-resources/owlbot-java@sha256:${owlbot_sha}"



  # copy existing pom, owlbot and version files if the source of truth repo is present
  if [[ -d "${output_folder}/${repository_path}" ]]; then
    rsync -avm \
      --include='*/' \
      --include='*.xml' \
      --include='package-info.java' \
      --include='owlbot.py' \
      --include='.OwlBot.yaml' \
      --exclude='*' \
      "${output_folder}/${repository_path}/" \
      "${workspace}"
  fi

  echo 'Running owl-bot-copy'
  pre_processed_libs_folder="${destination_path}/pre-processed"
  mkdir -p "${pre_processed_libs_folder}/${proto_path}/$(basename "${destination_path}")"
  find "${destination_path}" -mindepth 1 -maxdepth 1 -type d -not -name 'pre-processed' \
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
  versions_file_arg=""
  if [ -f "${versions_file}" ];then
    versions_file_arg="-v ${versions_file}:/versions.txt"
  fi
  # run the postprocessor
  docker run --rm \
    -v "${workspace}:/workspace" \
    ${versions_file_arg} \
    --user $(id -u):$(id -g) \
    "${owlbot_postprocessor_image}"
}
