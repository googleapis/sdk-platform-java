#!/usr/bin/env bash

mvn install -f shared-dependency-3.18.0-pom.xml
mvn install -f gax-example-pom.xml
mvn install -f nested-dependency-pom.xml
mvn install -f transitive-dependency-pom.xml
mvn install -f firestore-pom.xml
mvn install -f datastore-pom.xml
