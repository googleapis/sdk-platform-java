package com.google.api.generator.util;

import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.ValueExpr;

public class TestUtils {
  public static ValueExpr generateStringValueExpr(String value) {
    return ValueExpr.builder().setValue(StringObjectValue.withValue(value)).build();
  }
}
