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

package com.google.api.generator.gapic.protoparser;

import static com.google.api.generator.gapic.utils.ParserUtils.parseMapFromYamlFile;

import com.google.api.generator.gapic.model.GapicLanguageSettings;
import com.google.common.annotations.VisibleForTesting;
import java.util.Map;
import java.util.Optional;

public class GapicLanguageSettingsParser {
  private static final String YAML_KEY_LANGUAGE_SETTINGS = "language_settings";
  private static final String YAML_KEY_JAVA = "java";
  private static final String YAML_KEY_PACKAGE_NAME = "package_name";
  private static final String YAML_KEY_INTERFACE_NAMES = "interface_names";

  public static Optional<GapicLanguageSettings> parse(Optional<String> gapicYamlConfigFilePathOpt) {
    return gapicYamlConfigFilePathOpt.isPresent()
        ? parse(gapicYamlConfigFilePathOpt.get())
        : Optional.empty();
  }

  @VisibleForTesting
  static Optional<GapicLanguageSettings> parse(String gapicYamlConfigFilePath) {
    Optional<Map<String, Object>> yamlMapOpt = parseMapFromYamlFile(gapicYamlConfigFilePath);
    if (!yamlMapOpt.isPresent()) {
      return Optional.empty();
    }

    return parseFromMap(yamlMapOpt.get());
  }

  private static Optional<GapicLanguageSettings> parseFromMap(Map<String, Object> yamlMap) {
    if (!yamlMap.containsKey(YAML_KEY_LANGUAGE_SETTINGS)) {
      return Optional.empty();
    }

    Map<String, Object> languageYamlConfig =
        (Map<String, Object>) yamlMap.get(YAML_KEY_LANGUAGE_SETTINGS);
    if (!languageYamlConfig.containsKey(YAML_KEY_JAVA)) {
      return Optional.empty();
    }

    Map<String, Object> javaYamlConfig =
        (Map<String, Object>) languageYamlConfig.get(YAML_KEY_JAVA);
    if (!javaYamlConfig.containsKey(YAML_KEY_PACKAGE_NAME)) {
      return Optional.empty();
    }

    GapicLanguageSettings.Builder gapicLanguageSettingsBuilder =
        GapicLanguageSettings.builder()
            .setPakkage((String) javaYamlConfig.get(YAML_KEY_PACKAGE_NAME));

    if (!javaYamlConfig.containsKey(YAML_KEY_INTERFACE_NAMES)) {
      return Optional.of(gapicLanguageSettingsBuilder.build());
    }

    return Optional.of(
        gapicLanguageSettingsBuilder
            .setProtoServiceToJavaClassname(
                (Map<String, String>) javaYamlConfig.get(YAML_KEY_INTERFACE_NAMES))
            .build());
  }
}
