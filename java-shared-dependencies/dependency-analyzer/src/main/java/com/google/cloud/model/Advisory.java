package com.google.cloud.model;

import java.util.Arrays;

/**
 * GetAdvisory returns information about security advisories hosted by OSV.
 * <p>
 * For more information, please refer to <a href="https://docs.deps.dev/api/v3/#getadvisory">GetAdvisory</a>.
 */
public class Advisory {
  private final AdvisoryKey advisoryKey;
  /**
   * The URL of the security advisory.
   */
  private final String url;
  /**
   * A brief human-readable description.
   */
  private final String title;
  /**
   * Other identifiers used for the advisory, including CVEs.
   */
  private final String[] aliases;
  /**
   * The severity of the advisory as a CVSS v3 score in the range [0,10].
   * A higher score represents greater severity.
   */
  private final double cvss3Score;
  /**
   * The severity of the advisory as a CVSS v3 vector string.
   */
  private final String cvss3Vector;

  public Advisory(AdvisoryKey advisoryKey, String url, String title, String[] aliases,
      double cvss3Score, String cvss3Vector) {
    this.advisoryKey = advisoryKey;
    this.url = url;
    this.title = title;
    this.aliases = aliases;
    this.cvss3Score = cvss3Score;
    this.cvss3Vector = cvss3Vector;
  }

  @Override
  public String toString() {
    return "Advisory{" +
        "advisoryKey=" + advisoryKey +
        ", url='" + url + '\'' +
        ", title='" + title + '\'' +
        ", aliases=" + Arrays.toString(aliases) +
        ", cvss3Score=" + cvss3Score +
        ", cvss3Vector='" + cvss3Vector + '\'' +
        '}';
  }
}
