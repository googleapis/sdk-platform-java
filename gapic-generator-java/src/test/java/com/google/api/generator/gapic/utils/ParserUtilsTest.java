package com.google.api.generator.gapic.utils;

import static com.google.api.generator.gapic.utils.ParserUtils.parseMapFromYamlFile;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;

public class ParserUtilsTest {

  private static final String YAML_DIRECTORY = "src/test/resources/";

  @Test
  public void parseMapFromYamlFileTest() {
    String yamlFilename = "customization_test.yaml";
    Path yamlPath = Paths.get(YAML_DIRECTORY, yamlFilename);
    Optional<Map<String, Object>> yamlMapOpt = parseMapFromYamlFile(yamlPath.toString());
    assertTrue(yamlMapOpt.isPresent());
  }
}
