# Development Workflow

## Set Up

1. Clone this repo.

2. Copy the Git pre-commit hooks. This will automatically check the build, run
   tests, and perform linting before each commit. (Symlinks don't seem to work,
   but if you find a way, please add it here!)

   ```sh
   cp .githooks/pre-commit .git/hooks/pre-commit
   ```

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
    mvn test          # unit tests
    bazel test //...  # integration tests
    ```

- Run all unit tests.

    ```sh
    mvn test
    ```

-   Run a single or multiple unit tests:

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

Before running the integration tests, make sure to install bazelisk:

```shell
$ go install github.com/bazelbuild/bazelisk@latest
$ export PATH=$PATH:$(go env GOPATH)/bin
```

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

## Running the Plugin

See also above section "Showcase Integration Testing".

To generate a production GAPIC API:

1. Clone [googleapis](https://github.com/googleapis/googleapis).

2. Point to local gapic-generator-java

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

   By replacing this portion using the built-in local_repository rule, you can make
   it refer to your local development repo:

   ```
   local_repository(
     name = "gapic_generator_java",
     path = "/home/<your id>/gapic-generator-java",
   )
   ```

3. Build the new target.

   You can generate any client library based on the protos within googleapis.
   You just need the name of the service within the `java_gapic_assembly_gradle_pkg`
   rules within the service's `BUILD.bazel` file.
   For instance, to run your local generator on the `speech`'s v2 service, you can
   run:

   ```
   bazel build //google/cloud/speech/v2:google-cloud-speech-v2-java
   ```



