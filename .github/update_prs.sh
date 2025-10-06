#!/bin/bash

# A script to update GitHub PRs with the latest from the main branch.
#
# It checks out each PR, merges main using the 'ours' strategy for conflicts
# (which keeps the PR's changes), and pushes the updated branch.
#
# Dependencies:
# - git
# - gh (GitHub CLI)
#
# Usage:
# ./update_prs.sh

# -----------------------------------------------------------------------------
# Configuration & Safety Checks
# -----------------------------------------------------------------------------

# Exit the script immediately if any command fails.
set -e

# Ensure required command-line tools are installed.
if ! command -v git &> /dev/null; then
    echo "❌ Error: 'git' is not installed. Please install it to continue."
    exit 1
fi

if ! command -v gh &> /dev/null; then
    echo "❌ Error: 'gh' (GitHub CLI) is not installed. Please install it to continue."
    exit 1
fi

# Ensure the user is authenticated with the GitHub CLI.
if ! gh auth status &> /dev/null; then
    echo "❌ Error: You are not logged into the GitHub CLI. Please run 'gh auth login'."
    exit 1
fi

# -----------------------------------------------------------------------------
# Main Logic
# -----------------------------------------------------------------------------

PR_URLS_429=(
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
)

PR_URLS_431=(
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

# Keep track of the directory where the script was started.
START_DIR=$(pwd)

function processPRs() {
    PR_URLS=("$@")
    # Loop through all PR URLs defined in the PR_URLS array.
    for pr_url in "${PR_URLS[@]}"; do
        echo "🚀 Processing PR: $pr_url"

        # Parse the URL to get the repository and PR number.
        # Example: https://github.com/googleapis/java-bigtable/pull/2614
        # REPO_OWNER -> googleapis
        # REPO_NAME  -> java-bigtable
        # PR_NUMBER  -> 2614
        REPO_OWNER=$(echo "$pr_url" | cut -d'/' -f4)
        REPO_NAME=$(echo "$pr_url" | cut -d'/' -f5)
        PR_NUMBER=$(echo "$pr_url" | cut -d'/' -f7)

        # Basic validation of the parsed components.
        if [ -z "$REPO_OWNER" ] || [ -z "$REPO_NAME" ] || [ -z "$PR_NUMBER" ]; then
            echo "⚠️  Could not parse URL: $pr_url. Skipping."
            echo "-----------------------------------------"
            continue
        fi

        REPO_FULL_NAME="$REPO_OWNER/$REPO_NAME"

        # Clone the repo into a subdirectory if it doesn't already exist.
        if [ ! -d "$REPO_NAME" ]; then
            echo "Cloning repository $REPO_FULL_NAME..."
            gh repo clone "$REPO_FULL_NAME"
        fi

        # Navigate into the repository's directory.
        cd "$REPO_NAME"

        git stash

        echo "Checking out PR #$PR_NUMBER..."
        gh pr checkout "$PR_NUMBER"

        echo "Fetching latest updates from origin..."
        git fetch origin

        echo "Merging latest 'origin/main' into the branch..."
        # The `-X ours` option is the key. It resolves any merge conflicts
        # by automatically favoring the version of the code from the
        # current branch (your PR branch).
        git merge -m "Merge branch 'main' into PR #$PR_NUMBER to update" -X ours origin/main

        echo "Pushing updated branch to remote..."
        git push

        echo "✅ Successfully updated PR #$PR_NUMBER in $REPO_FULL_NAME."

        if git stash list | grep -q 'stash@{0}'; then
            git stash pop
        else
            echo "No stash entries found, skipping git stash pop."
        fi

        # Return to the starting directory to process the next repository.
        cd "$START_DIR"
        echo "-----------------------------------------"
    done
    echo "🎉 All done!"
}

echo "Running for all 4.29 Protobuf PRs..."
processPRs "${PR_URLS_429[@]}"
echo "-----------------------------------------"
echo "Running for all 4.31 Protobuf PRs..."
processPRs "${PR_URLS_431[@]}"
echo "-----------------------------------------"