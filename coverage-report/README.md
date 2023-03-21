## Coverage Report

This module gathers aggregated jacoco test coverage metrics across the `api--common`, `gax-java` and `showcase` modules. The purpose of
the metrics is to provide insights into how much of api-common and GAX code is being exercised by showcase, GAX and api-common tests and where 
(unit tests versus integration tests). They will also track any change in coverage as showcase tests continue to be added to the repository.

### Unit Test Coverage
In order to view aggregate unit test coverage of api-common and GAX in `api-common`, `gax-java` and `showcase`:

1. At the root of the repository, run `mvn clean test -DenableTestCoverage`.
2. The metrics can be found at `gapic-generator-java/coverage-report/target/site/jacoco-aggregate/index.html`

![Screenshot 2023-03-03 at 6 32 50 PM](https://user-images.githubusercontent.com/66699525/222854612-787b4dde-f9a3-469a-8227-8f46dc0a4a20.png)

### Integration Test Coverage

In order to view aggregate integration test coverage of api-common and GAX in `api-common`, `gax-java` and `showcase`:

1. At the root of the repository, run `mvn clean verify -DskipUnitTests -DenableTestCoverage -Penable-integration-tests`.
2. The metrics can be found at `gapic-generator-java/coverage-report/target/site/jacoco-aggregate/index.html`

![Screenshot 2023-03-03 at 6 36 26 PM](https://user-images.githubusercontent.com/66699525/222854973-f8a96f01-abc1-4e6b-9ab8-99b5e50dec6a.png)