package com.google.api.generator.util;

import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.TypeNode.TypeKind;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;

public class TestUtils {
  public static ValueExpr generateStringValueExpr(String value) {
    return ValueExpr.builder().setValue(StringObjectValue.withValue(value)).build();
  }

  public static VariableExpr generateClassValueExpr(String clazzName) {
    return VariableExpr.builder()
        .setVariable(Variable.builder().setType(TypeNode.CLASS_OBJECT).setName("class").build())
        .setStaticReferenceType(
            TypeNode.builder()
                .setReference(
                    VaporReference.builder().setName(clazzName).setPakkage("com.test").build())
                .setTypeKind(TypeKind.OBJECT)
                .build())
        .build();
  }
}
