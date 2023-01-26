// Copyright 2022 Google LLC
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

import com.google.api.generator.gapic.model.GapicSnippetConfig;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfig;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.protobuf.util.JsonFormat;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class SnippetConfigParser {
  public static Optional<GapicSnippetConfig> parse(String snippetConfigFilePath) {
    Optional<SnippetConfig> rawConfig = parseFile(snippetConfigFilePath);
    return rawConfig.map(GapicSnippetConfig ::create);
  }

  @VisibleForTesting
  public static Optional<SnippetConfig> parseFile(String snippetConfigFilePath) {
    if (Strings.isNullOrEmpty(snippetConfigFilePath)) {
      return Optional.empty();
    }

    SnippetConfig.Builder builder = SnippetConfig.newBuilder();
    FileReader file;
    try {
      file = new FileReader(snippetConfigFilePath);
      JsonFormat.parser().merge(file, builder);
    } catch (IOException e) {
      // TODO @alicejli: confirm whether a malformed snippet config should break client library generation or not. Currently, it does not.
      return Optional.empty();
    }
    return Optional.of(builder.build());
  }
}
