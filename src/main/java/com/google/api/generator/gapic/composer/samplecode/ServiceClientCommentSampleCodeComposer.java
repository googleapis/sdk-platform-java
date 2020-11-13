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
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

public class ServiceClientCommentSampleCodeComposer {

  private static final String SETTINGS_NAME_PATTERN = "%sSettings";
  private static final String CLASS_NAME_PATTERN = "%sClient";

  public static String composeClassHeaderCredentialsSampleCode(Service service, Map<String, TypeNode> types) {
    String settingsVarName = JavaStyle.toLowerCamelCase(getSettingsName(service.name()));
    TypeNode settingsVarType = types.get(getSettingsName(service.name()));
    VariableExpr settingsVarExpr = VariableExpr.withVariable(
        Variable.builder()
            .setName(settingsVarName)
            .setType(settingsVarType)
            .build());
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
        .setStaticReferenceType(settingsVarType)
        .setMethodName("newBuilder")
        .build();
    TypeNode fixedCredentialProvideType = TypeNode.withReference(
        ConcreteReference.withClazz(FixedCredentialsProvider.class)
    );
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

    Expr initLocalSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    String className = JavaStyle.toLowerCamelCase(getClientClassName(service.name()));
    TypeNode classType = types.get(getClientClassName(service.name()));
    VariableExpr clientVarExpr = VariableExpr.withVariable(Variable.builder().setName(className).setType(classType).build());
    MethodInvocationExpr createMethodExpr = MethodInvocationExpr.builder().setStaticReferenceType(classType).setArguments(settingsVarExpr).setMethodName("create").setReturnType(classType).build();
    Expr initClientVarExpr = AssignmentExpr.builder()
        .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(createMethodExpr)
        .build();

    List<Statement> statements =
        Arrays.asList(
            initLocalSettingsVarExpr,
            initClientVarExpr
        )
            .stream()
            .map(e -> ExprStatement.withExpr(e))
            .collect(Collectors.toList());
    return SampleCodeJavaFormatter.format(writeStatements(statements));
  }

  private String getClientClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }

  private String getSettingsName(String serviceName) {
    return String.format(SETTINGS_NAME_PATTERN, serviceName);
  }

  private String writeStatements(List<Statement> statements) {
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    for (Statement statement : statements) {
      statement.accept(visitor);
    }
    return visitor.write();
  }
}
