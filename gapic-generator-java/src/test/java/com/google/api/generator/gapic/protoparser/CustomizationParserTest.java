// Copyright 2023 Google LLC
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

import static com.google.api.generator.gapic.protoparser.CustomizationParser.parseAddBaseResourceNameCustomization;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertTrue;

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
    assertThat(messages)
        .containsAnyIn(
            new String[] {
              "com.google.cloud.monitoring.v3.ListAlertPoliciesRequest",
              "com.google.cloud.monitoring.v3.AnotherRequest"
            });
  }

  @Test
  public void parseNonExistedCustomizationTest() {
    List<String> messages = parseAddBaseResourceNameCustomization("nonExisted.yaml");
    assertTrue(messages.isEmpty());
  }
}
