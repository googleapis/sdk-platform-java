package com.google.api.generator.gapic.composer.samplecode;

import static junit.framework.Assert.assertEquals;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.testutils.LineFormatter;
import java.util.Optional;
import org.junit.Test;

public class SettingsSampleCodeComposerTest {
  @Test
  public void composeSettingsSampleCode_noMethods() {
    TypeNode classType = TypeNode.withReference(VaporReference.builder()
    .setName("EchoSettings")
    .setPakkage("com.google.showcase.v1beta1")
    .build());
    Optional<String> results =  SettingsSampleCodeComposer.composeSampleCode(Optional.empty(), "EchoSettings", classType);
    assertEquals(results, Optional.empty());
  }

  @Test
  public void composeSettingsSampleCode_serviceSettingsClass() {
    TypeNode classType = TypeNode.withReference(VaporReference.builder()
        .setName("EchoSettings")
        .setPakkage("com.google.showcase.v1beta1")
        .build());
    Optional<String> results = SettingsSampleCodeComposer.composeSampleCode(Optional.of("Echo"), "EchoSettings", classType);
    String expected = LineFormatter.lines(
        "EchoSettings.Builder echoSettingsBuilder = EchoSettings.newBuilder();\n",
        "echoSettingsBuilder\n",
        "    .echoSettings()\n",
        "    .setRetrySettings(\n",
        "        echoSettingsBuilder\n",
        "            .echoSettings()\n",
        "            .getRetrySettings()\n",
        "            .toBuilder()\n",
        "            .setTotalTimeout(Duration.ofSeconds(30))\n",
        "            .build());\n",
        "EchoSettings echoSettings = echoSettingsBuilder.build();");
    assertEquals(results.get(), expected);
  }

  @Test
  public void composeSettingsSampleCode_serviceStubClass() {
    TypeNode classType = TypeNode.withReference(VaporReference.builder()
        .setName("EchoStubSettings")
        .setPakkage("com.google.showcase.v1beta1")
        .build());
    Optional<String> results = SettingsSampleCodeComposer.composeSampleCode(Optional.of("Echo"), "EchoSettings", classType);
    String expected = LineFormatter.lines(
        "EchoStubSettings.Builder echoSettingsBuilder = EchoStubSettings.newBuilder();\n",
        "echoSettingsBuilder\n",
        "    .echoSettings()\n",
        "    .setRetrySettings(\n",
        "        echoSettingsBuilder\n",
        "            .echoSettings()\n",
        "            .getRetrySettings()\n",
        "            .toBuilder()\n",
        "            .setTotalTimeout(Duration.ofSeconds(30))\n",
        "            .build());\n",
        "EchoStubSettings echoSettings = echoSettingsBuilder.build();");
    assertEquals(results.get(), expected);
  }

}
