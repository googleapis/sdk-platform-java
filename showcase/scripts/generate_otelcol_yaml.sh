#!/bin/bash

# Check if both endpoint and file path are provided
if [ "$#" -ne 3 ]; then
    echo "Usage: $0 <port_number> <file_path>"
    exit 1
fi

localhost_port="$1"
file_path="$2"
output_file="$3"

# Define YAML template
yaml_template="receivers:
  otlp:
    protocols:
      grpc:
        endpoint: \"localhost:$localhost_port\"

exporters:
  file:
    path: \"$file_path\"

service:
  extensions: []
  pipelines:
    metrics:
      receivers: [otlp]
      processors: []
      exporters: [file]"

# Replace the port number after "localhost:"
yaml_content=$(echo "$yaml_template" | sed "s|localhost:[0-9]*|localhost:$localhost_port|")

mkdir -p "../test_data"

# Write the modified YAML content to a file
echo "$yaml_content" > "$output_file"

echo "YAML file successfully generated."