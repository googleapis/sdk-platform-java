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

import com.google.api.ResourceDescriptor;
import com.google.api.ResourceProto;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.ResourceNameConstants;
import com.google.api.pathtemplate.PathTemplate;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.protobuf.DescriptorProtos.FileOptions;
import com.google.protobuf.DescriptorProtos.MessageOptions;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ResourceNameParser {
  /** Returns a map of resource types (strings) to ResourceName POJOs. */
  public static Map<String, ResourceName> parseResourceNames(FileDescriptor fileDescriptor) {
    Map<String, ResourceName> resourceNames = parseResourceNamesFromFile(fileDescriptor);
    String pakkage = TypeParser.getPackage(fileDescriptor);
    resourceNames.putAll(parseResourceNamesFromMessages(fileDescriptor.getMessageTypes(), pakkage));
    return resourceNames;
  }

  @VisibleForTesting
  static Map<String, ResourceName> parseResourceNamesFromFile(FileDescriptor fileDescriptor) {
    Map<String, ResourceName> typeStringToResourceNames = new HashMap<>();
    FileOptions fileOptions = fileDescriptor.getOptions();
    if (fileOptions.getExtensionCount(ResourceProto.resourceDefinition) <= 0) {
      return typeStringToResourceNames;
    }
    List<ResourceDescriptor> protoResources =
        fileOptions.getExtension(ResourceProto.resourceDefinition);
    String pakkage = TypeParser.getPackage(fileDescriptor);
    for (ResourceDescriptor protoResource : protoResources) {
      Optional<ResourceName> resourceNameModelOpt = createResourceName(protoResource, pakkage);
      if (!resourceNameModelOpt.isPresent()) {
        continue;
      }
      ResourceName resourceNameModel = resourceNameModelOpt.get();
      // Clobber anything if we're creating a new ResourceName from a proto.
      typeStringToResourceNames.put(resourceNameModel.resourceTypeString(), resourceNameModel);
    }
    return typeStringToResourceNames;
  }

  @VisibleForTesting
  static Map<String, ResourceName> parseResourceNamesFromMessages(
      List<Descriptor> messageTypeDescriptors, String pakkage) {
    Map<String, ResourceName> resourceNames = new HashMap<>();
    for (Descriptor messageTypeDescriptor : messageTypeDescriptors) {
      Optional<ResourceName> resourceNameModelOpt =
          parseResourceNameFromMessageType(messageTypeDescriptor, pakkage);
      if (resourceNameModelOpt.isPresent()) {
        ResourceName resourceName = resourceNameModelOpt.get();
        resourceNames.put(resourceName.resourceTypeString(), resourceName);
      }
    }
    return resourceNames;
  }

  @VisibleForTesting
  static Optional<ResourceName> parseResourceNameFromMessageType(
      Descriptor messageTypeDescriptor, String pakkage) {
    MessageOptions messageOptions = messageTypeDescriptor.getOptions();
    if (!messageOptions.hasExtension(ResourceProto.resource)) {
      return Optional.empty();
    }

    // aip.dev/4231.
    Preconditions.checkNotNull(
        messageTypeDescriptor.findFieldByName(ResourceNameConstants.NAME_FIELD_NAME),
        String.format(
            "Message %s has a resource annotation but no \"name\" field",
            messageTypeDescriptor.getName()));

    return createResourceName(messageOptions.getExtension(ResourceProto.resource), pakkage);
  }

  private static Optional<ResourceName> createResourceName(
      ResourceDescriptor protoResource, String pakkage) {
    // We may need to modify this list.
    List<String> patterns = new ArrayList<>(protoResource.getPatternList());
    Preconditions.checkState(
        !patterns.isEmpty(),
        String.format(
            "Resource name definition for %s must have at least one pattern",
            protoResource.getType()));

    if (patterns.size() == 1 && patterns.get(0).equals(ResourceNameConstants.WILDCARD_PATTERN)) {
      return Optional.empty();
    }

    // Assuming that both patterns end with the same variable name.
    Optional<String> resourceVariableNameOpt = Optional.empty();
    for (int i = 0; i < patterns.size(); i++) {
      resourceVariableNameOpt = getVariableNameFromPattern(patterns.get(i));
      if (resourceVariableNameOpt.isPresent()) {
        break;
      }
    }
    Preconditions.checkState(
        resourceVariableNameOpt.isPresent(),
        String.format("Resource variable name not found in patterns %s", patterns));

    if (patterns.contains(ResourceNameConstants.WILDCARD_PATTERN)) {
      patterns.remove(ResourceNameConstants.WILDCARD_PATTERN);
    }

    return Optional.of(
        ResourceName.builder()
            .setVariableName(resourceVariableNameOpt.get())
            .setPakkage(pakkage)
            .setResourceTypeString(protoResource.getType())
            .setPatterns(patterns)
            .build());
  }

  @VisibleForTesting
  static Optional<String> getVariableNameFromPattern(String pattern) {
    // Expected to be small (e.g. less than 10) most of the time.
    String resourceVariableName = null;
    String[] tokens = pattern.split("/");
    String lastToken = tokens[tokens.length - 1];
    if (lastToken.equals(ResourceNameConstants.DELETED_TOPIC_LITERAL)) {
      resourceVariableName = lastToken;
    } else if (lastToken.equals(ResourceNameConstants.WILDCARD_PATTERN)) {
      resourceVariableName = null;
    } else {
      Preconditions.checkState(
          lastToken.contains("{"),
          String.format(
              "Pattern %s must end with a brace-encapsulated variable, e.g. {foobar}", pattern));
      Set<String> variableNames = PathTemplate.create(pattern).vars();
      for (String variableName : variableNames) {
        if (lastToken.contains(variableName)) {
          resourceVariableName = variableName;
          break;
        }
      }
    }
    return Strings.isNullOrEmpty(resourceVariableName)
        ? Optional.empty()
        : Optional.of(resourceVariableName);
  }
}
