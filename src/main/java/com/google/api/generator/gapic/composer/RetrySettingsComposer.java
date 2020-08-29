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

import com.google.api.gax.grpc.ProtoOperationTransformers;
import com.google.api.gax.longrunning.OperationSnapshot;
import com.google.api.gax.longrunning.OperationTimedPollAlgorithm;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EnumRefExpr;
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
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.protobuf.Duration;
import com.google.protobuf.util.Durations;
import com.google.rpc.Code;
import io.grpc.serviceconfig.MethodConfig.RetryPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RetrySettingsComposer {
  private static final Map<String, TypeNode> STATIC_TYPES = createStaticTypes();
  private static final TypeNode STATUS_CODE_CODE_TYPE =
      TypeNode.withReference(ConcreteReference.withClazz(StatusCode.Code.class));

  // TODO(miraleung): Determine defaults here.
  // Default values for LongRunningConfig fields.
  private static final long LRO_DEFAULT_INITIAL_POLL_DELAY_MILLIS = 500;
  private static final double LRO_DEFAULT_POLL_DELAY_MULTIPLIER = 1.5;
  private static final long LRO_DEFAULT_MAX_POLL_DELAY_MILLIS = 5000;
  private static final long LRO_DEFAULT_TOTAL_POLL_TIMEOUT_MILLS = 300000;
  private static final double LRO_DEFAULT_MAX_RPC_TIMEOUT = 1.0;

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

  public static BlockStatement createRetryCodesDefinitionsBlock(
      Service service,
      GapicServiceConfig serviceConfig,
      VariableExpr retryCodesDefinitionsClassMemberVarExpr) {
    TypeNode definitionsType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ImmutableMap.Builder.class)
                .setGenerics(retryCodesDefinitionsClassMemberVarExpr.type().reference().generics())
                .build());
    VariableExpr definitionsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(definitionsType).setName("definitions").build());

    List<Expr> bodyExprs = new ArrayList<>();
    // Create the first expr.
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

    for (Map.Entry<String, List<Code>> codeEntry :
        serviceConfig.getAllRetryCodes(service).entrySet()) {
      bodyExprs.add(
          createRetryCodeDefinitionExpr(
              codeEntry.getKey(), codeEntry.getValue(), definitionsVarExpr));
    }

    // Reassign the new codes.
    bodyExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(retryCodesDefinitionsClassMemberVarExpr)
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(definitionsVarExpr)
                    .setMethodName("build")
                    .setReturnType(retryCodesDefinitionsClassMemberVarExpr.type())
                    .build())
            .build());

    // Put everything together.
    return BlockStatement.builder()
        .setIsStatic(true)
        .setBody(
            bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
        .build();
  }

  public static Expr createSimpleBuilderSettingsExpr(
      Service service,
      GapicServiceConfig serviceConfig,
      Method method,
      VariableExpr builderVarExpr,
      VariableExpr retryableCodeDefsVarExpr,
      VariableExpr retryParamDefsVarExpr) {
    String codeName = serviceConfig.getRetryCodeName(service, method);
    String retryParamName = serviceConfig.getRetryParamsName(service, method);
    String settingsGetterMethodName =
        String.format("%sSettings", JavaStyle.toLowerCamelCase(method.name()));

    Expr builderSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderVarExpr)
            .setMethodName(settingsGetterMethodName)
            .build();

    Function<String, ValueExpr> strValExprFn =
        s -> ValueExpr.withValue(StringObjectValue.withValue(s));
    builderSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderSettingsExpr)
            .setMethodName("setRetryableCodes")
            .setArguments(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(retryableCodeDefsVarExpr)
                    .setMethodName("get")
                    .setArguments(strValExprFn.apply(codeName))
                    .build())
            .build();
    builderSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderSettingsExpr)
            .setMethodName("setRetrySettings")
            .setArguments(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(retryParamDefsVarExpr)
                    .setMethodName("get")
                    .setArguments(strValExprFn.apply(retryParamName))
                    .build())
            .build();

    return builderSettingsExpr;
  }

  public static Expr createLroSettingsBuilderExpr(
      Service service,
      GapicServiceConfig serviceConfig,
      Method method,
      VariableExpr builderVarExpr,
      VariableExpr retryableCodeDefsVarExpr,
      VariableExpr retryParamDefsVarExpr) {
    Preconditions.checkState(
        method.hasLro(),
        String.format(
            "Tried to create LRO settings initialization for non-LRO method %s", method.name()));

    String codeName = serviceConfig.getRetryCodeName(service, method);
    String retryParamName = serviceConfig.getRetryParamsName(service, method);
    String settingsGetterMethodName =
        String.format("%sOperationSettings", JavaStyle.toLowerCamelCase(method.name()));

    Function<String, ValueExpr> strValExprFn =
        s -> ValueExpr.withValue(StringObjectValue.withValue(s));

    // Argument for setInitialCallSettings.
    Expr unaryCallSettingsExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("UnaryCallSettings"))
            .setGenerics(
                Arrays.asList(
                    method.inputType().reference(),
                    STATIC_TYPES.get("OperationSnapshot").reference()))
            .setMethodName("newUnaryCallSettingsBuilder")
            .build();
    unaryCallSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(unaryCallSettingsExpr)
            .setMethodName("setRetryableCodes")
            .setArguments(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(retryableCodeDefsVarExpr)
                    .setMethodName("get")
                    .setArguments(strValExprFn.apply(codeName))
                    .build())
            .build();
    unaryCallSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(unaryCallSettingsExpr)
            .setMethodName("setRetrySettings")
            .setArguments(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(retryParamDefsVarExpr)
                    .setMethodName("get")
                    .setArguments(strValExprFn.apply(retryParamName))
                    .build())
            .build();
    unaryCallSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(unaryCallSettingsExpr)
            .setMethodName("build")
            .build();

    Expr builderSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderVarExpr)
            .setMethodName(settingsGetterMethodName)
            .build();
    builderSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderSettingsExpr)
            .setMethodName("setInitialCallSettings")
            .setArguments(unaryCallSettingsExpr)
            .build();

    Function<TypeNode, VariableExpr> classFieldRefFn =
        t ->
            VariableExpr.builder()
                .setVariable(
                    Variable.builder()
                        .setType(TypeNode.withReference(ConcreteReference.withClazz(Class.class)))
                        .setName("class")
                        .build())
                .setStaticReferenceType(t)
                .build();
    builderSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderSettingsExpr)
            .setMethodName("setResponseTransformer")
            .setArguments(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(
                        TypeNode.withReference(
                            ConcreteReference.withClazz(
                                ProtoOperationTransformers.ResponseTransformer.class)))
                    .setMethodName("create")
                    .setArguments(classFieldRefFn.apply(method.lro().responseType()))
                    .build())
            .build();
    builderSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderSettingsExpr)
            .setMethodName("setMetadataTransformer")
            .setArguments(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(
                        TypeNode.withReference(
                            ConcreteReference.withClazz(
                                ProtoOperationTransformers.MetadataTransformer.class)))
                    .setMethodName("create")
                    .setArguments(classFieldRefFn.apply(method.lro().metadataType()))
                    .build())
            .build();

    // TODO(miraleung): Determine fianl LRO settings values here.
    Expr lroRetrySettingsExpr = createLroRetrySettingsExpr();
    Expr pollAlgoExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("OperationTimedPollAlgorithm"))
            .setMethodName("create")
            .setArguments(lroRetrySettingsExpr)
            .build();

    builderSettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderSettingsExpr)
            .setMethodName("setPollingAlgorithm")
            .setArguments(pollAlgoExpr)
            .build();

    return builderSettingsExpr;
  }

  private static Expr createRetryCodeDefinitionExpr(
      String codeName, List<Code> retryCodes, VariableExpr definitionsVarExpr) {
    // Construct something like `definitions.put("code_name",
    //          ImmutableSet.copYOf(Lists.<StatusCode.Code>newArrayList()));`
    MethodInvocationExpr codeListExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("Lists"))
            .setGenerics(Arrays.asList(STATUS_CODE_CODE_TYPE.reference()))
            .setMethodName("newArrayList")
            .setArguments(
                retryCodes.stream()
                    .map(c -> toStatusCodeEnumRefExpr(c))
                    .collect(Collectors.toList()))
            .build();

    MethodInvocationExpr codeSetExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("ImmutableSet"))
            .setMethodName("copyOf")
            .setArguments(codeListExpr)
            .build();
    return MethodInvocationExpr.builder()
        .setExprReferenceExpr(definitionsVarExpr)
        .setMethodName("put")
        .setArguments(ValueExpr.withValue(StringObjectValue.withValue(codeName)), codeSetExpr)
        .build();
  }

  private static List<Expr> createRetrySettingsExprs(
      String settingsName,
      GapicRetrySettings settings,
      VariableExpr settingsVarExpr,
      VariableExpr definitionsVarExpr) {
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
              .setArguments(createDurationOfMillisExpr(toValExpr(retryPolicy.getInitialBackoff())))
              .build();

      settingsBuilderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(settingsBuilderExpr)
              .setMethodName("setRetryDelayMultiplier")
              .setArguments(toValExpr(retryPolicy.getBackoffMultiplier()))
              .build();

      Preconditions.checkState(
          retryPolicy.hasMaxBackoff(),
          String.format("maxBackoff not found for setting %s", settingsName));
      settingsBuilderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(settingsBuilderExpr)
              .setMethodName("setMaxRetryDelay")
              .setArguments(createDurationOfMillisExpr(toValExpr(retryPolicy.getMaxBackoff())))
              .build();
    }

    if (!settings.kind().equals(GapicRetrySettings.Kind.NONE)) {
      settingsBuilderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(settingsBuilderExpr)
              .setMethodName("setInitialRpcTimeout")
              .setArguments(createDurationOfMillisExpr(toValExpr(settings.timeout())))
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
                .setArguments(createDurationOfMillisExpr(toValExpr(settings.timeout())))
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

  private static Expr createLroRetrySettingsExpr() {
    // TODO(miraleung): Determine fianl LRO settings values here.
    Expr lroRetrySettingsExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("RetrySettings"))
            .setMethodName("newBuilder")
            .build();

    lroRetrySettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(lroRetrySettingsExpr)
            .setMethodName("setInitialRetryDelay")
            .setArguments(
                createDurationOfMillisExpr(toValExpr(LRO_DEFAULT_INITIAL_POLL_DELAY_MILLIS)))
            .build();

    lroRetrySettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(lroRetrySettingsExpr)
            .setMethodName("setRetryDelayMultiplier")
            .setArguments(toValExpr(LRO_DEFAULT_POLL_DELAY_MULTIPLIER))
            .build();

    lroRetrySettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(lroRetrySettingsExpr)
            .setMethodName("setMaxRetryDelay")
            .setArguments(createDurationOfMillisExpr(toValExpr(LRO_DEFAULT_MAX_POLL_DELAY_MILLIS)))
            .build();

    Expr zeroDurationExpr =
        EnumRefExpr.builder().setType(STATIC_TYPES.get("Duration")).setName("ZERO").build();
    // TODO(miraleung): Find a way to add an "// ignored" comment here.
    lroRetrySettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(lroRetrySettingsExpr)
            .setMethodName("setInitialRpcTimeout")
            .setArguments(zeroDurationExpr)
            .build();

    // TODO(miraleung): Find a way to add an "// ignored" comment here.
    lroRetrySettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(lroRetrySettingsExpr)
            .setMethodName("setRpcTimeoutMultiplier")
            .setArguments(toValExpr(LRO_DEFAULT_MAX_RPC_TIMEOUT))
            .build();

    // TODO(miraleung): Find a way to add an "// ignored" comment here.
    lroRetrySettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(lroRetrySettingsExpr)
            .setMethodName("setMaxRpcTimeout")
            .setArguments(zeroDurationExpr)
            .build();

    lroRetrySettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(lroRetrySettingsExpr)
            .setMethodName("setTotalTimeout")
            .setArguments(
                createDurationOfMillisExpr(toValExpr(LRO_DEFAULT_TOTAL_POLL_TIMEOUT_MILLS)))
            .build();

    lroRetrySettingsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(lroRetrySettingsExpr)
            .setMethodName("build")
            .build();

    return lroRetrySettingsExpr;
  }

  private static EnumRefExpr toStatusCodeEnumRefExpr(Code code) {
    return EnumRefExpr.builder().setType(STATUS_CODE_CODE_TYPE).setName(code.name()).build();
  }

  private static ValueExpr toValExpr(long longValue) {
    return ValueExpr.withValue(
        PrimitiveValue.builder()
            .setType(TypeNode.LONG)
            .setValue(String.format("%dL", longValue))
            .build());
  }

  private static ValueExpr toValExpr(float floatValue) {
    return toValExpr((double) floatValue);
  }

  private static ValueExpr toValExpr(double val) {
    return ValueExpr.withValue(
        PrimitiveValue.builder()
            .setType(TypeNode.DOUBLE)
            .setValue(String.format("%.1f", val))
            .build());
  }

  private static ValueExpr toValExpr(Duration duration) {
    return toValExpr(Durations.toMillis(duration));
  }

  private static MethodInvocationExpr createDurationOfMillisExpr(ValueExpr valExpr) {
    return MethodInvocationExpr.builder()
        .setStaticReferenceType(STATIC_TYPES.get("Duration"))
        .setMethodName("ofMillis")
        .setArguments(valExpr)
        .build();
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            org.threeten.bp.Duration.class,
            ImmutableMap.class,
            ImmutableSet.class,
            Lists.class,
            OperationSnapshot.class,
            OperationTimedPollAlgorithm.class,
            ProtoOperationTransformers.class,
            RetrySettings.class,
            StatusCode.class,
            UnaryCallSettings.class);

    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }
}
