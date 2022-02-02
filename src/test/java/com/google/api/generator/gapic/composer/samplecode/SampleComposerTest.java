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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.testutils.LineFormatter;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SampleComposerTest {
  private final String packageName = "com.google.example";
  private final String sampleMethodName = "echoClientMethod";
  private final List<CommentStatement> header =
      Arrays.asList(CommentStatement.withComment(BlockComment.withComment("Apache License")));

  @Test
  public void createExecutableSampleNoSample() {
    assertThrows(
        NullPointerException.class,
        () -> ExecutableSampleComposer.createExecutableSample(null, packageName));
  }

  @Test
  public void createExecutableSampleNoName() {
    Sample sample = Sample.builder().build();

    assertThrows(
        IllegalStateException.class,
        () -> ExecutableSampleComposer.createExecutableSample(sample, packageName));
  }

  @Test
  public void createExecutableSampleEmptyNoRegionTags() {
    Sample sample = Sample.builder().setName(sampleMethodName).setFileHeader(header).build();

    String sampleResult = ExecutableSampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "/*\n",
            " * Apache License\n",
            " */\n",
            "package com.google.example;\n",
            "\n",
            "public class EchoClientMethod {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    echoClientMethod();\n",
            "  }\n",
            "\n",
            "  public static void echoClientMethod() throws Exception {}\n",
            "}\n");

    assertEquals(expected, sampleResult);
  }

  @Test
  public void createExecutableSampleEmptyNoHeader() {
    Sample sample =
        Sample.builder()
            .setName(sampleMethodName)
            .setRegionTag("Create_Executable_Sample_Empty_NoHeader")
            .build();

    String sampleResult = ExecutableSampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "\npackage com.google.example;\n",
            "// [START Create_Executable_Sample_Empty_NoHeader]\n",
            "\n",
            "public class EchoClientMethod {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    echoClientMethod();\n",
            "  }\n",
            "\n",
            "  public static void echoClientMethod() throws Exception {}\n",
            "}\n",
            "// [END Create_Executable_Sample_Empty_NoHeader]");

    assertEquals(expected, sampleResult);
  }

  @Test
  public void createExecutableSampleEmptyStatementSample() {
    Sample sample =
        Sample.builder()
            .setName(sampleMethodName)
            .setFileHeader(header)
            .setRegionTag("Create_Executable_Sample_Empty_Statement_Sample")
            .build();

    String sampleResult = ExecutableSampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "/*\n",
            " * Apache License\n",
            " */\n",
            "package com.google.example;\n",
            "// [START Create_Executable_Sample_Empty_Statement_Sample]\n",
            "\n",
            "public class EchoClientMethod {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    echoClientMethod();\n",
            "  }\n",
            "\n",
            "  public static void echoClientMethod() throws Exception {}\n",
            "}\n",
            "// [END Create_Executable_Sample_Empty_Statement_Sample]");

    assertEquals(expected, sampleResult);
  }

  @Test
  public void createExecutableSampleMethodArgsNoVar() {
    Statement sampleBody = ExprStatement.withExpr(systemOutPrint("Testing " + sampleMethodName));
    Sample sample =
        Sample.builder()
            .setName(sampleMethodName)
            .setBody(ImmutableList.of(sampleBody))
            .setFileHeader(header)
            .setRegionTag("Create_Executable_Sample_Method_Args_NoVar")
            .build();

    String sampleResult = ExecutableSampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "/*\n",
            " * Apache License\n",
            " */\n",
            "package com.google.example;\n",
            "// [START Create_Executable_Sample_Method_Args_NoVar]\n",
            "\n",
            "public class EchoClientMethod {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    echoClientMethod();\n",
            "  }\n",
            "\n",
            "  public static void echoClientMethod() throws Exception {\n",
            "    System.out.println(\"Testing echoClientMethod\");\n",
            "  }\n",
            "}\n",
            "// [END Create_Executable_Sample_Method_Args_NoVar]");

    assertEquals(expected, sampleResult);
  }

  @Test
  public void createExecutableSampleMethod() {
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
    Statement sampleBody = ExprStatement.withExpr(systemOutPrint(variableExpr));
    Sample sample =
        Sample.builder()
            .setName(sampleMethodName)
            .setBody(ImmutableList.of(sampleBody))
            .setVariableAssignments(ImmutableList.of(varAssignment))
            .setFileHeader(header)
            .setRegionTag("Create_Executable_Sample_Method")
            .build();

    String sampleResult = ExecutableSampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "/*\n",
            " * Apache License\n",
            " */\n",
            "package com.google.example;\n",
            "// [START Create_Executable_Sample_Method]\n",
            "\n",
            "public class EchoClientMethod {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    String content = \"Testing echoClientMethod\";\n",
            "    echoClientMethod(content);\n",
            "  }\n",
            "\n",
            "  public static void echoClientMethod(String content) throws Exception {\n",
            "    System.out.println(content);\n",
            "  }\n",
            "}\n",
            "// [END Create_Executable_Sample_Method]");

    assertEquals(expected, sampleResult);
  }

  @Test
  public void createExecutableSampleMethodMultipleStatements() {
    VariableExpr strVariableExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setType(TypeNode.STRING).setName("content").build())
            .setIsDecl(true)
            .build();
    VariableExpr intVariableExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setType(TypeNode.INT).setName("num").build())
            .setIsDecl(true)
            .build();
    VariableExpr objVariableExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setType(TypeNode.OBJECT).setName("thing").build())
            .setIsDecl(true)
            .build();
    AssignmentExpr strVarAssignment =
        AssignmentExpr.builder()
            .setVariableExpr(strVariableExpr)
            .setValueExpr(
                ValueExpr.withValue(
                    StringObjectValue.withValue("Testing ".concat(sampleMethodName))))
            .build();
    AssignmentExpr intVarAssignment =
        AssignmentExpr.builder()
            .setVariableExpr(intVariableExpr)
            .setValueExpr(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setType(TypeNode.INT).setValue("2").build()))
            .build();
    AssignmentExpr objVarAssignment =
        AssignmentExpr.builder()
            .setVariableExpr(objVariableExpr)
            .setValueExpr(NewObjectExpr.builder().setType(TypeNode.OBJECT).build())
            .build();

    Statement strBodyStatement = ExprStatement.withExpr(systemOutPrint(strVariableExpr));
    Statement intBodyStatement = ExprStatement.withExpr(systemOutPrint(intVariableExpr));
    Statement objBodyStatement =
        ExprStatement.withExpr(
            systemOutPrint(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(objVariableExpr.toBuilder().setIsDecl(false).build())
                    .setMethodName("response")
                    .build()));
    Sample sample =
        Sample.builder()
            .setName(sampleMethodName)
            .setBody(ImmutableList.of(strBodyStatement, intBodyStatement, objBodyStatement))
            .setVariableAssignments(
                ImmutableList.of(strVarAssignment, intVarAssignment, objVarAssignment))
            .setFileHeader(header)
            .setRegionTag("Create_Executable_Sample_Method_Multiple_Statements")
            .build();

    String sampleResult = ExecutableSampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "/*\n",
            " * Apache License\n",
            " */\n",
            "package com.google.example;\n",
            "// [START Create_Executable_Sample_Method_Multiple_Statements]\n",
            "\n",
            "public class EchoClientMethod {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    String content = \"Testing echoClientMethod\";\n",
            "    int num = 2;\n",
            "    Object thing = new Object();\n",
            "    echoClientMethod(content, num, thing);\n",
            "  }\n",
            "\n",
            "  public static void echoClientMethod(String content, int num, Object thing) throws Exception {\n",
            "    System.out.println(content);\n",
            "    System.out.println(num);\n",
            "    System.out.println(thing.response());\n",
            "  }\n",
            "}\n",
            "// [END Create_Executable_Sample_Method_Multiple_Statements]");
    assertEquals(expected, sampleResult);
  }

  private Expr systemOutPrint(MethodInvocationExpr response) {
    return composeSystemOutPrint(response);
  }

  private static MethodInvocationExpr systemOutPrint(String content) {
    return composeSystemOutPrint(ValueExpr.withValue(StringObjectValue.withValue(content)));
  }

  private static MethodInvocationExpr systemOutPrint(VariableExpr variableExpr) {
    return composeSystemOutPrint(variableExpr.toBuilder().setIsDecl(false).build());
  }

  private static MethodInvocationExpr composeSystemOutPrint(Expr content) {
    VaporReference out =
        VaporReference.builder()
            .setEnclosingClassNames("System")
            .setName("out")
            .setPakkage("java.lang")
            .build();
    return MethodInvocationExpr.builder()
        .setStaticReferenceType(TypeNode.withReference(out))
        .setMethodName("println")
        .setArguments(content)
        .build();
  }
}
