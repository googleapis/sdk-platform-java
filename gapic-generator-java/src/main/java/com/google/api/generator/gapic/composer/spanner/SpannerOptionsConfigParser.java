package com.google.api.generator.gapic.composer.spanner;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.spanner.SpannerOptionsConfig;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

public class SpannerOptionsConfigParser {
  public static Optional<SpannerOptionsConfig.OptionMapperConfigList> parse(String configFilePath) {
    if (Strings.isNullOrEmpty(configFilePath) || !(new File(configFilePath)).exists()) {
      return Optional.empty();
    }

    String fileContents = null;
    try {
      fileContents =
          new String(Files.readAllBytes(Paths.get(configFilePath)), StandardCharsets.UTF_8);
    } catch (IOException e) {
      return Optional.empty();
    }

    Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
    Map<String, Object> yamlMap = yaml.load(fileContents);
    Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
    String jsonString = gson.toJson(yamlMap, LinkedHashMap.class);
    SpannerOptionsConfig.OptionMapperConfigList.Builder serviceBuilder = SpannerOptionsConfig.OptionMapperConfigList.newBuilder();
    try {
      JsonFormat.parser().ignoringUnknownFields().merge(jsonString, serviceBuilder);
    } catch (InvalidProtocolBufferException e) {
      return Optional.empty();
    }
    return Optional.of(serviceBuilder.build());
  }
}
