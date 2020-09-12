workspace(name = "com_google_api_generator")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# DO NOT REMOVE.
# This is needed to clobber any transitively-pulled in versions of bazel_skylib so that packages
# like protobuf will build.
http_archive(
    name = "bazel_skylib",
    sha256 = "97e70364e9249702246c0e9444bccdc4b847bed1eb03c5a3ece4f83dfe6abc44",
    urls = [
        "https://mirror.bazel.build/github.com/bazelbuild/bazel-skylib/releases/download/1.0.2/bazel-skylib-1.0.2.tar.gz",
        "https://github.com/bazelbuild/bazel-skylib/releases/download/1.0.2/bazel-skylib-1.0.2.tar.gz",
    ],
)

load("//:repository_rules.bzl", "com_google_api_generator_properties")

com_google_api_generator_properties(
    name = "com_google_api_generator_properties",
    file = "//:dependencies.properties",
)

load("//:repositories.bzl", "com_google_api_generator_repositories")

com_google_api_generator_repositories()

# protobuf
load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

# Java dependencies.
# Import the monolith so we can transitively use its gapic rules for googleapis.
http_archive(
    name = "com_google_api_codegen",
    strip_prefix = "gapic-generator-2.4.6",
    urls = ["https://github.com/googleapis/gapic-generator/archive/v2.4.6.zip"],
)

load("@com_google_googleapis//:repository_rules.bzl", "switched_rules_by_language")

switched_rules_by_language(
    name = "com_google_googleapis_imports",
    gapic = True,
    grpc = True,
    java = True,
)

# TODO(miraleung): When the gax-java Bazel build PRs are submitted, do the following:
# - Rename the next rule.
# - Use these rules in build files:
#  -  "@com_google_api_gax_java//gax",
#  - "@com_google_api_gax_java//gax-grpc:gax_grpc",
_gax_java_version = "1.58.2"

http_archive(
    name = "com_google_api_gax_java_temp",
    strip_prefix = "gax-java-%s" % _gax_java_version,
    urls = ["https://github.com/googleapis/gax-java/archive/v%s.zip" % _gax_java_version],
)

load("@com_google_api_gax_java_temp//:repository_rules.bzl", "com_google_api_gax_java_properties")

com_google_api_gax_java_properties(
    name = "com_google_api_gax_java_properties",
    file = "@com_google_api_gax_java_temp//:dependencies.properties",
)

load("@com_google_api_gax_java_temp//:repositories.bzl", "com_google_api_gax_java_repositories")

com_google_api_gax_java_repositories()

load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories()
