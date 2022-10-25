package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import com.google.api.generator.util.TestUtils;
import org.junit.Test;

public class AnonymousArrayAnnotationExprTest {

  @Test
  public void validAnonymousArray_sametype() {
    AnonymousArrayAnnotationExpr.Builder exprBuilder =
        AnonymousArrayAnnotationExpr.builder()
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
    AnonymousArrayAnnotationExpr.Builder exprBuilder = AnonymousArrayAnnotationExpr.builder();
    assertThrows(IllegalStateException.class, () -> exprBuilder.build());
  }
}
