#!/bin/bash
# wrapper script for the hermetic build cli entrypoint that sets environment
# variables that can be read by any user calling the container
set -ex
export HOME=/home
export MAVEN_HOME=/home/.m2
python /src/cli/entry_point.py generate $@

