#!/bin/bash

# For each {module}/pom.xml
# Removes dependencyManagement and pluginManagement that should be handled in the parent
# Also removes other configuration that is handled in google-cloud-pom-parent and google-cloud-jar-parent

set -ex

library_root=$1

function runRegexOnPoms {
  pom_file=$1
  perl_command=$2
  search=$3

  if grep -q "${search}" "$pom_file" && [[ $(wc -c < "$pom_file") !=  $(perl -0pe "$perl_command" "$pom_file" | wc -c) ]]; then
    # execute the replacement in pom.xml
    echo "Applying $perl_command for $pom_file"
    perl -i -0pe "$perl_command" "$pom_file"
  fi

}

function removeArtifact {
  pom_file=$1
  type=$2
  name=$3
  parent=$4
  perl_command="s/(<${parent}>.*?)\s*<${type}>\s*?<groupId>[a-z\-\.]*<\/groupId>\s*?<artifactId>${name//-/\-}<\/artifactId>.*?<\/${type}>(.*?<\/${parent}>)/\$1\$2/s"
  runRegexOnPoms "$pom_file" "$perl_command" "$name"
}

function removeArtifactVersion {
  pom_file=$1
  type=$2
  name=$3
  perl_command="s/(\s*<${type}>\s*?<groupId>[a-z\-\.]*<\/groupId>\s*?<artifactId>${name//-/\-}<\/artifactId>\s*?(<scope>.*?<\/scope>)?)\s*?<version>[^\\n]*?<\/version>(.*?<\/${type}>)/\$1\$3/s"
  runRegexOnPoms "$pom_file" "$perl_command" "$name"
}

function annotateArtifactVersion {
  pom_file=$1
  type=$2
  name=$3
  perl_command="s/(\s*<${type}>\s*?<groupId>[a-z\-\.]*<\/groupId>\s*?<artifactId>${name//-/\-}<\/artifactId>\s*?(<scope>.*?<\/scope>)?\s*?)(<version>[^\\n]*?<\/version>)([^:\\n]*\\n.*?<\/${type}>)/\$1\$3<!-- {x-version-update:${name//-/\-}:current} -->\$4/s"
  runRegexOnPoms "$pom_file" "$perl_command" "$name"
}

function removeManagedDependency {
  pom_file=$1
  dependency=$2
  removeArtifact "$pom_file" 'dependency' "${dependency}" "dependencyManagement"
}

function removeElement {
  pom_file=$1
  element=$2
  perl_command="s/\s*<${element}>.*?<\/${element}>//s"
  runRegexOnPoms "$pom_file" "$perl_command" "<${element}>"
}

function setGrafeasCheckstyleHeaderConfig {
  pom_file=$1
  perl_command="s/(\s*<properties>\s*.*?)(\s*<checkstyle.header.file>grafeas.header<\/checkstyle.header.file>)?(\s*<\/properties>)/\$1\n    <checkstyle.header.file>grafeas.header<\/checkstyle.header.file>\$3/s"
  runRegexOnPoms "$pom_file" "$perl_command" ">Grafeas Client<"
}


function commonPomProcessing {
  pom_file=$1
  setGrafeasCheckstyleHeaderConfig $pom_file
  removeManagedDependency $pom_file 'google-cloud-shared-dependencies'
  removeManagedDependency $pom_file 'junit'
  removeManagedDependency $pom_file 'joda-time'
  removeManagedDependency $pom_file 'truth'
  removeManagedDependency $pom_file 'easymock'
  removeManagedDependency $pom_file 'perfmark-api'
  removeManagedDependency $pom_file 'google-cloud-pubsub'
  removeManagedDependency $pom_file 'proto-google-cloud-pubsub-v1'
  removeManagedDependency $pom_file 'google-cloud-pubsub-bom'
  removeManagedDependency $pom_file 'google-api-services-translate'
  removeElement $pom_file 'reporting'
  removeElement $pom_file 'developers'
  removeElement $pom_file 'organization'
  removeElement $pom_file 'scm'
  removeElement $pom_file 'issueManagement'
  removeElement $pom_file 'licenses'
  removeElement $pom_file 'profiles'
  removeElement $pom_file 'junit.version'
  removeElement $pom_file 'build'
  removeElement $pom_file 'url'
  removeArtifact $pom_file 'plugin' 'nexus-staging-maven-plugin' 'plugins'
  removeArtifact $pom_file 'dependency' 'checkstyle' 'plugins'
  removeArtifactVersion $pom_file 'dependency' 'junit'
  removeArtifactVersion $pom_file 'dependency' 'easymock'
  removeArtifactVersion $pom_file 'dependency' 'truth'
  removeArtifactVersion $pom_file 'dependency' 'grpc-google-common-protos'
  removeArtifactVersion $pom_file 'dependency' 'google-api-services-dns'
  removeArtifactVersion $pom_file 'dependency' 'google-api-services-translate'
  removeArtifactVersion $pom_file 'dependency' 'google-api-services-cloudresourcemanager'
  removeArtifactVersion $pom_file 'dependency' 'google-api-services-storage'
  removeArtifactVersion $pom_file 'dependency' 'google-cloud-storage'
  removeArtifactVersion $pom_file 'plugin' 'maven-checkstyle-plugin'
  removeArtifactVersion $pom_file 'dependency' 'mockito-all'
  removeArtifactVersion $pom_file 'dependency' 'objenesis'
  annotateArtifactVersion $pom_file 'dependency' 'grafeas'
  annotateArtifactVersion $pom_file 'dependency' 'proto-google-cloud-orgpolicy-v1'
  annotateArtifactVersion $pom_file 'dependency' 'proto-google-identity-accesscontextmanager-v1'
  annotateArtifactVersion $pom_file 'dependency' 'proto-google-cloud-os-config-v1'
  annotateArtifactVersion $pom_file 'dependency' 'google-cloud-resourcemanager'
}

function gapicPomProcessing {
  pom_file=$1
  removeArtifact "$pom_file" 'dependency' "grpc-google-common-protos" "dependencies"
  removeArtifact "$pom_file" 'dependency' "proto-google-iam-v1" "dependencies"
  removeArtifact "$pom_file" 'dependency' "grpc-google-iam-v1" "dependencies"
}

find $library_root \
  -name 'pom.xml' \
  -type f \
  -exec bash -c 'commonPomProcessing "$0"' {} \;

find $library_root \
  -mindepth 2 \
  -name 'pom.xml' \
  -not -wholename '*/proto-*' \
  -not -wholename '*/grpc*' \
  -not -wholename '*bom/*' \
  -exec bash -c 'gapicPomProcessing "$0"' {} \;
