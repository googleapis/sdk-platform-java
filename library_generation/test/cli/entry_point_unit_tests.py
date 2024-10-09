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
import unittest
from unittest.mock import patch, ANY
from click.testing import CliRunner
from library_generation.cli.entry_point import (
    generate,
    validate_generation_config,
    __generate_repo_and_pr_description_impl as generate_impl,
)
from library_generation.model.generation_config import from_yaml

script_dir = os.path.dirname(os.path.realpath(__file__))
test_resource_dir = os.path.join(script_dir, "..", "resources", "test-config")


class EntryPointTest(unittest.TestCase):
    def test_entry_point_without_config_raise_file_exception(self):
        os.chdir(script_dir)
        runner = CliRunner()
        # noinspection PyTypeChecker
        result = runner.invoke(generate, ["--repository-path=."])
        self.assertEqual(1, result.exit_code)
        self.assertEqual(FileNotFoundError, result.exc_info[0])
        self.assertRegex(
            result.exception.args[0], "generation_config.yaml does not exist."
        )

    def test_entry_point_with_baseline_without_current_raise_file_exception(self):
        runner = CliRunner()
        # noinspection PyTypeChecker
        result = runner.invoke(
            generate,
            [
                "--baseline-generation-config-path=path/to/config.yaml",
                "--repository-path=.",
            ],
        )
        self.assertEqual(1, result.exit_code)
        self.assertEqual(FileNotFoundError, result.exc_info[0])
        self.assertRegex(
            result.exception.args[0],
            "current_generation_config is not specified when "
            "baseline_generation_config is specified.",
        )

    def test_validate_generation_config_succeeds(
        self,
    ):
        runner = CliRunner()
        # noinspection PyTypeChecker
        result = runner.invoke(
            validate_generation_config,
            [f"--generation-config-path={test_resource_dir}/generation_config.yaml"],
        )
        self.assertEqual(0, result.exit_code)

    def test_validate_generation_config_with_duplicate_library_name_raise_file_exception(
        self,
    ):
        runner = CliRunner()
        # noinspection PyTypeChecker
        result = runner.invoke(
            validate_generation_config,
            [
                f"--generation-config-path={test_resource_dir}/generation_config_with_duplicate_library_name.yaml"
            ],
        )
        self.assertEqual(1, result.exit_code)
        self.assertEqual(SystemExit, result.exc_info[0])
        self.assertRegex(
            result.output,
            "have the same library name",
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_non_monorepo_without_changes_triggers_full_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of non monorepos
        (HW libraries). generate() should call generate_from_yaml()
        with target_library_names=None in order to trigger the full generation
        """
        config_path = f"{test_resource_dir}/generation_config.yaml"
        self.assertFalse(from_yaml(config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=config_path,
            current_generation_config_path=config_path,
            includes=None,
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=None,
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_non_monorepo_without_changes_with_includes_triggers_full_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of non monorepos
        (HW libraries). generate() should call generate_from_yaml()
        with target_library_names=None in order to trigger the full generation
        """
        config_path = f"{test_resource_dir}/generation_config.yaml"
        self.assertFalse(from_yaml(config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=config_path,
            current_generation_config_path=config_path,
            includes="cloudasset,non-existent-library",
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=None,
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_non_monorepo_with_changes_triggers_full_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of non monorepos
        (HW libraries). generate() should call generate_from_yaml()
        with target_library_names=None in order to trigger the full generation
        """
        baseline_config_path = f"{test_resource_dir}/generation_config.yaml"
        current_config_path = (
            f"{test_resource_dir}/generation_config_library_modified.yaml"
        )
        self.assertFalse(from_yaml(current_config_path).is_monorepo())
        self.assertFalse(from_yaml(baseline_config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=baseline_config_path,
            current_generation_config_path=current_config_path,
            includes=None,
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=None,
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_non_monorepo_with_changes_with_includes_triggers_full_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of non monorepos
        (HW libraries). generate() should call generate_from_yaml()
        with target_library_names=None in order to trigger the full generation
        """
        baseline_config_path = f"{test_resource_dir}/generation_config.yaml"
        current_config_path = (
            f"{test_resource_dir}/generation_config_library_modified.yaml"
        )
        self.assertFalse(from_yaml(current_config_path).is_monorepo())
        self.assertFalse(from_yaml(baseline_config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=baseline_config_path,
            current_generation_config_path=current_config_path,
            includes="cloudasset,non-existent-library",
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=None,
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_monorepo_with_common_protos_triggers_full_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of a monorepo with
        common protos.
        generate() should call generate_from_yaml() with
        target_library_names=None in order to trigger the full generation
        """
        config_path = f"{test_resource_dir}/monorepo_with_common_protos.yaml"
        self.assertTrue(from_yaml(config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=config_path,
            current_generation_config_path=config_path,
            includes=None,
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=None,
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_monorepo_with_common_protos_with_includes_triggers_full_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of a monorepo with
        common protos.
        target_library_names is None even though includes is specified.
        """
        config_path = f"{test_resource_dir}/monorepo_with_common_protos.yaml"
        self.assertTrue(from_yaml(config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=config_path,
            current_generation_config_path=config_path,
            includes="iam,non-existent-library",
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=None,
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_monorepo_without_common_protos_does_not_trigger_full_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of a monorepo without
        common protos.
        generate() should call generate_from_yaml() with
        target_library_names=changed libraries which does not trigger the full
        generation.
        """
        config_path = f"{test_resource_dir}/monorepo_without_common_protos.yaml"
        self.assertTrue(from_yaml(config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=config_path,
            current_generation_config_path=config_path,
            includes=None,
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=[],
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_monorepo_with_changed_config_and_includes_trigger_union_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of a monorepo without
        common protos.
        target_library_names should be the union of changed libraries and
        include libraries, regardless the library exists or not.
        """
        current_config_path = f"{test_resource_dir}/monorepo_current.yaml"
        baseline_config_path = f"{test_resource_dir}/monorepo_baseline.yaml"
        self.assertTrue(from_yaml(current_config_path).is_monorepo())
        self.assertTrue(from_yaml(baseline_config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=baseline_config_path,
            current_generation_config_path=current_config_path,
            includes="cloudbuild,non-existent-library",
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=["asset", "cloudbuild", "non-existent-library"],
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_monorepo_with_changed_config_without_includes_trigger_selective_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of a monorepo without
        common protos.
        target_library_names should be the changed libraries if includes
        is not specified.
        """
        current_config_path = f"{test_resource_dir}/monorepo_current.yaml"
        baseline_config_path = f"{test_resource_dir}/monorepo_baseline.yaml"
        self.assertTrue(from_yaml(current_config_path).is_monorepo())
        self.assertTrue(from_yaml(baseline_config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=baseline_config_path,
            current_generation_config_path=current_config_path,
            includes=None,
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=["asset"],
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_monorepo_without_changed_config_with_includes_trigger_selective_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of a monorepo without
        common protos.
        target_library_names should be the changed libraries if includes
        is not specified.
        """
        config_path = f"{test_resource_dir}/monorepo_without_common_protos.yaml"
        self.assertTrue(from_yaml(config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=config_path,
            current_generation_config_path=config_path,
            includes="cloudbuild,non-existent-library",
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=["cloudbuild", "non-existent-library"],
        )

    @patch("library_generation.cli.entry_point.generate_from_yaml")
    @patch("library_generation.cli.entry_point.generate_pr_descriptions")
    def test_generate_monorepo_without_changed_config_without_includes_does_not_trigger_generation(
        self,
        generate_pr_descriptions,
        generate_from_yaml,
    ):
        """
        this tests confirms the behavior of generation of a monorepo without
        common protos.
        target_library_names should be the changed libraries if includes
        is not specified.
        """
        config_path = f"{test_resource_dir}/monorepo_without_common_protos.yaml"
        self.assertTrue(from_yaml(config_path).is_monorepo())
        # we call the implementation method directly since click
        # does special handling when a method is annotated with @main.command()
        generate_impl(
            baseline_generation_config_path=config_path,
            current_generation_config_path=config_path,
            includes=None,
            repository_path=".",
            api_definitions_path=".",
        )
        generate_from_yaml.assert_called_with(
            config=ANY,
            repository_path=ANY,
            api_definitions_path=ANY,
            target_library_names=[],
        )
