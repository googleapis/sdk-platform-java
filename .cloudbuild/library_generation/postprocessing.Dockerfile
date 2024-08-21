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

# build from the root of this repo:
FROM gcr.io/cloud-devrel-public-resources/python

ENV HOME=/home

# install OS tools
RUN apt-get update && apt-get install -y \
	unzip openjdk-17-jdk rsync maven jq \
	&& apt-get clean

COPY library_generation /src
# remove workflows that are not used in non-GAPIC libraries.
RUN rm /src/owlbot/templates/java_library/.github/workflows/update_generation_config.yaml
RUN rm -rf /src/owlbot/templates/java_library/.github/scripts

RUN rm $(which python3)
RUN ln -s $(which python3.11) /usr/local/bin/python
RUN ln -s $(which python3.11) /usr/local/bin/python3
RUN python -m pip install --upgrade pip
# install requirements.
WORKDIR /src
RUN python -m pip install --require-hashes -r requirements.txt
RUN python -m pip install .
# allow users to access the script folders
RUN chmod -R o+rx /src
RUN chmod -R a+rw /home

WORKDIR /workspace
ENTRYPOINT [ "/src/owlbot/bin/entrypoint.sh", "/src", "versions.txt", "false"]