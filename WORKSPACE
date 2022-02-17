workspace(name = "gapic_generator_java")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-4.2",
    sha256 = "cd1a77b7b02e8e008439ca76fd34f5b07aecb8c752961f9640dea15e9e5ba1ca",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/4.2.zip",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

# DO NOT REMOVE.
# This is needed to clobber any transitively-pulled in versions of bazel_skylib so that packages
# like protobuf will build.
http_archive(
    name = "bazel_skylib",
    sha256 = "af87959afe497dc8dfd4c6cb66e1279cb98ccc84284619ebfec27d9c09a903de",
    urls = [
        "https://mirror.bazel.build/github.com/bazelbuild/bazel-skylib/releases/download/1.2.0/bazel-skylib-1.2.0.tar.gz",
        "https://github.com/bazelbuild/bazel-skylib/releases/download/1.2.0/bazel-skylib-1.2.0.tar.gz",
    ],
)

maven_install(
    artifacts = ["com.google.googlejavaformat:google-java-format:jar:all-deps:1.7"],
    repositories = ["https://repo.maven.apache.org/maven2/"],
)

# gax-java and its transitive dependencies must be imported before
# gapic-generator-java dependencies to match the order in googleapis repository,
# which in its turn, prioritizes actual generated clients runtime dependencies
# over the generator dependencies.

_gax_java_version = "2.12.2"

http_archive(
    name = "com_google_api_gax_java",
    strip_prefix = "gax-java-%s" % _gax_java_version,
    urls = ["https://github.com/googleapis/gax-java/archive/v%s.zip" % _gax_java_version],
)

load("@com_google_api_gax_java//:repository_rules.bzl", "com_google_api_gax_java_properties")
com_google_api_gax_java_properties(
    name = "com_google_api_gax_java_properties",
    file = "@com_google_api_gax_java//:dependencies.properties",
)

load("@com_google_api_gax_java//:repositories.bzl", "com_google_api_gax_java_repositories")

com_google_api_gax_java_repositories()

load("//:repositories.bzl", "gapic_generator_java_repositories")

gapic_generator_java_repositories()

# protobuf
load("@com_google_protobuf//:protobuf_deps.bzl", "PROTOBUF_MAVEN_ARTIFACTS", "protobuf_deps")

maven_install(
    artifacts = PROTOBUF_MAVEN_ARTIFACTS,
    repositories = ["https://repo.maven.apache.org/maven2/"],
)

protobuf_deps()

# Bazel rules.
_rules_gapic_version = "0.5.5"

http_archive(
    name = "rules_gapic",
    strip_prefix = "rules_gapic-%s" % _rules_gapic_version,
    urls = ["https://github.com/googleapis/rules_gapic/archive/v%s.tar.gz" % _rules_gapic_version],
)

# Java dependencies.
load("@com_google_googleapis//:repository_rules.bzl", "switched_rules_by_language")

switched_rules_by_language(
    name = "com_google_googleapis_imports",
    gapic = True,
    grpc = True,
    java = True,
)

load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories()

http_archive(
    name = "com_google_disco_to_proto3_converter",
    strip_prefix = "disco-to-proto3-converter-ce8d8732120cdfb5bf4847c3238b5be8acde87e3",
    urls = ["https://github.com/googleapis/disco-to-proto3-converter/archive/ce8d8732120cdfb5bf4847c3238b5be8acde87e3.zip"],
)
