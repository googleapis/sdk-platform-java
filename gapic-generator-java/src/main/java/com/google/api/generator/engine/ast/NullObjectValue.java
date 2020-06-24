package com.google.api.generator.engine.ast;


public class NullObjectValue implements ObjectValue {
  private static final String NULL_VALUE = "null";
  @Override
  public TypeNode type() {
    return null;
  }

  @Override
  public String value() {
    return NULL_VALUE;
  }

  @Override
  public String toString() {
    return value();
  }

  public static NullObjectValue create() {
    return new NullObjectValue();
  }
}
