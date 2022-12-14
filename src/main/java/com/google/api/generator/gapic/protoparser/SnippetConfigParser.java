package com.google.api.generator.gapic.protoparser;
import com.google.api.generator.gapic.model.GapicSnippetConfig;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfig;
import com.google.protobuf.util.JsonFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
