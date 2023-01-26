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

package com.google.api.generator.gapic.model;

import static org.junit.Assert.assertEquals;

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.protoparser.SnippetConfigParser;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Test;

public class GapicSnippetConfigTest {
  private static final String TESTDATA_DIRECTORY = "src/test/resources/";
  private static final String jsonFilename = "configured_snippet_config.json";
  private static final Path jsonPath = Paths.get(TESTDATA_DIRECTORY, jsonFilename);
  private static final Optional<GapicSnippetConfig> snippetConfigOpt =
      SnippetConfigParser.parse(jsonPath.toString());
  private static final GapicSnippetConfig snippetConfig = snippetConfigOpt.get();

  // TODO Add more tests
  @Test
  public void snippetConfig_getSnippetNameAndDesc() {
    assertEquals(
        "Custom Class Creation", GapicSnippetConfig.getConfiguredSnippetSnippetName(snippetConfig));
    assertEquals(
        "Shows how to create a custom class",
        GapicSnippetConfig.getConfiguredSnippetSnippetDescription(snippetConfig));
  }

  @Test
  public void snippetConfig_getPackageString() {
    assertEquals(
        "com.google.cloud.speech.v1",
        GapicSnippetConfig.getConfiguredSnippetPakkageString(snippetConfig));
  }

  @Test
  public void snippetConfig_getSnippetEndpoint() {
    assertEquals(
            "us-speech.googleapis.com:443",
            GapicSnippetConfig.getConfiguredSnippetEndpoint(snippetConfig));
  }

  @Test
  public void parseSnippetSignatureReturnType_test() {
    String actualReturnValue = GapicSnippetConfig.getConfiguredSnippetReturnType(snippetConfig);

    assertEquals("google.cloud.speech.v1.CustomClass", actualReturnValue);
  }

  @Test
  public void getSnippetResponseValue_test() {
    String actualResponseValue = GapicSnippetConfig.getResponseValue(snippetConfig);

    assertEquals("createdCustomClass", actualResponseValue);
  }
  // Add test for getRequestValues()
  //  @Test
  //  public void snippetConfig_getRequestValues() {
  //    assertEquals("com.google.cloud.speech.v1",
  // GapicSnippetConfig.getRequestValue(snippetConfig));
  //  }

  @Test
  public void snippetConfig_composeVariableExpr() {
    VariableExpr strVariableExpr1 =
        VariableExpr.builder()
            .setVariable(Variable.builder().setType(TypeNode.STRING).setName("parent").build())
            .setIsDecl(true)
            .build();

    AssignmentExpr strVarAssignment1 =
        AssignmentExpr.builder()
            .setVariableExpr(strVariableExpr1)
            .setValueExpr(
                ValueExpr.withValue(StringObjectValue.withValue("projects/[PROJECT]/locations/us")))
            .build();

    VariableExpr strVariableExpr2 =
        VariableExpr.builder()
            .setVariable(
                Variable.builder().setType(TypeNode.STRING).setName("customClassId").build())
            .setIsDecl(true)
            .build();

    AssignmentExpr strVarAssignment2 =
        AssignmentExpr.builder()
            .setVariableExpr(strVariableExpr2)
            .setValueExpr(ValueExpr.withValue(StringObjectValue.withValue("passengerships")))
            .build();

    List<VariableExpr> sampleVariableExpr = Arrays.asList(strVariableExpr1, strVariableExpr2);
    List<AssignmentExpr> sampleAssignmentExpr = Arrays.asList(strVarAssignment1, strVarAssignment2);

    LinkedHashMap<String, List> configSignatureParameters =
        GapicSnippetConfig.getConfiguredSnippetSignatureParameters(snippetConfig);
    List<VariableExpr> listOfVarExpr =
        GapicSnippetConfig.composeMainMethodArgs(configSignatureParameters).keySet().stream()
            .collect(Collectors.toList());
    List<AssignmentExpr> listOfAssignmentExpr =
        GapicSnippetConfig.composeMainMethodArgs(configSignatureParameters).values().stream()
            .collect(Collectors.toList());

    assertEquals(sampleVariableExpr, listOfVarExpr);
    assertEquals(sampleAssignmentExpr, listOfAssignmentExpr);
  }
}
