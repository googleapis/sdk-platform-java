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

package com.google.api.generator.spring.composer.comment;

import static com.google.api.generator.spring.composer.comment.CommentComposer.CLASS_HEADER_SUMMARY_PATTERN;

import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpringAutoconfigCommentComposer {

  private static final String CLASS_HEADER_GENERAL_DESCRIPTION =
          "Provides auto-configuration for Spring Boot";
  private static final String CLASS_HEADER_DEFAULTS_DESCRIPTION =
      "The default instance has everything set to sensible defaults:";
  private static final String CLASS_HEADER_DEFAULTS_CREDENTIALS_DESCRIPTION =
      "Credentials are acquired automatically through Application Default Credentials.";
  private static final String CLASS_HEADER_DEFAULTS_TRANSPORT_DESCRIPTION =
      "The default transport provider is used.";
  private static final String CLASS_HEADER_DEFAULTS_RETRIES_DESCRIPTION =
      "Retries are configured for idempotent methods but not for non-idempotent methods.";

  public static final String CREDENTIALS_PROVIDER_GENERAL_DESCRIPTION =
      "Obtains the default credentials provider. The used key will be obtained from application.properties";

  public static final String TRANSPORT_CHANNEL_PROVIDER_GENERAL_DESCRIPTION =
      "Returns the default channel provider. The default is gRPC and will default to it unless the "
          + "useRest option is provided to use HTTP transport instead";
  public static final String CLIENT_BEAN_GENERAL_DESCRIPTION = "Provides a %s client configured to "
      + "use the default credentials provider (obtained with googleCredentials()) and its default "
      + "transport channel provider (%s()). It also configures the quota project ID if provided. It "
      + "will configure an executor provider in case there is more than one thread configured "
      + "in the client ";

  public static final String CLIENT_BEAN_RETRY_SETTINGS_DESCRIPTION = "Individual retry settings "
      + "are configured as well. It will use the default retry settings obtained from %s when they "
      + "are not specified";

  public SpringAutoconfigCommentComposer() {}

  public static List<CommentStatement> createClassHeaderComments(
      String configuredClassName) {

    JavaDocComment.Builder javaDocCommentBuilder =
        JavaDocComment.builder()
            .addUnescapedComment(String.format(CLASS_HEADER_SUMMARY_PATTERN, configuredClassName))
            .addParagraph(CLASS_HEADER_GENERAL_DESCRIPTION)
            .addParagraph(CLASS_HEADER_DEFAULTS_DESCRIPTION)
            .addUnorderedList(
                Arrays.asList(
                    CLASS_HEADER_DEFAULTS_TRANSPORT_DESCRIPTION,
                    CLASS_HEADER_DEFAULTS_CREDENTIALS_DESCRIPTION,
                    CLASS_HEADER_DEFAULTS_RETRIES_DESCRIPTION));

    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_CLASS_COMMENT,
        CommentStatement.withComment(javaDocCommentBuilder.build()));
  }
  public static CommentStatement createCredentialsProviderBeanComment() {
    return CommentStatement.withComment(JavaDocComment.builder()
            .addParagraph(CREDENTIALS_PROVIDER_GENERAL_DESCRIPTION)
            .build()
    );
  }

  public static CommentStatement createTransportChannelProviderComment() {
    return CommentStatement.withComment(JavaDocComment.builder()
        .addParagraph(TRANSPORT_CHANNEL_PROVIDER_GENERAL_DESCRIPTION)
        .build()
    );
  }
  public static CommentStatement createClientBeanComment(
      String serviceName,
      String propertiesClazzName,
      String channelProviderName) {
    return CommentStatement.withComment(JavaDocComment.builder()
        .addParagraph(String.format(CLIENT_BEAN_GENERAL_DESCRIPTION, serviceName, channelProviderName))
        .addParagraph(String.format(CLIENT_BEAN_RETRY_SETTINGS_DESCRIPTION, propertiesClazzName))
        .build()
    );
  }
}
