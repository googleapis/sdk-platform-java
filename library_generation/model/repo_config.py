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
from typing import Dict
from library_generation.model.library_config import LibraryConfig


class RepoConfig:
    """
    Class that represents a generated repository
    """
    def __init__(
        self,
        output_folder: str,
        libraries: Dict[str, LibraryConfig],
        versions_file: str
    ):
        """
        Init a RepoConfig object
        :param output_folder: the path to which the generated repo goes
        :param libraries: a mapping from library_path to LibraryConfig object
        :param versions_file: the path of versions.txt used in post-processing
        """
        self.output_folder = output_folder
        self.libraries = libraries
        self.versions_file = versions_file
