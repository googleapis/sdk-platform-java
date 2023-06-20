#!/usr/bin/env bash

set -e

PROTO_PATH=$1
CONTAINS_CLOUD=$2
TRANSPORT=$3 # grpc+rest or grpc
REST_NUMERIC_ENUMS=$4 # true or false
IS_GAPIC_LIBRARY=$5 # true or false
INCLUDE_SAMPLES=$6 # true or false

cd "$(dirname "$(readlink -f "$0")")"
chmod +x generate_library.sh
./generate_library.sh \
53a0be29c4a95a1d3b4c0d3a7a2ac8b52af2a3c0 \
21.12 \
1.54.1 \
2.19.0 \
"${PROTO_PATH}" \
"${CONTAINS_CLOUD}" \
"${TRANSPORT}" \
"${REST_NUMERIC_ENUMS}" \
"${IS_GAPIC_LIBRARY}" \
"${INCLUDE_SAMPLES}"