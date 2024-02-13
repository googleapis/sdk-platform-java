package com.google.cloud.model;

import java.util.List;

public class DependencyResponse {
  private List<Node> nodes;

  public DependencyResponse(List<Node> nodes) {
    this.nodes = nodes;
  }

  public List<Node> getNodes() {
    return nodes;
  }
}
