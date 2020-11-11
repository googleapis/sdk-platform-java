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
  private static final String EQUALS = "=";

  // Synced to rules_java_gapic/java_gapic.bzl.
  @VisibleForTesting static final String KEY_GRPC_SERVICE_CONFIG = "grpc-service-config";

  private static final String JSON_FILE_ENDING = "grpc_service_config.json";

  static Optional<String> parseJsonConfigPath(CodeGeneratorRequest request) {
    return parseJsonConfigPath(request.getParameter());
  }

  /** Expects a comma-separated list of file paths. */
  @VisibleForTesting
  static Optional<String> parseJsonConfigPath(String pluginProtocArgument) {
    if (Strings.isNullOrEmpty(pluginProtocArgument)) {
      return Optional.<String>empty();
    }
    for (String argComponent : pluginProtocArgument.split(COMMA)) {
      String[] args = argComponent.trim().split(EQUALS);
      if (args.length < 2) {
        continue;
      }
      String keyVal = args[0];
      String valueVal = args[1];
      if (keyVal.equals(KEY_GRPC_SERVICE_CONFIG) && valueVal.endsWith(JSON_FILE_ENDING)) {
        return Optional.of(valueVal);
      }
    }
    return Optional.<String>empty();
  }
}
