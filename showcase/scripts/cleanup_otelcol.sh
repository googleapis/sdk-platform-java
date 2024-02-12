#!/bin/bash

# Directories to delete
for ((i = 1; i <= 10; i++)); do
    directory="../directory$i"
    if [ -d "$directory" ]; then
        rm -rf "$directory" && echo "Directory '$directory' has been deleted."
    else
        echo "Error: Directory '$directory' does not exist."
    fi
done

exit 0
