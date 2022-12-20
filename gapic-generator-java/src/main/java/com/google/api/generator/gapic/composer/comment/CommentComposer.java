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

import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.Statement;
import java.util.Arrays;
import java.util.List;

public class CommentComposer {
  private static final String APACHE_LICENSE_STRING =
      "Copyright 2022 Google LLC\n\n"
          + "Licensed under the Apache License, Version 2.0 (the \"License\");\n"
          + "you may not use this file except in compliance with the License.\n"
          + "You may obtain a copy of the License at\n\n"
          + "     https://www.apache.org/licenses/LICENSE-2.0\n\n"
          + "Unless required by applicable law or agreed to in writing, software\n"
          + "distributed under the License is distributed on an \"AS IS\" BASIS,\n"
          + "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
          + "See the License for the specific language governing permissions and\n"
          + "limitations under the License.";

  private static final String AUTO_GENERATED_CLASS_DISCLAIMER_STRING =
      "AUTO-GENERATED DOCUMENTATION AND CLASS.";

  private static final String AUTO_GENERATED_METHOD_DISCLAIMER_STRING =
      "AUTO-GENERATED DOCUMENTATION AND METHOD.";

  static final String DEPRECATED_CLASS_STRING =
      "This class is deprecated and will be removed in the next major version update.";

  static final String DEPRECATED_METHOD_STRING =
      "This method is deprecated and will be removed in the next major version update.";

  public static final CommentStatement APACHE_LICENSE_COMMENT =
      CommentStatement.withComment(BlockComment.withComment(APACHE_LICENSE_STRING));

  public static final CommentStatement AUTO_GENERATED_CLASS_COMMENT =
      CommentStatement.withComment(LineComment.withComment(AUTO_GENERATED_CLASS_DISCLAIMER_STRING));

  public static final CommentStatement AUTO_GENERATED_METHOD_COMMENT =
      CommentStatement.withComment(
          LineComment.withComment(AUTO_GENERATED_METHOD_DISCLAIMER_STRING));

  public static final List<Statement> AUTO_GENERATED_SAMPLE_COMMENT =
      Arrays.asList(
          CommentStatement.withComment(
              LineComment.withComment(
                  "This snippet has been automatically generated and should be regarded as a code template only.")),
          CommentStatement.withComment(
              LineComment.withComment("It will require modifications to work:")),
          CommentStatement.withComment(
              LineComment.withComment(
                  "- It may require correct/in-range values for request initialization.")),
          CommentStatement.withComment(
              LineComment.withComment(
                  "- It may require specifying regional endpoints when creating the service client as shown in")),
          CommentStatement.withComment(
              LineComment.withComment(
                  "https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library")));
}
