package com.google.api.generator.gapic.model;

import com.google.api.generator.gapic.protoparser.SnippetConfigParser;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfigMetadata;
import com.google.protobuf.Descriptors;
import com.google.protobuf.util.Durations;
import com.google.rpc.Code;
import com.google.showcase.v1beta1.EchoOuterClass;
import com.google.api.generator.gapic.model.GapicSnippetConfig;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GapicSnippetConfigTest {
    private static final String TESTDATA_DIRECTORY = "src/test/resources/";

    @Test
    public void snippetConfig_metadataOnly() {
        String jsonFilename = "configured_snippet_metadata_only_config.json";
        Path jsonPath = Paths.get(TESTDATA_DIRECTORY, jsonFilename);
        Optional<GapicSnippetConfig> snippetConfigOpt = SnippetConfigParser.parse(jsonPath.toString());
        assertTrue(snippetConfigOpt.isPresent());
        GapicSnippetConfig snippetConfig = snippetConfigOpt.get();

        assertEquals("Custom Class Creation", GapicSnippetConfig.getConfiguredSnippetSnippetName(snippetConfig));
        assertEquals("Shows how to create a custom class", GapicSnippetConfig.getConfiguredSnippetSnippetDescription(snippetConfig));

    }
}
