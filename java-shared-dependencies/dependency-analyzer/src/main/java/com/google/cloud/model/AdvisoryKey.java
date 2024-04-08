package com.google.cloud.model;

public class AdvisoryKey {
  private final String id;

  public AdvisoryKey(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return "AdvisoryKey{" +
        "id='" + id + '\'' +
        '}';
  }
}
