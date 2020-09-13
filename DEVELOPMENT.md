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

1.  Clone [googleapis](https://github.com/googleapis/googleapis) and
    [gapic-showcase](https://github.com/googleapis/gapic-showcase/) and install
    protoc.
2.  Copy the protos from Showcase into googleapis/google/showcase.

    ```sh
    cp gapic-showcase/schema/google/showcase/v1beta1 googleapis/google/showcase/v1beta
    ```

3.  Export tool paths.

    ```sh
    PROTOC_INCLUDE_DIR=/usr/local/include/google/protobuf/
    GOOGLEAPIS_DIR=/usr/local/google/home/$USER/dev/googleapis
    YOUR_PROTO_DIR=/usr/local/google/home/$USER/dev/googleapis/google/showcase/v1beta
    ```

4.  Build this plugin.

    ```sh
    bazel build :protoc-gen-java_gapic
    ```

5.  Run the plugin. At this stage, it will not do anything except write
    hardcoded Java into two files.

    ```
    protoc -I=${PROTOC_INCLUDE_DIR} -I=${GOOGLEAPIS_DIR} -I=${YOUR_PROTO_DIR} \
        --plugin=bazel-bin/protoc-gen-java_gapic ~/dev/googleapis/google/showcase/v1test/*.proto \
        --gapic-java_out=/tmp/test
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
