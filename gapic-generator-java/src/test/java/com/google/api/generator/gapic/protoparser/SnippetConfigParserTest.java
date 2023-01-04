// Copyright 2022 Google LLC
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

import com.google.cloud.tools.snippetgen.configlanguage.v1.Rpc;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfig;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfigMetadata;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetSignature;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;

public class SnippetConfigParserTest {
  private static final String JSON_DIRECTORY = "src/test/resources/";

  // TODO: Update tests
  @Test
  public void parseSnippetConfig() {
    String jsonFilename = "configured_snippet_config.json";
    Path jsonPath = Paths.get(JSON_DIRECTORY, jsonFilename);
    Optional<SnippetConfig> configOpt = SnippetConfigParser.parseFile(jsonPath.toString());
    assertTrue(configOpt.isPresent());

    SnippetConfig config = configOpt.get();

    SnippetConfigMetadata metadata = config.getMetadata();
    assertEquals("Basic", metadata.getConfigId());
    assertEquals("Custom Class Creation", metadata.getSnippetName());
    assertEquals("Shows how to create a custom class", metadata.getSnippetDescription());

    Rpc rpc = config.getRpc();
    assertEquals("google.cloud.speech", rpc.getProtoPackage());
    assertEquals(Arrays.asList("v1"), rpc.getApiVersionList());
    assertEquals("Adaptation", rpc.getServiceName());
    assertEquals("CreateCustomClass", rpc.getRpcName());

    SnippetSignature signature = config.getSignature();
    assertEquals("create_custom_class", signature.getSnippetMethodName());
    assertEquals(
        "google.cloud.speech.v1.CustomClass",
        signature.getReturnType().getMessageType().getMessageFullName());
    assertEquals(SnippetSignature.SyncPreference.PREFER_ASYNC, signature.getSyncPreference());
  }
}
