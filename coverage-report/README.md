## Coverage Report

This module is meant to gather aggregate jacoco test coverage metrics across the `gax-java` and `showcase` modules.

### Unit Test Coverage
In order to view aggregate unit test coverage of GAX in both `gax-java` and `showcase`:

1. At the root of the monorepo, run `mvn clean test -Ptest-coverage`.
2. The metrics can be found at `${HOME}/gapic-generator-java/coverage-report/target/site/jacoco-aggregate/index.html`

### Integration Test Coverage

In order to view aggregate integration test coverage of GAX in both `gax-java` and `showcase`:

1. At the root of the monorepo, run `mvn clean verify -DskipUnitTests -Ptest-coverage -Penable-integration-tests`.
2. The metrics can be found at `${HOME}/gapic-generator-java/coverage-report/target/site/jacoco-aggregate/index.html`
