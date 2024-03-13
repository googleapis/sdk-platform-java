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
from filecmp import cmp
from filecmp import dircmp

from git import Repo
from pathlib import Path
from typing import List

from library_generation.generate_pr_description import generate_pr_descriptions
from library_generation.generate_repo import generate_from_yaml
from library_generation.model.generation_config import from_yaml, GenerationConfig
from library_generation.test.compare_poms import compare_xml
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
output_dir = shell_call("get_output_folder")
# this map tells which branch of each repo should we use for our diff tests
committish_map = {
    "google-cloud-java": "chore/test-hermetic-build",
    "java-bigtable": "chore/test-hermetic-build",
}


class IntegrationTest(unittest.TestCase):
    def test_get_commit_message_success(self):
        repo_url = "https://github.com/googleapis/googleapis.git"
        config_files = self.__get_config_files(config_dir)
        monorepo_baseline_commit = "a17d4caf184b050d50cacf2b0d579ce72c31ce74"
        split_repo_baseline_commit = "679060c64136e85b52838f53cfe612ce51e60d1d"
        for repo, config_file in config_files:
            baseline_commit = (
                monorepo_baseline_commit
                if repo == "google-cloud-java"
                else split_repo_baseline_commit
            )
            description = generate_pr_descriptions(
                generation_config_yaml=config_file,
                repo_url=repo_url,
                baseline_commit=baseline_commit,
            )
            description_file = f"{config_dir}/{repo}/pr-description.txt"
            if os.path.isfile(f"{description_file}"):
                os.remove(f"{description_file}")
            with open(f"{description_file}", "w+") as f:
                f.write(description)
            self.assertTrue(
                cmp(
                    f"{config_dir}/{repo}/pr-description-golden.txt",
                    f"{description_file}",
                ),
                "The generated PR description does not match the expected golden file",
            )
            os.remove(f"{description_file}")

    def test_generate_repo(self):
        shutil.rmtree(f"{golden_dir}", ignore_errors=True)
        os.makedirs(f"{golden_dir}", exist_ok=True)
        config_files = self.__get_config_files(config_dir)
        for repo, config_file in config_files:
            config = from_yaml(config_file)
            repo_dest = self.__pull_repo_to(
                Path(f"{output_dir}/{repo}"), repo, committish_map[repo]
            )
            library_names = self.__get_library_names_from_config(config)
            # prepare golden files
            for library_name in library_names:
                if config.is_monorepo:
                    copy_tree(
                        f"{repo_dest}/{library_name}", f"{golden_dir}/{library_name}"
                    )
                    copy_tree(
                        f"{repo_dest}/gapic-libraries-bom",
                        f"{golden_dir}/gapic-libraries-bom",
                    )
                    copy_file(f"{repo_dest}/pom.xml", golden_dir)
                else:
                    copy_tree(f"{repo_dest}", f"{golden_dir}/{library_name}")
            generate_from_yaml(
                generation_config_yaml=config_file, repository_path=repo_dest
            )
            # compare result
            print(
                "Generation finished successfully. Will now compare differences between generated and existing libraries"
            )
            for library_name in library_names:
                actual_library = (
                    f"{repo_dest}/{library_name}" if config.is_monorepo else repo_dest
                )
                print("*" * 50)
                print(f"Checking for differences in '{library_name}'.")
                print(f"  The expected library is in {golden_dir}/{library_name}.")
                print(f"  The actual library is in {actual_library}. ")
                target_repo_dest = (
                    f"{repo_dest}/{library_name}" if config.is_monorepo else repo_dest
                )
                compare_result = dircmp(
                    f"{golden_dir}/{library_name}",
                    target_repo_dest,
                    ignore=[".repo-metadata.json"],
                )
                diff_files = []
                golden_only = []
                generated_only = []
                # compare source code
                self.__recursive_diff_files(
                    compare_result, diff_files, golden_only, generated_only
                )

                # print all found differences for inspection
                print_file = lambda f: print(f"   -  {f}")
                if len(diff_files) > 0:
                    print("  Some files (found in both folders) are differing:")
                    [print_file(f) for f in diff_files]
                if len(golden_only) > 0:
                    print("  There were files found only in the golden dir:")
                    [print_file(f) for f in golden_only]
                if len(generated_only) > 0:
                    print("  Some files were found to have differences:")
                    [print_file(f) for f in generated_only]

                self.assertTrue(len(golden_only) == 0)
                self.assertTrue(len(generated_only) == 0)
                self.assertTrue(len(diff_files) == 0)

                print("  No differences found in {library_name}")
                # compare .repo-metadata.json
                self.assertTrue(
                    self.__compare_json_files(
                        f"{golden_dir}/{library_name}/.repo-metadata.json",
                        f"{target_repo_dest}/.repo-metadata.json",
                    ),
                    msg=f"  The generated {library_name}/.repo-metadata.json is different from golden.",
                )
                print("  .repo-metadata.json comparison succeed.")

                if not config.is_monorepo:
                    continue

                # compare gapic-libraries-bom/pom.xml and pom.xml
                self.assertFalse(
                    compare_xml(
                        f"{golden_dir}/gapic-libraries-bom/pom.xml",
                        f"{repo_dest}/gapic-libraries-bom/pom.xml",
                        False,
                    )
                )
                print("  gapic-libraries-bom/pom.xml comparison succeed.")
                self.assertFalse(
                    compare_xml(
                        f"{golden_dir}/pom.xml",
                        f"{repo_dest}/pom.xml",
                        False,
                    )
                )
                print("  pom.xml comparison succeed.")

    @classmethod
    def __pull_repo_to(cls, default_dest: Path, repo: str, committish: str) -> str:
        if "RUNNING_IN_DOCKER" in os.environ:
            # the docker image expects the repo to be in /workspace
            dest_in_docker = f"/workspace/{repo}"
            run_process_and_print_output(
                [
                    "git",
                    "config",
                    "--global",
                    "--add",
                    "safe.directory",
                    dest_in_docker,
                ],
                f"Add /workspace/{repo} to safe directories",
            )
            dest = Path(dest_in_docker)
            repo = Repo(dest)
        else:
            dest = default_dest
            shutil.rmtree(dest, ignore_errors=True)
            repo_url = f"{repo_prefix}/{repo}"
            print(f"Cloning repository {repo_url}")
            repo = Repo.clone_from(repo_url, dest)
        repo.git.checkout(committish)
        return str(dest)

    @classmethod
    def __get_library_names_from_config(cls, config: GenerationConfig) -> List[str]:
        library_names = []
        for library in config.libraries:
            library_names.append(f"java-{get_library_name(library)}")

        return library_names

    @classmethod
    def __get_config_files(cls, path: str) -> List[tuple[str, str]]:
        config_files = []
        for sub_dir in Path(path).resolve().iterdir():
            if sub_dir.is_file():
                continue
            repo = sub_dir.name
            if repo in ["golden", "java-bigtable"]:
                continue
            config = f"{sub_dir}/{config_name}"
            config_files.append((repo, config))

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

    @classmethod
    def __recursive_diff_files(
        self,
        dcmp: dircmp,
        diff_files: List[str],
        left_only: List[str],
        right_only: List[str],
        dirname: str = "",
    ):
        """
        recursively compares two subdirectories. The found differences are passed to three expected list references
        """
        append_dirname = lambda d: dirname + d
        diff_files.extend(map(append_dirname, dcmp.diff_files))
        left_only.extend(map(append_dirname, dcmp.left_only))
        right_only.extend(map(append_dirname, dcmp.right_only))
        for sub_dirname, sub_dcmp in dcmp.subdirs.items():
            self.__recursive_diff_files(
                sub_dcmp, diff_files, left_only, right_only, dirname + sub_dirname + "/"
            )
