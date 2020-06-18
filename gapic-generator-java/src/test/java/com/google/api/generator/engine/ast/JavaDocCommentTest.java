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

package com.google.api.generator.engine.ast;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.generator.engine.ast.JavaDocComment.ParamPair;
import org.junit.Test;

public class JavaDocCommentTest {
  @Test
  public void writeNormalJavaDocComment() {
    String content = "this is a test comment";
    String deprecatedText = "Use the {@link ArchivedBookName} class instead.";
    ParamPair p = new ParamPair("shelfName", "The name of the shelf where books are published to.");
    String sampleCode =
        "try (LibraryClient libraryClient = LibraryClient.create()) {\n Shelf shelf = Shelf.newBuilder().build();\nShelf response = libraryClient.createShelf(shelf);\n}";
    String throwText = "com.google.api.gax.rpc.ApiException if the remote call fails";
    JavaDocComment javaDocComment =
        JavaDocComment.builder()
            .addComment(content)
            .setSampleCode(sampleCode)
            .addParam(p)
            .setDeprecated(deprecatedText)
            .setThrowsText(throwText)
            .build();
    String expected =
        "/**\n"
            + "* this is a test comment\n"
            + "* @param shelfName The name of the shelf where books are published to.\n"
            + "* Sample code:\n"
            + "* <pre><code>\n"
            + "* try (LibraryClient libraryClient = LibraryClient.create()) {\n"
            + "*  Shelf shelf = Shelf.newBuilder().build();\n"
            + "* Shelf response = libraryClient.createShelf(shelf);\n"
            + "* }\n"
            + "* </code></pre>\n"
            + "* @deprecated Optional[Use the {@link ArchivedBookName} class instead.]\n"
            + "* @throws Optional[com.google.api.gax.rpc.ApiException if the remote call fails]\n"
            + "*/\n";
    System.out.println(javaDocComment.write());
    System.out.println(expected);
    assertThat(javaDocComment.write()).isEqualTo(expected);
  }
}
