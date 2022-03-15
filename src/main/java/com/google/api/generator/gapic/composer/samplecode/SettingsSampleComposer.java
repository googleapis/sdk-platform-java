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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.RegionTag;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class SettingsSampleComposer {

  public static Optional<Sample> composeSettingsSample(
      Optional<String> methodNameOpt, String settingsClassName, TypeNode classType) {
    if (!methodNameOpt.isPresent()) {
      return Optional.empty();
    }

    // Initialize services settingsBuilder with newBuilder()
    // e.g. FoobarSettings.Builder foobarSettingsBuilder = FoobarSettings.newBuilder();
    TypeNode builderType =
        TypeNode.withReference(
            VaporReference.builder()
                .setEnclosingClassNames(classType.reference().name())
                .setName("Builder")
                .setPakkage(classType.reference().pakkage())
                .build());
    VariableExpr localSettingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(String.format("%sBuilder", settingsClassName)))
                .setType(builderType)
                .build());
    MethodInvocationExpr settingsBuilderMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(classType)
            .setMethodName("newBuilder")
            .setReturnType(builderType)
            .build();
    AssignmentExpr initLocalSettingsExpr =
        AssignmentExpr.builder()
            .setVariableExpr(localSettingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(settingsBuilderMethodInvocationExpr)
            .build();

    // Builder with set value method
    // e.g foobarSettingBuilder.fooSetting().setRetrySettings(
    // echoSettingsBuilder.echoSettings().getRetrySettings().toBuilder().setTotalTimeout(Duration.ofSeconds(30)).build());
    MethodInvocationExpr settingBuilderMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(localSettingsVarExpr)
            .setMethodName(
                JavaStyle.toLowerCamelCase(String.format("%sSettings", methodNameOpt.get())))
            .build();
    String disambiguation = "Settings";
    MethodInvocationExpr retrySettingsArgExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingBuilderMethodInvocationExpr)
            .setMethodName("getRetrySettings")
            .build();
    retrySettingsArgExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(retrySettingsArgExpr)
            .setMethodName("toBuilder")
            .build();
    MethodInvocationExpr ofSecondMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(
                TypeNode.withReference(ConcreteReference.withClazz(Duration.class)))
            .setMethodName("ofSeconds")
            .setArguments(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setType(TypeNode.INT).setValue("30").build()))
            .build();
    retrySettingsArgExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(retrySettingsArgExpr)
            .setMethodName("setTotalTimeout")
            .setArguments(ofSecondMethodInvocationExpr)
            .build();
    retrySettingsArgExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(retrySettingsArgExpr)
            .setMethodName("build")
            .build();
    settingBuilderMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingBuilderMethodInvocationExpr)
            .setMethodName("setRetrySettings")
            .setArguments(retrySettingsArgExpr)
            .build();

    // Initialize clientSetting with builder() method.
    // e.g: Foobar<Stub>Settings foobarSettings = foobarSettingsBuilder.build();
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(classType)
                .setName(JavaStyle.toLowerCamelCase(settingsClassName))
                .build());

    AssignmentExpr settingBuildAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(localSettingsVarExpr)
                    .setMethodName("build")
                    .setReturnType(classType)
                    .build())
            .build();

    List<Statement> statements =
        Arrays.asList(
                initLocalSettingsExpr,
                settingBuilderMethodInvocationExpr,
                settingBuildAssignmentExpr)
            .stream()
            .map(e -> ExprStatement.withExpr(e))
            .collect(Collectors.toList());

    RegionTag regionTag =
        RegionTag.builder()
            .setServiceName(classType.reference().name())
            .setRpcName(methodNameOpt.get())
            .build();
    return Optional.of(Sample.builder().setBody(statements).setRegionTag(regionTag).build());
  }
}
