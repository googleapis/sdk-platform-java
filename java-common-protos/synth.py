# Copyright 2020 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""This script is used to synthesize generated parts of this library."""

import synthtool as s
from synthtool import gcp
from synthtool.sources import git
import synthtool.languages.java as java
from pathlib import Path

gapic = gcp.GAPICBazel()
googleapis = Path("/home/chingor/code/googleapis")

proto_targets = ["api", "cloud/audit", "geo/type", "logging/type", "longrunning", "rpc", "rpc/context", "type"]
for path in proto_targets:
    target = path.replace("/", "-")
    target = f"google-{target}-java"
    library = gapic.java_library(
        service=target,
        version="unused",
        proto_path=f"google/{path}:", #google-iam-{version}-logging-java',
        bazel_target=f"//google/{path}:{target}",
    )

    library = library / target
    java.fix_proto_headers(library / f"proto-{target}")
    s.copy(library / f"proto-{target}" / "src", "proto-google-common-protos/src", required=True)

    java.fix_grpc_headers(library / f"grpc-{target}", package_name=None)
    s.copy(library / f"grpc-{target}" / "src", "grpc-google-common-protos/src")

java.format_code("proto-google-common-protos/src")
java.format_code("grpc-google-common-protos/src")

java.common_templates(excludes=[
  'README.md',
  'samples/*',
])