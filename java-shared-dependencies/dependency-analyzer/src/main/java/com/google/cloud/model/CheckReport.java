package com.google.cloud.model;

import java.util.List;

public class CheckReport {
  private final List<PackageInfo> result;

  public CheckReport(List<PackageInfo> result) {
    this.result = result;
  }
}
