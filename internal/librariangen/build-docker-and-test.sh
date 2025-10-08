#!/bin/bash
# Copyright 2025 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


set -e
SCRIPT_DIR=$(dirname "$0")
IMAGE_NAME="librariangen-test"

echo "Building Docker image..."
docker build -t "${IMAGE_NAME}" "${SCRIPT_DIR}"

echo "Running version check..."
output=$(docker run --rm -e GOOGLE_SDK_JAVA_LOGGING_LEVEL=quiet "${IMAGE_NAME}" --version)

if [[ ! "$output" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
  echo "Version format is incorrect. Got: $output"
  exit 1
fi

echo "Version check passed. Version is $output"