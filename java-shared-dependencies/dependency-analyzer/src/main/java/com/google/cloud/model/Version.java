package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * Information about a specific package version, including its licenses and any security advisories
 * known to affect it.
 * <p>
 * For more information, please refer to <a href="https://docs.deps.dev/api/v3/#getversion">GetVersion</a>.
 */
public class Version {
  private final VersionKey versionKey;
  /**
   * The licenses governing the use of this package version.
   */
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
