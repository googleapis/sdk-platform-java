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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.NullObjectValue;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.Value;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.samplecode.SampleComposerUtil;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.cloud.tools.snippetgen.configlanguage.v1.GeneratorOutputLanguage;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Rpc;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Snippet;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfig;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfigMetadata;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetSignature;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Statement;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Type;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** A convenience wrapper for the data in com.google.cloud.tools.snippetgen.configlanguage for generating configured Java samples. */
public class GapicSnippetConfig {

  private final Map<String, Object> configMetadata;
  private final Map<String, Object> configRpc;
  private final Map<String, Object> configSignature;
  private final LinkedHashMap<String, List> configSignatureParameters;
  private final Map<String, Object> configSnippet;

  public GapicSnippetConfig(
      Map<String, Object> configMetadata,
      Map<String, Object> configRpc,
      Map<String, Object> configSignature,
      LinkedHashMap<String, List> configSignatureParameters,
      Map<String, Object> configSnippet) {
    this.configMetadata = configMetadata;
    this.configRpc = configRpc;
    this.configSignature = configSignature;
    this.configSignatureParameters = configSignatureParameters;
    this.configSnippet = configSnippet;
  }

  private static final GapicSnippetConfig emptyGapicSnippetConfig =
      new GapicSnippetConfig(
          Collections.emptyMap(),
          Collections.emptyMap(),
          Collections.emptyMap(),
          new LinkedHashMap(),
          Collections.emptyMap());

  // Order matters
  // Key is name of parameter
  // Value is array with first element being parameter description, second element is the TypeKind
  // of the parameter,
  // third element is full parameter
  public static LinkedHashMap<String, List> parseSignatureParametersForHeaderStatement(
      SnippetSignature rawConfigSignature) {
    LinkedHashMap<String, List> configSignatureParameters = new LinkedHashMap<>();
    for (Statement.Declaration parameter : rawConfigSignature.getParametersList()) {
      configSignatureParameters.put(
          parameter.getName(),
          // TODO: test for when description is null
          Arrays.asList(
              parameter.getDescription(), parameter.getType().getTypeKindCase(), parameter));
    }
    return configSignatureParameters;
  }

  // Generates parameters for main method
  public static LinkedHashMap<VariableExpr, AssignmentExpr> composeMainMethodArgs(
      LinkedHashMap<String, List> configSignatureParameters) {
    LinkedHashMap<VariableExpr, AssignmentExpr> mainMethodVariables = new LinkedHashMap<>();
    Iterator<Map.Entry<String, List>> iterator = configSignatureParameters.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, List> param = iterator.next();
      Type.TypeKindCase paramTypeKindCase = (Type.TypeKindCase) param.getValue().get(1);
      Statement.Declaration parameter = (Statement.Declaration) param.getValue().get(2);
      // TODO: handle other TypeKinds; currently only handles SCALAR_TYPE for prototype
      // Not handled yet: MapType, MessageType, RepeatedType, EnumType, BytesType
      if (paramTypeKindCase == Type.TypeKindCase.SCALAR_TYPE) {
        Type.ScalarType varScalarType = parameter.getType().getScalarType();
        VariableExpr methodVar =
            VariableExpr.builder()
                .setVariable(
                    Variable.builder()
                        // convert Type to TypeNode
                        .setType(SampleComposerUtil.convertScalarTypeToTypeNode(varScalarType))
                        // Key is the name of the parameter
                        .setName(JavaStyle.toLowerCamelCase(param.getKey()))
                        .build())
                .setIsDecl(true)
                .build();
        // If parameters have default values, assign them
        if (parameter.hasValue()) {
          AssignmentExpr methodAssignment =
              AssignmentExpr.builder()
                  .setVariableExpr(methodVar)
                  .setValueExpr(
                      ValueExpr.withValue(
                          StringObjectValue.withValue(parameter.getValue().getStringValue())))
                  .build();
          mainMethodVariables.put(methodVar, methodAssignment);
        } else {
          Value value = NullObjectValue.create();
          Expr valueExpr = ValueExpr.builder().setValue(value).build();
          AssignmentExpr methodAssignment =
              AssignmentExpr.builder().setVariableExpr(methodVar).setValueExpr(valueExpr).build();
          mainMethodVariables.put(methodVar, methodAssignment);
        }
      }
    }
    return mainMethodVariables;
  }

  public static GapicSnippetConfig create(SnippetConfig snippetConfig) {

    SnippetConfigMetadata rawConfigMetadata = snippetConfig.getMetadata();
    Map<String, Object> configMetadata = new HashMap<>();
    // check if Java is a skipped language
    if (rawConfigMetadata.getSkipped()
        || rawConfigMetadata.getSkippedLanguagesList().contains(GeneratorOutputLanguage.JAVA)) {
      return emptyGapicSnippetConfig;
    }
    configMetadata.put("config_id", rawConfigMetadata.getConfigId());
    configMetadata.put("snippet_name", rawConfigMetadata.getSnippetName());
    configMetadata.put("snippet_description", rawConfigMetadata.getSnippetDescription());

    Rpc rawConfigRpc = snippetConfig.getRpc();
    Map<String, Object> configRpc = new HashMap<>();
    configRpc.put("proto_package", rawConfigRpc.getProtoPackage());
    // TODO: If multiple versions are listed here, need to generate same sample for multiple
    // versions; figure out what it should look like
    // For now just taking first version listed
    configRpc.put("api_version", rawConfigRpc.getApiVersionList().get(0));
    configRpc.put("service_name", rawConfigRpc.getServiceName());
    configRpc.put("rpc_name", rawConfigRpc.getRpcName());

    SnippetSignature rawConfigSignature = snippetConfig.getSignature();
    Map<String, Object> configSignature = new HashMap<>();

    configSignature.put("sync_preference", rawConfigSignature.getSyncPreference());
    configSignature.put("has_return_type", rawConfigSignature.hasReturnType());
    if (rawConfigSignature.hasReturnType()) {
      configSignature.put("return_type", rawConfigSignature.getReturnType());
    }

    // Order of parameters matters
    LinkedHashMap<String, List> configSignatureParameters =
        parseSignatureParametersForHeaderStatement(rawConfigSignature);

    Snippet rawConfigSnippet = snippetConfig.getSnippet();
    Map<String, Object> configSnippet = new HashMap<>();

    configSnippet.put(
        "has_service_client_initialization", rawConfigSnippet.hasServiceClientInitialization());
    configSnippet.put("has_custom_service_endpoint", FALSE);
    // If service client initialization exists, add values to configSnippet
    if (rawConfigSnippet.hasServiceClientInitialization()) {
      // TODO: parse statement type
      configSnippet.put(
          "pre_client_initialization",
          rawConfigSnippet
              .getServiceClientInitializationOrBuilder()
              .getPreClientInitializationList());
      // If specified endpoint exists, add value to configSnippet
      // Endpoint should be `region`-`host`:`port`. If port is not specified, use
      // Parser.DEFAULT_PORT
      if (rawConfigSnippet.getServiceClientInitializationOrBuilder().hasCustomServiceEndpoint()) {
        configSnippet.replace("has_custom_service_endpoint", TRUE);
        String region =
            rawConfigSnippet
                .getServiceClientInitializationOrBuilder()
                .getCustomServiceEndpoint()
                .getRegion();
        String host =
            rawConfigSnippet
                .getServiceClientInitializationOrBuilder()
                .getCustomServiceEndpoint()
                .getHost();
        int port =
            rawConfigSnippet
                .getServiceClientInitializationOrBuilder()
                .getCustomServiceEndpoint()
                .getPort();
        String endpoint = region + "-" + host + ":";
        // If port doesn't exist, then use DEFAULT_PORT
        if (port == 0) {
          endpoint = endpoint + Parser.DEFAULT_PORT;
        } else {
          endpoint = endpoint + port;
        }
        configSnippet.put("endpoint", endpoint);
      }
    }

    configSnippet.put("call_type", rawConfigSnippet.getCallCase().name());
    if (rawConfigSnippet.hasStandard()) {
      configSnippet.put(
          "pre_request_initialization_statements",
          rawConfigSnippet
              .getStandard()
              .getRequestInitialization()
              .getPreRequestInitializationList());
      configSnippet.put(
          "request_value",
          SampleComposerUtil.convertExpressionToString(
              rawConfigSnippet.getStandard().getRequestInitialization().getRequestValue()));
      configSnippet.put(
          "request_name",
          JavaStyle.toLowerCamelCase(
              rawConfigSnippet.getStandard().getRequestInitialization().getRequestName()));
      configSnippet.put(
          "pre_call_statements", rawConfigSnippet.getStandard().getCall().getPreCallList());
      configSnippet.put(
          "response_name",
          JavaStyle.toLowerCamelCase(
              rawConfigSnippet.getStandard().getResponseHandling().getResponseName()));
    }
    // TODO: handle other call types
    //    else if(rawConfigSnippet.hasLro()){
    //      configSnippet.put("has_pre_request_initialization",
    // rawConfigSnippet.getLro().hasRequestInitialization());
    //      configSnippet.put("pre_request_initialization",
    // rawConfigSnippet.getLro().getRequestInitialization());
    //    }
    //    else if(rawConfigSnippet.hasPaginated()){
    //      configSnippet.put("has_pre_request_initialization",
    // rawConfigSnippet.getPaginated().hasRequestInitialization());
    //      configSnippet.put("pre_request_initialization",
    // rawConfigSnippet.getPaginated().getRequestInitialization());
    //    }
    //    else if(rawConfigSnippet.hasClientStreaming()){
    //      configSnippet.put("has_pre_request_initialization",
    // rawConfigSnippet.getClientStreaming().hasRequestInitialization());
    //      configSnippet.put("pre_request_initialization",
    // rawConfigSnippet.getClientStreaming().getRequestInitialization());
    //    }
    //    else if(rawConfigSnippet.hasServerStreaming()){
    //      configSnippet.put("has_pre_request_initialization",
    // rawConfigSnippet.getServerStreaming().hasRequestInitialization());
    //      configSnippet.put("pre_request_initialization",
    // rawConfigSnippet.getServerStreaming().getRequestInitialization());
    //    }
    //    else if(rawConfigSnippet.hasBidiStreaming()){
    //      configSnippet.put("has_pre_request_initialization",
    // rawConfigSnippet.getBidiStreaming().hasRequestInitialization());
    //      configSnippet.put("pre_request_initialization",
    // rawConfigSnippet.getBidiStreaming().getRequestInitialization());
    //    }
    configSnippet.put("final_statements", rawConfigSnippet.getFinalStatementsList());

    return new GapicSnippetConfig(
        configMetadata, configRpc, configSignature, configSignatureParameters, configSnippet);
  }

  public static String getResponseValue(GapicSnippetConfig gapicSnippetConfig) {
    return (String) gapicSnippetConfig.configSnippet.get("response_name");
  }

  public static Map<String, Object> getRequestValue(GapicSnippetConfig gapicSnippetConfig) {
    return (Map<String, Object>) gapicSnippetConfig.configSnippet.get("request_value");
  }

  public static String getRequestName(GapicSnippetConfig gapicSnippetConfig) {
    return (String) gapicSnippetConfig.configSnippet.get("request_name");
  }

  public static List<Statement> getPreCallStatements(GapicSnippetConfig gapicSnippetConfig) {
    return (List<Statement>) gapicSnippetConfig.configSnippet.get("pre_call_statements");
  }

  public static List<Statement> getPreRequestInitializationStatements(
      GapicSnippetConfig gapicSnippetConfig) {
    return (List<Statement>)
        gapicSnippetConfig.configSnippet.get("pre_request_initialization_statements");
  }

  public static List<Statement> getFinalStatements(GapicSnippetConfig gapicSnippetConfig) {
    return (List<Statement>) gapicSnippetConfig.configSnippet.get("final_statements");
  }

  public static String getConfiguredSnippetReturnType(GapicSnippetConfig gapicSnippetConfig) {
    if ((Boolean) gapicSnippetConfig.configSignature.get("has_return_type")) {
      return (String)
          SampleComposerUtil.convertTypeToString(
              (Type) gapicSnippetConfig.configSignature.get("return_type"));
    }
    return "";
  }

  // TODO: parse through and convert to Statement
  public static List<Statement> getPreClientInitializationStatements(
      GapicSnippetConfig gapicSnippetConfig) {
    if ((Boolean) gapicSnippetConfig.configSnippet.get("has_service_client_initialization")) {
      return (List<Statement>) gapicSnippetConfig.configSnippet.get("pre_client_initialization");
    }
    return null;
  }

  public static String getConfiguredSnippetEndpoint(GapicSnippetConfig gapicSnippetConfig) {
    if ((Boolean) gapicSnippetConfig.configSnippet.get("has_custom_service_endpoint")) {
      return (String) gapicSnippetConfig.configSnippet.get("endpoint");
    }
    return null;
  }

  public static LinkedHashMap<String, List> getConfiguredSnippetSignatureParameters(
      GapicSnippetConfig gapicSnippetConfig) {
    return gapicSnippetConfig.configSignatureParameters;
  }

  public static String getConfiguredSnippetSnippetName(GapicSnippetConfig gapicSnippetConfig) {
    return (String) gapicSnippetConfig.configMetadata.get("snippet_name");
  }

  public static String getConfiguredSnippetSnippetDescription(
      GapicSnippetConfig gapicSnippetConfig) {
    return (String) gapicSnippetConfig.configMetadata.get("snippet_description");
  }

  public static String getConfiguredSnippetRpcName(GapicSnippetConfig gapicSnippetConfig) {
    return (String) gapicSnippetConfig.configRpc.get("rpc_name");
  }

  public static String getConfiguredSnippetServiceName(GapicSnippetConfig gapicSnippetConfig) {
    return (String) gapicSnippetConfig.configRpc.get("service_name");
  }

  // PackageName for configured Snippet
  public static String getConfiguredSnippetPackageString(GapicSnippetConfig gapicSnippetConfig) {
    return gapicSnippetConfig.configRpc.get("proto_package")
        + "."
        + gapicSnippetConfig.configRpc.get("api_version")
        + ".samples";
  }

  // PakkageName for Types in configured Snippet
  public static String getConfiguredSnippetPakkageString(GapicSnippetConfig gapicSnippetConfig) {
    return "com."
        + gapicSnippetConfig.configRpc.get("proto_package")
        + "."
        + gapicSnippetConfig.configRpc.get("api_version");
  }

  // ApiShortName for regionTag for configured Snippet
  private static String parseProtoPackage(String protopackage) {
    // Get the ApiShortName as last part of the protoPackage
    String apiShortName = Iterables.getLast(Splitter.on(".").split(protopackage), protopackage);
    return apiShortName;
  }

  // Sync preference for regionTag.
  // TODO: build in additional logic; if BOTH then need to generate multiple samples. Confirm that
  // Java prefers Async
  public static Boolean parseSyncPreference(GapicSnippetConfig gapicSnippetConfig) {
    Boolean isAsync = TRUE;
    if (gapicSnippetConfig.configSignature.get("sync_preference")
        == SnippetSignature.SyncPreference.PREFER_SYNC) {
      isAsync = FALSE;
    }
    return isAsync;
  }

  public static RegionTag getConfiguredSnippetRegionTag(GapicSnippetConfig gapicSnippetConfig) {
    return RegionTag.builder()
        .setApiShortName(
            parseProtoPackage(String.valueOf(gapicSnippetConfig.configRpc.get("proto_package"))))
        .setTagType("config")
        .setConfigId(String.valueOf(gapicSnippetConfig.configMetadata.get("config_id")))
        .setServiceName(String.valueOf(gapicSnippetConfig.configRpc.get("service_name")))
        .setRpcName(String.valueOf(gapicSnippetConfig.configRpc.get("rpc_name")))
        .setApiVersion(String.valueOf(gapicSnippetConfig.configRpc.get("api_version")))
        .setIsAsynchronous(parseSyncPreference(gapicSnippetConfig))
        .build();
  }
}
