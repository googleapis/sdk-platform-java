package com.google.api.generator.gapic.model;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.google.cloud.tools.snippetgen.configlanguage.v1.GeneratorOutputLanguage;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Rpc;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Snippet;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfig;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfigMetadata;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetSignature;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Statement;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Represents the data in a snippet_config.json file. */
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

  // TODO Update to parse logic for other types
  private static int parseSnippetCallType(Snippet rawConfigSnippet) {
    Snippet.CallCase call_type = rawConfigSnippet.getCallCase();
    if (call_type == Snippet.CallCase.STANDARD) {
      return 2;
    }
    return 2;
  }

  // Order matters
  // Key is name of parameter
  // Value is array with first element being parameter description, second element is type of
  // parameter, and third element the actual value of the parameter
  public static LinkedHashMap<String, List> parseSignatureParameters(
      SnippetSignature rawConfigSignature) {
    LinkedHashMap<String, List> configSignatureParameters = new LinkedHashMap<>();
    for (Statement.Declaration parameter : rawConfigSignature.getParametersList()) {
      configSignatureParameters.put(
          parameter.getName(),
          Arrays.asList(parameter.getDescription(), parameter.getType(), parameter.getValue()));
    }
    return configSignatureParameters;
  }

  public static GapicSnippetConfig create(Optional<SnippetConfig> snippetConfigOpt) {
    if (!snippetConfigOpt.isPresent()) {
      return emptyGapicSnippetConfig;
    }

    SnippetConfig snippetConfig = snippetConfigOpt.get();
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

    // Order of parameters matters
    LinkedHashMap<String, List> configSignatureParameters =
        parseSignatureParameters(rawConfigSignature);

    Snippet rawConfigSnippet = snippetConfig.getSnippet();
    Map<String, Object> configSnippet = new HashMap<>();

    // TODO: Depending on call type, generate a different sample
    //    STANDARD(2),
    //    PAGINATED(3),
    //    LRO(4),
    //    CLIENT_STREAMING(5),
    //    SERVER_STREAMING(6),
    //    BIDI_STREAMING(7),
    // For now, just generate a Standard sample
    configSnippet.put("call_type", parseSnippetCallType(rawConfigSnippet));

    return new GapicSnippetConfig(
        configMetadata, configRpc, configSignature, configSignatureParameters, configSnippet);
  }

  // for scratch testing
  public static String getConfiguredSnippetCallType(GapicSnippetConfig gapicSnippetConfig) {
    return gapicSnippetConfig.configSignatureParameters.toString();
  }

  public static Map<String, List> getConfiguredSnippetSignatureParameters(
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

  // PackageName for configured Snippet
  public static String getConfiguredSnippetPackageString(GapicSnippetConfig gapicSnippetConfig) {
    return (String) gapicSnippetConfig.configRpc.get("proto_package")
        + "."
        + gapicSnippetConfig.configRpc.get("api_version")
        + ".samples";
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
