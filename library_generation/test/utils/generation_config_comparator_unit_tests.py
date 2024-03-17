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

baseline_config = GenerationConfig(
    gapic_generator_version="",
    googleapis_commitish="",
    owlbot_cli_image="",
    synthtool_commitish="",
    template_excludes=[],
    path_to_yaml="",
    grpc_version="",
    protobuf_version="",
    libraries=[]
)
latest_config = GenerationConfig(
    gapic_generator_version="",
    googleapis_commitish="",
    owlbot_cli_image="",
    synthtool_commitish="",
    template_excludes=[],
    path_to_yaml="",
    grpc_version="",
    protobuf_version="",
    libraries=[]
)


class GenerationConfigComparatorTest(unittest.TestCase):
    def test_compare_config_not_change(self):
        result = compare_config(
            baseline_config=baseline_config,
            latest_config=latest_config,
        )
        self.assertTrue(len(result) == 0)

    def test_compare_config_googleapis_update(self):
        baseline_config.googleapis_commitish = "1a45bf7393b52407188c82e63101db7dc9c72026"
        latest_config.googleapis_commitish = "1e6517ef4f949191c9e471857cf5811c8abcab84"
        result = compare_config(
            baseline_config=baseline_config,
            latest_config=latest_config,
        )
        self.assertTrue(ChangeType.GOOGLEAPIS_COMMIT in result)
        self.assertEqual([], result[ChangeType.GOOGLEAPIS_COMMIT])

    def test_compare_config_generator_update(self):
        baseline_config.gapic_generator_version = "1.2.3"
        latest_config.gapic_generator_version = "1.2.4"
        result = compare_config(
            baseline_config=baseline_config,
            latest_config=latest_config,
        )
        self.assertTrue(ChangeType.GENERATOR in result)
        self.assertEqual([], result[ChangeType.GENERATOR])