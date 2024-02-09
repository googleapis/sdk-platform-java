#!/bin/bash

# add logic for verifying metrics


# perform cleanup
# Specify the directory to delete
directory_to_delete="../opentelemetry-logs"

# Check if the directory exists
if [ -d "$directory_to_delete" ]; then
  rm -rf "$directory_to_delete"
  echo "Directory '$directory_to_delete' has been deleted."
else
  echo "Error: Directory '$directory_to_delete' does not exist."
fi

exit 0
