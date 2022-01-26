#!/bin/sh

set -o errexit

TEST_CLASS=$1
CLASSPATH=$( find . -name '*.jar' ! -name 'liblite.jar' | xargs echo | tr ' ' ':' )

TEST_OUTPUT_HOME=${BUILD_WORKSPACE_DIRECTORY}/src/test/java \
  java -cp ${CLASSPATH} com.google.api.generator.test.framework.SingleJUnitTestRunner ${TEST_CLASS}
