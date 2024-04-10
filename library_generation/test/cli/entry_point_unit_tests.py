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
from click.testing import CliRunner
from library_generation.cli.entry_point import generate


class EntryPointTest(unittest.TestCase):
    def test_entry_point_without_config_raise_file_exception(self):
        runner = CliRunner()
        # noinspection PyTypeChecker
        result = runner.invoke(generate, ["--repository-path=."])
        self.assertEqual(1, result.exit_code)
        self.assertEqual(FileNotFoundError, result.exc_info[0])
        self.assertRegex(
            result.exception.args[0], "generation_config.yaml does not exist."
        )

    def test_entry_point_with_baseline_without_current_raise_file_exception(self):
        runner = CliRunner()
        # noinspection PyTypeChecker
        result = runner.invoke(
            generate,
            ["--baseline-generation-config=path/to/config.yaml", "--repository-path=."],
        )
        self.assertEqual(1, result.exit_code)
        self.assertEqual(FileNotFoundError, result.exc_info[0])
        self.assertRegex(
            result.exception.args[0],
            "current_generation_config is not specified when "
            "baseline_generation_config is specified.",
        )
