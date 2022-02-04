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

import java.nio.file.Path;
import java.nio.file.Paths;
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
}
