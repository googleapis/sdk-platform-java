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

from library_generation.model.gapic_config import GapicConfig
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.library_config import LibraryConfig
from library_generation.utils.generation_config_comparator import ChangeType
from library_generation.utils.generation_config_comparator import compare_config


class GenerationConfigComparatorTest(unittest.TestCase):
    def setUp(self) -> None:
        self.baseline_library = LibraryConfig(
            api_shortname="existing_library",
            api_description="",
            name_pretty="",
            product_documentation="",
            gapic_configs=[],
        )
        self.latest_library = LibraryConfig(
            api_shortname="existing_library",
            api_description="",
            name_pretty="",
            product_documentation="",
            gapic_configs=[],
        )
        self.baseline_config = GenerationConfig(
            gapic_generator_version="",
            googleapis_commitish="",
            owlbot_cli_image="",
            synthtool_commitish="",
            template_excludes=[],
            path_to_yaml="",
            grpc_version="",
            protobuf_version="",
            libraries=[self.baseline_library],
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
            libraries=[self.latest_library],
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

    def test_compare_config_library_addition(self):
        self.latest_config.libraries.append(
            LibraryConfig(
                api_shortname="new_library",
                api_description="",
                name_pretty="",
                product_documentation="",
                gapic_configs=[],
            )
        )
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.LIBRARIES_ADDITION: ["new_library"]}, result)

    def test_compare_config_library_removal(self):
        self.latest_config.libraries = []
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.LIBRARIES_REMOVAL: ["existing_library"]}, result)

    def test_compare_config_api_description_update(self):
        self.latest_config.libraries[0].api_description = "updated description"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.API_DESCRIPTION: ["existing_library"]}, result)

    def test_compare_config_name_pretty_update(self):
        self.latest_config.libraries[0].name_pretty = "new name"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.NAME_PRETTY: ["existing_library"]}, result)

    def test_compare_config_product_docs_update(self):
        self.latest_config.libraries[0].product_documentation = "new docs"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.PRODUCT_DOCS: ["existing_library"]}, result)

    def test_compare_config_library_type_update(self):
        self.latest_config.libraries[0].library_type = "GAPIC_COMBO"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.LIBRARY_TYPE: ["existing_library"]}, result)

    def test_compare_config_release_level_update(self):
        self.latest_config.libraries[0].release_level = "STABLE"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.RELEASE_LEVEL: ["existing_library"]}, result)

    def test_compare_config_api_id_update(self):
        self.latest_config.libraries[0].api_id = "new_id"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.API_ID: ["existing_library"]}, result)

    def test_compare_config_api_reference_update(self):
        self.latest_config.libraries[0].api_reference = "new api_reference"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.API_REFERENCE: ["existing_library"]}, result)

    def test_compare_config_code_owner_team_update(self):
        self.latest_config.libraries[0].codeowner_team = "new team"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.CODEOWNER_TEAM: ["existing_library"]}, result)

    def test_compare_config_excluded_deps_update(self):
        self.latest_config.libraries[0].excluded_dependencies = "group:artifact"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual(
            {ChangeType.EXCLUDED_DEPENDENCIES: ["existing_library"]}, result
        )

    def test_compare_config_excluded_poms_update(self):
        self.latest_config.libraries[0].excluded_poms = "pom.xml"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.EXCLUDED_POMS: ["existing_library"]}, result)

    def test_compare_config_client_docs_update(self):
        self.latest_config.libraries[0].client_documentation = "new client docs"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.CLIENT_DOCS: ["existing_library"]}, result)

    def test_compare_config_issue_tracker_update(self):
        self.latest_config.libraries[0].issue_tracker = "new issue tracker"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.ISSUE_TRACKER: ["existing_library"]}, result)

    def test_compare_config_rest_docs_update(self):
        self.latest_config.libraries[0].rest_documentation = "new rest docs"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.REST_DOCS: ["existing_library"]}, result)

    def test_compare_config_rpc_docs_update(self):
        self.latest_config.libraries[0].rpc_documentation = "new rpc docs"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.RPC_DOCS: ["existing_library"]}, result)

    def test_compare_config_requires_billing_update(self):
        self.latest_config.libraries[0].requires_billing = False
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.REQUIRES_BILLING: ["existing_library"]}, result)

    def test_compare_config_extra_versioned_mod_update(self):
        self.latest_config.libraries[0].extra_versioned_modules = "extra module"
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual(
            {ChangeType.EXTRA_VERSIONED_MODULES: ["existing_library"]}, result
        )

    def test_compare_config_version_addition(self):
        self.latest_config.libraries[0].gapic_configs = [
            GapicConfig(proto_path="google/new/library/v1")
        ]
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.GAPIC_ADDITION: ["existing_library"]}, result)

    def test_compare_config_version_removal(self):
        self.baseline_config.libraries[0].gapic_configs = [
            GapicConfig(proto_path="google/old/library/v1")
        ]
        result = compare_config(
            baseline_config=self.baseline_config,
            latest_config=self.latest_config,
        )
        self.assertEqual({ChangeType.GAPIC_REMOVAL: ["existing_library"]}, result)
