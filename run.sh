#!/bin/bash -e

# Run gapic-generator-java as a protoc plugin.

# Usage example here:
: << 'EXAMPLE'
  ./run.sh --g ~/dev/googleapis --p ~/dev/googleapis/google/showcase/v1beta1
EXAMPLE

source gbash.sh

# Required flags.
DEFINE_string --alias=p protos '' 'Path to the protos to generate.'
DEFINE_string --alias=g googleapis '' 'Path to the googleapis directory.'


# Optional flags.
DEFINE_bool --alias=c use_cached false 'If true, does not rebuild the plugin.'
DEFINE_string --alias=o out '/tmp/test' 'Output directory'

gbash::init_google "$@"

# Variables.
PROTOC_INCLUDE_DIR=/usr/local/include/google/protobuf

function echo_error {
  BOLD="\e[1m"
  UNSET="\e[0m"
  WHITE="\e[97m"
  RED="\e[91m"
  BACK_MAGENTA="\e[45m"
  BACK_BLUE="\e[44m"
  BACK_RED="\e[41m"
  echo -e "$BOLD $BACK_BLUE $WHITE $1 $UNSET"
}

function echo_success {
  BOLD="\e[1m"
  UNSET="\e[0m"
  WHITE="\e[97m"
  BACK_GREEN="\e[42m"
  BACK_BLUE="\e[44m"
  echo -e "$BOLD $BACK_BLUE $WHITE $BACK_GREEN $1 $UNSET"
}

# Flag check.
if [[ -z "${FLAGS_protos}" ]]
then
  echo_error "Required flag --protos must be set."
  exit 1
fi

if [[ -z "${FLAGS_googleapis}" ]]
then
  echo_error "Required flag --googleapis must be set."
  exit 1
fi

# Build if needed.
if [[ "${FLAGS_use_cached}" == 0 ]] || [[ ! -f bazel-bin/protoc-gen-gapic-java ]]
then
  echo_success "Rebuilding the microgenerator..."
  bazel build :protoc-gen-gapic-java
  if [[ $? -ne 0 ]]
  then
    echo_error "Build failed."
    exit 1
  fi

  echo_success "Done"
fi

# Run protoc.
protoc -I="${PROTOC_INCLUDE_DIR}" -I="${FLAGS_googleapis}" -I="${FLAGS_protos}" \
    -I="${FLAGS_googleapis}/google/longrunning" \
    --plugin=bazel-bin/protoc-gen-gapic-java ${FLAGS_protos}/*.proto \
    --gapic-java_out="${FLAGS_out}" \
    --experimental_allow_proto3_optional

echo_success "Output files written to ${FLAGS_out}"
