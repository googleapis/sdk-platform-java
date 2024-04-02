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
import unittest

from library_generation.generate_repo import get_target_libraries
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.library_config import LibraryConfig


class GenerateRepoTest(unittest.TestCase):
    def test_get_target_library_returns_selected_libraries(self):
        one_library = GenerateRepoTest.__get_an_empty_library_config()
        one_library.api_shortname = "one_library"
        another_library = GenerateRepoTest.__get_an_empty_library_config()
        another_library.api_shortname = "another_library"
        config = GenerateRepoTest.__get_an_empty_generation_config()
        config.libraries.extend([one_library, another_library])
        target_libraries = get_target_libraries(config, ["another_library"])
        self.assertEqual([another_library], target_libraries)

    def test_get_target_library_given_null_returns_all_libraries(self):
        one_library = GenerateRepoTest.__get_an_empty_library_config()
        one_library.api_shortname = "one_library"
        another_library = GenerateRepoTest.__get_an_empty_library_config()
        another_library.api_shortname = "another_library"
        config = GenerateRepoTest.__get_an_empty_generation_config()
        config.libraries.extend([one_library, another_library])
        target_libraries = get_target_libraries(config)
        self.assertEqual([one_library, another_library], target_libraries)

    @staticmethod
    def __get_an_empty_generation_config() -> GenerationConfig:
        return GenerationConfig(
            gapic_generator_version="",
            googleapis_commitish="",
            synthtool_commitish="",
            owlbot_cli_image="",
            template_excludes=[],
            path_to_yaml="",
            libraries=[],
        )

    @staticmethod
    def __get_an_empty_library_config() -> LibraryConfig:
        return LibraryConfig(
            api_shortname="",
            name_pretty="",
            api_description="",
            product_documentation="",
            gapic_configs=[],
        )
