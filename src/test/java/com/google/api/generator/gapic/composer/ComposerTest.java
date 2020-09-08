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

package com.google.api.generator.gapic.composer;

import static junit.framework.Assert.assertEquals;

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ComposerTest {
  private static final String GOLDENFILES_DIRECTORY =
      "src/test/java/com/google/api/generator/gapic/composer/goldens/";

  @Test
  public void gapicClass_addApacheLicense() throws IOException {
    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString("com.google.showcase.v1beta1.stub")
            .setName("EchoStubSettings")
            .setScope(ScopeNode.PUBLIC)
            .build();
    List<GapicClass> gapicClassWithHeaderList =
        Composer.addApacheLicense(Arrays.asList(GapicClass.create(Kind.TEST, classDef)));
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    gapicClassWithHeaderList.get(0).classDefinition().accept(visitor);
    Path goldeFilePath = Paths.get(GOLDENFILES_DIRECTORY, "ComposerTest.golden");
    String expectedClassString = new String(Files.readAllBytes(goldeFilePath));
    assertEquals(visitor.write(), expectedClassString);
  }
}
