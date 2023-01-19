workspace(name = "gapic_generator_java")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# gax-java and its transitive dependencies must be imported before
# gapic-generator-java dependencies to match the order in googleapis repository,
# which in its turn, prioritizes actual generated clients runtime dependencies
# over the generator dependencies.

_gax_java_version = "2.19.0"

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

load("@com_google_protobuf//:protobuf_deps.bzl", "PROTOBUF_MAVEN_ARTIFACTS", "protobuf_deps")
load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = PROTOBUF_MAVEN_ARTIFACTS,
    repositories = ["https://repo.maven.apache.org/maven2/"],
)

_gapic_generator_java_version = "2.14.0"  # {x-version-update:gapic-generator-java:current}

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

load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories()

_disco_to_proto3_converter_commit = "ce8d8732120cdfb5bf4847c3238b5be8acde87e3"

http_archive(
    name = "com_google_disco_to_proto3_converter",
    strip_prefix = "disco-to-proto3-converter-%s" % _disco_to_proto3_converter_commit,
    urls = ["https://github.com/googleapis/disco-to-proto3-converter/archive/%s.zip" % _disco_to_proto3_converter_commit],
)

# Showcase
_showcase_commit = "90d73532a0cab753a85f45c158394f24fc21d91a"

_showcase_sha256 = "0f582541a379be0746e6b8bc5af3df511581d4b1f18f7dfb9ce203be1a64cef1"

http_archive(
    name = "com_google_gapic_showcase",
    sha256 = _showcase_sha256,
    strip_prefix = "gapic-showcase-%s" % _showcase_commit,
    urls = [
        # "https://github.com/googleapis/gapic-showcase/archive/refs/tags/v%s.zip" % _showcase_version,
        "https://github.com/googleapis/gapic-showcase/archive/%s.zip" % _showcase_commit,
    ],
)
