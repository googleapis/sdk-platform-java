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

public class JavaDocCommentTest {
    @Test
    public void simpleJavaDocComment(){
      String content = "this is a test comment";
      String deprecatedText = "Use the {@link ArchivedBookName} class instead.";
      String paramName = "shelfName";
      String paramDescription =  "The name of the shelf where books are published to.";
      String htmlP1 = "This class provides the ability to make remote calls to the backing service through method calls that map to API methods. Sample code to get started:";
      String htmlP2 = "The surface of this class includes several types of Java methods for each of the API's methods:";
  
      TryCatchStatement tryCatch =
          TryCatchStatement.builder()
              .setTryResourceExpr(createAssignmentExpr("condition", "false", TypeNode.BOOLEAN))
              .setTryBody(
                  Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
              .setIsSampleCode(true)
              .build();
  
      tryCatch.accept(writerVisitor);
      String sampleCode = writerVisitor.write();
      List<String> htmlList = Arrays.asList("A flattened method.", " A request object method.", "A callable method.");
      String throwText = "com.google.api.gax.rpc.ApiException if the remote call fails.";
      JavaDocComment javaDocComment =
          JavaDocComment.builder()
              .addComment(content)
              .addParagraph(htmlP1)
              .addSampleCode(sampleCode)
              .addParagraph(htmlP2)
              .addOrderedList(htmlList)
              .addSampleCode(sampleCode)
              .addParam(paramName, paramDescription)
              .setDeprecated(deprecatedText)
              .setThrowsText(throwText)
              .build();
      String expected =
          "/**\n"
              + "* this is a test comment\n"
              + "* <p> This class provides the ability to make remote calls to the backing service through method calls that map to API methods. Sample code to get started:\n"
              + "* <pre><code>\n"
              + "* try (boolean condition = false) {\n"
              + "* int x = 3;\n"
              + "* }\n"
              + "* </code></pre>\n"
              + "* <p> The surface of this class includes several types of Java methods for each of the API's methods:\n"
              + "* <ol>\n"
              + "* <li> A flattened method.\n"
              + "* <li>  A request object method.\n"
              + "* <li> A callable method.\n"
              + "* </ol>\n"
              + "* <pre><code>\n"
              + "* try (boolean condition = false) {\n"
              + "* int x = 3;\n"
              + "* }\n"
              + "* </code></pre>\n"
              + "* @param shelfName The name of the shelf where books are published to.\n"
              + "* @deprecated Use the {@link ArchivedBookName} class instead.\n"
              + "* @throws com.google.api.gax.rpc.ApiException if the remote call fails.\n"
              + "*/\n";
      String formattedComment = javaDocComment.accept(writerVisitor);
      assertThat(formattedComment).isEqualTo(expected);
    }
}