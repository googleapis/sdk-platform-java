# Copyright 2020 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

load("@com_google_api_codegen//rules_gapic:gapic.bzl", "proto_custom_library", "unzipped_srcjar")

def _java_gapic_postprocess_srcjar_impl(ctx):
    gapic_srcjar = ctx.file.gapic_srcjar
    output_srcjar_name = ctx.label.name
    output_main = ctx.outputs.main
    output_test = ctx.outputs.test
    output_resource_name = ctx.outputs.resource_name
    formatter = ctx.executable.formatter

    output_dir_name = ctx.label.name
    output_dir_path = "%s/%s" % (output_main.dirname, output_dir_name)

    script = """
    unzip -q {gapic_srcjar}
    # Sync'd to the output file name in Writer.java.
    unzip -q temp-codegen.srcjar -d {output_dir_path}
    # This may fail if there are spaces and/or too many files (exceed max length of command length).
    {formatter} --replace $(find {output_dir_path} -type f -printf "%p ")
    zip -r -j {output_srcjar_name}.srcjar {output_dir_path}/src/main/*
    zip -r -j {output_srcjar_name}-resource-name.srcjar {output_dir_path}/proto/src/main/*
    zip -r -j {output_srcjar_name}-tests.srcjar {output_dir_path}/src/test/*
    mv {output_srcjar_name}.srcjar {output_main}
    mv {output_srcjar_name}-resource-name.srcjar {output_resource_name}
    mv {output_srcjar_name}-tests.srcjar {output_test}
    """.format(
        gapic_srcjar = gapic_srcjar.path,
        output_srcjar_name = output_srcjar_name,
        formatter = formatter,
        output_dir_name = output_dir_name,
        output_dir_path = output_dir_path,
        output_main = output_main.path,
        output_resource_name = output_resource_name.path,
        output_test = output_test.path,
    )

    ctx.actions.run_shell(
        inputs = [gapic_srcjar],
        tools = [formatter],
        command = script,
        outputs = [output_main, output_resource_name, output_test],
    )

_java_gapic_postprocess_srcjar = rule(
    attrs = {
        "gapic_srcjar": attr.label(mandatory = True, allow_single_file = True),
        "formatter": attr.label(
            default = Label("//:google_java_format_binary"),
            executable = True,
            cfg = "host",
        ),
    },
    outputs = {
        "main": "%{name}.srcjar",
        "resource_name": "%{name}-resource-name.srcjar",
        "test": "%{name}-test.srcjar",
    },
    implementation = _java_gapic_postprocess_srcjar_impl,
)

def java_gapic_library(
        name,
        srcs,
        package = None,
        service_yaml = None,
        grpc_service_config = None,
        gapic_yaml = None,
        deps = [],
        test_deps = [],
        **kwargs):
    file_args_dict = {}

    if grpc_service_config:
        file_args_dict[grpc_service_config] = "grpc-service-config"

    if gapic_yaml:
        file_args_dict[gapic_yaml] = "gapic-config"

    # Currently a no-op.
    if service_yaml:
        file_args_dict[service_yaml] = "gapic-service-config"

    srcjar_name = name + "_srcjar"
    raw_srcjar_name = srcjar_name + "_raw"
    output_suffix = ".srcjar"

    _java_generator_name = "java_gapic"
    proto_custom_library(
        name = raw_srcjar_name,
        deps = srcs,
        plugin = Label("@gapic_generator_java//:protoc-gen-%s" % _java_generator_name),
        plugin_file_args = {},
        opt_file_args = file_args_dict,
        output_type = _java_generator_name,
        output_suffix = output_suffix,
        **kwargs
    )

    _java_gapic_postprocess_srcjar(
        name = srcjar_name,
        gapic_srcjar = "%s.srcjar" % raw_srcjar_name,
        **kwargs
    )

    resource_name_name = "%s_resource_name" % name
    resource_name_deps = [resource_name_name]
    native.java_library(
        name = resource_name_name,
        srcs = ["%s-resource-name.srcjar" % srcjar_name],
        deps = [
            "@com_google_api_api_common//jar",
            "@com_google_guava_guava//jar",
            "@javax_annotation_javax_annotation_api//jar",
        ],
        **kwargs
    )

    # General additional deps.
    actual_deps = deps + resource_name_deps + [
        "@com_google_googleapis//google/rpc:rpc_java_proto",
        "@com_google_googleapis//google/longrunning:longrunning_java_proto",
        "@com_google_protobuf//:protobuf_java",
        "@com_google_api_api_common//jar",
        "@com_google_api_gax_java//gax:gax",
        "@com_google_api_gax_java//gax-grpc:gax_grpc",
        "@com_google_guava_guava//jar",
        "@io_grpc_grpc_java//core:core",
        "@io_grpc_grpc_java//protobuf:protobuf",
        "@com_google_code_findbugs_jsr305//jar",
        "@org_threeten_threetenbp//jar",
        "@io_opencensus_opencensus_api//jar",
        "@com_google_auth_google_auth_library_credentials//jar",
        "@com_google_auth_google_auth_library_oauth2_http//jar",
        "@com_google_http_client_google_http_client//jar",
        "@javax_annotation_javax_annotation_api//jar",
    ]

    native.java_library(
        name = name,
        srcs = ["%s.srcjar" % srcjar_name],
        deps = actual_deps,
        **kwargs
    )

    # Test deps.
    actual_test_deps = test_deps + actual_deps + [
        "@com_google_api_gax_java//gax-grpc:gax_grpc_testlib",
        "@com_google_api_gax_java//gax:gax_testlib",
        "@com_google_code_gson_gson//jar",
        "@io_grpc_grpc_java//auth:auth",
        "@io_grpc_grpc_netty_shaded//jar",
        "@io_grpc_grpc_java//stub:stub",
        "@io_opencensus_opencensus_contrib_grpc_metrics//jar",
        "@junit_junit//jar",
    ]

    native.java_library(
        name = "%s_test" % name,
        srcs = ["%s-test.srcjar" % srcjar_name],
        deps = [":%s" % name] + actual_test_deps,
        **kwargs
    )

def java_gapic_test(name, runtime_deps, test_classes, **kwargs):
    for test_class in test_classes:
        native.java_test(
            name = test_class,
            test_class = test_class,
            runtime_deps = runtime_deps,
            **kwargs
        )
    native.test_suite(
        name = name,
        tests = test_classes,
        **kwargs
    )
