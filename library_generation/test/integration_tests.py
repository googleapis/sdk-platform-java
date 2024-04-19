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
from filecmp import cmp
from filecmp import dircmp
from git import Repo
import os
import shutil
import subprocess
import unittest
from distutils.dir_util import copy_tree
from distutils.file_util import copy_file
from pathlib import Path
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.generation_config import from_yaml
from library_generation.test.compare_poms import compare_xml
from library_generation.utils.utilities import sh_util as shell_call

script_dir = os.path.dirname(os.path.realpath(__file__))
golden_dir = os.path.join(script_dir, "resources", "integration", "golden")
repo_root_dir = os.path.join(script_dir, "..", "..")
build_file = os.path.join(
    repo_root_dir, ".cloudbuild", "library_generation", "library_generation.Dockerfile"
)
image_tag = "test-image:latest"
repo_prefix = "https://github.com/googleapis"
output_dir = shell_call("get_output_folder")
# this map tells which branch of each repo should we use for our diff tests
committish_map = {
    "google-cloud-java": "chore/test-hermetic-build",
    "java-bigtable": "chore/test-hermetic-build",
}
config_dir = f"{script_dir}/resources/integration"
baseline_config_name = "baseline_generation_config.yaml"
current_config_name = "current_generation_config.yaml"


class IntegrationTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls) -> None:
        IntegrationTest.__build_image(docker_file=build_file, cwd=repo_root_dir)

    @classmethod
    def tearDownClass(cls) -> None:
        cls.__remove_docker_image()

    @classmethod
    def setUp(cls) -> None:
        cls.__remove_generated_files()
        os.makedirs(f"{golden_dir}", exist_ok=True)

    def test_entry_point_running_in_container(self):
        config_files = self.__get_config_files(config_dir)
        for repo, config_file in config_files:
            config = from_yaml(config_file)
            # 1. pull repository
            repo_dest = self.__pull_repo_to(
                Path(f"{output_dir}/{repo}"), repo, committish_map[repo]
            )
            # 2. prepare golden files
            library_names = self.__get_library_names_from_config(config)
            self.__prepare_golden_files(
                config=config, library_names=library_names, repo_dest=repo_dest
            )
            # 3. bind repository and configuration to docker volumes
            self.__bind_device_to_volumes(
                volume_name=f"repo-{repo}", device_dir=f"{output_dir}/{repo}"
            )
            self.__bind_device_to_volumes(
                volume_name=f"config-{repo}", device_dir=f"{golden_dir}/../{repo}"
            )
            repo_volumes = f"-v repo-{repo}:/workspace/{repo} -v config-{repo}:/workspace/config-{repo}"
            # 4. run entry_point.py in docker container
            self.__run_entry_point_in_docker_container(
                repo=repo,
                repo_volumes=repo_volumes,
                baseline_config=baseline_config_name,
                current_config=current_config_name,
            )
            # 5. compare generation result with golden files
            print(
                "Generation finished successfully. "
                "Will now compare differences between generated and existing "
                "libraries"
            )
            for library_name in library_names:
                actual_library = (
                    f"{repo_dest}/{library_name}" if config.is_monorepo() else repo_dest
                )
                print("*" * 50)
                print(f"Checking for differences in '{library_name}'.")
                print(f"  The expected library is in {golden_dir}/{library_name}.")
                print(f"  The actual library is in {actual_library}. ")
                target_repo_dest = (
                    f"{repo_dest}/{library_name}" if config.is_monorepo() else repo_dest
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

                print(f"  No differences found in {library_name}")
                # compare .repo-metadata.json
                self.assertTrue(
                    self.__compare_json_files(
                        f"{golden_dir}/{library_name}/.repo-metadata.json",
                        f"{target_repo_dest}/.repo-metadata.json",
                    ),
                    msg=f"  The generated {library_name}/.repo-metadata.json is different from golden.",
                )
                print("  .repo-metadata.json comparison succeed.")

                if not config.is_monorepo():
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
                # compare PR description
                description_file = f"{output_dir}/{repo}/pr_description.txt"
                self.assertTrue(
                    cmp(
                        f"{config_dir}/{repo}/pr-description-golden.txt",
                        f"{description_file}",
                    ),
                    "The generated PR description does not match the expected golden file",
                )
                print("  PR description comparison succeed.")
        self.__remove_generated_files()

    @classmethod
    def __build_image(cls, docker_file: str, cwd: str):
        subprocess.check_call(
            ["docker", "build", "--rm", "-f", docker_file, "-t", image_tag, "."],
            cwd=cwd,
        )

    @classmethod
    def __remove_generated_files(cls):
        # uncomment this line when the generated files don't owned by root.
        # shutil.rmtree(f"{output_dir}", ignore_errors=True)
        if os.path.isdir(f"{golden_dir}"):
            shutil.rmtree(f"{golden_dir}")

    @classmethod
    def __pull_repo_to(cls, dest: Path, repo: str, committish: str) -> str:
        shutil.rmtree(dest, ignore_errors=True)
        repo_url = f"{repo_prefix}/{repo}"
        print(f"Cloning repository {repo_url}")
        repo = Repo.clone_from(repo_url, dest)
        repo.git.checkout(committish)
        return str(dest)

    @classmethod
    def __get_library_names_from_config(cls, config: GenerationConfig) -> list[str]:
        library_names = []
        for library in config.libraries:
            library_names.append(f"java-{library.get_library_name()}")

        return library_names

    @classmethod
    def __prepare_golden_files(
        cls, config: GenerationConfig, library_names: list[str], repo_dest: str
    ):
        for library_name in library_names:
            if config.is_monorepo():
                copy_tree(f"{repo_dest}/{library_name}", f"{golden_dir}/{library_name}")
                copy_tree(
                    f"{repo_dest}/gapic-libraries-bom",
                    f"{golden_dir}/gapic-libraries-bom",
                )
                copy_file(f"{repo_dest}/pom.xml", golden_dir)
            else:
                copy_tree(f"{repo_dest}", f"{golden_dir}/{library_name}")

    @classmethod
    def __bind_device_to_volumes(cls, volume_name: str, device_dir: str):
        # We use a volume to hold the repositories used in the integration
        # tests. This is because the test container creates a child container
        # using the host machine's docker socket, meaning that we can only
        # reference volumes created from within the host machine (i.e. the
        # machine running this script).
        #
        # To summarize, we create a special volume that can be referenced both
        # in the main container and in any child containers created by this one.

        # use subprocess.run because we don't care about the return value (we
        # want to remove the volume in any case).
        subprocess.run(["docker", "volume", "rm", volume_name])
        subprocess.check_call(
            [
                "docker",
                "volume",
                "create",
                "--name",
                volume_name,
                "--opt",
                "type=none",
                "--opt",
                f"device={device_dir}",
                "--opt",
                "o=bind",
            ]
        )

    @classmethod
    def __run_entry_point_in_docker_container(
        cls,
        repo: str,
        repo_volumes: str,
        baseline_config: str,
        current_config: str,
    ):
        subprocess.check_call(
            [
                "docker",
                "run",
                "--rm",
                "-v",
                f"repo-{repo}:/workspace/{repo}",
                "-v",
                f"config-{repo}:/workspace/config-{repo}",
                "-v",
                "/tmp:/tmp",
                "-v",
                "/var/run/docker.sock:/var/run/docker.sock",
                "-e",
                "RUNNING_IN_DOCKER=true",
                "-e",
                f"REPO_BINDING_VOLUMES={repo_volumes}",
                "-w",
                "/src",
                image_tag,
                "python",
                "/src/cli/entry_point.py",
                "generate",
                f"--baseline-generation-config-path=/workspace/config-{repo}/{baseline_config}",
                f"--current-generation-config-path=/workspace/config-{repo}/{current_config}",
                f"--repository-path=/workspace/{repo}",
            ]
        )

    @classmethod
    def __get_config_files(cls, path: str) -> list[tuple[str, str]]:
        config_files = []
        for sub_dir in Path(path).resolve().iterdir():
            if sub_dir.is_file():
                continue
            repo = sub_dir.name
            if repo in ["golden", "java-bigtable"]:
                continue
            config = f"{sub_dir}/{current_config_name}"
            config_files.append((repo, config))
        return config_files

    @classmethod
    def __compare_json_files(cls, expected: str, actual: str) -> bool:
        return cls.__load_json_to_sorted_list(
            expected
        ) == cls.__load_json_to_sorted_list(actual)

    @classmethod
    def __load_json_to_sorted_list(cls, path: str) -> list[tuple]:
        with open(path) as f:
            data = json.load(f)
        res = [(key, value) for key, value in data.items()]

        return sorted(res, key=lambda x: x[0])

    @classmethod
    def __recursive_diff_files(
        cls,
        dcmp: dircmp,
        diff_files: list[str],
        left_only: list[str],
        right_only: list[str],
        dirname: str = "",
    ):
        """
        Recursively compares two subdirectories. The found differences are
        passed to three expected list references.
        """
        append_dirname = lambda d: dirname + d
        diff_files.extend(map(append_dirname, dcmp.diff_files))
        left_only.extend(map(append_dirname, dcmp.left_only))
        right_only.extend(map(append_dirname, dcmp.right_only))
        for sub_dirname, sub_dcmp in dcmp.subdirs.items():
            cls.__recursive_diff_files(
                sub_dcmp, diff_files, left_only, right_only, dirname + sub_dirname + "/"
            )

    @classmethod
    def __remove_docker_image(cls):
        subprocess.check_call(["docker", "image", "rmi", image_tag])
