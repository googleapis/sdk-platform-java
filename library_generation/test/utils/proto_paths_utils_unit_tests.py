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
import os
import unittest
from pathlib import Path

from library_generation.model.generation_config import from_yaml
from library_generation.utils.proto_path_utils import (
    get_file_paths,
    find_versioned_proto_path,
    remove_version_from,
)

script_dir = os.path.dirname(os.path.realpath(__file__))
resources_dir = os.path.join(script_dir, "..", "resources")
test_config_dir = Path(os.path.join(resources_dir, "test-config")).resolve()


class ProtoPathsUtilsTest(unittest.TestCase):
    def test_get_file_paths_from_yaml_success(self):
        paths = get_file_paths(from_yaml(f"{test_config_dir}/generation_config.yaml"))
        self.assertEqual(
            {
                "google/cloud/asset/v1": "asset",
                "google/cloud/asset/v1p1beta1": "asset",
                "google/cloud/asset/v1p2beta1": "asset",
                "google/cloud/asset/v1p5beta1": "asset",
                "google/cloud/asset/v1p7beta1": "asset",
            },
            paths,
        )

    def test_find_versioned_proto_path_nested_version_success(self):
        file_path = "google/cloud/aiplatform/v1/schema/predict/params/image_classification.proto"
        expected = "google/cloud/aiplatform/v1"
        proto_path = find_versioned_proto_path(file_path)
        self.assertEqual(expected, proto_path)

    def test_find_versioned_proto_path_success(self):
        file_path = "google/cloud/asset/v1p2beta1/assets.proto"
        expected = "google/cloud/asset/v1p2beta1"
        proto_path = find_versioned_proto_path(file_path)
        self.assertEqual(expected, proto_path)

    def test_find_versioned_proto_without_version_return_itself(self):
        file_path = "google/type/color.proto"
        expected = "google/type/color.proto"
        proto_path = find_versioned_proto_path(file_path)
        self.assertEqual(expected, proto_path)

    def test_remove_version_from_returns_non_versioned_path(self):
        proto_path = "google/cloud/aiplatform/v1"
        self.assertEqual("google/cloud/aiplatform", remove_version_from(proto_path))

    def test_remove_version_from_returns_self(self):
        proto_path = "google/cloud/aiplatform"
        self.assertEqual("google/cloud/aiplatform", remove_version_from(proto_path))
