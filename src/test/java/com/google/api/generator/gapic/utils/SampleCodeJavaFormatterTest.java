// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.gapic.utils;

import static junit.framework.TestCase.assertEquals;

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Value;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.utils.SampleCodeJavaFormatter.FormatException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class SampleCodeJavaFormatterTest {

  @Test
  public void validFormatSampleCode_tryCatchStatement() {
    List<Statement> statements = Arrays.asList(createTryCatchSampleCode());
    String result = SampleCodeJavaFormatter.format(writeStatements(statements));
    String expected =
        String.format(createLines(3), "try (boolean condition = false) {\n", "  int x = 3;\n", "}");
    assertEquals(expected, result);
  }

  @Test
  public void validFormatSampleCode_longLineStatement() {
    TypeNode type =
        TypeNode.withReference(
            VaporReference.builder()
                .setPakkage("com.google.pubsub.v1")
                .setName("SubscriptionAdminSettings")
                .build());
    VariableExpr varDclExpr = createVariableDeclExpr("subscriptionAdminSettings", type);
    VariableExpr varExpr = createVariableExpr("SubscriptionAdminSettings", type);
    MethodInvocationExpr firstMethodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("newBuilder")
            .setExprReferenceExpr(varExpr)
            .build();
    MethodInvocationExpr secondMethodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("setEndpoint")
            .setExprReferenceExpr(firstMethodExpr)
            .setArguments(Arrays.asList(createVariableExpr("myEndpoint", TypeNode.STRING)))
            .build();
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("build")
            .setExprReferenceExpr(secondMethodExpr)
            .setReturnType(type)
            .build();
    List<Statement> statements =
        Arrays.asList(
            ExprStatement.withExpr(
                AssignmentExpr.builder()
                    .setVariableExpr(varDclExpr)
                    .setValueExpr(methodExpr)
                    .build()));
    String result = SampleCodeJavaFormatter.format(writeStatements(statements));
    String expected =
        String.format(
            createLines(2),
            "SubscriptionAdminSettings subscriptionAdminSettings =\n",
            "    SubscriptionAdminSettings.newBuilder().setEndpoint(myEndpoint).build();");
    assertEquals(expected, result);
  }

  @Test(expected = FormatException.class)
  public void invalidFormatSampleCode_nonStatement() {
    SampleCodeJavaFormatter.format("abc");
  }

  /** =============================== HELPERS =============================== */
  private static String createLines(int numLines) {
    return new String(new char[numLines]).replace("\0", "%s");
  }

  private static Statement createTryCatchSampleCode() {
    TryCatchStatement tryCatch =
        TryCatchStatement.builder()
            .setTryResourceExpr(createAssignmentExpr("condition", "false", TypeNode.BOOLEAN))
            .setTryBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
            .setIsSampleCode(true)
            .build();
    return tryCatch;
  }

  private static AssignmentExpr createAssignmentExpr(
      String variableName, String value, TypeNode type) {
    VariableExpr variableExpr = createVariableDeclExpr(variableName, type);
    Value val = PrimitiveValue.builder().setType(type).setValue(value).build();
    Expr valueExpr = ValueExpr.builder().setValue(val).build();
    return AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
  }

  private static VariableExpr createVariableDeclExpr(String variableName, TypeNode type) {
    return createVariableExpr(variableName, type, true);
  }

  private static VariableExpr createVariableExpr(String variableName, TypeNode type) {
    return createVariableExpr(variableName, type, false);
  }

  private static VariableExpr createVariableExpr(
      String variableName, TypeNode type, boolean isDecl) {
    return VariableExpr.builder()
        .setVariable(createVariable(variableName, type))
        .setIsDecl(isDecl)
        .build();
  }

  private static Variable createVariable(String variableName, TypeNode type) {
    return Variable.builder().setName(variableName).setType(type).build();
  }

  private static String writeStatements(List<Statement> statements) {
    JavaWriterVisitor javaWriterVisitor = new JavaWriterVisitor();
    for (Statement statement : statements) {
      statement.accept(javaWriterVisitor);
    }
    return javaWriterVisitor.write();
  }
}
