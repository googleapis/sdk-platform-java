package com.google.api.generator.gapic.protoparser;

import static com.google.api.generator.gapic.utils.ParserUtils.parseMapFromYamlFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CustomizationParser {

  private static final String ADD_BASE_RESOURCE_NAME = "add-base-resource-name";

  private CustomizationParser() {}

  public static List<String> parseAddBaseResourceNameCustomization(String yamlPath) {
    Optional<Map<String, Object>> customizationsOpt =
        parseMapFromYamlFile(yamlPath);

    if (!customizationsOpt.isPresent()) {
      return Collections.emptyList();
    }

    Map<String, Object> customizations = customizationsOpt.get();
    return (List<String>)
        customizations.getOrDefault(ADD_BASE_RESOURCE_NAME, Collections.emptyList());
  }
}
