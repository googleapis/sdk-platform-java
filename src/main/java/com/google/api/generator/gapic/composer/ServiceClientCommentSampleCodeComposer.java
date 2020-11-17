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

package com.google.api.generator.gapic.composer;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.samplecode.SampleCodeJavaFormatter;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Method.Stream;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceClientCommentSampleCodeComposer {

  private static final String SETTINGS_NAME_PATTERN = "%sSettings";
  private static final String CLASS_NAME_PATTERN = "%sClient";

  public static String composeClassHeaderMethodSampleCode(
      Service service, Map<String, TypeNode> types) {
    String clientName = getClientClassName(service.name());
    TypeNode clientType = types.get(clientName);
    Method sampleMethod =
        service.methods().stream()
            .reduce(
                (m1, m2) -> (m1.stream() == Stream.NONE && !m1.hasLro() && !m1.isPaged()) ? m1 : m2)
            .get();
    List<MethodArgument> arguments =
        !sampleMethod.methodSignatures().isEmpty()
            ? sampleMethod.methodSignatures().get(0)
            : Collections.emptyList();
    if (sampleMethod.stream() != Stream.NONE) {
      return writeSampleCode(
          SampleCodeHelperComposer.composeRpcCallableMethodSampleCode(
              clientName, clientType, sampleMethod));
    }
    return writeSampleCode(
        SampleCodeHelperComposer.composeRpcMethodSampleCode(
            clientName, clientType, sampleMethod, arguments));
  }

  public static String composeClassHeaderCredentialsSampleCode(
      Service service, Map<String, TypeNode> types) {
    String settingsVarName = JavaStyle.toLowerCamelCase(getSettingsName(service.name()));
    TypeNode settingsVarType = types.get(getSettingsName(service.name()));
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(settingsVarName).setType(settingsVarType).build());
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsVarType)
            .setMethodName("newBuilder")
            .build();
    TypeNode fixedCredentialProvideType =
        TypeNode.withReference(ConcreteReference.withClazz(FixedCredentialsProvider.class));
    MethodInvocationExpr credentialArgExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(fixedCredentialProvideType)
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue("myCredentials")))
            .setMethodName("create")
            .build();
    MethodInvocationExpr credentialsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderMethodExpr)
            .setArguments(credentialArgExpr)
            .setMethodName("setCredentialsProvider")
            .build();
    MethodInvocationExpr buildMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(credentialsMethodExpr)
            .setReturnType(settingsVarType)
            .setMethodName("build")
            .build();

    Expr initSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    String className = JavaStyle.toLowerCamelCase(getClientClassName(service.name()));
    TypeNode classType = types.get(getClientClassName(service.name()));
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(Variable.builder().setName(className).setType(classType).build());
    MethodInvocationExpr createMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(classType)
            .setArguments(settingsVarExpr)
            .setMethodName("create")
            .setReturnType(classType)
            .build();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();

    return writeSampleCode(Arrays.asList(initSettingsVarExpr, initClientVarExpr));
  }

  public static String composeClassHeaderEndpointSampleCode(
      Service service, Map<String, TypeNode> types) {
    String settingsVarName = JavaStyle.toLowerCamelCase(getSettingsName(service.name()));
    TypeNode settingsVarType = types.get(getSettingsName(service.name()));
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(settingsVarName).setType(settingsVarType).build());
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsVarType)
            .setMethodName("newBuilder")
            .build();
    MethodInvocationExpr credentialsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderMethodExpr)
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue("myEndpoint")))
            .setMethodName("setEndpoint")
            .build();
    MethodInvocationExpr buildMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(credentialsMethodExpr)
            .setReturnType(settingsVarType)
            .setMethodName("build")
            .build();

    Expr initSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    String className = JavaStyle.toLowerCamelCase(getClientClassName(service.name()));
    TypeNode classType = types.get(getClientClassName(service.name()));
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(Variable.builder().setName(className).setType(classType).build());
    MethodInvocationExpr createMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(classType)
            .setArguments(settingsVarExpr)
            .setMethodName("create")
            .setReturnType(classType)
            .build();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();

    return writeSampleCode(Arrays.asList(initSettingsVarExpr, initClientVarExpr));
  }

  public static String composeRpcMethodHeaderSampleCode(
      Service service, Method method, List<MethodArgument> arguments, Map<String, TypeNode> types) {
    String clientName = getClientClassName(service.name());
    TypeNode clientType = types.get(clientName);
    return writeSampleCode(
        SampleCodeHelperComposer.composeRpcMethodSampleCode(
            clientName, clientType, method, arguments));
  }

  public static String composeRpcCallableMethodHeaderSampleCode(
      String serviceName, Method method, Map<String, TypeNode> types, String callableMethodName) {
    String clientName = getClientClassName(serviceName);
    TypeNode clientType = types.get(clientName);
    if (method.stream() != Stream.NONE) {
      switch (method.stream()) {
        case CLIENT:
          return writeSampleCode(
              SampleCodeHelperComposer.composeStreamClientRpcCallableMethodSampleCode(
                  clientName, clientType, method));
        case BIDI:
          return writeSampleCode(SampleCodeHelperComposer.composeStreamBiDiRpcCallableMethodSampleCode(
              clientName, clientType, method));
        case SERVER:
          return writeSampleCode(
              SampleCodeHelperComposer.composeStreamServerRpcCallableMethodSampleCode(
                  clientName, clientType, method));
      }
    }
    if (method.hasLro()) {
      return "LRO callable;";
    }
    if (method.isPaged()) {
      return "paged callable;";
    }
    return writeSampleCode(
        SampleCodeHelperComposer.composeRpcCallableMethodSampleCode(
            clientName, clientType, method));
  }
  // =============================== Helpers ==================================================//

  private static String getClientClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }

  private static String getSettingsName(String serviceName) {
    return String.format(SETTINGS_NAME_PATTERN, serviceName);
  }

  private static String writeSampleCode(List<Expr> exprs) {
    List<Statement> statements =
        exprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    for (Statement statement : statements) {
      statement.accept(visitor);
    }
    return SampleCodeJavaFormatter.format(visitor.write());
  }

  private static String writeSampleCode(Statement statement) {
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    statement.accept(visitor);
    return SampleCodeJavaFormatter.format(visitor.write());
  }
}
