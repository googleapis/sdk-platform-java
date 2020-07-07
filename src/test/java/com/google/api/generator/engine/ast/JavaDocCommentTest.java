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
  @Test
  public void createJavaDocComment_basic() {
    String content = "this is a test comment";
    JavaDocComment javaDocComment = JavaDocComment.withComment(content);
    assertEquals(javaDocComment.comment(), content);
  }

  @Test
  public void createavaDocComment_allComponents() {
    String content = "this is a test comment";
    String deprecatedText = "Use the {@link ArchivedBookName} class instead.";
    String paramName = "shelfName";
    String paramDescription = "The name of the shelf where books are published to.";
    String paragraph1 =
        "This class provides the ability to make remote calls to the backing service through method calls that map to API methods. Sample code to get started:";
    String paragraph2 =
        "The surface of this class includes several types of Java methods for each of the API's methods:";
    List<String> orderedList =
        Arrays.asList("A flattened method.", "A request object method.", "A callable method.");
    String throwType = "com.google.api.gax.rpc.ApiException";
    String throwsDescription = "if the remote call fails.";
    JavaDocComment javaDocComment =
        JavaDocComment.builder()
            .addComment(content)
            .addParagraph(paragraph1)
            .addParagraph(paragraph2)
            .addOrderedList(orderedList)
            .addParam(paramName, paramDescription)
            .setThrows(throwType, throwsDescription)
            .setDeprecated(deprecatedText)
            .build();
    String expected =
        "this is a test comment\n"
            + "<p> This class provides the ability to make remote calls to the backing service through method calls that map to API methods. Sample code to get started:\n"
            + "<p> The surface of this class includes several types of Java methods for each of the API's methods:\n"
            + "<ol>\n"
            + "<li> A flattened method.\n"
            + "<li> A request object method.\n"
            + "<li> A callable method.\n"
            + "</ol>\n"
            + "@param shelfName The name of the shelf where books are published to.\n"
            + "@throws com.google.api.gax.rpc.ApiException if the remote call fails.\n"
            + "@deprecated Use the {@link ArchivedBookName} class instead.";
    assertEquals(javaDocComment.comment(), expected);
  }
}
