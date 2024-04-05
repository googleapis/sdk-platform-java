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
from unittest.mock import patch

from library_generation.utils.commit_message_formatter import format_commit_message
from library_generation.utils.commit_message_formatter import wrap_nested_commit
from library_generation.utils.commit_message_formatter import wrap_override_commit


class CommitMessageFormatterTest(unittest.TestCase):
    def test_format_commit_message_should_add_library_name_for_conventional_commit(
        self,
    ):
        with patch("git.Commit") as mock_commit:
            commit = mock_commit.return_value
            commit.message = "feat: a commit message\nPiperOrigin-RevId: 123456"
            commits = {commit: "example_library"}
            self.assertEqual(
                [
                    "BEGIN_NESTED_COMMIT",
                    "feat: [example_library] a commit message",
                    "PiperOrigin-RevId: 123456",
                    "END_NESTED_COMMIT",
                ],
                format_commit_message(commits, True),
            )

    def test_format_commit_message_should_add_library_name_for_mutliline_conventional_commit(
        self,
    ):
        with patch("git.Commit") as mock_commit:
            commit = mock_commit.return_value
            commit.message = "feat: a commit message\nfix: an another commit message\nPiperOrigin-RevId: 123456"
            commits = {commit: "example_library"}
            self.assertEqual(
                [
                    "BEGIN_NESTED_COMMIT",
                    "feat: [example_library] a commit message",
                    "fix: [example_library] an another commit message",
                    "PiperOrigin-RevId: 123456",
                    "END_NESTED_COMMIT",
                ],
                format_commit_message(commits, True),
            )

    def test_format_commit_message_should_not_add_library_name_for_nonconvnentional_commit(
        self,
    ):
        with patch("git.Commit") as mock_commit:
            commit = mock_commit.return_value
            commit.message = "PiperOrigin-RevId: 123456"
            commits = {commit: "example_library"}
            self.assertEqual(
                [
                    "BEGIN_NESTED_COMMIT",
                    "PiperOrigin-RevId: 123456",
                    "END_NESTED_COMMIT",
                ],
                format_commit_message(commits, True),
            )

    def test_format_commit_message_should_not_add_library_name_if_not_monorepo(self):
        with patch("git.Commit") as mock_commit:
            commit = mock_commit.return_value
            commit.message = "feat: a commit message\nPiperOrigin-RevId: 123456"
            commits = {commit: "example_library"}
            self.assertEqual(
                [
                    "BEGIN_NESTED_COMMIT",
                    "feat: a commit message",
                    "PiperOrigin-RevId: 123456",
                    "END_NESTED_COMMIT",
                ],
                format_commit_message(commits, False),
            )

    def test_format_commit_message_should_not_add_library_name_for_multiline_commit_if_not_monorepo(
        self,
    ):
        with patch("git.Commit") as mock_commit:
            commit = mock_commit.return_value
            commit.message = "feat: a commit message\nfix: an another commit message\nPiperOrigin-RevId: 123456"
            commits = {commit: "example_library"}
            self.assertEqual(
                [
                    "BEGIN_NESTED_COMMIT",
                    "feat: a commit message",
                    "fix: an another commit message",
                    "PiperOrigin-RevId: 123456",
                    "END_NESTED_COMMIT",
                ],
                format_commit_message(commits, False),
            )

    def test_wrap_nested_commit_success(self):
        messages = ["a commit message", "another message"]
        self.assertEqual(
            [
                "BEGIN_NESTED_COMMIT",
                "a commit message",
                "another message",
                "END_NESTED_COMMIT",
            ],
            wrap_nested_commit(messages),
        )

    def test_wrap_override_commit_success(self):
        messages = ["a commit message", "another message"]
        self.assertEqual(
            [
                "BEGIN_COMMIT_OVERRIDE",
                "a commit message",
                "another message",
                "END_COMMIT_OVERRIDE",
            ],
            wrap_override_commit(messages),
        )
