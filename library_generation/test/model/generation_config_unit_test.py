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
    def test_from_yaml_succeeds(self):
        config = from_yaml(f"{test_config_dir}/generation_config.yaml")
        self.assertEqual("2.34.0", config.gapic_generator_version)
        self.assertEqual(25.2, config.protoc_version)
        self.assertEqual(
            "1a45bf7393b52407188c82e63101db7dc9c72026", config.googleapis_commitish
        )
        self.assertEqual("26.37.0", config.libraris_bom_version)
        self.assertEqual(
            "sha256:623647ee79ac605858d09e60c1382a716c125fb776f69301b72de1cd35d49409",
            config.owlbot_cli_image,
        )
        self.assertEqual(
            "6612ab8f3afcd5e292aecd647f0fa68812c9f5b5", config.synthtool_commitish
        )
        self.assertEqual(
            [
                ".github/*",
                ".kokoro/*",
                "samples/*",
                "CODE_OF_CONDUCT.md",
                "CONTRIBUTING.md",
                "LICENSE",
                "SECURITY.md",
                "java.header",
                "license-checks.xml",
                "renovate.json",
                ".gitignore",
            ],
            config.template_excludes,
        )
        library = config.libraries[0]
        self.assertEqual("cloudasset", library.api_shortname)
        self.assertEqual("Cloud Asset Inventory", library.name_pretty)
        self.assertEqual(
            "https://cloud.google.com/resource-manager/docs/cloud-asset-inventory/overview",
            library.product_documentation,
        )
        self.assertEqual(
            "provides inventory services based on a time series database.",
            library.api_description,
        )
        self.assertEqual("asset", library.library_name)
        self.assertEqual("@googleapis/analytics-dpe", library.codeowner_team)
        self.assertEqual(
            "proto-google-iam-v1-bom,google-iam-policy,proto-google-iam-v1",
            library.excluded_poms,
        )
        self.assertEqual("google-iam-policy", library.excluded_dependencies)
        gapics = library.gapic_configs
        self.assertEqual(5, len(gapics))
        self.assertEqual("google/cloud/asset/v1", gapics[0].proto_path)
        self.assertEqual("google/cloud/asset/v1p1beta1", gapics[1].proto_path)
        self.assertEqual("google/cloud/asset/v1p2beta1", gapics[2].proto_path)
        self.assertEqual("google/cloud/asset/v1p5beta1", gapics[3].proto_path)
        self.assertEqual("google/cloud/asset/v1p7beta1", gapics[4].proto_path)

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
            libraries_bom_version="",
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
            libraries_bom_version="",
            owlbot_cli_image="",
            synthtool_commitish="",
            template_excludes=[],
            libraries=[library_1, library_2],
        )
        self.assertTrue(config.is_monorepo())
