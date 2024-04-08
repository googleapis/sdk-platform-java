package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.stream.Collectors;

public class QueryResult {
  private final List<Result> results;

  public QueryResult(List<Result> versions) {
    this.results = versions;
  }

  public List<Result> getResults() {
    return ImmutableList.copyOf(results);
  }
}
