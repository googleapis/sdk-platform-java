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
    "--destination-name",
    type=str,
    default=None,
    help="The directory name of the new library. By default it's "
         "java-<api_shortname>"
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
    "--owlbot-image",
    type=str,
    default="gcr.io/cloud-devrel-public-resources/owlbot-java",
    show_default=True,
    help="The owlbot container image used in OwlBot.yaml"
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
    destination_name,
    proto_path,
    cloud_api,
    group_id,
    owlbot_image,
    library_type,
    rest_docs,
    rpc_docs,
):
    cloud_prefix = "cloud-" if cloud_api else ""

    output_name = destination_name if destination_name else api_shortname
    if distribution_name is None:
        distribution_name = f"{group_id}:google-{cloud_prefix}{output_name}"

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

    repo_metadata = {
        "api_shortname": api_shortname,
        "name_pretty": name_pretty,
        "product_documentation": product_docs,
        "api_description": api_description,
        "client_documentation": client_documentation,
        "release_level": release_level,
        "transport": transport,
        "language": language,
        "repo": f"googleapis/{language}-{output_name}",
        "repo_short": f"{language}-{output_name}",
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
    workdir = Path(f"{sys.path[0]}/../../output/java-{output_name}").resolve()
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
        module_name=f"java-{output_name}",
        api_shortname=api_shortname
    )

    # get the sha256 digets for the owlbot image
    # subprocess.check_call(["docker", "pull", "-q", owlbot_image])
    # owlbot_image_digest = (
    #     subprocess.check_output(
    #         ["docker", "inspect", "--format='{{index .RepoDigests 0}}", owlbot_image],
    #         encoding="utf-8",
    #     ).strip().split("@")[-1]
    # )

    user = subprocess.check_output(["id", "-u"], encoding="utf8").strip()
    group = subprocess.check_output(["id", "-g"], encoding="utf8").strip()

    # run generate_library.sh
    print("Cloning googleapis...")
    output_dir = Path(f"{sys.path[0]}/../../output").resolve()
    __clone_googleapis(output_dir)
    repo_root_dir = Path(f"{sys.path[0]}/../../").resolve()
    subprocess.check_call([
        "library_generation/generate_library.sh",
        f"-p {proto_path}",
        "-d java-alloydb"],
        cwd=f""
    )

    print(f"Prepared new library in {workdir}")
    print(f"Please create a pull request:\n"
          f"  $ git checkout -b new_module_java-{output_name}\n"
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
        "mv",
        f"{output_dir}/googleapis/WORKSPACE",
        f"{output_dir}"]
    )
    subprocess.check_call([
        "rm",
        "-rf",
        f"{output_dir}/googleapis"]
    )


if __name__ == "__main__":
    main()
