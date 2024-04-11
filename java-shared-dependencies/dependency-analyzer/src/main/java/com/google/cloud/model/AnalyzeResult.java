package com.google.cloud.model;

public enum AnalyzeResult {
  PASS("Pass, package has no known vulnerabilities and non-compliant licenses."),
  FAIL("Fail, package has known vulnerabilities or non-compliant licenses.");

  private final String message;

  AnalyzeResult(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return String.format("Analyze result: %s", message);
  }
}
