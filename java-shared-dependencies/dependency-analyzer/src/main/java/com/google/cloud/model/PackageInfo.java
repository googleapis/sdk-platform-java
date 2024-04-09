package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import java.util.List;

public class PackageInfo {
  private final Dependency mavenCoordinate;
  private final List<String> licenses;
  private final List<Advisory> advisories;

  public PackageInfo(Dependency mavenCoordinate, List<String> licenses, List<Advisory> advisories) {
    this.mavenCoordinate = mavenCoordinate;
    this.licenses = licenses;
    this.advisories = advisories;
  }

  public Dependency getMavenCoordinate() {
    return mavenCoordinate;
  }

  public List<String> getLicenses() {
    return ImmutableList.copyOf(licenses);
  }

  public List<Advisory> getAdvisories() {
    return ImmutableList.copyOf(advisories);
  }
}
