package com.google.cloud.model;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MavenCoordinate)) {
      return false;
    }
    MavenCoordinate that = (MavenCoordinate) o;
    return groupId.equals(that.groupId)
        && artifactId.equals(that.artifactId)
        && version.equals(that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, artifactId, version);
  }
}
