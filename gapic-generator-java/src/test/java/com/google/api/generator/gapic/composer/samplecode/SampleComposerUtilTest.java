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

import static com.google.cloud.tools.snippetgen.configlanguage.v1.Type.ScalarType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.RegionTag;
import com.google.api.generator.gapic.model.Sample;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Expression;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Statement;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Type;
import org.junit.Test;

public class SampleComposerUtilTest {
  private static final String SHOWCASE_PACKAGE_NAME = "com.google.showcase.v1beta1";
  TypeNode clientType =
      TypeNode.withReference(
          VaporReference.builder().setName("EchoClient").setPakkage(SHOWCASE_PACKAGE_NAME).build());
  VariableExpr echoClientVariableExpr =
      VariableExpr.withVariable(
          Variable.builder().setName("echoClient").setType(clientType).build());
  VariableExpr stringVariableExpr =
      VariableExpr.withVariable(
          Variable.builder().setName("String").setType(TypeNode.STRING).build());
  VariableExpr intVariableExpr =
      VariableExpr.withVariable(Variable.builder().setName("INT").setType(TypeNode.INT).build());
  Sample echoClientSample =
      Sample.builder()
          .setRegionTag(
              RegionTag.builder().setServiceName("echoClient").setRpcName("create").build())
          .setBody(
              Arrays.asList(
                  ExprStatement.withExpr(
                      SampleComposerUtil.assignClientVariableWithCreateMethodExpr(
                          echoClientVariableExpr))))
          .build();

  @Test
  public void assignClientVariableWithCreateMethodExpr() {
    String result =
        SampleCodeWriter.write(
            SampleComposerUtil.assignClientVariableWithCreateMethodExpr(echoClientVariableExpr));

    String expected = "EchoClient echoClient = EchoClient.create();";
    assertEquals(expected, result);
  }

  @Test
  public void createOverloadDisambiguation_noargs() {
    String result = SampleComposerUtil.createOverloadDisambiguation(new ArrayList<>());
    String expected = "Noargs";
    assertEquals(expected, result);
  }

  @Test
  public void createOverloadDisambiguation_sameargs() {
    List<VariableExpr> methodArgVarExprs = Collections.nCopies(5, stringVariableExpr);

    String result = SampleComposerUtil.createOverloadDisambiguation(methodArgVarExprs);
    String expected = "StringStringStringStringString";
    assertEquals(expected, result);
  }

  @Test
  public void createOverloadDisambiguation_containsInt() {
    List<VariableExpr> methodArgVarExprs =
        Arrays.asList(echoClientVariableExpr, stringVariableExpr, intVariableExpr);

    String result = SampleComposerUtil.createOverloadDisambiguation(methodArgVarExprs);
    String expected = "EchoclientStringInt";
    assertEquals(expected, result);
  }

  @Test
  public void handleDuplicateSamples_actualDuplicates() {
    List<Sample> samples = Collections.nCopies(5, echoClientSample);
    assertEquals(samples.size(), 5);

    List<Sample> result = SampleComposerUtil.handleDuplicateSamples(samples);
    assertEquals(result.size(), 1);
    assertEquals(result.get(0), echoClientSample);
  }

  @Test
  public void handleDuplicateSamples_distinctDuplicates() {
    VariableExpr echoClientVariableExprDiffVarName =
        VariableExpr.withVariable(
            Variable.builder().setName("echoClient2").setType(clientType).build());
    Sample echoClientSampleDiffVarName =
        echoClientSample
            .toBuilder()
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(
                        SampleComposerUtil.assignClientVariableWithCreateMethodExpr(
                            echoClientVariableExprDiffVarName))))
            .build();
    List<Sample> samples =
        Arrays.asList(echoClientSample, echoClientSample, echoClientSampleDiffVarName);
    assertEquals(samples.size(), 3);
    assertEquals(samples.get(0).name(), "SyncCreate");
    assertEquals(samples.get(1).name(), "SyncCreate");
    assertEquals(samples.get(2).name(), "SyncCreate");

    List<Sample> result = SampleComposerUtil.handleDuplicateSamples(samples);
    assertEquals(result.size(), 2);
    assertTrue(result.contains(echoClientSample));
    assertTrue(result.stream().filter(s -> s.name().equals("SyncCreate1")).findFirst().isPresent());
  }

  @Test
  public void handleDuplicateSamples_notDuplicateName() {
    Sample echoClientSampleDiffRpcName =
        echoClientSample.withRegionTag(
            echoClientSample.regionTag().toBuilder().setRpcName("createB").build());

    List<Sample> samples =
        Arrays.asList(echoClientSample, echoClientSample, echoClientSampleDiffRpcName);
    assertEquals(samples.size(), 3);
    assertEquals(samples.get(0).name(), "SyncCreate");
    assertEquals(samples.get(1).name(), "SyncCreate");
    assertEquals(samples.get(2).name(), "SyncCreateB");

    List<Sample> result = SampleComposerUtil.handleDuplicateSamples(samples);
    assertEquals(result.size(), 2);
    assertTrue(result.contains(echoClientSample));
    assertTrue(result.contains(echoClientSampleDiffRpcName));
  }

  @Test
  public void testConvertTypeToTypeNode() {
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_BOOL), TypeNode.BOOLEAN);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_FLOAT), TypeNode.FLOAT);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_INT64), TypeNode.LONG);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_FIXED64), TypeNode.LONG);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_SFIXED64), TypeNode.LONG);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_SINT64), TypeNode.LONG);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_UINT64), TypeNode.INT);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_INT32), TypeNode.INT);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_FIXED32), TypeNode.INT);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_SFIXED32), TypeNode.INT);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_SINT32), TypeNode.INT);
    assertEquals(SampleComposerUtil.convertScalarTypeToTypeNode(TYPE_STRING), TypeNode.STRING);
  }

  @Test
  public void testConvertMessageTypeToReturnType() {
    String messageType = "google.cloud.speech.v1.CustomClass";
    assertEquals(SampleComposerUtil.convertTypeToReturnType(messageType), "CustomClass");
  }

  @Test
  public void testConvertStandardOutputStatementWithStringValueToStatement() {
    Expression expression = Expression.newBuilder().setStringValue("potato").build();
    Statement.StandardOutput standardOutput = Statement.StandardOutput.newBuilder().setValue(expression).build();
    Statement sampleStatement = Statement.newBuilder().setStandardOutput(standardOutput).build();

    com.google.api.generator.engine.ast.Statement expected = ExprStatement.withExpr(SampleComposerUtil.systemOutPrint("potato"));

    assertEquals(SampleComposerUtil.convertStandardOutputStatementToStatement(sampleStatement), expected);
  }

  @Test
  public void testConvertStandardOutputStatementWithNameValueToStatement() {
    Expression expression = Expression.newBuilder().setNameValue(Expression.NameValue.newBuilder().setName("apple")).build();
    Statement.StandardOutput standardOutput = Statement.StandardOutput.newBuilder().setValue(expression).build();
    Statement sampleStatement = Statement.newBuilder().setStandardOutput(standardOutput).build();

    com.google.api.generator.engine.ast.Statement result = ExprStatement.withExpr(SampleComposerUtil.systemOutPrint("apple"));

    assertEquals(SampleComposerUtil.convertStandardOutputStatementToStatement(sampleStatement), result);
  }

  @Test
  public void testConvertStandardOutputStatementWithNameValueAndPathToStatement() {
    Expression expression = Expression.newBuilder().setNameValue(Expression.NameValue.newBuilder().setName("apple").setPath(0,"seed")).build();
    Statement.StandardOutput standardOutput = Statement.StandardOutput.newBuilder().setValue(expression).build();
    Statement sampleStatement = Statement.newBuilder().setStandardOutput(standardOutput).build();

    com.google.api.generator.engine.ast.Statement result = ExprStatement.withExpr(SampleComposerUtil.systemOutPrint("apple"));

    assertEquals(SampleComposerUtil.convertStandardOutputStatementToStatement(sampleStatement), result);
  }

}
