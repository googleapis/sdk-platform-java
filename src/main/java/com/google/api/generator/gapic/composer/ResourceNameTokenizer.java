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

import com.google.api.pathtemplate.PathTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourceNameTokenizer {
  private static final String SLASH = "/";
  private static final String LEFT_BRACE = "{";
  private static final String RIGHT_BRACE = "}";

  static List<List<String>> parseTokenHierarchy(List<String> patterns) {
    List<String> nonSlashSepStrings = Arrays.asList("}_{", "}-{", "}.{", "}~{");

    List<List<String>> tokenHierachies = new ArrayList<>();
    for (String pattern : patterns) {
      List<String> hierarchy = new ArrayList<>();
      Set<String> vars = PathTemplate.create(pattern).vars();
      String[] patternTokens = pattern.split(SLASH);
      for (String patternToken : patternTokens) {
        if (patternToken.startsWith(LEFT_BRACE) && patternToken.endsWith(RIGHT_BRACE)) {
          String processedPatternToken = patternToken;

          // Handle non-slash separators.
          if (nonSlashSepStrings.stream().anyMatch(s -> patternToken.contains(s))) {
            for (String str : nonSlashSepStrings) {
              processedPatternToken = processedPatternToken.replace(str, "_");
            }
          } else {
            // Handles wildcards.
            processedPatternToken =
                vars.stream()
                    .filter(v -> patternToken.contains(v))
                    .collect(Collectors.toList())
                    .get(0);
          }
          hierarchy.add(processedPatternToken.replace("{", "").replace("}", ""));
        }
      }
      tokenHierachies.add(hierarchy);
    }
    return tokenHierachies;
  }
}
