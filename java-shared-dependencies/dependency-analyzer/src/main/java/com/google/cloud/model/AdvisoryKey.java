package com.google.cloud.model;

import com.google.common.base.Objects;

/**
 * The identifier for the security advisory.
 */
public class AdvisoryKey {

  /**
   * The OSV identifier for the security advisory.
   */
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AdvisoryKey)) {
      return false;
    }
    AdvisoryKey that = (AdvisoryKey) o;
    return Objects.equal(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
