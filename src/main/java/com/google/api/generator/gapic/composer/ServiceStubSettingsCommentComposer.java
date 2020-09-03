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

package com.google.api.generator.gapic.composer;

import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.LineComment;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ServiceStubSettingsCommentComposer {
  private static final String BUILDER_CLASS_DOC_PATTERN = "Builder for %s.";
  private static final String CALL_SETTINGS_METHOD_DOC_PATTERN =
      "Returns the object with the settings used for calls to %s.";
  private static final String CALL_SETTINGS_BUILDER_METHOD_DOC_PATTERN =
      "Returns the builder for the settings used for calls to %s.";

  static final CommentStatement DEFAULT_SCOPES_COMMENT =
      toSimpleComment("The default scopes of the service.");

  static final CommentStatement DEFAULT_EXECUTOR_PROVIDER_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a builder for the default ExecutorProvider for this service.");
  static final CommentStatement DEFAULT_SERVICE_ENDPOINT_METHOD_COMMENT =
      toSimpleComment("Returns the default service endpoint.");
  static final CommentStatement DEFAULT_SERVICE_SCOPES_METHOD_COMMENT =
      toSimpleComment("Returns the default service scopes.");

  static final CommentStatement DEFAULT_CREDENTIALS_PROVIDER_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a builder for the default credentials for this service.");

  static final CommentStatement DEFAULT_GRPC_TRANSPORT_PROVIDER_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a builder for the default ChannelProvider for this service.");

  static final CommentStatement NEW_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a new builder for this class.");

  static final CommentStatement TO_BUILDER_METHOD_COMMENT =
      toSimpleComment("Returns a builder containing all the values of this settings class.");

  static final List<CommentStatement> APPLY_TO_ALL_UNARY_METHODS_METHOD_COMMENTS =
      Arrays.asList(
              LineComment.withComment("NEXT_MAJOR_VER: remove 'throws Exception'."),
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

  static CommentStatement createCallSettingsGetterComment(String javaMethodName) {
    return toSimpleComment(String.format(CALL_SETTINGS_METHOD_DOC_PATTERN, javaMethodName));
  }

  static CommentStatement createBuilderClassComment(String outerClassName) {
    return toSimpleComment(String.format(BUILDER_CLASS_DOC_PATTERN, outerClassName));
  }

  static CommentStatement createCallSettingsBuilderGetterComment(String javaMethodName) {
    return toSimpleComment(String.format(CALL_SETTINGS_BUILDER_METHOD_DOC_PATTERN, javaMethodName));
  }

  private static CommentStatement toSimpleComment(String comment) {
    return CommentStatement.withComment(JavaDocComment.withComment(comment));
  }
}
