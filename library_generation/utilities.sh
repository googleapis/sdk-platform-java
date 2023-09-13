#!/usr/bin/env bash

set -xeo pipefail

# define utility functions

extract_folder_name() {
  local destination_path=$1
  local folder_name=${destination_path##*/}
  echo "$folder_name"
}

remove_empty_files() {
  local category=$1
  find "${destination_path}/${category}-${folder_name}/src/main/java" -type f -size 0 | while read -r f; do rm -f "${f}"; done
  if [ -d "${destination_path}/${category}-${folder_name}/src/main/java/samples" ]; then
      mv "${destination_path}/${category}-${folder_name}/src/main/java/samples" "${destination_path}/${category}-${folder_name}"
  fi
}

# Move generated files to folders in destination_path.
mv_src_files() {
  local category=$1 # one of gapic, proto, samples
  local type=$2 # one of main, test
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
  local jar_file=java_${category}.jar
  mkdir -p "${destination_path}/${category}-${folder_name}/src/main/java"
  unzip -q -o "${destination_path}/${jar_file}" -d "${destination_path}/${category}-${folder_name}/src/main/java"
  rm -r -f "${destination_path}/${category}-${folder_name}/src/main/java/META-INF"
}

find_additional_protos_in_yaml() {
  local pattern=$1
  local find_result
  find_result=$(grep --include=\*.yaml -rw "${proto_path}" -e "${pattern}")
  if [ -n "${find_result}" ]; then
    echo "${find_result}"
  fi
}

# Apart from proto files in proto_path, additional protos are needed in order
# to generate GAPIC client libraries.
# In most cases, these protos should be within google/ directory, which is
# pulled from googleapis as a prerequisite.
# Search additional protos in .yaml files.
search_additional_protos() {
  additional_protos="google/cloud/common_resources.proto" # used by every library
  iam_policy=$(find_additional_protos_in_yaml "name: '*google.iam.v1.IAMPolicy'*")
  if [ -n "$iam_policy" ]; then
    additional_protos="$additional_protos google/iam/v1/iam_policy.proto"
  fi
  locations=$(find_additional_protos_in_yaml "name: '*google.cloud.location.Locations'*")
  if [ -n "${locations}" ]; then
    additional_protos="$additional_protos google/cloud/location/locations.proto"
  fi
  echo "${additional_protos}"
}

# get gapic options from .yaml and .json files from proto_path.
get_gapic_opts() {
  local gapic_config
  local grpc_service_config
  local api_service_config
  gapic_config=$(find "${proto_path}" -type f -name "*gapic.yaml")
  if [ -z "${gapic_config}" ]; then
    gapic_config=""
  else
    gapic_config="gapic-config=${gapic_config},"
  fi
  grpc_service_config=$(find "${proto_path}" -type f -name "*service_config.json")
  api_service_config=$(find "${proto_path}" -maxdepth 1 -type f \( -name "*.yaml" ! -name "*gapic.yaml" \))
  if [ "${rest_numeric_enums}" == "true" ]; then
    rest_numeric_enums="rest-numeric-enums,"
  else
    rest_numeric_enums=""
  fi
  echo "transport=${transport},${rest_numeric_enums}grpc-service-config=${grpc_service_config},${gapic_config}api-service-config=${api_service_config}"
}

remove_grpc_version() {
  find "${destination_path}" -type f -name "*Grpc.java" -exec \
  sed -i.bak 's/value = \"by gRPC proto compiler.*/value = \"by gRPC proto compiler\",/g' {}  \; -exec rm {}.bak \;
}

download_gapic_generator_pom_parent() {
  local gapic_generator_version=$1
  if [ ! -f "gapic-generator-java-pom-parent-${gapic_generator_version}.pom" ]; then
    if [[ "${gapic_generator_version}" == *"-SNAPSHOT" ]]; then
      # copy a SNAPSHOT version from maven local repository.
      copy_from "$HOME/.m2/repository/com/google/api/gapic-generator-java-pom-parent/${gapic_generator_version}/gapic-generator-java-pom-parent-${gapic_generator_version}.pom" \
      "gapic-generator-java-pom-parent-${gapic_generator_version}.pom"
      return
    fi
    # download gapic-generator-java-pom-parent from Google maven central mirror.
    download_from \
    "https://maven-central.storage-download.googleapis.com/maven2/com/google/api/gapic-generator-java-pom-parent/${gapic_generator_version}/gapic-generator-java-pom-parent-${gapic_generator_version}.pom" \
    "gapic-generator-java-pom-parent-${gapic_generator_version}.pom"
  fi
  # file exists, do not need to download again.
}

get_grpc_version() {
  local gapic_generator_version=$1
  local grpc_version
  # get grpc version from gapic-generator-java-pom-parent/pom.xml
  download_gapic_generator_pom_parent "${gapic_generator_version}"
  grpc_version=$(grep grpc.version "gapic-generator-java-pom-parent-${gapic_generator_version}.pom" | sed 's/<grpc\.version>\(.*\)<\/grpc\.version>/\1/' | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')
  echo "$grpc_version"
}

get_protobuf_version() {
  local gapic_generator_version=$1
  local protobuf_version
  # get protobuf version from gapic-generator-java-pom-parent/pom.xml
  download_gapic_generator_pom_parent "${gapic_generator_version}"
  protobuf_version=$(grep protobuf.version "gapic-generator-java-pom-parent-${gapic_generator_version}.pom" | sed 's/<protobuf\.version>\(.*\)<\/protobuf\.version>/\1/' | sed 's/^[[:space:]]*//;s/[[:space:]]*$//' | cut -d "." -f2-)
  echo "${protobuf_version}"
}

download_tools() {
  pushd "${output_folder}"
  local gapic_generator_version=$1
  local protobuf_version=$2
  local grpc_version=$3
  local os_architecture=$4
  download_generator "${gapic_generator_version}"
  download_protobuf "${protobuf_version}" "${os_architecture}"
  download_grpc_plugin "${grpc_version}" "${os_architecture}"
  popd
}

download_generator() {
  local gapic_generator_version=$1
  if [ ! -f "gapic-generator-java-${gapic_generator_version}.jar" ]; then
    if [[ "${gapic_generator_version}" == *"-SNAPSHOT" ]]; then
      # copy a SNAPSHOT version from maven local repository.
      copy_from "$HOME/.m2/repository/com/google/api/gapic-generator-java/${gapic_generator_version}/gapic-generator-java-${gapic_generator_version}.jar" \
      "gapic-generator-java-${gapic_generator_version}.jar"
      return
    fi
    # download gapic-generator-java from Google maven central mirror.
    download_from \
    "https://maven-central.storage-download.googleapis.com/maven2/com/google/api/gapic-generator-java/${gapic_generator_version}/gapic-generator-java-${gapic_generator_version}.jar" \
    "gapic-generator-java-${gapic_generator_version}.jar"
  fi
}

download_protobuf() {
  local protobuf_version=$1
  local os_architecture=$2
  if [ ! -d "protobuf-${protobuf_version}.zip" ]; then
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

copy_from() {
  local local_repo=$1
  local save_as=$2
  cp "${local_repo}" "${save_as}" || \
    download_fail "${save_as}" "maven local"
}

download_fail() {
  local artifact=$1
  local repo=${2:-"maven central mirror"}
  >&2 echo "Fail to download ${artifact} from ${repo} repository. Please install ${artifact} first if you want to download a SNAPSHOT."
  exit 1
}

# Obtains a version from a bazel WORKSPACE file
#
# versions look like "_ggj_version="1.2.3"
# It will return 1.2.3 for such example
get_version_from_WORKSPACE() {
  version_key_word=$1
  workspace=$2
  version=$(\
    grep "${version_key_word}" "${workspace}" |\
    head -n 1 |\
    sed 's/\(.*\) = "\(.*\)"\(.*\)/\2/' |\
    sed 's/[a-zA-Z-]*//'
  )
  echo "${version}"
}

# Used to obtain configuration values from a bazel BUILD file
#
# inspects a $build_file for a certain $rule (e.g. java_gapic_library). If the
# first 15 lines after the declaration of the rule contain $pattern, then
# it will return $if_match if $pattern is found, otherwise $default
get_config_from_BUILD() {
  build_file=$1
  rule=$2
  pattern=$3
  default=$4
  if_match=$5

  result="${default}"
  if grep -A 15 "${rule}" "${build_file}" | grep -q "${pattern}"; then
    result="${if_match}"
  fi
  echo "${result}"
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

# takes a versions.txt file and returns its version
get_version_from_versions_txt() {
  versions=$1
  key=$2
  version=$(grep "$key:" "${versions}" | cut -d: -f3) # 3rd field is snapshot
  echo "${version}"
}
