package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class MethodInvocationExprTest {

  @Test
  public void validBuildMethodInvocationExpr() {
    Reference stringRef = ConcreteReference.withClazz(String.class);
    TypeNode returnType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ArrayList.class)
                .setGenerics(Arrays.asList(stringRef))
                .build());

    MethodInvocationExpr.builder().setMethodName("getSomeList").setReturnType(returnType).build();
    // No exception thrown, we're good.
  }

  @Test
  public void invalidBuildMethodInvocationExpr_nullReturnType() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          MethodInvocationExpr.builder()
              .setMethodName("invalid")
              .setReturnType(TypeNode.NULL)
              .build();
        });
  }

  @Test
  public void invalidBuildMethodInvocationExpr_staticAndExprBoth() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          Variable variable = Variable.builder().setType(TypeNode.INT).setName("someVar").build();
          VariableExpr varExpr = VariableExpr.builder().setVariable(variable).build();
          MethodInvocationExpr.builder()
              .setMethodName("foobar")
              .setGenerics(Arrays.asList(ConcreteReference.withClazz(String.class)))
              .setStaticReferenceName("someClass")
              .setExprReferenceExpr(varExpr)
              .setReturnType(TypeNode.STRING)
              .build();
        });
  }
}
