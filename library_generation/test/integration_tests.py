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
import json
import os
import shutil
import unittest
from distutils.dir_util import copy_tree
from distutils.file_util import copy_file
from filecmp import dircmp

from lxml import etree
from git import Repo
from pathlib import Path
from typing import List
from typing import Dict
from library_generation.generate_repo import generate_from_yaml
from library_generation.model.generation_config import from_yaml
from library_generation.test.compare_poms import compare_xml
from library_generation.utilities import get_library_name

config_name = "generation_config.yaml"
script_dir = os.path.dirname(os.path.realpath(__file__))
# for simplicity, the configuration files should be in a relative directory
# within config_dir named {repo}/generation_config.yaml, where repo is
# the name of the repository the target libraries live.
config_dir = f"{script_dir}/resources/integration"
golden_dir = f"{config_dir}/golden"
repo_prefix = "https://github.com/googleapis"
committish_list = ["chore/test-hermetic-build"]  # google-cloud-java


class IntegrationTest(unittest.TestCase):
    def test_generate_repo(self):
        shutil.rmtree(f"{golden_dir}", ignore_errors=True)
        os.makedirs(f"{golden_dir}", exist_ok=True)
        config_files = self.__get_config_files(config_dir)
        i = 0
        for repo, config_file in config_files.items():
            repo_dest = f"{golden_dir}/{repo}"
            self.__pull_repo_to(Path(repo_dest), repo, committish_list[i])
            library_names = self.__get_library_names_from_config(config_file)
            # prepare golden files
            for library_name in library_names:
                copy_tree(f"{repo_dest}/{library_name}", f"{golden_dir}/{library_name}")
            copy_tree(
                f"{repo_dest}/gapic-libraries-bom", f"{golden_dir}/gapic-libraries-bom"
            )
            copy_file(f"{repo_dest}/pom.xml", golden_dir)
            generate_from_yaml(
                generation_config_yaml=config_file, repository_path=repo_dest
            )
            # compare result
            for library_name in library_names:
                print(
                    f"Compare generation result: "
                    f"expected library in {golden_dir}/{library_name}, "
                    f"actual library in {repo_dest}/{library_name}."
                )
                compare_result = dircmp(
                    f"{golden_dir}/{library_name}",
                    f"{repo_dest}/{library_name}",
                    ignore=[".repo-metadata.json"],
                )
                # compare source code
                self.assertEqual([], compare_result.left_only)
                self.assertEqual([], compare_result.right_only)
                # self.assertEqual([], compare_result.diff_files)
                print("Source code comparison succeed.")
                # compare .repo-metadata.json
                self.assertTrue(
                    self.__compare_json_files(
                        f"{golden_dir}/{library_name}/.repo-metadata.json",
                        f"{repo_dest}/{library_name}/.repo-metadata.json",
                    ),
                    msg=f"The generated {library_name}/.repo-metadata.json is different from golden.",
                )
                print(".repo-metadata.json comparison succeed.")
                # compare gapic-libraries-bom/pom.xml and pom.xml
                self.assertFalse(
                    compare_xml(
                        f"{golden_dir}/gapic-libraries-bom/pom.xml",
                        f"{repo_dest}/gapic-libraries-bom/pom.xml",
                        False,
                    )
                )
                print("gapic-libraries-bom/pom.xml comparison succeed.")
                self.assertFalse(
                    compare_xml(
                        f"{golden_dir}/pom.xml",
                        f"{repo_dest}/pom.xml",
                        False,
                    )
                )
                print("pom.xml comparison succeed.")
            # remove google-cloud-java
            i += 1

    @classmethod
    def __pull_repo_to(cls, dest: Path, repo: str, committish: str):
        repo_url = f"{repo_prefix}/{repo}"
        repo = Repo.clone_from(repo_url, dest)
        repo.git.checkout(committish)

    @classmethod
    def __get_library_names_from_config(cls, config_path: str) -> List[str]:
        config = from_yaml(config_path)
        library_names = []
        for library in config.libraries:
            library_names.append(f"java-{get_library_name(library)}")

        return library_names

    @classmethod
    def __get_config_files(cls, path: str) -> Dict[str, str]:
        config_files = {}
        for sub_dir in Path(path).resolve().iterdir():
            repo = sub_dir.name
            if repo == "golden":
                continue
            config = f"{sub_dir}/{config_name}"
            config_files[repo] = config

        return config_files

    @classmethod
    def __compare_json_files(cls, expected: str, actual: str) -> bool:
        return cls.__load_json_to_sorted_list(
            expected
        ) == cls.__load_json_to_sorted_list(actual)

    @classmethod
    def __load_json_to_sorted_list(cls, path: str) -> List[tuple]:
        with open(path) as f:
            data = json.load(f)
        res = [(key, value) for key, value in data.items()]

        return sorted(res, key=lambda x: x[0])
