#!/usr/bin/env bash

set -ex

script_dir=$(dirname "$(realpath "$0")")
proto_path_list=$1
proto_path=$2
destination_path=$3
cd "$script_dir"/..
owlbot_post_processor_sha=$(grep sha < "$(find . -name .OwlBot.lock.yaml)" | cut -d ":" -f 3)

cd "$script_dir"
if [ ! -f proto_path_list ]; then
  # generate a single library
  exit
fi

# generate a set of libraries
while read -r line; do
  proto_path=$(cut -d "," -f 1 "$line")
  destination_path=$(cut -d "," -f 2 "$line")

done < "$proto_path_list"
