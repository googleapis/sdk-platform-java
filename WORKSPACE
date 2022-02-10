workspace(name = "gapic_generator_java")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_jvm_external",
    sha256 = "cd1a77b7b02e8e008439ca76fd34f5b07aecb8c752961f9640dea15e9e5ba1ca",
    strip_prefix = "rules_jvm_external-4.2",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/4.2.zip",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "com.google.api:api-common:2.1.4",
        "com.google.api:gax:2.12.0",
        "com.google.api:gax:jar:testlib:2.12.0",
        "com.google.api:gax-grpc:2.12.0",
        "com.google.api:gax-grpc:jar:testlib:2.12.0",
        "com.google.api:gax-httpjson:0.97.0",
        "com.google.api:gax-httpjson:jar:testlib:0.97.0",
        "com.google.api.grpc:proto-google-common-protos:2.7.3",
        "com.google.auth:google-auth-library-credentials:1.4.0",
        "com.google.auth:google-auth-library-oauth2-http:1.4.0",
        "com.google.auto.value:auto-value:1.7.2",
        "com.google.auto.value:auto-value-annotations:1.7.2",
        "com.google.code.findbugs:jsr305:3.0.2",
        "com.google.code.gson:gson:2.8.6",
        "com.google.googlejavaformat:google-java-format:jar:all-deps:1.7",
        "com.google.guava:guava:30.1-jre",
        "com.google.http-client:google-http-client:1.41.2",
        "com.google.protobuf:protobuf-java:3.19.1",
        "com.google.protobuf:protobuf-java-util:3.19.1",
        "com.google.truth:truth:1.1.2",
        "io.github.java-diff-utils:java-diff-utils:4.0",
        "io.grpc:grpc-auth:1.44.0",
        "io.grpc:grpc-netty-shaded:1.44.0",
        "io.grpc:grpc-protobuf:1.44.0",
        "io.grpc:grpc-stub:1.44.0",
        "io.opencensus:opencensus-api:0.31.0",
        "io.opencensus:opencensus-contrib-grpc-metrics:0.31.0",
        "javax.annotation:javax.annotation-api:1.3.2",
        "junit:junit:4.13.1",
        "org.apache.tomcat:annotations-api:6.0.53",
        "org.threeten:threetenbp:1.3.3",
        "org.yaml:snakeyaml:1.26",
    ],
    generate_compat_repositories = True,
    repositories = ["https://repo.maven.apache.org/maven2/"],
)
load("@maven//:compat.bzl", "compat_repositories")
compat_repositories()

http_archive(
    name = "com_google_googleapis",
    strip_prefix = "googleapis-0899ba0f4c053a4487ccf0b699df5f850e39a45f",
    urls = ["https://github.com/googleapis/googleapis/archive/0899ba0f4c053a4487ccf0b699df5f850e39a45f.zip"],
)

# to build :service_config_java_proto, we need the protobuf source in the grpc-proto repo
http_archive(
    name = "io_grpc_proto",
    strip_prefix = "grpc-proto-8e3fec8612bc0708e857950dccadfd5063703e04", # Nov 6, 2021
    urls = ["https://github.com/grpc/grpc-proto/archive/8e3fec8612bc0708e857950dccadfd5063703e04.zip"],
)

# To load java_grpc_library(). This is only used in integraion tests.
http_archive(
    name = "io_grpc_grpc_java",
    strip_prefix = "grpc-java-1.44.0",
    urls = ["https://github.com/grpc/grpc-java/archive/v1.44.0.zip"],
)

# proto_library() requires getting protoc (the protobuf compiler) via @com_google_protobuf//:protoc.
http_archive(
    name = "com_google_protobuf",
    strip_prefix = "protobuf-3.19.1",
    urls = ["https://github.com/protocolbuffers/protobuf/archive/v3.19.1.zip"],
)
load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")
protobuf_deps()

# to load proto_custom_library() and proto_library_with_info()
http_archive(
    name = "rules_gapic",
    strip_prefix = "rules_gapic-0.5.5",
    urls = ["https://github.com/googleapis/rules_gapic/archive/v0.5.5.tar.gz"],
)

load("@com_google_googleapis//:repository_rules.bzl", "switched_rules_by_language")
switched_rules_by_language(
    name = "com_google_googleapis_imports",
    gapic = True,
    grpc = True,
    java = True,
)

# required for generating Compute API, which is based on discovery
http_archive(
    name = "com_google_disco_to_proto3_converter",
    strip_prefix = "disco-to-proto3-converter-ce8d8732120cdfb5bf4847c3238b5be8acde87e3",
    urls = ["https://github.com/googleapis/disco-to-proto3-converter/archive/ce8d8732120cdfb5bf4847c3238b5be8acde87e3.zip"],
)
