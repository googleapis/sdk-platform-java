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

package com.google.api.generator.spring.composer;

import com.google.api.generator.engine.ast.AstNode;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicRetrySettings;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.protobuf.Duration;
import com.google.protobuf.util.Durations;
import io.grpc.serviceconfig.MethodConfig.RetryPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Utils {
  private static final TypeStore FIXED_TYPESTORE = createStaticTypes();

  private static final String BRAND_NAME = "spring.cloud.gcp";

  public static String getLibName(GapicContext context) {
    String pakkageName = context.services().get(0).pakkage();
    List<String> pakkagePhrases = Splitter.on(".").splitToList(pakkageName);
    // TODO: confirm if this is guaranteed pattern: xx.[...].xx.lib-name.v[version]
    // eg. for vision proto: "com.google.cloud.vision.v1"
    // https://github.com/googleapis/java-vision/blob/main/proto-google-cloud-vision-v1/src/main/proto/google/cloud/vision/v1/image_annotator.proto#L36
    return pakkagePhrases.get(pakkagePhrases.size() - 2);
  }

  public static String springPropertyPrefix(String libName, String serviceName) {
    return "spring.cloud.gcp.autoconfig."
        + CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_HYPHEN, libName)
        + "."
        + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, serviceName);
  }

  public static List<? extends AstNode> processRetrySettings(
      Service service,
      GapicServiceConfig gapicServiceConfig,
      TypeNode thisClassType,
      Function<String, List<? extends AstNode>> perMethodFuncBeforeSettings,
      BiFunction<List<String>, Expr, List<? extends AstNode>> processFunc,
      Function<String, List<? extends AstNode>> perMethodFuncAfterSettings) {
    List resultList = new ArrayList<>();
    for (Method method : service.methods()) {
      String methodName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, method.name());
      String retryParamName = gapicServiceConfig.getRetryParamsName(service, method);
      resultList.addAll(perMethodFuncBeforeSettings.apply(methodName));
      // follow logic in:
      // com.google.api.generator.gapic.composer.common.RetrySettingsComposer.createRetrySettingsExprs
      // Build the settings object for each config.

      String settingsName = retryParamName;
      GapicRetrySettings settings =
          gapicServiceConfig.getAllGapicRetrySettings(service).get(retryParamName);
      RetryPolicy retryPolicy = settings.retryPolicy();
      if (settings.kind().equals(GapicRetrySettings.Kind.FULL)) {
        Preconditions.checkState(
            retryPolicy.hasInitialBackoff(),
            String.format("initialBackoff not found for setting %s", settingsName));

        resultList.addAll(
            processFunc.apply(
                Arrays.asList(methodName, "InitialRetryDelay"),
                createDurationOfMillisExpr(toValExpr(retryPolicy.getInitialBackoff()))));

        resultList.addAll(
            processFunc.apply(
                Arrays.asList(methodName, "RetryDelayMultiplier"),
                toValExpr(retryPolicy.getBackoffMultiplier())));

        Preconditions.checkState(
            retryPolicy.hasMaxBackoff(),
            String.format("maxBackoff not found for setting %s", settingsName));

        resultList.addAll(
            processFunc.apply(
                Arrays.asList(methodName, "MaxRetryDelay"),
                createDurationOfMillisExpr(toValExpr(retryPolicy.getMaxBackoff()))));
      }

      if (!settings.kind().equals(GapicRetrySettings.Kind.NONE)) {

        resultList.addAll(
            processFunc.apply(
                Arrays.asList(methodName, "InitialRpcTimeout"),
                createDurationOfMillisExpr(toValExpr(settings.timeout()))));
      }

      // This will always be done, no matter the type of the retry settings object.
      resultList.addAll(
          processFunc.apply(
              Arrays.asList(methodName, "RpcTimeoutMultiplier"),
              ValueExpr.withValue(
                  // this value is hardcoded in, risk of gapic- changes in future?
                  // com.google.api.generator.gapic.composer.common.RetrySettingsComposer.createRetrySettingsExprs
                  PrimitiveValue.builder().setType(TypeNode.DOUBLE).setValue("1.0").build())));

      if (!settings.kind().equals(GapicRetrySettings.Kind.NONE)) {
        for (String setterMethodName : Arrays.asList("MaxRpcTimeout", "TotalTimeout")) {

          resultList.addAll(
              processFunc.apply(
                  Arrays.asList(methodName, setterMethodName),
                  createDurationOfMillisExpr(toValExpr(settings.timeout()))));
        }
      }

      resultList.addAll(perMethodFuncAfterSettings.apply(methodName));
    }
    return resultList;
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

  private static TypeStore createStaticTypes() {
    List<Class<?>> concreteClazzes = Arrays.asList(org.threeten.bp.Duration.class);
    return new TypeStore(concreteClazzes);
  }

  private static MethodInvocationExpr createDurationOfMillisExpr(ValueExpr valExpr) {
    return MethodInvocationExpr.builder()
        .setStaticReferenceType(FIXED_TYPESTORE.get("Duration"))
        .setMethodName("ofMillis")
        .setArguments(valExpr)
        .setReturnType(FIXED_TYPESTORE.get("Duration"))
        .build();
  }
}
