# Goldens Files For Integration Test

This folder contains goldens files that are expected to be generated from Java Microgenerator
against differenct APIs. For example `redis` folder has all the Java source files in the generated 
Redis client library. They are all actual Java files, but used as goldens.

## Purpose

When running integration test for the Java Microgenerator using the below command, 
the goldens files in this folder will be used to compare with the actual generated Java source files.
If they are not identical, then the integration test will fail.

```sh
bazel test //test/integration:redis_java_gapic
```

## How To Update Goldens

If the actual generated Java source files are not identical with the goldens files, and we want to
update the goldens using source files. Run the command below to overwrite the goldens files in `redis` folder.

```sh
bazel run //test/integration:redis_goldens_update
```
