package com.google.cloud.model;

import java.util.List;

public class Version {
  private final VersionKey versionKey;
  private final List<String> licenses;

  public Version(VersionKey versionKey, List<String> licenses) {
    this.versionKey = versionKey;
    this.licenses = licenses;
  }

  public List<String> getLicenses() {
    return licenses;
  }

  public VersionKey getVersionKey() {
    return versionKey;
  }
}
