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

package com.google.api.generator.gapic.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.api.generator.gapic.protoparser.SnippetConfigParser;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.junit.Test;

public class GapicSnippetConfigTest {
  private static final String TESTDATA_DIRECTORY = "src/test/resources/";

  // TODO Add more tests
  @Test
  public void snippetConfig_metadataOnly() {
    String jsonFilename = "configured_snippet_config.json";
    Path jsonPath = Paths.get(TESTDATA_DIRECTORY, jsonFilename);
    Optional<GapicSnippetConfig> snippetConfigOpt = SnippetConfigParser.parse(jsonPath.toString());
    assertTrue(snippetConfigOpt.isPresent());
    GapicSnippetConfig snippetConfig = snippetConfigOpt.get();

    assertEquals(
        "Custom Class Creation", GapicSnippetConfig.getConfiguredSnippetSnippetName(snippetConfig));
    assertEquals(
        "Shows how to create a custom class",
        GapicSnippetConfig.getConfiguredSnippetSnippetDescription(snippetConfig));
  }
}
