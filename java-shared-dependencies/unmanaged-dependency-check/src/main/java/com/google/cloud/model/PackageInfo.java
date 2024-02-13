package com.google.cloud.model;

import java.util.List;

public class PackageInfo {
  private final List<Version> versions;

  public PackageInfo(List<Version> versions) {
    this.versions = versions;
  }

  public List<Version> getVersions() {
    return versions;
  }
}
