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

package com.google.api.generator.spring.composer;

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SpringComposerTest {
  private GapicContext context;

  @Before
  public void setUp() {
    this.context = TestProtoLoader.instance().parseShowcaseEcho();
  }

  @Test
  public void spring_composer_test() {

    List<GapicClass> gapicClasses = SpringComposer.composeServiceAutoConfigClasses(context);

    // write to verify result for now
    // This unit test does not update or write to any golden files, but checks against the same ones
    // as those generated and updated by individual class composer tests
    // TODO: may need to revisit this approach as more components are added to SpringComposer
    JavaWriterVisitor codeWriter = new JavaWriterVisitor();
    for (GapicClass gapicClazz : gapicClasses) {
      ClassDefinition clazz = gapicClazz.classDefinition();
      codeWriter.clear();
      clazz.accept(codeWriter);
      Path goldenFilePath =
          Paths.get(Utils.getGoldenDir(this.getClass()), clazz.classIdentifier() + ".golden");
      Assert.assertCodeEquals(goldenFilePath, codeWriter.write());
    }
  }
}
