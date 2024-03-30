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
from library_generation.utilities import sh_util as shell_call

script_dir = os.path.dirname(os.path.realpath(__file__))
golden_dir = os.path.join(script_dir, "resources", "integration", "golden")
repo_root_dir = os.path.join(script_dir, "..", "..")
docker_file = os.path.join(
    repo_root_dir, ".cloudbuild", "library_generation", "library_generation.Dockerfile"
)
image_tag = f"test-image:latest"
repo_prefix = "https://github.com/googleapis"
output_dir = shell_call("get_output_folder")
# this map tells which branch of each repo should we use for our diff tests
committish_map = {
    "google-cloud-java": "chore/test-hermetic-build",
    "java-bigtable": "chore/test-hermetic-build",
}
config_dir = f"{script_dir}/resources/integration"
config_name = "generation_config.yaml"


class ContainerIntegrationTest(unittest.TestCase):
    def test_entry_point_running_in_container(self):
        # build docker image
        subprocess.check_call(
            ["docker", "build", "--rm", "-f", docker_file, "-t", image_tag, "."],
            cwd=repo_root_dir,
        )

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
            # bind repository to docker volumes
            self.__bind_device_to_volumes(
                volume_name=f"repo-{repo}", device_dir=f"{output_dir}/{repo}"
            )
            # bind configuration to docker volumes
            self.__bind_device_to_volumes(
                volume_name=f"config-{repo}", device_dir=f"{golden_dir}/../{repo}"
            )
            repo_volumes = f"-v repo-{repo}:/workspace/{repo} -v config-{repo}:/workspace/config-{repo}"
            # run docker container
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
                    "/src/generate_repo.py",
                    "generate",
                    f"--generation-config-yaml=/workspace/config-{repo}/{config_name}",
                    f"--repository-path=/workspace/{repo}",
                ]
            )

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
    def __bind_device_to_volumes(cls, volume_name: str, device_dir: str):
        subprocess.check_call(["docker", "volume", "rm", volume_name])
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
    def __get_config_files(cls, path: str) -> list[tuple[str, str]]:
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
