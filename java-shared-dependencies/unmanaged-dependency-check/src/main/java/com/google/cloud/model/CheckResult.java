package com.google.cloud.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CheckResult {
  private final Map<String, Pair<List<String>, List<Advisory>>> result;

  public CheckResult() {
    result = new HashMap<>();
  }

  public void add(String coordinate, List<String> licenses, List<Advisory> advisories) {
    result.put(coordinate, ImmutablePair.of(licenses, advisories));
  }

}
