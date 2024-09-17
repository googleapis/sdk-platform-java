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
# Creates the owl-bot binary (no node runtime needed)

# node:22.1-alpine
FROM us-docker.pkg.dev/artifact-foundry-prod/docker-3p-trusted/node@sha256:487dc5d5122d578e13f2231aa4ac0f63068becd921099c4c677c850df93bede8 as owlbot-cli-build
ARG OWLBOT_CLI_COMMITTISH=ac84fa5c423a0069bbce3d2d869c9730c8fdf550

# install tools
RUN apk add git

# Clone the owlbot-cli source code
WORKDIR /tools
RUN git clone https://github.com/googleapis/repo-automation-bots
WORKDIR /tools/repo-automation-bots/packages/owl-bot
RUN git checkout "${OWLBOT_CLI_COMMITTISH}"

# Part of the code path (that we don't use) ends up touching a dependency called
# @google-cloud/datastore that tries a fs.readFileSync that is not handled by
# default by esbundle (esbundle is good a figuring out imports but doesn't
# actively scan filesystem interactions such as fs.readFileSync). This makes the
# app to fetch a file at runtime that is not available in the bundle context.
# This is why we remove this import and its usage from the entrypoint.
RUN sed -i '/testWebhook/d' src/bin/owl-bot.ts

# Bundle the source code and its dependencies into a single javascript file
# with all its dependencies embedded.
# This is because SEA (see below) cannot
# resolve external modules in a multi-file project.
# We use the esbuild tool. See https://esbuild.github.io/
COPY ./.cloudbuild/library_generation/image-configuration/owlbot-cli-build-config.js .
RUN npm i esbuild
RUN node owlbot-cli-build-config.js

# Compile the bundled javascript file into a Linux executable
# Create a Standalone Executable Application (SEA) configuration file.
# See https://nodejs.org/api/single-executable-applications.html
RUN echo '{ "main": "bundle.js", "output": "sea-prep.blob" }' > build/sea-config.json
WORKDIR /tools/repo-automation-bots/packages/owl-bot/build
RUN node --experimental-sea-config sea-config.json
RUN cp $(command -v node) owl-bot-bin
RUN npx postject owl-bot-bin NODE_SEA_BLOB sea-prep.blob \
    --sentinel-fuse NODE_SEA_FUSE_fce680ab2cc467b6e072b8b5df1996b2

# move to a simple path for convenience
RUN cp ./owl-bot-bin /owl-bot-bin

# Creates the generator jar
# maven:3.8.6-openjdk-11-slim
FROM us-docker.pkg.dev/artifact-foundry-prod/docker-3p-trusted/maven@sha256:2cb7c73ba2fd0f7ae64cfabd99180030ec85841a1197b4ae821d21836cb0aa3b  as ggj-build

WORKDIR /sdk-platform-java
COPY . .
# {x-version-update-start:gapic-generator-java:current}
ENV DOCKER_GAPIC_GENERATOR_VERSION="2.45.1-SNAPSHOT" 
# {x-version-update-end}

# use Docker Buildkit caching for faster local builds
RUN --mount=type=cache,target=/root/.m2 mvn install -B -ntp -T 1.5C \
    -Dclirr.skip -Dcheckstyle.skip -Djacoco.skip -Dmaven.test.skip \
    -Dmaven.site.skikip -Dmaven.javadoc.skip -pl gapic-generator-java -am

RUN --mount=type=cache,target=/root/.m2 cp "/root/.m2/repository/com/google/api/gapic-generator-java/${DOCKER_GAPIC_GENERATOR_VERSION}/gapic-generator-java-${DOCKER_GAPIC_GENERATOR_VERSION}.jar" \
  "/gapic-generator-java.jar"

# Builds the python scripts in library_generation
# python:3.11-alpine
FROM us-docker.pkg.dev/artifact-foundry-prod/docker-3p-trusted/python@sha256:0b5ed25d3cc27cd35c7b0352bac8ef2ebc8dd3da72a0c03caaf4eb15d9ec827a as python-scripts-build

# This will use GOOGLE_APPLICATION_CREDENTIALS if passed in docker build command.
# If not passed will leave it unset to support GCE Metadata in CI builds
ARG GOOGLE_APPLICATION_CREDENTIALS

RUN apk add bash curl

# Install gcloud to obtain the credentials to use the Airlock repostiory
RUN curl -sSL https://sdk.cloud.google.com | bash -e
ENV PATH $PATH:/root/google-cloud-sdk/bin


# Configure the Airlock pip package repository
RUN pip install keyrings.google-artifactregistry-auth -i https://pypi.org/simple/
COPY .cloudbuild/library_generation/image-configuration/airlock-pypirc /root/.pypirc
COPY .cloudbuild/library_generation/image-configuration/airlock-pip.conf /etc/pip.conf
RUN chmod 600 /root/.pypirc /etc/pip.conf

COPY library_generation /src

# install main scripts as a python package
WORKDIR /src

RUN --mount=type=secret,id=credentials python -m pip install --target /usr/local/lib/python3.11 -r requirements.txt
RUN python -m pip install --target /usr/local/lib/python3.11 .

# Final image. Installs the rest of the dependencies and gets the binaries
# from the previous stages. We use the node base image for it to be compatible
# with the standalone binary owl-bot compiled in the previous stage
# node:22.1-alpine
FROM us-docker.pkg.dev/artifact-foundry-prod/docker-3p-trusted/node@sha256:487dc5d5122d578e13f2231aa4ac0f63068becd921099c4c677c850df93bede8 as final

ARG PROTOC_VERSION=25.4
ARG GRPC_VERSION=1.66.0
# This SHA is the latest known-to-work version of this binary compatibility tool
ARG GLIB_MUS_SHA=7717dd4dc26377dd9cedcc92b72ebf35f9e68a2d
ENV HOME=/home
ENV OS_ARCH="linux-x86_64"

# Install shell script tools. Keep them in sorted order.
RUN apk update && apk add \
    bash \
    curl \
    git \
    jq \
    maven \
    py-pip \
    python3 \
    rsync \
    sudo \
    unzip
SHELL [ "/bin/bash", "-c" ]

# Install compatibility layer to run glibc-based programs (such as the
# grpc plugin).
# Alpine, by default, only supports musl-based binaries, and there is no public
# downloadable distrubution of the grpc that is Alpine (musl) compatible.
# This is one of the recommended approaches to ensure glibc-compatibility
# as per https://wiki.alpinelinux.org/wiki/Running_glibc_programs
WORKDIR /home
RUN git clone https://gitlab.com/manoel-linux1/GlibMus-HQ.git
WORKDIR /home/GlibMus-HQ
# We lock the tool to the latest known-to-work version
RUN git checkout "${GLIB_MUS_SHA}"
RUN chmod a+x compile-x86_64-alpine-linux.sh
RUN ./compile-x86_64-alpine-linux.sh
WORKDIR /home
RUN rm -rf /home/GlibMus-HQ
# We remove some unnecessary compatibility SOs and archive files
WORKDIR /usr/lib
RUN rm -rf LibLLVM-17* libatomic.a gcc llvm17 libexec
# We also remove unnecessary programs installed by this tool
WORKDIR /usr/bin
RUN rm -rf lto-dump



# Use utilites script to download dependencies
COPY library_generation/utils/utilities.sh /utilities.sh

# install protoc
WORKDIR /protoc
RUN source /utilities.sh && download_protoc "${PROTOC_VERSION}" "${OS_ARCH}"
# we indicate protoc is available in the container via env vars
ENV DOCKER_PROTOC_LOCATION=/protoc
ENV DOCKER_PROTOC_VERSION="${PROTOC_VERSION}"

# install grpc
WORKDIR /grpc
RUN source /utilities.sh && download_grpc_plugin "${GRPC_VERSION}" "${OS_ARCH}"
# similar to protoc, we indicate grpc is available in the container via env vars
ENV DOCKER_GRPC_LOCATION="/grpc/protoc-gen-grpc-java-${GRPC_VERSION}-${OS_ARCH}.exe"
ENV DOCKER_GRPC_VERSION="${GRPC_VERSION}"

# Remove utilities script now that we downloaded the generation tools
RUN rm /utilities.sh

# Make home folder accessible for all users since the container is usually
# launched using the -u $(user -i) argument.
# Execution is needed for gapic-generator-java.jar, whereas write permission is
# needed for writing .gitconfig and creating .gitconfig.lock (postprocessing).
# Note that this is NOT a recursive permission setting.
RUN chmod 777 "${HOME}"
RUN touch "${HOME}/.bashrc" && chmod 755 "${HOME}/.bashrc"

# Here we transfer gapic-generator-java from the previous stage.
# Note that the destination is a well-known location that will be assumed at runtime.
# We hard-code the location string so it cannot be overriden.
COPY --from=ggj-build "/gapic-generator-java.jar" "${HOME}/.library_generation/gapic-generator-java.jar"
RUN chmod 755 "${HOME}/.library_generation"
RUN chmod 555 "${HOME}/.library_generation/gapic-generator-java.jar"

# Copy the owlbot-cli binary
COPY --from=owlbot-cli-build "/owl-bot-bin" "/usr/bin/owl-bot"
RUN chmod 555 "/usr/bin/owl-bot"

# Copy the library_generation python packages
COPY --from=python-scripts-build "/usr/local/lib/python3.11/" "/usr/lib/python3.11/"

# set dummy git credentials for the empty commit used in postprocessing
# we use system so all users using the container will use this configuration
RUN git config --system user.email "cloud-java-bot@google.com"
RUN git config --system user.name "Cloud Java Bot"
RUN touch "${HOME}/.gitconfig"
RUN chmod 666 "${HOME}/.gitconfig"

WORKDIR /workspace
ENTRYPOINT [ "python", "-m", "library_generation.cli.entry_point", "generate" ]
