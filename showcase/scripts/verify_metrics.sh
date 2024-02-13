#!/bin/bash

file_exists_and_non_empty() {
    if [ -s "$1" ]; then
        return 0  # File exists and is non-empty
    else
        return 1  # File does not exist or is empty
    fi
}

# Function to check if the JSON file contains the given string
metrics_contains_string() {
    local file="$1"
    local str="$2"
    if grep -qF "$str" "$file"; then
        return 0  # String found in the JSON file
    else
        return 1  # String not found in the JSON file
    fi
}

if [ "$#" -lt 2 ]; then
    echo "Usage: $0 <metrics_file> <search_string1> [<search_string2> ...]"
    exit 1
fi

# Check for valid json structure
if jq '.' "$1" >/dev/null 2>&1; then
    echo "Valid JSON"
else
    echo "Invalid JSON"
fi

metrics_file="$1"
shift

if ! file_exists_and_non_empty "$metrics_file"; then
    echo "Error: File '$metrics_file' does not exist or is empty."
    exit 1
fi

all_found=true
for search_string in "$@"; do
    if ! metrics_contains_string "$metrics_file" "$search_string"; then
        echo "The metrics file does not contain the attribute '$search_string'."
        all_found=false
    fi
done

if [ "$all_found" = false ]; then
    echo "The metrics file does not contain all of the given attributes."
    exit 1
else
    echo "All search attributes found in the metrics file."
fi