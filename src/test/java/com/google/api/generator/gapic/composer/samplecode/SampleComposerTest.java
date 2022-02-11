// Copyright 2022 Google LLC
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
import static org.junit.Assert.assertThrows;

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
import com.google.api.generator.gapic.model.RegionTag;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.testutils.LineFormatter;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class SampleComposerTest {
  private final String packageName = "com.google.example";
  private final List<Statement> header =
      Arrays.asList(CommentStatement.withComment(BlockComment.withComment("Apache License")));
  private final RegionTag.Builder regionTag =
      RegionTag.builder().setApiShortName("echo").setApiVersion("v1beta").setServiceName("echo");

  @Test
  public void createExecutableSampleNoSample() {
    assertThrows(
        NullPointerException.class, () -> SampleComposer.createExecutableSample(null, packageName));
  }

  @Test
  public void createInlineSampleNoSample() {
    assertThrows(NullPointerException.class, () -> SampleComposer.createInlineSample(null));
  }

  @Test
  public void createInlineSample() {
    List<Statement> sampleBody = Arrays.asList(ExprStatement.withExpr(systemOutPrint("testing")));
    String sampleResult = SampleComposer.createInlineSample(sampleBody);
    String expected =
        LineFormatter.lines(
            "// This snippet has been automatically generated for illustrative purposes only.\n",
            "// It may require modifications to work in your environment.\n",
            "System.out.println(\"testing\");");

    assertEquals(expected, sampleResult);
  }

  @Test
  public void createExecutableSampleMissingRegionTagAttributes() {
    Sample noApiShortNameSample =
        Sample.builder()
            .setRegionTag(
                regionTag
                    .setApiShortName("")
                    .setRpcName("createExecutableSample")
                    .setOverloadDisambiguation("MissingApiShortName")
                    .build())
            .build();

    assertThrows(
        IllegalStateException.class,
        () -> SampleComposer.createExecutableSample(noApiShortNameSample, packageName));

    Sample noApiVersionSample =
        Sample.builder()
            .setRegionTag(
                regionTag
                    .setApiVersion("")
                    .setRpcName("createExecutableSample")
                    .setOverloadDisambiguation("MissingApiVersion")
                    .build())
            .build();

    assertThrows(
        IllegalStateException.class,
        () -> SampleComposer.createExecutableSample(noApiVersionSample, packageName));
  }

  @Test
  public void createExecutableSampleNoHeader() {
    Sample sample =
        Sample.builder()
            .setRegionTag(
                regionTag
                    .setRpcName("createExecutableSample")
                    .setOverloadDisambiguation("NoHeader")
                    .build())
            .build();
    assertThrows(
        IllegalStateException.class,
        () -> SampleComposer.createExecutableSample(sample, packageName));
  }

  @Test
  public void createExecutableSampleEmptyStatementSample() {
    Sample sample =
        Sample.builder()
            .setFileHeader(header)
            .setRegionTag(
                regionTag
                    .setRpcName("createExecutableSample")
                    .setOverloadDisambiguation("EmptyStatementSample")
                    .build())
            .build();

    String sampleResult = SampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "/*\n",
            " * Apache License\n",
            " */\n",
            "package com.google.example;\n",
            "\n",
            "// [START echo_v1beta_generated_echo_createexecutablesample_emptystatementsample]\n",
            "public class CreateExecutableSampleEmptyStatementSample {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    createExecutableSampleEmptyStatementSample();\n",
            "  }\n",
            "\n",
            "  public static void createExecutableSampleEmptyStatementSample() throws Exception {\n",
            "    // This snippet has been automatically generated for illustrative purposes only.\n",
            "    // It may require modifications to work in your environment.\n",
            "  }\n",
            "}\n",
            "// [END echo_v1beta_generated_echo_createexecutablesample_emptystatementsample]");

    assertEquals(expected, sampleResult);
  }

  @Test
  public void createExecutableSampleMethodArgsNoVar() {
    Statement sampleBody =
        ExprStatement.withExpr(systemOutPrint("Testing CreateExecutableSampleMethodArgsNoVar"));
    Sample sample =
        Sample.builder()
            .setBody(ImmutableList.of(sampleBody))
            .setFileHeader(header)
            .setRegionTag(
                regionTag
                    .setRpcName("createExecutableSample")
                    .setOverloadDisambiguation("MethodArgsNoVar")
                    .build())
            .build();

    String sampleResult = SampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "/*\n",
            " * Apache License\n",
            " */\n",
            "package com.google.example;\n",
            "\n",
            "// [START echo_v1beta_generated_echo_createexecutablesample_methodargsnovar]\n",
            "public class CreateExecutableSampleMethodArgsNoVar {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    createExecutableSampleMethodArgsNoVar();\n",
            "  }\n",
            "\n",
            "  public static void createExecutableSampleMethodArgsNoVar() throws Exception {\n",
            "    // This snippet has been automatically generated for illustrative purposes only.\n",
            "    // It may require modifications to work in your environment.\n",
            "    System.out.println(\"Testing CreateExecutableSampleMethodArgsNoVar\");\n",
            "  }\n",
            "}\n",
            "// [END echo_v1beta_generated_echo_createexecutablesample_methodargsnovar]");

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
                ValueExpr.withValue(
                    StringObjectValue.withValue("Testing CreateExecutableSampleMethod")))
            .build();
    Statement sampleBody = ExprStatement.withExpr(systemOutPrint(variableExpr));
    Sample sample =
        Sample.builder()
            .setBody(ImmutableList.of(sampleBody))
            .setVariableAssignments(ImmutableList.of(varAssignment))
            .setFileHeader(header)
            .setRegionTag(regionTag.setRpcName("createExecutableSample").build())
            .build();

    String sampleResult = SampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "/*\n",
            " * Apache License\n",
            " */\n",
            "package com.google.example;\n",
            "\n",
            "// [START echo_v1beta_generated_echo_createexecutablesample]\n",
            "public class CreateExecutableSample {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    String content = \"Testing CreateExecutableSampleMethod\";\n",
            "    createExecutableSample(content);\n",
            "  }\n",
            "\n",
            "  public static void createExecutableSample(String content) throws Exception {\n",
            "    // This snippet has been automatically generated for illustrative purposes only.\n",
            "    // It may require modifications to work in your environment.\n",
            "    System.out.println(content);\n",
            "  }\n",
            "}\n",
            "// [END echo_v1beta_generated_echo_createexecutablesample]");

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
                    StringObjectValue.withValue(
                        "Testing CreateExecutableSampleMethodMultipleStatements")))
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
            .setBody(ImmutableList.of(strBodyStatement, intBodyStatement, objBodyStatement))
            .setVariableAssignments(
                ImmutableList.of(strVarAssignment, intVarAssignment, objVarAssignment))
            .setFileHeader(header)
            .setRegionTag(
                regionTag
                    .setRpcName("createExecutableSample")
                    .setOverloadDisambiguation("MethodMultipleStatements")
                    .build())
            .build();

    String sampleResult = SampleComposer.createExecutableSample(sample, packageName);
    String expected =
        LineFormatter.lines(
            "/*\n",
            " * Apache License\n",
            " */\n",
            "package com.google.example;\n",
            "\n",
            "// [START echo_v1beta_generated_echo_createexecutablesample_methodmultiplestatements]\n",
            "public class CreateExecutableSampleMethodMultipleStatements {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    String content = \"Testing CreateExecutableSampleMethodMultipleStatements\";\n",
            "    int num = 2;\n",
            "    Object thing = new Object();\n",
            "    createExecutableSampleMethodMultipleStatements(content, num, thing);\n",
            "  }\n",
            "\n",
            "  public static void createExecutableSampleMethodMultipleStatements(\n",
            "      String content, int num, Object thing) throws Exception {\n",
            "    // This snippet has been automatically generated for illustrative purposes only.\n",
            "    // It may require modifications to work in your environment.\n",
            "    System.out.println(content);\n",
            "    System.out.println(num);\n",
            "    System.out.println(thing.response());\n",
            "  }\n",
            "}\n",
            "// [END echo_v1beta_generated_echo_createexecutablesample_methodmultiplestatements]");
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
