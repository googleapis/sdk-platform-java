package com.google.api.generator.gapic.utils;

import com.google.common.base.Strings;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

public class ParserUtils {

  private ParserUtils() {}

  public static Optional<Map<String, Object>> parseMapFromYamlFile(String yamlFilePath) {
    if (Strings.isNullOrEmpty(yamlFilePath) || !(new File(yamlFilePath)).exists()) {
      return Optional.empty();
    }

    String fileContents;
    try {
      fileContents =
          new String(Files.readAllBytes(Paths.get(yamlFilePath)), StandardCharsets.UTF_8);
    } catch (IOException e) {
      return Optional.empty();
    }

    Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
    return Optional.of(yaml.load(fileContents));
  }
}
