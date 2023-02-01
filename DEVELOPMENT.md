# Development Workflow

## Set Up

1. Clone this repo.

2. (OPTIONAL) Copy the Git pre-commit hooks. This will automatically check the build, run
    tests, and perform linting before each commit. (Symlinks don't seem to work,
    but if you find a way, please add it here!)

    ```sh
    cp .githooks/pre-commit .git/hooks/pre-commit
    ```

    ### Note: You may see this error with the pre-commits due to the monorepo migration:
    ```
    [ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.10.1:compile (default-compile) on project gapic-generator-java: Compilation failure: Compilation failure: 
    [ERROR] gapic-generator-java/gapic-generator-java/src/main/java/com/google/api/generator/gapic/composer/rest/ServiceClientTestClassComposer.java:[19,43] package com.google.api.gax.httpjson.testing does not exist
    [ERROR] gapic-generator-java/gapic-generator-java/src/main/java/com/google/api/generator/gapic/composer/rest/ServiceClientTestClassComposer.java:[24,38] package com.google.api.gax.rpc.testing does not exist
    [ERROR] gapic-generator-java/gapic-generator-java/src/main/java/com/google/api/generator/gapic/composer/grpc/ServiceClientTestClassComposer.java:[18,39] package com.google.api.gax.grpc.testing does not exist
    ```
    Remove the pre-commit hooks. Tracking the issue in https://github.com/googleapis/gapic-generator-java/issues/1253
    
3. Install [`bazelisk`](https://github.com/bazelbuild/bazelisk) in your `PATH`.

## Code Formatting

- Run linter checks without actually doing the formatting.

    ```sh
    mvn fmt:check
    ```

- Format files.

    ```sh
    mvn fmt:format
    ```

## Test Running

- Run all unit and integration tests.

    ```sh
    mvn install          # unit tests, maven test wouldn't work in root folder because gapic-generator-java is dependant on test jars of gax-java
    bazel test //...  # integration tests
    ```

- Run all unit tests.

    ```sh
    mvn install
    ```
- For running unit tests in `gapic-generator-java` submodule, first build all modules with `mvn install -DskipTests`, then `cd` into `gapic-generator-java` submodule for the following commands:
  - Run a single or multiple unit tests:

    ```sh
    mvn test -Dtest=JavaCodeGeneratorTest

    mvn test "-Dtest=Basic*, !%regex[.*.Unstable.*], !%regex[.*.MyTest.class#one.*|two.*], %regex[#fast.*|slow.*]"
    ```

  - Update all unit test golden files:

    ```sh
    mvn test -DupdateUnitGoldens
    ```

  - Update a single unit test golden file, for example `JavaCodeGeneratorTest.java`:

    ```sh
    mvn test -DupdateUnitGoldens -Dtest=JavaCodeGeneratorTest
    ```

- Run a single integration test for API like `Redis`, it generates Java source
  code using the Java microgenerator and compares them with the goldens files
  in `test/integration/goldens/redis`.

    ```sh
    bazel test //test/integration:redis
    ```

- Update integration test golden files, for example `Redis`. This clobbers all the
  files in `test/integration/goldens/redis`.

    ```sh
    bazel run //test/integration:update_redis
    ```

## Showcase Integration Testing

[GAPIC Showcase](https://github.com/googleapis/gapic-showcase) is an API that demonstrates Generated
API Client (GAPIC) features and common API patterns used by Google. It follows the [Cloud APIs
design guide](https://cloud.google.com/apis/design/). `gapic-generator-java` generates a client for
the Showcase API which can communicate with a local Showcase server to perform integration tests.

### Requirements

* Install [Go](https://go.dev) in your `PATH`.

### Installing the Server

Using the latest version of showcase is recommended, but backward compatibility between server
versions is not guaranteed. If changing the version of the server, it may also be necessary to
update to a compatible client version in `./WORKSPACE`.

```shell
$ GAPIC_SHOWCASE_VERSION=0.25.0
$ go install github.com/googleapis/gapic-showcase/cmd/gapic-showcase@v"$GAPIC_SHOWCASE_VERSION"
$ PATH=$PATH:`go env GOPATH`/bin
$ gapic-showcase --help
> Root command of gapic-showcase
> 
> Usage:
>   gapic-showcase [command]
> 
> Available Commands:
>   completion  Emits bash a completion for gapic-showcase
>   compliance  This service is used to test that GAPICs...
>   echo        This service is used showcase the four main types...
>   help        Help about any command
>   identity    A simple identity service.
>   messaging   A simple messaging service that implements chat...
>   run         Runs the showcase server
>   sequence    Sub-command for Service: Sequence
>   testing     A service to facilitate running discrete sets of...
> 
> Flags:
>   -h, --help      help for gapic-showcase
>   -j, --json      Print JSON output
>   -v, --verbose   Print verbose output
>       --version   version for gapic-showcase
```

### Running the Server

Run the showcase server to allow requests to be sent to it. This opens port `:7469` to send and
receive requests.

```shell
$ gapic-showcase run
> 2022/11/21 16:22:15 Showcase listening on port: :7469
> 2022/11/21 16:22:15 Starting endpoint 0: gRPC endpoint
> 2022/11/21 16:22:15 Starting endpoint 1: HTTP/REST endpoint
> 2022/11/21 16:22:15 Starting endpoint multiplexer
> 2022/11/21 16:22:15 Listening for gRPC-fallback connections
> 2022/11/21 16:22:15 Listening for gRPC connections
> 2022/11/21 16:22:15 Listening for REST connections
> 2022/11/21 16:22:15 Fallback server listening on port: :1337
```

### Running the Integration Tests

Open a new terminal window in the root project directory.

```shell
$ cd showcase
$ mvn verify -P enable-integration-tests -P enable-golden-tests
```

Note:

* `-P enable-golden-tests` is optional. These tests do not require a local server.

### Update the Golden Showcase Files

Open a new terminal window in the root project directory.

```shell
$ cd showcase
$ mvn compile -P update
```

## Running the Plugin under googleapis with local gapic-generator-java

For running the Plugin with showcase protos and local gapic-generator-java, see above section "Showcase Integration Testing".

To generate a production GAPIC API:

1. Clone [googleapis](https://github.com/googleapis/googleapis).

2. Modify `googleapis/WORKSPACE` to point to local gapic-generator-java

   Normally, googleapis's build pulls in gapic-generator-java from Maven Central. 
   For a local run, we first need to build a local SNAPSHOT jar of the generator. Then we point googleapis to 
   both the local SNAPSHOT jar and the local copy of the generator.

   Replace the following section in googleapis
   ```
    _gapic_generator_java_version = "2.13.0"

    maven_install(
        artifacts = [
            "com.google.api:gapic-generator-java:" + _gapic_generator_java_version,
        ],
        #Update this False for local development
        fail_on_missing_checksum = True,
        repositories = [
            "m2Local",
            "https://repo.maven.apache.org/maven2/",
        ]
    )
    
    http_archive(
        name = "gapic_generator_java",
        strip_prefix = "gapic-generator-java-%s" % _gapic_generator_java_version,
        urls = ["https://github.com/googleapis/gapic-generator-java/archive/v%s.zip" % _gapic_generator_java_version],
    )
   ```

   to

   ```
    _gapic_generator_java_version = "2.13.1-SNAPSHOT"

    maven_install(
        artifacts = [
           "com.google.api:gapic-generator-java:" + _gapic_generator_java_version,
        ],
        #Update this False for local development
        fail_on_missing_checksum = False,
        repositories = [
            "m2Local",
            "https://repo.maven.apache.org/maven2/",
        ]
    )
    
   local_repository(
       name = "gapic_generator_java",
       path = "/absolute/path/to/your/local/gapic-generator-java",
   )
   ```
   
   Note: At the time of writing, the gapic-generator version was `2.13.0`. Update the version to the latest version in the pom.xml

3. Build the new target.

   You can generate any client library based on the protos within googleapis.
   You just need the name of the service within the `java_gapic_assembly_gradle_pkg`
   rules within the service's `BUILD.bazel` file.
   For instance, to run your local generator on the `speech`'s v2 service, you can
   run:

   ```
   bazel build //google/cloud/speech/v2:google-cloud-speech-v2-java
   ```

   Note: If you are running into bazel build issues, you can try to remove gapic-generator-java cached in your local m2
   Try running this command:
   ```
    rm -rf ~/.m2/repository/com/google/api/
   ```
   and then rebuild gapic-generator-java (`mvn clean install`).

## FAQ

### Error in workspace: workspace() got unexpected keyword argument 'managed_directories'

Full Error:

```
ERROR: Traceback (most recent call last):
        File "/home/alicejli/googleapis/WORKSPACE", line 1, column 10, in <toplevel>
                workspace(
Error in workspace: workspace() got unexpected keyword argument 'managed_directories'
ERROR: Error computing the main repository mapping: Encountered error while reading extension file 'tools/build_defs/repo/http.bzl': no such package '@bazel_tools//tools/build_defs/repo': error loading package 'external': Could not load //external package
```

You may be using the latest version of bazel which this project does not support yet. Try installing bazelisk to force
bazel to use the version specified in `.bazeliskrc`
