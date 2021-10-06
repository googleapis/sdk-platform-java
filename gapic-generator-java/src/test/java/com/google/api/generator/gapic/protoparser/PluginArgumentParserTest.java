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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Test;

public class PluginArgumentParserTest {
  @Test
  public void parseJsonPath_onlyOnePresent() {
    String jsonPath = "/tmp/grpc_service_config.json";
    assertEquals(
        jsonPath,
        PluginArgumentParser.parseJsonConfigPath(createGrpcServiceConfig(jsonPath)).get());
  }

  @Test
  public void parseJsonPath_returnsFirstOneFound() {
    String jsonPathOne = "/tmp/foobar_grpc_service_config.json";
    String jsonPathTwo = "/tmp/some_other_grpc_service_config.json";
    assertEquals(
        jsonPathOne,
        PluginArgumentParser.parseJsonConfigPath(
                String.join(
                    ",",
                    Arrays.asList(
                        createGrpcServiceConfig(jsonPathOne),
                        createGrpcServiceConfig(jsonPathTwo))))
            .get());
  }

  @Test
  public void parseJsonPath_similarFileAppearsFirst() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "/tmp/something_gapic.yaml";
    String rawArgument =
        String.join(
            ",",
            Arrays.asList(
                createGapicConfig(gapicPath),
                createGrpcServiceConfig("/tmp/something.json"),
                createGrpcServiceConfig("/tmp/some_grpc_service_configjson"),
                createGrpcServiceConfig(jsonPath),
                createGapicConfig(gapicPath)));
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
                createGapicConfig(gapicPath),
                createGrpcServiceConfig("/tmp/something.json"),
                createGrpcServiceConfig("/tmp/some_grpc_service_configjson"),
                createGrpcServiceConfig(jsonPath),
                createGapicConfig(gapicPath)));
    assertEquals(jsonPath, PluginArgumentParser.parseJsonConfigPath(rawArgument).get());
  }

  @Test
  public void parseJsonPath_restAreEmpty() {
    String jsonPath = "/tmp/foobar_grpc_service_config.json";
    String gapicPath = "";
    String rawArgument =
        String.join(",", Arrays.asList(gapicPath, createGrpcServiceConfig(jsonPath), gapicPath));
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
    assertEquals(
        gapicPath,
        PluginArgumentParser.parseGapicYamlConfigPath(createGapicConfig(gapicPath)).get());
  }

  @Test
  public void parseGapicYamlPath_returnsFirstOneFound() {
    String gapicPathOne = "/tmp/something_gapic.yaml";
    String gapicPathTwo = "/tmp/other_gapic.yaml";
    assertEquals(
        gapicPathOne,
        PluginArgumentParser.parseGapicYamlConfigPath(
                String.join(
                    ",",
                    Arrays.asList(
                        createGapicConfig(gapicPathOne), createGapicConfig(gapicPathTwo))))
            .get());
  }

  @Test
  public void parseGapicYamlPath_similarFileAppearsFirst() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "/tmp/something_gapic.yaml";
    String rawArgument =
        String.join(
            ",",
            Arrays.asList(
                createGrpcServiceConfig(jsonPath),
                createGapicConfig("/tmp/something.yaml"),
                createGapicConfig("/tmp/some_gapicyaml"),
                createGapicConfig(gapicPath)));
    assertEquals(gapicPath, PluginArgumentParser.parseGapicYamlConfigPath(rawArgument).get());
  }

  @Test
  public void parseGapicYamlPath_restAreEmpty() {
    String jsonPath = "";
    String gapicPath = "/tmp/something_gapic.yaml";
    String rawArgument =
        String.join(",", Arrays.asList(jsonPath, createGapicConfig(gapicPath), jsonPath));
    assertEquals(gapicPath, PluginArgumentParser.parseGapicYamlConfigPath(rawArgument).get());
  }

  @Test
  public void parseGapicYamlPath_noneFound() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "";
    String rawArgument =
        String.join(",", Arrays.asList(createGrpcServiceConfig(jsonPath), gapicPath));
    assertFalse(PluginArgumentParser.parseGapicYamlConfigPath(rawArgument).isPresent());
  }

  @Test
  public void parseServiceYamlPath_onlyOnePresent() {
    String servicePath = "/tmp/something.yaml";
    assertEquals(
        servicePath,
        PluginArgumentParser.parseServiceYamlConfigPath(createServiceConfig(servicePath)).get());
  }

  @Test
  public void parseServiceYamlPath_returnsFirstOneFound() {
    String servicePathOne = "/tmp/something.yaml";
    String servicePathTwo = "/tmp/other.yaml";
    assertEquals(
        servicePathOne,
        PluginArgumentParser.parseServiceYamlConfigPath(
                String.join(
                    ",",
                    Arrays.asList(
                        createServiceConfig(servicePathOne), createServiceConfig(servicePathTwo))))
            .get());
  }

  @Test
  public void parseServiceYamlPath_gapicFilePresent() {
    String gapicPath = "/tmp/something_gapic.yaml";
    String servicePath = "/tmp/something.yaml";
    // Both passed under the service yaml flag.
    String rawArgument =
        String.join(
            ",", Arrays.asList(createServiceConfig(gapicPath), createServiceConfig(servicePath)));
    assertEquals(servicePath, PluginArgumentParser.parseServiceYamlConfigPath(rawArgument).get());

    // Passed under the right flags.
    rawArgument =
        String.join(
            ",", Arrays.asList(createGapicConfig(gapicPath), createServiceConfig(servicePath)));
    assertEquals(servicePath, PluginArgumentParser.parseServiceYamlConfigPath(rawArgument).get());

    // Swapped flags.
    rawArgument =
        String.join(
            ",", Arrays.asList(createGapicConfig(servicePath), createServiceConfig(gapicPath)));
    assertFalse(PluginArgumentParser.parseServiceYamlConfigPath(rawArgument).isPresent());

    // Both passed under the Gapic yaml flag.
    rawArgument =
        String.join(
            ",", Arrays.asList(createGapicConfig(gapicPath), createGapicConfig(servicePath)));
    assertFalse(PluginArgumentParser.parseServiceYamlConfigPath(rawArgument).isPresent());
  }

  @Test
  public void parseServiceYamlPath_similarFileAppearsFirst() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "/tmp/something_gapic.yaml";
    String servicePath = "/tmp/something.yaml";
    String rawArgument =
        String.join(
            ",",
            Arrays.asList(
                createGrpcServiceConfig(jsonPath),
                createGapicConfig("/tmp/something.yaml"),
                createGapicConfig("/tmp/some_gapicyaml"),
                createServiceConfig(gapicPath),
                createServiceConfig(servicePath)));
    assertEquals(servicePath, PluginArgumentParser.parseServiceYamlConfigPath(rawArgument).get());
  }

  @Test
  public void parseServiceYamlPath_noneFound() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "";
    String rawArgument =
        String.join(",", Arrays.asList(createGrpcServiceConfig(jsonPath), gapicPath));
    assertFalse(PluginArgumentParser.parseServiceYamlConfigPath(rawArgument).isPresent());
  }

  @Test
  public void parseMetadataFlag_noneFound() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "";
    String rawArgument =
        String.join(",", Arrays.asList(createGrpcServiceConfig(jsonPath), gapicPath));
    assertFalse(PluginArgumentParser.hasMetadataFlag(rawArgument));

    // Wrong casing.
    rawArgument =
        String.join(",", Arrays.asList("Metadata", createGrpcServiceConfig(jsonPath), gapicPath));
    assertFalse(PluginArgumentParser.hasMetadataFlag(rawArgument));
  }

  @Test
  public void parseMetadataFlag_flagFound() {
    String jsonPath = "/tmp/foo_grpc_service_config.json";
    String gapicPath = "";
    String rawArgument =
        String.join(",", Arrays.asList("metadata", createGrpcServiceConfig(jsonPath), gapicPath));
    assertTrue(PluginArgumentParser.hasMetadataFlag(rawArgument));
  }

  private static String createGrpcServiceConfig(String path) {
    return String.format("%s=%s", PluginArgumentParser.KEY_GRPC_SERVICE_CONFIG, path);
  }

  private static String createGapicConfig(String path) {
    return String.format("%s=%s", PluginArgumentParser.KEY_GAPIC_CONFIG, path);
  }

  private static String createServiceConfig(String path) {
    return String.format("%s=%s", PluginArgumentParser.KEY_SERVICE_YAML_CONFIG, path);
  }
}
