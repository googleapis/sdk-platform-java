package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * Selected package information associated with a package version, including licenses and security
 * advisories.
 */
public record PackageInfo(
    VersionKey versionKey,
    List<String> licenses,
    List<Advisory> advisories) {

  public List<String> licenses() {
    return ImmutableList.copyOf(licenses);
  }

  public List<Advisory> advisories() {
    return ImmutableList.copyOf(advisories);
  }
}
