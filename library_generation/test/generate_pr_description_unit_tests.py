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

from library_generation.generate_pr_description import (
    get_commit_messages,
    generate_pr_descriptions,
)
from library_generation.model.config_change import ConfigChange
from library_generation.model.generation_config import GenerationConfig


class GeneratePrDescriptionTest(unittest.TestCase):
    def test_get_commit_messages_current_is_older_raise_exception(self):
        # committed on April 1st, 2024
        current_commit = "36441693dddaf0ed73951ad3a15c215a332756aa"
        # committed on April 2nd, 2024
        baseline_commit = "d5020fff4cbe108bdf506074791c56cff7840bef"
        self.assertRaisesRegex(
            ValueError,
            "newer than",
            get_commit_messages,
            "https://github.com/googleapis/googleapis.git",
            current_commit,
            baseline_commit,
            {},
            True,
        )

    def test_get_commit_messages_current_and_baseline_are_returns_empty_message(self):
        # committed on April 1st, 2024
        current_commit = "36441693dddaf0ed73951ad3a15c215a332756aa"
        baseline_commit = "36441693dddaf0ed73951ad3a15c215a332756aa"
        self.assertEqual(
            "",
            get_commit_messages(
                "https://github.com/googleapis/googleapis.git",
                current_commit,
                baseline_commit,
                {},
                True,
            ),
        )

    def test_generate_pr_description_with_same_googleapis_commits(self):
        commit_sha = "36441693dddaf0ed73951ad3a15c215a332756aa"
        config = GenerationConfig(
            gapic_generator_version="",
            googleapis_commitish=commit_sha,
            libraries_bom_version="",
            # use empty libraries to make sure no qualified commit between
            # two commit sha.
            libraries=[],
        )
        cwd = os.getcwd()
        generate_pr_descriptions(
            config_change=ConfigChange(
                change_to_libraries={},
                baseline_config=config,
                current_config=config,
            ),
            description_path=cwd,
        )
        self.assertFalse(os.path.isfile(f"{cwd}/pr_description.txt"))

    def test_generate_pr_description_does_not_create_pr_description_without_qualified_commit(
        self,
    ):
        # committed on May 22nd, 2024
        old_commit_sha = "30717c0b0c9966906880703208a4c820411565c4"
        # committed on May 23rd, 2024
        new_commit_sha = "eeed69d446a90eb4a4a2d1762c49d637075390c1"
        cwd = os.getcwd()
        generate_pr_descriptions(
            config_change=ConfigChange(
                change_to_libraries={},
                baseline_config=GenerationConfig(
                    gapic_generator_version="",
                    googleapis_commitish=old_commit_sha,
                    # use empty libraries to make sure no qualified commit between
                    # two commit sha.
                    libraries=[],
                ),
                current_config=GenerationConfig(
                    gapic_generator_version="",
                    googleapis_commitish=new_commit_sha,
                    # use empty libraries to make sure no qualified commit between
                    # two commit sha.
                    libraries=[],
                ),
            ),
            description_path=cwd,
        )
        self.assertFalse(os.path.isfile(f"{cwd}/pr_description.txt"))
