package com.google.cloud.model;

public class VersionKey {
  private final String system;
  private final String name;
  private final String version;

  public VersionKey(String system, String name, String version) {
    this.system = system;
    this.name = name;
    this.version = version;
  }

  public Dependency toDependency() {
    return new Dependency(system, name, version);
  }
}
