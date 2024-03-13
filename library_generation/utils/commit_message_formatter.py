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
import re
from typing import List
from typing import Dict
from git import Commit


def format_commit_message(commits: Dict[Commit, str], is_monorepo: bool) -> List[str]:
    """
    Format commit messages. Add library_name to conventional commit messages if
    is_monorepo is True; otherwise no op.

    :param commits: a mapping from commit to library_name.
    :param is_monorepo: whether it's monorepo or not.
    :return: formatted commit messages.
    """
    all_commits = []
    # please see go/java-client-releasing#conventional-commit-messages
    # for conventional commit.
    type_regex = re.compile(r"(feat|fix|docs|deps|test|samples|chore)!?:.*")
    for commit, library_name in commits.items():
        # a commit message may contain multiple lines, we need to
        # add library_name for each line.
        messages = []
        for message_line in commit.message.split("\n"):
            # add library name to a conventional commit message;
            # otherwise no op.
            if type_regex.search(message_line):
                commit_type, _, summary = message_line.partition(":")
                formatted_message = (
                    f"{commit_type}: [{library_name}]{str(summary).rstrip()}"
                    if is_monorepo
                    else f"{commit_type}:{str(summary).rstrip()}"
                )
                messages.append(formatted_message)
            else:
                messages.append(message_line)
        all_commits.extend(wrap_nested_commit(messages))
    return all_commits


def wrap_nested_commit(messages: List[str]) -> List[str]:
    """
    Wrap message between `BEGIN_NESTED_COMMIT` and `BEGIN_NESTED_COMMIT`.

    :param messages: a (multi-line) commit message, one line per item.
    :return: wrapped messages.
    """
    result = ["BEGIN_NESTED_COMMIT"]
    result.extend(messages)
    result.append("END_NESTED_COMMIT")
    return result


def wrap_override_commit(messages: List[str]) -> List[str]:
    """
    Wrap message between `BEGIN_COMMIT_OVERRIDE` and `END_COMMIT_OVERRIDE`.

    :param messages: a (multi-line) commit message, one line per item.
    :return: wrapped messages.
    """
    result = ["BEGIN_COMMIT_OVERRIDE"]
    result.extend(messages)
    result.append("END_COMMIT_OVERRIDE")
    return result
