package com.google.api.generator.engine.ast;

public class BreakStatement implements Statement {
  private BreakStatement() {}

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static BreakStatement create() {
    return new BreakStatement();
  }
}
