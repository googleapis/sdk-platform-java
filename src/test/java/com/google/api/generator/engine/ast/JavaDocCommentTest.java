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

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class JavaDocCommentTest {
  // TODO(xiaozhenliu): add escaping-related unit tests for JavaDocComment class.
  @Test
  public void createJavaDocComment_basic() {
    String content = "this is a test comment";
    JavaDocComment javaDocComment = JavaDocComment.builder().addComment(content).build();
    assertEquals(javaDocComment.comment(), content);
  }

  @Test
  public void createJavaDocComment_sampleCode() {
    String comment = "sample codes:";
    String sampleCode = "resource = project/{project}/shelfId/{shelfId}";
    JavaDocComment javaDocComment =
        JavaDocComment.builder().addComment(comment).addSampleCode(sampleCode).build();
    String expected =
        "sample codes:\n"
            + "<pre><code>\n"
            + "resource = project/{project}/shelfId/{shelfId}\n"
            + "</code></pre>";
    assertEquals(javaDocComment.comment(), expected);
  }

  @Test
  public void createJavaDocComment_multipleComments() {
    // Add methods, like `addComment()`, should add components at any place,
    // and they will get printed in order.
    String comment1 = "This is a test comment.";
    String comment2 = "This is unordered list.";
    String comment3 = "This is ordered list.";
    List<String> orderedList =
        Arrays.asList("A flattened method.", "A request object method.", "A callable method.");
    List<String> unOrderedList =
        Arrays.asList("A flattened method.", "A request object method.", "A callable method.");
    JavaDocComment javaDocComment =
        JavaDocComment.builder()
            .addComment(comment1)
            .addComment(comment2)
            .addUnorderedList(unOrderedList)
            .addComment(comment3)
            .addOrderedList(orderedList)
            .build();
    String expected =
        "This is a test comment.\n"
            + "This is unordered list.\n"
            + "<ul>\n"
            + "<li> A flattened method.\n"
            + "<li> A request object method.\n"
            + "<li> A callable method.\n"
            + "</ul>\n"
            + "This is ordered list.\n"
            + "<ol>\n"
            + "<li> A flattened method.\n"
            + "<li> A request object method.\n"
            + "<li> A callable method.\n"
            + "</ol>";
    assertEquals(javaDocComment.comment(), expected);
  }

  @Test
  public void createJavaDocComment_multipleParams() {
    // Parameters should be grouped together and get printed after block comments.
    String comment = "This is a block comment.";
    String paramName1 = "shelfName";
    String paramDescription1 = "The name of the shelf where books are published to.";
    String paramName2 = "shelfId";
    String paramDescription2 = "The shelfId of the shelf where books are published to.";
    JavaDocComment javaDocComment =
        JavaDocComment.builder()
            .addParam(paramName1, paramDescription1)
            .addParam(paramName2, paramDescription2)
            .addComment(comment)
            .build();
    String expected =
        "This is a block comment.\n"
            + "@param shelfName The name of the shelf where books are published to.\n"
            + "@param shelfId The shelfId of the shelf where books are published to.";
    assertEquals(javaDocComment.comment(), expected);
  }

  @Test
  public void createJavaDocComment_throesDeprecated() {
    // No matter how many times or order `setThrows` and `setDeprecated` are called,
    // only one @throws and @deprecated will be printed.
    String throwType = "com.google.api.gax.rpc.ApiException";
    String throwsDescription = "if the remote call fails.";
    String throwType_print = "java.lang.RuntimeException";
    String throwsDescription_print = "if the remote call fails.";

    String deprecatedText = "Use the {@link ArchivedBookName} class instead.";
    String deprecatedText_print = "Use the {@link ShelfBookName} class instead.";

    JavaDocComment javaDocComment =
        JavaDocComment.builder()
            .setThrows(throwType, throwsDescription)
            .setDeprecated(deprecatedText)
            .setThrows(throwType_print, throwsDescription_print)
            .setDeprecated(deprecatedText_print)
            .build();
    String expected =
        "@throws java.lang.RuntimeException if the remote call fails.\n"
            + "@deprecated Use the {@link ShelfBookName} class instead.";
    assertEquals(javaDocComment.comment(), expected);
  }

  @Test
  public void createavaDocComment_allComponents() {
    // No matter what order `setThrows`, `setDeprecated` are called,
    // They will be printed at the end. And `@param` should be grouped,
    // they should always be printed right before `@throws` and `@deprecated`.
    // All other add methods should keep the order of how they are added.
    String content = "this is a test comment";
    String deprecatedText = "Use the {@link ArchivedBookName} class instead.";
    String paramName1 = "shelfName";
    String paramDescription1 = "The name of the shelf where books are published to.";
    String paramName2 = "shelf";
    String paramDescription2 = "The shelf to create.";
    String paragraph1 =
        "This class provides the ability to make remote calls to the backing service through"
            + " method calls that map to API methods. Sample code to get started:";
    String paragraph2 =
        "The surface of this class includes several types of Java methods for each of the API's"
            + " methods:";
    List<String> orderedList =
        Arrays.asList("A flattened method.", "A request object method.", "A callable method.");
    String throwType = "com.google.api.gax.rpc.ApiException";
    String throwsDescription = "if the remote call fails.";
    JavaDocComment javaDocComment =
        JavaDocComment.builder()
            .setDeprecated(deprecatedText)
            .setThrows(throwType, throwsDescription)
            .addParam(paramName1, paramDescription1)
            .addComment(content)
            .addParagraph(paragraph1)
            .addParagraph(paragraph2)
            .addOrderedList(orderedList)
            .addParam(paramName2, paramDescription2)
            .build();
    String expected =
        "this is a test comment\n"
            + "<p> This class provides the ability to make remote calls to the backing service"
            + " through method calls that map to API methods. Sample code to get started:\n"
            + "<p> The surface of this class includes several types of Java methods for each of"
            + " the API's methods:\n"
            + "<ol>\n"
            + "<li> A flattened method.\n"
            + "<li> A request object method.\n"
            + "<li> A callable method.\n"
            + "</ol>\n"
            + "@param shelfName The name of the shelf where books are published to.\n"
            + "@param shelf The shelf to create.\n"
            + "@throws com.google.api.gax.rpc.ApiException if the remote call fails.\n"
            + "@deprecated Use the {@link ArchivedBookName} class instead.";
    assertEquals(javaDocComment.comment(), expected);
  }
}
