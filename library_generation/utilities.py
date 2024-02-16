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
import re
from pathlib import Path
from lxml import etree
from library_generation.model.bom_config import BomConfig
from library_generation.model.generation_config import GenerationConfig
from library_generation.model.library_config import LibraryConfig
from typing import List
from jinja2 import Environment, FileSystemLoader

from library_generation.model.repo_config import RepoConfig

script_dir = os.path.dirname(os.path.realpath(__file__))
jinja_env = Environment(loader=FileSystemLoader(f"{script_dir}/templates"))
project_tag = "{http://maven.apache.org/POM/4.0.0}"
group_id_tag = "groupId"
artifact_tag = "artifactId"
version_tag = "version"


def __render(template_name: str, output_name: str, **kwargs):
    template = jinja_env.get_template(template_name)
    t = template.stream(kwargs)
    directory = os.path.dirname(output_name)
    if not os.path.isdir(directory):
        os.makedirs(directory)
    t.dump(str(output_name))


def __search_for_java_modules(
    repository_path: str,
) -> List[str]:
    repo = Path(repository_path).resolve()
    modules = []
    for sub_dir in repo.iterdir():
        if sub_dir.is_dir() and sub_dir.name.startswith("java-"):
            modules.append(sub_dir.name)
    return sorted(modules)


def __search_for_bom_artifact(
    repository_path: str,
) -> List[BomConfig]:
    repo = Path(repository_path).resolve()
    module_exclusions = ["gapic-libraries-bom"]
    group_id_inclusions = [
        "com.google.cloud",
        "com.google.analytics",
        "com.google.area120",
    ]
    bom_configs = []
    for module in repo.iterdir():
        if module.is_file() or module.name in module_exclusions:
            continue
        for sub_module in module.iterdir():
            if sub_module.is_dir() and sub_module.name.endswith("-bom"):
                root = etree.parse(f"{sub_module}/pom.xml").getroot()
                group_id = root.find(f"{project_tag}{group_id_tag}").text
                if group_id not in group_id_inclusions:
                    continue
                artifact_id = root.find(f"{project_tag}{artifact_tag}").text
                version = root.find(f"{project_tag}{version_tag}").text
                index = artifact_id.rfind("-")
                version_annotation = artifact_id[:index]
                bom_configs.append(
                    BomConfig(
                        group_id=group_id,
                        artifact_id=artifact_id,
                        version=version,
                        version_annotation=version_annotation,
                    )
                )
    # handle edge case: java-grafeas
    bom_configs += __handle_special_bom(
        repository_path=repository_path,
        module="java-grafeas",
        group_id="io.grafeas",
        artifact_id="grafeas",
    )
    # handle edge case: java-dns
    bom_configs += __handle_special_bom(
        repository_path=repository_path,
        module="java-dns",
        group_id="com.google.cloud",
        artifact_id="google-cloud-dns",
    )
    # handle edge case: java-notification
    bom_configs += __handle_special_bom(
        repository_path=repository_path,
        module="java-notification",
        group_id="com.google.cloud",
        artifact_id="google-cloud-notification",
    )

    return sorted(bom_configs)


def __handle_special_bom(
    repository_path: str,
    module: str,
    group_id: str,
    artifact_id: str,
) -> List[BomConfig]:
    pom = f"{repository_path}/{module}/pom.xml"
    if not Path(pom).exists():
        return []
    root = etree.parse(pom).getroot()
    version = root.find(f"{project_tag}{version_tag}").text
    return [
        BomConfig(
            group_id=group_id,
            artifact_id=artifact_id,
            version=version,
            version_annotation=artifact_id,
            is_import=False,
        )
    ]


def create_argument(arg_key: str, arg_container: object) -> List[str]:
    """
    Generates a list of two elements [argument, value], or returns
    an empty array if arg_val is None
    """
    arg_val = getattr(arg_container, arg_key, None)
    if arg_val is not None:
        return [f"--{arg_key}", f"{arg_val}"]
    return []


def get_library_name(
    library: LibraryConfig,
) -> str:
    """
    Return the library name of a given LibraryConfig object
    :param library: an object of LibraryConfig
    :return: the library name
    """
    return library.library_name if library.library_name else library.api_shortname


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


def remove_version_from(proto_path: str) -> str:
    """
    Remove the version of a proto_path
    :param proto_path: versioned proto_path
    :return: the proto_path without version
    """
    version_pattern = "^v[1-9]"
    index = proto_path.rfind("/")
    version = proto_path[index + 1 :]
    if re.match(version_pattern, version):
        return proto_path[:index]
    return proto_path


def check_monorepo(config: GenerationConfig) -> bool:
    """
    Check whether to generate a monorepo according to the
    generation config.
    :param config: the generation configuration
    :return: True if it's to generate a monorepo
    """
    return len(config.libraries) > 1


def prepare_repo(
    gen_config: GenerationConfig,
    library_config: List[LibraryConfig],
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
    is_monorepo = check_monorepo(gen_config)
    libraries = {}
    for library in library_config:
        library_name = (
            f"{language}-{library.library_name}"
            if library.library_name
            else f"{language}-{library.api_shortname}"
        )
        library_path = f"{repo_path}/{library_name}" if is_monorepo else f"{repo_path}"
        # use absolute path because docker requires absolute path
        # in volume name.
        absolute_library_path = str(Path(library_path).resolve())
        if absolute_library_path in libraries:
            # check whether the java_library is unique among all libraries
            # because two libraries should not go to the same destination.
            raise ValueError(f"{absolute_library_path} already exists.")
        libraries[absolute_library_path] = library
        # remove existing .repo-metadata.json
        json_name = ".repo-metadata.json"
        if os.path.exists(f"{absolute_library_path}/{json_name}"):
            os.remove(f"{absolute_library_path}/{json_name}")
    versions_file = f"{repo_path}/versions.txt"
    if not Path(versions_file).exists():
        raise FileNotFoundError(f"{versions_file} is not found.")

    return RepoConfig(
        output_folder=output_folder,
        libraries=libraries,
        versions_file=str(Path(versions_file).resolve()),
    )


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
    print('pull_api_definition 1')
    googleapis_commitish = config.googleapis_commitish
    if library.googleapis_commitish:
        googleapis_commitish = library.googleapis_commitish
        print(f"using library-specific googleapis commitish: {googleapis_commitish}")
    else:
        print(f"using common googleapis_commitish: {config.googleapis_commitish}")

    if googleapis_commitish != config.googleapis_commitish:
        print("removing existing APIs definition")
        shutil.rmtree(f"{output_folder}/google", ignore_errors=True)
        shutil.rmtree(f"{output_folder}/grafeas", ignore_errors=True)

    if not (
        os.path.exists(f"{output_folder}/google")
        and os.path.exists(f"{output_folder}/grafeas")
    ):
        print("downloading googleapis")
        sh_util(
            f"download_googleapis_files_and_folders {output_folder} {googleapis_commitish}"
        )


def generate_prerequisite_files(
    config: GenerationConfig,
    library: LibraryConfig,
    proto_path: str,
    transport: str,
    library_path: str,
    language: str = "java",
    is_monorepo: bool = True,
) -> None:
    """
    Generate prerequisite files for a library.

    Note that the version, if any, in the proto_path will be removed.
    :param config: a GenerationConfig object representing a parsed configuration
    yaml
    :param library: the library configuration
    :param proto_path: the proto path
    :param transport: transport supported by the library
    :param library_path: the path to which the generated file goes
    :param language: programming language of the library
    :param is_monorepo: whether the library is in a monorepo
    :return: None
    """
    cloud_prefix = "cloud-" if library.cloud_api else ""
    library_name = get_library_name(library)
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
        "distribution_name": distribution_name,
        "api_id": api_id,
        "library_type": library.library_type,
        "requires_billing": library.requires_billing,
    }

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

    # generate .repo-meta.json
    json_file = ".repo-metadata.json"
    # .repo-metadata.json is removed before generating the first version of
    # a library. This check ensures no duplicated generation.
    if not os.path.exists(f"{library_path}/{json_file}"):
        with open(f"{library_path}/{json_file}", "w") as fp:
            json.dump(repo_metadata, fp, indent=2)

    # generate .OwlBot.yaml
    yaml_file = ".OwlBot.yaml"
    if not os.path.exists(f"{library_path}/{yaml_file}"):
        __render(
            template_name="owlbot.yaml.monorepo.j2",
            output_name=f"{library_path}/{yaml_file}",
            artifact_name=distribution_name_short,
            proto_path=remove_version_from(proto_path),
            module_name=repo_metadata["repo_short"],
            api_shortname=library.api_shortname,
        )

    # generate owlbot.py
    py_file = "owlbot.py"
    if not os.path.exists(f"{library_path}/{py_file}"):
        __render(
            template_name="owlbot.py.j2",
            output_name=f"{library_path}/{py_file}",
            should_include_templates=True,
            template_excludes=config.template_excludes,
        )


def repo_level_post_process(
    repository_path: str,
    versions_file: str,
) -> None:
    """
    Perform repository level post-processing
    :param repository_path: the path of the repository
    :param versions_file: the versions_txt contains version of modules
    :return: None
    """
    print("Regenerating root pom.xml")
    modules = __search_for_java_modules(repository_path)
    __render(
        template_name="root-pom.xml.j2",
        output_name=f"{repository_path}/pom.xml",
        modules=modules,
    )
    print("Regenerating gapic-libraries-bom")
    bom_configs = __search_for_bom_artifact(repository_path)
    monorepo_version = get_version_from(
        versions_file=versions_file,
        artifact_id="google-cloud-java",
    )
    __render(
        template_name="gapic-libraries-bom.xml.j2",
        output_name=f"{repository_path}/gapic-libraries-bom/pom.xml",
        monorepo_version=monorepo_version,
        bom_configs=bom_configs,
    )


def get_version_from(
    versions_file: str, artifact_id: str, is_released: bool = False
) -> str:
    """
    Get version of a given artifact from versions.txt
    :param versions_file: the path of version.txt
    :param artifact_id: the artifact id
    :param is_released: whether returns the released or current version
    :return: the version of the artifact
    """
    index = 1 if is_released else 2
    with open(versions_file, "r") as f:
        for line in f.readlines():
            if artifact_id in line:
                return line.split(":")[index].strip()
