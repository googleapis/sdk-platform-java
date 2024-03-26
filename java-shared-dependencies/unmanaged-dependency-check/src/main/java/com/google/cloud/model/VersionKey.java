package com.google.cloud.model;

import org.apache.bcel.generic.NEW;

public class VersionKey {
  private final String system;
  private final String name;
  private final String version;

  public VersionKey(String system, String name, String version) {
    this.system = system;
    this.name = name;
    this.version = version;
  }

  public MavenCoordinate toMavenCoordinate() {
    String[] splits = name.split(":");
    return new MavenCoordinate(splits[0], splits[1], version);
  }
}
