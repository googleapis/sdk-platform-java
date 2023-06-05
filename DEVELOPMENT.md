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

## Testing guide
There are 4 layers of testing in this repo:
1. Traditional unit tests
2. Golden unit tests
3. Showcase integration tests
4. Golden integration tests(We should stop adding new ones, and rely on golden unit tests and showcase tests)

Based on where the code changes are, we should add different tests, in general

- If the changes are in `gax/api-common` only, you must add traditional unit tests, you may add showcase integration tests if you are modifying a public setting.
- If the changes are in `gapic-generator-java` only, showcase integration tests are most likely not needed
  - If it is in the `composer` module, you must add golden unit tests, you may add traditional unit tests for better coverage and easier testing.    
  - If it is in `other modules(ast, parser, writer etc.)`, you must add traditional unit tests, you may add golden unit tests to easily see the changes.
- If the changes are in both `gax` and `gapic-generator-java`, you must add all test layers, including traditional unit tests, golden unit tests and showcase integration tests. 

