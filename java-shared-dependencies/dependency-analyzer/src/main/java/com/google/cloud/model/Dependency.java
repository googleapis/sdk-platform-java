package com.google.cloud.model;

import java.util.Objects;

public class Dependency {
  private final PkgManagement system;
  private final String name;
  private final String version;

  public Dependency(String system, String name, String version) {
    this.system = PkgManagement.valueOf(system.toUpperCase());
    this.name = name;
    this.version = version;
  }

  public PkgManagement getPackageManagementSys() {
    return system;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  @Override
  public String toString() {
    if (Objects.requireNonNull(system) == PkgManagement.MAVEN) {
      return String.format("%s:%s", name, version);
    }
    return "Do not support";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Dependency)) {
      return false;
    }
    Dependency that = (Dependency) o;
    return system.equals(that.system)
        && name.equals(that.name)
        && version.equals(that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(system, name, version);
  }
}
