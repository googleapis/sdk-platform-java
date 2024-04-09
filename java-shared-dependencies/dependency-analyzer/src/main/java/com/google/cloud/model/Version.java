package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import java.util.List;

public class Version {
  private final VersionKey versionKey;
  private final List<String> licenses;
  private final List<AdvisoryKey> advisoryKeys;

  public Version(VersionKey versionKey, List<String> licenses, List<AdvisoryKey> advisoryKeys) {
    this.versionKey = versionKey;
    this.licenses = licenses;
    this.advisoryKeys = advisoryKeys;
  }

  public List<AdvisoryKey> getAdvisoryKeys() {
    return ImmutableList.copyOf(advisoryKeys);
  }

  public List<String> getLicenses() {
    return ImmutableList.copyOf(licenses);
  }

  public VersionKey getVersionKey() {
    return versionKey;
  }
}
