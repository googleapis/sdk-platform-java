# Copyright 2021 Google LLC
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

# install tools
RUN apt-get update && apt-get install -y \
	unzip openjdk-17-jdk rsync maven \
	&& apt-get clean

COPY library_generation /src

RUN rm $(which python3)
RUN ln -s $(which python3.11) /usr/local/bin/python
RUN ln -s $(which python3.11) /usr/local/bin/python3
RUN python -m pip install --upgrade pip
RUN cd /src && python -m pip install -r requirements.in
RUN cd /src && python -m pip install .

# set dummy git credentials for empty commit used in postprocessing
RUN git config --global user.email "cloud-java-bot@google.com"
RUN git config --global user.name "Cloud Java Bot"

WORKDIR /workspace
RUN chmod 750 /workspace
RUN chmod 750 /src/generate_repo.py

CMD [ "/src/generate_repo.py" ]
