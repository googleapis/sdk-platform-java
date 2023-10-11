#!/usr/bin/env bash

set -xeo pipefail

# This script is used to test the result of `generate_library.sh` against generated
# source code in google-cloud-java repository.
# Specifically, this script will do
# 1. checkout the master branch of googleapis/google and WORKSPACE
# 2. parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
# 3. generate a library with proto_path and destination_path in a proto_path
#    list by invoking `generate_library.sh`. GAPIC options to generate a library
#    will be parsed from proto_path/BUILD.bazel.
# 4. checkout the master branch google-cloud-java repository and compare the result.

# defaults
googleapis_gen_url="git@github.com:googleapis/googleapis-gen.git"
script_dir=$(dirname "$(readlink -f "$0")")
proto_path_list="${script_dir}/resources/proto_path_list.txt"
library_generation_dir="${script_dir}"/..
source "${script_dir}/test_utilities.sh"
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
  -s|--owlbot_sha)
    owlbot_sha="$2"
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

if [ -z $enable_postprocessing ]; then
  enable_postprocessing="true"
fi

script_dir=$(dirname "$(readlink -f "$0")")
source "${script_dir}/../utilities.sh"
library_generation_dir="${script_dir}"/..
mkdir -p "${output_folder}"
pushd "${output_folder}"
# checkout the master branch of googleapis/google (proto files) and WORKSPACE
echo "Checking out googlapis repository..."
# sparse_clone will remove folder contents first, so we have to checkout googleapis
# only once.
sparse_clone https://github.com/googleapis/googleapis.git "google grafeas WORKSPACE"
pushd googleapis
cp -r google "${output_folder}"
cp -r grafeas "${output_folder}"
# parse version of gapic-generator-java, protobuf and grpc from WORKSPACE
gapic_generator_version=$(get_version_from_WORKSPACE "_gapic_generator_java_version" WORKSPACE "=")
echo "The version of gapic-generator-java is ${gapic_generator_version}."
protobuf_version=$(get_version_from_WORKSPACE "protobuf-" WORKSPACE "-")
echo "The version of protobuf is ${protobuf_version}"
popd # googleapis
popd # output

grep -v '^ *#' < "${proto_path_list}" | while IFS= read -r line; do
  proto_path=$(echo "$line" | cut -d " " -f 1 | sed 's/,/ /g')
  # the first one is the main one
  first_proto_path=$(echo "${proto_path}" | cut -d " " -f1)
  destination_path=$(echo "$line" | cut -d " " -f 2)
  repository_path=$(echo "$line" | cut -d " " -f 3)
  more_versions_coming=$(echo "${line}" | cut -d " " -f 4)
  is_handwritten=$(echo "$line" | cut -d " " -f 5)
  custom_gapic_name=$(echo "$line" | cut -d " " -f 6)
  # parse GAPIC options from proto_path/BUILD.bazel
  pushd "${output_folder}"
  proto_build_file_path="${first_proto_path}/BUILD.bazel"
  proto_only=$(get_proto_only_from_BUILD "${proto_build_file_path}")
  gapic_additional_protos=$(get_gapic_additional_protos_from_BUILD "${proto_build_file_path}")
  transport=$(get_transport_from_BUILD "${proto_build_file_path}")
  rest_numeric_enums=$(get_rest_numeric_enums_from_BUILD "${proto_build_file_path}")
  include_samples=$(get_include_samples_from_BUILD "${proto_build_file_path}")
  popd # output_folder
  echo "GAPIC options are transport=${transport}, rest_numeric_enums=${rest_numeric_enums}, include_samples=${include_samples}."
  # generate GAPIC client library
  echo "Generating library from ${first_proto_path}, to ${destination_path}..."
  if [ $enable_postprocessing == "true" ]; then
    if [[ "${repository_path}" == "null" ]]; then
      # we need a repository to compare the generated results with. Skip this
      # library
      continue
    fi
    if [ "${is_handwritten}" == "true" ]; then
      echo 'this is a handwritten library'
      hw_library=$(echo "${repository_path}" | cut -d: -f2)
      pushd "${output_folder}"
      if [ ! -d "${hw_library}" ];then
        git clone "https://github.com/googleapis/${hw_library}.git"
      fi
      target_folder="${output_folder}/${hw_library}"
      owlbot_sha=$(grep 'sha256' "${target_folder}/.github/.OwlBot.lock.yaml" | cut -d: -f3)
    else 
      echo 'this is a monorepo library'
      pushd "${output_folder}"
      if [ ! -d "google-cloud-java" ]; then
        sparse_clone "https://github.com/googleapis/google-cloud-java.git" "${repository_path} google-cloud-pom-parent google-cloud-jar-parent versions.txt .github"
      fi
      repository_path="google-cloud-java/${repository_path}"
      target_folder="${output_folder}/${repository_path}"
    fi
    popd # output_folder
    # will check if a custom path exists in `test/resources/repo_metadatas` and
    # use that one if so. The script will default to the one contained in
    # `target_path`
    repo_metadata_json_path="${script_dir}/resources/repo_metadatas/$(echo "${repository_path}" | cut -d: -f2).json"
    if [ ! -f "${repo_metadata_json_path}" ]; then
      echo 'using default repo_metadata.json file'
      repo_metadata_json_path=""
    fi

    "${library_generation_dir}"/generate_library.sh \
      -p "${proto_path}" \
      -d "${destination_path}" \
      --gapic_generator_version "${gapic_generator_version}" \
      --protobuf_version "${protobuf_version}" \
      --proto_only "${proto_only}" \
      --gapic_additional_protos "${gapic_additional_protos}" \
      --transport "${transport}" \
      --rest_numeric_enums "${rest_numeric_enums}" \
      --include_samples "${include_samples}" \
      --repo_metadata_json_path "${repo_metadata_json_path}" \
      --owlbot_sha "${owlbot_sha}" \
      --repository_path "${repository_path}" \
      --more_versions_coming "${more_versions_coming}" \
      --custom_gapic_name "${custom_gapic_name}" \
      --enable_postprocessing "true"
  else
    "${library_generation_dir}"/generate_library.sh \
      -p "${proto_path}" \
      -d "${destination_path}" \
      --gapic_generator_version "${gapic_generator_version}" \
      --protobuf_version "${protobuf_version}" \
      --proto_only "${proto_only}" \
      --gapic_additional_protos "${gapic_additional_protos}" \
      --transport "${transport}" \
      --rest_numeric_enums "${rest_numeric_enums}" \
      --include_samples "${include_samples}" \
      --enable_postprocessing "false"
  fi

  echo "Generate library finished."
  echo "Compare generation result..."
  pushd "${output_folder}"
  if [ $enable_postprocessing == "true" ] && [ "${more_versions_coming}" == "false" ]; then
    echo "Checking out repository..."
    cp -r ${output_folder}/workspace/* "${target_folder}"
    pushd "${target_folder}"
    SOURCE_DIFF_RESULT=0
    git diff \
      --ignore-space-at-eol \
      -r \
      --exit-code \
      -- \
      ':!*pom.xml' \
      ':!*README.md' \
      ':!*package-info.java' \
      || SOURCE_DIFF_RESULT=$?

    POM_DIFF_RESULT=$(compare_poms "${target_folder}")
    popd # target_folder
    if [[ ${SOURCE_DIFF_RESULT} == 0 ]] && [[ ${POM_DIFF_RESULT} == 0 ]] ; then
      echo "SUCCESS: Comparison finished, no difference is found."
      # this is the last api version being processed. Delete google-cloud-java to
      # allow a sparse clone of the next library
      # We only perform this action here in case the script has failed to keep
      # the folder for further investigation
      if [ "${repository_path}" == "hw:*" ]; then
        rm -rdf "${hw_library}"
      else
        rm -rdf google-cloud-java
      fi
    elif [ ${SOURCE_DIFF_RESULT} != 0 ]; then
      echo "FAILURE: Differences found in proto path: ${first_proto_path}."
      exit "${SOURCE_DIFF_RESULT}"
    elif [ ${POM_DIFF_RESULT} != 0 ]; then
      echo "FAILURE: Differences found in generated poms"
      exit "${POM_DIFF_RESULT}"
    fi
  elif [ $enable_postprocessing == "false" ]; then
    # include gapic_metadata.json and package-info.java after
    # resolving https://github.com/googleapis/sdk-platform-java/issues/1986
    echo "Checking out googleapis-gen repository..."
    sparse_clone "${googleapis_gen_url}" "${first_proto_path}/${destination_path}"
    SOURCE_DIFF_RESULT=0
    diff --strip-trailing-cr -r "googleapis-gen/${first_proto_path}/${destination_path}" "${output_folder}/${destination_path}" \
      -x "*gradle*" \
      -x "gapic_metadata.json" \
      -x "package-info.java" || RESULT=$?
    if [ ${SOURCE_DIFF_RESULT} == 0 ] ; then
      echo "SUCCESS: Comparison finished, no difference is found."
    else
      echo "FAILURE: Differences found in proto path: ${proto_path}." 
      exit "${SOURCE_DIFF_RESULT}"
    fi
  fi

  popd # output_folder
done

# rm -rf "${output_folder}"
