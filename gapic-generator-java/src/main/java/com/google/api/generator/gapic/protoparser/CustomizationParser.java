// Copyright 2023 Google LLC
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CustomizationParser {

  private static final String ADD_BASE_RESOURCE_NAME = "add-base-resource-name";

  private CustomizationParser() {}

  public static List<String> parseAddBaseResourceNameCustomization(String yamlPath) {
    Optional<Map<String, Object>> customizationsOpt = parseMapFromYamlFile(yamlPath);

    if (!customizationsOpt.isPresent()) {
      return Collections.emptyList();
    }

    Map<String, Object> customizations = customizationsOpt.get();
    return (List<String>)
        customizations.getOrDefault(ADD_BASE_RESOURCE_NAME, Collections.emptyList());
  }
}
