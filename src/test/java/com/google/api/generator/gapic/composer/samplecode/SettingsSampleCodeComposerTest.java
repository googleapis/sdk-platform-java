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
import com.google.api.generator.testutils.LineFormatter;
import java.util.Optional;
import org.junit.Test;

public class SettingsSampleCodeComposerTest {
  @Test
  public void composeSettingsSampleCode_noMethods() {
    TypeNode classType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoSettings")
                .setPakkage("com.google.showcase.v1beta1")
                .build());
    Optional<String> results =
        ExecutableSampleComposer.createExecutableSample(
            SettingsSampleCodeComposer.composeSampleCode(
                Optional.empty(), "EchoSettings", classType));
    assertEquals(results, Optional.empty());
  }

  @Test
  public void composeSettingsSampleCode_serviceSettingsClass() {
    TypeNode classType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoSettings")
                .setPakkage("com.google.showcase.v1beta1")
                .build());
    Optional<String> results =
        ExecutableSampleComposer.createExecutableSample(
            SettingsSampleCodeComposer.composeSampleCode(
                Optional.of("Echo"), "EchoSettings", classType));
    String expected =
        LineFormatter.lines(
            "package com.google.example;\n",
            "\n",
            "import com.google.showcase.v1beta1.EchoSettings;\n",
            "import java.time.Duration;\n",
            "\n",
            "public class EchoSettings {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    echoSettings();\n",
            "  }\n",
            "\n",
            "  public static void echoSettings() throws Exception {\n",
            "    EchoSettings.Builder echoSettingsBuilder = EchoSettings.newBuilder();\n",
            "    echoSettingsBuilder\n",
            "        .echoSettings()\n",
            "        .setRetrySettings(\n",
            "            echoSettingsBuilder\n",
            "                .echoSettings()\n",
            "                .getRetrySettings()\n",
            "                .toBuilder()\n",
            "                .setTotalTimeout(Duration.ofSeconds(30))\n",
            "                .build());\n",
            "    EchoSettings echoSettings = echoSettingsBuilder.build();\n",
            "  }\n",
            "}\n");
    assertEquals(expected, results.get());
  }

  @Test
  public void composeSettingsSampleCode_serviceStubClass() {
    TypeNode classType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoStubSettings")
                .setPakkage("com.google.showcase.v1beta1")
                .build());
    Optional<String> results =
        ExecutableSampleComposer.createExecutableSample(
            SettingsSampleCodeComposer.composeSampleCode(
                Optional.of("Echo"), "EchoSettings", classType));
    String expected =
        LineFormatter.lines(
            "package com.google.example;\n",
            "\n",
            "import com.google.showcase.v1beta1.EchoStubSettings;\n",
            "import java.time.Duration;\n",
            "\n",
            "public class EchoSettings {\n",
            "\n",
            "  public static void main(String[] args) throws Exception {\n",
            "    echoSettings();\n",
            "  }\n",
            "\n",
            "  public static void echoSettings() throws Exception {\n",
            "    EchoStubSettings.Builder echoSettingsBuilder = EchoStubSettings.newBuilder();\n",
            "    echoSettingsBuilder\n",
            "        .echoSettings()\n",
            "        .setRetrySettings(\n",
            "            echoSettingsBuilder\n",
            "                .echoSettings()\n",
            "                .getRetrySettings()\n",
            "                .toBuilder()\n",
            "                .setTotalTimeout(Duration.ofSeconds(30))\n",
            "                .build());\n",
            "    EchoStubSettings echoSettings = echoSettingsBuilder.build();\n",
            "  }\n",
            "}\n");
    assertEquals(expected, results.get());
  }
}
