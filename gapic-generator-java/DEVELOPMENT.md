# Development Workflow

## Test Running

### Unit Tests

To run the unit tests in `gapic-generator-java` submodule, first build all
modules with `mvn -pl '!gapic-generator-java' install -DskipTests` at the root
directory,
then `cd` into `gapic-generator-java` submodule for the following commands:

- Run all unit tests:

  ```sh
  # In gapic-generator-java submodule
  mvn test
  ```

- Run a single or multiple unit tests:

  ```sh
  # In gapic-generator-java submodule
  mvn test -Dtest=JavaCodeGeneratorTest

  mvn test "-Dtest=Basic*, !%regex[.*.Unstable.*], !%regex[.*.MyTest.class#one.*|two.*], %regex[#fast.*|slow.*]"
  ```

- Update all unit test golden files:

  ```sh
  # In gapic-generator-java submodule
  mvn test -DupdateUnitGoldens
  ```

- Update a single unit test golden file, for example `JavaCodeGeneratorTest.java`:

  ```sh
  # In gapic-generator-java submodule
  mvn test -DupdateUnitGoldens -Dtest=JavaCodeGeneratorTest
  ```

Note that `mvn -pl '!gapic-generator-java' install -DskipTests`
at the root directory is needed for `mvn test` commands,
because the gapic-generator-java submodule depends on the "test jars" of
gax-java. The test jars are absent until Maven's "package" phase, which is later
than the "test" phase.

### Integration Tests

To run integration test for gapic-generator-java, run this Bazel command in the
root of the repository (where you have WORKSPACE file for Bazel.)

```sh
# In the repository root directory
bazel test //...  # integration tests
```


- Run a single integration test for API like `Redis`, it generates Java source
  code using the Java microgenerator and compares them with the goldens files
  in `test/integration/goldens/redis`.

    ```sh
    # In the repository root directory
    bazel test //test/integration:redis
    ```

- Update integration test golden files, for example `Redis`. This clobbers all the
  files in `test/integration/goldens/redis`.

    ```sh
    # In the repository root directory
    bazel run //test/integration:update_redis
    ```

## Running the Plugin under googleapis with local gapic-generator-java

For running the Plugin with showcase protos and local gapic-generator-java, see
[Showcase Integration Testing](../showcase/README.md).

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

