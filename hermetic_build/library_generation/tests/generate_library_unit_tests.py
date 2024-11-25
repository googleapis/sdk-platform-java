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

    def test_get_generator_location_with_env_returns_env(self):
        os.environ["GAPIC_GENERATOR_LOCATION"] = "/gapic-generator-java"
        result = self._run_command_and_get_sdout("get_gapic_generator_location")
        self.assertEqual("/gapic-generator-java", result)
        os.environ.pop("GAPIC_GENERATOR_LOCATION")

    def test_get_generator_location_without_env_with_local_returns_local(self):
        bash_call(
            f"touch {self.simulated_home}/.library_generation/gapic-generator-java.jar"
        )
        result = self._run_command_and_get_sdout("get_gapic_generator_location")
        self.assertEqual(
            f"{self.simulated_home}/.library_generation/gapic-generator-java.jar",
            result,
        )

    def test_get_generator_location_with_no_env_no_local_file_failed(self):
        result = self._run_command("get_gapic_generator_location")
        self.assertEqual(1, result.returncode)
        self.assertRegex(result.stdout.decode(), "Can't find GAPIC generator in")

    def test_get_protoc_location_with_env_returns_env(self):
        os.environ["DOCKER_PROTOC_LOCATION"] = "/protoc"
        result = self._run_command_and_get_sdout("get_protoc_location")
        self.assertEqual("/protoc", result)
        os.environ.pop("DOCKER_PROTOC_LOCATION")

    def test_get_protoc_location_without_env_with_local_returns_local(self):
        bash_call(f"mkdir -p {self.simulated_home}/.library_generation/bin")
        result = self._run_command_and_get_sdout("get_protoc_location")
        self.assertEqual(
            f"{self.simulated_home}/.library_generation/bin",
            result,
        )

    def test_get_protoc_location_with_no_env_no_local_file_failed(self):
        result = self._run_command("get_protoc_location")
        self.assertEqual(1, result.returncode)
        self.assertRegex(result.stdout.decode(), "Can't find protoc in")

    def test_get_grpc_plugin_location_with_env_returns_env(self):
        os.environ["DOCKER_GRPC_LOCATION"] = "/grpc"
        result = self._run_command_and_get_sdout("get_grpc_plugin_location")
        self.assertEqual("/grpc", result)
        os.environ.pop("DOCKER_GRPC_LOCATION")

    def test_get_grpc_plugin_location_without_env_with_local_returns_local(self):
        bash_call(
            f"touch {self.simulated_home}/.library_generation/protoc-gen-grpc-java.exe"
        )
        result = self._run_command_and_get_sdout("get_grpc_plugin_location")
        self.assertEqual(
            f"{self.simulated_home}/.library_generation/protoc-gen-grpc-java.exe",
            result,
        )

    def test_get_grpc_plugin_location_with_no_env_no_local_file_failed(self):
        result = self._run_command("get_grpc_plugin_location")
        self.assertEqual(1, result.returncode)
        self.assertRegex(result.stdout.decode(), "Can't find grpc plugin in")

    def test_get_formatter_location_with_env_returns_env(self):
        os.environ["JAVA_FORMATTER_LOCATION"] = "/java-formatter"
        result = self._run_command_and_get_sdout("get_java_formatter_location")
        self.assertEqual("/java-formatter", result)
        os.environ.pop("JAVA_FORMATTER_LOCATION")

    def test_get_formatter_location_without_env_with_local_returns_local(self):
        bash_call(
            f"touch {self.simulated_home}/.library_generation/google-java-format.jar"
        )
        result = self._run_command_and_get_sdout("get_java_formatter_location")
        self.assertEqual(
            f"{self.simulated_home}/.library_generation/google-java-format.jar",
            result,
        )

    def test_get_formatter_location_with_no_env_no_local_file_failed(self):
        result = self._run_command("get_java_formatter_location")
        self.assertEqual(1, result.returncode)
        self.assertRegex(result.stdout.decode(), "Can't find Java formatter in")
