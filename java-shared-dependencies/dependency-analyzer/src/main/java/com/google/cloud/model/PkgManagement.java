package com.google.cloud.model;

public enum PkgManagement {
  MAVEN;

  public static boolean checkDependencyName(PkgManagement system, String name) {
    if (system == MAVEN) {
      String[] strs = name.split(":");
      return strs.length == 2;
    }

    return false;
  }
}
