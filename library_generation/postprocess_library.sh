#!/bin/bash
#
# Main functions to interact with owlbot post-processor and postprocessing
# scripts


# Runs the owlbot post-processor docker image. The resulting post-processed
# library gets stored in the $workspace argument
# Arguments
# 1 - workspace: the location of the grpc,proto and gapic libraries to be
# processed
# 2 - preprocessed_sources_path: used to transfer the raw grpc, proto and gapic
# libraries into the workspace via copy-code
# 3 - versions_file: path to file containing versions to be applied to the poms
scripts_root=$(dirname "$(readlink -f "$0")")

workspace=$1
preprocessed_sources_path=$2
versions_file=$3

source "${scripts_root}"/utilities.sh

for owlbot_file in ".repo-metadata.json" "owlbot.py" ".OwlBot.yaml"
do
  if [[ $(find "${workspace}" -name '.repo-metadata.json' | wc -l) -eq 0 ]]; then
    echo "necessary file for postprocessing '${owlbot_file}' was not found in workspace"
    echo "please provide a workspace that is owlbot compatible"
    exit 1
  fi
done

repository_root=$(dirname "${workspace}")
owlbot_sha=$(get_owlbot_sha "${repository_root}")
proto_path=$(get_proto_path_from_preprocessed_sources "${preprocessed_sources_path}")

# ensure pyenv scripts are available
eval "$(pyenv init --path)"
eval "$(pyenv init -)"
eval "$(pyenv virtualenv-init -)"
# create python virtualenv
python_version=$(cat "${scripts_root}/configuration/python-version")
if [ $(pyenv versions | grep "${python_version}" | wc -l) -eq 0 ]; then
  pyenv install "${python_version}"
fi
if [ $(pyenv virtualenvs | grep "${python_version}" | grep "postprocessing" | wc -l) -eq 0 ];then
  pyenv virtualenv "${python_version}" "postprocessing"
fi
pyenv activate "postprocessing"

# call owl-bot-copy
owlbot_staging_folder="${workspace}/owl-bot-staging"
mkdir -p "${owlbot_staging_folder}"
owlbot_postprocessor_image="gcr.io/cloud-devrel-public-resources/owlbot-java@sha256:${owlbot_sha}"


echo 'Running owl-bot-copy'
pre_processed_libs_folder=$(mktemp -d)
# By default (thanks to generation templates), .OwlBot.yaml `deep-copy` section
# references a wildcard pattern matching a folder
# ending with `-java` at the leaf of proto_path. 
mkdir -p "${pre_processed_libs_folder}/${proto_path}/generated-java"
folder_name=$(extract_folder_name "${preprocessed_sources_path}")
copy_directory_if_exists "${preprocessed_sources_path}/proto-${folder_name}" \
  "${pre_processed_libs_folder}/${proto_path}/generated-java/proto-google-cloud-${folder_name}"
copy_directory_if_exists "${preprocessed_sources_path}/grpc-${folder_name}" \
  "${pre_processed_libs_folder}/${proto_path}/generated-java/grpc-google-cloud-${folder_name}"
copy_directory_if_exists "${preprocessed_sources_path}/gapic-${folder_name}" \
  "${pre_processed_libs_folder}/${proto_path}/generated-java/gapic-google-cloud-${folder_name}"
copy_directory_if_exists "${preprocessed_sources_path}/samples" \
  "${pre_processed_libs_folder}/${proto_path}/generated-java/samples"
pushd "${pre_processed_libs_folder}"
# create an empty repository so owl-bot-copy can process this as a repo
# (cannot process non-git-repositories)
git init
git commit --allow-empty -m 'empty commit'
popd # pre_processed_libs_folder

owlbot_cli_image_sha=$(cat "${scripts_root}/configuration/owlbot-cli-sha" | grep "sha256")

docker run --rm \
  --user $(id -u):$(id -g) \
  -v "${workspace}:/repo" \
  -v "${pre_processed_libs_folder}:/pre-processed-libraries" \
  -w /repo \
  --env HOME=/tmp \
  gcr.io/cloud-devrel-public-resources/owlbot-cli@"${owlbot_cli_image_sha}" \
  copy-code \
  --source-repo-commit-hash=none \
  --source-repo=/pre-processed-libraries \
  --config-file=.OwlBot.yaml

# we clone the synthtool library and manually build it
mkdir -p /tmp/synthtool
pushd /tmp/synthtool
if [ ! -d "synthtool" ]; then
  git clone https://github.com/googleapis/synthtool.git
  pushd "synthtool"
  python3 -m pip install -e .
  python3 -m pip install -r requirements.in
  popd # synthtool
fi
popd # temp dir

# now we use the image to call owlbot.py
echo 'processing owlbot.py'

pushd "${scripts_root}/owlbot/src/"
python3 -m pip install -r requirements.in
popd # owlbot/src

# run the postprocessor
echo 'running owl-bot post-processor'
pushd "${workspace}"
bash "${scripts_root}/owlbot/bin/entrypoint.sh" "${scripts_root}" "${versions_file}" "${synthtool_image_id}"
popd # workspace
