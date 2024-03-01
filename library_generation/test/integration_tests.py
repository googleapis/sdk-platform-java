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

from git import Repo
from pathlib import Path
from typing import List
from typing import Dict
from library_generation.generate_repo import generate_from_yaml
from library_generation.model.generation_config import from_yaml
from library_generation.test.compare_poms import compare_xml
from library_generation.utilities import get_commit_messages
from library_generation.utilities import get_file_paths
from library_generation.utilities import (
    get_library_name,
    sh_util as shell_call,
    run_process_and_print_output,
)

config_name = "generation_config.yaml"
script_dir = os.path.dirname(os.path.realpath(__file__))
# for simplicity, the configuration files should be in a relative directory
# within config_dir named {repo}/generation_config.yaml, where repo is
# the name of the repository the target libraries live.
config_dir = f"{script_dir}/resources/integration"
golden_dir = f"{config_dir}/golden"
repo_prefix = "https://github.com/googleapis"
committish_list = ["chore/test-hermetic-build"]  # google-cloud-java
output_folder = shell_call("get_output_folder")


class IntegrationTest(unittest.TestCase):
    def test_get_commit_message_success(self):
        repo_url = "https://github.com/googleapis/googleapis.git"
        new_committish = "5c5ecf0eb9bd8acf5bed57fe1ce0df1770466089"
        old_committish = "e04130efabc98c20c56675b0c0af603087681f48"
        paths = get_file_paths(f"{config_dir}/google-cloud-java/{config_name}")
        message = get_commit_messages(
            repo_url=repo_url,
            new_committish=new_committish,
            old_committish=old_committish,
            paths=paths,
        )
        self.assertTrue("5c5ecf0eb9bd8acf5bed57fe1ce0df1770466089" in message)
        self.assertTrue("8df1cd698e2fe0b7d1c298dabafdf13aa01c4d39" in message)
        self.assertTrue("87a71a910ce60009cc578eb183062fb7d62e664f" in message)
        self.assertTrue("cfda64661b5e005730e9c7f24745ff2e40680647" in message)
        self.assertFalse("e04130efabc98c20c56675b0c0af603087681f48" in message)

    def test_generate_repo(self):
        shutil.rmtree(f"{golden_dir}", ignore_errors=True)
        os.makedirs(f"{golden_dir}", exist_ok=True)
        config_files = self.__get_config_files(config_dir)
        i = 0
        for repo, config_file in config_files.items():
            repo_dest = self.__pull_repo_to(
                Path(f"{golden_dir}/{repo}"), repo, committish_list[i]
            )
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
                    f"Generation finished. Will now compare "
                    f"the expected library in {golden_dir}/{library_name}, "
                    f"with the actual library in {repo_dest}/{library_name}. "
                    f"Compare generation result: "
                )
                compare_result = dircmp(
                    f"{golden_dir}/{library_name}",
                    f"{repo_dest}/{library_name}",
                    ignore=[".repo-metadata.json"],
                )
                # compare source code
                self.assertEqual([], compare_result.left_only)
                self.assertEqual([], compare_result.right_only)
                self.assertEqual([], compare_result.diff_files)
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
    def __pull_repo_to(cls, default_dest: Path, repo: str, committish: str) -> str:
        if "RUNNING_IN_DOCKER" in os.environ:
            # the docker image expects the repo to be in /workspace
            dest_in_docker = "/workspace"
            run_process_and_print_output(
                [
                    "git",
                    "config",
                    "--global",
                    "--add",
                    "safe.directory",
                    dest_in_docker,
                ],
                "Add /workspace to safe directories",
            )
            dest = Path(dest_in_docker)
            repo = Repo(dest)
        else:
            dest = default_dest
            repo_dest = f"{golden_dir}/{repo}"
            repo_url = f"{repo_prefix}/{repo}"
            print(f"Cloning repository {repo_url}")
            repo = Repo.clone_from(repo_url, dest)
        repo.git.checkout(committish)
        return str(dest)

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
            # skip the split repo.
            if repo == "golden" or repo == "java-bigtable":
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
