package com.google.cloud.model;

import java.util.List;

public class Version {
  private final List<String> licenses;

  public Version(List<String> licenses) {
    this.licenses = licenses;
  }

  public List<String> getLicenses() {
    return licenses;
  }
}
