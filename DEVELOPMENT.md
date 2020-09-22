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
3.  Add the new microgenerator rules to the protobuf directory's `BUILD.bazel` file
    as follows:
    
    ```python
    load(
        "@com_google_googleapis_imports//:imports.bzl",
        # Existing rules here.
        # The left-side name is simply an alias, we can use anything here.
        java_microgen_gapic_assembly_gradle_pkg = "java_microgenerator_gapic_assembly_gradle_pkg",
        java_microgen_gapic_library = "java_microgenerator_gapic_library",
        java_microgen_gapic_test = "java_microgenerator_gapic_test",
    )

    # This should either replace the existing monolith target or have a unique name
    # that includes "java_gapic".
    java_microgen_gapic_library(
        name = "showcase_java_gapic",
        srcs = [":showcase_proto_with_info"],
        # The gapic_yaml file is needed only for APIs that have batching configs.
        gapic_yaml = "showcase_gapic.yaml",
        grpc_service_config = "showcase_grpc_service_config.json",
        package = "google.showcase.v1beta1",
        service_yaml = "showcase.yaml",
        test_deps = [
            ":showcase_java_grpc",
        ],
        deps = [
            ":showcase_java_proto",
        ],
    )

    java_microgen_gapic_assembly_gradle_pkg(
        name = "google-cloud-showcase-v1beta1-java-microgen",
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
