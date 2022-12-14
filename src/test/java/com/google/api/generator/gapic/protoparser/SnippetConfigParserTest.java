package com.google.api.generator.gapic.protoparser;

import com.google.cloud.tools.snippetgen.configlanguage.v1.Rpc;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfig;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfigLanguageProto;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfigMetadata;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetSignature;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SnippetConfigParserTest {
    private static final String JSON_DIRECTORY = "src/test/resources/";

    @Test
    public void parseSnippetConfig_metadata_only() {
        String jsonFilename = "configured_snippet_metadata_only_config.json";
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
        assertEquals("google.cloud.speech.v1.CustomClass", signature.getReturnType().getMessageType().getMessageFullName());
        assertEquals(SnippetSignature.SyncPreference.PREFER_ASYNC, signature.getSyncPreference());
    }

    @Test
    public void parseSnippetConfig_full_path() {
        String jsonFilename = "/home/alicejli/gapic-generator-java/test/integration/apis/speech/v1/speech_createCustomClass.json";
//        Path jsonPath = Paths.get(JSON_DIRECTORY, jsonFilename);
        Optional<SnippetConfig> configOpt = SnippetConfigParser.parseFile(jsonFilename);
        assertTrue(configOpt.isPresent());

        SnippetConfig config = configOpt.get();

        SnippetConfigMetadata metadata = config.getMetadata();
        assertEquals("Basic", metadata.getConfigId());
        assertEquals("Custom Class Creation", metadata.getSnippetName());
        assertEquals("Shows how to create a custom class", metadata.getSnippetDescription());
    }
}
