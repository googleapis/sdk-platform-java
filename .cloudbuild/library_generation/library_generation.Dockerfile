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

# install gapic-generator-java in a separate layer so we don't overload the image
# with the transferred source code and jars
FROM gcr.io/cloud-devrel-public-resources/java21@sha256:2ceff5eeea72260258df56d42e44ed413e52ee421c1b77393c5f2c9c4d7c41da AS ggj-build

WORKDIR /sdk-platform-java
COPY . .
# {x-version-update-start:gapic-generator-java:current}
ENV DOCKER_GAPIC_GENERATOR_VERSION="2.49.0" 
# {x-version-update-end}

RUN mvn install -B -ntp -DskipTests -Dclirr.skip -Dcheckstyle.skip
RUN cp "/root/.m2/repository/com/google/api/gapic-generator-java/${DOCKER_GAPIC_GENERATOR_VERSION}/gapic-generator-java-${DOCKER_GAPIC_GENERATOR_VERSION}.jar" \
  "./gapic-generator-java.jar"

# build from the root of this repo:
FROM gcr.io/cloud-devrel-public-resources/python@sha256:9c5ea427632f195ad164054831968389d86fdde4a15abca651f3fcb2a71268cb

SHELL [ "/bin/bash", "-c" ]


ARG OWLBOT_CLI_COMMITTISH=38fe6f89a2339ee75c77739b31b371f601b01bb3
ARG PROTOC_VERSION=25.5
ARG GRPC_VERSION=1.67.1
ARG JAVA_FORMAT_VERSION=1.8
ENV HOME=/home
ENV OS_ARCHITECTURE="linux-x86_64"

# install OS tools
RUN apt-get update && apt-get install -y \
	unzip openjdk-17-jdk rsync maven jq \
	&& apt-get clean

# copy source code
COPY hermetic_build/common /src/common
COPY hermetic_build/library_generation /src/library_generation

# install protoc
WORKDIR /protoc
RUN source /src/library_generation/utils/utilities.sh \
	&& download_protoc "${PROTOC_VERSION}" "${OS_ARCHITECTURE}"
# we indicate protoc is available in the container via env vars
ENV DOCKER_PROTOC_LOCATION=/protoc
ENV DOCKER_PROTOC_VERSION="${PROTOC_VERSION}"

# install grpc
WORKDIR /grpc
RUN source /src/library_generation/utils/utilities.sh \
	&& download_grpc_plugin "${GRPC_VERSION}" "${OS_ARCHITECTURE}"
# similar to protoc, we indicate grpc is available in the container via env vars
ENV DOCKER_GRPC_LOCATION="/grpc/protoc-gen-grpc-java-${GRPC_VERSION}-${OS_ARCHITECTURE}.exe"
ENV DOCKER_GRPC_VERSION="${GRPC_VERSION}"


# Here we transfer gapic-generator-java from the previous stage.
# Note that the destination is a well-known location that will be assumed at runtime
# We hard-code the location string to avoid making it configurable (via ARG) as
# well as to avoid it making it overridable at runtime (via ENV).
COPY --from=ggj-build "/sdk-platform-java/gapic-generator-java.jar" "${HOME}/.library_generation/gapic-generator-java.jar"
RUN chmod 755 "${HOME}/.library_generation/gapic-generator-java.jar"

#  use python 3.12 (the base image has several python versions; here we define the default one)
RUN rm $(which python3)
RUN ln -s $(which python3.12) /usr/local/bin/python
RUN ln -s $(which python3.12) /usr/local/bin/python3
RUN python -m pip install --upgrade pip

# install main scripts as a python package
WORKDIR /
RUN python -m pip install --require-hashes -r src/common/requirements.txt
RUN python -m pip install src/common
RUN python -m pip install --require-hashes -r src/library_generation/requirements.txt
RUN python -m pip install src/library_generation

# Install nvm with node and npm
ENV NODE_VERSION 20.12.0
WORKDIR /home
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.35.3/install.sh | bash
RUN chmod o+rx /home/.nvm
ENV NODE_PATH=/home/.nvm/versions/node/v${NODE_VERSION}/bin
ENV PATH=${PATH}:${NODE_PATH}
RUN node --version
RUN npm --version

# install the owl-bot CLI
WORKDIR /tools
RUN git clone https://github.com/googleapis/repo-automation-bots
WORKDIR /tools/repo-automation-bots/packages/owl-bot
RUN git checkout "${OWLBOT_CLI_COMMITTISH}"
RUN npm i && npm run compile && npm link
RUN owl-bot copy-code --version
RUN chmod -R o+rx ${NODE_PATH}
RUN ln -sf ${NODE_PATH}/* /usr/local/bin

# download the Java formatter
ADD https://maven-central.storage-download.googleapis.com/maven2/com/google/googlejavaformat/google-java-format/${JAVA_FORMAT_VERSION}/google-java-format-${JAVA_FORMAT_VERSION}-all-deps.jar \
  "${HOME}"/.library_generation/google-java-format.jar
RUN chmod 755 "${HOME}"/.library_generation/google-java-format.jar

# allow users to access the script folders
RUN chmod -R o+rx /src

# set dummy git credentials for the empty commit used in postprocessing
# we use system so all users using the container will use this configuration
RUN git config --system user.email "cloud-java-bot@google.com"
RUN git config --system user.name "Cloud Java Bot"

# allow read-write for /home and execution for binaries in /home/.nvm
RUN chmod -R a+rw /home
RUN chmod -R a+rx /home/.nvm

WORKDIR /workspace
ENTRYPOINT [ "python", "/src/library_generation/cli/entry_point.py", "generate" ]
