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

package com.google.api.generator.gapic.composer.samplecode;

import static junit.framework.TestCase.assertEquals;

import com.google.api.gax.rpc.ClientSettings;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import java.util.Arrays;
import org.junit.Test;

public class SampleCodeWriterTest {
  @Test
  public void writeSampleCode_statements() {
    TypeNode settingType =
        TypeNode.withReference(ConcreteReference.withClazz(ClientSettings.class));
    Variable aVar = Variable.builder().setName("clientSettings").setType(settingType).build();
    VariableExpr aVarExpr = VariableExpr.withVariable(aVar);
    MethodInvocationExpr aValueExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setMethodName("newBuilder")
                    .setStaticReferenceType(settingType)
                    .build())
            .setReturnType(settingType)
            .setMethodName("build")
            .build();
    AssignmentExpr assignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(aVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(aValueExpr)
            .build();
    Statement sampleStatement =
        TryCatchStatement.builder()
            .setTryResourceExpr(createAssignmentExpr("aBool", "false", TypeNode.BOOLEAN))
            .setTryBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
            .setIsSampleCode(true)
            .build();
    String result = SampleCodeWriter.write(ExprStatement.withExpr(assignmentExpr), sampleStatement);
    String expected =
        "ClientSettings clientSettings = ClientSettings.newBuilder().build();\n"
            + "try (boolean aBool = false) {\n"
            + "  int x = 3;\n"
            + "}";
    assertEquals(expected, result);
  }

  private AssignmentExpr createAssignmentExpr(String varName, String varValue, TypeNode type) {
    Variable variable = Variable.builder().setName(varName).setType(type).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();
    return AssignmentExpr.builder()
        .setVariableExpr(variableExpr)
        .setValueExpr(
            ValueExpr.withValue(PrimitiveValue.builder().setType(type).setValue(varValue).build()))
        .build();
  }
}
