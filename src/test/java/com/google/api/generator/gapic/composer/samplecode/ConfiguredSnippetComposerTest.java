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
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.ClientLibraryPackageInfoComposer;
import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicPackageInfo;
import com.google.api.generator.gapic.model.GapicSnippetConfig;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import com.google.api.generator.testutils.LineFormatter;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Type;
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

  @Test
  public void composeConfiguredSnippet_speech() {
    ClassDefinition configuredSnippet = ConfiguredSnippetComposer.composeConfiguredSnippetClass(snippetConfig);
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    configuredSnippet.accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "ConfiguredSnippetComposerSpeech.golden", visitor.write());
    Path goldenFilePath =
            Paths.get(Utils.getGoldenDir(this.getClass()), "ConfiguredSnippetComposerSpeech.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
    }

  private static String writeSample(ClassDefinition sample) {
    return SampleCodeWriter.write(sample);
  }

  private static String writeSample(List<Statement> sample) {
    return SampleCodeWriter.write(sample);
  }
}
