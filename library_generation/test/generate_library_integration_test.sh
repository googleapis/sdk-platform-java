#!/usr/bin/env bash

set -xeo pipefail

# This script is used to test the result of `generate_library.sh` against generated
# source code in the specified repository.
# Specifically, this script will do
# 1. take a configuration yaml describing the structure of the libraries to
# generate
# 2. For each api_shortname, call generate_composed_library.py to generate the groups of libraries
# 3. After the generation is done, compare the resulting library with the
# corresponding cloned repository

# defaults
googleapis_gen_url="git@github.com:googleapis/googleapis-gen.git"
enable_postprocessing="true"

script_dir=$(dirname "$(readlink -f "$0")")
proto_path_list="${script_dir}/resources/proto_path_list.txt"
library_generation_dir="${script_dir}"/..
source "${script_dir}/test_utilities.sh"
source "${script_dir}/../utilities.sh"
output_folder="$(pwd)/output"


while [[ $# -gt 0 ]]; do
key="$1"
case $key in
  -p|--proto_path_list)
    proto_path_list="$2"
    shift
    ;;
  -e|--enable_postprocessing)
    enable_postprocessing="$2"
    shift
    ;;
  -g|--googleapis_gen_url)
    googleapis_gen_url="$2"
    shift
    ;;
  *)
    echo "Invalid option: [$1]"
    exit 1
    ;;
esac
shift # past argument or value
done

mkdir -p "${output_folder}"

if [ -f "${output_folder}/generation_times" ];then
  rm "${output_folder}/generation_times"
fi

declare -a configuration_yamls=(
  "${script_dir}/resources/integration/java-bigtable/generation_config.yaml"
  "${script_dir}/resources/integration/google-cloud-java/generation_config.yaml"
)


for configuration_yaml in "${configuration_yamls[@]}"; do
  library_api_shortnames=$(py_util "get_configuration_yaml_library_api_shortnames" "${configuration_yaml}")
  destination_path=$(py_util "get_configuration_yaml_destination_path" "${configuration_yaml}")
  pushd "${output_folder}"
  if [[ "${destination_path}" == *google-cloud-java* ]]; then
    git clone "https://github.com/googleapis/google-cloud-java"
    repository_path="${output_folder}/google-cloud-java"
  else
    git clone "https://github.com/googleapis/${destination_path}"
    repository_path="${output_folder}/${destination_path}"
  fi
  popd

  for api_shortname in ${library_api_shortnames}; do
    pushd "${output_folder}"

    echo "Generating library ${api_shortname}..."
    generation_start=$(date "+%s")
    python3 "${library_generation_dir}"/main.py generate-from-yaml \
      --generation-config-yaml "${configuration_yaml}" \
      --enable-postprocessing "${enable_postprocessing}" \
      --target-library-api-shortname "${api_shortname}" \
      --repository-path "${repository_path}"
    generation_end=$(date "+%s")

    # some generations are less than 1 second (0 produces exit code 1 in `expr`)
    generation_duration_seconds=$(expr "${generation_end}" - "${generation_start}" || true)
    echo "Generation time for ${api_shortname} was ${generation_duration_seconds} seconds."
    pushd "${output_folder}"
    echo "${proto_path} ${generation_duration_seconds}" >> generation_times

    echo "Generate library finished."
    echo "Compare generation result..."
    if [ ${enable_postprocessing} == "true" ]; then
      echo "Checking out repository..."
      if [[ "${destination_path}" == *google-cloud-java* ]]; then
        target_folder="${output_folder}/google-cloud-java/java-${api_shortname}"
      else
        target_folder="${output_folder}/java-${api_shortname}"
      fi

      pushd "${target_folder}"
      source_diff_result=0
      git diff \
        --ignore-space-at-eol \
        -r \
        --exit-code \
        -- \
        ':!*pom.xml' \
        ':!*README.md' \
        ':!*gapic_metadata.json' \
        ':!*reflect-config.json' \
        ':!*package-info.java' \
        || source_diff_result=$?

      pom_diff_result=$(compare_poms "${target_folder}")
      popd # target_folder
      if [[ ${source_diff_result} == 0 ]] && [[ ${pom_diff_result} == 0 ]] ; then
        echo "SUCCESS: Comparison finished, no difference is found."
      elif [ ${source_diff_result} != 0 ]; then
        echo "FAILURE: Differences found in proto path: java-${api_shortname}."
        exit "${source_diff_result}"
      elif [ ${pom_diff_result} != 0 ]; then
        echo "FAILURE: Differences found in generated java-${api_shortname}'s poms"
        exit "${pom_diff_result}"
      fi
    elif [ "${enable_postprocessing}" == "false" ]; then
      for proto_path in "${proto_paths[@]}"; do
        destination_path=$(compute_destination_path "${proto_path}" "${output_folder}")
        # include gapic_metadata.json and package-info.java after
        # resolving https://github.com/googleapis/sdk-platform-java/issues/1986
        source_diff_result=0
        diff --strip-trailing-cr -r "googleapis-gen/${proto_path}/${destination_path}" "${output_folder}/${destination_path}" \
          -x "*gradle*" \
          -x "gapic_metadata.json" \
          -x "package-info.java" || source_diff_result=$?
        if [ ${source_diff_result} == 0 ] ; then
          echo "SUCCESS: Comparison finished, no difference is found."
        else
          echo "FAILURE: Differences found in proto path: ${proto_path}."
          exit "${source_diff_result}"
        fi
      done
    fi

    popd # output_folder
  done
done
echo "ALL TESTS SUCCEEDED"
echo "generation times in seconds (does not consider repo checkout):"
cat "${output_folder}/generation_times"
