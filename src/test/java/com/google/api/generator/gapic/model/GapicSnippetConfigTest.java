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
