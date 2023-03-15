# Development Setup

You need Java 11 or higher to run the build. The build produces Java 8-compatible
bytecode.

Install [`bazelisk`](https://github.com/bazelbuild/bazelisk) in your `PATH`
for gapic-generator-java's Bazel build.

## Set Up

1. Clone this repo.

2. (OPTIONAL) Copy the Git pre-commit hooks. This will automatically check the build, run
   tests, and perform linting before each commit. (Symlinks don't seem to work,
   but if you find a way, please add it here!)

    ```sh
    cp .githooks/pre-commit .git/hooks/pre-commit
    ```

## Run Tests for All Modules

- Run all unit tests in all modules.

    ```sh
    mvn install
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