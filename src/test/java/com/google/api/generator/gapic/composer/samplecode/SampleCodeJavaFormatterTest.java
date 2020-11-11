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

import static junit.framework.TestCase.assertEquals;

import com.google.api.generator.gapic.composer.samplecode.SampleCodeJavaFormatter;
import com.google.api.generator.gapic.composer.samplecode.SampleCodeJavaFormatter.FormatException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class SampleCodeJavaFormatterTest {

  @Test
  public void validFormatSampleCode_tryCatchStatement() {
    String samplecode = String.format(createLines(3),
        "try(boolean condition = false){",
        "int x = 3;",
        "}");
    String result = SampleCodeJavaFormatter.format(samplecode);
    String expected =
        String.format(createLines(3), "try (boolean condition = false) {\n", "  int x = 3;\n", "}");
    assertEquals(expected, result);
  }

  @Test
  public void validFormatSampleCode_longLineStatement() {
    String sampleCode =
        "SubscriptionAdminSettings subscriptionAdminSettings = "
            + "SubscriptionAdminSettings.newBuilder().setEndpoint(myEndpoint).build();";
    String result = SampleCodeJavaFormatter.format(sampleCode);
    String expected =
        String.format(
            createLines(2),
            "SubscriptionAdminSettings subscriptionAdminSettings =\n",
            "    SubscriptionAdminSettings.newBuilder().setEndpoint(myEndpoint).build();");
    assertEquals(expected, result);
  }

  @Test
  public void validFormatSampleCode_longChainMethod() {
    String sampleCode = "echoSettingsBuilder.echoSettings().setRetrySettings(echoSettingsBuilder.echoSettings().getRetrySettings().toBuilder().setTotalTimeout(Duration.ofSeconds(30)).build());";
    String result = SampleCodeJavaFormatter.format(sampleCode);
    String expected =
        String.format(
            createLines(9),
            "echoSettingsBuilder\n",
            "    .echoSettings()\n",
            "    .setRetrySettings(\n",
            "        echoSettingsBuilder\n",
            "            .echoSettings()\n",
            "            .getRetrySettings()\n",
            "            .toBuilder()\n",
            "            .setTotalTimeout(Duration.ofSeconds(30))\n",
            "            .build());");
    assertEquals(expected, result);
  }

  @Test(expected = FormatException.class)
  public void invalidFormatSampleCode_nonStatement() {
    SampleCodeJavaFormatter.format("abc");
  }

  /** =============================== HELPERS =============================== */
  private static String createLines(int numLines) {
    return new String(new char[numLines]).replace("\0", "%s");
  }
}
