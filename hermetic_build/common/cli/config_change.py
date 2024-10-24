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

import click as click

from common.model.generation_config import from_yaml
from common.utils.generation_config_comparator import compare_config


@click.group(invoke_without_command=False)
@click.pass_context
@click.version_option(message="%(version)s")
def main(ctx):
    pass


@main.command()
@click.option(
    "--baseline-generation-config-path",
    required=True,
    type=str,
    help="""
    Absolute or relative path to a generation_config.yaml.
    This config file is used for commit history generation, not library
    generation.
    """,
)
@click.option(
    "--current-generation-config-path",
    required=True,
    type=str,
    help="""
    Absolute or relative path to a generation_config.yaml that contains the
    metadata about library generation.
    """,
)
def create(
    baseline_generation_config_path: str,
    current_generation_config_path: str,
) -> str:
    """
    Compare baseline generation config and current generation config and
    generate changed library names (a comma separated string) based on current
    generation config.
    """
    baseline_generation_config_path = os.path.abspath(baseline_generation_config_path)
    if not os.path.isfile(baseline_generation_config_path):
        raise FileNotFoundError(
            f"{baseline_generation_config_path} does not exist. "
            "A valid generation config has to be passed in as "
            "baseline_generation_config_path."
        )
    current_generation_config_path = os.path.abspath(current_generation_config_path)
    if not os.path.isfile(current_generation_config_path):
        raise FileNotFoundError(
            f"{current_generation_config_path} does not exist. "
            "A valid generation config has to be passed in as "
            "current_generation_config_path."
        )
    config_change = compare_config(
        baseline_config=from_yaml(baseline_generation_config_path),
        current_config=from_yaml(current_generation_config_path),
    )

    return ",".join(config_change.get_changed_libraries())


if __name__ == "__main__":
    main()
