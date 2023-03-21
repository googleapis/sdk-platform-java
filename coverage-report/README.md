## Coverage Report

This module gathers aggregated jacoco test coverage metrics across the `api-common`, `gax-java` and `showcase` modules. The purpose of
the metrics is to provide insights into how much of api-common and GAX code is being exercised by showcase, GAX and api-common tests and where 
(unit tests versus integration tests). They will also assist with tracking any changes in coverage as showcase tests continue to be added to the repository.

### Unit Test Coverage
In order to view aggregate unit test coverage of api-common and GAX in `api-common`, `gax-java` and `showcase`:

1. At the root of the repository, run `mvn clean test -DenableTestCoverage`.
2. The metrics can be found at `gapic-generator-java/coverage-report/target/site/jacoco-aggregate/index.html`

![Screenshot 2023-03-21 at 12 25 41 PM](https://user-images.githubusercontent.com/66699525/226675190-5225e778-99d4-44d0-8177-29d48d1c35ee.png)

### Integration Test Coverage

In order to view aggregate integration test coverage of api-common and GAX in `api-common`, `gax-java` and `showcase`:

1. At the root of the repository, run `mvn clean verify -DskipUnitTests -DenableTestCoverage -Penable-integration-tests`.
2. The metrics can be found at `gapic-generator-java/coverage-report/target/site/jacoco-aggregate/index.html`

![Screenshot 2023-03-21 at 12 26 26 PM](https://user-images.githubusercontent.com/66699525/226675461-97a1c4b5-a90f-470d-b0c8-51e63a35a548.png)
