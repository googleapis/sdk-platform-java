#!/bin/bash
#
# Main functions to interact with owlbot post-processor and postprocessing
# scripts


# Runs the owlbot post-processor docker image. The resulting post-processed
# library gets stored in `${output_folder}/workspace`
# Arguments
# 1 - workspace: the location of the grpc,proto and gapic libraries to be
# processed
# 2 - scripts_root: location of the generation scripts
# 3 - destination_path: used to transfer the raw grpc, proto and gapic libraries
# 5 - proto_path: googleapis path of the library. This is used to prepare the
# folder structure to run `owlbot-cli copy-code`
# 6 - versions_file: path to file containing versions to be applied to the poms
# 7 - output_folder: main workspace of the generation process

workspace=$1
scripts_root=$2
destination_path=$3
proto_path=$4
versions_file=$5
output_folder=$6

source "${scripts_root}"/utilities.sh

repository_root=$(echo "${destination_path}" | cut -d/ -f1)
repo_metadata_json_path=$(get_repo_metadata_json "${destination_path}" "${output_folder}")
owlbot_sha=$(get_owlbot_sha "${output_folder}" "${repository_root}")

# read or infer owlbot sha

cp "${repo_metadata_json_path}" "${workspace}"/.repo-metadata.json

# call owl-bot-copy
owlbot_staging_folder="${workspace}/owl-bot-staging"
mkdir -p "${owlbot_staging_folder}"
owlbot_postprocessor_image="gcr.io/cloud-devrel-public-resources/owlbot-java@sha256:${owlbot_sha}"



# copy existing pom, owlbot and version files if the source of truth repo is present
# pre-processed folders are ommited
if [[ -d "${output_folder}/${destination_path}" ]]; then
  rsync -avm \
    --include='*/' \
    --include='*.xml' \
    --include='owlbot.py' \
    --include='.OwlBot.yaml' \
    --exclude='*' \
    "${output_folder}/${destination_path}/" \
    "${workspace}"
fi

echo 'Running owl-bot-copy'
pre_processed_libs_folder="${output_folder}/pre-processed"
# By default (thanks to generation templates), .OwlBot.yaml `deep-copy` section
# references a wildcard pattern matching a folder
# ending with `-java` at the leaf of proto_path. We can simply hardcode
mkdir -p "${pre_processed_libs_folder}/${proto_path}/generated-java"
find "${output_folder}/${destination_path}" -mindepth 1 -maxdepth 1 -type d -not -name 'pre-processed' \
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
