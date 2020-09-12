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
import com.google.api.generator.engine.ast.TypeNode;

class ServiceClientCommentComposer {
  private static final String COLON = ":";

  private static final String CREATE_METHOD_STUB_ARG_PATTERN =
      "Constructs an instance of EchoClient, using the given stub for making calls. This is for"
          + " advanced usage - prefer using create(%s).";

  static final CommentStatement CREATE_METHOD_NO_ARG_COMMENT =
      toSimpleComment("Constructs an instance of EchoClient with default settings.");

  static final CommentStatement CREATE_METHOD_SETTINGS_ARG_COMMENT =
      toSimpleComment(
          "Constructs an instance of EchoClient, using the given settings. The channels are"
              + " created based  on the settings passed in, or defaults for any settings that are"
              + " not set.");

  static final CommentStatement PROTECTED_CONSTRUCTOR_SETTINGS_ARG_COMMENT =
      toSimpleComment(
          "Constructs an instance of EchoClient, using the given settings. This is protected so"
              + " that it is easy to make a subclass, but otherwise, the static factory methods"
              + " should be preferred.");

  static final CommentStatement GET_OPERATIONS_CLIENT_METHOD_COMMENT =
      toSimpleComment(
          "Returns the OperationsClient that can be used to query the status of a long-running"
              + " operation returned by another API method call.");

  static CommentStatement createCreateMethodStubArgComment(TypeNode settingsType) {
    return toSimpleComment(
        String.format(CREATE_METHOD_STUB_ARG_PATTERN, settingsType.reference().name()));
  }

  private static CommentStatement toSimpleComment(String comment) {
    return CommentStatement.withComment(JavaDocComment.withComment(comment));
  }
}
