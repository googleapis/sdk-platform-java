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

package com.google.api.generator.spring.composer.comment;

import static com.google.api.generator.spring.composer.comment.CommentComposer.CLASS_HEADER_SUMMARY_PATTERN;

import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import java.util.Arrays;
import java.util.List;

public class SpringPropertiesCommentComposer {
  private static final String CLASS_HEADER_GENERAL_DESCRIPTION =
      "Provides default configuration values for %s client";

  public static List<CommentStatement> createClassHeaderComments(
      String configuredClassName, String serviceName) {

    JavaDocComment.Builder javaDocCommentBuilder =
        JavaDocComment.builder()
            .addUnescapedComment(String.format(CLASS_HEADER_SUMMARY_PATTERN, configuredClassName))
            .addParagraph(String.format(CLASS_HEADER_GENERAL_DESCRIPTION, serviceName));
    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_CLASS_COMMENT,
        CommentStatement.withComment(javaDocCommentBuilder.build()));
  }
}
