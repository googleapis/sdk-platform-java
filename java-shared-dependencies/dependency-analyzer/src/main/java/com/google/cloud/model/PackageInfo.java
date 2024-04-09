package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import java.util.List;

public class PackageInfo {
  private final VersionKey versionKey;
  private final List<String> licenses;
  private final List<Advisory> advisories;

  public PackageInfo(VersionKey versionKey, List<String> licenses, List<Advisory> advisories) {
    this.versionKey = versionKey;
    this.licenses = licenses;
    this.advisories = advisories;
  }

  public VersionKey getVersionKey() {
    return versionKey;
  }

  public List<String> getLicenses() {
    return ImmutableList.copyOf(licenses);
  }

  public List<Advisory> getAdvisories() {
    return ImmutableList.copyOf(advisories);
  }
}
