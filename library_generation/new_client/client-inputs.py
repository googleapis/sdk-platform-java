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

gapic_pattern = r"""
java_gapic_library\(
(.*?)
\)
"""
transport_pattern = r"transport = \"(.*?)\""
rest_pattern = r"rest_numeric_enums = \"(.*?)\""


class ClientInput:
    def __init__(
        self,
        transport,
        rest_numeric_enum,
        proto_only
    ):
        self.transport = transport
        self.rest_numeric_enum = rest_numeric_enum
        self.proto_only = proto_only


def parse(build_path: Path) -> ClientInput:
    with open(f"{build_path}/BUILD.bazel") as build:
        content = build.read()

    gapic_target = re.compile(gapic_pattern, re.DOTALL | re.VERBOSE)\
                     .findall(content)
    if len(gapic_target) == 0:
        return ClientInput(
          transport="",
          rest_numeric_enum="",
          proto_only="true"
        )

    transport = re.findall(transport_pattern, gapic_target[0])
    rest_numeric_enum = re.findall(rest_pattern, gapic_target[0])

    return ClientInput(
      transport=transport,
      rest_numeric_enum=rest_numeric_enum,
      proto_only="false"
    )




