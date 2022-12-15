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

package com.google.api.generator.gapic.composer.samplecode;

import static org.junit.Assert.assertEquals;

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicSnippetConfig;
import java.util.List;

import com.google.api.generator.testutils.LineFormatter;
import org.junit.Test;

public class ConfiguredSnippetComposerTest {

  private GapicContext context = TestProtoLoader.instance().parseShowcaseEcho();
  private List<GapicSnippetConfig> gapicSnippetConfigList = context.snippetConfigs();
  private GapicSnippetConfig snippetConfig = gapicSnippetConfigList.get(0);

  @Test
  public void composeSampleMethodName() {
    String result = ConfiguredSnippetComposer.composeSampleMethodName(snippetConfig);
    assertEquals("asyncCreateCustomClass", result);
  }

  // TODO: Update test
      @Test
      public void createConfigSnippet() {
          String sampleResult =
   writeSample(ConfiguredSnippetComposer.composeConfiguredSnippetClass(snippetConfig));
          String expected =
                  LineFormatter.lines(
                          "google.cloud.speech.v1.samples;\n",
                          "\n",
                          "// [START adaptation_v1_config_Adaptation_CreateCustomClass_Basic_sync]\n",
                          "public class SyncCreateExecutableSampleEmptyStatementSample {\n",
                          "\n",
                          "  public static void main(String[] args) throws Exception {\n",
                          "    syncCreateExecutableSampleEmptyStatementSample();\n",
                          "  }\n",
                          "\n",
                          "  public static void syncCreateExecutableSampleEmptyStatementSample()throws Exception {\n",
                          "  }\n",
                          "}\n",
                          "// [END adaptation_v1_config_Adaptation_CreateCustomClass_Basic_sync]\n");
          assertEquals(expected, sampleResult);
      }

  private static String writeSample(ClassDefinition sample) {
    return SampleCodeWriter.write(sample);
  }

  private static String writeSample(List<Statement> sample) {
    return SampleCodeWriter.write(sample);
  }
}
