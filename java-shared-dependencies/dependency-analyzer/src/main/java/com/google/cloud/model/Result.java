package com.google.cloud.model;

/**
 * Result matching the query. A wrapper around {@link Version}.
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
