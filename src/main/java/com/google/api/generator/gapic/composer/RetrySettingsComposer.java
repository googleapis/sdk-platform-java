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

import com.google.api.gax.retrying.RetrySettings;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NullObjectValue;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicRetrySettings;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Service;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.Duration;
import com.google.protobuf.util.Durations;
import io.grpc.serviceconfig.MethodConfig.RetryPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RetrySettingsComposer {
  private static final Map<String, TypeNode> STATIC_TYPES = createStaticTypes();

  public static BlockStatement createRetryParamDefinitionsBlock(
      Service service,
      GapicServiceConfig serviceConfig,
      VariableExpr retryParamDefinitionsClassMemberVarExpr) {
    List<Expr> bodyExprs = new ArrayList<>();

    TypeNode definitionsType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ImmutableMap.Builder.class)
                .setGenerics(retryParamDefinitionsClassMemberVarExpr.type().reference().generics())
                .build());
    VariableExpr definitionsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(definitionsType).setName("definitions").build());
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(STATIC_TYPES.get("RetrySettings"))
                .setName("settings")
                .build());

    // Create the first two exprs.
    bodyExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(definitionsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(STATIC_TYPES.get("ImmutableMap"))
                    .setMethodName("builder")
                    .setReturnType(definitionsVarExpr.type())
                    .build())
            .build());
    bodyExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(ValueExpr.withValue(NullObjectValue.create()))
            .build());

    // Build the settings object for each config.
    for (Map.Entry<String, GapicRetrySettings> settingsEntry :
        serviceConfig.getAllGapicRetrySettings(service).entrySet()) {
      bodyExprs.addAll(
          createRetrySettingsExprs(
              settingsEntry.getKey(),
              settingsEntry.getValue(),
              settingsVarExpr,
              definitionsVarExpr));
    }

    // Reassign the new settings.
    bodyExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(retryParamDefinitionsClassMemberVarExpr)
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(definitionsVarExpr)
                    .setMethodName("build")
                    .setReturnType(retryParamDefinitionsClassMemberVarExpr.type())
                    .build())
            .build());

    // Put everything together.
    return BlockStatement.builder()
        .setIsStatic(true)
        .setBody(
            bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
        .build();
  }

  private static List<Expr> createRetrySettingsExprs(
      String settingsName,
      GapicRetrySettings settings,
      VariableExpr settingsVarExpr,
      VariableExpr definitionsVarExpr) {
    Function<Duration, ValueExpr> durationToMillisValExprFn =
        d ->
            ValueExpr.withValue(
                PrimitiveValue.builder()
                    .setType(TypeNode.LONG)
                    .setValue(String.format("%dL", Durations.toMillis(d)))
                    .build());
    Function<Float, ValueExpr> floatToValExprFn =
        f ->
            ValueExpr.withValue(
                PrimitiveValue.builder()
                    .setType(TypeNode.DOUBLE)
                    .setValue(String.format("%.1f", f))
                    .build());
    Function<ValueExpr, MethodInvocationExpr> durationMillisMethodFn =
        v ->
            MethodInvocationExpr.builder()
                .setStaticReferenceType(STATIC_TYPES.get("Duration"))
                .setMethodName("ofMillis")
                .setArguments(v)
                .build();

    Expr settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("RetrySettings"))
            .setMethodName("newBuilder")
            .build();

    RetryPolicy retryPolicy = settings.retryPolicy();
    if (settings.kind().equals(GapicRetrySettings.Kind.FULL)) {
      Preconditions.checkState(
          retryPolicy.hasInitialBackoff(),
          String.format("initialBackoff not found for setting %s", settingsName));
      settingsBuilderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(settingsBuilderExpr)
              .setMethodName("setInitialRetryDelay")
              .setArguments(
                  durationMillisMethodFn.apply(
                      durationToMillisValExprFn.apply(retryPolicy.getInitialBackoff())))
              .build();

      settingsBuilderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(settingsBuilderExpr)
              .setMethodName("setRetryDelayMultiplier")
              .setArguments(floatToValExprFn.apply(retryPolicy.getBackoffMultiplier()))
              .build();

      Preconditions.checkState(
          retryPolicy.hasMaxBackoff(),
          String.format("maxBackoff not found for setting %s", settingsName));
      settingsBuilderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(settingsBuilderExpr)
              .setMethodName("setMaxRetryDelay")
              .setArguments(
                  durationMillisMethodFn.apply(
                      durationToMillisValExprFn.apply(retryPolicy.getMaxBackoff())))
              .build();
    }

    if (!settings.kind().equals(GapicRetrySettings.Kind.NONE)) {
      settingsBuilderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(settingsBuilderExpr)
              .setMethodName("setInitialRpcTimeout")
              .setArguments(
                  durationMillisMethodFn.apply(durationToMillisValExprFn.apply(settings.timeout())))
              .build();
    }

    // This will always be done, no matter the type of the retry settings object.
    settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingsBuilderExpr)
            .setMethodName("setRpcTimeoutMultiplier")
            .setArguments(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setType(TypeNode.DOUBLE).setValue("1.0").build()))
            .build();

    if (!settings.kind().equals(GapicRetrySettings.Kind.NONE)) {
      for (String setterMethodName : Arrays.asList("setMaxRpcTimeout", "setTotalTimeout")) {
        settingsBuilderExpr =
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(settingsBuilderExpr)
                .setMethodName(setterMethodName)
                .setArguments(
                    durationMillisMethodFn.apply(
                        durationToMillisValExprFn.apply(settings.timeout())))
                .build();
      }
    }

    settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingsBuilderExpr)
            .setMethodName("build")
            .setReturnType(settingsVarExpr.type())
            .build();

    Expr settingsAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr)
            .setValueExpr(settingsBuilderExpr)
            .build();

    Expr definitionsPutExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(definitionsVarExpr)
            .setMethodName("put")
            .setArguments(
                ValueExpr.withValue(StringObjectValue.withValue(settingsName)), settingsVarExpr)
            .build();

    return Arrays.asList(settingsAssignExpr, definitionsPutExpr);
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(org.threeten.bp.Duration.class, ImmutableMap.class, RetrySettings.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }
}
