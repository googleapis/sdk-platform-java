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

from library_generation.generate_pr_description import get_commit_messages


class GeneratePrDescriptionTest(unittest.TestCase):
    def test_get_commit_messages_current_is_older_raise_exception(self):
        self.assertRaisesRegex(
            ValueError,
            "newer than",
            get_commit_messages,
            "https://github.com/googleapis/googleapis.git",
            "36441693dddaf0ed73951ad3a15c215a332756aa",
            "8d326d5a7e7146e41ecce7f921e51c97504f7487",
            {},
            True,
        )
