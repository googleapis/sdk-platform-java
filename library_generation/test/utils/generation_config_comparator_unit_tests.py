# Copyright 2024 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
import unittest

from library_generation.model.generation_config import GenerationConfig
from library_generation.utils.generation_config_comparator import ChangeType
from library_generation.utils.generation_config_comparator import compare_config


class GenerationConfigComparatorTest(unittest.TestCase):
    def setUp(self) -> None:
        self.baseline_config = GenerationConfig(
            gapic_generator_version="",
            googleapis_commitish="",
            owlbot_cli_image="",
            synthtool_commitish="",
            template_excludes=[],
            path_to_yaml="",
            grpc_version="",
            protobuf_version="",
            libraries=[],
        )
        self.latest_config = GenerationConfig(
            gapic_generator_version="",
            googleapis_commitish="",
            owlbot_cli_image="",
            synthtool_commitish="",
            template_excludes=[],
            path_to_yaml="",
            grpc_version="",
            protobuf_version="",
            libraries=[],
        )

    def test_compare_config_not_change(self):
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertTrue(len(result) == 0)

    def test_compare_config_googleapis_update(self):
        self.baseline_config.googleapis_commitish = (
            "1a45bf7393b52407188c82e63101db7dc9c72026"
        )
        self.latest_config.googleapis_commitish = (
            "1e6517ef4f949191c9e471857cf5811c8abcab84"
        )
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.GOOGLEAPIS_COMMIT: []}, result)

    def test_compare_config_generator_update(self):
        self.baseline_config.gapic_generator_version = "1.2.3"
        self.latest_config.gapic_generator_version = "1.2.4"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.GENERATOR: []}, result)

    def test_compare_config_owlbot_cli_update(self):
        self.baseline_config.owlbot_cli_image = "image_version_123"
        self.latest_config.owlbot_cli_image = "image_version_456"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.OWLBOT_CLI: []}, result)

    def test_compare_config_synthtool_update(self):
        self.baseline_config.synthtool_commitish = "commit123"
        self.latest_config.synthtool_commitish = "commit456"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.SYNTHTOOL: []}, result)

    def test_compare_protobuf_update(self):
        self.baseline_config.protobuf_version = "3.25.2"
        self.latest_config.protobuf_version = "3.27.0"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.PROTOBUF: []}, result)

    def test_compare_config_grpc_update(self):
        self.baseline_config.grpc_version = "1.60.0"
        self.latest_config.grpc_version = "1.61.0"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.GRPC: []}, result)

    def test_compare_config_template_excludes_update(self):
        self.baseline_config.template_excludes = [".github/*", ".kokoro/*"]
        self.latest_config.template_excludes = [
            ".github/*",
            ".kokoro/*",
            "samples/*",
            "CODE_OF_CONDUCT.md",
        ]
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.TEMPLATE_EXCLUDES: []}, result)
