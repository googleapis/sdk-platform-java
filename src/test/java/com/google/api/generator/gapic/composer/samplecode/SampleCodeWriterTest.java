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

import com.google.api.gax.rpc.ClientSettings;
import com.google.api.generator.engine.ast.*;
import com.google.api.generator.testutils.LineFormatter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class SampleCodeWriterTest {
  private static String packageName;
  private static MethodInvocationExpr methodInvocationExpr;
  private static AssignmentExpr assignmentExpr;
  private static Statement sampleStatement;
  private static ClassDefinition classDefinition;
  private static String className;

  @BeforeClass
  public static void setup() {
    TypeNode settingType =
        TypeNode.withReference(ConcreteReference.withClazz(ClientSettings.class));
    Variable aVar = Variable.builder().setName("clientSettings").setType(settingType).build();
    VariableExpr aVarExpr = VariableExpr.withVariable(aVar);
    methodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setMethodName("newBuilder")
                    .setStaticReferenceType(settingType)
                    .build())
            .setReturnType(settingType)
            .setMethodName("build")
            .build();
    assignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(aVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(methodInvocationExpr)
            .build();
    sampleStatement =
        TryCatchStatement.builder()
            .setTryResourceExpr(createAssignmentExpr("aBool", "false", TypeNode.BOOLEAN))
            .setTryBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
            .setIsSampleCode(true)
            .build();

    MethodDefinition methdod =
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setReturnType(TypeNode.VOID)
            .setName("main")
            .setArguments(
                VariableExpr.builder()
                    .setVariable(
                        Variable.builder().setType(TypeNode.STRING_ARRAY).setName("args").build())
                    .setIsDecl(true)
                    .build())
            .setBody(Arrays.asList(sampleStatement))
            .build();

    packageName = "com.google.example";
    className = "SampleClassName";
    classDefinition =
        ClassDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setPackageString(packageName)
            .setName(className)
            .setMethods(Arrays.asList(methdod))
            .build();
  }

  @Test
  public void writeSampleCode_statements() {
    String result = SampleCodeWriter.write(ExprStatement.withExpr(assignmentExpr), sampleStatement);
    String expected =
        "ClientSettings clientSettings = ClientSettings.newBuilder().build();\n"
            + "try (boolean aBool = false) {\n"
            + "  int x = 3;\n"
            + "}";
    assertEquals(expected, result);
  }

  @Test
  public void writeSampleCode_methodInvocation() {
    String result = SampleCodeWriter.write(methodInvocationExpr);
    String expected = "ClientSettings.newBuilder().build()";
    assertEquals(expected, result);
  }

  @Test
  public void writeSampleCode_classDefinition() {
    String result = SampleCodeWriter.write(classDefinition);
    String expected =
        LineFormatter.lines(
            "package " + packageName + ";\n",
            "\n",
            "public class " + className + " {\n",
            "\n",
            "  public static void main(String[] args) {\n",
            "    try (boolean aBool = false) {\n",
            "      int x = 3;\n",
            "    }\n",
            "  }\n",
            "}\n");

    assertEquals(expected, result);
  }

  private static AssignmentExpr createAssignmentExpr(
      String varName, String varValue, TypeNode type) {
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
