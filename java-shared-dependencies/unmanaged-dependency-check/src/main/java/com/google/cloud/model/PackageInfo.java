package com.google.cloud.model;

import java.util.List;
import java.util.stream.Collectors;

public class PackageInfo {
  private final List<Version> versions;

  public PackageInfo(List<Version> versions) {
    this.versions = versions;
  }

  public List<Version> getVersions() {
    return versions;
  }

  public List<MavenCoordinate> toMavenCoordinates() {
    return getVersions()
        .stream()
        .map(version -> version.getVersionKey().toMavenCoordinate())
        .collect(Collectors.toList());
  }
}
