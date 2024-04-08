package com.google.cloud.model;

import com.google.common.collect.ImmutableList;
import java.util.List;

public class DependencyResponse {
  private final List<Node> nodes;

  public DependencyResponse(List<Node> nodes) {
    this.nodes = nodes;
  }

  public List<Node> getNodes() {
    return ImmutableList.copyOf(nodes);
  }
}
