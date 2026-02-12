#!/bin/bash

# A script to update .kokoro/build.sh with a specific protobuf-java version.
# Usage: ./update_kokoro_protobuf.sh <version>

# Exit the script immediately if any command fails.
set -e

NEW_VERSION="$1"

if [ -z "$NEW_VERSION" ]; then
    echo "❌ Usage: $0 <version>"
    exit 1
fi

# Update .kokoro/build.sh if it exists
if [ -f ".kokoro/build.sh" ]; then
    echo "Updating .kokoro/build.sh to use -Dprotobuf.version=$NEW_VERSION..."
    # Update the protobuf version property, accounting for different sed versions (macOS/Linux).
    # Looks for shapes like -Dprotobuf.version=4.33.2 or -Dprotobuf.version=3.25.x
    if sed --version >/dev/null 2>&1; then
        sed -i "s/-Dprotobuf\.version=[a-zA-Z0-9.-]*/-Dprotobuf.version=$NEW_VERSION/g" .kokoro/build.sh
    else
        # macOS requires an empty string argument for -i
        sed -i "" "s/-Dprotobuf\.version=[a-zA-Z0-9.-]*/-Dprotobuf.version=$NEW_VERSION/g" .kokoro/build.sh
    fi
    echo "✅ Successfully updated .kokoro/build.sh."
else
    echo "⚠️  .kokoro/build.sh not found in the current directory, skipping update."
fi
