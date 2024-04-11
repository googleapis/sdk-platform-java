package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * A resolved dependency graph for the given package version.
 * <p>
 * For more information, please refer to <a href="https://docs.deps.dev/api/v3/#getdependencies">GetDependencies</a>.
 */
public class DependencyResponse {

  // The first node is the root of the graph.
  private final List<Node> nodes;

  public DependencyResponse(List<Node> nodes) {
    this.nodes = nodes;
  }

  public List<Node> getNodes() {
    return ImmutableList.copyOf(nodes);
  }
}
