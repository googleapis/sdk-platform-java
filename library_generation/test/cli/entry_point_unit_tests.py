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
from click.testing import CliRunner
from library_generation.cli.entry_point import generate

script_dir = os.path.dirname(os.path.realpath(__file__))
resource_dir = os.path.join(script_dir, "..", "resources", "entry_point")


class EntryPointTest(unittest.TestCase):
    def test_entry_point_without_config_raise_file_exception(self):
        runner = CliRunner()
        # noinspection PyTypeChecker
        result = runner.invoke(generate, ["--repository-path=."])
        self.assertEqual(1, result.exit_code)
        self.assertEqual(FileNotFoundError, result.exc_info[0])

    def test_entry_point_with_default_config_success(self):
        os.chdir(resource_dir)
        runner = CliRunner()
        # noinspection PyTypeChecker
        result = runner.invoke(generate, ["--repository-path=."])
        self.assertEqual(0, result.exit_code)
