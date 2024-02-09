#!/bin/bash

# Define the directory where you want to install everything
install_dir="$1"

# Create the install directory if it doesn't exist
mkdir -p "$install_dir"

# Change directory to the install directory
cd "$install_dir"

## in future iterations/improvement, make this version dynamic
curl --proto '=https' --tlsv1.2 -fOL https://github.com/open-telemetry/opentelemetry-collector-releases/releases/download/v0.93.0/otelcol_0.93.0_linux_amd64.tar.gz
tar -xvf otelcol_0.93.0_linux_amd64.tar.gz

killall otelcol

## Start OpenTelemetry Collector with the updated config file
echo "Starting OpenTelemetry Collector with the updated config file: $2"
nohup ./otelcol --config "$2" > /dev/null 2>&1 &

exit 0
