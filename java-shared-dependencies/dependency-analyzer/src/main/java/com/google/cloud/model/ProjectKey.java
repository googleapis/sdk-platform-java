package com.google.cloud.model;

/**
 * The identifier for project.
 *
 * @param id A project identifier of the form github.com/user/repo, gitlab.com/user/repo, or
 * bitbucket.org/user/repo.
 */
public record ProjectKey(String id) {
  public boolean isGitHubProject() {
    return id.startsWith("github.com");
  }

  public String organization() {
    String[] strs = id.split("/");
    return strs[1];
  }

  public String repo() {
    String[] strs = id.split("/");
    return strs[2];
  }
}
