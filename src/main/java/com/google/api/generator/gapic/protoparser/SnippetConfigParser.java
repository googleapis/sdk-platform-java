package com.google.api.generator.gapic.protoparser;

import com.google.api.generator.gapic.model.GapicSnippetConfig;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfig;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.protobuf.util.JsonFormat;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class SnippetConfigParser {
  public static Optional<GapicSnippetConfig> parse(String snippetConfigFilePath) {
    Optional<SnippetConfig> rawConfig = parseFile(snippetConfigFilePath);
    return Optional.of(GapicSnippetConfig.create(rawConfig));
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
    } catch (FileNotFoundException e) {
      return Optional.empty();
    }
    try {
      JsonFormat.parser().merge(file, builder);
    } catch (IOException e) {
      return Optional.empty();
    }
    return Optional.of(builder.build());
  }
}
