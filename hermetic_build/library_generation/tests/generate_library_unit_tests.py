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
import subprocess
import unittest
import os
from library_generation.utils.utilities import (
    run_process_and_print_output as bash_call,
    run_process_and_get_output_string as get_bash_call_output,
)

script_dir = os.path.dirname(os.path.realpath(__file__))


class GenerateLibraryUnitTests(unittest.TestCase):
    """
    Confirms the correct behavior of `library_generation/utils/utilities.sh`.

    Note that there is an already existing, shell-based, test suite for
    generate_library.sh, but these tests will soon be transferred to this one as
    an effort to unify the implementation of the Hermetic Build scripts as
    python-only. New tests for `utilities.sh` should be added in this file.
    """

    TEST_ARCHITECTURE = "linux-x86_64"

    def setUp(self):
        # we create a simulated home folder that has a fake generator jar
        # in its well-known location
        self.simulated_home = get_bash_call_output("mktemp -d")
        bash_call(f"mkdir {self.simulated_home}/.library_generation")
        bash_call(
            f"touch {self.simulated_home}/.library_generation/gapic-generator-java.jar"
        )

        # We create a per-test directory where all output files will be created into.
        # Each folder will be deleted after its corresponding test finishes.
        test_dir = get_bash_call_output("mktemp -d")
        self.output_folder = self._run_command_and_get_sdout(
            "get_output_folder",
            cwd=test_dir,
        )
        bash_call(f"mkdir {self.output_folder}")

    def tearDown(self):
        bash_call(f"rm -rf {self.simulated_home}")

    def _run_command(self, command, **kwargs):
        env = os.environ.copy()
        env["HOME"] = self.simulated_home
        if "cwd" not in kwargs:
            kwargs["cwd"] = self.output_folder
        return bash_call(
            [
                "bash",
                "-exc",
                f"source {script_dir}/../utils/utilities.sh " + f"&& {command}",
            ],
            exit_on_fail=False,
            env=env,
            **kwargs,
        )

    def _run_command_and_get_sdout(self, command, **kwargs):
        return self._run_command(
            command, stderr=subprocess.PIPE, **kwargs
        ).stdout.decode()[:-1]

    def test_get_grpc_version_with_no_env_var_fails(self):
        # the absence of DOCKER_GRPC_VERSION will make this function to fail
        result = self._run_command("get_grpc_version")
        self.assertEqual(1, result.returncode)
        self.assertRegex(result.stdout.decode(), "DOCKER_GRPC_VERSION is not set")

    def test_get_protoc_version_with_no_env_var_fails(self):
        # the absence of DOCKER_PROTOC_VERSION will make this function to fail
        result = self._run_command("get_protoc_version")
        self.assertEqual(1, result.returncode)
        self.assertRegex(result.stdout.decode(), "DOCKER_PROTOC_VERSION is not set")

    def test_download_tools_without_baked_generator_fails(self):
        # This test has the same structure as
        # download_tools_succeed_with_baked_protoc, but meant for
        # gapic-generator-java.

        test_protoc_version = "1.64.0"
        test_grpc_version = "1.64.0"
        jar_location = (
            f"{self.simulated_home}/.library_generation/gapic-generator-java.jar"
        )
        # we expect the function to fail because the generator jar is not found in
        # its well-known location. To achieve this, we temporarily remove the fake
        # generator jar
        bash_call(f"rm {jar_location}")
        result = self._run_command(
            f"download_tools {test_protoc_version} {test_grpc_version} {self.TEST_ARCHITECTURE}"
        )
        self.assertEqual(1, result.returncode)
        self.assertRegex(result.stdout.decode(), "Please configure your environment")
