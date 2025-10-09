# Copyright 2025 Google LLC
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

# This Dockerfile creates a MOSS-compliant image for the Go librariangen,
# which is invoked by the Librarian tool. It uses a multi-stage build to
# create a minimal final image.

# --- Builder Stage ---
# This stage builds the librariangen binary using the MOSS-compliant base image.
FROM marketplace.gcr.io/google/debian12:latest AS builder

# Set environment variables for tool versions for easy updates.
ENV GO_VERSION=1.24.0

# Install build dependencies.
RUN apt-get update && \
    apt-get install -y \
    build-essential \
    ca-certificates \
    curl \
    wget && \
    rm -rf /var/lib/apt/lists/*

# Install the specific Go version required for compatibility.
RUN wget https://go.dev/dl/go${GO_VERSION}.linux-amd64.tar.gz -O go.tar.gz && \
    tar -C /usr/local -xzf go.tar.gz && \
    rm go.tar.gz
ENV PATH=/usr/local/go/bin:$PATH
RUN go version

WORKDIR /src

# Copy go.mod and go.sum to download dependencies before copying all the source.
# This allows Docker to cache the dependencies layer.
# COPY go.mod go.sum ./
# RUN go mod download

# Copy all source code.
COPY . .

# Build the librariangen binary.
RUN CGO_ENABLED=0 GOOS=linux go build -v -o /librariangen .

# --- Final Stage ---
# This stage creates the final, minimal image with the compiled binary and
# all required runtime dependencies pinned to specific versions for compatibility.
FROM marketplace.gcr.io/google/debian12:latest

# Copy the compiled librariangen binary from the builder stage.
COPY --from=builder /librariangen /usr/local/bin/librariangen

# Set the entrypoint for the container to run the compiled librariangen.
# The Librarian will provide commands like 'generate' as arguments.
ENTRYPOINT [ "/usr/local/bin/librariangen" ]