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
import os
import shutil
import unittest
from filecmp import dircmp
from unittest.mock import patch

from library_generation.owlbot.synthtool.languages.java import common_templates
from library_generation.test.test_utils import FileComparator

script_dir = os.path.dirname(os.path.realpath(__file__))
template_dir = os.path.join(script_dir, "..", "..", "..", "..", "owlbot", "templates")
resources_dir = os.path.join(
    script_dir, "..", "..", "..", "resources", "test_synthtool"
)


class JavaTest(unittest.TestCase):

    @patch.dict(
        os.environ,
        {
            "SYNTHTOOL_TEMPLATES": template_dir,
            "SYNTHTOOL_LIBRARIES_BOM_VERSION": "26.34.0",
            "SYNTHTOOL_LIBRARY_VERSION": "3.19.2",
        },
    )
    def test_common_templates_render_github_dir(self):
        old_dir = os.getcwd()
        test_resource = os.path.join(resources_dir, "without_excludes")
        rendered_dir = os.path.join(test_resource, "rendered")
        if not os.path.isdir(rendered_dir):
            os.makedirs(rendered_dir)
        shutil.copy(os.path.join(test_resource, ".repo-metadata.json"), rendered_dir)
        os.chdir(rendered_dir)
        common_templates(
            excludes=[
                ".kokoro/build.sh",
                ".kokoro/common.cfg",
                ".kokoro/presubmit.cfg",
                ".kokoro/trampoline.cfg",
                "CODE_OF_CONDUCT.md",
                "CONTRIBUTING.md",
                "java.header",
                "LICENSE",
                "license-check.xml",
                "README.md",
                "renovate.json",
                "SECURITY.md",
            ]
        )
        diff_files = []
        golden_only = []
        generated_only = []
        FileComparator.recursive_diff_files(
            dcmp=dircmp(
                a=f"{test_resource}/goldens",
                b=rendered_dir,
                ignore=[".repo-metadata.json"],
            ),
            diff_files=diff_files,
            left_only=golden_only,
            right_only=generated_only,
        )
        self.assertTrue(len(golden_only) == 0)
        self.assertTrue(len(generated_only) == 0)
        self.assertTrue(len(diff_files) == 0)
        shutil.rmtree(rendered_dir)
        os.chdir(old_dir)
