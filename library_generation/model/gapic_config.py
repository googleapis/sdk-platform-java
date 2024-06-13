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
from library_generation.utils.proto_path_utils import find_version_in_proto_path

ALPHA_VERSION = "alpha"
BETA_VERSION = "beta"


class GapicConfig:
    """
    Class that represents a GAPICs single entry, inside a `LibraryConfig` in
    a generation_config.yaml
    """

    def __init__(self, proto_path: str):
        self.proto_path = proto_path

    def __eq__(self, other) -> bool:
        if not isinstance(other, GapicConfig):
            return False
        return self.proto_path == other.proto_path

    def __lt__(self, other) -> bool:
        self_version = find_version_in_proto_path(self.proto_path)
        other_version = find_version_in_proto_path(other.proto_path)
        # if only one config has a version in the proto_path, it is smaller
        # than the other one.
        if self_version and (not other_version):
            return True
        if (not self_version) and other_version:
            return False

        self_dirs = self.proto_path.split("/")
        other_dirs = other.proto_path.split("/")
        # if both of the configs have no version in the proto_path, the one
        # with lower depth is smaller.
        if (not self_version) and (not other_version):
            return len(self_dirs) < len(other_dirs)

        self_stable = GapicConfig.__is_stable(self_version)
        other_stable = GapicConfig.__is_stable(other_version)
        # if only config has a stable version in proto_path, it is smaller than
        # the other one.
        if self_stable and (not other_stable):
            return True
        if (not self_stable) and other_stable:
            return False
        # if two configs have different depth in proto_path, the one with
        # lower depth is smaller.
        if len(self_dirs) != len(other_dirs):
            return len(self_dirs) < len(other_dirs)
        # otherwise, the one with higher version is smaller.
        return self_version > other_version

    @staticmethod
    def __is_stable(version: str) -> bool:
        return not (ALPHA_VERSION in version or BETA_VERSION in version)
