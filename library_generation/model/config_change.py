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
from enum import Enum
from library_generation.model.library_config import LibraryConfig


class ChangeType(Enum):
    GOOGLEAPIS_COMMIT = 1
    REPO_LEVEL_CHANGE = 2
    LIBRARIES_ADDITION = 3
    # As of Mar. 2024, we decide not to produce this type of change because we
    # still need to manually remove the libray.
    # LIBRARIES_REMOVAL = 4
    LIBRARY_LEVEL_CHANGE = 5
    GAPIC_ADDITION = 6
    # As of Mar. 2024, we decide not to produce this type of change because we
    # still need to manually remove the libray.
    # GAPIC_REMOVAL = 7


class HashLibrary:
    """
    Data class to group a LibraryConfig object and its hash value together.
    """

    def __init__(self, hash_value: int, library: LibraryConfig):
        self.hash_value = hash_value
        self.library = library


class LibraryChange:
    def __init__(self, changed_param: str, latest_value: str, library_name: str = ""):
        self.changed_param = changed_param
        self.latest_value = latest_value
        self.library_name = library_name


class ConfigChange:
    def __init__(self, change_to_libraries: dict[ChangeType, list[LibraryChange]]):
        self.change_to_libraries = change_to_libraries

    def get_changed_libraries(self):
        pass


























