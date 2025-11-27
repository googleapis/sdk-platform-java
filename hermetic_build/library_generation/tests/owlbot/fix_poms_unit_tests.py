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
import shutil
import unittest
import tempfile
from pathlib import Path
from library_generation.owlbot.src.fix_poms import main
from library_generation.tests.compare_poms import compare_pom_in_subdir

script_dir = os.path.dirname(os.path.realpath(__file__))
resources_dir = os.path.join(script_dir, "..", "resources", "test-owlbot")


class FixPomsTest(unittest.TestCase):
    def setUp(self):
        self.temp_dir = tempfile.mkdtemp()
        self.start_dir = os.getcwd()
        os.chdir(self.temp_dir)

    def tearDown(self):
        os.chdir(self.start_dir)


    def test_update_poms_group_id_does_not_start_with_google_correctly(self):
        ad_manager_resource = os.path.join(resources_dir, "java-admanager")
        versions_file = os.path.join(ad_manager_resource, "versions.txt")
        os.chdir(ad_manager_resource)
        sub_dirs = ["ad-manager", "ad-manager-bom", "proto-ad-manager-v1", "."]
        for sub_dir in sub_dirs:
            self.__copy__golden(ad_manager_resource, sub_dir)
        main(versions_file, "true")
        for sub_dir in sub_dirs:
            self.assertFalse(compare_pom_in_subdir(ad_manager_resource, sub_dir))
        for sub_dir in sub_dirs:
            self.__remove_file_in_subdir(ad_manager_resource, sub_dir)


    def test_add_new_library(self):
        # Setup test environment in a structure similar to a real repo
        repo_dir = os.path.join(self.temp_dir, "google-cloud-java")
        os.makedirs(repo_dir)
        source_resource_dir = os.path.join(resources_dir, "google-cloud-java")
        new_library_dir_name = "java-newapi"
        new_library_path = os.path.join(repo_dir, new_library_dir_name)

        # Copy test resources to the temporary directory
        shutil.copytree(os.path.join(source_resource_dir, "java-newapi"), new_library_path)

        # Modify the .repo-metadata.json in the temporary new library folder
        repo_metadata_path = os.path.join(new_library_path, ".repo-metadata.json")
        with open(repo_metadata_path, "r") as f:
            repo_metadata_content = f.read()
        repo_metadata_content = repo_metadata_content.replace("google-cloud-newfolder", "google-cloud-newapi")
        with open(repo_metadata_path, "w") as f:
            f.write(repo_metadata_content)

        source_versions_file = os.path.join(source_resource_dir, "versions.txt")
        dest_versions_file = os.path.join(repo_dir, "versions.txt")
        shutil.copyfile(source_versions_file, dest_versions_file)

        # Create dummy directories for proto and grpc modules.
        # In the full generation process, `generate_library.sh` would create these.
        os.makedirs(os.path.join(new_library_path, "proto-google-cloud-newapi-v1"))
        os.makedirs(os.path.join(new_library_path, "grpc-google-cloud-newapi-v1"))
        os.chdir(new_library_path)

        # Get initial state of versions.txt (excluding comments and empty lines)
        with open(dest_versions_file, "r") as f:
            initial_active_lines = [line.strip() for line in f if line.strip() and not line.strip().startswith("#")]

        # Execute the main function from fix_poms.py
        main(dest_versions_file, "true")

        # --- Verify versions.txt ---
        with open(dest_versions_file, "r") as f:
            updated_versions_content = f.readlines()

        updated_active_lines = [line.strip() for line in updated_versions_content if line.strip() and not line.strip().startswith("#")]

        self.assertEqual(len(initial_active_lines) + 1, len(updated_active_lines),
                         "A single line should have been added to versions.txt")

        expected_new_line = "newapi:0.0.0:0.0.1-SNAPSHOT"
        self.assertTrue(any(expected_new_line in line for line in updated_active_lines),
                      f"The new line '{expected_new_line}' was not found in versions.txt")

        filtered_updated_lines = [line for line in updated_active_lines if expected_new_line not in line]
        self.assertCountEqual(initial_active_lines, filtered_updated_lines,
                              "Existing lines in versions.txt should be unaffected.")
        self.assertFalse(any("google-cloud-newapi" in line for line in updated_versions_content),
                         "The artifactId 'google-cloud-newapi' should not be in versions.txt")

        # --- Verify generated pom.xml files ---
        expected_poms = [
            "pom.xml",
            "google-cloud-newapi/pom.xml",
            "google-cloud-newapi-bom/pom.xml",
            "proto-google-cloud-newapi-v1/pom.xml",
            "grpc-google-cloud-newapi-v1/pom.xml",
        ]
        for pom_path in expected_poms:
            full_pom_path = os.path.join(new_library_path, pom_path)
            self.assertTrue(os.path.exists(full_pom_path), f"Expected POM file not found: {full_pom_path}")
            with open(full_pom_path, "r") as f:
                pom_content = f.read()
            self.assertIn("<!-- {x-version-update:newapi:current} -->", pom_content,
                          f"x-version-update tag in {pom_path} does not reference 'newapi'")
            # Ensure the artifactId is not changed to the release_please_key
            if pom_path == "google-cloud-newapi/pom.xml":
                self.assertIn("<artifactId>google-cloud-newapi</artifactId>", pom_content)
            if pom_path == "proto-google-cloud-newapi-v1/pom.xml":
                self.assertIn("<artifactId>proto-google-cloud-newapi-v1</artifactId>", pom_content)


    @classmethod
    def __copy__golden(cls, base_dir: str, subdir: str):
        golden = os.path.join(base_dir, subdir, "pom-golden.xml")
        pom = os.path.join(base_dir, subdir, "pom.xml")
        shutil.copyfile(golden, pom)

    @classmethod
    def __remove_file_in_subdir(cls, base_dir: str, subdir: str):
        pom = os.path.join(base_dir, subdir, "pom.xml")
        os.unlink(pom)
