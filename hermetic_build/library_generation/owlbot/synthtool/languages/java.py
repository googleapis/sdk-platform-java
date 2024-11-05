# Copyright 2018 Google LLC
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
import xml.etree.ElementTree as ET
import re
import requests
import yaml
import synthtool as s
import synthtool.gcp as gcp
import logging
from synthtool.gcp import common, samples, snippets
from pathlib import Path
from typing import Any, Optional, Dict, Iterable, List
from datetime import date

logger = logging.getLogger()
logger.setLevel(logging.DEBUG)


JAR_DOWNLOAD_URL = "https://github.com/google/google-java-format/releases/download/google-java-format-{version}/google-java-format-{version}-all-deps.jar"
DEFAULT_FORMAT_VERSION = "1.7"
CURRENT_YEAR = date.today().year
GOOD_LICENSE = f"""/*
 * Copyright {CURRENT_YEAR} Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
"""
PROTOBUF_HEADER = "// Generated by the protocol buffer compiler.  DO NOT EDIT!"
BAD_LICENSE = """/\\*
 \\* Copyright \\d{4} Google LLC
 \\*
 \\* Licensed under the Apache License, Version 2.0 \\(the "License"\\); you may not use this file except
 \\* in compliance with the License. You may obtain a copy of the License at
 \\*
 \\* http://www.apache.org/licenses/LICENSE-2.0
 \\*
 \\* Unless required by applicable law or agreed to in writing, software distributed under the License
 \\* is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 \\* or implied. See the License for the specific language governing permissions and limitations under
 \\* the License.
 \\*/
"""
DEFAULT_MIN_SUPPORTED_JAVA_VERSION = 8
METADATA = "metadata"
LIBRARIES_BOM_VERSION = "libraries_bom_version"
LIBRARIES_BOM_VERSION_ENV_KEY = "SYNTHTOOL_LIBRARIES_BOM_VERSION"
HEADER_REGEX = re.compile("\\* Copyright \\d{4} Google LLC")
LIBRARY_VERSION = "library_version"
LIBRARY_VERSION_ENV_KEY = "SYNTHTOOL_LIBRARY_VERSION"


def _file_has_header(path: Path) -> bool:
    """Return true if the file already contains a license header."""
    with open(path, "rt") as fp:
        for line in fp:
            if HEADER_REGEX.search(line):
                return True
    return False


def _filter_no_header(paths: Iterable[Path]) -> Iterable[Path]:
    """Return a subset of files that do not already have a header."""
    for path in paths:
        anchor = Path(path.anchor)
        remainder = str(path.relative_to(path.anchor))
        for file in anchor.glob(remainder):
            if not _file_has_header(file):
                yield file


def fix_proto_headers(proto_root: Path) -> None:
    """Helper to ensure that generated proto classes have appropriate license headers.

    If the file does not already contain a license header, inject one at the top of the file.
    Some resource name classes may contain malformed license headers. In those cases, replace
    those with our standard license header.
    """
    s.replace(
        _filter_no_header([proto_root / "src/**/*.java"]),
        PROTOBUF_HEADER,
        f"{GOOD_LICENSE}{PROTOBUF_HEADER}",
    )
    # https://github.com/googleapis/gapic-generator/issues/3074
    s.replace(
        [proto_root / "src/**/*Name.java", proto_root / "src/**/*Names.java"],
        BAD_LICENSE,
        GOOD_LICENSE,
    )


def fix_grpc_headers(grpc_root: Path, package_name: str = "unused") -> None:
    """Helper to ensure that generated grpc stub classes have appropriate license headers.

    If the file does not already contain a license header, inject one at the top of the file.
    """
    s.replace(
        _filter_no_header([grpc_root / "src/**/*.java"]),
        "^package (.*);",
        f"{GOOD_LICENSE}package \\1;",
    )


def latest_maven_version(group_id: str, artifact_id: str) -> Optional[str]:
    """Helper function to find the latest released version of a Maven artifact.

    Fetches metadata from Maven Central and parses out the latest released
    version.

    Args:
        group_id (str): The groupId of the Maven artifact
        artifact_id (str): The artifactId of the Maven artifact

    Returns:
        The latest version of the artifact as a string or None
    """
    group_path = "/".join(group_id.split("."))
    url = (
        f"https://repo1.maven.org/maven2/{group_path}/{artifact_id}/maven-metadata.xml"
    )
    response = requests.get(url)
    if response.status_code >= 400:
        return "0.0.0"

    return version_from_maven_metadata(response.text)


def version_from_maven_metadata(metadata: str) -> Optional[str]:
    """Helper function to parse the latest released version from the Maven
    metadata XML file.

    Args:
        metadata (str): The XML contents of the Maven metadata file

    Returns:
        The latest version of the artifact as a string or None
    """
    root = ET.fromstring(metadata)
    latest = root.find("./versioning/latest")
    if latest is not None:
        return latest.text

    return None


def _merge_release_please(destination_text: str):
    handle_gh_release_key = "handleGHRelease"
    branches_key = "branches"
    config = yaml.safe_load(destination_text)
    if handle_gh_release_key in config:
        return destination_text

    config[handle_gh_release_key] = True

    if branches_key in config:
        for branch in config[branches_key]:
            branch[handle_gh_release_key] = True
    return yaml.dump(config)


def _merge_common_templates(
    source_text: str, destination_text: str, file_path: Path
) -> str:
    # keep any existing pom.xml
    if file_path.match("pom.xml") or file_path.match("sync-repo-settings.yaml"):
        logger.debug(f"existing pom file found ({file_path}) - keeping the existing")
        return destination_text

    if file_path.match("release-please.yml"):
        return _merge_release_please(destination_text)

    # by default return the newly generated content
    return source_text


def _common_template_metadata() -> Dict[str, Any]:
    metadata = {}  # type: Dict[str, Any]
    repo_metadata = common._load_repo_metadata()
    if repo_metadata:
        metadata["repo"] = repo_metadata
        group_id, artifact_id = repo_metadata["distribution_name"].split(":")

        metadata["latest_version"] = latest_maven_version(
            group_id=group_id, artifact_id=artifact_id
        )

    metadata["latest_bom_version"] = latest_maven_version(
        group_id="com.google.cloud",
        artifact_id="libraries-bom",
    )

    metadata["samples"] = samples.all_samples(["samples/**/src/main/java/**/*.java"])
    metadata["snippets"] = snippets.all_snippets(
        ["samples/**/src/main/java/**/*.java", "samples/**/pom.xml"]
    )
    if repo_metadata and "min_java_version" in repo_metadata:
        metadata["min_java_version"] = repo_metadata["min_java_version"]
    else:
        metadata["min_java_version"] = DEFAULT_MIN_SUPPORTED_JAVA_VERSION

    return metadata


def common_templates(
    excludes: List[str] = [],
    template_path: Optional[Path] = None,
    **kwargs,
) -> None:
    """Generate common templates for a Java Library

    Fetches information about the repository from the .repo-metadata.json file,
    information about the latest artifact versions and copies the files into
    their expected location.

    Args:
        :param excludes: List of template paths to ignore
        :param template_path:
        :param kwargs: Additional options for CommonTemplates.java_library()
    """
    metadata = _common_template_metadata()
    kwargs[METADATA] = metadata

    # Generate flat to tell this repository is a split repo that have migrated
    # to monorepo. The owlbot.py in the monorepo sets monorepo=True.
    monorepo = kwargs.get("monorepo", False)
    kwargs["monorepo"] = monorepo
    split_repo = not monorepo
    repo_metadata = metadata["repo"]
    repo_short = repo_metadata["repo_short"]
    if os.getenv(LIBRARIES_BOM_VERSION_ENV_KEY, default=None) is not None:
        kwargs[METADATA][LIBRARIES_BOM_VERSION] = os.getenv(
            LIBRARIES_BOM_VERSION_ENV_KEY
        )
    kwargs[METADATA][LIBRARY_VERSION] = os.getenv(LIBRARY_VERSION_ENV_KEY)
    # Special libraries that are not GAPIC_AUTO but in the monorepo
    special_libs_in_monorepo = [
        "java-translate",
        "java-dns",
        "java-notification",
        "java-resourcemanager",
    ]
    kwargs["migrated_split_repo"] = split_repo and (
        repo_metadata["library_type"] == "GAPIC_AUTO"
        or (repo_short and repo_short in special_libs_in_monorepo)
    )
    logger.info(
        "monorepo: {}, split_repo: {}, library_type: {},"
        " repo_short: {}, migrated_split_repo: {}".format(
            monorepo,
            split_repo,
            repo_metadata["library_type"],
            repo_short,
            kwargs["migrated_split_repo"],
        )
    )

    templates = gcp.common.CommonTemplates(template_path=template_path).java_library(
        **kwargs
    )

    s.copy([templates], excludes=excludes, merge=_merge_common_templates)


def custom_templates(files: List[str], **kwargs) -> None:
    """Generate custom template files

    Fetches information about the repository from the .repo-metadata.json file,
    information about the latest artifact versions and copies the files into
    their expected location.

    Args:
        files (List[str], optional): List of template paths to include
        **kwargs: Additional options for CommonTemplates.render()
    """
    kwargs["metadata"] = _common_template_metadata()
    kwargs["metadata"]["partials"] = common.load_partials()
    for file in files:
        template = gcp.CommonTemplates().render(file, **kwargs)
        s.copy([template])


def remove_method(filename: str, signature: str):
    """Helper to remove an entire method.

    Goes line-by-line to detect the start of the block. Determines
    the end of the block by a closing brace at the same indentation
    level. This requires the file to be correctly formatted.

    Example: consider the following class:

        class Example {
            public void main(String[] args) {
                System.out.println("Hello World");
            }

            public String foo() {
                return "bar";
            }
        }

    To remove the `main` method above, use:

        remove_method('path/to/file', 'public void main(String[] args)')

    Args:
        filename (str): Path to source file
        signature (str): Full signature of the method to remove. Example:
            `public void main(String[] args)`.
    """
    lines = []
    leading_regex = None
    with open(filename, "r") as fp:
        line = fp.readline()
        while line:
            # for each line, try to find the matching
            regex = re.compile("(\\s*)" + re.escape(signature) + ".*")
            match = regex.match(line)
            if match:
                leading_regex = re.compile(match.group(1) + "}")
                line = fp.readline()
                continue

            # not in a ignore block - preserve the line
            if not leading_regex:
                lines.append(line)
                line = fp.readline()
                continue

            # detect the closing tag based on the leading spaces
            match = leading_regex.match(line)
            if match:
                # block is closed, resume capturing content
                leading_regex = None

            line = fp.readline()

    with open(filename, "w") as fp:
        for line in lines:
            # print(line)
            fp.write(line)


def copy_and_rename_method(filename: str, signature: str, before: str, after: str):
    """Helper to make a copy an entire method and rename it.

    Goes line-by-line to detect the start of the block. Determines
    the end of the block by a closing brace at the same indentation
    level. This requires the file to be correctly formatted.
    The method is copied over and renamed in the method signature.
    The calls to both methods are separate and unaffected.

    Example: consider the following class:

        class Example {
            public void main(String[] args) {
                System.out.println("Hello World");
            }

            public String foo() {
                return "bar";
            }
        }

    To copy and rename the `main` method above, use:

    copy_and_rename_method('path/to/file', 'public void main(String[] args)',
        'main', 'foo1')

    Args:
        filename (str): Path to source file
        signature (str): Full signature of the method to remove. Example:
            `public void main(String[] args)`.
        before (str): name of the method to be copied
        after (str): new name of the copied method
    """
    lines = []
    method = []
    leading_regex = None
    with open(filename, "r") as fp:
        line = fp.readline()
        while line:
            # for each line, try to find the matching
            regex = re.compile("(\\s*)" + re.escape(signature) + ".*")
            match = regex.match(line)
            if match:
                leading_regex = re.compile(match.group(1) + "}")
                lines.append(line)
                method.append(line.replace(before, after))
                line = fp.readline()
                continue

            lines.append(line)
            # not in a ignore block - preserve the line
            if leading_regex:
                method.append(line)
            else:
                line = fp.readline()
                continue

            # detect the closing tag based on the leading spaces
            match = leading_regex.match(line)
            if match:
                # block is closed, resume capturing content
                leading_regex = None
                lines.append("\n")
                lines.extend(method)

            line = fp.readline()

    with open(filename, "w") as fp:
        for line in lines:
            # print(line)
            fp.write(line)


def add_javadoc(filename: str, signature: str, javadoc_type: str, content: List[str]):
    """Helper to add a javadoc annoatation to a method.

        Goes line-by-line to detect the start of the block.
        Then finds the existing method comment (if it exists). If the
        comment already exists, it will append the javadoc annotation
        to the javadoc block. Otherwise, it will create a new javadoc
        comment block.

        Example: consider the following class:

            class Example {
                public void main(String[] args) {
                    System.out.println("Hello World");
                }

                public String foo() {
                    return "bar";
                }
            }

        To add a javadoc annotation the `main` method above, use:

        add_javadoc('path/to/file', 'public void main(String[] args)',
            'deprecated', 'Please use foo instead.')

    Args:
        filename (str): Path to source file
        signature (str): Full signature of the method to remove. Example:
            `public void main(String[] args)`.
        javadoc_type (str): The type of javadoc annotation. Example: `deprecated`.
        content (List[str]): The javadoc lines
    """
    lines: List[str] = []
    annotations: List[str] = []
    with open(filename, "r") as fp:
        line = fp.readline()
        while line:
            # for each line, try to find the matching
            regex = re.compile("(\\s*)" + re.escape(signature) + ".*")
            match = regex.match(line)
            if match:
                leading_spaces = len(line) - len(line.lstrip())
                indent = leading_spaces * " "
                last_line = lines.pop()
                while last_line.lstrip() and last_line.lstrip()[0] == "@":
                    annotations.append(last_line)
                    last_line = lines.pop()
                if last_line.strip() == "*/":
                    first = True
                    for content_line in content:
                        if first:
                            lines.append(
                                indent
                                + " * @"
                                + javadoc_type
                                + " "
                                + content_line
                                + "\n"
                            )
                            first = False
                        else:
                            lines.append(indent + " *   " + content_line + "\n")
                    lines.append(last_line)
                else:
                    lines.append(last_line)
                    lines.append(indent + "/**\n")
                    first = True
                    for content_line in content:
                        if first:
                            lines.append(
                                indent
                                + " * @"
                                + javadoc_type
                                + " "
                                + content_line
                                + "\n"
                            )
                            first = False
                        else:
                            lines.append(indent + " *   " + content_line + "\n")
                    lines.append(indent + " */\n")
                lines.extend(annotations[::-1])
            lines.append(line)
            line = fp.readline()

    with open(filename, "w") as fp:
        for line in lines:
            # print(line)
            fp.write(line)


def annotate_method(filename: str, signature: str, annotation: str):
    """Helper to add an annotation to a method.

        Goes line-by-line to detect the start of the block.
        Then adds the annotation above the found method signature.

        Example: consider the following class:

            class Example {
                public void main(String[] args) {
                    System.out.println("Hello World");
                }

                public String foo() {
                    return "bar";
                }
            }

        To add an annotation the `main` method above, use:

        annotate_method('path/to/file', 'public void main(String[] args)',
            '@Generated()')

    Args:
        filename (str): Path to source file
        signature (str): Full signature of the method to remove. Example:
            `public void main(String[] args)`.
        annotation (str): Full annotation. Example: `@Deprecated`
    """
    lines: List[str] = []
    with open(filename, "r") as fp:
        line = fp.readline()
        while line:
            # for each line, try to find the matching
            regex = re.compile("(\\s*)" + re.escape(signature) + ".*")
            match = regex.match(line)
            if match:
                leading_spaces = len(line) - len(line.lstrip())
                indent = leading_spaces * " "
                lines.append(indent + annotation + "\n")
            lines.append(line)
            line = fp.readline()

    with open(filename, "w") as fp:
        for line in lines:
            # print(line)
            fp.write(line)


def deprecate_method(filename: str, signature: str, alternative: str):
    """Helper to deprecate a method.

        Goes line-by-line to detect the start of the block.
        Then adds the deprecation comment before the method signature.
        The @Deprecation annotation is also added.

        Example: consider the following class:

            class Example {
                public void main(String[] args) {
                    System.out.println("Hello World");
                }

                public String foo() {
                    return "bar";
                }
            }

        To deprecate the `main` method above, use:

        deprecate_method('path/to/file', 'public void main(String[] args)',
            DEPRECATION_WARNING.format(new_method="foo"))

    Args:
        filename (str): Path to source file
        signature (str): Full signature of the method to remove. Example:
            `public void main(String[] args)`.
        alternative: DEPRECATION WARNING: multiline javadoc comment with user
            specified leading open/close comment tags
    """
    add_javadoc(filename, signature, "deprecated", alternative.splitlines())
    annotate_method(filename, signature, "@Deprecated")
