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

# install OS tools
RUN apt-get update && apt-get install -y \
	unzip openjdk-17-jdk rsync maven jq \
	&& apt-get clean

COPY library_generation/owlbot/bin /owlbot/bin
COPY library_generation/owlbot/src /owlbot/src
COPY library_generation/owlbot/templates /owlbot/templates
COPY library_generation/owlbot/synthtool /owlbot/synthtool
COPY library_generation/requirements.txt /owlbot
###################### Install requirements.
WORKDIR /owlbot
RUN rm $(which python3)
RUN ln -s $(which python3.11) /usr/local/bin/python
RUN ln -s $(which python3.11) /usr/local/bin/python3
RUN python -m pip install --upgrade pip
RUN python3 -m pip install --require-hashes -r requirements.txt

# allow users to access the script folders
RUN chmod -R o+rx /owlbot

WORKDIR /workspace

CMD [ "/owlbot/bin/entrypoint.sh" ]