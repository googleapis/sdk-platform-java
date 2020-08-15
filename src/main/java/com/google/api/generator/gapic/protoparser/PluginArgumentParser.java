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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import java.util.Optional;

// Parses the arguments from the protoc plugin.
public class PluginArgumentParser {
  private static final String COMMA = ",";
  private static final String JSON_FILE_ENDING = "grpc_service_config.json";
  private static final String GAPIC_YAML_FILE_ENDING = "gapic.yaml";

  public static Optional<String> parseJsonConfigPath(CodeGeneratorRequest request) {
    return parseJsonConfigPath(request.getParameter());
  }

  public static Optional<String> parseGapicYamlConfigPath(CodeGeneratorRequest request) {
    return parseGapicYamlConfigPath(request.getParameter());
  }

  /** Expects a comma-separated list of file paths. */
  @VisibleForTesting
  static Optional<String> parseJsonConfigPath(String pluginProtocArgument) {
    return parseArgument(pluginProtocArgument, JSON_FILE_ENDING);
  }

  @VisibleForTesting
  static Optional<String> parseGapicYamlConfigPath(String pluginProtocArgument) {
    return parseArgument(pluginProtocArgument, GAPIC_YAML_FILE_ENDING);
  }

  private static Optional<String> parseArgument(String pluginProtocArgument, String fileEnding) {
    if (Strings.isNullOrEmpty(pluginProtocArgument)) {
      return Optional.<String>empty();
    }
    for (String rawPath : pluginProtocArgument.split(COMMA)) {
      String path = rawPath.trim();
      if (path.endsWith(fileEnding)) {
        return Optional.of(path);
      }
    }
    return Optional.<String>empty();
  }
}
