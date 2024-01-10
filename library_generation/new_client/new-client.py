# Copyright 2019 Google LLC
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
from pathlib import Path
import re
import subprocess
import sys

import click
import templates
from git import Repo
from client_inputs import parse
import shutil


@click.group(invoke_without_command=False)
@click.pass_context
@click.version_option(message="%(version)s")
def main(ctx):
    pass


@main.command()
@click.option(
    "--api_shortname",
    required=True,
    type=str,
    prompt="Service name? (e.g. automl)",
    help="Name for the new directory name and (default) artifact name"
)
@click.option(
    "--name-pretty",
    required=True,
    type=str,
    prompt="Pretty name? (e.g. 'Cloud AutoML')",
    help="The human-friendly name that appears in README.md"
)
@click.option(
    "--product-docs",
    required=True,
    type=str,
    prompt="Product Documentation URL",
    help="Documentation URL that appears in README.md"
)
@click.option(
    "--api-description",
    required=True,
    type=str,
    prompt="Description for README. The first sentence is prefixed by the "
           "pretty name",
    help="Description that appears in README.md"
)
@click.option(
    "--release-level",
    type=click.Choice(["stable", "preview"]),
    default="preview",
    show_default=True,
    help="A label that appears in repo-metadata.json. The first library "
         "generation is always 'preview'."
)
@click.option(
    "--transport",
    type=click.Choice(["grpc", "http", "both"]),
    default="grpc",
    show_default=True,
    help="A label that appears in repo-metadata.json"
)
@click.option("--language", type=str, default="java", show_default=True)
@click.option(
    "--distribution-name",
    type=str,
    help="Maven coordinates of the generated library. By default it's "
         "com.google.cloud:google-cloud-<api_shortname>"
)
@click.option(
    "--api-id",
    type=str,
    help="The value of the apiid parameter used in README.md It has link to "
         "https://console.cloud.google.com/flows/enableapi?apiid=<api_id>"
)
@click.option(
    "--requires-billing",
    type=bool,
    default=True,
    show_default=True,
    help="Based on this value, README.md explains whether billing setup is "
         "needed or not."
)
@click.option(
    "--proto-path",
    required=True,
    type=str,
    default=None,
    help="Path to proto file from the root of the googleapis repository to the"
         "directory that contains the proto files (without the version)."
         "For example, to generate the library for 'google/maps/routing/v2', "
         "then you specify this value as 'google/maps/routing'"
)
@click.option(
    "--cloud-api",
    type=bool,
    default=True,
    show_default=True,
    help="If true, the artifact ID of the library is 'google-cloud-'; "
         "otherwise 'google-'"
)
@click.option(
    "--group-id",
    type=str,
    default="com.google.cloud",
    show_default=True,
    help="The group ID of the artifact when distribution name is not set"
)
@click.option(
    "--library-type",
    type=str,
    default="GAPIC_AUTO",
    show_default=True,
    help="A label that appear in repo-metadata.json to tell how the library is "
         "maintained or generated"
)
@click.option(
    "--rest-docs",
    type=str,
    help="If it exists, link to the REST Documentation for a service"
)
@click.option(
    "--rpc-docs",
    type=str,
    help="If it exists, link to the RPC Documentation for a service"
)
@click.option(
    "--versions-file",
    type=str,
    default="output/versions.txt",
    help="A text file contains versions of modules"
)
@click.option(
    "--split-repo",
    type=bool,
    default=False,
    help="Whether generating a library into a split repository"
)
def generate(
    api_shortname,
    name_pretty,
    product_docs,
    api_description,
    release_level,
    distribution_name,
    api_id,
    requires_billing,
    transport,
    language,
    proto_path,
    cloud_api,
    group_id,
    library_type,
    rest_docs,
    rpc_docs,
    versions_file,
    split_repo,
):
    cloud_prefix = "cloud-" if cloud_api else ""

    destination_path = f"{language}-{api_shortname}"

    if distribution_name is None:
        distribution_name = f"{group_id}:google-{cloud_prefix}{api_shortname}"

    distribution_name_short = re.split(r"[:\/]", distribution_name)[-1]

    if api_id is None:
        api_id = f"{api_shortname}.googleapis.com"

    if not product_docs.startswith("https"):
        sys.exit("product_docs must starts with 'https://'")

    client_documentation = (
        f"https://cloud.google.com/{language}/docs/reference/{distribution_name_short}/latest/overview"
    )

    if api_shortname == "":
        sys.exit("api_shortname is empty")

    repo = "googleapis/google-cloud-java"
    if split_repo:
        repo = f"{language}-{api_shortname}"

    repo_metadata = {
        "api_shortname": api_shortname,
        "name_pretty": name_pretty,
        "product_documentation": product_docs,
        "api_description": api_description,
        "client_documentation": client_documentation,
        "release_level": release_level,
        "transport": transport,
        "language": language,
        "repo": f"{repo}",
        "repo_short": f"{language}-{api_shortname}",
        "distribution_name": distribution_name,
        "api_id": api_id,
        "library_type": library_type,
    }
    if requires_billing:
        repo_metadata["requires_billing"] = True

    if rest_docs:
        repo_metadata["rest_documentation"] = rest_docs

    if rpc_docs:
        repo_metadata["rpc_documentation"] = rpc_docs
    # Initialize workdir
    workdir = Path(f"{sys.path[0]}/../../output/java-{api_shortname}").resolve()
    if os.path.isdir(workdir):
        sys.exit(
            "Couldn't create the module because "
            f"the module {workdir} already exists. In Java client library "
            "generation, a new API version of an existing module does not "
            "require new-client.py invocation. "
            "See go/yoshi-java-new-client#adding-a-new-service-version-by-owlbot."
        )
    print(f"Creating a new module {workdir}")
    os.makedirs(workdir, exist_ok=False)
    # write .repo-metadata.json file
    with open(workdir / ".repo-metadata.json", "w") as fp:
        json.dump(repo_metadata, fp, indent=2)

    # create owlbot.py
    templates.render(
        template_name="owlbot.py.j2",
        output_name=str(workdir / "owlbot.py"),
        should_include_templates=True,
        template_excludes=[],
    )

    # In monorepo, .OwlBot.yaml needs to be in the directory of the module.
    owlbot_yaml_location_from_module = ".OwlBot.yaml"
    # create owlbot config
    templates.render(
        template_name="owlbot.yaml.monorepo.j2",
        output_name=str(workdir / owlbot_yaml_location_from_module),
        artifact_name=distribution_name_short,
        proto_path=proto_path,
        module_name=f"java-{api_shortname}",
        api_shortname=api_shortname
    )

    print("Cloning googleapis...")
    output_dir = Path(f"{sys.path[0]}/../../output").resolve()
    __clone_googleapis(output_dir)
    # Find a versioned directory within proto_path
    # We only need to generate one version of the library as OwlBot
    # will copy other versions from googleapis-gen.
    version = __find_version(
        Path(f"{sys.path[0]}/../../output/{proto_path}").resolve()
    )
    versioned_proto_path = f"{proto_path}/{version}"
    print(f"Generating from {versioned_proto_path}...")
    # parse BUILD.bazel in proto_path
    client_input = parse(
        build_path=Path(f"{sys.path[0]}/../../output/{versioned_proto_path}")
        .resolve(),
        versioned_path=versioned_proto_path,
    )
    repo_root_dir = Path(f"{sys.path[0]}/../../").resolve()
    versions = Path(f"{versions_file}").resolve()
    # run generate_library.sh
    subprocess.check_call([
        "library_generation/generate_library.sh",
        "-p",
        versioned_proto_path,
        "-d",
        destination_path,
        "--gapic_generator_version",
        "2.31.0",
        "--protobuf_version",
        "23.2",
        "--proto_only",
        client_input.proto_only,
        "--gapic_additional_protos",
        client_input.additional_protos,
        "--transport",
        client_input.transport,
        "--rest_numeric_enums",
        client_input.rest_numeric_enum,
        "--gapic_yaml",
        client_input.gapic_yaml,
        "--service_config",
        client_input.service_config,
        "--service_yaml",
        client_input.service_yaml,
        "--include_samples",
        client_input.include_samples,
        "--versions_file",
        f"{versions}"],
        cwd=repo_root_dir
    )

    # Move generated module to repo root.
    __move_modules(
        source=output_dir,
        dest=repo_root_dir,
        name_prefix="java-"
    )

    # Repo level post process
    script_dir = "library_generation/repo-level-postprocess"
    print("Remove irrelevant files from templates")
    subprocess.check_call(
        [
            "bash",
            f"{script_dir}/update_owlbot_postprocessor_config.sh"
         ],
        cwd=repo_root_dir
    )

    print("Deleting non generated samples")
    subprocess.check_call(
        [
            "bash",
            f"{script_dir}/delete_non_generated_samples.sh"
        ],
        cwd=repo_root_dir
    )

    print("Regenerating root pom.xml")
    subprocess.check_call(
        [
            f"{script_dir}/generate_root_pom.sh",
            f"{output_dir}"
        ],
        cwd=repo_root_dir,
    )

    print("Consolidating configurations")
    subprocess.check_call(
        [
            f"{script_dir}/consolidate_config.sh",
            f"{output_dir}"
        ],
        cwd=repo_root_dir,
    )

    if not split_repo:
        print("Regenerating the GAPIC BOM")
        subprocess.check_call(
            [
                f"{script_dir}/generate_gapic_bom.sh",
                f"{output_dir}"
            ],
            cwd=repo_root_dir,
        )

        print("Setting parent poms")
        subprocess.check_call(
            [
                "bash",
                f"{script_dir}/set_parent_pom.sh",
            ],
            cwd=repo_root_dir,
        )

    print("Applying the versions")
    subprocess.check_call(
        [
            "bash",
            f"{script_dir}/apply_current_versions.sh",
        ],
        cwd=repo_root_dir,
    )

    print("Adding annotations in readme")
    subprocess.check_call(
        [
            "bash",
            f"{script_dir}/readme_update.sh",
        ],
        cwd=repo_root_dir,
    )

    print(f"Prepared new library in {workdir}")
    print(f"Please create a pull request:\n"
          f"  $ git checkout -b new_module_java-{api_shortname}\n"
          f"  $ git add .\n"
          f"  $ git commit -m 'feat: [{api_shortname}] new module for {api_shortname}'\n"
          f"  $ gh pr create --title 'feat: [{api_shortname}] new module for {api_shortname}'")


def __clone_googleapis(output_dir: Path) -> None:
    Repo.clone_from(
        "https://github.com/googleapis/googleapis.git",
        to_path=f"{output_dir}/googleapis"
    )
    subprocess.check_call([
        "mv",
        f"{output_dir}/googleapis/google",
        f"{output_dir}"]
    )
    subprocess.check_call([
        "mv",
        f"{output_dir}/googleapis/grafeas",
        f"{output_dir}"]
    )
    subprocess.check_call([
        "rm",
        "-rf",
        f"{output_dir}/googleapis"]
    )


def __find_version(proto_path: Path) -> str:
    for child in proto_path.iterdir():
        if child.is_dir() and re.search(r"v[1-9]", child.name) is not None:
            return child.name
    return ""


def __move_modules(
    source: Path,
    dest: Path,
    name_prefix: str
) -> None:
    for folder in source.iterdir():
        if folder.is_dir() and folder.name.startswith(name_prefix):
            shutil.move(folder, dest)


if __name__ == "__main__":
    main()
