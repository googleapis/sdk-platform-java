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
import java.util.Arrays;
import java.util.List;

class StubCommentComposer {
  private static final String STUB_CLASS_HEADER_SUMMARY_PATTERN =
      "Base stub class for the %s service API.";
  private static final String GRPC_CALLABLE_FACTORY_CLASS_HEADER_SUMMARY_PATTERN =
      "gRPC callable factory implementation for the %s service API.";
  private static final String GRPC_STUB_CLASS_HEADER_SUMMARY_PATTERN =
      "gRPC stub implementation for the %s service API.";

  private static final String ADVANCED_USAGE_DESCRIPTION = "This class is for advanced usage.";
  private static final String ADVANCED_USAGE_API_REFLECTION_DESCRIPTION =
      "This class is for advanced usage and reflects the underlying API directly.";

  static List<CommentStatement> createGrpcServiceStubClassHeaderComments(String serviceName) {
    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_CLASS_COMMENT,
        CommentStatement.withComment(
            JavaDocComment.builder()
                .addComment(String.format(GRPC_STUB_CLASS_HEADER_SUMMARY_PATTERN, serviceName))
                .addParagraph(ADVANCED_USAGE_API_REFLECTION_DESCRIPTION)
                .build()));
  }

  static List<CommentStatement> createGrpcServiceCallableFactoryClassHeaderComments(
      String serviceName) {
    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_CLASS_COMMENT,
        CommentStatement.withComment(
            JavaDocComment.builder()
                .addComment(
                    String.format(GRPC_CALLABLE_FACTORY_CLASS_HEADER_SUMMARY_PATTERN, serviceName))
                .addParagraph(ADVANCED_USAGE_DESCRIPTION)
                .build()));
  }

  static List<CommentStatement> createServiceStubClassHeaderComments(String serviceName) {
    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_CLASS_COMMENT,
        CommentStatement.withComment(
            JavaDocComment.builder()
                .addComment(String.format(STUB_CLASS_HEADER_SUMMARY_PATTERN, serviceName))
                .addParagraph(ADVANCED_USAGE_API_REFLECTION_DESCRIPTION)
                .build()));
  }
}
