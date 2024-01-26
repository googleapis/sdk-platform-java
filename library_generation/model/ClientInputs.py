#!/usr/bin/env python3
#  Copyright 2024 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from pathlib import Path
import re

proto_library_pattern = r"""
proto_library_with_info\(
(.*?)
\)
"""
gapic_pattern = r"""
java_gapic_library\(
(.*?)
\)
"""
assembly_pattern = r"""
java_gapic_assembly_gradle_pkg\(
(.*?)
\)
"""
resource_pattern = r"//google/cloud:common_resources_proto"
location_pattern = r"//google/cloud/location:location_proto"
iam_pattern = r"//google/iam/v1:iam_policy_proto"
transport_pattern = r"transport = \"(.*?)\""
rest_pattern = r"rest_numeric_enums = True"
gapic_yaml_pattern = r"gapic_yaml = \"(.*?)\""
service_config_pattern = r"grpc_service_config = \"(.*?)\""
service_yaml_pattern = r"service_yaml = \"(.*?)\""
include_samples_pattern = r"include_samples = True"


class ClientInput:
    """
    A data class containing inputs to invoke generate_library.sh to generate
    a GAPIC library.
    """
    def __init__(
        self,
        proto_only="true",
        additional_protos="google/cloud/common_resources.proto",
        transport="",
        rest_numeric_enum="",
        gapic_yaml="",
        service_config="",
        service_yaml="",
        include_samples="true",
    ):
        self.proto_only = proto_only
        self.additional_protos = additional_protos
        self.transport = transport
        self.rest_numeric_enum = rest_numeric_enum
        self.gapic_yaml = gapic_yaml
        self.service_config = service_config
        self.service_yaml = service_yaml
        self.include_samples = include_samples


def parse(
    build_path: Path,
    versioned_path: str,
    build_file_name: str = 'BUILD.bazel'
) -> ClientInput:
    """
    Utility function to parse inputs of generate_library.sh from BUILD.bazel.
    :param build_path: the file path of BUILD.bazel
    :param versioned_path: a versioned path in googleapis repository, e.g.,
    google/cloud/asset/v1.
    :return: an ClientInput object.
    """
    with open(f"{build_path}/{build_file_name}") as build:
        content = build.read()

    proto_library_target = re.compile(
        proto_library_pattern, re.DOTALL | re.VERBOSE
    ).findall(content)
    additional_protos = ''
    if len(proto_library_target) > 0:
      additional_protos = __parse_additional_protos(proto_library_target[0])
    gapic_target = re.compile(gapic_pattern, re.DOTALL | re.VERBOSE)\
                     .findall(content)
    assembly_target = re.compile(assembly_pattern, re.DOTALL | re.VERBOSE)\
                        .findall(content)
    include_samples = 'false'
    if len(assembly_target) > 0:
      include_samples = __parse_include_samples(assembly_target[0])
    if len(gapic_target) == 0:
        return ClientInput(
          include_samples=include_samples
        )

    transport = __parse_transport(gapic_target[0])
    rest_numeric_enum = __parse_rest_numeric_enums(gapic_target[0])
    gapic_yaml = __parse_gapic_yaml(gapic_target[0], versioned_path)
    service_config = __parse_service_config(gapic_target[0], versioned_path)
    service_yaml = __parse_service_yaml(gapic_target[0], versioned_path)

    return ClientInput(
      proto_only="false",
      additional_protos=additional_protos,
      transport=transport,
      rest_numeric_enum=rest_numeric_enum,
      gapic_yaml=gapic_yaml,
      service_config=service_config,
      service_yaml=service_yaml,
      include_samples=include_samples,
    )


def __parse_additional_protos(proto_library_target: str) -> str:
    res = [" "]
    if len(re.findall(resource_pattern, proto_library_target)) != 0:
        res.append("google/cloud/common_resources.proto")
    if len(re.findall(location_pattern, proto_library_target)) != 0:
        res.append("google/cloud/location/locations.proto")
    if len(re.findall(iam_pattern, proto_library_target)) != 0:
        res.append("google/iam/v1/iam_policy.proto")
    return " ".join(res)


def __parse_transport(gapic_target: str) -> str:
    transport = re.findall(transport_pattern, gapic_target)
    return transport[0] if len(transport) != 0 else "grpc"


def __parse_rest_numeric_enums(gapic_target: str) -> str:
    rest_numeric_enums = re.findall(rest_pattern, gapic_target)
    return "true" if len(rest_numeric_enums) != 0 else "false"


def __parse_gapic_yaml(gapic_target: str, versioned_path: str) -> str:
    gapic_yaml = re.findall(gapic_yaml_pattern, gapic_target)
    return f"{versioned_path}/{gapic_yaml[0]}" if len(gapic_yaml) != 0 else ""


def __parse_service_config(gapic_target: str, versioned_path: str) -> str:
    service_config = re.findall(service_config_pattern, gapic_target)
    return f"{versioned_path}/{service_config[0]}" if len(service_config) != 0 \
        else ""


def __parse_service_yaml(gapic_target: str, versioned_path: str) -> str:
    service_yaml = re.findall(service_yaml_pattern, gapic_target)
    return f"{versioned_path}/{service_yaml[0]}" if len(service_yaml) != 0 \
        else ""


def __parse_include_samples(assembly_target: str) -> str:
    include_samples = re.findall(include_samples_pattern, assembly_target)
    return "true" if len(include_samples) != 0 else "false"
