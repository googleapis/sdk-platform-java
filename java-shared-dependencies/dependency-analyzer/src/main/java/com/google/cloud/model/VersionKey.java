package com.google.cloud.model;

import com.google.common.base.Objects;

public class VersionKey {
  private final PkgManagement system;
  private final String name;
  private final String version;

  public VersionKey(String system, String name, String version) {
    this.system = PkgManagement.valueOf(system.toUpperCase());
    this.name = name;
    this.version = version;
  }

  public PkgManagement getSystem() {
    return system;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof VersionKey)) {
      return false;
    }
    VersionKey that = (VersionKey) o;
    return system == that.system && Objects.equal(name, that.name)
        && Objects.equal(version, that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(system, name, version);
  }
}
