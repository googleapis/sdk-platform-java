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
import re


def remove_version_from(proto_path: str) -> str:
    """
    Remove the version of a proto_path
    :param proto_path: versioned proto_path
    :return: the proto_path without version
    """
    version_pattern = "^v[1-9]"
    index = proto_path.rfind("/")
    version = proto_path[index + 1 :]
    if re.match(version_pattern, version):
        return proto_path[:index]
    return proto_path


def find_versioned_proto_path(proto_path: str) -> str:
    """
    Returns a versioned proto_path from a given proto_path; or proto_path itself
    if it doesn't contain a versioned proto_path.
    :param proto_path: a proto file path
    :return: the versioned proto_path
    """
    version_regex = re.compile(r"^v[1-9].*")
    directories = proto_path.split("/")
    for directory in directories:
        result = version_regex.search(directory)
        if result:
            version = result[0]
            idx = proto_path.find(version)
            return proto_path[:idx] + version
    return proto_path
