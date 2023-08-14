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
  find "$destination_path" -type f -name "*ServiceGrpc.java" -exec \
  sed -i 's/value = \"by gRPC proto compiler.*/value = \"by gRPC proto compiler\",/g' {} \;
}
