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

import json
import os
import re
import shutil
import fnmatch
from copy import deepcopy
from pathlib import Path
from typing import Dict, List, Optional
import jinja2
from datetime import date

from synthtool import shell, _tracked_paths
from synthtool.gcp import partials
from synthtool.log import logger
from synthtool.sources import git, templates

PathOrStr = templates.PathOrStr
TEMPLATES_URL: str = git.make_repo_clone_url("googleapis/synthtool")
DEFAULT_TEMPLATES_PATH = "synthtool/gcp/templates"
LOCAL_TEMPLATES: Optional[str] = os.environ.get("SYNTHTOOL_TEMPLATES")


class CommonTemplates:
    def __init__(self, template_path: Optional[Path] = None):
        if template_path:
            self._template_root = template_path
        elif LOCAL_TEMPLATES:
            logger.debug(f"Using local templates at {LOCAL_TEMPLATES}")
            self._template_root = Path(LOCAL_TEMPLATES)
        else:
            templates_git = git.clone(TEMPLATES_URL)
            self._template_root = templates_git / DEFAULT_TEMPLATES_PATH

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

        # TODO: migrate to python.py once old sample gen is deprecated
        if directory == "python_samples":
            t.env.globals["get_help"] = lambda filename: shell.run(
                ["python", filename, "--help"]
            ).stdout

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
        metadata["partials"] = partials.load_partials()

        # Loads repo metadata information from the default location if it
        # hasn't already been set. Some callers may have already loaded repo
        # metadata, so we don't need to do it again or overwrite it. Also, only
        # set the "repo" key.
        if "repo" not in metadata:
            metadata["repo"] = _load_repo_metadata(relative_dir=relative_dir)


def detect_versions(
    path: str = "./src",
    default_version: Optional[str] = None,
    default_first: Optional[bool] = None,
) -> List[str]:
    """
    Detects the versions a library has, based on distinct folders
    within path. This is based on the fact that our GAPIC libraries are
    structured as follows:

    src/v1
    src/v1beta
    src/v1alpha

    With folder names mapping directly to versions.

    Returns: a list of the sorted subdirectories; for the example above:
      ['v1', 'v1alpha', 'v1beta']
      If the `default_version` argument is not provided, the `default_version`
      will be read from `.repo-metadata.json`, if it exists.
      If `default_version` is available, the `default_version` is moved to
      at the front or the end of the sorted list depending on the value of `default_first`.
      The `default_version` will be first in the list when `default_first` is `True`.
    """

    versions = []

    if not default_version:
        try:
            # Get the `default_version` from ``.repo-metadata.json`.
            default_version = json.load(open(".repo-metadata.json", "rt")).get(
                "default_version"
            )
        except FileNotFoundError:
            pass

    # Detect versions up to a depth of 4 in directory hierarchy
    for level in ("*v[1-9]*", "*/*v[1-9]*", "*/*/*v[1-9]*", "*/*/*/*v[1-9]*"):
        # Sort the sub directories alphabetically.
        sub_dirs = sorted([p.name for p in Path(path).glob(level) if p.is_dir()])
        # Don't proceed to the next level if we've detected versions in this depth level
        if sub_dirs:
            break

    if sub_dirs:
        # if `default_version` is not specified, return the sorted directories.
        if not default_version:
            versions = sub_dirs
        else:
            # The subdirectory with the same suffix as the default_version
            # will be the default client.
            default_client = next(
                iter([d for d in sub_dirs if d.endswith(default_version)]), None
            )

            # start with all the versions except for the default client
            versions = [d for d in sub_dirs if not d.endswith(default_version)]

            if default_client:
                # If `default_first` is true, the default_client will be first
                # in the list.
                if default_first:
                    versions = [default_client] + versions
                else:
                    versions += [default_client]
    return versions


def decamelize(value: str):
    """Parser to convert fooBar.js to Foo Bar."""
    if not value:
        return ""
    str_decamelize = re.sub("^.", value[0].upper(), value)  # apple -> Apple.
    str_decamelize = re.sub(
        "([A-Z]+)([A-Z])([a-z0-9])", r"\1 \2\3", str_decamelize
    )  # ACLBatman -> ACL Batman.
    return re.sub("([a-z0-9])([A-Z])", r"\1 \2", str_decamelize)  # FooBar -> Foo Bar.


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
