#!/usr/bin/env bash

mvn install -f gax-example-pom.xml
mvn install -f nested-dependency-pom.xml
mvn install -f transitive-dependency-pom.xml