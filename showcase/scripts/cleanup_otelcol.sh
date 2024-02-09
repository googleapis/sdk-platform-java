#!/bin/bash

killall otelcol

# Directories to delete
directories_to_delete=(
    "../directory1"
    "../directory2"
    "../directory3"
)

# Iterate over each directory and delete it if it exists
for directory in "${directories_to_delete[@]}"; do
    if [ -d "$directory" ]; then
        rm -rf "$directory"
        echo "Directory '$directory' has been deleted."
    else
        echo "Error: Directory '$directory' does not exist."
    fi
done

exit 0