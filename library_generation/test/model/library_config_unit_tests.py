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

from library_generation.model.library_config import LibraryConfig


class LibraryConfigTest(unittest.TestCase):
    def test_get_library_returns_library_name(self):
        library = LibraryConfig(
            api_shortname="secret",
            name_pretty="",
            product_documentation="",
            api_description="",
            gapic_configs=list(),
            library_name="secretmanager",
        )
        self.assertEqual("secretmanager", library.get_library_name())

    def test_get_library_returns_api_shortname(self):
        library = LibraryConfig(
            api_shortname="secret",
            name_pretty="",
            product_documentation="",
            api_description="",
            gapic_configs=list(),
        )
        self.assertEqual("secret", library.get_library_name())
