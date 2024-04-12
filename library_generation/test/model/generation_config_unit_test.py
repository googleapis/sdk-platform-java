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
import os
import unittest
from pathlib import Path

from library_generation.model.generation_config import from_yaml, GenerationConfig
from library_generation.model.library_config import LibraryConfig

script_dir = os.path.dirname(os.path.realpath(__file__))
resources_dir = os.path.join(script_dir, "..", "resources")
test_config_dir = Path(os.path.join(resources_dir, "test-config")).resolve()

library_1 = LibraryConfig(
    api_shortname="a_library",
    api_description="",
    name_pretty="",
    product_documentation="",
    gapic_configs=[],
)
library_2 = LibraryConfig(
    api_shortname="another_library",
    api_description="",
    name_pretty="",
    product_documentation="",
    gapic_configs=[],
)


class GenerationConfigTest(unittest.TestCase):
    def test_get_proto_path_to_library_name_success(self):
        paths = from_yaml(
            f"{test_config_dir}/generation_config.yaml"
        ).get_proto_path_to_library_name()
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

    def test_is_monorepo_with_one_library_returns_false(self):
        config = GenerationConfig(
            gapic_generator_version="",
            googleapis_commitish="",
            owlbot_cli_image="",
            synthtool_commitish="",
            template_excludes=[],
            libraries=[library_1],
        )
        self.assertFalse(config.is_monorepo())

    def test_is_monorepo_with_two_libraries_returns_true(self):
        config = GenerationConfig(
            gapic_generator_version="",
            googleapis_commitish="",
            owlbot_cli_image="",
            synthtool_commitish="",
            template_excludes=[],
            libraries=[library_1, library_2],
        )
        self.assertTrue(config.is_monorepo())
