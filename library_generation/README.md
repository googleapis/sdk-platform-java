# Generate GAPIC Client Library without post-processing

The script, `generate_library.sh`, allows you to generate a GAPIC client library from proto files.

## Environment

Use Linux environment and install java runtime environment (8 or above).

## Prerequisite
Protos referenced by protos in `proto_path` (see `proto_path` below) should be copied to the current
working directory (refers as `$cwd`, a directory contains `generate_library.sh`).
The directory structure should be the same as import statements in protos.

For example, we want to generate from `folder1/folder2/protoA`, so `proto_path` 
should be set to `folder1/folder2` (a relative path from `$cwd`). 
protoA imports protoB as `folder3/folder4/protoB`, then there should 
be `folder3/folder4` (containing protoB) in `$cwd`.

In order to generate a GAPIC library, you need to pull `google/` from [googleapis](https://github.com/googleapis/googleapis)
and put it into `$cwd` since protos in `google/` are likely referenced by 
protos from which the library are generated.

## Parameters to run `generate_library.sh`

You need to run the script with the following parameters.

### proto_path
A directory in `$cwd` and copy proto files into it. 
The absolute path of `proto_path` is `$cwd/$proto_path`. 

Use `-p` or `--proto_path` to specify the value.

### destination_path 
A directory within `$cwd`. 
This is the path in which the generated library will reside. 
The absolute path of `destination_path` is `$cwd/$destination_path`. 

Use `-d` or `--destination_path` to specify the value.
   
Note that you do not need to create `$detination_path` beforehand.

The directory structure of the generated library is
```
$destination_path
  |_gapic-*
  |    |_src
  |       |_main
  |          |_java
  |          |_resources
  |       |_test
  |_grpc-*
  |    |_src
  |       |_main
  |          |_java
  |    
  |_proto-*
  |    |_src
  |       |_main
  |          |_java
  |          |_proto
  |_samples
      |_snippets
          |_generated
```
You can't build the library as-is since it does not have `pom.xml` or `build.gradle`.
To use the library, copy the generated files to the corresponding directory
of a library repository, e.g., `google-cloud-java`.

### gapic_generator_version
You can find the released version of gapic-generator-java in [maven central](https://repo1.maven.org/maven2/com/google/api/gapic-generator-java/).

Use `--gapic_generator_version` to specify the value.

Note that you can specify a `SNAPSHOT` version as long as you have build the SNAPSHOT of gapic-generator-java in your maven
local repository.

### protobuf_version (optional)
You can find the released version of protobuf in [GitHub](https://github.com/protocolbuffers/protobuf/releases/).
The default value is defined in `gapic-generator-java-pom-parent/pom.xml`.

Use `--protobuf_version` to specify the value.

Note that if specified, the version should be compatible with gapic-generator-java.

### grpc_version (optional)
You can find the released version of grpc in [maven central](https://repo1.maven.org/maven2/io/grpc/protoc-gen-grpc-java/).
The default value is defined in `gapic-generator-java-pom-parent/pom.xml`.

Use `--grpc_version` to specify the value.

Note that if specified, the version should be compatible with gapic-generator-java.

### transport (optional)
One of GAPIC options passed to the generator. The value is either `grpc` or `grpc+rest`.
The default value is `grpc`.

Use `--transport` to specify the value.

### rest_numeric_enums (optional)
One of GAPIC options passed to the generator. The value is either `true` or `false`.
The default value is `true`.

Use `--rest_numeric_enums` to specify the value.

### include_samples (optional)
Whether generates code samples. The value is either `true` or `false`. 
The default value is `true`.

Use `--include_samples` to specify the value.

### os_architecture (optional)
Choose the protoc binary type from https://github.com/protocolbuffers/protobuf/releases.
Default is "linux-x86_64".

## An example to generate a client library
```
library_generation/generate_library.sh \
-p google/cloud/confidentialcomputing/v1 \
-d google-cloud-confidentialcomputing-v1-java \
--gapic_generator_version 2.24.0 \
--protobuf_version 23.2 \
--grpc_version 1.55.1 \
--transport grpc+rest \
--rest_numeric_enums true \
--include_samples true
```

## An example to generate showcase client
```
library_generation/generate_library.sh \
-p schema/google/showcase/v1beta1 \ # google/ should be in library_generation/.
-d output \
--gapic_generator_version 2.24.0 \
--protobuf_version 23.2 \
--grpc_version 1.55.1 \
--transport grpc+rest \
--rest_numeric_enums false \
--include_samples false
```
