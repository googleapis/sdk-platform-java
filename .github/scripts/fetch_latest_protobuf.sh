#!/bin/bash

# A script to fetch the latest GA protobuf-java version and update .kokoro/build.sh.

# Exit the script immediately if any command fails.
set -e

echo "Fetching latest protobuf-java version from Maven Central metadata..."
# Fetch the maven-metadata.xml and parse out the valid, stable, GA versions.
# We extract everything between <version> and </version>, ignore RC/alpha/beta/dev/snapshot,
# then sort version numbers and pick the highest one.
LATEST_VERSION=$(curl -s https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/maven-metadata.xml | awk -F'[<>]' '/<version>/ {print $3}' | grep -viE 'rc|alpha|beta|dev|snapshot' | sort -V | tail -n 1)

if [ -z "$LATEST_VERSION" ]; then
    echo "❌ Error: Could not determine latest protobuf-java version."
    exit 1
fi

echo "Latest GA protobuf-java version found: $LATEST_VERSION"

# Update .kokoro/build.sh if it exists
if [ -f ".kokoro/build.sh" ]; then
    echo "Updating .kokoro/build.sh to use -Dprotobuf.version=$LATEST_VERSION..."
    # Update the protobuf version property, accounting for different sed versions (macOS/Linux).
    # Looks for shapes like -Dprotobuf.version=4.33.2 or -Dprotobuf.version=3.25.x
    if sed --version >/dev/null 2>&1; then
        sed -i "s/-Dprotobuf\.version=[a-zA-Z0-9.-]*/-Dprotobuf.version=$LATEST_VERSION/g" .kokoro/build.sh
    else
        # macOS requires an empty string argument for -i
        sed -i "" "s/-Dprotobuf\.version=[a-zA-Z0-9.-]*/-Dprotobuf.version=$LATEST_VERSION/g" .kokoro/build.sh
    fi
    echo "✅ Successfully updated .kokoro/build.sh."
else
    echo "⚠️  .kokoro/build.sh not found, skipping update."
fi
