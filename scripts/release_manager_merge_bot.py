import os
import requests
import time
import argparse
from urllib.parse import urlparse

# --- Configuration ---
# The labels to add when a test fails.
LABELS_TO_ADD = ["kokoro:force-run", "kokoro:run"]
# Your GitHub personal access token.
# It is recommended to set this as an environment variable for security.
# For example: export GITHUB_TOKEN="your_token_here"
GITHUB_TOKEN = os.environ.get("GITHUB_TOKEN")
# The base URL for the GitHub API.
GITHUB_API_URL = "https://api.github.com"
# The headers for the API requests.
HEADERS = {
    "Accept": "application/vnd.github.v3+json",
    "Authorization": f"token {GITHUB_TOKEN}",
}
# --- End of Configuration ---


def get_pr_labels(owner, repo, pr_number):
    """Gets the labels of the pull request."""
    url = f"{GITHUB_API_URL}/repos/{owner}/{repo}/issues/{pr_number}/labels"
    response = requests.get(url, headers=HEADERS)
    response.raise_for_status()
    return [label["name"] for label in response.json()]


def get_pr_head_sha(owner, repo, pr_number):
    """Gets the SHA of the head commit of the pull request."""
    url = f"{GITHUB_API_URL}/repos/{owner}/{repo}/pulls/{pr_number}"
    response = requests.get(url, headers=HEADERS)
    response.raise_for_status()
    return response.json()["head"]["sha"]


def get_commit_status(owner, repo, sha):
    """Gets the combined status of a commit."""
    url = f"{GITHUB_API_URL}/repos/{owner}/{repo}/commits/{sha}/status"
    response = requests.get(url, headers=HEADERS)
    response.raise_for_status()
    return response.json()


def add_labels_to_pr(owner, repo, pr_number, labels):
    """Adds labels to the pull request."""
    url = f"{GITHUB_API_URL}/repos/{owner}/{repo}/issues/{pr_number}/labels"
    data = {"labels": labels}
    response = requests.post(url, headers=HEADERS, json=data)
    response.raise_for_status()
    print(f"Added labels to PR #{pr_number}: {', '.join(labels)}")


def merge_pr(owner, repo, pr_number):
    """Squashes and merges the pull request."""
    url = f"{GITHUB_API_URL}/repos/{owner}/{repo}/pulls/{pr_number}/merge"
    data = {
        "commit_title": f"Merge pull request #{pr_number} from {owner}/{repo}",
        "commit_message": "Squash and merge",
        "merge_method": "squash",
    }
    response = requests.put(url, headers=HEADERS, json=data)
    response.raise_for_status()
    print(f"Successfully squashed and merged PR #{pr_number}")


def main():
    """
    Main function to check the status of the pull request and take action.
    """
    parser = argparse.ArgumentParser(
        description="A bot to automatically retry and merge GitHub pull requests."
    )
    parser.add_argument("url", help="The URL of the pull request to process.")
    args = parser.parse_args()

    if not GITHUB_TOKEN:
        print("Error: GITHUB_TOKEN environment variable is not set.")
        print("Please set it to your GitHub personal access token.")
        return

    try:
        parsed_url = urlparse(args.url)
        path_parts = parsed_url.path.strip("/").split("/")
        if len(path_parts) < 4 or path_parts[2] != "pull":
            raise ValueError("Invalid GitHub pull request URL.")
        owner, repo, _, pr_number = path_parts
        pr_number = int(pr_number)
    except (ValueError, IndexError) as e:
        print(f"Error parsing URL: {e}")
        return

    # --- Initial Label Check ---
    try:
        print(f"Performing initial label check for PR #{pr_number}...")
        current_labels = get_pr_labels(owner, repo, pr_number)
        missing_labels = [
            label for label in LABELS_TO_ADD if label not in current_labels
        ]
        if missing_labels:
            print("Required Kokoro labels are missing. Adding them now...")
            add_labels_to_pr(owner, repo, pr_number, missing_labels)
        else:
            print("Required Kokoro labels are already present.")
    except requests.exceptions.RequestException as e:
        print(f"An error occurred during the initial label check: {e}")
    except Exception as e:
        print(f"An unexpected error occurred: {e}")
    # --- End of Initial Label Check ---

    while True:
        try:
            print(f"Checking status of PR #{pr_number} in {owner}/{repo}...")
            head_sha = get_pr_head_sha(owner, repo, pr_number)
            status = get_commit_status(owner, repo, head_sha)

            state = status["state"]

            print(f"Overall status: {state}")

            if state == "failure":
                print("Some checks have failed. Retrying the tests...")
                add_labels_to_pr(owner, repo, pr_number, LABELS_TO_ADD)
            elif state == "success":
                print("All checks have passed. Merging the pull request...")
                merge_pr(owner, repo, pr_number)
                break  # Exit the loop on success
            elif state == "pending":
                print("Some checks are still pending. Waiting for them to complete.")
            else:
                print(f"Unknown state: {state}. No action taken.")

        except requests.exceptions.RequestException as e:
            print(f"An error occurred while communicating with the GitHub API: {e}")
        except Exception as e:
            print(f"An unexpected error occurred: {e}")

        print("Waiting for 1 minute before retrying...")
        time.sleep(60)


if __name__ == "__main__":
    main()

