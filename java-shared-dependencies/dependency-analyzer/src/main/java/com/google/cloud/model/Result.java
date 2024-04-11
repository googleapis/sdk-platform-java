package com.google.cloud.model;

/**
 * A single Result within a {@link QueryResult}, also a wrapper around {@link Version}.
 */
public class Result {
  private final Version version;

  public Result(Version version) {
    this.version = version;
  }

  public Version getVersion() {
    return version;
  }
}
