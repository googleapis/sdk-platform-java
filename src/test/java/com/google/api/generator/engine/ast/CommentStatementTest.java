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

import org.junit.Test;

public class CommentStatementTest {
  @Test
  public void validCommentStatement_lineComment() {
    LineComment lineComment = LineComment.withComment("Line comment statement.");
    CommentStatement commentStatement = CommentStatement.withComment(lineComment);
    assertEquals(commentStatement.comment(), lineComment);
  }

  @Test
  public void validCommentStatement_blockComment() {
    BlockComment blockComment =
        BlockComment.withComment("Test response handling for methods that return empty");
    CommentStatement commentStatement = CommentStatement.withComment(blockComment);
    assertEquals(commentStatement.comment(), blockComment);
  }

  @Test
  public void validCommentStatement_javaDocComment() {
    JavaDocComment javaDocComment = createJavaDocComment();
    CommentStatement commentStatement = CommentStatement.withComment(javaDocComment);
    assertEquals(commentStatement.comment(), javaDocComment);
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
