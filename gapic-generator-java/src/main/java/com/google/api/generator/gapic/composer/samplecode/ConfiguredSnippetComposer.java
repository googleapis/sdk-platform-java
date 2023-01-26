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

import com.google.api.core.ApiFuture;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
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
import java.util.stream.Collectors;

public class ConfiguredSnippetComposer {

  static List<CommentStatement> fileHeader = Arrays.asList(CommentComposer.APACHE_LICENSE_COMMENT);

  // Generates the JavaDoc comments for each configured snippet that includes the request
  // parameters, response type, and snippet description provided by the snippet config
  public static List<CommentStatement> composeHeaderStatements(GapicSnippetConfig snippetConfig) {
    JavaDocComment.Builder javaDocComment =
        JavaDocComment.builder()
            .addComment("AUTO-GENERATED DOCUMENTATION\n")
            .addComment(GapicSnippetConfig.getConfiguredSnippetSnippetName(snippetConfig))
            .addParagraph(GapicSnippetConfig.getConfiguredSnippetSnippetDescription(snippetConfig));

    if (GapicSnippetConfig.getConfiguredSnippetReturnType(snippetConfig).length() > 1) {
      javaDocComment.setReturn(GapicSnippetConfig.getConfiguredSnippetReturnType(snippetConfig));
    }

    Iterator<Map.Entry<String, List>> iterator =
        GapicSnippetConfig.getConfiguredSnippetSignatureParameters(snippetConfig)
            .entrySet()
            .iterator();

    while (iterator.hasNext()) {
      Map.Entry<String, List> actualValue = iterator.next();
      // Key is the name of the parameter, first element of Value is the description
      javaDocComment.addParam(
          JavaStyle.toLowerCamelCase(actualValue.getKey()),
          actualValue.getValue().get(0).toString());
    }

    return Arrays.asList(CommentStatement.withComment(javaDocComment.build()));
  }

  // Generate the sample method name for the configured snippet. If `both` is specified as the
  // SyncPreference, then we should return both a sync and an async sample.
  public static String composeSampleMethodName(GapicSnippetConfig snippetConfig) {
    if (GapicSnippetConfig.parseSyncPreference(snippetConfig)) {
      return "async" + GapicSnippetConfig.getConfiguredSnippetRpcName(snippetConfig);
    } else {
      return "sync" + GapicSnippetConfig.getConfiguredSnippetRpcName(snippetConfig);
    }
  }

  // Composes request and response handling within the sample method
  // Currently only generates a Standard Operation sample
  // TODO: add support for Paginated Operation (P2, with exception that paginated responses are P0),
  // LRO (P2, with exception that Polling type response handling is P0), Server Streaming (P3),
  // Client Streaming (P3), and Bidi Streaming (P3)
  public static List<Statement> composeSampleMethodBodyStatements(
      GapicSnippetConfig snippetConfig) {
    List<Statement> sampleBodyStatements = new ArrayList<>();
    // Create Request Statement
    // e.g. CreateCustomClassRequest createCustomClassRequest =
    //          CreateCustomClassRequest.newBuilder()
    String requestName =
        String.format(
            "%s", JavaStyle.toLowerCamelCase(GapicSnippetConfig.getRequestName(snippetConfig)));
    String requestTypeName =
        String.format(
            "%s", JavaStyle.toUpperCamelCase(GapicSnippetConfig.getRequestName(snippetConfig)));
    TypeNode requestType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(requestTypeName)
                .setPakkage(GapicSnippetConfig.getConfiguredSnippetPakkageString(snippetConfig))
                .build());
    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(requestName).setType(requestType).build());
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(requestType)
            .setMethodName("newBuilder")
            .build();
    // TODO: this will depend on the request initialization parameters; currently hardcoded for
    // golden sample
    // .setParent(parent)
    // .setCustomClassId(customClassId)

    // Have a map where the key is the name of the variable, first value is the TypeNode
    // MethodName is "set + name of variable"
    // TODO: how to handle nested requests?
    MethodInvocationExpr firstRequestMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderMethodExpr)
            .setArguments(
                VariableExpr.withVariable(
                    Variable.builder().setName("parent").setType(TypeNode.STRING).build()))
            .setMethodName("setParent")
            .build();
    MethodInvocationExpr secondRequestMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(firstRequestMethodExpr)
            .setArguments(
                VariableExpr.withVariable(
                    Variable.builder().setName("customClassId").setType(TypeNode.STRING).build()))
            .setMethodName("setCustomClassId")
            .build();
    //    Compose nested Argument Variables
    TypeNode nestedRequestType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("CustomClass")
                .setPakkage(GapicSnippetConfig.getConfiguredSnippetPakkageString(snippetConfig))
                .build());
    VariableExpr nestedRequestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("CustomClass").setType(nestedRequestType).build());

    MethodInvocationExpr nestedFirstNewBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(nestedRequestVarExpr)
            .setMethodName("newBuilder")
            .build();

    VaporReference CustomClassClassItem =
        VaporReference.builder()
            .setEnclosingClassNames("CustomClass")
            .setName("ClassItem")
            .setPakkage(GapicSnippetConfig.getConfiguredSnippetPakkageString(snippetConfig))
            .build();
    MethodInvocationExpr nestedSecondNewBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(TypeNode.withReference(CustomClassClassItem))
            .setMethodName("newBuilder")
            .build();

    // TODO update to intake from config instead of hardcode
    // TODO currently this parameter is duplicated; fix
    MethodInvocationExpr setValueTitanic =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(nestedSecondNewBuilderMethodExpr)
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue("Titanic")))
            .setMethodName("setValue")
            .build();

    // TODO update to intake from config instead of hardcode
    MethodInvocationExpr setValueRMSQueenMary =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(nestedSecondNewBuilderMethodExpr)
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue("RMSQueenMary")))
            .setMethodName("setValue")
            .build();

    MethodInvocationExpr addItemsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(nestedFirstNewBuilderMethodExpr)
            .setArguments(setValueTitanic)
            .setMethodName("addItems")
            .build();

    MethodInvocationExpr innerNestedBuildMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(addItemsMethodExpr)
            .setReturnType(requestType)
            .setMethodName("build")
            .build();

    MethodInvocationExpr addItemsMethodExpr2 =
            MethodInvocationExpr.builder()
                    .setExprReferenceExpr(addItemsMethodExpr)
                    .setArguments(setValueRMSQueenMary)
                    .setMethodName("addItems")
                    .build();

    MethodInvocationExpr innerNestedBuildMethodExpr2 =
            MethodInvocationExpr.builder()
                    .setExprReferenceExpr(addItemsMethodExpr2)
                    .setReturnType(requestType)
                    .setMethodName("build")
                    .build();

    MethodInvocationExpr thirdRequestMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(secondRequestMethodExpr)
            .setArguments(innerNestedBuildMethodExpr, innerNestedBuildMethodExpr2)
            .setMethodName("setCustomClass")
            .build();

    MethodInvocationExpr buildMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(thirdRequestMethodExpr)
            .setReturnType(requestType)
            .setMethodName("build")
            .build();

    Expr initRequestVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    sampleBodyStatements.add(ExprStatement.withExpr(initRequestVarExpr));

    // Parse preCall statements
    List<com.google.cloud.tools.snippetgen.configlanguage.v1.Statement> preCallStatements =
        GapicSnippetConfig.getPreCallStatements(snippetConfig);
    for (com.google.cloud.tools.snippetgen.configlanguage.v1.Statement statement :
        preCallStatements) {
      if (statement.hasStandardOutput()) {
        sampleBodyStatements.add(
            ExprStatement.withExpr(
                SampleComposerUtil.systemOutPrint(
                    SampleComposerUtil.convertExpressionToString(
                        statement.getStandardOutput().getValue()))));
      }
    }

    // Create the call statement using api future variable expression, and assign it with a value by
    // invoking callable method
    //    e.g. ApiFuture<CustomClass> future =
    // adaptationClient.createCustomClassCallable().futureCall(createCustomClassRequest);

    // TODO: Confirm that this works for non-message Return Types
    // TODO: Build in logic if there is no return how to handle

    String returnType =
        SampleComposerUtil.convertTypeToReturnType(
            GapicSnippetConfig.getConfiguredSnippetReturnType(snippetConfig));
    TypeNode responseType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(returnType)
                .setPakkage(GapicSnippetConfig.getConfiguredSnippetPakkageString(snippetConfig))
                .build());
    TypeNode apiFutureType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ApiFuture.class)
                .setGenerics(responseType.reference())
                .build());
    VariableExpr apiFutureVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("future").setType(apiFutureType).build());

    String clientName =
        String.format(
            "%sClient",
            JavaStyle.toLowerCamelCase(
                GapicSnippetConfig.getConfiguredSnippetServiceName(snippetConfig)));
    String clientTypeName =
        String.format(
            "%sClient", GapicSnippetConfig.getConfiguredSnippetServiceName(snippetConfig));

    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(clientTypeName)
                .setPakkage(GapicSnippetConfig.getConfiguredSnippetPakkageString(snippetConfig))
                .build());

    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(clientName).setType(clientType).build());
    MethodInvocationExpr callableMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(
                JavaStyle.toLowerCamelCase(
                    String.format(
                        "%sCallable",
                        GapicSnippetConfig.getConfiguredSnippetRpcName(snippetConfig))))
            .build();
    callableMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(callableMethodInvocationExpr)
            .setMethodName("futureCall")
            .setArguments(requestVarExpr)
            .setReturnType(apiFutureType)
            .build();

    AssignmentExpr futureAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(apiFutureVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(callableMethodInvocationExpr)
            .build();
    sampleBodyStatements.add(ExprStatement.withExpr(futureAssignmentExpr));

    // Get the future call
    //  CustomClass createdCustomClass = future.get();

    MethodInvocationExpr getMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(apiFutureVarExpr)
            .setMethodName("get")
            .setReturnType(responseType)
            .build();

    VariableExpr responseVarExpr =
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setType(responseType)
                    .setName(GapicSnippetConfig.getResponseValue(snippetConfig))
                    .build())
            .setIsDecl(true)
            .build();
    AssignmentExpr responseAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(responseVarExpr)
            .setValueExpr(getMethodInvocationExpr)
            .build();
    sampleBodyStatements.add(ExprStatement.withExpr(responseAssignmentExpr));

    // Parse final statements
    List<com.google.cloud.tools.snippetgen.configlanguage.v1.Statement> finalStatements =
        GapicSnippetConfig.getFinalStatements(snippetConfig);
    for (com.google.cloud.tools.snippetgen.configlanguage.v1.Statement statement :
        finalStatements) {
      if (statement.hasStandardOutput()) {
        sampleBodyStatements.add(
               SampleComposerUtil.convertStandardOutputStatementToStatement(statement));
      }
      if (statement.hasIteration()) {
        sampleBodyStatements.add(
            SampleComposerUtil.convertIterationTypeStatementToStatement(statement));
      }
    }

    return sampleBodyStatements;
  }

  // Composes the client settings initialization with an endpoint if specified in the config as well
  // as the actual client initialization using the settings
  // Includes try-catch wrapper within sample method
  public static List<Statement> composeClientInitializationStatements(
      GapicSnippetConfig snippetConfig) {
    // TODO: parse PreClientInitializationStatements (P2)
    List<Statement> clientInitializationStatements = new ArrayList<>();

    // Initialize client settings with builder() method.
    // e.g. EchoSettings echoSettings = EchoSettings.newBuilder();
    String settingsName =
        String.format(
            "%sSettings",
            JavaStyle.toLowerCamelCase(
                GapicSnippetConfig.getConfiguredSnippetServiceName(snippetConfig)));
    String settingsTypeName =
        String.format(
            "%sSettings", GapicSnippetConfig.getConfiguredSnippetServiceName(snippetConfig));
    TypeNode settingsType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(settingsTypeName)
                .setPakkage(GapicSnippetConfig.getConfiguredSnippetPakkageString(snippetConfig))
                .build());
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(settingsName).setType(settingsType).build());
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsType)
            .setMethodName("newBuilder")
            .build();
    Expr buildMethodExpr;

    // Set endpoint if configured
    // e.g. .setEndpoint("endpoint").build()
    if (GapicSnippetConfig.getConfiguredSnippetEndpoint(snippetConfig) != null) {
      VariableExpr endpointVar =
          VariableExpr.builder()
              .setVariable(Variable.builder().setType(TypeNode.STRING).setName("endpoint").build())
              .setIsDecl(true)
              .build();
      AssignmentExpr endpointAssignment =
          AssignmentExpr.builder()
              .setVariableExpr(endpointVar)
              .setValueExpr(
                  ValueExpr.withValue(
                      StringObjectValue.withValue(
                          GapicSnippetConfig.getConfiguredSnippetEndpoint(snippetConfig))))
              .build();

      Statement endpointStatement = ExprStatement.withExpr(endpointAssignment);
      clientInitializationStatements.add(endpointStatement);

      MethodInvocationExpr credentialsMethodExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(newBuilderMethodExpr)
              .setArguments(
                  VariableExpr.withVariable(
                      Variable.builder().setName("endpoint").setType(TypeNode.STRING).build()))
              .setMethodName("setEndpoint")
              .build();

      buildMethodExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(credentialsMethodExpr)
              .setReturnType(settingsType)
              .setMethodName("build")
              .build();
    } else {
      buildMethodExpr =
          MethodInvocationExpr.builder().setReturnType(settingsType).setMethodName("build").build();
    }

    Expr initSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    clientInitializationStatements.add(ExprStatement.withExpr(initSettingsVarExpr));

    // compose try Catch block
    String clientName =
        String.format(
            "%sClient",
            JavaStyle.toLowerCamelCase(
                GapicSnippetConfig.getConfiguredSnippetServiceName(snippetConfig)));
    String clientTypeName =
        String.format(
            "%sClient", GapicSnippetConfig.getConfiguredSnippetServiceName(snippetConfig));

    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(clientTypeName)
                .setPakkage(GapicSnippetConfig.getConfiguredSnippetPakkageString(snippetConfig))
                .build());

    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(clientName).setType(clientType).build());
    MethodInvocationExpr createMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(clientType)
            .setArguments(settingsVarExpr)
            .setMethodName("create")
            .setReturnType(clientType)
            .build();
    AssignmentExpr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();

    TryCatchStatement sampleStatement =
        TryCatchStatement.builder()
            .setTryResourceExpr(initClientVarExpr)
            .setTryBody(
                composeSampleMethodBodyStatements(snippetConfig)) // Set based on configuration
            .setIsSampleCode(true)
            .build();

    clientInitializationStatements.add(sampleStatement);

    return clientInitializationStatements;
  }

  public static ClassDefinition composeConfiguredSnippetClass(GapicSnippetConfig snippetConfig) {
    List<VariableExpr> sampleMethodArgs =
        GapicSnippetConfig.composeMainMethodArgs(
                GapicSnippetConfig.getConfiguredSnippetSignatureParameters(snippetConfig))
            .keySet().stream()
            .collect(Collectors.toList());
    List<AssignmentExpr> sampleVariableAssignments =
        GapicSnippetConfig.composeMainMethodArgs(
                GapicSnippetConfig.getConfiguredSnippetSignatureParameters(snippetConfig))
            .values().stream()
            .collect(Collectors.toList());
    MethodDefinition mainMethod =
        SampleComposer.composeMainMethod(
            SampleComposer.composeMainBody(
                sampleVariableAssignments,
                SampleComposer.composeInvokeMethodStatement(
                    composeSampleMethodName(snippetConfig), sampleMethodArgs)));
    MethodDefinition sampleMethod =
        SampleComposer.composeSampleMethod(
            composeSampleMethodName(snippetConfig),
            sampleMethodArgs,
            composeClientInitializationStatements(snippetConfig));
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
