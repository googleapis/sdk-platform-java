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

from library_generation.model.library_config import get_library_name, LibraryConfig


class LibraryConfigTest(unittest.TestCase):
    def test_get_library_returns_library_name(self):
        library = LibraryConfig(
            api_shortname="baremetalsolution",
            name_pretty="Bare Metal Solution",
            product_documentation="https://cloud.google.com/bare-metal/docs",
            api_description="Bring your Oracle workloads to Google Cloud with Bare Metal Solution and jumpstart your cloud journey with minimal risk.",
            gapic_configs=list(),
            library_name="bare-metal-solution",
            rest_documentation="https://cloud.google.com/bare-metal/docs/reference/rest",
            rpc_documentation="https://cloud.google.com/bare-metal/docs/reference/rpc",
        )
        self.assertEqual("bare-metal-solution", get_library_name(library))

    def test_get_library_returns_api_shortname(self):
        library = LibraryConfig(
            api_shortname="secretmanager",
            name_pretty="Secret Management",
            product_documentation="https://cloud.google.com/solutions/secrets-management/",
            api_description="allows you to encrypt, store, manage, and audit infrastructure and application-level secrets.",
            gapic_configs=list(),
        )
        self.assertEqual("secretmanager", get_library_name(library))
