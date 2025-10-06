#!/bin/bash

# A script to check the status of a specific CI job ("Kokoro - Test: Integration") 
# for a list of GitHub Pull Requests.
#
# Assumes the user has the GitHub CLI (`gh`) installed and is authenticated.

# Hardcoded list of downstream Protobuf testing PRs to check the status of
PULL_REQUESTS=(
    "https://github.com/googleapis/java-bigtable/pull/2614"
    "https://github.com/googleapis/java-bigquery/pull/3867"
    "https://github.com/googleapis/java-bigquerystorage/pull/3030"
    "https://github.com/googleapis/java-datastore/pull/1900"
    "https://github.com/googleapis/java-firestore/pull/2165"
    "https://github.com/googleapis/java-logging/pull/1825"
    "https://github.com/googleapis/java-logging-logback/pull/1492"
    "https://github.com/googleapis/java-pubsub/pull/2470"
    "https://github.com/googleapis/java-pubsublite/pull/1889"
    "https://github.com/googleapis/java-spanner/pull/3928"
    "https://github.com/googleapis/java-spanner-jdbc/pull/2113"
    "https://github.com/googleapis/java-storage/pull/3178"
    "https://github.com/googleapis/java-storage-nio/pull/1612"
    "https://github.com/googleapis/java-bigtable/pull/2663"
    "https://github.com/googleapis/java-bigquery/pull/3942"
    "https://github.com/googleapis/java-bigquerystorage/pull/3083"
    "https://github.com/googleapis/java-datastore/pull/1954"
    "https://github.com/googleapis/java-firestore/pull/2227"
    "https://github.com/googleapis/java-logging/pull/1851"
    "https://github.com/googleapis/java-logging-logback/pull/1512"
    "https://github.com/googleapis/java-pubsub/pull/2537"
    "https://github.com/googleapis/java-pubsublite/pull/1917"
    "https://github.com/googleapis/java-spanner/pull/4026"
    "https://github.com/googleapis/java-spanner-jdbc/pull/2191"
    "https://github.com/googleapis/java-storage/pull/3266"
    "https://github.com/googleapis/java-storage-nio/pull/1644"
)

# The name of the Integration Testing CI job
JOB_NAME="Kokoro - Test: Integration"

# --- Script Logic ---
echo "🔎 Starting check for '${JOB_NAME}' status..."
echo "------------------------------------------------"

# A counter for failed or pending jobs.
FAILURES=0
PENDING=0

# Loop through each PR in the array.
for pr_url in "${PULL_REQUESTS[@]}"; do
  echo "Checking PR: ${pr_url}"

  # Use `gh pr checks` to get the status of all checks for the PR.
  # Then, use `grep` to find the line for our specific job.
  # We anchor the search to the beginning of the line (^) and ensure it is
  # followed by whitespace to avoid partial matches with other job names.
  check_status_line=$(gh pr checks "${pr_url}" | grep "^${JOB_NAME}[[:space:]]")

  if [[ -z "${check_status_line}" ]]; then
    # If `grep` returns nothing, the job was not found.
    echo "  -> 🟡 WARNING: Could not find the '${JOB_NAME}' job."
    ((FAILURES++))
  else
    # Get the json values for the name of the job and the status
    status=$(gh pr checks ${pr_url} --json name,state -q ".[] | select(.name == \"${JOB_NAME}\") | .state")

    # Check the status and report accordingly.
    if [[ "${status}" == "SUCCESS" ]]; then
      echo "  -> ✅ SUCCESS: The job passed."
    elif [[ "${status}" == "FAILURE" ]]; then
      echo "  -> ❌ FAILURE: The job failed."
      ((FAILURES++))
    elif [[ "${status}" == "PENDING" ]]; then
      echo "  -> ⏳ PENDING: The job is still running."
      ((PENDING++))
    else
      # Handle any other statuses (e.g., SKIPPED, CANCELED).
      echo "  -> ⚪️ INFO: The job status is '${status}'."
    fi
  fi
  echo "" # Add a newline for better readability.
done

echo "------------------------------------------------"

# Final summary report.
if [[ ${FAILURES} -eq 0 && ${PENDING} -eq 0 ]]; then
  echo "🎉 All checks for '${JOB_NAME}' passed successfully!"
else
  echo "🚨 Found ${FAILURES} PR(s) where '${JOB_NAME}' did not succeed."
  echo "🚨 Found ${PENDING} PR(s) where '${JOB_NAME}' status is unknown."
fi