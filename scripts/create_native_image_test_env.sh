#!/bin/bash

set -eo pipefail

function modify_shared_config() {
  xmllint --shell pom.xml <<EOF
  setns x=http://maven.apache.org/POM/4.0.0
  cd .//x:artifactId[text()="google-cloud-shared-config"]
  cd ../x:version
  set ${SHARED_CONFIG_VERSION}
  save pom.xml
EOF
}

function modify_shared_dependencies() {
  xmllint --shell pom.xml <<EOF
  setns x=http://maven.apache.org/POM/4.0.0
  cd .//x:artifactId[text()="google-cloud-shared-dependencies"]
  cd ../x:version
  set ${SHARED_DEPS_VERSION}
  save pom.xml
EOF
}

if [ -z "$GRAALVM_VERSION" ]; then
  echo "Please provide GRAALVM_VERSION"
  exit 1
fi

if [ -z "$NATIVE_MAVEN_PLUGIN" ]; then
  echo "Please provide NATIVE_MAVEN_PLUGIN"
  exit 1
fi

GRAALVM_BRANCH="${GRAALVM_VERSION}_update"

## Round 1: Add gapic-generator-java and update graal-sdk version in GAX.
if [ ! -d "gapic-generator-java" ]; then
  echo "Create gapic-generator-java submodule if one does not exist"
  git submodule add --force https://github.com/googleapis/gapic-generator-java.git
fi

# Modify graal-sdk version in GAX
pushd gapic-generator-java/gax-java
xmllint --shell pom.xml <<EOF
setns x=http://maven.apache.org/POM/4.0.0
cd .//x:artifactId[text()="graal-sdk"]
cd ../x:version
set ${GRAALVM_VERSION}
save pom.xml
EOF

# Get java-shared-dependencies version
popd
pushd gapic-generator-java
SHARED_DEPS_VERSION=$(sed -e 's/xmlns=".*"//' java-shared-dependencies/pom.xml | xmllint --xpath '/project/version/text()' -)
echo $SHARED_DEPS_VERSION

git diff
git checkout -b "${GRAALVM_BRANCH}"
git add gax-java/pom.xml
git commit -m "chore: update graalvm-sdk's version in GAX for testing"
git push origin "${GRAALVM_BRANCH}"
popd

## Round 2: Add java-shared-config if not present and update native-maven-plugin's version
if [ ! -d "java-shared-config" ]; then
  echo "Create java-shared-config submodule if one does not exist"
  git submodule add --force https://github.com/googleapis/java-shared-config.git
fi

# Modify junit-platform-native and native-maven-plugin
pushd java-shared-config
SHARED_CONFIG_VERSION=$(sed -e 's/xmlns=".*"//' pom.xml | xmllint --xpath '/project/version/text()' -)

xmllint --shell pom.xml <<EOF
setns x=http://maven.apache.org/POM/4.0.0
cd .//x:artifactId[text()="junit-platform-native"]
cd ../x:version
set ${NATIVE_MAVEN_PLUGIN}
save pom.xml
EOF

xmllint --shell pom.xml <<EOF
setns x=http://maven.apache.org/POM/4.0.0
cd .//x:artifactId[text()="native-maven-plugin"]
cd ../x:version
set ${NATIVE_MAVEN_PLUGIN}
save pom.xml
EOF

echo "Modified native-maven-plugin in shared-config"
git diff

# Create branch on github
git checkout -b "${GRAALVM_BRANCH}"
git add pom.xml
git commit -m "chore: update native-maven-plugin's version in java-shared-config for testing"
git push origin "${GRAALVM_BRANCH}"
popd

## Round 3: Add java-pubsub if not present and update versions of shared-dependencies and java-shared-config.
if [ ! -d "java-pubsub" ]; then
  echo "Create java-pubsub submodule if one does not exist"
  git submodule add --force https://github.com/googleapis/java-pubsub.git
fi

# Update shared-config and shared-dependencies version
pushd java-pubsub
modify_shared_config
modify_shared_dependencies
echo "Modified shared-config and shared-dependencies versions in java-pubsub"
git diff

git checkout -b graalvm-submodule-test2
git add pom.xml
git commit -m "chore: update shared-dependencies version for testing"
git push origin graalvm-submodule-test2
popd

## Round 4: Add java-bigquery if not present and update versions of shared-dependencies and java-shared-config.
if [ ! -d "java-bigquery" ]; then
  echo "Create java-bigquery submodule if one does not exist"
  git submodule add --force https://github.com/googleapis/java-bigquery.git
fi

# Update shared-config and shared-dependencies version
pushd java-bigquery
modify_shared_config
modify_shared_dependencies
echo "Modified shared-config and shared-dependencies versions in java-bigquery"
git diff
git checkout -b "${GRAALVM_BRANCH}"
git add pom.xml
git commit -m "chore: update shared-dependencies version for testing"
git push origin "${GRAALVM_BRANCH}"
popd

## Round 5: Add java-bigtable if not present and update versions of shared-dependencies and java-shared-config.
if [ ! -d "java-bigtable" ]; then
  echo "Create java-bigtable submodule if one does not exist"
  git submodule add --force https://github.com/googleapis/java-bigtable.git
fi

# Update shared-config and shared-dependencies version
pushd java-bigtable/google-cloud-bigtable-deps-bom
modify_shared_config
modify_shared_dependencies

popd
pushd java-bigtable/google-cloud-bigtable-bom
modify_shared_config

popd
pushd java-bigtable
modify_shared_config

echo "Modified shared-config and shared-dependencies versions in java-bigtable"
git diff

git checkout -b "${GRAALVM_BRANCH}"
git add pom.xml
git add google-cloud-bigtable-deps-bom/pom.xml
git add google-cloud-bigtable-bom/pom.xml
git commit -m "chore: update shared-dependencies version for testing"
git push origin "${GRAALVM_BRANCH}"
popd

## Round 6: Add java-spanner-jdbc if not present and update versions of shared-dependencies and java-shared-config.
if [ ! -d "java-spanner-jdbc" ]; then
  echo "Create java-spanner-jdbc submodule if one does not exist"
  git submodule add --force https://github.com/googleapis/java-spanner-jdbc.git
fi

# Update shared-config and shared-dependencies version
pushd java-spanner-jdbc
modify_shared_config
modify_shared_dependencies
echo "Modified shared-config and shared-dependencies versions in java-spanner-jdbc"
git diff

git checkout -b "${GRAALVM_BRANCH}"
git add pom.xml
git commit -m "chore: update shared-dependencies version for testing"
git push origin "${GRAALVM_BRANCH}"
popd

## Round 7: Push modified repos to submodule repository.
git add gapic-generator-java
git add java-shared-config
git add java-pubsub
git add java-bigquery
git add java-bigtable
git add java-spanner-jdbc
git commit -m "chore: populate the submodule project"
git push origin main
