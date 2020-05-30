
package com.google.api.generator.engine.ast;

public interface AstNode {
  /** Writes the syntatically-correct Java code representation of this node. */
  public String write();
}
