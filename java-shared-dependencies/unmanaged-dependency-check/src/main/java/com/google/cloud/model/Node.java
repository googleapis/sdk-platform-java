package com.google.cloud.model;

public class Node {
  private final VersionKey versionKey;
  private final Relation relation;

  public Node(VersionKey versionKey, Relation relation) {
    this.versionKey = versionKey;
    this.relation = relation;
  }

  public VersionKey getVersionKey() {
    return versionKey;
  }

  public Relation getRelation() {
    return relation;
  }
}
