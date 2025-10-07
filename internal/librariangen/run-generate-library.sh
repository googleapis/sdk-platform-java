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

# This script generates a library using the compiled librariangen binary.
# It simulates the environment that the Librarian tool would create by:
# 1. Creating a temporary directory structure for inputs and outputs.
# 2. Copying the testdata protos into the temporary source directory.
# 3. Creating a generate-request.json file.
# 4. Compiling and running the librariangen binary with flags pointing to the
#    temporary directories.

set -e # Exit immediately if a command exits with a non-zero status.
cd "$(dirname "$0")" # Change to the script's directory to make relative paths work.

LIBRARIANGEN_GOTOOLCHAIN=local
LIBRARIANGEN_LOG=librariangen.log
GAPIC_GENERATOR_VERSION="2.62.3"
echo "Cleaning up from last time: rm -f $LIBRARIANGEN_LOG"
rm -f "$LIBRARIANGEN_LOG"

# --- Dependency Checks & Version Info ---
(
echo "--- Tool Versions ---"
echo "Go: $(GOWORK=off GOTOOLCHAIN=${LIBRARIANGEN_GOTOOLCHAIN} go version)"
echo "protoc: $(protoc --version 2>&1)"
echo "---------------------"
) >> "$LIBRARIANGEN_LOG" 2>&1

# Ensure that all required protoc dependencies are available in PATH.
if ! command -v "protoc" &> /dev/null; then
  echo "Error: protoc not found in PATH. Please install it."
fi

# Download gapic-generator-java
echo "Downloading gapic-generator-java version $GAPIC_GENERATOR_VERSION..."
wget -q https://repo1.maven.org/maven2/com/google/api/gapic-generator-java/$GAPIC_GENERATOR_VERSION/gapic-generator-java-$GAPIC_GENERATOR_VERSION.jar -O gapic-generator-java.jar
#cp /usr/local/google/home/meltsufin/projects/sdk-platform-java/gapic-generator-java/target/gapic-generator-java-2.62.4-SNAPSHOT.jar gapic-generator-java.jar

# Create wrapper script
ABS_PATH=$(pwd)
cat > protoc-gen-java_gapic << EOL
#!/bin/bash
set -e
exec java -cp "$ABS_PATH/gapic-generator-java.jar" com.google.api.generator.Main $@
EOL
chmod +x protoc-gen-java_gapic

echo "Cleaning up from last time..."
rm -rf ./output ./librarian

# --- Setup ---

enable_post_processor=true

# Define the directories replicating the mounts in the Docker container.
LIBRARIAN_DIR="$(pwd)/librarian"
OUTPUT_DIR="$(pwd)/output"
mkdir -p "$LIBRARIAN_DIR" "$OUTPUT_DIR"

# Use an external googleapis checkout.
if [ ! -d "$LIBRARIANGEN_GOOGLEAPIS_DIR" ]; then
  echo "Error: LIBRARIANGEN_GOOGLEAPIS_DIR is not set or not a directory."
  echo "Please set it to the path of your local googleapis clone."
  exit 1
fi
echo "Using googleapis source from $LIBRARIANGEN_GOOGLEAPIS_DIR"
SOURCE_DIR="$LIBRARIANGEN_GOOGLEAPIS_DIR"

# The compiled binary will be placed in the current directory.
BINARY_PATH="./librariangen"
echo "Cleaning up from last time: rm -f $BINARY_PATH"
rm -f "$BINARY_PATH"

# --- Prepare Inputs ---

# 1. Copy the generate-request.json into the librarian directory.
cp "testdata/generate/librarian/generate-request.json" "$LIBRARIAN_DIR/"

# --- Execute ---

# 3. Compile the librariangen binary.
echo "Compiling librariangen..."
GOWORK=off GOTOOLCHAIN=${LIBRARIANGEN_GOTOOLCHAIN} go build -o "$BINARY_PATH" .

# 4. Run the librariangen generate command.
echo "Running librariangen..."
enable_post_processor=false
if [ "$enable_post_processor" = true ]; then
    PATH=$(pwd):$(GOWORK=off GOTOOLCHAIN=${LIBRARIANGEN_GOTOOLCHAIN} go env GOPATH)/bin:$HOME/go/bin:$PATH ./librariangen generate \
      --source="$SOURCE_DIR" \
      --librarian="$LIBRARIAN_DIR" \
      --output="$OUTPUT_DIR" >> "$LIBRARIANGEN_LOG" 2>&1
else
    PATH=$(pwd):$(GOWORK=off GOTOOLCHAIN=${LIBRARIANGEN_GOTOOLCHAIN} go env GOPATH)/bin:$HOME/go/bin:$PATH ./librariangen generate \
      --source="$SOURCE_DIR" \
      --librarian="$LIBRARIAN_DIR" \
      --output="$OUTPUT_DIR" \
      --disable-post-processor >> "$LIBRARIANGEN_LOG" 2>&1
fi

echo "Library generation complete."
echo "Generated files are available in: ./output"
echo "Librariangen logs are available in: $LIBRARIANGEN_LOG"
