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
      String configuredClassName,
      String serviceName) {

    JavaDocComment.Builder javaDocCommentBuilder =
        JavaDocComment.builder()
            .addUnescapedComment(String.format(CLASS_HEADER_SUMMARY_PATTERN, configuredClassName))
            .addParagraph(String.format(CLASS_HEADER_GENERAL_DESCRIPTION, serviceName));
    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_CLASS_COMMENT,
        CommentStatement.withComment(javaDocCommentBuilder.build()));
  }
}
