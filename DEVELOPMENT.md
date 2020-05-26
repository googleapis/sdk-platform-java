# Development Workflow

This is a temporal workflow that will evolve with codebase. The steps described
below are temporary and better ones will be coming.

## Set Up

1.  Clone this repo.
2.  Copy the Git pre-commit hooks. This will automatically check the build, run
    tests, and perform linting before each commit.

    ```sh
    cp .githooks/pre-commit .git/hooks/pre-commit
    ```

## Running the Plugin

1.  Clone [googleapis](https://github.com/googleapis/googleapis) and install protoc.

2. create output folder

    ```sh
    mkdir .test-out-showcase
    ```

3.  Export tool paths.

    ```sh
    PROTOC_INCLUDE_DIR=test-fixtures/protos/api-common-protos
    YOUR_PROTO_DIR=test-fixtures/protos/google
    YOUR_PROTO_FILES=test-fixtures/protos/google/showcase/v1beta1/*.proto
    OUTPUT_DIR=.test-out-showcase
    ```

4.  Build this plugin.

    ```sh
    bazel build :protoc-gen-gapic-java
    ```

5.  Run the plugin. At this stage, it will not do anything except write
    hardcoded Java into two files.

    ```
    protoc -I=${PROTOC_INCLUDE_DIR} -I=${GOOGLEAPIS_DIR} -I=${YOUR_PROTO_DIR} \
        ${YOUR_PROTO_FILES} --plugin=bazel-bin/protoc-gen-gapic-java
        --gapic-java_out=${OUTPUT_DIR}
    ```

## Code Formatting

-   Run linter checks without actually doing the formatting.

    ```sh
    bazel build :google_java_format_verification
    ```

-   Format files.

    ```sh
    bazel run :google_java_format
    ```
