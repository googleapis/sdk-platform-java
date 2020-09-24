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

package com.google.api.generator.gapic.dummy;

import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.Test;

public class FileDiffInfraDummyTest {
  // Add two simple tests for testing the file-diff infra.
  // 1. The two unit tests create simple java classes.
  // 2. Two unit tests are created because we have the case of two expected strings comparison
  // in one test class e.g. ResourceNameHelperClassComposer. In that case, two golden files will be
  // created.
  //
  // TODO(xiaozhenliu): remove this test class once the file-diff infra is in place and well-tested.
  private final String GOLDENFILES_DIRECTORY = Utils.getGoldenDir(this.getClass());
  private final String GOLDENFILES_SIMPLE_CLASS =
      Utils.getClassName(this.getClass()) + "SimpleClass.golden";
  private final String GOLDENFILES_CLASS_WITH_HEADER =
      Utils.getClassName(this.getClass()) + "ClassWithHeader.golden";

  @Test
  public void simpleClass() {
    ClassDefinition classDef =
        ClassDefinition.builder()
            .setHeaderCommentStatements(
                Arrays.asList(
                    CommentStatement.withComment(
                        LineComment.withComment("This is a test class for file-diff infra"))))
            .setPackageString("com.google.showcase.v1beta1.stub")
            .setName("EchoStubSettings")
            .setScope(ScopeNode.PUBLIC)
            .build();
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    classDef.accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), GOLDENFILES_SIMPLE_CLASS, visitor.write());
    Path goldenFilePath = Paths.get(GOLDENFILES_DIRECTORY, GOLDENFILES_SIMPLE_CLASS);
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void classWithHeader() {
    ClassDefinition classDef =
        ClassDefinition.builder()
            .setFileHeader(
                Arrays.asList(
                    CommentStatement.withComment(BlockComment.withComment(APACHE_LICENSE_STRING))))
            .setPackageString("com.google.showcase.v1beta1.stub")
            .setName("EchoStubSettings")
            .setScope(ScopeNode.PUBLIC)
            .build();
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    classDef.accept(visitor);
    // Save the generated code to a file for updating goldens if needed.
    Utils.saveCodegenToFile(this.getClass(), GOLDENFILES_CLASS_WITH_HEADER, visitor.write());
    Path goldenFilePath = Paths.get(GOLDENFILES_DIRECTORY, GOLDENFILES_CLASS_WITH_HEADER);
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  // Add a simple test for two strings comparison.
  @Test
  public void simpleLineComment() {
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    LineComment lineComment = LineComment.withComment("test strings comparison.");
    lineComment.accept(visitor);
    Assert.assertCodeEquals("// test strings comparison.", visitor.write());
  }

  private static final String APACHE_LICENSE_STRING =
      "Copyright 2020 Google LLC\n\n"
          + "Licensed under the Apache License, Version 2.0 (the \"License\");\n"
          + "you may not use this file except in compliance with the License.\n"
          + "You may obtain a copy of the License at\n\n"
          + "     https://www.apache.org/licenses/LICENSE-2.0\n\n"
          + "Unless required by applicable law or agreed to in writing, software\n"
          + "distributed under the License is distributed on an \"AS IS\" BASIS,\n"
          + "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
          + "See the License for the specific language governing permissions and\n"
          + "limitations under the License.";
}
