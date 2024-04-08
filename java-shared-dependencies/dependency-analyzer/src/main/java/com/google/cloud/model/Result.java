package com.google.cloud.model;

public class Result {
  private final Version version;

  public Result(Version version) {
    this.version = version;
  }

  public Version getVersion() {
    return version;
  }
}
