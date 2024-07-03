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
import shutil
import yaml
from pathlib import Path
from typing import Dict, List, Optional
import jinja2
import logging

from synthtool import _tracked_paths
from synthtool.sources import templates

logger = logging.getLogger()
logger.setLevel(logging.DEBUG)


DEFAULT_TEMPLATES_PATH = "synthtool/gcp/templates"
LOCAL_TEMPLATES: Optional[str] = os.environ.get("SYNTHTOOL_TEMPLATES")

# Originally brought from gcp/partials.py.
# These are the default locations to look up
_DEFAULT_PARTIAL_FILES = [
    ".readme-partials.yml",
    ".readme-partials.yaml",
    ".integration-partials.yaml",
]


class CommonTemplates:
    def __init__(self, template_path: Optional[Path] = None):
        if LOCAL_TEMPLATES is None:
          logger.error("env var SYNTHTOOL_TEMPLATES must be set")
          sys.exit(1)
        logger.debug(f"Using local templates at {LOCAL_TEMPLATES}")
        self._template_root = Path(LOCAL_TEMPLATES)
        self._templates = templates.Templates(self._template_root)
        self.excludes = []  # type: List[str]

    def _generic_library(self, directory: str, relative_dir=None, **kwargs) -> Path:
        # load common repo meta information (metadata that's not language specific).
        if "metadata" in kwargs:
            self._load_generic_metadata(kwargs["metadata"], relative_dir=relative_dir)
            # if no samples were found, don't attempt to render a
            # samples/README.md.
            if "samples" not in kwargs["metadata"] or not kwargs["metadata"]["samples"]:
                self.excludes.append("samples/README.md")

        t = templates.TemplateGroup(self._template_root / directory, self.excludes)

        if "repository" in kwargs["metadata"] and "repo" in kwargs["metadata"]:
            kwargs["metadata"]["repo"]["default_branch"] = _get_default_branch_name(
                kwargs["metadata"]["repository"]
            )

        result = t.render(**kwargs)
        _tracked_paths.add(result)

        return result

    def java_library(self, **kwargs) -> Path:
        # kwargs["metadata"] is required to load values from .repo-metadata.json
        if "metadata" not in kwargs:
            kwargs["metadata"] = {}
        return self._generic_library("java_library", **kwargs)

    def render(self, template_name: str, **kwargs) -> Path:
        template = self._templates.render(template_name, **kwargs)
        _tracked_paths.add(template)
        return template

    def _load_generic_metadata(self, metadata: Dict, relative_dir=None):
        """
        loads additional meta information from .repo-metadata.json.
        """
        metadata["partials"] = load_partials()

        # Loads repo metadata information from the default location if it
        # hasn't already been set. Some callers may have already loaded repo
        # metadata, so we don't need to do it again or overwrite it. Also, only
        # set the "repo" key.
        if "repo" not in metadata:
            metadata["repo"] = _load_repo_metadata(relative_dir=relative_dir)


def _load_repo_metadata(
    relative_dir=None, metadata_file: str = "./.repo-metadata.json"
) -> Dict:
    """Parse a metadata JSON file into a Dict.

    Currently, the defined fields are:
    * `name` - The service's API name
    * `name_pretty` - The service's API title. This will be used for generating titles on READMEs
    * `product_documentation` - The product documentation on cloud.google.com
    * `client_documentation` - The client library reference documentation
    * `issue_tracker` - The public issue tracker for the product
    * `release_level` - The release level of the client library. One of: alpha, beta,
      ga, deprecated, preview, stable
    * `language` - The repo language. One of dotnet, go, java, nodejs, php, python, ruby
    * `repo` - The GitHub repo in the format {owner}/{repo}
    * `distribution_name` - The language-idiomatic package/distribution name
    * `api_id` - The API ID associated with the service. Fully qualified identifier use to
      enable a service in the cloud platform (e.g. monitoring.googleapis.com)
    * `requires_billing` - Whether or not the API requires billing to be configured on the
      customer's acocunt

    Args:
        metadata_file (str, optional): Path to the metadata json file

    Returns:
        A dictionary of metadata. This may not necessarily include all the defined fields above.
    """
    if relative_dir is not None:
        if os.path.exists(Path(relative_dir, metadata_file).resolve()):
            with open(Path(relative_dir, metadata_file).resolve()) as f:
                return json.load(f)
    elif os.path.exists(metadata_file):
        with open(metadata_file) as f:
            return json.load(f)
    return {}


def _get_default_branch_name(repository_name: str) -> str:
    """Read the default branch name from the environment.

    First checks environment variable DEFAULT_BRANCH_PATH.  If found, it
    reads the contents of the file at DEFAULT_BRANCH_PATH and returns it.

    Then checks environment varabile DEFAULT_BRANCH, and returns it if found.
    """
    default_branch_path = os.getenv("DEFAULT_BRANCH_PATH")
    if default_branch_path:
        return Path(default_branch_path).read_text().strip()

    # This default should be switched to "main" once we've migrated
    # the majority of our repositories:
    return os.getenv("DEFAULT_BRANCH", "master")

def load_partials(files: List[str] = []) -> Dict:
    """
    hand-crafted artisanal markdown can be provided in a .readme-partials.yml.
    The following fields are currently supported:

    body: custom body to include in the usage section of the document.
    samples_body: an optional body to place below the table of contents
        in samples/README.md.
    introduction: a more thorough introduction than metadata["description"].
    title: provide markdown to use as a custom title.
    deprecation_warning: a warning to indicate that the library has been
        deprecated and a pointer to an alternate option
    """
    result: Dict[str, Dict] = {}
    cwd_path = Path(os.getcwd())
    for file in files + _DEFAULT_PARTIAL_FILES:
        partials_file = cwd_path / file
        if os.path.exists(partials_file):
            with open(partials_file) as f:
                result.update(yaml.load(f, Loader=yaml.SafeLoader))
    return result
