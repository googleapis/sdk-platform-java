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
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ServiceClientCommentComposer {
  // Tokens.
  private static final String COLON = ":";
  private static final String EMPTY_STRING = "";
  private static final String API_EXCEPTION_TYPE_NAME = "com.google.api.gax.rpc.ApiException";
  private static final String EXCEPTION_CONDITION = "if the remote call fails";

  // Constants.
  private static final String SERVICE_DESCRIPTION_INTRO_STRING =
      "This class provides the ability to make remote calls to the backing service through method "
          + "calls that map to API methods. Sample code to get started:";
  private static final String SERVICE_DESCRIPTION_CLOSE_STRING =
      "Note: close() needs to be called on the echoClient object to clean up resources such as "
          + "threads. In the example above, try-with-resources is used, which automatically calls "
          + "close().";
  private static final String SERVICE_DESCRIPTION_SURFACE_SUMMARY_STRING =
      "The surface of this class includes several types of Java methods for each of the API's "
          + "methods:";
  private static final String SERVICE_DESCRIPTION_SURFACE_CODA_STRING =
      "See the individual methods for example code.";
  private static final String SERVICE_DESCRIPTION_RESOURCE_NAMES_FORMATTING_STRING =
      "Many parameters require resource names to be formatted in a particular way. To assist with"
          + " these names, this class includes a format method for each type of name, and"
          + " additionally a parse  method to extract the individual identifiers contained within"
          + " names that are returned.";
  private static final String SERVICE_DESCRIPTION_CREDENTIALS_SUMMARY_STRING =
      "To customize credentials:";
  private static final String SERVICE_DESCRIPTION_ENDPOINT_SUMMARY_STRING =
      "To customize the endpoint:";

  private static final String METHOD_DESCRIPTION_SAMPLE_CODE_SUMMARY_STRING = "Sample code:";

  private static final List<String> SERVICE_DESCRIPTION_SURFACE_DESCRIPTION =
      Arrays.asList(
          "A \"flattened\" method. With this type of method, the fields of the request type have"
              + " been converted into function parameters. It may be the case that not all fields"
              + " are available as parameters, and not every API method will have a flattened"
              + " method entry point.",
          "A \"request object\" method. This type of method only takes one parameter, a request"
              + " object, which must be constructed before the call. Not every API method will"
              + " have a request object method.",
          "A \"callable\" method. This type of method takes no parameters and returns an immutable "
              + "API callable object, which can be used to initiate calls to the service.");

  // Patterns.
  private static final String CREATE_METHOD_STUB_ARG_PATTERN =
      "Constructs an instance of EchoClient, using the given stub for making calls. This is for"
          + " advanced usage - prefer using create(%s).";

  private static final String SERVICE_DESCRIPTION_CUSTOMIZE_SUMMARY_PATTERN =
      "This class can be customized by passing in a custom instance of %s to create(). For"
          + " example:";

  private static final String SERVICE_DESCRIPTION_SUMMARY_PATTERN = "Service Description: %s";

  // Comments.
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

  static List<CommentStatement> createClassHeaderComments(Service service) {
    JavaDocComment.Builder classHeaderJavadocBuilder = JavaDocComment.builder();
    if (service.hasDescription()) {
      classHeaderJavadocBuilder.addComment(
          String.format(SERVICE_DESCRIPTION_SUMMARY_PATTERN, service.description()));
    }

    // Service introduction.
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_INTRO_STRING);
    // TODO(summerji): Add sample code here.

    // API surface description.
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_CLOSE_STRING);
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_SURFACE_SUMMARY_STRING);
    classHeaderJavadocBuilder.addOrderedList(SERVICE_DESCRIPTION_SURFACE_DESCRIPTION);
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_SURFACE_CODA_STRING);

    // Formatting resource names.
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_RESOURCE_NAMES_FORMATTING_STRING);

    // Customization examples.
    classHeaderJavadocBuilder.addParagraph(
        String.format(
            SERVICE_DESCRIPTION_CUSTOMIZE_SUMMARY_PATTERN,
            String.format("%sSettings", JavaStyle.toUpperCamelCase(service.name()))));
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_CREDENTIALS_SUMMARY_STRING);
    // TODO(summerji): Add credentials' customization sample code here.
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_ENDPOINT_SUMMARY_STRING);
    // TODO(summerji): Add endpoint customization sample code here.

    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_CLASS_COMMENT,
        CommentStatement.withComment(classHeaderJavadocBuilder.build()));
  }

  static CommentStatement createCreateMethodStubArgComment(TypeNode settingsType) {
    return toSimpleComment(
        String.format(CREATE_METHOD_STUB_ARG_PATTERN, settingsType.reference().name()));
  }

  static List<CommentStatement> createRpcMethodHeaderComment(
      Method method, List<MethodArgument> methodArguments) {
    JavaDocComment.Builder methodJavadocBuilder = JavaDocComment.builder();

    if (method.hasDescription()) {
      methodJavadocBuilder.addComment(method.description());
    }

    methodJavadocBuilder.addParagraph(METHOD_DESCRIPTION_SAMPLE_CODE_SUMMARY_STRING);
    // TODO(summerji): Add sample code here.

    if (methodArguments.isEmpty()) {
      methodJavadocBuilder.addParam(
          "request", "The request object containing all of the parameters for the API call.");
    } else {
      for (MethodArgument argument : methodArguments) {
        String description =
            argument.field().hasDescription() ? argument.field().description() : EMPTY_STRING;
        methodJavadocBuilder.addParam(argument.name(), description);
      }
    }

    methodJavadocBuilder.setThrows(API_EXCEPTION_TYPE_NAME, EXCEPTION_CONDITION);

    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_METHOD_COMMENT,
        CommentStatement.withComment(methodJavadocBuilder.build()));
  }

  static List<CommentStatement> createRpcMethodHeaderComment(Method method) {
    return createRpcMethodHeaderComment(method, Collections.emptyList());
  }

  static List<CommentStatement> createRpcCallableMethodHeaderComment(Method method) {
    JavaDocComment.Builder methodJavadocBuilder = JavaDocComment.builder();

    if (method.hasDescription()) {
      methodJavadocBuilder.addComment(method.description());
    }

    methodJavadocBuilder.addParagraph(METHOD_DESCRIPTION_SAMPLE_CODE_SUMMARY_STRING);
    // TODO(summerji): Add sample code here.

    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_METHOD_COMMENT,
        CommentStatement.withComment(methodJavadocBuilder.build()));
  }

  private static CommentStatement toSimpleComment(String comment) {
    return CommentStatement.withComment(JavaDocComment.withComment(comment));
  }
}
