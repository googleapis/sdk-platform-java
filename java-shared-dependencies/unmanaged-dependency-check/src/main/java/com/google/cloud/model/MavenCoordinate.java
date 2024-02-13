package com.google.cloud.model;

public class MavenCoordinate {
  private final String groupId;
  private final String artifactId;
  private final String version;

  public MavenCoordinate(String groupId, String artifactId, String version) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
  }

  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getVersion() {
    return version;
  }

  @Override
  public String toString() {
    return String.format("%s:%s:%s", groupId, artifactId, version);
  }
}
