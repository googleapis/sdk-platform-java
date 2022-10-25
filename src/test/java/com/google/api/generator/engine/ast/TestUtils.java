package com.google.api.generator.engine.ast;

public class TestUtils {
  public static ValueExpr generateStringValueExpr(String value) {
    return ValueExpr.builder().setValue(StringObjectValue.withValue(value)).build();
  }
}
