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
import sys
import subprocess
import os
import shutil
from collections.abc import Sequence
import re
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.library_config import LibraryConfig
from model.generation_config import from_yaml
from typing import List
from jinja2 import Environment, FileSystemLoader

script_dir = os.path.dirname(os.path.realpath(__file__))
jinja_env = Environment(loader=FileSystemLoader(f"{script_dir}/templates"))


def __render(template_name: str, output_name: str, **kwargs):
    template = jinja_env.get_template(template_name)
    t = template.stream(kwargs)
    directory = os.path.dirname(output_name)
    if not os.path.isdir(directory):
        os.makedirs(directory)
    t.dump(str(output_name))


def create_argument(arg_key: str, arg_container: object) -> List[str]:
    """
    Generates a list of two elements [argument, value], or returns
    an empty array if arg_val is None
    """
    arg_val = getattr(arg_container, arg_key, None)
    if arg_val is not None:
        return [f"--{arg_key}", f"{arg_val}"]
    return []


def get_configuration_yaml_library_api_shortnames(
    generation_config_yaml: str,
) -> List[str]:
    """
    For a given configuration yaml path, it returns a space-separated list of
    the api_shortnames contained in such configuration_yaml
    """
    config = from_yaml(generation_config_yaml)
    result = ""
    for library in config.libraries:
        result += f"{library.api_shortname} "
    return result[:-1]


def get_configuration_yaml_destination_path(generation_config_yaml: str) -> str:
    """
    For a given configuration yaml path, it returns the destination_path
    entry at the root of the yaml
    """
    config = from_yaml(generation_config_yaml)
    return config.destination_path or ""


def run_process_and_print_output(arguments: List[str], job_name: str = "Job"):
    """
    Runs a process with the given "arguments" list and prints its output.
    If the process fails, then the whole program exits
    """
    # check_output() raises an exception if it exited with a nonzero code
    try:
        output = subprocess.check_output(arguments, stderr=subprocess.STDOUT)
        print(output.decode(), end="", flush=True)
        print(f"{job_name} finished successfully")
    except subprocess.CalledProcessError as ex:
        print(ex.output.decode(), end="", flush=True)
        print(f"{job_name} failed")
        sys.exit(1)


def sh_util(statement: str, **kwargs) -> str:
    """
    Calls a function defined in library_generation/utilities.sh
    """
    if "stdout" not in kwargs:
        kwargs["stdout"] = subprocess.PIPE
    if "stderr" not in kwargs:
        kwargs["stderr"] = subprocess.PIPE
    output = ""
    with subprocess.Popen(
        ["bash", "-exc", f"source {script_dir}/utilities.sh && {statement}"],
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


def delete_if_exists(path: str) -> None:
    """
    Deletes a file or folder if it exists.
    :param path: the path to the file or folder
    :return: None
    """
    if os.path.isfile(path):  # Check if it's a file
        os.remove(path)
        print(f"File deleted: {path}")
    elif os.path.isdir(path):  # Check if it's a directory
        shutil.rmtree(path)
        print(f"Folder deleted: {path}")
    else:
        print(f"Path does not exist: {path}")


def remove_version_from(proto_path: str) -> str:
    """
    Remove the version of a proto_path
    :param proto_path: versioned proto_path
    :return: the proto_path without version
    """
    index = proto_path.rfind("/")
    return proto_path[:index]


def check_monorepo(config: GenerationConfig) -> bool:
    """
    Check whether to generate a monorepo according to the
    generation config.
    :param config: the generation configuration
    :return: True if it's to generate a monorepo
    """
    return len(config.libraries) > 1


def pull_api_definition(
    config: GenerationConfig, library: LibraryConfig, output_folder: str
) -> None:
    """
    Pull APIs definition from googleapis/googleapis repository.
    To avoid duplicated pulling, only perform pulling if the library uses a
    different commitish than in generation config.
    :param config: a GenerationConfig object representing a parsed configuration
    yaml
    :param library: a LibraryConfig object contained inside config, passed here
    for convenience and to prevent all libraries to be processed
    :param output_folder: the folder to which APIs definition (proto files) goes
    :return: None
    """
    googleapis_commitish = config.googleapis_commitish
    if library.googleapis_commitish:
        googleapis_commitish = library.googleapis_commitish
        print(f"using library-specific googleapis commitish: {googleapis_commitish}")
    else:
        print(f"using common googleapis_commitish: {config.googleapis_commitish}")

    if googleapis_commitish != config.googleapis_commitish:
        print("removing existing APIs definition")
        delete_if_exists(f"{output_folder}/google")
        delete_if_exists(f"{output_folder}/grafeas")

    if not (
        os.path.exists(f"{output_folder}/google")
        and os.path.exists(f"{output_folder}/grafeas")
    ):
        print("downloading googleapis")
        sh_util(
            f"download_googleapis_files_and_folders {output_folder} {googleapis_commitish}"
        )


def generate_prerequisite_files(
    library: LibraryConfig,
    proto_path: str,
    transport: str,
    library_path: str,
    language: str = "java",
    is_monorepo: bool = True,
) -> None:
    """Generate .repo-metadata.json for a library
    :param library: the library configuration
    :param proto_path: the proto path without version
    :param transport: transport supported by the library
    :param library_path: the path to which the generated file goes
    :param language: programming language of the library
    :param is_monorepo: whether the library is in a monorepo
    :return: None
    """
    cloud_prefix = "cloud-" if library.cloud_api else ""
    library_name = (
        library.library_name if library.library_name else library.api_shortname
    )
    distribution_name = (
        library.distribution_name
        if library.distribution_name
        else f"{library.group_id}:google-{cloud_prefix}{library_name}"
    )
    distribution_name_short = re.split(r"[:/]", distribution_name)[-1]
    repo = (
        "googleapis/google-cloud-java" if is_monorepo else f"{language}-{library_name}"
    )
    api_id = (
        library.api_id if library.api_id else f"{library.api_shortname}.googleapis.com"
    )
    client_documentation = (
        library.client_documentation
        if library.client_documentation
        else f"https://cloud.google.com/{language}/docs/reference/{distribution_name_short}/latest/overview"
    )

    repo_metadata = {
        "api_shortname": library.api_shortname,
        "name_pretty": library.name_pretty,
        "product_documentation": library.product_documentation,
        "api_description": library.api_description,
        "client_documentation": client_documentation,
        "release_level": library.release_level,
        "transport": transport,
        "language": language,
        "repo": repo,
        "repo_short": f"{language}-{library_name}",
        "distribution_name": distribution_name,
        "api_id": api_id,
        "library_type": library.library_type.name,
    }

    if library.requires_billing:
        repo_metadata["requires_billing"] = True
    if library.rest_documentation:
        repo_metadata["rest_documentation"] = library.rest_documentation
    if library.rpc_documentation:
        repo_metadata["rpc_documentation"] = library.rpc_documentation

    # generate .repo-meta.json
    if not os.path.exists(f"{library_path}/.repo-meta.json"):
        with open(f"{library_path}/.repo-metadata.json", "w") as fp:
            json.dump(repo_metadata, fp, indent=2)

    # generate .OwlBot.yaml
    if not os.path.exists(f"{library_path}/.OwlBot.yaml"):
        __render(
            template_name="owlbot.yaml.monorepo.j2",
            output_name=f"{library_path}/.OwlBot.yaml",
            artifact_name=distribution_name_short,
            proto_path=proto_path,
            module_name=repo_metadata["repo_short"],
            api_shortname=library.api_shortname,
        )

    # generate owlbot.py
    if not os.path.exists(f"{library_path}/owlbot.py"):
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
        __render(
            template_name="owlbot.py.j2",
            output_name=f"{library_path}/owlbot.py",
            should_include_templates=True,
            template_excludes=template_excludes,
        )


def main(argv: Sequence[str]) -> None:
    if len(argv) < 1:
        raise ValueError(
            "Usage: python generate_composed_library_args.py function_name arg1...argN"
        )

    function_name = argv[1]
    arguments = argv[2:]
    try:
        function = getattr(sys.modules[__name__], function_name)
        print(function(*arguments))
    except AttributeError:
        print(f'function name "{function_name}" not found in utilities.py')
        sys.exit(1)


if __name__ == "__main__":
    main(sys.argv)
