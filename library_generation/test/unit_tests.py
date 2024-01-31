"""
Unit tests for utilities.py
"""

import unittest
import os
import io
import contextlib
import subprocess
from pathlib import Path

from library_generation import utilities as util
from library_generation.model.gapic_config import GapicConfig
from library_generation.model.gapic_inputs import parse as parse_build_file

script_dir = os.path.dirname(os.path.realpath(__file__))
resources_dir = os.path.join(script_dir, "resources")


class UtilitiesTest(unittest.TestCase):
    CONFIGURATION_YAML_PATH = os.path.join(
        script_dir,
        "resources",
        "integration",
        "google-cloud-java",
        "generation_config.yaml",
    )

    def test_create_argument_valid_container_succeeds(self):
        container_value = "google/test/v1"
        container = GapicConfig(container_value)
        argument_key = "proto_path"
        result = util.create_argument(argument_key, container)
        self.assertEqual([f"--{argument_key}", container_value], result)

    def test_create_argument_empty_container_returns_empty_list(self):
        container = dict()
        argument_key = "proto_path"
        result = util.create_argument(argument_key, container)
        self.assertEqual([], result)

    def test_create_argument_none_container_fails(self):
        container = None
        argument_key = "proto_path"
        result = util.create_argument(argument_key, container)
        self.assertEqual([], result)

    # def test_get_config_library_api_shortnames_valid_input_returns_valid_list(
    #     self,
    # ):
    #     result = util.get_configuration_yaml_library_api_shortnames(
    #         self.CONFIGURATION_YAML_PATH
    #     )
    #     self.assertEqual(
    #         "cloudasset speech apigee-connect dialogflow compute kms "
    #         + "redis containeranalysis iam iamcredentials",
    #         result,
    #     )

    def test_get_config_destination_path_returns_valid_destination_path(
        self,
    ):
        result = util.get_configuration_yaml_destination_path(
            self.CONFIGURATION_YAML_PATH
        )
        self.assertEqual("google-cloud-java", result)

    def test_sh_util_existent_function_succeeds(self):
        result = util.sh_util("extract_folder_name path/to/folder_name")
        self.assertEqual("folder_name", result)

    def test_sh_util_nonexistent_function_fails(self):
        with self.assertRaises(RuntimeError):
            result = util.sh_util("nonexistent_function")

    def test_eprint_valid_input_succeeds(self):
        test_input = "This is some test input"
        # create a stdio capture object
        stderr_capture = io.StringIO()
        # run eprint() with the capture object
        with contextlib.redirect_stderr(stderr_capture):
            util.eprint(test_input)
        result = stderr_capture.getvalue()
        # print() appends a `\n` each time it's called
        self.assertEqual(test_input + "\n", result)

    def test_delete_if_exists_preexisting_temp_files_succeeds(self):
        # create temporary directory
        # also remove last character (\n)
        temp_dir = subprocess.check_output(["mktemp", "-d"]).decode()[:-1]

        # add a file and a folder to the temp dir
        file = os.path.join(temp_dir, "temp_file")
        with open(file, "a"):
            os.utime(file, None)
        folder = os.path.join(temp_dir, "temp_child_dir")
        os.mkdir(folder)
        self.assertEqual(2, len(os.listdir(temp_dir)))

        # remove file and folder
        util.delete_if_exists(file)
        util.delete_if_exists(folder)
        self.assertEqual(0, len(os.listdir(temp_dir)))

    def test_client_inputs_parse_grpc_only_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(build_file, "", "BUILD_grpc.bazel")
        self.assertEqual("grpc", parsed.transport)

    def test_client_inputs_parse_grpc_rest_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(build_file, "", "BUILD_grpc_rest.bazel")
        self.assertEqual("grpc+rest", parsed.transport)

    def test_client_inputs_parse_rest_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(build_file, "", "BUILD_rest.bazel")
        self.assertEqual("rest", parsed.transport)

    def test_client_inputs_parse_empty_include_samples_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(build_file, "", "BUILD_include_samples_empty.bazel")
        self.assertEqual("false", parsed.include_samples)

    def test_client_inputs_parse_include_samples_false_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(build_file, "", "BUILD_include_samples_false.bazel")
        self.assertEqual("false", parsed.include_samples)

    def test_client_inputs_parse_include_samples_true_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(build_file, "", "BUILD_include_samples_true.bazel")
        self.assertEqual("true", parsed.include_samples)

    def test_client_inputs_parse_empty_rest_numeric_enums_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(
            build_file, "", "BUILD_rest_numeric_enums_empty.bazel"
        )
        self.assertEqual("false", parsed.rest_numeric_enum)

    def test_client_inputs_parse_rest_numeric_enums_false_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(
            build_file, "", "BUILD_rest_numeric_enums_false.bazel"
        )
        self.assertEqual("false", parsed.rest_numeric_enum)

    def test_client_inputs_parse_rest_numeric_enums_true_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(build_file, "", "BUILD_rest_numeric_enums_true.bazel")
        self.assertEqual("true", parsed.rest_numeric_enum)

    def test_client_inputs_parse_no_gapic_library_returns_proto_only_true(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        # include_samples_empty only has a gradle assembly rule
        parsed = parse_build_file(build_file, "", "BUILD_include_samples_empty.bazel")
        self.assertEqual("true", parsed.proto_only)

    def test_client_inputs_parse_with_gapic_library_returns_proto_only_false(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        # rest.bazel has a java_gapic_library rule
        parsed = parse_build_file(build_file, "", "BUILD_rest.bazel")
        self.assertEqual("false", parsed.proto_only)

    def test_client_inputs_parse_gapic_yaml_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_gapic_yaml.bazel"
        )
        self.assertEqual("test/versioned/path/test_gapic_yaml.yaml", parsed.gapic_yaml)

    def test_client_inputs_parse_no_gapic_yaml_returns_empty_string(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_no_gapic_yaml.bazel"
        )
        self.assertEqual("", parsed.gapic_yaml)

    def test_client_inputs_parse_service_config_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_service_config.bazel"
        )
        self.assertEqual(
            "test/versioned/path/test_service_config.json", parsed.service_config
        )

    def test_client_inputs_parse_no_service_config_returns_empty_string(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_no_service_config.bazel"
        )
        self.assertEqual("", parsed.service_config)

    def test_client_inputs_parse_service_yaml_succeeds(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_service_yaml.bazel"
        )
        self.assertEqual(
            "test/versioned/path/test_service_yaml.yaml", parsed.service_yaml
        )

    def test_client_inputs_parse_no_service_yaml_returns_empty_string(self):
        build_file = Path(os.path.join(resources_dir, "misc")).resolve()
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_no_service_yaml.bazel"
        )
        self.assertEqual("", parsed.service_yaml)


if __name__ == "__main__":
    unittest.main()
