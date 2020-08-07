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

import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.ResourceReference;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.api.generator.gapic.utils.ResourceNameConstants;
import com.google.api.pathtemplate.PathTemplate;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ResourceReferenceParser {
  private static final String EMPTY_STRING = "";
  private static final String LEFT_BRACE = "{";
  private static final String RIGHT_BRACE = "}";
  private static final String SLASH = "/";

  public static List<ResourceName> parseResourceNames(
      ResourceReference resourceReference,
      String servicePackage,
      Map<String, ResourceName> resourceNames,
      Map<String, ResourceName> patternsToResourceNames) {
    ResourceName resourceName = resourceNames.get(resourceReference.resourceTypeString());
    Preconditions.checkNotNull(
        resourceName,
        String.format(
            "No resource definition found for reference with type %s",
            resourceReference.resourceTypeString()));
    if (!resourceReference.isChildType() || resourceName.isOnlyWildcard()) {
      return Arrays.asList(resourceName);
    }

    // Create a parent ResourceName for each pattern.
    List<ResourceName> parentResourceNames = new ArrayList<>();
    Set<String> resourceTypeStrings = new HashSet<>();
    for (String pattern : resourceName.patterns()) {
      Optional<ResourceName> parentResourceNameOpt =
          parseParentResourceName(
              pattern,
              servicePackage,
              resourceName.pakkage(),
              resourceName.resourceTypeString(),
              patternsToResourceNames);
      // Prevent duplicates.
      if (parentResourceNameOpt.isPresent()
          && !resourceTypeStrings.contains(parentResourceNameOpt.get().resourceTypeString())) {
        ResourceName parentResourceName = parentResourceNameOpt.get();
        parentResourceNames.add(parentResourceName);
        resourceTypeStrings.add(parentResourceName.resourceTypeString());
      }
    }
    return parentResourceNames;
  }

  @VisibleForTesting
  static Optional<ResourceName> parseParentResourceName(
      String pattern,
      String servicePackage,
      String resourcePackage,
      String resourceTypeString,
      Map<String, ResourceName> patternsToResourceNames) {
    Optional<String> parentPatternOpt = parseParentPattern(pattern);
    if (!parentPatternOpt.isPresent()) {
      return Optional.empty();
    }

    String parentPattern = parentPatternOpt.get();
    if (patternsToResourceNames.get(parentPattern) != null) {
      return Optional.of(patternsToResourceNames.get(parentPattern));
    }

    String[] tokens = parentPattern.split(SLASH);
    int numTokens = tokens.length;
    String lastToken = tokens[numTokens - 1];
    Set<String> variableNames = PathTemplate.create(parentPattern).vars();
    String parentVariableName = null;
    // Try the extracting from the conventional pattern first.
    // E.g. Profile is the parent of users/{user}/profiles/{profile}/blurbs/{blurb}.
    for (String variableName : variableNames) {
      if (lastToken.contains(variableName)) {
        parentVariableName = variableName;
      }
    }

    // TODO(miraleung): Add unit tests that exercise these edge cases.
    // Check unconventional patterns.
    // Assume that non-slash separators will only ever appear in the last component of a patetrn.
    // That is, they will not appear in the parent components under consideration.
    if (Strings.isNullOrEmpty(parentVariableName)) {
      String lowerTypeName =
          resourceTypeString.substring(resourceTypeString.indexOf(SLASH) + 1).toLowerCase();
      // Check for the parent of users/{user}/profile/blurbs/legacy/{legacy_user}~{blurb}.
      // We're curerntly at users/{user}/profile/blurbs.
      if ((lastToken.endsWith("s") || lastToken.contains(lowerTypeName)) && numTokens > 2) {
        // Not the singleton we're looking for, back up.
        parentVariableName = tokens[numTokens - 2];
      } else {
        // Check for the parent of users/{user}/profile/blurbs/{blurb}.
        // We're curerntly at users/{user}/profile.
        parentVariableName = lastToken;
      }
      parentVariableName =
          parentVariableName.replace(LEFT_BRACE, EMPTY_STRING).replace(RIGHT_BRACE, EMPTY_STRING);
    }
    Preconditions.checkNotNull(
        parentVariableName,
        String.format("Could not parse variable name from pattern %s", parentPattern));

    // Use the package where the resource was defined, only if that is a sub-package of the
    // current service (which is assumed to be the project's package).
    String pakkage = resolvePackages(resourcePackage, servicePackage);
    String parentResourceTypeString =
        String.format(
            "%s/%s",
            resourceTypeString.substring(0, resourceTypeString.indexOf(SLASH)),
            JavaStyle.toUpperCamelCase(parentVariableName));
    ResourceName parentResourceName =
        ResourceName.builder()
            .setVariableName(parentVariableName)
            .setPakkage(pakkage)
            .setResourceTypeString(parentResourceTypeString)
            .setPatterns(Arrays.asList(parentPattern))
            .build();
    patternsToResourceNames.put(parentPattern, parentResourceName);

    return Optional.of(parentResourceName);
  }

  @VisibleForTesting
  static Optional<String> parseParentPattern(String pattern) {
    String[] tokens = pattern.split(SLASH);
    String lastToken = tokens[tokens.length - 1];
    if (lastToken.equals(ResourceNameConstants.DELETED_TOPIC_LITERAL)
        || lastToken.equals(ResourceNameConstants.WILDCARD_PATTERN)) {
      return Optional.empty();
    }

    // No fully-formed parent. Expected: ancestors/{ancestor}/childNodes/{child_node}.
    if (tokens.length < 4) {
      return Optional.empty();
    }

    Preconditions.checkState(
        lastToken.contains("{"),
        String.format(
            "Pattern %s must end with a brace-encapsulated variable, e.g. {foobar}", pattern));

    return Optional.of(String.join(SLASH, Arrays.asList(tokens).subList(0, tokens.length - 2)));
  }

  @VisibleForTesting
  static String resolvePackages(String resourceNamePackage, String servicePackage) {
    return resourceNamePackage.contains(servicePackage) ? resourceNamePackage : servicePackage;
  }
}
