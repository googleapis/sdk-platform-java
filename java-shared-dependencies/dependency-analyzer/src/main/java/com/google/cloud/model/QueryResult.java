package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Information about multiple package versions.
 * <p>
 * For more information, please refer to <a href="https://docs.deps.dev/api/v3/#query">Query</a>
 */
public class QueryResult {
  private final List<Result> results;

  public QueryResult(List<Result> results) {
    this.results = results;
  }

  public List<Result> getResults() {
    return ImmutableList.copyOf(results);
  }
}
