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

import org.junit.Test;

public class CommentStatementTest {
  @Test
  public void validCommentStatement_lineComments() {
    // Real test case: Only line comments are in the comment statement.
    CommentStatement commentStatement =
        CommentStatement.builder()
            .addLineComment(
                createLineComment(
                    "DO NOT EDIT! This is a generated sample (\"LongRunningRequestAsync\",  \"hopper\""))
            .addLineComment(createLineComment("sample-metadata:"))
            .addLineComment(createLineComment("title: GetBigBook: 'War and Peace'"))
            .addLineComment(createLineComment("description: Testing calling forms"))
            .addLineComment(
                createLineComment(
                    "usage: gradle run -PmainClass=com.google.example.examples.library.v1.Hopper [--args='[--shelf \"Novel\\\"`\b\t\n\r\"]']"))
            .build();
  }

  @Test
  public void validCommentStatement_blockComment() {
    // Real test case: Only block comment is in the comment statement.
    CommentStatement commentStatement =
        CommentStatement.builder()
            .setBlockComment(
                createBlockComment("Test response handling for methods that return empty"))
            .build();
  }

  @Test
  public void validCommentStatement_javaDocComment() {
    // Real test case: Only javaDocComment is in the comment statement.
    CommentStatement commentStatement =
        CommentStatement.builder().setJavaDocComment(createJavaDocComment()).build();
  }

  @Test
  public void validCommentStatement_allComponenets() {
    // LineComments should be grouped together, and comments should be in the order of
    // LineComments -> JavaDocComment -> BlockComment
    CommentStatement commentStatement =
        CommentStatement.builder()
            .addLineComment(createLineComment("AUTO-GENERATED DOCUMENTATION AND METHOD"))
            .setBlockComment(
                createBlockComment(
                    "Returns the object with the settings used for calls to myMethod."))
            .setJavaDocComment(createJavaDocComment())
            .addLineComment(createLineComment("NEXT_MAJOR_VER: remove 'throws Exception'"))
            .build();
  }

  private LineComment createLineComment(String comment) {
    return LineComment.withComment(comment);
  }

  private BlockComment createBlockComment(String comment) {
    return BlockComment.withComment(comment);
  }

  private JavaDocComment createJavaDocComment() {
    JavaDocComment javaDocComment =
        JavaDocComment.builder()
            .addComment(
                "Parses the book from the given fully-qualified path which represents a shelf_book resource.")
            .setDeprecated("Use the {@link ShelfBookName} class instead.")
            .build();
    return javaDocComment;
  }
}
