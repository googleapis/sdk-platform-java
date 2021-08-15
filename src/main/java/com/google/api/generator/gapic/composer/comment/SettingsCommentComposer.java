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

package com.google.api.generator.gapic.composer.comment;

import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SettingsCommentComposer {
  private static final String COLON = ":";

  private static final String STUB_PATTERN = "%sStub";
  private static final String BUILDER_CLASS_DOC_PATTERN = "Builder for %s.";
  private static final String CALL_SETTINGS_METHOD_DOC_PATTERN =
      "Returns the object with the settings used for calls to %s.";
  private static final String CALL_SETTINGS_BUILDER_METHOD_DOC_PATTERN =
      "Returns the builder for the settings used for calls to %s.";

  // Class header patterns.
  private static final String CLASS_HEADER_SUMMARY_PATTERN =
      "Settings class to configure an instance of {@link %s}.";
  private static final String CLASS_HEADER_DEFAULT_ADDRESS_PORT_PATTERN =
      "The default service address (%s) and default port (%d) are used.";
  private static final String CLASS_HEADER_SAMPLE_CODE_PATTERN =
      "For example, to set the total timeout of %s to 30 seconds:";

  private static final String CLASS_HEADER_BUILDER_DESCRIPTION =
      "The builder of this class is recursive, so contained classes are themselves builders. When"
          + " build() is called, the tree of builders is called to create the complete settings"
          + " object.";
  private static final String CLASS_HEADER_DEFAULTS_DESCRIPTION =
      "The default instance has everything set to sensible defaults:";
  private static final String CLASS_HEADER_DEFAULTS_CREDENTIALS_DESCRIPTION =
      "Credentials are acquired automatically through Application Default Credentials.";
  private static final String CLASS_HEADER_DEFAULTS_RETRIES_DESCRIPTION =
      "Retries are configured for idempotent methods but not for non-idempotent methods.";

  public static final CommentStatement DEFAULT_SCOPES_COMMENT =
      toSimpleComment("The default scopes of the service.");

  public static final CommentStatement DEFAULT_EXECUTOR_PROVIDER_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a builder for the default ExecutorProvider for this service.");
  public static final CommentStatement DEFAULT_SERVICE_ENDPOINT_METHOD_COMMENT =
      toSimpleComment("Returns the default service endpoint.");
  public static final CommentStatement DEFAULT_SERVICE_MTLS_ENDPOINT_METHOD_COMMENT =
      toSimpleComment("Returns the default mTLS service endpoint.");
  public static final CommentStatement DEFAULT_SERVICE_SCOPES_METHOD_COMMENT =
      toSimpleComment("Returns the default service scopes.");

  public static final CommentStatement DEFAULT_CREDENTIALS_PROVIDER_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a builder for the default credentials for this service.");

  public static final CommentStatement DEFAULT_TRANSPORT_PROVIDER_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a builder for the default ChannelProvider for this service.");

  public static final CommentStatement NEW_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a new builder for this class.");

  public static final CommentStatement TO_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a builder containing all the values of this settings class.");

  public static final List<CommentStatement> APPLY_TO_ALL_UNARY_METHODS_METHOD_COMMENTS =
      Arrays.asList(
              JavaDocComment.builder()
                  .addComment(
                      "Applies the given settings updater function to all of the unary API methods"
                          + " in this service.")
                  .addParagraph(
                      "Note: This method does not support applying settings to streaming methods.")
                  .build())
          .stream()
          .map(c -> CommentStatement.withComment(c))
          .collect(Collectors.toList());

  public static CommentStatement createCallSettingsGetterComment(
      String javaMethodName, boolean isMethodDeprecated) {
    String methodComment = String.format(CALL_SETTINGS_METHOD_DOC_PATTERN, javaMethodName);
    return isMethodDeprecated
        ? toDeprecatedSimpleComment(methodComment)
        : toSimpleComment(methodComment);
  }

  public static CommentStatement createBuilderClassComment(String outerClassName) {
    return toSimpleComment(String.format(BUILDER_CLASS_DOC_PATTERN, outerClassName));
  }

  public static CommentStatement createCallSettingsBuilderGetterComment(
      String javaMethodName, boolean isMethodDeprecated) {
    String methodComment = String.format(CALL_SETTINGS_BUILDER_METHOD_DOC_PATTERN, javaMethodName);
    return isMethodDeprecated
        ? toDeprecatedSimpleComment(methodComment)
        : toSimpleComment(methodComment);
  }

  public static List<CommentStatement> createClassHeaderComments(
      String configuredClassName,
      String defaultHost,
      boolean isDeprecated,
      Optional<String> methodNameOpt,
      Optional<String> sampleCodeOpt,
      TypeNode classType) {
    // Split default address and port.
    int colonIndex = defaultHost.indexOf(COLON);
    Preconditions.checkState(
        colonIndex > 0 && colonIndex < defaultHost.length() - 1,
        String.format(
            "No valid address and port found for %s, expected a string formatted like"
                + " localhost:8888",
            defaultHost));
    String defaultAddress = defaultHost.substring(0, colonIndex);
    int defaultPort = Integer.parseInt(defaultHost.substring(colonIndex + 1));

    JavaDocComment.Builder javaDocCommentBuilder =
        JavaDocComment.builder()
            .addUnescapedComment(String.format(CLASS_HEADER_SUMMARY_PATTERN, configuredClassName))
            .addParagraph(CLASS_HEADER_DEFAULTS_DESCRIPTION)
            .addUnorderedList(
                Arrays.asList(
                    String.format(
                        CLASS_HEADER_DEFAULT_ADDRESS_PORT_PATTERN, defaultAddress, defaultPort),
                    CLASS_HEADER_DEFAULTS_CREDENTIALS_DESCRIPTION,
                    CLASS_HEADER_DEFAULTS_RETRIES_DESCRIPTION))
            .addParagraph(CLASS_HEADER_BUILDER_DESCRIPTION);

    if (methodNameOpt.isPresent() && sampleCodeOpt.isPresent()) {
      javaDocCommentBuilder =
          javaDocCommentBuilder
              .addParagraph(
                  String.format(
                      CLASS_HEADER_SAMPLE_CODE_PATTERN,
                      JavaStyle.toLowerCamelCase(methodNameOpt.get())))
              .addSampleCode(sampleCodeOpt.get());
    }

    if (isDeprecated) {
      javaDocCommentBuilder.setDeprecated(CommentComposer.DEPRECATED_CLASS_STRING);
    }

    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_CLASS_COMMENT,
        CommentStatement.withComment(javaDocCommentBuilder.build()));
  }

  private static CommentStatement toSimpleComment(String comment) {
    return CommentStatement.withComment(JavaDocComment.withComment(comment));
  }

  private static CommentStatement toDeprecatedSimpleComment(String comment) {
    return CommentStatement.withComment(
        JavaDocComment.builder()
            .addComment(comment)
            .setDeprecated(CommentComposer.DEPRECATED_METHOD_STRING)
            .build());
  }
}
