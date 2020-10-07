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

3.  Add the new microgenerator rules to the protobuf directory's `BUILD.bazel`
    file as follows:

    ```python
    load(
        "@com_google_googleapis_imports//:imports.bzl",
        # Existing rules here.
        "java_gapic_assembly_gradle_pkg2",
        "java_gapic_library2",
    )

    # This should either replace the existing monolith target or have a unique name
    # that includes "java_gapic".
    java_gapic_library2(
        name = "showcase_java_gapic",
        srcs = [":showcase_proto_with_info"],
        # The gapic_yaml file is needed only for APIs that have batching configs.
        grpc_service_config = "showcase_grpc_service_config.json",
        package = "google.showcase.v1beta1",
        test_deps = [
            ":showcase_java_grpc",
        ],
        deps = [
            ":showcase_java_proto",
        ],
    )

    java_gapic_assembly_gradle_pkg2(
        # This name should be unique from the existing target name.
        name = "google-cloud-showcase-v1beta1-java",
        deps = [
            # This is the new microgen target above.
            ":showcase_java_gapic",
            # The following targets already exist.
            ":showcase_java_grpc",
            ":showcase_java_proto",
            ":showcase_proto",
        ],
    )
    ```

4.  Build the new target.

    ```sh
    bazel build google/showcase/v1beta1:showcase_java_gapic
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

## Test Running

-   Run all unit tests.

    ```sh
    bazel test //...
    ```

-   Run a single unit test like `JavaCodeGeneratorTest.java`

    ```sh
    bazel run //src/test/java/com/google/api/generator/engine:JavaCodeGeneratorTest
    ```

-   Update goldens files based on code generation in unit test, for example `JavaCodeGeneratorTest.java`

    ```sh
    bazel run //src/test/java/com/google/api/generator/engine:JavaCodeGeneratorTest_update
    ```
