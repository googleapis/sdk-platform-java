#!/usr/bin/env bash

set -xe

# define utility functions
extract_folder_name() {
  destination_path=$1
  folder_name=${destination_path##*/}
  echo "$folder_name"
}
remove_empty_files() {
  category=$1
  find "$destination_path/$category-$folder_name/src/main/java" -type f -size 0 | while read -r f; do rm -f "${f}"; done
  if [ -d "$destination_path/$category-$folder_name/src/main/java/samples" ]; then
      mv "$destination_path/$category-$folder_name/src/main/java/samples" "$destination_path/$category-$folder_name"
  fi
}

mv_src_files() {
  category=$1 # one of gapic, proto, samples
  type=$2 # one of main, test
  if [ "$category" == "samples" ]; then
    folder_suffix="samples/snippets/generated"
    src_suffix="samples/snippets/generated/src/main/java/com"
  elif [ "$category" == "proto" ]; then
    folder_suffix="$category-$folder_name/src/$type"
    src_suffix="$category/src/$type/java"
  else
    folder_suffix="$category-$folder_name/src"
    src_suffix="src/$type"
  fi
  mkdir -p "$destination_path/$folder_suffix"
  cp -r "$destination_path/java_gapic_srcjar/$src_suffix" "$destination_path/$folder_suffix"
  if [ "$category" != "samples" ]; then
    rm -r -f "$destination_path/$folder_suffix/java/META-INF"
  fi
}

# unzip jar file
unzip_src_files() {
  category=$1
  jar_file=java_$category.jar
  mkdir -p "$destination_path/$category-$folder_name/src/main/java"
  unzip -q -o "$destination_path/$jar_file" -d "$destination_path/$category-$folder_name/src/main/java"
  rm -r -f "$destination_path/$category-$folder_name/src/main/java/META-INF"
}

find_additional_protos_in_yaml() {
  pattern=$1
  find_result=$(grep --include=\*.yaml -rw "$proto_path" -e "$pattern")
  if [ -n "$find_result" ]; then
    echo "$find_result"
  fi
}

search_additional_protos() {
  additional_protos="google/cloud/common_resources.proto" # used by every library
  iam_policy=$(find_additional_protos_in_yaml "name: google.iam.v1.IAMPolicy")
  if [ -n "$iam_policy" ]; then
    additional_protos="$additional_protos google/iam/v1/iam_policy.proto"
  fi
  locations=$(find_additional_protos_in_yaml "name: google.cloud.location.Locations")
  if [ -n "$locations" ]; then
    additional_protos="$additional_protos google/cloud/location/locations.proto"
  fi
  echo "$additional_protos"
}

get_gapic_opts() {
  gapic_config=$(find "${proto_path}" -type f -name "*gapic.yaml")
  if [ -z "${gapic_config}" ]; then
    gapic_config=""
  else
    gapic_config="gapic-config=$gapic_config,"
  fi
  grpc_service_config=$(find "${proto_path}" -type f -name "*service_config.json")
  api_service_config=$(find "${proto_path}" -maxdepth 1 -type f \( -name "*.yaml" ! -name "*gapic.yaml" \))
  if [ "${rest_numeric_enums}" == "true" ]; then
    rest_numeric_enums="rest-numeric-enums,"
  else
    rest_numeric_enums=""
  fi
  echo "transport=$transport,${rest_numeric_enums}grpc-service-config=$grpc_service_config,${gapic_config}api-service-config=$api_service_config"
}

remove_grpc_version() {
  find "$destination_path" -type f -name "*Grpc.java" -exec \
  sed -i 's/value = \"by gRPC proto compiler.*/value = \"by gRPC proto compiler\",/g' {} \;
}

download_gapic_generator_parent_pom() {
  gapic_generator_version=$1
  if [ ! -f parent-pom.xml ]; then
    curl -LJ -o parent-pom.xml "https://repo1.maven.org/maven2/com/google/api/gapic-generator-java-pom-parent/$gapic_generator_version/gapic-generator-java-pom-parent-$gapic_generator_version.pom"
  fi
}

get_grpc_version() {
  gapic_generator_version=$1
  download_gapic_generator_parent_pom "$gapic_generator_version"
  grpc_version=$(grep grpc.version parent-pom.xml | sed 's/<grpc\.version>\(.*\)<\/grpc\.version>/\1/' | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')
  echo "$grpc_version"
}

get_protobuf_version() {
  gapic_generator_version=$1
  download_gapic_generator_parent_pom "$gapic_generator_version"
  protobuf_version=$(grep protobuf.version parent-pom.xml | sed 's/<protobuf\.version>\(.*\)<\/protobuf\.version>/\1/' | sed 's/^[[:space:]]*//;s/[[:space:]]*$//' | cut -d "." -f2-)
  echo "$protobuf_version"
}

download_tools() {
  gapic_generator_version=$1
  protobuf_version=$2
  grpc_version=$3
  download_generator "$gapic_generator_version"
  download_protobuf "$protobuf_version"
  download_grpc_plugin "$grpc_version"
}

download_generator() {
  gapic_generator_version=$1
  cd "$working_directory"
  if [[ "$gapic_generator_version" == *"-SNAPSHOT" ]]; then
    # get SNAPSHOT from maven local repository.
    generator_jar=$HOME/.m2/repository/com/google/api/gapic-generator-java/$gapic_generator_version/gapic-generator-java-$gapic_generator_version.jar
    if [ ! -f "$generator_jar" ]; then
      echo "Can't copy gapic-generator-java-$gapic_generator_version.jar from maven local repository."
      exit 1
    fi
    cp "$generator_jar" gapic-generator-java.jar
    echo "Copy gapic-generator-java-$gapic_generator_version.jar from maven local repository."
  fi

  if [ ! -f "gapic-generator-java-$gapic_generator_version.jar" ]; then
    curl -LJ -o "gapic-generator-java-$gapic_generator_version.jar" https://repo1.maven.org/maven2/com/google/api/gapic-generator-java/"$gapic_generator_version"/gapic-generator-java-"$gapic_generator_version".jar
  fi
}

download_protobuf() {
  protobuf_version=$1
  cd "$working_directory"
  if [ ! -d "protobuf-$protobuf_version" ]; then
    # pull proto files and protoc from protobuf repository
    # maven central doesn't have proto files
    curl -LJ -o "protobuf-$protobuf_version.zip" https://github.com/protocolbuffers/protobuf/releases/download/v"$protobuf_version"/protoc-"$protobuf_version"-linux-x86_64.zip
    unzip -o -q "protobuf-$protobuf_version.zip" -d "protobuf-$protobuf_version"
    cp -r "protobuf-$protobuf_version/include/google" "$working_directory"
  fi

  protoc_path=$working_directory/protobuf-$protobuf_version/bin
  echo "protoc version: $("$protoc_path"/protoc --version)"
}

download_grpc_plugin() {
  grpc_version=$1
  cd "$working_directory"
  if [ ! -f "grpc-java-plugin-$grpc_version" ]; then
    curl -LJ -o "grpc-java-plugin-$grpc_version" https://repo1.maven.org/maven2/io/grpc/protoc-gen-grpc-java/"$grpc_version"/protoc-gen-grpc-java-"$grpc_version"-linux-x86_64.exe
    chmod +x "grpc-java-plugin-$grpc_version"
  fi
}