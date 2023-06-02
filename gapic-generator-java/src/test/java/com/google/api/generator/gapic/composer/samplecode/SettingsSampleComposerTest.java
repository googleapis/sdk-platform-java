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

package com.google.api.generator.gapic.composer.samplecode;

import static org.junit.Assert.assertEquals;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.test.utils.LineFormatter;
import java.util.Optional;
import org.junit.Test;

public class SettingsSampleComposerTest {
  @Test
  public void composeSettingsSample_noMethods() {
    TypeNode classType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoSettings")
                .setPakkage("com.google.showcase.v1beta1")
                .build());
    Optional<String> results =
        writeSample(
            SettingsSampleComposer.composeSettingsSample(
                Optional.empty(), "EchoSettings", classType));
    assertEquals(results, Optional.empty());
  }

  @Test
  public void composeSettingsSample_serviceSettingsClass() {
    TypeNode classType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoSettings")
                .setPakkage("com.google.showcase.v1beta1")
                .build());
    Optional<String> results =
        writeSample(
            SettingsSampleComposer.composeSettingsSample(
                Optional.of("Echo"), "EchoSettings", classType));
    String expected =
        LineFormatter.lines(
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
  public void composeSettingsSample_serviceStubClass() {
    TypeNode classType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoStubSettings")
                .setPakkage("com.google.showcase.v1beta1")
                .build());
    Optional<String> results =
        writeSample(
            SettingsSampleComposer.composeSettingsSample(
                Optional.of("Echo"), "EchoSettings", classType));
    String expected =
        LineFormatter.lines(
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

  private Optional<String> writeSample(Optional<Sample> sample) {
    if (sample.isPresent()) {
      return Optional.of(SampleCodeWriter.write(sample.get().body()));
    }
    return Optional.empty();
  }
}
