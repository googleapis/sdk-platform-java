package com.google.cloud.model;

import java.util.Arrays;

public class Advisory {

  private final AdvisoryKey advisoryKey;
  private final String url;
  private final String title;
  private final String[] aliases;
  private final double cvss3Score;
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
