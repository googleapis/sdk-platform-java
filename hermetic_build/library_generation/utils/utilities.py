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
import json
import os
import re
import sys
import subprocess
from pathlib import Path
from typing import Any, Optional

from common.model.generation_config import GenerationConfig
from common.model.library_config import LibraryConfig
from library_generation.model.repo_config import RepoConfig
from library_generation.utils.file_render import render, render_to_str
from library_generation.utils.proto_path_utils import remove_version_from

script_dir = os.path.dirname(os.path.realpath(__file__))
SDK_PLATFORM_JAVA = "googleapis/sdk-platform-java"


def create_argument(arg_key: str, arg_container: object) -> list[str]:
    """
    Generates a list of two elements [argument, value], or returns
    an empty array if arg_val is None
    """
    arg_val = getattr(arg_container, arg_key, None)
    if arg_val is not None:
        return [f"--{arg_key}", f"{arg_val}"]
    return []


def run_process_and_print_output(
    arguments: list[str] | str, job_name: str = "Job", exit_on_fail=True, **kwargs
) -> Any:
    """
    Runs a process with the given "arguments" list and prints its output.
    If the process fails, then the whole program exits
    :param arguments: list of strings or string containing the arguments
    :param job_name: optional job name to be printed
    :param exit_on_fail: True if the main program should exit when the
    subprocess exits with non-zero. Used for testing.
    :param kwargs: passed to the underlying subprocess.run() call
    """
    # split "arguments" if passed as a single string
    if isinstance(arguments, str):
        arguments = arguments.split(" ")
    if "stderr" not in kwargs:
        kwargs["stderr"] = subprocess.STDOUT
    proc_info = subprocess.run(arguments, stdout=subprocess.PIPE, **kwargs)
    print(proc_info.stdout.decode(), end="", flush=True)
    print(
        f"{job_name} {'finished successfully' if proc_info.returncode == 0 else 'failed'}"
    )
    if exit_on_fail and proc_info.returncode != 0:
        sys.exit(1)
    return proc_info


def run_process_and_get_output_string(
    arguments: list[str] | str, job_name: str = "Job", exit_on_fail=True, **kwargs
) -> Any:
    """
    Wrapper of run_process_and_print_output() that returns the merged
    stdout and stderr in a single string without its trailing newline char.
    """
    return run_process_and_print_output(
        arguments, job_name, exit_on_fail, stderr=subprocess.PIPE, **kwargs
    ).stdout.decode()[:-1]


def sh_util(statement: str, **kwargs) -> str:
    """
    Calls a function defined in library_generation/utils/utilities.sh
    """
    if "stdout" not in kwargs:
        kwargs["stdout"] = subprocess.PIPE
    if "stderr" not in kwargs:
        kwargs["stderr"] = subprocess.PIPE
    output = ""
    with subprocess.Popen(
        [
            "bash",
            "-exc",
            f"source {script_dir}/utilities.sh && {statement}",
        ],
        **kwargs,
    ) as proc:
        print("command stderr:")
        for line in proc.stderr:
            print(line.decode(), end="", flush=True)
        print("command stdout:")
        for line in proc.stdout:
            print(line.decode(), end="", flush=True)
            output += line.decode()
        proc.wait()
        if proc.returncode != 0:
            raise RuntimeError(
                f"function {statement} failed with exit code {proc.returncode}"
            )
    # captured stdout may contain a newline at the end, we remove it
    if len(output) > 0 and output[-1] == "\n":
        output = output[:-1]
    return output


def eprint(*args, **kwargs):
    """
    prints to stderr
    """
    print(*args, file=sys.stderr, **kwargs)


def prepare_repo(
    gen_config: GenerationConfig,
    library_config: list[LibraryConfig],
    repo_path: str,
    language: str = "java",
) -> RepoConfig:
    """
    Gather information of the generated repository.

    :param gen_config: a GenerationConfig object representing a parsed
    configuration yaml
    :param library_config: a LibraryConfig object contained inside config,
    passed here for convenience and to prevent all libraries to be processed
    :param repo_path: the path to which the generated repository goes
    :param language: programming language of the library
    :return: a RepoConfig object contained repository information
    :raise FileNotFoundError if there's no versions.txt in repo_path
    :raise ValueError if two libraries have the same library_name
    """
    output_folder = sh_util("get_output_folder")
    print(f"output_folder: {output_folder}")
    os.makedirs(output_folder, exist_ok=True)
    libraries = {}
    for library in library_config:
        library_name = f"{language}-{library.get_library_name()}"
        library_path = (
            f"{repo_path}/{library_name}"
            if gen_config.is_monorepo()
            else f"{repo_path}"
        )
        # use absolute path because docker requires absolute path
        # in volume name.
        absolute_library_path = str(Path(library_path).resolve())
        libraries[absolute_library_path] = library
        # remove existing .repo-metadata.json
        json_name = ".repo-metadata.json"
        if os.path.exists(f"{absolute_library_path}/{json_name}"):
            os.remove(f"{absolute_library_path}/{json_name}")
    versions_file = Path(repo_path) / "versions.txt"
    if not versions_file.exists():
        versions_file.touch()
        print(f"Created empty versions file: {versions_file}")
    return RepoConfig(
        output_folder=output_folder,
        libraries=libraries,
        versions_file=str(versions_file),
    )


def generate_postprocessing_prerequisite_files(
    config: GenerationConfig,
    library: LibraryConfig,
    proto_path: str,
    transport: str,
    library_path: str,
    language: str = "java",
) -> None:
    """
    Generates the postprocessing prerequisite files for a library.

    :param config: a GenerationConfig object representing a parsed configuration
    yaml
    :param library: the library configuration
    :param proto_path: the path from the root of googleapis to the location of the service
    protos. If the path contains a version, it will be removed
    :param transport: transport supported by the library
    :param library_path: the path to which the generated file goes
    :param language: programming language of the library
    :return: None
    """
    library_name = library.get_library_name()
    artifact_id = library.get_artifact_id()
    if config.contains_common_protos():
        repo = SDK_PLATFORM_JAVA
    elif config.is_monorepo():
        repo = "googleapis/google-cloud-java"
    else:
        repo = f"googleapis/{language}-{library_name}"
    api_id = (
        library.api_id if library.api_id else f"{library.api_shortname}.googleapis.com"
    )
    client_documentation = (
        library.client_documentation
        if library.client_documentation
        else f"https://cloud.google.com/{language}/docs/reference/{artifact_id}/latest/overview"
    )

    # The mapping is needed because transport in .repo-metadata.json
    # is one of grpc, http and both,
    if transport == "grpc":
        converted_transport = "grpc"
    elif transport == "rest":
        converted_transport = "http"
    else:
        converted_transport = "both"

    repo_metadata = {
        "api_shortname": library.api_shortname,
        "name_pretty": library.name_pretty,
        "product_documentation": library.product_documentation,
        "api_description": library.api_description,
        "client_documentation": client_documentation,
        "release_level": library.release_level,
        "transport": converted_transport,
        "language": language,
        "repo": repo,
        "repo_short": f"{language}-{library_name}",
        "distribution_name": library.get_maven_coordinate(),
        "api_id": api_id,
        "library_type": library.library_type,
        "requires_billing": library.requires_billing,
    }

    # this removal is for java-common-protos and java-iam in
    # sdk-platform-java
    if repo == SDK_PLATFORM_JAVA:
        repo_metadata.pop("api_id")

    if library.api_reference:
        repo_metadata["api_reference"] = library.api_reference
    if library.codeowner_team:
        repo_metadata["codeowner_team"] = library.codeowner_team
    if library.excluded_dependencies:
        repo_metadata["excluded_dependencies"] = library.excluded_dependencies
    if library.excluded_poms:
        repo_metadata["excluded_poms"] = library.excluded_poms
    if library.issue_tracker:
        repo_metadata["issue_tracker"] = library.issue_tracker
    if library.rest_documentation:
        repo_metadata["rest_documentation"] = library.rest_documentation
    if library.rpc_documentation:
        repo_metadata["rpc_documentation"] = library.rpc_documentation
    if library.extra_versioned_modules:
        repo_metadata["extra_versioned_modules"] = library.extra_versioned_modules
    if library.recommended_package:
        repo_metadata["recommended_package"] = library.recommended_package
    if library.min_java_version:
        repo_metadata["min_java_version"] = library.min_java_version

    # generate .repo-meta.json
    json_file = ".repo-metadata.json"
    # .repo-metadata.json is removed before generating the first version of
    # a library. This check ensures no duplicated generation.
    if not os.path.exists(f"{library_path}/{json_file}"):
        with open(f"{library_path}/{json_file}", "w") as fp:
            json.dump(repo_metadata, fp, indent=2)

    # generate .OwlBot-hermetic.yaml
    owlbot_yaml_file = ".OwlBot-hermetic.yaml"
    path_to_owlbot_yaml_file = (
        f"{library_path}/{owlbot_yaml_file}"
        if config.is_monorepo()
        else f"{library_path}/.github/{owlbot_yaml_file}"
    )
    if not os.path.exists(path_to_owlbot_yaml_file):
        # 1. Render the base content
        generated_content = render_to_str(
            template_name="owlbot.yaml.monorepo.j2",
            artifact_id=artifact_id,
            proto_path=remove_version_from(proto_path),
            module_name=repo_metadata["repo_short"],
            api_shortname=library.api_shortname,
            owlbot_yaml=library.owlbot_yaml,
        )
        # 2. Apply additions and removals
        modified_content = apply_owlbot_config(generated_content, library.owlbot_yaml)

        # 3. Write the modified content back to the file
        directory = os.path.dirname(path_to_owlbot_yaml_file)
        os.makedirs(directory, exist_ok=True)
        with open(path_to_owlbot_yaml_file, "w") as f:
            f.write(modified_content)

    # generate owlbot.py
    py_file = "owlbot.py"
    template_excludes = [
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
    ]
    if not os.path.exists(f"{library_path}/{py_file}"):
        render(
            template_name="owlbot.py.j2",
            output_name=f"{library_path}/{py_file}",
            should_include_templates=True,
            template_excludes=template_excludes,
        )


def apply_owlbot_config(
    content: str, owlbot_config: Optional["OwlbotYamlConfig"]
) -> str:
    """
    Applies the addition and removal configurations to the generated owlbot.yaml content.

    Args:
        content: The generated owlbot.yaml content as a string.
        owlbot_config: The OwlbotYamlConfig object containing the addition and removal rules.

    Returns:
        The modified owlbot.yaml content.
    """
    if not owlbot_config:
        return content

    modified_content = content

    if owlbot_config.removals:
        if owlbot_config.removals.deep_remove_regex:
            for regex_pattern in owlbot_config.removals.deep_remove_regex:
                modified_content = re.sub(
                    r'- "' + regex_pattern + r'"[^\S\n]*\n', "", modified_content
                )
        if owlbot_config.removals.deep_preserve_regex:
            for regex_pattern in owlbot_config.removals.deep_preserve_regex:
                modified_content = re.sub(
                    r'- "' + regex_pattern + r'"[^\S\n]*\n', "", modified_content
                )
        if owlbot_config.removals.deep_copy_regex:
            for item in owlbot_config.removals.deep_copy_regex:
                # Construct the regex to match both source and dest lines
                source_regex = r'- source: "' + re.escape(item.source) + r'"\n'
                dest_regex = r'\s+dest: "' + re.escape(item.dest) + r'"\n'
                removal_regex = rf"{source_regex}{dest_regex}"
                modified_content = re.sub(
                    removal_regex, "", modified_content, flags=re.MULTILINE
                )
    if owlbot_config.additions:
        if owlbot_config.additions.deep_remove_regex:
            modified_content = _add_items_after_key(
                content=modified_content,
                key="deep-remove-regex:",
                items=owlbot_config.additions.deep_remove_regex,
                quote=True,
            )
        if owlbot_config.additions.deep_preserve_regex:
            modified_content = _add_items_after_key(
                content=modified_content,
                key="deep-preserve-regex:",
                items=owlbot_config.additions.deep_preserve_regex,
                quote=True,
            )
        if owlbot_config.additions.deep_copy_regex:
            modified_content = _add_deep_copy_regex_items(
                content=modified_content,
                key="deep-copy-regex:",
                items=owlbot_config.additions.deep_copy_regex,
            )

    return modified_content


def _add_items_after_key(
    content: str, key: str, items: list[str], quote: bool = False
) -> str:
    """
    Adds items after a specified key in the content, following the template pattern.

    Args:
        content: The generated owlbot.yaml content.
        key: The key after which to add the items (e.g., "deep-remove-regex:").
        items: The list of items to add.
        quote: Whether to enclose the item in double quotes.

    Returns:
        The modified content.
    """

    def format_item(item: str) -> str:
        if quote:
            return f'- "{item}"\n'
        else:
            return f"- {item}\n"

    pattern = re.compile(rf"^{re.escape(key)}", re.MULTILINE)
    match = pattern.search(content)

    if match:
        insert_position = match.end() + 1  # Insert after the newline
        new_items = "".join(format_item(item) for item in items)
        return content[:insert_position] + new_items + content[insert_position:]
    else:
        return content  # Key not found, return original content


def _add_deep_copy_regex_items(
    content: str, key: str, items: list["DeepCopyRegexItem"]
) -> str:
    """
    Adds deep_copy_regex items after a specified key in the content.

    Args:
        content: The generated owlbot.yaml content.
        key: The key after which to add the items (e.g., "deep-copy-regex:").
        items: The list of DeepCopyRegexItem objects to add.

    Returns:
        The modified content.
    """

    pattern = re.compile(rf"^{re.escape(key)}", re.MULTILINE)
    match = pattern.search(content)

    if match:
        insert_position = match.end() + 1  # Insert after the newline
        new_items = "".join(
            f'- source: "{item.source}"\n  dest: "{item.dest}"\n' for item in items
        )
        return content[:insert_position] + new_items + content[insert_position:]
    else:
        return content
