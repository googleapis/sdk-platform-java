// Copyright 2021 Google LLC
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

import static org.junit.Assert.assertEquals;

import com.google.api.generator.engine.ast.*;
import com.google.api.generator.testutils.LineFormatter;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import org.junit.Test;

public class ExecutableSampleComposerTest {

  @Test
  public void createExecutableSampleEmptySample() {
    String packageName = "com.google.example";
    String sampleMethodName = "echoClientWait";

    ExecutableSample executableSample =
        new ExecutableSample(packageName, sampleMethodName, new ArrayList<>(), new ArrayList<>());
    String sampleResult = ExecutableSampleComposer.createExecutableSample(executableSample);

    String expected =
        LineFormatter.lines(
            "package com.google.example;\n",
            "\n",
            "public class EchoClientWait {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    echoClientWait();\n",
            "  }\n",
            "\n",
            "  public static void echoClientWait() throws Exception {}\n",
            "}\n");

    assertEquals(sampleResult, expected);
  }

  @Test
  public void createExecutableSampleMethodArgsNoVar() {
    String packageName = "com.google.example";
    String sampleMethodName = "echoClientWait";
    Statement sampleBody =
        ExprStatement.withExpr(SampleUtil.systemOutPrint("Testing " + sampleMethodName));
    ExecutableSample executableSample =
        new ExecutableSample(
            packageName, sampleMethodName, new ArrayList<>(), ImmutableList.of(sampleBody));

    String sampleResult = ExecutableSampleComposer.createExecutableSample(executableSample);
    String expected =
        LineFormatter.lines(
            "package com.google.example;\n",
            "\n",
            "public class EchoClientWait {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    echoClientWait();\n",
            "  }\n",
            "\n",
            "  public static void echoClientWait() throws Exception {\n",
            "    System.out.println(\"Testing echoClientWait\");\n",
            "  }\n",
            "}\n");

    assertEquals(sampleResult, expected);
  }

  @Test
  public void createExecutableSampleMethod() {
    String packageName = "com.google.example";
    String sampleMethodName = "echoClientWait";
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setType(TypeNode.STRING).setName("content").build())
            .setIsDecl(true)
            .build();
    AssignmentExpr varAssignment =
        AssignmentExpr.builder()
            .setVariableExpr(variableExpr)
            .setValueExpr(
                ValueExpr.withValue(StringObjectValue.withValue("Testing " + sampleMethodName)))
            .build();
    Statement sampleBody = ExprStatement.withExpr(SampleUtil.systemOutPrint(variableExpr));
    ExecutableSample executableSample =
        new ExecutableSample(
            packageName,
            sampleMethodName,
            ImmutableList.of(varAssignment),
            ImmutableList.of(sampleBody));

    String sampleResult = ExecutableSampleComposer.createExecutableSample(executableSample);
    String expected =
        LineFormatter.lines(
            "package com.google.example;\n",
            "\n",
            "public class EchoClientWait {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    String content = \"Testing echoClientWait\";\n",
            "    echoClientWait(content);\n",
            "  }\n",
            "\n",
            "  public static void echoClientWait(String content) throws Exception {\n",
            "    System.out.println(content);\n",
            "  }\n",
            "}\n");

    assertEquals(sampleResult, expected);
  }

  @Test
  public void createExecutableSampleMethodMultipleStatements() {
    String packageName = "com.google.example";
    String sampleMethodName = "echoClientWait";
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setType(TypeNode.STRING).setName("content").build())
            .setIsDecl(true)
            .build();
    VariableExpr variableExpr2 =
        VariableExpr.builder()
            .setVariable(
                Variable.builder().setType(TypeNode.STRING).setName("otherContent").build())
            .setIsDecl(true)
            .build();
    AssignmentExpr varAssignment =
        AssignmentExpr.builder()
            .setVariableExpr(variableExpr)
            .setValueExpr(
                ValueExpr.withValue(StringObjectValue.withValue("Testing " + sampleMethodName)))
            .build();
    AssignmentExpr varAssignment2 =
        AssignmentExpr.builder()
            .setVariableExpr(variableExpr2)
            .setValueExpr(
                ValueExpr.withValue(StringObjectValue.withValue("Samples " + sampleMethodName)))
            .build();
    Statement bodyStatement = ExprStatement.withExpr(SampleUtil.systemOutPrint(variableExpr));
    Statement bodyStatement2 = ExprStatement.withExpr(SampleUtil.systemOutPrint(variableExpr2));
    ExecutableSample executableSample =
        new ExecutableSample(
            packageName,
            sampleMethodName,
            ImmutableList.of(varAssignment, varAssignment2),
            ImmutableList.of(bodyStatement, bodyStatement2));

    String sampleResult = ExecutableSampleComposer.createExecutableSample(executableSample);
    String expected =
        LineFormatter.lines(
            "package com.google.example;\n",
            "\n",
            "public class EchoClientWait {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    String content = \"Testing echoClientWait\";\n",
            "    String otherContent = \"Samples echoClientWait\";\n",
            "    echoClientWait(content, otherContent);\n",
            "  }\n",
            "\n",
            "  public static void echoClientWait(String content, String otherContent) throws Exception {\n",
            "    System.out.println(content);\n",
            "    System.out.println(otherContent);\n",
            "  }\n",
            "}\n");

    assertEquals(sampleResult, expected);
  }
}
