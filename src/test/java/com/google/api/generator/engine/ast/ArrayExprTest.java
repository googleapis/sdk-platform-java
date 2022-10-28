package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import com.google.api.generator.util.TestUtils;
import org.junit.Test;

public class ArrayExprTest {

  @Test
  public void validAnonymousArray_sametype() {
    ArrayExpr.Builder exprBuilder =
        ArrayExpr.builder()
            .addExpr(TestUtils.generateStringValueExpr("test1"))
            .addExpr(TestUtils.generateStringValueExpr("test2"))
            .addExpr(TestUtils.generateStringValueExpr("test3"));

    assertThrows(
        IllegalStateException.class,
        () ->
            exprBuilder.addExpr(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setValue("1").setType(TypeNode.INT).build())));
  }

  @Test
  public void validAnonymousArray_emptythrows() {
    ArrayExpr.Builder exprBuilder = ArrayExpr.builder();
    assertThrows(IllegalStateException.class, () -> exprBuilder.build());
  }
}
