# Generate GAPIC Client Library without post-processing

The script, `generate_library.sh`, allows you to generate a gapic client library from proto files.

## Environment

Use Linux environment and install java runtime environment.

## Parameters to run `generate_library.sh`

You need to run the script with the following parameters.

### proto_path
A directory in the current working directory (refers as `$cwd`) and copy proto files into it. 
The absolute path of `proto_path` is `$cwd/$proto_path`. 

Use `-p` or `--proto_path` to specify the value.

   Note that usually additional proto files are needed to generate a gapic library, 
   you should also copy `google/` folder from [googleapis](https://github.com/googleapis/googleapis) 
   into `$cwd`.

### destination_path 
A directory in `$cwd`. This is the path in which the generated library will reside. 
The absolute path of `destination_path` is `$cwd/$destination_path`. 

Use `-d` or `--destination_path` to specify the value.
   
   Note that you do not need to create `$detination_path` beforehand.

### version of gapic-generator-java
You can find the released version of gapic-generator-java in [maven central](https://repo1.maven.org/maven2/com/google/api/gapic-generator-java/).

Use `--gapic_generator_version` to specify the value.

Note that you can specify a `SNAPSHOT` version as long as you have build the SNAPSHOT of gapic-generator-java in your maven
local repository.

### version of protobuf (optional)
You can find the released version of protobuf in [GitHub](https://github.com/protocolbuffers/protobuf/releases/).
The default value is defined in `gapic-generator-java-pom-parent/pom.xml`.

Use `--protobuf_version` to specify the value.

Note that if specified, the version should be compatible with gapic-generator-java.

### version of grpc  (optional)
You can find the released version of grpc in [maven central](https://repo1.maven.org/maven2/io/grpc/protoc-gen-grpc-java/).
The default value is defined in `gapic-generator-java-pom-parent/pom.xml`.

Use `--grpc_version` to specify the value.

Note that if specified, the version should be compatible with gapic-generator-java.

### transport (optional)
One of gapic options passed to the generator. The value is either `grpc` or `grpc+rest`.
The default value is `grpc`.

Use `--transport` to specify the value.

### rest_numeric_enums (optional)
One of gapic options passed to the generator. The value is either `true` or `false`.
The default value is `true`.

Use `--rest_numeric_enums` to specify the value.

### include_samples (optional)
Whether generates code samples. The value is either `true` or `false`. 
The default value is `true`.

Use `--include_samples` to specify the value.

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