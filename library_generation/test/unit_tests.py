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
"""
Unit tests for python scripts
"""

import unittest
import os
import io
import contextlib
from pathlib import Path
from difflib import unified_diff
from typing import List
from parameterized import parameterized
from library_generation import utilities as util
from library_generation.model.gapic_config import GapicConfig
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.gapic_inputs import parse as parse_build_file
from library_generation.model.generation_config import from_yaml
from library_generation.model.library_config import LibraryConfig

script_dir = os.path.dirname(os.path.realpath(__file__))
resources_dir = os.path.join(script_dir, "resources")
build_file = Path(os.path.join(resources_dir, "misc")).resolve()
test_config_dir = Path(os.path.join(resources_dir, "test-config")).resolve()
library_1 = LibraryConfig(
    api_shortname="baremetalsolution",
    name_pretty="Bare Metal Solution",
    product_documentation="https://cloud.google.com/bare-metal/docs",
    api_description="Bring your Oracle workloads to Google Cloud with Bare Metal Solution and jumpstart your cloud journey with minimal risk.",
    gapic_configs=list(),
    library_name="bare-metal-solution",
    rest_documentation="https://cloud.google.com/bare-metal/docs/reference/rest",
    rpc_documentation="https://cloud.google.com/bare-metal/docs/reference/rpc",
)
library_2 = LibraryConfig(
    api_shortname="secretmanager",
    name_pretty="Secret Management",
    product_documentation="https://cloud.google.com/solutions/secrets-management/",
    api_description="allows you to encrypt, store, manage, and audit infrastructure and application-level secrets.",
    gapic_configs=list(),
)


class UtilitiesTest(unittest.TestCase):
    """
    Unit tests for utilities.py
    """

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

    # parameterized tests need to run from the class, see
    # https://github.com/wolever/parameterized/issues/37 for more info.
    # This test confirms that a ValueError with an error message about a
    # missing key (specified in the first parameter of each `parameterized`
    # tuple) when parsing a test configuration yaml (second parameter) will
    # be raised.
    @parameterized.expand(
        [
            ("libraries", f"{test_config_dir}/config_without_libraries.yaml"),
            ("GAPICs", f"{test_config_dir}/config_without_gapics.yaml"),
            ("proto_path", f"{test_config_dir}/config_without_proto_path.yaml"),
            ("api_shortname", f"{test_config_dir}/config_without_api_shortname.yaml"),
            (
                "api_description",
                f"{test_config_dir}/config_without_api_description.yaml",
            ),
            ("name_pretty", f"{test_config_dir}/config_without_name_pretty.yaml"),
            (
                "product_documentation",
                f"{test_config_dir}/config_without_product_docs.yaml",
            ),
            (
                "gapic_generator_version",
                f"{test_config_dir}/config_without_generator.yaml",
            ),
            (
                "googleapis_commitish",
                f"{test_config_dir}/config_without_googleapis.yaml",
            ),
            ("owlbot_cli_image", f"{test_config_dir}/config_without_owlbot.yaml"),
            ("synthtool_commitish", f"{test_config_dir}/config_without_synthtool.yaml"),
            (
                "template_excludes",
                f"{test_config_dir}/config_without_temp_excludes.yaml",
            ),
        ]
    )
    def test_from_yaml_without_key_fails(self, error_message_contains, path_to_yaml):
        self.assertRaisesRegex(
            ValueError,
            error_message_contains,
            from_yaml,
            path_to_yaml,
        )

    def test_from_yaml_succeeds(self):
        config = from_yaml(f"{test_config_dir}/generation_config.yaml")
        self.assertEqual("2.34.0", config.gapic_generator_version)
        self.assertEqual(25.2, config.protobuf_version)
        self.assertEqual(
            "1a45bf7393b52407188c82e63101db7dc9c72026", config.googleapis_commitish
        )
        self.assertEqual(
            "sha256:623647ee79ac605858d09e60c1382a716c125fb776f69301b72de1cd35d49409",
            config.owlbot_cli_image,
        )
        self.assertEqual(
            "6612ab8f3afcd5e292aecd647f0fa68812c9f5b5", config.synthtool_commitish
        )
        self.assertEqual(
            [
                ".github/*",
                ".kokoro/*",
                "samples/*",
                "CODE_OF_CONDUCT.md",
                "CONTRIBUTING.md",
                "LICENSE",
                "SECURITY.md",
                "java.header",
                "license-checks.xml",
                "renovate.json",
                ".gitignore",
            ],
            config.template_excludes,
        )
        library = config.libraries[0]
        self.assertEqual("cloudasset", library.api_shortname)
        self.assertEqual("Cloud Asset Inventory", library.name_pretty)
        self.assertEqual(
            "https://cloud.google.com/resource-manager/docs/cloud-asset-inventory/overview",
            library.product_documentation,
        )
        self.assertEqual(
            "provides inventory services based on a time series database.",
            library.api_description,
        )
        self.assertEqual("asset", library.library_name)
        self.assertEqual("@googleapis/analytics-dpe", library.codeowner_team)
        self.assertEqual(
            "proto-google-iam-v1-bom,google-iam-policy,proto-google-iam-v1",
            library.excluded_poms,
        )
        self.assertEqual("google-iam-policy", library.excluded_dependencies)
        gapics = library.gapic_configs
        self.assertEqual(5, len(gapics))
        self.assertEqual("google/cloud/asset/v1", gapics[0].proto_path)
        self.assertEqual("google/cloud/asset/v1p1beta1", gapics[1].proto_path)
        self.assertEqual("google/cloud/asset/v1p2beta1", gapics[2].proto_path)
        self.assertEqual("google/cloud/asset/v1p5beta1", gapics[3].proto_path)
        self.assertEqual("google/cloud/asset/v1p7beta1", gapics[4].proto_path)

    def test_gapic_inputs_parse_grpc_only_succeeds(self):
        parsed = parse_build_file(build_file, "", "BUILD_grpc.bazel")
        self.assertEqual("grpc", parsed.transport)

    def test_gapic_inputs_parse_grpc_rest_succeeds(self):
        parsed = parse_build_file(build_file, "", "BUILD_grpc_rest.bazel")
        self.assertEqual("grpc+rest", parsed.transport)

    def test_gapic_inputs_parse_rest_succeeds(self):
        parsed = parse_build_file(build_file, "", "BUILD_rest.bazel")
        self.assertEqual("rest", parsed.transport)

    def test_gapic_inputs_parse_empty_include_samples_succeeds(self):
        parsed = parse_build_file(build_file, "", "BUILD_include_samples_empty.bazel")
        self.assertEqual("false", parsed.include_samples)

    def test_gapic_inputs_parse_include_samples_false_succeeds(self):
        parsed = parse_build_file(build_file, "", "BUILD_include_samples_false.bazel")
        self.assertEqual("false", parsed.include_samples)

    def test_gapic_inputs_parse_include_samples_true_succeeds(self):
        parsed = parse_build_file(build_file, "", "BUILD_include_samples_true.bazel")
        self.assertEqual("true", parsed.include_samples)

    def test_gapic_inputs_parse_empty_rest_numeric_enums_succeeds(self):
        parsed = parse_build_file(
            build_file, "", "BUILD_rest_numeric_enums_empty.bazel"
        )
        self.assertEqual("false", parsed.rest_numeric_enum)

    def test_gapic_inputs_parse_rest_numeric_enums_false_succeeds(self):
        parsed = parse_build_file(
            build_file, "", "BUILD_rest_numeric_enums_false.bazel"
        )
        self.assertEqual("false", parsed.rest_numeric_enum)

    def test_gapic_inputs_parse_rest_numeric_enums_true_succeeds(self):
        parsed = parse_build_file(build_file, "", "BUILD_rest_numeric_enums_true.bazel")
        self.assertEqual("true", parsed.rest_numeric_enum)

    def test_gapic_inputs_parse_no_gapic_library_returns_proto_only_true(self):
        # include_samples_empty only has a gradle assembly rule
        parsed = parse_build_file(build_file, "", "BUILD_include_samples_empty.bazel")
        self.assertEqual("true", parsed.proto_only)

    def test_gapic_inputs_parse_with_gapic_library_returns_proto_only_false(self):
        # rest.bazel has a java_gapic_library rule
        parsed = parse_build_file(build_file, "", "BUILD_rest.bazel")
        self.assertEqual("false", parsed.proto_only)

    def test_gapic_inputs_parse_gapic_yaml_succeeds(self):
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_gapic_yaml.bazel"
        )
        self.assertEqual("test/versioned/path/test_gapic_yaml.yaml", parsed.gapic_yaml)

    def test_gapic_inputs_parse_no_gapic_yaml_returns_empty_string(self):
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_no_gapic_yaml.bazel"
        )
        self.assertEqual("", parsed.gapic_yaml)

    def test_gapic_inputs_parse_service_config_succeeds(self):
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_service_config.bazel"
        )
        self.assertEqual(
            "test/versioned/path/test_service_config.json", parsed.service_config
        )

    def test_gapic_inputs_parse_service_yaml_relative_target(self):
        parsed = parse_build_file(
            build_file,
            "google/cloud/compute/v1",
            "BUILD_service_config_relative_target.bazel",
        )
        self.assertEqual(
            "google/cloud/compute/v1/compute_grpc_service_config.json",
            parsed.service_config,
        )

    def test_gapic_inputs_parse_no_service_config_returns_empty_string(self):
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_no_service_config.bazel"
        )
        self.assertEqual("", parsed.service_config)

    def test_gapic_inputs_parse_service_yaml_succeeds(self):
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_service_yaml.bazel"
        )
        self.assertEqual(
            "test/versioned/path/test_service_yaml.yaml", parsed.service_yaml
        )

    def test_gapic_inputs_parse_service_yaml_absolute_target(self):
        parsed = parse_build_file(
            build_file, "", "BUILD_service_yaml_absolute_target.bazel"
        )
        self.assertEqual(
            "google/cloud/videointelligence/videointelligence_v1p3beta1.yaml",
            parsed.service_yaml,
        )

    def test_gapic_inputs_parse_no_service_yaml_returns_empty_string(self):
        parsed = parse_build_file(
            build_file, "test/versioned/path", "BUILD_no_service_yaml.bazel"
        )
        self.assertEqual("", parsed.service_yaml)

    def test_remove_version_from_returns_non_versioned_path(self):
        proto_path = "google/cloud/aiplatform/v1"
        self.assertEqual(
            "google/cloud/aiplatform", util.remove_version_from(proto_path)
        )

    def test_remove_version_from_returns_self(self):
        proto_path = "google/cloud/aiplatform"
        self.assertEqual(
            "google/cloud/aiplatform", util.remove_version_from(proto_path)
        )

    def test_get_version_from_returns_current(self):
        versions_file = f"{resources_dir}/misc/versions.txt"
        artifact = "gax-grpc"
        self.assertEqual(
            "2.33.1-SNAPSHOT", util.get_version_from(versions_file, artifact)
        )

    def test_get_version_from_returns_released(self):
        versions_file = f"{resources_dir}/misc/versions.txt"
        artifact = "gax-grpc"
        self.assertEqual("2.34.0", util.get_version_from(versions_file, artifact, True))

    def test_get_library_returns_library_name(self):
        self.assertEqual("bare-metal-solution", util.get_library_name(library_1))

    def test_get_library_returns_api_shortname(self):
        self.assertEqual("secretmanager", util.get_library_name(library_2))

    def test_generate_prerequisite_files_success(self):
        library_path = f"{resources_dir}/goldens"
        files = [
            f"{library_path}/.repo-metadata.json",
            f"{library_path}/.OwlBot.yaml",
            f"{library_path}/owlbot.py",
        ]
        self.__cleanup(files)
        config = self.__get_a_gen_config(1)
        proto_path = "google/cloud/baremetalsolution/v2"
        transport = "grpc"
        util.generate_prerequisite_files(
            config=config,
            library=library_1,
            proto_path=proto_path,
            transport=transport,
            library_path=library_path,
        )

        self.__compare_files(
            f"{library_path}/.repo-metadata.json",
            f"{library_path}/.repo-metadata-golden.json",
        )
        self.__compare_files(
            f"{library_path}/.OwlBot.yaml", f"{library_path}/.OwlBot-golden.yaml"
        )
        self.__compare_files(
            f"{library_path}/owlbot.py", f"{library_path}/owlbot-golden.py"
        )

    def test_prepare_repo_monorepo_success(self):
        gen_config = self.__get_a_gen_config(2)
        repo_config = util.prepare_repo(
            gen_config=gen_config,
            library_config=gen_config.libraries,
            repo_path=f"{resources_dir}/misc",
        )
        self.assertEqual("output", Path(repo_config.output_folder).name)
        library_path = sorted([Path(key).name for key in repo_config.libraries])
        self.assertEqual(
            ["java-bare-metal-solution", "java-secretmanager"], library_path
        )

    def test_prepare_repo_monorepo_failed(self):
        gen_config = self.__get_a_gen_config(2)
        self.assertRaises(
            FileNotFoundError,
            util.prepare_repo,
            gen_config,
            gen_config.libraries,
            f"{resources_dir}/non-exist",
        )

    def test_prepare_repo_split_repo_success(self):
        gen_config = self.__get_a_gen_config(1)
        repo_config = util.prepare_repo(
            gen_config=gen_config,
            library_config=gen_config.libraries,
            repo_path=f"{resources_dir}/misc",
        )
        self.assertEqual("output", Path(repo_config.output_folder).name)
        library_path = sorted([Path(key).name for key in repo_config.libraries])
        self.assertEqual(["misc"], library_path)

    def test_repo_level_post_process_success(self):
        repository_path = f"{resources_dir}/test_repo_level_postprocess"
        versions_file = f"{repository_path}/versions.txt"
        files = [
            f"{repository_path}/pom.xml",
            f"{repository_path}/gapic-libraries-bom/pom.xml",
        ]
        self.__cleanup(files)
        util.repo_level_post_process(
            repository_path=repository_path, versions_file=versions_file
        )
        self.__compare_files(
            expect=f"{repository_path}/pom-golden.xml",
            actual=f"{repository_path}/pom.xml",
        )
        self.__compare_files(
            expect=f"{repository_path}/gapic-libraries-bom/pom-golden.xml",
            actual=f"{repository_path}/gapic-libraries-bom/pom.xml",
        )

    def __compare_files(self, expect: str, actual: str):
        with open(expect, "r") as f:
            expected_lines = f.readlines()
        with open(actual, "r") as f:
            actual_lines = f.readlines()

        diff = list(unified_diff(expected_lines, actual_lines))
        self.assertEqual(
            first=[], second=diff, msg="Unexpected file contents:\n" + "".join(diff)
        )

    @staticmethod
    def __get_a_gen_config(num: int):
        """
        Returns an object of GenerationConfig with one or two of
        LibraryConfig objects. Other attributes are set to empty str.

        :param num: the number of LibraryConfig objects associated with
        the GenerationConfig. Only support one or two.
        :return: an object of GenerationConfig
        """
        if num > 1:
            libraries = [library_1, library_2]
        else:
            libraries = [library_1]

        return GenerationConfig(
            gapic_generator_version="",
            googleapis_commitish="",
            owlbot_cli_image="",
            synthtool_commitish="",
            template_excludes=[
                ".github/*",
                ".kokoro/*",
                "samples/*",
                "CODE_OF_CONDUCT.md",
                "CONTRIBUTING.md",
                "LICENSE",
                "SECURITY.md",
                "java.header",
                "license-checks.xml",
                "renovate.json",
                ".gitignore",
            ],
            path_to_yaml=".",
            libraries=libraries,
        )

    @staticmethod
    def __cleanup(files: List[str]):
        for file in files:
            path = Path(file).resolve()
            if path.is_file():
                path.unlink()
            elif path.is_dir():
                path.rmdir()


if __name__ == "__main__":
    unittest.main()
