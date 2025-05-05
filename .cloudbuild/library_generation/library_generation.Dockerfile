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


FROM docker.io/library/maven:3.9.9-eclipse-temurin-17-alpine@sha256:969014ee8852c9910ff5ef09de17541c2587819364b79d7dc044634dfb8a3388 AS ggj-build

WORKDIR /sdk-platform-java
COPY . .
# {x-version-update-start:gapic-generator-java:current}
ENV DOCKER_GAPIC_GENERATOR_VERSION="2.57.0"
# {x-version-update-end}

# Download the java formatter
RUN mvn -pl gapic-generator-java-pom-parent help:evaluate -Dexpression='google-java-format.version' -q -DforceStdout > /java-formatter-version
RUN cat /java-formatter-version
RUN V=$(cat /java-formatter-version) && curl -o "/google-java-format.jar" "https://maven-central.storage-download.googleapis.com/maven2/com/google/googlejavaformat/google-java-format/${V}/google-java-format-${V}-all-deps.jar"

# Compile and install packages
RUN mvn install -B -ntp -DskipTests -Dclirr.skip -Dcheckstyle.skip
RUN cp "/root/.m2/repository/com/google/api/gapic-generator-java/${DOCKER_GAPIC_GENERATOR_VERSION}/gapic-generator-java-${DOCKER_GAPIC_GENERATOR_VERSION}.jar" \
  "./gapic-generator-java.jar"

FROM docker.io/library/alpine:3.21.2@sha256:56fa17d2a7e7f168a043a2712e63aed1f8543aeafdcee47c58dcffe38ed51099 as glibc-compat

RUN apk update && apk add git sudo
# This SHA is the latest known-to-work version of this binary compatibility tool
ARG GLIB_MUS_SHA=e94aca542e3ab08b42aa0b0d6e72478b935bb8e8
WORKDIR /home

# Install compatibility layer to run glibc-based programs (such as the
# grpc plugin).
# Alpine, by default, only supports musl-based binaries, and there is no public
# downloadable distribution of the grpc plugin that is Alpine (musl) compatible.
# This is one of the recommended approaches to ensure glibc-compatibility
# as per https://wiki.alpinelinux.org/wiki/Running_glibc_programs
RUN git clone https://gitlab.com/manoel-linux1/GlibMus-HQ.git
WORKDIR /home/GlibMus-HQ
# We lock the tool to the latest known-to-work version
RUN git checkout "${GLIB_MUS_SHA}"
RUN chmod a+x compile-x86_64-alpine-linux.sh
RUN sh compile-x86_64-alpine-linux.sh

FROM docker.io/library/python:3.13.2-alpine3.20@sha256:816feb29731cdee64b15b0ae91dd9f1cbc36765984ff8ea85a3d90f064417237 as final

ARG OWLBOT_CLI_COMMITTISH=3a68a9c0de318784b3aefadcc502a6521b3f1bc5
ARG PROTOC_VERSION=25.5
ARG GRPC_VERSION=1.70.0
ENV HOME=/home
ENV OS_ARCHITECTURE="linux-x86_64"

# install OS tools
RUN apk update && apk add unzip curl rsync openjdk17 jq bash nodejs npm git

SHELL [ "/bin/bash", "-c" ]

# Copy glibc shared objects to enable execution of the grpc plugin.
# This list was obtained via `libtree -pvvv /grpc/*` in the final container as
# well as inspecting the modifications done by compile-x86_64-alpine-linux.sh
# in the glibc-compat stage using the `dive` command.
COPY --from=glibc-compat /etc/libgcc* /etc/
COPY --from=glibc-compat /lib64/ld-linux-x86-64.so.2 /lib64/
COPY --from=glibc-compat /lib/GLIBCFAKE.so.0 /lib/
COPY --from=glibc-compat /lib/ld-linux-x86-64.so.2 /lib/
COPY --from=glibc-compat /lib/libpthread* /lib/
COPY --from=glibc-compat /lib/libucontext* /lib/
COPY --from=glibc-compat /lib/libc.* /lib/
COPY --from=glibc-compat /usr/lib/libgcc* /usr/lib/
COPY --from=glibc-compat /usr/lib/libstdc* /usr/lib/
COPY --from=glibc-compat /usr/lib/libobstack* /usr/lib/
COPY --from=glibc-compat /lib/libm.so.6 /usr/lib/
COPY --from=glibc-compat /usr/lib/libucontext.so.1 /usr/lib/


# copy source code
COPY hermetic_build/common /src/common
COPY hermetic_build/library_generation /src/library_generation

# install protoc
WORKDIR /protoc
RUN source /src/library_generation/utils/utilities.sh \
	&& download_protoc "${PROTOC_VERSION}" "${OS_ARCHITECTURE}"
# we indicate protoc is available in the container via env vars
ENV DOCKER_PROTOC_LOCATION=/protoc/bin
ENV DOCKER_PROTOC_VERSION="${PROTOC_VERSION}"

# install grpc
WORKDIR /grpc
RUN source /src/library_generation/utils/utilities.sh \
	&& download_grpc_plugin "${GRPC_VERSION}" "${OS_ARCHITECTURE}"
# similar to protoc, we indicate grpc is available in the container via env vars
ENV DOCKER_GRPC_LOCATION="/grpc/protoc-gen-grpc-java.exe"

RUN python -m pip install --upgrade pip

# install main scripts as a python package
WORKDIR /
RUN python -m pip install --require-hashes -r src/common/requirements.txt
RUN python -m pip install src/common
RUN python -m pip install --require-hashes -r src/library_generation/requirements.txt
RUN python -m pip install src/library_generation

# install the owl-bot CLI
WORKDIR /tools
RUN git clone https://github.com/googleapis/repo-automation-bots
WORKDIR /tools/repo-automation-bots/packages/owl-bot
RUN git checkout "${OWLBOT_CLI_COMMITTISH}"
RUN npm i && npm run compile && npm link
RUN owl-bot copy-code --version
RUN chmod o+rx $(which owl-bot)
RUN apk del -r npm && apk cache clean

# copy the Java formatter
COPY --from=ggj-build "/google-java-format.jar" "${HOME}"/.library_generation/google-java-format.jar
RUN chmod 755 "${HOME}"/.library_generation/google-java-format.jar
ENV JAVA_FORMATTER_LOCATION="${HOME}/.library_generation/google-java-format.jar"

# Here we transfer gapic-generator-java from the previous stage.
# Note that the destination is a well-known location that will be assumed at runtime
# We hard-code the location string to avoid making it configurable (via ARG) as
# well as to avoid it making it overridable at runtime (via ENV).
COPY --from=ggj-build "/sdk-platform-java/gapic-generator-java.jar" "${HOME}/.library_generation/gapic-generator-java.jar"
RUN chmod 755 "${HOME}/.library_generation/gapic-generator-java.jar"
ENV GAPIC_GENERATOR_LOCATION="${HOME}/.library_generation/gapic-generator-java.jar"

# allow users to access the script folders
RUN chmod -R o+rx /src

# set dummy git credentials for the empty commit used in postprocessing
# we use system so all users using the container will use this configuration
RUN git config --system user.email "cloud-java-bot@google.com"
RUN git config --system user.name "Cloud Java Bot"

# allow read-write for /home and execution for binaries in /home/.nvm
RUN chmod -R a+rw /home

WORKDIR /workspace
ENTRYPOINT [ "python", "/src/library_generation/cli/entry_point.py", "generate" ]
