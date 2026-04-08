#!/bin/bash
# revert_versions_robust.sh

declare -A versions
versions["gax"]="2.76.1-SNAPSHOT"
versions["gax-grpc"]="2.76.1-SNAPSHOT"
versions["api-common"]="2.59.1-SNAPSHOT"
versions["proto-google-iam-v3"]="1.62.1-SNAPSHOT"
versions["proto-google-iam-v3beta"]="1.62.1-SNAPSHOT"
versions["grpc-google-iam-v3"]="1.62.1-SNAPSHOT"
versions["grpc-google-iam-v3beta"]="1.62.1-SNAPSHOT"
versions["google-cloud-dns"]="2.33.0-SNAPSHOT"
versions["google-cloud-service-control"]="1.35.0-SNAPSHOT"
versions["google-cloud-tasks"]="2.35.0-SNAPSHOT"
versions["gapic-generator-java"]="2.68.1-SNAPSHOT"

for feature in "${!versions[@]}"; do
  version="${versions[$feature]}"
  echo "Reverting $feature to $version"
  # Robust regex matching optional spaces before <!--
  find . -type f \( -name "pom.xml" -o -name "*.yaml" -o -name "*.Dockerfile" \) -exec sed -i -E "s|<version>.*</version>\s*<!--\s*\{x-version-update:$feature:current\}\s*-->|<version>$version</version><!-- {x-version-update:$feature:current} -->|g" {} +
done

# Handle special cases for google-cloud-shared-dependencies in YAML and properties
echo "Handling special cases for google-cloud-shared-dependencies"
find . -type f \( -name "*.yaml" -o -name "pom.xml" \) -exec sed -i -E "s|_SHARED_DEPENDENCIES_VERSION: '.*' # \{x-version-update:google-cloud-shared-dependencies:current\}|_SHARED_DEPENDENCIES_VERSION: '3.58.1-SNAPSHOT' # {x-version-update:google-cloud-shared-dependencies:current}|g" {} +
find . -type f \( -name "*.yaml" -o -name "pom.xml" \) -exec sed -i -E "s|value: \"gcr.io/cloud-devrel-public-resources/graalvm_sdk_platform_(a\|b\|c):.*\" # \{x-version-update:google-cloud-shared-dependencies:current\}|value: \"gcr.io/cloud-devrel-public-resources/graalvm_sdk_platform_\1:3.58.1-SNAPSHOT\" # {x-version-update:google-cloud-shared-dependencies:current}|g" {} +
find . -type f -name "pom.xml" -exec sed -i -E "s|<google-cloud-shared-dependencies.version>.*</google-cloud-shared-dependencies.version>\s*<!--\s*\{x-version-update:google-cloud-shared-dependencies:current\}\s*-->|<google-cloud-shared-dependencies.version>3.58.1-SNAPSHOT</google-cloud-shared-dependencies.version> <!-- {x-version-update:google-cloud-shared-dependencies:current} -->|g" {} +

# Also handle standard version tag for google-cloud-shared-dependencies if any
find . -type f -name "pom.xml" -exec sed -i -E "s|<version>.*</version>\s*<!--\s*\{x-version-update:google-cloud-shared-dependencies:current\}\s*-->|<version>3.58.1-SNAPSHOT</version><!-- {x-version-update:google-cloud-shared-dependencies:current} -->|g" {} +
