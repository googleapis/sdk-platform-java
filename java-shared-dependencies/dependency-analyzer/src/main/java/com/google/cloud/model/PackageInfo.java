package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;

/**
 * Selected package information associated with a package version, including licenses and security
 * advisories.
 */
public record PackageInfo(
    VersionKey versionKey,
    List<License> licenses,
    List<Advisory> advisories,
    Optional<PullRequestStatistics> pullRequestStatistics) {

  public List<License> licenses() {
    return ImmutableList.copyOf(licenses);
  }

  public List<Advisory> advisories() {
    return ImmutableList.copyOf(advisories);
  }
}
