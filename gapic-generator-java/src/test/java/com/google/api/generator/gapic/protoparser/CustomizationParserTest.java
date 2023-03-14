package com.google.api.generator.gapic.protoparser;

import static com.google.api.generator.gapic.protoparser.CustomizationParser.parseAddBaseResourceNameCustomization;
import static com.google.common.truth.Truth.assertThat;

import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;

public class CustomizationParserTest {

  private static final String YAML_PATH =
      Paths.get("src/test/resources/", "customization_test.yaml").toString();

  @Test
  public void parseAddBaseResourceNameCustomizationTest() {
    List<String> messages = parseAddBaseResourceNameCustomization(YAML_PATH);
    assertThat(messages.size()).isEqualTo(2);
    assertThat(messages).containsAnyIn(new String[] {
        "com.google.cloud.monitoring.v3.ListAlertPoliciesRequest",
        "com.google.cloud.monitoring.v3.AnotherRequest"});
  }
}
