package com.google.cloud.model;

import java.util.ArrayList;
import java.util.List;

public class CheckReport {
  private final List<PackageInfo> result;

  public CheckReport() {
    this.result = new ArrayList<>();
  }

  public void addPackageInfo(PackageInfo info) {
    this.result.add(info);
  }
}
