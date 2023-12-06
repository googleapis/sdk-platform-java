#!/usr/bin/env bash

set -xeo pipefail

# Utility functions used in `generate_library.sh` and showcase generation.
extract_folder_name() {
  local destination_path=$1
  local folder_name=${destination_path##*/}
  echo "${folder_name}"
}

remove_empty_files() {
  local category=$1
  local destination_path=$2
  local file_num
  find "${destination_path}/${category}-${folder_name}/src/main/java" -type f -size 0 | while read -r f; do rm -f "${f}"; done
  # remove the directory if the directory has no files.
  file_num=$(find "${destination_path}/${category}-${folder_name}" -type f | wc -l | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')
  if [[ "${file_num}" == 0 ]]; then
    rm -rf "${destination_path}/${category}-${folder_name}"
  fi

  if [ -d "${destination_path}/${category}-${folder_name}/src/main/java/samples" ]; then
      mv "${destination_path}/${category}-${folder_name}/src/main/java/samples" "${destination_path}/${category}-${folder_name}"
  fi
}

# Move generated files to folders in destination_path.
mv_src_files() {
  local category=$1 # one of gapic, proto, samples
  local type=$2 # one of main, test
  local destination_path=$3
  if [ "${category}" == "samples" ]; then
    src_suffix="samples/snippets/generated/src/main/java/com"
    folder_suffix="samples/snippets/generated"
  elif [ "${category}" == "proto" ]; then
    src_suffix="${category}/src/${type}/java"
    folder_suffix="${category}-${folder_name}/src/${type}"
  else
    src_suffix="src/${type}"
    folder_suffix="${category}-${folder_name}/src"
  fi
  mkdir -p "${destination_path}/${folder_suffix}"
  cp -r "${destination_path}/java_gapic_srcjar/${src_suffix}" "${destination_path}/${folder_suffix}"
  if [ "${category}" != "samples" ]; then
    rm -r -f "${destination_path}/${folder_suffix}/java/META-INF"
  fi
}

# unzip jar file
unzip_src_files() {
  local category=$1
  local destination_path=$2
  local jar_file=java_${category}.jar
  mkdir -p "${destination_path}/${category}-${folder_name}/src/main/java"
  unzip -q -o "${destination_path}/${jar_file}" -d "${destination_path}/${category}-${folder_name}/src/main/java"
  rm -r -f "${destination_path}/${category}-${folder_name}/src/main/java/META-INF"
}

# get gapic options from .yaml and .json files from proto_path.
get_gapic_opts() {
  local transport=$1
  local rest_numeric_enums=$2
  local gapic_yaml=$3
  local service_config=$4
  local service_yaml=$5
  if [ "${rest_numeric_enums}" == "true" ]; then
    rest_numeric_enums="rest-numeric-enums"
  else
    rest_numeric_enums=""
  fi
  # If any of the gapic options is empty (default value), try to search for
  # it in proto_path.
  if [[ "${gapic_yaml}" == "" ]]; then
    gapic_yaml=$(find "${proto_path}" -type f -name "*gapic.yaml")
  fi

  if [[ "${service_config}" == "" ]]; then
    service_config=$(find "${proto_path}" -type f -name "*service_config.json")
  fi

  if [[ "${service_yaml}" == "" ]]; then
    service_yaml=$(find "${proto_path}" -maxdepth 1 -type f \( -name "*.yaml" ! -name "*gapic*.yaml" \))
  fi
  echo "transport=${transport},${rest_numeric_enums},grpc-service-config=${service_config},gapic-config=${gapic_yaml},api-service-config=${service_yaml}"
}

remove_grpc_version() {
  local destination_path=$1
  find "${destination_path}" -type f -name "*Grpc.java" -exec \
  sed -i.bak 's/value = \"by gRPC proto compiler.*/value = \"by gRPC proto compiler\",/g' {}  \; -exec rm {}.bak \;
}

download_gapic_generator_pom_parent() {
  local gapic_generator_version=$1
  download_generator_artifact "${gapic_generator_version}" "gapic-generator-java-pom-parent-${gapic_generator_version}.pom" "gapic-generator-java-pom-parent"
}

get_grpc_version() {
  local gapic_generator_version=$1
  local grpc_version
  pushd "${output_folder}" > /dev/null
  # get grpc version from gapic-generator-java-pom-parent/pom.xml
  download_gapic_generator_pom_parent "${gapic_generator_version}"
  grpc_version=$(grep grpc.version "gapic-generator-java-pom-parent-${gapic_generator_version}.pom" | sed 's/<grpc\.version>\(.*\)<\/grpc\.version>/\1/' | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')
  popd > /dev/null
  echo "${grpc_version}"
}

get_protobuf_version() {
  local gapic_generator_version=$1
  local protobuf_version
  pushd "${output_folder}" > /dev/null
  # get protobuf version from gapic-generator-java-pom-parent/pom.xml
  download_gapic_generator_pom_parent "${gapic_generator_version}"
  protobuf_version=$(grep protobuf.version "gapic-generator-java-pom-parent-${gapic_generator_version}.pom" | sed 's/<protobuf\.version>\(.*\)<\/protobuf\.version>/\1/' | sed 's/^[[:space:]]*//;s/[[:space:]]*$//' | cut -d "." -f2-)
  popd > /dev/null
  echo "${protobuf_version}"
}

download_tools() {
  local gapic_generator_version=$1
  local protobuf_version=$2
  local grpc_version=$3
  local os_architecture=$4
  pushd "${output_folder}"
  download_generator_artifact "${gapic_generator_version}" "gapic-generator-java-${gapic_generator_version}.jar"
  download_protobuf "${protobuf_version}" "${os_architecture}"
  download_grpc_plugin "${grpc_version}" "${os_architecture}"
  popd
}

download_generator_artifact() {
  local gapic_generator_version=$1
  local artifact=$2
  local project=${3:-"gapic-generator-java"}
  if [ ! -f "${artifact}" ]; then
    # first, try to fetch the generator locally
    local local_fetch_successful
    local_fetch_successful=$(copy_from "$HOME/.m2/repository/com/google/api/${project}/${gapic_generator_version}/${artifact}" \
      "${artifact}")
    if [[ "${local_fetch_successful}" == "false" ]];then 
      # download gapic-generator-java artifact from Google maven central mirror if not
      # found locally
      >&2 echo "${artifact} not found locally. Attempting a download from Maven Central"
      download_from \
      "https://maven-central.storage-download.googleapis.com/maven2/com/google/api/${project}/${gapic_generator_version}/${artifact}" \
      "${artifact}"
      >&2 echo "${artifact} found and downloaded from Maven Central"
    else
      >&2 echo "${artifact} found copied from local repository (~/.m2)"
    fi
  fi
}

download_protobuf() {
  local protobuf_version=$1
  local os_architecture=$2
  if [ ! -d "protobuf-${protobuf_version}" ]; then
    # pull proto files and protoc from protobuf repository as maven central
    # doesn't have proto files
    download_from \
    "https://github.com/protocolbuffers/protobuf/releases/download/v${protobuf_version}/protoc-${protobuf_version}-${os_architecture}.zip" \
    "protobuf-${protobuf_version}.zip" \
    "GitHub"
    unzip -o -q "protobuf-${protobuf_version}.zip" -d "protobuf-${protobuf_version}"
    cp -r "protobuf-${protobuf_version}/include/google" .
    rm "protobuf-${protobuf_version}.zip"
  fi

  protoc_path="${output_folder}/protobuf-${protobuf_version}/bin"
}

download_grpc_plugin() {
  local grpc_version=$1
  local os_architecture=$2
  if [ ! -f "protoc-gen-grpc-java-${grpc_version}-${os_architecture}.exe" ]; then
    # download protoc-gen-grpc-java plugin from Google maven central mirror.
    download_from \
    "https://maven-central.storage-download.googleapis.com/maven2/io/grpc/protoc-gen-grpc-java/${grpc_version}/protoc-gen-grpc-java-${grpc_version}-${os_architecture}.exe" \
    "protoc-gen-grpc-java-${grpc_version}-${os_architecture}.exe"
    chmod +x "protoc-gen-grpc-java-${grpc_version}-${os_architecture}.exe"
  fi
}

download_from() {
  local url=$1
  local save_as=$2
  local repo=$3
  # fail-fast, 30 seconds at most, retry 2 times
  curl -LJ -o "${save_as}" --fail -m 30 --retry 2 "$url" || download_fail "${save_as}" "${repo}"
}

# copies the specified file in $1 to $2
# will return "true" if the copy was successful
copy_from() {
  local local_repo=$1
  local save_as=$2
  copy_successful=$(cp "${local_repo}" "${save_as}" && echo "true" || echo "false")
  echo "${copy_successful}"
}

download_fail() {
  local artifact=$1
  local repo=${2:-"maven central mirror"}
  >&2 echo "Fail to download ${artifact} from ${repo} repository. Please install ${artifact} first if you want to use a non-published artifact."
  exit 1
}

# gets the output folder where all sources and dependencies will be located.
get_output_folder() {
  echo "$(pwd)/output"
}

detect_os_architecture() {
  local os_type
  local os_architecture
  os_type=$(uname -sm)
  case "${os_type}" in
    *"Linux x86_64"*)
      os_architecture="linux-x86_64"
      ;;
    *"Darwin x86_64"*)
      os_architecture="osx-x86_64"
      ;;
    *)
      os_architecture="osx-aarch_64"
      ;;
  esac
  echo "${os_architecture}"
}

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

# copies $1 as a folder as $2 only if $1 exists
copy_directory_if_exists() {
  local source_folder=$1
  local destination_folder=$2
  if [ -d "${source_folder}" ]; then
    cp -r "${source_folder}" "${destination_folder}"
  fi
}

# computes proto_path from a given folder of preprocessed sources
# It will inspect the proto library to compute the path
get_proto_path_from_preprocessed_sources() {
  local sources=$1
  pushd "${sources}" > /dev/null
  local proto_library=$(find . -maxdepth 1 -type d -name 'proto-*')
  popd > /dev/null # sources
  pushd "${proto_library}/src/main/proto" > /dev/null
  local result=$(find . -type f -name '*.proto' | head -n 1 | xargs dirname | sed 's/\.\///')
  popd > /dev/null # proto_library
  echo "${result}"
}
