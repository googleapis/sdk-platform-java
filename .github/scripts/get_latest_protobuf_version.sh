#!/bin/bash

# A script to fetch the latest GA protobuf-java version from Maven Central.
# Outputs strictly the version string (e.g., "4.33.5") to stdout.

# Exit the script immediately if any command fails.
set -e

# Fetch the maven-metadata.xml and parse out the valid, stable, GA versions.
# We extract everything between <version> and </version>, ignore RC/alpha/beta/dev/snapshot,
# then sort version numbers and pick the highest one.
LATEST_VERSION=$(curl -s https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/maven-metadata.xml | awk -F'[<>]' '/<version>/ {print $3}' | grep -viE 'rc|alpha|beta|dev|snapshot' | sort -V | tail -n 1)

if [ -z "$LATEST_VERSION" ]; then
    echo "❌ Error: Could not determine latest protobuf-java version." >&2
    exit 1
fi

echo "$LATEST_VERSION"
