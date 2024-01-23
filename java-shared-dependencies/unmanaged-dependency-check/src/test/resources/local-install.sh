#!/usr/bin/env bash

mvn install -f shared-dependency--pom.xml
mvn install -f gax-example-pom.xml
mvn install -f nested-dependency-pom.xml
mvn install -f transitive-dependency-pom.xml
mvn install -f google-internal-artifact-test-case-pom.xml
