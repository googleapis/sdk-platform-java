# Copyright 2019 Google LLC
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

    #file_args = ["%s=%s" % (k, v) for k, v in file_args_dict.items()]

    srcjar_name = name + "_srcjar"
    raw_srcjar_name = srcjar_name + "_raw"
    output_suffix = ".srcjar"

    _java_generator_name = "java_gapic"
    proto_custom_library(
        name = raw_srcjar_name,
        deps = srcs,
        plugin = Label("@com_google_api_generator//:protoc-gen-%s" % _java_generator_name),
        plugin_file_args = {},
        opt_file_args = file_args_dict,
        output_type = _java_generator_name,
        output_suffix = output_suffix,
        **kwargs
    )
