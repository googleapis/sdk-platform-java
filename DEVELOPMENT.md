# Development Workflow

## Set Up

1.  Clone this repo.

2.  Copy the Git pre-commit hooks. This will automatically check the build, run
    tests, and perform linting before each commit. (Symlinks don't seem to work,
    but if you find a way, please add it here!)

    ```sh
    cp .githooks/pre-commit .git/hooks/pre-commit
    ```

## Running the Plugin

1.  Clone [googleapis](https://github.com/googleapis/googleapis) and
    [gapic-showcase](https://github.com/googleapis/gapic-showcase/).

2.  Copy the protos from Showcase into googleapis/google/showcase.

    ```sh
    mkdir googleapis/google/showcase
    cp -r gapic-showcase/schema/google/showcase/v1beta1 googleapis/google/showcase/v1beta1
    ```

3.  Add the new microgenerator rules to
    `googleapis/google/showcase/v1beta1/BUILD.bazel` file as follows:

    ```python
    load(
        "@com_google_googleapis_imports//:imports.bzl",
        # Existing rules here.
        "java_gapic_assembly_gradle_pkg",
        "java_gapic_library",
        "java_proto_library",
        "proto_library_with_info",
    )

    proto_library_with_info(
        name = "showcase_proto_with_info",
        deps = [
            ":showcase_proto",
        ],
    )

    java_proto_library(
        name = "showcase_java_proto",
        deps = [
            ":showcase_proto",
        ],
    )

    # This should either replace the existing monolith target or have a unique name
    # that includes "java_gapic".
    java_gapic_library(
        name = "showcase_java_gapic",
        srcs = [":showcase_proto_with_info"],
        grpc_service_config = "showcase_grpc_service_config.json",
        test_deps = [
            ":showcase_java_grpc",
        ],
        deps = [
            ":showcase_java_proto",
        ],
    )

    java_gapic_assembly_gradle_pkg(
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
    bazel build //google/showcase/v1beta1:showcase_java_gapic
    ```

## Code Formatting

-   Run linter checks without actually doing the formatting.

    ```sh
    bazel run //:google_java_format_verification
    ```

-   Format files.

    ```sh
    bazel run //:google_java_format
    ```

## Test Running

-   Run all unit and integration tests.

    ```sh
    bazel test //...
    ```

-   Run all unit tests.

    ```sh
    bazel test //:units
    ```

-   Run a single unit test like `JavaCodeGeneratorTest.java`

    ```sh
    bazel test //:unit_com_google_api_generator_engine_JavaCodeGeneratorTest
    ```

-   Update unit test golden files, for example `JavaCodeGeneratorTest.java`:

    ```sh
    bazel run //:update_com_google_api_generator_engine_JavaCodeGeneratorTest
    ```

-   Run a single integration test for API like `Redis`, it generates Java source
    code using the Java microgenerator and compares them with the goldens files
    in `test/integration/goldens/redis`.

    ```sh
    bazel test //test/integration:redis
    ```

-   Update integration test golden files, for example `Redis`. This clobbers all the
    files in `test/integration/goldens/redis`.

    ```sh
    bazel run //test/integration:update_redis
    ```
