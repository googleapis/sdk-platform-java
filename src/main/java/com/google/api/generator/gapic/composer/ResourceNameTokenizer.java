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
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourceNameTokenizer {
  private static final String LEFT_BRACE = "{";
  private static final String RIGHT_BRACE = "}";
  private static final String SLASH = "/";
  private static final String EMPTY = "";

  private static final String EQUALS_WILDCARD = "=*";
  private static final String EQUALS_PATH_WILDCARD = "=**";

  static List<List<String>> parseTokenHierarchy(List<String> patterns) {
    List<String> nonSlashSepStrings = Arrays.asList("}_{", "}-{", "}.{", "}~{");

    List<List<String>> tokenHierachies = new ArrayList<>();
    for (String pattern : patterns) {
      List<String> hierarchy = new ArrayList<>();
      Set<String> vars = PathTemplate.create(pattern).vars();
      String[] patternTokens = pattern.split(SLASH);
      for (String patternToken : patternTokens) {
        if (patternToken.startsWith(LEFT_BRACE) && patternToken.endsWith(RIGHT_BRACE)) {
          String processedPatternToken =
              // Replacement order matters - ensure the first is not a subcomponent of the second.
              patternToken.replace(EQUALS_PATH_WILDCARD, EMPTY).replace(EQUALS_WILDCARD, EMPTY);

          // Handle non-slash separators.
          if (nonSlashSepStrings.stream().anyMatch(s -> patternToken.contains(s))) {
            for (String str : nonSlashSepStrings) {
              processedPatternToken = processedPatternToken.replace(str, "_");
            }
          } else {
            final int processedPatternTokenLength = processedPatternToken.length();
            // Handles wildcards.
            List<String> candidateVars =
                vars.stream()
                    // Check that the token size is within ~3 of the var, to avoid mismatching on
                    // variables with same-named subcomponents.
                    // Otherwise, "customer_client_link" will match with "customer".
                    .filter(
                        v ->
                            patternToken.contains(v)
                                // Accounting for braces.
                                && processedPatternTokenLength - v.length() < 3)
                    .collect(Collectors.toList());
            Preconditions.checkState(
                !candidateVars.isEmpty(),
                String.format(
                    "No variable candidates found for token %s in pattern %s",
                    processedPatternToken, pattern));
            processedPatternToken = candidateVars.get(0);
          }
          hierarchy.add(
              processedPatternToken.replace(LEFT_BRACE, EMPTY).replace(RIGHT_BRACE, EMPTY));
        }
      }
      tokenHierachies.add(hierarchy);
    }
    return tokenHierachies;
  }
}
