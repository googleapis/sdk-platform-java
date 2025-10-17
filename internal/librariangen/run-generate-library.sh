#!/bin/bash
# Copyright 2025 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# This script is a development tool for the librariangen command. It
# orchestrates the end-to-end process of generating a Java client library from a
# test configuration.
#
# Key steps include:
#   1. Setting up an isolated workspace directory.
#   2. Downloading the required version of the gapic-generator-java artifact.
#   3. Compiling the librariangen Go binary.
#   4. Executing the `librariangen generate` command with pre-defined test
#      inputs and capturing its output and logs.
#
# This allows for quick, reproducible test runs of the entire generation
# process.

set -e # Exit immediately if a command exits with a non-zero status.

cd "$(dirname "$0")" # Change to the script's directory to make relative paths work.

# --- Configuration ---
WORKSPACE="$(pwd)/workspace"
LIBRARIANGEN_GOOGLEAPIS_DIR=../../../googleapis
LIBRARIANGEN_GOTOOLCHAIN=local
GAPIC_GENERATOR_VERSION="2.62.3"
LIBRARIANGEN_LOG="$WORKSPACE/librariangen.log"

# --- Cleanup and Setup ---
echo "Cleaning up from last time..."
rm -rf "$WORKSPACE"
mkdir -p "$WORKSPACE"
echo "Working directory: $WORKSPACE"

# --- Dependency Checks & Version Info ---
(
  echo "--- Tool Versions ---"
  echo "Go: $(GOWORK=off GOTOOLCHAIN=${LIBRARIANGEN_GOTOOLCHAIN} go version)"
  echo "protoc: $(protoc --version 2>&1)"
  echo "---------------------\n"
) >> "$LIBRARIANGEN_LOG" 2>&1

# Ensure that all required protoc dependencies are available in PATH.
if ! command -v "protoc" &> /dev/null; then
  echo "Error: protoc not found in PATH. Please install it."
  exit 1
fi

# --- Download and Prepare Tools ---
echo "Downloading gapic-generator-java version $GAPIC_GENERATOR_VERSION..."
wget -q "https://repo1.maven.org/maven2/com/google/api/gapic-generator-java/$GAPIC_GENERATOR_VERSION/gapic-generator-java-$GAPIC_GENERATOR_VERSION.jar" -O "$WORKSPACE/gapic-generator-java.jar"

# Create wrapper script for protoc-gen-java_gapic
echo "Creating protoc-gen-java_gapic wrapper..."
cat > "$WORKSPACE/protoc-gen-java_gapic" << EOL
#!/bin/bash
set -e
exec java -cp "$WORKSPACE/gapic-generator-java.jar" com.google.api.generator.Main \$@
EOL
chmod +x "$WORKSPACE/protoc-gen-java_gapic"

# --- Prepare Inputs ---
LIBRARIAN_DIR="$WORKSPACE/librarian"
OUTPUT_DIR="$WORKSPACE/output"
mkdir -p "$LIBRARIAN_DIR" "$OUTPUT_DIR"

# Use an external googleapis checkout.
if [ ! -d "$LIBRARIANGEN_GOOGLEAPIS_DIR" ]; then
  echo "Error: LIBRARIANGEN_GOOGLEAPIS_DIR is not set or not a directory."
  echo "Please set it to the path of your local googleapis clone."
  exit 1
fi
echo "Using googleapis source from $LIBRARIANGEN_GOOGLEAPIS_DIR"
SOURCE_DIR=$(cd "$LIBRARIANGEN_GOOGLEAPIS_DIR" && pwd)

# Copy the generate-request.json into the librarian directory.
cp "testdata/generate/librarian/generate-request.json" "$LIBRARIAN_DIR/"

# --- Execute ---
# Compile the librariangen binary.
BINARY_PATH="$WORKSPACE/librariangen"
echo "Compiling librariangen..."
GOWORK=off GOTOOLCHAIN=${LIBRARIANGEN_GOTOOLCHAIN} go build -o "$BINARY_PATH" .

# Run the librariangen generate command.
echo "Running librariangen..."
PATH="$WORKSPACE:$(GOWORK=off GOTOOLCHAIN=${LIBRARIANGEN_GOTOOLCHAIN} go env GOPATH)/bin:$HOME/go/bin:$PATH" \
"$BINARY_PATH" generate \
  --source="$SOURCE_DIR" \
  --librarian="$LIBRARIAN_DIR" \
  --output="$OUTPUT_DIR" \
   >> "$LIBRARIANGEN_LOG" 2>&1

echo "Library generation complete."
echo "Generated files are available in: $OUTPUT_DIR"
echo "Librariangen logs are available in: $LIBRARIANGEN_LOG"
