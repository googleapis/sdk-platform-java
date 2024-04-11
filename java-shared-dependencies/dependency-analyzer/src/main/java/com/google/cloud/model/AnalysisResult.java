package com.google.cloud.model;

public enum AnalysisResult {
  PASS("Pass, package has no known vulnerabilities and non-compliant licenses."),
  FAIL("Fail, package has known vulnerabilities or non-compliant licenses.");

  private final String message;

  AnalysisResult(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return String.format("Analyze result: %s", message);
  }
}
