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

from library_generation.model.config_change import ChangeType
from library_generation.model.config_change import ConfigChange
from library_generation.model.config_change import LibraryChange
from library_generation.model.generation_config import GenerationConfig


class ConfigChangeTest(unittest.TestCase):
    def test_get_changed_libraries_with_repo_level_change_returns_none(self):
        config_change = ConfigChange(
            change_to_libraries={
                ChangeType.REPO_LEVEL_CHANGE: [],
                # add a library level change to verify this type of change has
                # no impact on the result.
                ChangeType.LIBRARY_LEVEL_CHANGE: [
                    LibraryChange(
                        changed_param="test",
                        latest_value="test",
                        library_name="test-library",
                    )
                ],
            },
            baseline_config=ConfigChangeTest.__get_a_gen_config(),
            latest_config=ConfigChangeTest.__get_a_gen_config(),
        )
        self.assertIsNone(config_change.get_changed_libraries())

    def test_get_changed_libraries_with_library_level_change_returns_list(self):
        config_change = ConfigChange(
            change_to_libraries={
                ChangeType.LIBRARY_LEVEL_CHANGE: [
                    LibraryChange(
                        changed_param="test",
                        latest_value="test",
                        library_name="a-library",
                    ),
                    LibraryChange(
                        changed_param="test",
                        latest_value="test",
                        library_name="another-library",
                    ),
                ],
                ChangeType.LIBRARIES_ADDITION: [
                    LibraryChange(
                        changed_param="test",
                        latest_value="test",
                        library_name="new-library",
                    ),
                ],
                ChangeType.GAPIC_ADDITION: [
                    LibraryChange(
                        changed_param="test",
                        latest_value="test",
                        library_name="library-with-new-version",
                    ),
                ],
            },
            baseline_config=ConfigChangeTest.__get_a_gen_config(),
            latest_config=ConfigChangeTest.__get_a_gen_config(),
        )
        self.assertEqual(
            ["a-library", "another-library", "new-library", "library-with-new-version"],
            config_change.get_changed_libraries(),
        )

    @staticmethod
    def __get_a_gen_config() -> GenerationConfig:
        return GenerationConfig(
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
