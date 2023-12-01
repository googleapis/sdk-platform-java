#!/usr/bin/env bash

mvn install -f src/test/resources/gax-example-pom.xml
mvn install -f src/test/resources/nested-dependency-pom.xml
mvn install -f src/test/resources/transitive-dependency-pom.xml