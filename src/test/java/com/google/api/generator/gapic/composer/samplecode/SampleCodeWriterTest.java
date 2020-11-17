package com.google.api.generator.gapic.composer.samplecode;

import static junit.framework.TestCase.assertEquals;

import com.google.api.gax.rpc.ClientSettings;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Method;
import java.lang.reflect.Array;
import java.util.Arrays;
import org.junit.Test;

public class SampleCodeWriterTest {
  @Test
  public void writeSampleCode_statements() {
    TypeNode settingType = TypeNode.withReference(ConcreteReference.withClazz(ClientSettings.class));
    Variable aVar = Variable.builder().setName("clientSettings").setType(settingType).build();
    VariableExpr aVarExpr = VariableExpr.withVariable(aVar);
    MethodInvocationExpr aValueExpr = MethodInvocationExpr.builder()
        .setExprReferenceExpr(MethodInvocationExpr.builder()
        .setMethodName("newBuilder")
        .setStaticReferenceType(settingType)
        .build())
        .setReturnType(settingType)
        .setMethodName("build")
        .build();
    AssignmentExpr assignmentExpr = AssignmentExpr.builder()
        .setVariableExpr(aVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(aValueExpr)
        .build();
    Statement sampleStatement = TryCatchStatement.builder()
        .setTryResourceExpr(createAssignmentExpr("aBool", "false", TypeNode.BOOLEAN))
        .setTryBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
        .setIsSampleCode(true)
        .build();
    String result = SampleCodeWriter.writeSampleCode(
        Arrays.asList(ExprStatement.withExpr(assignmentExpr),
            sampleStatement));
    String expected = "ClientSettings clientSettings = ClientSettings.newBuilder().build();\n"
        + "try (boolean aBool = false) {\n"
        + "  int x = 3;\n"
        + "}";
    assertEquals(expected, result);
  }

  private AssignmentExpr createAssignmentExpr(String varName, String varValue, TypeNode type) {
    Variable variable = Variable.builder().setName(varName).setType(type).build();
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(variable)
            .setIsDecl(true)
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(variableExpr)
        .setValueExpr(ValueExpr.withValue(PrimitiveValue.builder().setType(type).setValue(varValue).build()))
        .build();
  }

}
