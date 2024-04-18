#!/usr/bin/env bash

set -eo pipefail
utilities_script_dir=$(dirname "$(realpath "${BASH_SOURCE[0]}")")

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
  if [[ $(basename $(pwd)) != "output" ]]; then
    echo "$(pwd)/output" 
  else
    echo $(pwd)
  fi
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


# copies $1 as a folder as $2 only if $1 exists
copy_directory_if_exists() {
  local base_folder=$1
  local folder_prefix=$2
  local destination_folder=$3
  if [ ! -d "${base_folder}" ]; then
    return
  fi
  pushd "${base_folder}"
  if [[ $(find . -maxdepth 1 -type d -name "${folder_prefix}*" | wc -l ) -gt 0  ]]; then
    cp -r ${base_folder}/${folder_prefix}* "${destination_folder}"
  fi
  popd # base_folder
}

# computes proto_path from a given folder of GAPIC sources
# It will inspect the proto library to compute the path
get_proto_path_from_preprocessed_sources() {
  set -e
  local sources=$1
  pushd "${sources}" > /dev/null
  local proto_library=$(find . -maxdepth 1 -type d -name 'proto-*' | sed 's/\.\///')
  local found_libraries=$(echo "${proto_library}" | wc -l)
  if [ -z ${proto_library} ]; then
    echo "no proto libraries found in the supplied sources path"
    exit 1
  elif [ ${found_libraries} -gt 1 ]; then
    echo "more than one proto library found in the supplied sources path"
    echo "cannot decide for a service version"
    exit 1
  fi
  pushd "$(pwd)/${proto_library}/src/main/proto" > /dev/null
  local result=$(find . -type f -name '*.proto' | head -n 1 | xargs dirname | sed 's/\.\///')
  popd > /dev/null # proto_library
  popd > /dev/null # sources
  echo "${result}"
}

# for a pre-processed library stored in $preprocessed_sources_path, a folder
# tree is built on $target_folder so it looks like a googleapis-gen folder and
# is therefore consumable by OwlBot CLI
build_owlbot_cli_source_folder() {
  local postprocessing_target=$1
  local target_folder=$2
  local preprocessed_sources_path=$3
  local proto_path=$4
  if [[ -z "${proto_path}" ]]; then
    proto_path=$(get_proto_path_from_preprocessed_sources "${preprocessed_sources_path}")
  fi
  owlbot_staging_folder="${postprocessing_target}/owl-bot-staging"
  mkdir -p "${owlbot_staging_folder}"

  # By default (thanks to generation templates), .OwlBot-hermetic.yaml `deep-copy` section
  # references a wildcard pattern matching a folder
  # ending with `-java` at the leaf of proto_path. We then use a generated-java
  # folder that will be picked up by copy-code
  mkdir -p "${target_folder}/${proto_path}/generated-java"
  copy_directory_if_exists "${preprocessed_sources_path}" "proto" \
    "${target_folder}/${proto_path}/generated-java/proto-google-cloud-library"
  copy_directory_if_exists "${preprocessed_sources_path}" "grpc" \
    "${target_folder}/${proto_path}/generated-java/grpc-google-cloud-library"
  copy_directory_if_exists "${preprocessed_sources_path}" "gapic" \
    "${target_folder}/${proto_path}/generated-java/gapic-google-cloud-library"
  copy_directory_if_exists "${preprocessed_sources_path}" "samples" \
    "${target_folder}/${proto_path}/generated-java/samples"
  pushd "${target_folder}"
  # create an empty commit so owl-bot-copy can process this as a repo
  # (it cannot process non-git-repositories)
  git init
  git commit --allow-empty -m 'empty commit'
  popd # target_folder
}

# Convenience function to clone only the necessary folders from a git repository
sparse_clone() {
  repo_url=$1
  paths=$2
  commitish=$3
  clone_dir=$(basename "${repo_url%.*}")
  rm -rf "${clone_dir}"
  git clone -n --depth=1 --no-single-branch --filter=tree:0 "${repo_url}"
  pushd "${clone_dir}"
  if [ -n "${commitish}" ]; then
    git checkout "${commitish}"
  fi
  git sparse-checkout set --no-cone ${paths}
  git checkout
  popd
}

# calls a function in utilities.py. THe first argument is the function name, the
# rest of the arguments are the positional arguments to such function
py_util() {
  python3 "${utilities_script_dir}/utilities.py" "$@"
}

download_googleapis_files_and_folders() {
  local output_folder=$1
  local googleapis_commitish=$2
  # checkout the master branch of googleapis/google (proto files) and WORKSPACE
  echo "Checking out googlapis repository..."
  # sparse_clone will remove folder contents first, so we have to checkout googleapis
  # only once.
  sparse_clone https://github.com/googleapis/googleapis.git "google grafeas" "${googleapis_commitish}"
  pushd googleapis
  cp -r google "${output_folder}"
  cp -r grafeas "${output_folder}"
}

