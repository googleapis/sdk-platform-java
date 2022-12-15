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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.comment.CommentComposer;
import com.google.api.generator.gapic.model.GapicSnippetConfig;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConfiguredSnippetComposer {

  static List<CommentStatement> fileHeader = Arrays.asList(CommentComposer.APACHE_LICENSE_COMMENT);

  static List<Statement> statementsForTest = new ArrayList<>();

  // TODO: Create method to create VariableExpr for main method to use
  // These are hardcoded for the golden file
  private static VariableExpr strVariableExpr1 =
      VariableExpr.builder()
          .setVariable(Variable.builder().setType(TypeNode.STRING).setName("parent").build())
          .setIsDecl(true)
          .build();
  private static AssignmentExpr strVarAssignment1 =
      AssignmentExpr.builder()
          .setVariableExpr(strVariableExpr1)
          .setValueExpr(
              ValueExpr.withValue(StringObjectValue.withValue("projects/[PROJECT]/locations/us")))
          .build();
  private static VariableExpr strVariableExpr2 =
      VariableExpr.builder()
          .setVariable(Variable.builder().setType(TypeNode.STRING).setName("customClassId").build())
          .setIsDecl(true)
          .build();
  private static AssignmentExpr strVarAssignment2 =
      AssignmentExpr.builder()
          .setVariableExpr(strVariableExpr2)
          .setValueExpr(ValueExpr.withValue(StringObjectValue.withValue("passengerships")))
          .build();
  private static List<AssignmentExpr> sampleVariableAssignments =
      Arrays.asList(strVarAssignment1, strVarAssignment2);
  private static List<VariableExpr> sampleMethodArgs =
      Arrays.asList(strVariableExpr1, strVariableExpr2);

  private static List<CommentStatement> composeHeaderStatements(GapicSnippetConfig snippetConfig) {
    Iterator<Map.Entry<String, List>> iterator =
        GapicSnippetConfig.getConfiguredSnippetSignatureParameters(snippetConfig)
            .entrySet()
            .iterator();
    JavaDocComment.Builder javaDocComment = JavaDocComment.builder();
    javaDocComment.addComment("AUTO-GENERATED DOCUMENTATION AND CLASS");
    javaDocComment.addComment(GapicSnippetConfig.getConfiguredSnippetSnippetName(snippetConfig));
    javaDocComment.addParagraph(
        GapicSnippetConfig.getConfiguredSnippetSnippetDescription(snippetConfig));
    // for scratch stuff
    //
    // .addComment(GapicSnippetConfig.getConfiguredSnippetCallType(snippetConfig))

    while (iterator.hasNext()) {
      Map.Entry<String, List> actualValue = iterator.next();
      // Key is the name of the parameter, Value is the description
      javaDocComment.addParam(
          JavaStyle.toLowerCamelCase(actualValue.getKey()),
          actualValue.getValue().get(0).toString());
    }

    return Arrays.asList(CommentStatement.withComment(javaDocComment.build()));
  }

  public static String composeSampleMethodName(GapicSnippetConfig snippetConfig) {
    if (GapicSnippetConfig.parseSyncPreference(snippetConfig)) {
      return "async" + GapicSnippetConfig.getConfiguredSnippetRpcName(snippetConfig);
    } else {
      return "sync" + GapicSnippetConfig.getConfiguredSnippetRpcName(snippetConfig);
    }
  }

  public static ClassDefinition composeConfiguredSnippetClass(GapicSnippetConfig snippetConfig) {
    MethodDefinition mainMethod =
        SampleComposer.composeMainMethod(
            SampleComposer.composeMainBody(
                sampleVariableAssignments,
                SampleComposer.composeInvokeMethodStatement(
                    composeSampleMethodName(snippetConfig), sampleMethodArgs)));
    MethodDefinition sampleMethod =
        SampleComposer.composeSampleMethod(
            composeSampleMethodName(snippetConfig), sampleMethodArgs, statementsForTest);
    return ClassDefinition.builder()
        .setFileHeader(fileHeader)
        .setHeaderCommentStatements(composeHeaderStatements(snippetConfig))
        .setPackageString(GapicSnippetConfig.getConfiguredSnippetPackageString(snippetConfig))
        .setName(GapicSnippetConfig.getConfiguredSnippetRpcName(snippetConfig))
        .setRegionTag(GapicSnippetConfig.getConfiguredSnippetRegionTag(snippetConfig))
        .setScope(ScopeNode.PUBLIC)
        .setMethods(ImmutableList.of(mainMethod, sampleMethod))
        .build();
  }
}
