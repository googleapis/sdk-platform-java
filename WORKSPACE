workspace(name = "gapic_generator_java")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

local_repository(
   name = "com_google_api_gax_java",
   path = "gax-java",
)

_googleapis_commit = "7438480b2a1bc6371d748e974f7a3647f90c4e8d"

http_archive(
    name = "com_google_googleapis",
    strip_prefix = "googleapis-%s" % _googleapis_commit,
    urls = [
        "https://github.com/googleapis/googleapis/archive/%s.zip" % _googleapis_commit,
    ],
)

# protobuf
RULES_JVM_EXTERNAL_TAG = "4.5"

RULES_JVM_EXTERNAL_SHA = "b17d7388feb9bfa7f2fa09031b32707df529f26c91ab9e5d909eb1676badd9a6"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

# We use RenovateBot to ensure gax-java's Maven build and gapic-generator-java's
# Bazel build use the same gRPC and Protobuf dependencies.

_protobuf_version = "3.21.10" # Renovate:com.google.protobuf:protobuf-bom
http_archive(
    name = "com_google_protobuf",
    urls = ["https://github.com/protocolbuffers/protobuf/archive/v%s.zip" % _protobuf_version],
    strip_prefix = "protobuf-%s" % _protobuf_version,
)
load("@com_google_protobuf//:protobuf_deps.bzl", "PROTOBUF_MAVEN_ARTIFACTS", "protobuf_deps")
load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = PROTOBUF_MAVEN_ARTIFACTS,
    repositories = ["https://repo.maven.apache.org/maven2/"],
)

_gapic_generator_java_version = "2.16.1-SNAPSHOT"  # {x-version-update:gapic-generator-java:current}

maven_install(
    artifacts = [
        "com.google.api:gapic-generator-java:" + _gapic_generator_java_version,
    ],
    fail_on_missing_checksum = False,
    repositories = [
        "m2Local",
        "https://repo.maven.apache.org/maven2/",
    ],
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

_grpc_version = "1.54.0" # Renovate:io.grpc:grpc-bom
http_archive(
    name = "io_grpc_grpc_java",
    urls = ["https://github.com/grpc/grpc-java/archive/v%s.zip" % _grpc_version],
    strip_prefix = "grpc-java-%s" % _grpc_version,
)

load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories()

_disco_to_proto3_converter_commit = "ce8d8732120cdfb5bf4847c3238b5be8acde87e3"

http_archive(
    name = "com_google_disco_to_proto3_converter",
    strip_prefix = "disco-to-proto3-converter-%s" % _disco_to_proto3_converter_commit,
    urls = ["https://github.com/googleapis/disco-to-proto3-converter/archive/%s.zip" % _disco_to_proto3_converter_commit],
)

# Showcase
_showcase_commit = "656e5f46d125a69c82c0cb7edcfcd8b03ed77b89"

_showcase_sha256 = "26d4b71ac31cbca5e4ed4cdcb5bfeca185e405392d70d2020b4528b5b47c8022"

http_archive(
    name = "com_google_gapic_showcase",
    sha256 = _showcase_sha256,
    strip_prefix = "gapic-showcase-%s" % _showcase_commit,
    urls = [
        # "https://github.com/googleapis/gapic-showcase/archive/refs/tags/v%s.zip" % _showcase_version,
        "https://github.com/googleapis/gapic-showcase/archive/%s.zip" % _showcase_commit,
    ],
)

maven_install(
    artifacts = [
      "io.opencensus:opencensus-api:0.31.1",
      "com.google.api:api-common:2.6.3",
      "javax.annotation:javax.annotation-api:1.3.2",
      "com.google.api:api-common:2.6.3",
      "com.google.http-client:google-http-client:1.43.1",
      "com.google.code.gson:gson:2.10.1",
      "com.google.auth:google-auth-library-credentials:1.16.0",
      "com.google.auth:google-auth-library-oauth2-http:1.16.0",
      "com.google.guava:guava:31.1-jre",
      "org.threeten:threetenbp:1.6.7",
      "junit:junit:4.13.2",
      "com.google.code.findbugs:jsr305:3.0.2",
      "io.grpc:grpc-auth:1.54.0",
      "io.grpc:grpc-netty-shaded:1.54.0",
      "io.grpc:grpc-stub:1.54.0",
    ],
    repositories = ["https://repo.maven.apache.org/maven2/"],
)

http_archive(
    name = "rules_pkg",
    sha256 = "8a298e832762eda1830597d64fe7db58178aa84cd5926d76d5b744d6558941c2",
    urls = [
        "https://mirror.bazel.build/github.com/bazelbuild/rules_pkg/releases/download/0.7.0/rules_pkg-0.7.0.tar.gz",
        "https://github.com/bazelbuild/rules_pkg/releases/download/0.7.0/rules_pkg-0.7.0.tar.gz",
    ],
)

load("@rules_pkg//:deps.bzl", "rules_pkg_dependencies")

rules_pkg_dependencies()
