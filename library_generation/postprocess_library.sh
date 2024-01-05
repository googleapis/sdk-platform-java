#!/bin/bash
#
# Main functions to interact with owlbot post-processor

# Runs the java owlbot post-processor. The resulting post-processed
# library gets stored in the $postprocessing_target argument
# Arguments
# 1 - postprocessing_target: path where the postprocessor will run. This folder
# has the following requirements
#   -  a .repo-metadata.json file must be present
#   -  an owlbot.py file must be present
#   -  an .OwlBot.yaml file must be present
# 2 - preprocessed_sources_path: used to transfer the raw grpc, proto and gapic
# libraries into the postprocessing_target via copy-code
# 3 - versions_file: path to file containing versions to be applied to the poms
# 4 - owlbot_cli_source_folder: alternative folder with a structure exactly like
# googleapis-gen. It will be used instead of preprocessed_sources_path if
# provided
set -xeo pipefail
scripts_root=$(dirname "$(readlink -f "$0")")

postprocessing_target=$1
preprocessed_sources_path=$2
versions_file=$3
owlbot_cli_source_folder=$4

source "${scripts_root}"/utilities.sh

for owlbot_file in ".repo-metadata.json" "owlbot.py" ".OwlBot.yaml"
do
  if [[ $(find "${postprocessing_target}" -name "${owlbot_file}" | wc -l) -eq 0 ]]; then
    echo "necessary file for postprocessing '${owlbot_file}' was not found in postprocessing_target"
    echo "please provide a postprocessing_target folder that is compatible with the OwlBot Java postprocessor"
    exit 1
  fi
done


# ensure pyenv scripts are available
eval "$(pyenv init --path)"
eval "$(pyenv init -)"
eval "$(pyenv virtualenv-init -)"

# create and activate the python virtualenv
python_version=$(cat "${scripts_root}/configuration/python-version")
if [ $(pyenv versions | grep "${python_version}" | wc -l) -eq 0 ]; then
  pyenv install "${python_version}"
fi
if [ $(pyenv virtualenvs | grep "${python_version}" | grep "postprocessing" | wc -l) -eq 0 ];then
  pyenv virtualenv "${python_version}" "postprocessing"
fi
pyenv activate "postprocessing"

if [[ -z "${owlbot_cli_source_folder}" ]]; then
  owlbot_cli_source_folder=$(mktemp -d)
  build_owlbot_cli_source_folder "${postprocessing_target}" "${owlbot_cli_source_folder}" "${preprocessed_sources_path}"
fi

owlbot_cli_image_sha=$(cat "${scripts_root}/configuration/owlbot-cli-sha" | grep "sha256")

# we determine the location of the .OwlBot.yaml file by checking if the target
# folder is a monorepo folder or not
if [[ "${postprocessing_target}" == *google-cloud-java* ]]; then
  owlbot_yaml_relative_path=".OwlBot.yaml"
else
  owlbot_yaml_relative_path=".github/.OwlBot.yaml"
fi

docker run --rm \
  --user $(id -u):$(id -g) \
  -v "${postprocessing_target}:/repo" \
  -v "${owlbot_cli_source_folder}:/pre-processed-libraries" \
  -w /repo \
  --env HOME=/tmp \
  gcr.io/cloud-devrel-public-resources/owlbot-cli@"${owlbot_cli_image_sha}" \
  copy-code \
  --source-repo-commit-hash=none \
  --source-repo=/pre-processed-libraries \
  --config-file="${owlbot_yaml_relative_path}"

# we have to "unpack" the
# owl-bot-staging folder so it's properly processed by java owlbot
# pushd "${postprocessing_target}"
# mv owl-bot-staging/* temp
# rm -rd owl-bot-staging/
# mv temp owl-bot-staging
# popd # postprocessing_target

# we clone the synthtool library and manually build it
mkdir -p /tmp/synthtool
pushd /tmp/synthtool
if [ ! -d "synthtool" ]; then
  git clone https://github.com/googleapis/synthtool.git
fi
pushd "synthtool"
synthtool_commitish=$(cat "${scripts_root}/configuration/synthtool-commitish")
git reset --hard "${synthtool_commitish}"
python3 -m pip install -e .
python3 -m pip install -r requirements.in
popd # synthtool
popd # temp dir

# we install the owlbot requirements
pushd "${scripts_root}/owlbot/src/"
python3 -m pip install -r requirements.in
popd # owlbot/src

# run the postprocessor
echo 'running owl-bot post-processor'
pushd "${postprocessing_target}"
bash "${scripts_root}/owlbot/bin/entrypoint.sh" "${scripts_root}" "${versions_file}"
popd # postprocessing_target
