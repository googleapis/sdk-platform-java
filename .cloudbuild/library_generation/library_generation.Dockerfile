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
ARG SYNTHTOOL_COMMITTISH=6612ab8f3afcd5e292aecd647f0fa68812c9f5b5

# install OS tools
RUN apt-get update && apt-get install -y \
	unzip openjdk-17-jdk rsync maven jq \
	&& apt-get clean

# copy source code
COPY library_generation /src

# install synthtool
WORKDIR /synthtool
RUN git clone https://github.com/googleapis/synthtool
WORKDIR /synthtool/synthtool
RUN git checkout "${SYNTHTOOL_COMMITTISH}"
RUN python3 -m pip install --no-deps -e .
RUN python3 -m pip install -r requirements.in


# use python 3.11 (the base image has several python versions; here we define the default one)
RUN rm $(which python3)
RUN ln -s $(which python3.11) /usr/local/bin/python
RUN ln -s $(which python3.11) /usr/local/bin/python3
RUN python -m pip install --upgrade pip
WORKDIR /src
RUN python -m pip install -r requirements.in
RUN python -m pip install .

# set dummy git credentials for the empty commit used in postprocessing
RUN git config --global user.email "cloud-java-bot@google.com"
RUN git config --global user.name "Cloud Java Bot"

WORKDIR /workspace
RUN chmod 750 /workspace
RUN chmod 750 /src/generate_repo.py

# define runtime env vars
ENV RUNNING_IN_DOCKER=true
CMD [ "/src/generate_repo.py" ]
