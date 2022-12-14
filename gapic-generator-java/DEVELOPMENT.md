# Development Workflow

## Set Up

1.  Clone this repo.

2.  Copy the Git pre-commit hooks. This will automatically check the build, run
    tests, and perform linting before each commit. (Symlinks don't seem to work,
    but if you find a way, please add it here!)

    ```sh
    cp .githooks/pre-commit .git/hooks/pre-commit
    ```

## Code Formatting

-   Run linter checks without actually doing the formatting.

    ```sh
    bazel run //:google_java_format_verification
    ```
    or
    ```sh
    mvn fmt:check
    ```

-   Format files.

    ```sh
    bazel run //:google_java_format
    ```
    or
    ```sh
    mvn fmt:format
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
    or
    ```sh
    mvn test
    ```

-   Run a single unit test like `JavaCodeGeneratorTest.java`:

    ```sh
    bazel test //:unit_com_google_api_generator_engine_JavaCodeGeneratorTest
    ```
    or
    ```sh
    mvn test -Dtest=JavaCodeGeneratorTest

    mvn test "-Dtest=Basic*, !%regex[.*.Unstable.*], !%regex[.*.MyTest.class#one.*|two.*], %regex[#fast.*|slow.*]"
    ```
￼
-   Update all unit test golden files:

    ```sh
    mvn test -DupdateUnitGoldens
    ```

-   Update a single unit test golden file, for example `JavaCodeGeneratorTest.java`:

    ```sh
    bazel run //:update_com_google_api_generator_engine_JavaCodeGeneratorTest
    ```
    or
    ```sh
    mvn test -DupdateUnitGoldens -Dtest=JavaCodeGeneratorTest
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

## Running the Plugin

1. Clone [googleapis](https://github.com/googleapis/googleapis) and
    [gapic-showcase](https://github.com/googleapis/gapic-showcase/).

2. Copy the protos from Showcase into googleapis/google/showcase.

    ```sh
    mkdir googleapis/google/showcase
    cp -r gapic-showcase/schema/google/showcase/v1beta1 googleapis/google/showcase/v1beta1
    ```

3. Add the new microgenerator rules to
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

4. Point to local gapic-generator-java
   
   Normally, googleapis's build pulls in googleapis/gapic-generator-java from the
   Internet:

   ```
   # Java microgenerator.
   …
   _gapic_generator_java_version = "2.1.0"
   
   http_archive(
       name = "gapic_generator_java",
       …
       urls = ["https://github.com/googleapis/gapic-generator-java/archive/v%s.zip" % _gapic_generator_java_version],
   )
   ```

   By replacing this portion using the built-in local_repository rule, you can mak
   it refer to your local development repo:

   ```
   local_repository(
     name = "gapic_generator_java",
     path = "/home/<your id>/gapic-generator-java",
   )
   ```

5. Build the new target.

   ```sh
   cd googleapis
   bazel build //google/showcase/v1beta1:showcase_java_gapic
   ```
    
   You can generate any client library based on the protos within googleapis.
   You just need the name of the service within the `java_gapic_assembly_gradle_pkg`
   rules within the service's `BUILD.bazel` file.
   For instance, to run your local generator on the `speech`'s v2 service, you can
   run:
    
   ```
   bazel build //google/cloud/speech/v2:google-cloud-speech-v2-java
   ```



