# Generate GAPIC Client Library without post-processing

The script, `generate_library.sh`, allows you to generate a GAPIC client library from proto files.

## Environment

Use Linux environment and install java runtime environment (8 or above).

## Prerequisite
Protos referenced by protos in `proto_path` (see `proto_path` below) should be copied to an `output`
directory located in the working directory, referred as `$cwd`
(for example, `library_generation/output` if planning to call from the same folder).
The directory structure should be the same as import statements in protos.

For example, you want to generate from `folder1/folder2/protoA`, so `proto_path` 
should be set to `folder1/folder2` (a relative path from `output`). 
protoA imports protoB as `folder3/folder4/protoB`, then there should 
be `folder3/folder4` (containing protoB) in `output`.

In order to generate a GAPIC library, you need to pull `google/` from [googleapis](https://github.com/googleapis/googleapis)
and put it into `output` since protos in `google/` are likely referenced by 
protos from which the library are generated.

In order to generate a post-processed GAPIC library, you need to pull the
original repository (e.g. google-cloud-java/java-asset) and pass it as
`repository_path`. This repository will be the source of truth for pre-existing
pom.xml files, owlbot.py and .OwlBot.yaml files. See the option belows for
custom postprocessed generations (e.g. custom `.repo-metadata.json` path).

## Parameters to run `generate_library.sh`

You need to run the script with the following parameters.

### proto_path
A directory in `$cwd/output` and copy proto files into it. 
The absolute path of `proto_path` is `$cwd/output/$proto_path`. 

Use `-p` or `--proto_path` to specify the value.

### destination_path 
A directory within `$cwd/output`. 
This is the path in which the generated library will reside. 
The absolute path of `destination_path` is `$cwd/output/$destination_path`. 

Use `-d` or `--destination_path` to specify the value.
   
Note that you do not need to create `$destination_path` beforehand.

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

Note that you can specify any non-published version (e.g. a SNAPSHOT) as long as you have installed it in your maven
local repository. The script will search locally first.

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

### proto_only (optional)
Whether this is a proto-only library (no `gapic-*` directory in the generated 
library).
The default value is `false`.

When set to `true`, the GAPIC generator will not be invoked.
Therefore, GAPIC options (`transport`, `rest_numeric_enums`) and 
`gapic_additional_protos` will be ignored.

Use `--proto_only` to specify the value.

### gapic_additional_protos (optional)
Additional protos that pass to the generator.
The default value is `google/cloud/common_resources.proto`.

Use `--gapic_additional_protos` to specify the value.

### transport (optional)
One of GAPIC options passed to the generator.
The value is either `grpc` or `grpc+rest`.
The default value is `grpc`.

Use `--transport` to specify the value.

### rest_numeric_enums (optional)
One of GAPIC options passed to the generator.
The value is either `true` or `false`.
The default value is `true`.

Use `--rest_numeric_enums` to specify the value.

### gapic_yaml (optional)
One of GAPIC options passed to the generator.
The default value is an empty string.

Use `--gapic_yaml` to specify the value.

### service_config (optional)
One of GAPIC options passed to the generator.
The default value is an empty string.

Use `--service_config` to specify the value.

### service_yaml (optional)
One of GAPIC options passed to the generator.
The default value is an empty string.

Use `--service_yaml` to specify the value.

### include_samples (optional)
Whether generates code samples. The value is either `true` or `false`. 
The default value is `true`.

Use `--include_samples` to specify the value.

### os_architecture (optional)
Choose the protoc binary type from https://github.com/protocolbuffers/protobuf/releases.
Default is "linux-x86_64".

### enable_postprocessing (optional)
Whether to enable the post-processing steps (usage of owlbot) in the generation
of this library
Default is "true".

### repository_path (optional)
Relative path from `output_folder` to the location of the original,
post-processed source code of the library being generated. It is necessary when
`enable_postprocessing` is `"true"`

### versions_file (optional)
It must point to a versions.txt file containing the versions the post-processed
poms will have. It is required when `enable_postprocessing` is `"true"`


## An example to generate a non post-processed client library
```bash
library_generation/generate_library.sh \
-p google/cloud/confidentialcomputing/v1 \
-d google-cloud-confidentialcomputing-v1-java \
--gapic_generator_version 2.24.0 \
--protobuf_version 23.2 \
--grpc_version 1.55.1 \
--gapic_additional_protos "google/cloud/common_resources.proto google/cloud/location/locations.proto" \
--transport grpc+rest \
--rest_numeric_enums true \
--enable_postprocessing false \
--include_samples true
```

## An example to generate a library with postprocessing
```bash
library_generation/generate_library.sh \
-p google/cloud/confidentialcomputing/v1 \
-d google-cloud-confidentialcomputing-v1-java \
--gapic_generator_version 2.24.0 \
--protobuf_version 23.2 \
--grpc_version 1.55.1 \
--gapic_additional_protos "google/cloud/common_resources.proto google/cloud/location/locations.proto" \
--transport grpc+rest \
--rest_numeric_enums true \
--enable_postprocessing true \
--repository_path "java-logging" \
--versions_file "path/to/versions.txt" \
--include_samples true
```
