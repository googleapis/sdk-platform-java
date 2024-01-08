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
import static org.junit.Assert.assertTrue;

import com.google.api.MethodSettings;
import com.google.api.Publishing;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class ServiceYamlParserTest {

  private static final String YAML_DIRECTORY = "src/test/resources/";

  @Test
  public void parseServiceYaml_basic() {
    String yamlFilename = "logging.yaml";
    Path yamlPath = Paths.get(YAML_DIRECTORY, yamlFilename);
    Optional<com.google.api.Service> serviceYamlProtoOpt =
        ServiceYamlParser.parse(yamlPath.toString());
    assertTrue(serviceYamlProtoOpt.isPresent());

    com.google.api.Service serviceYamlProto = serviceYamlProtoOpt.get();
    assertEquals("logging.googleapis.com", serviceYamlProto.getName());
  }

  // TODO: Add more scenarios (e.g. null MethodSettings, null PublishingSettings, incorrect
  // FieldNames, etc.)
  @Test
  public void parseServiceYaml_autoPopulatedFields() {
    String yamlFilename = "echo_v1beta1.yaml";
    Path yamlPath = Paths.get(YAML_DIRECTORY, yamlFilename);
    Optional<com.google.api.Service> serviceYamlProtoOpt =
        ServiceYamlParser.parse(yamlPath.toString());
    assertTrue(serviceYamlProtoOpt.isPresent());

    com.google.api.Service serviceYamlProto = serviceYamlProtoOpt.get();
    assertEquals("showcase.googleapis.com", serviceYamlProto.getName());

    Publishing publishingSettings = serviceYamlProto.getPublishing();
    List<MethodSettings> methodSettings = publishingSettings.getMethodSettingsList();
    MethodSettings methodSetting = methodSettings.get(0);
    assertEquals("google.showcase.v1beta1.Echo.Echo", methodSetting.getSelector());
    assertEquals("request_id", methodSetting.getAutoPopulatedFieldsList().get(0));
  }
}
