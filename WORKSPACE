workspace(name = "com_google_api_codegen")

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

load("//:repository_rules.bzl", "com_google_api_codegen_properties")

com_google_api_codegen_properties(
    name = "com_google_api_codegen_properties",
    file = "//:dependencies.properties",
)

load("//:repositories.bzl", "com_google_api_codegen_repositories")

com_google_api_codegen_repositories()

# protobuf
load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

# Resource names plugin.
load(
    "@com_google_protoc_java_resource_names_plugin//:repositories.bzl",
    "com_google_protoc_java_resource_names_plugin_repositories",
)
load("@com_google_googleapis//:repository_rules.bzl", "switched_rules_by_language")

switched_rules_by_language(
    name = "com_google_googleapis_imports",
)

com_google_protoc_java_resource_names_plugin_repositories(omit_com_google_protobuf = True)
