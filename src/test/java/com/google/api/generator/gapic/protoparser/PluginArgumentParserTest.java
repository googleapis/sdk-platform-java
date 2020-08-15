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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import java.util.Arrays;
import org.junit.Test;

public class PluginArgumentParserTest {
  @Test
  public void parseJsonPath_onlyOnePresent() {
    String jsonPath = "/tmp/grpc_service_config.json";
    assertEquals(jsonPath, PluginArgumentParser.parseJsonConfigPath(jsonPath).get());
  }

  @Test
  public void parseJsonPath_similarFileAppearsFirst() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "/tmp/something_gapic.yaml";
    String rawArgument =
        String.join(
            ",",
            Arrays.asList(
                gapicPath,
                "/tmp/something.json",
                "/tmp/some_grpc_service_configjson",
                jsonPath,
                gapicPath));
    assertEquals(jsonPath, PluginArgumentParser.parseJsonConfigPath(rawArgument).get());
  }

  @Test
  public void parseJsonPath_argumentHasSpaces() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "/tmp/something_gapic.yaml";
    String rawArgument =
        String.join(
            " , ",
            Arrays.asList(
                gapicPath,
                "/tmp/something.json",
                "/tmp/some_grpc_service_configjson",
                jsonPath,
                gapicPath));
    assertEquals(jsonPath, PluginArgumentParser.parseJsonConfigPath(rawArgument).get());
  }

  @Test
  public void parseJsonPath_restAreEmpty() {
    String jsonPath = "/tmp/foobar_grpc_service_config.json";
    String gapicPath = "";
    String rawArgument = String.join(",", Arrays.asList(gapicPath, jsonPath, gapicPath));
    assertEquals(jsonPath, PluginArgumentParser.parseJsonConfigPath(rawArgument).get());
  }

  @Test
  public void parseJsonPath_noneFound() {
    String gapicPath = "/tmp/something_gapic.yaml";
    String rawArgument = String.join(",", Arrays.asList(gapicPath));
    assertFalse(PluginArgumentParser.parseJsonConfigPath(rawArgument).isPresent());
  }

  @Test
  public void parseGapicYamlPath_onlyOnePresent() {
    String gapicPath = "/tmp/something_gapic.yaml";
    assertEquals(gapicPath, PluginArgumentParser.parseGapicYamlConfigPath(gapicPath).get());
  }

  @Test
  public void parseGapicYamlPath_similarFileAppearsFirst() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "/tmp/something_gapic.yaml";
    String rawArgument =
        String.join(
            ",", Arrays.asList(jsonPath, "/tmp/something.yaml", "/tmp/some_gapicyaml", gapicPath));
    assertEquals(gapicPath, PluginArgumentParser.parseGapicYamlConfigPath(rawArgument).get());
  }

  @Test
  public void parseGapicYamlPath_restAreEmpty() {
    String jsonPath = "";
    String gapicPath = "/tmp/something_gapic.yaml";
    String rawArgument = String.join(",", Arrays.asList(jsonPath, gapicPath, jsonPath));
    assertEquals(gapicPath, PluginArgumentParser.parseGapicYamlConfigPath(rawArgument).get());
  }

  @Test
  public void parseGapicYamlPath_noneFound() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "";
    String rawArgument = String.join(",", Arrays.asList(jsonPath, gapicPath));
    assertFalse(PluginArgumentParser.parseGapicYamlConfigPath(rawArgument).isPresent());
  }
}
