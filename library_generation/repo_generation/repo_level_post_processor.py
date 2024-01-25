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
from pathlib import Path


def repo_level_post_process(
    repo_root_dir: Path,
    output_dir: Path,
    is_monorepo: bool = True,
) -> None:
    """
    Perform post-processing at the repository level.
    :param repo_root_dir: the path to the repository root
    :param output_dir: the output path of the post-processing
    :param is_monorepo: whether the repository is a monorepo or not
    """
    script_dir = "library_generation/repo-level-postprocess"

    print("Running post-processing at the repository level...")
    print("Regenerating root pom.xml")
    subprocess.check_call(
        [
            f"{script_dir}/generate_root_pom.sh",
            f"{output_dir}"
        ],
        cwd=repo_root_dir,
    )

    if is_monorepo:
        print("Regenerating the GAPIC BOM in the monorepo")
        subprocess.check_call(
            [
                f"{script_dir}/generate_gapic_bom.sh",
                f"{output_dir}"
            ],
            cwd=repo_root_dir,
        )
