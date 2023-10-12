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
import com.google.api.generator.gapic.composer.utils.ClassNames;
import com.google.api.generator.gapic.composer.utils.CommentFormatter;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceClientCommentComposer {
  // Tokens.
  private static final String EMPTY_STRING = "";
  private static final String API_EXCEPTION_TYPE_NAME = "com.google.api.gax.rpc.ApiException";
  private static final String EXCEPTION_CONDITION = "if the remote call fails";

  // Constants.
  private static final String SERVICE_DESCRIPTION_INTRO_STRING =
      "This class provides the ability to make remote calls to the backing service through method "
          + "calls that map to API methods. Sample code to get started:";
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
  private static final String SERVICE_DESCRIPTION_TRANSPORT_SUMMARY_STRING =
      "To use %s transport (instead of %s) for sending and receiving requests over the wire:";

  private static final String SERVICE_DESCRIPTION_SAMPLE_REFERENCE_STRING =
      "Please refer to the GitHub repository's samples for more quickstart code snippets.";

  private static final String METHOD_DESCRIPTION_SAMPLE_CODE_SUMMARY_STRING = "Sample code:";

  // Patterns.
  private static final String CREATE_METHOD_STUB_ARG_PATTERN =
      "Constructs an instance of %s, using the given stub for making calls. This is for"
          + " advanced usage - prefer using create(%s).";

  private static final String SERVICE_DESCRIPTION_CLOSE_PATTERN =
      "Note: close() needs to be called on the %s object to clean up resources such as "
          + "threads. In the example above, try-with-resources is used, which automatically calls "
          + "close().";

  private static final String SERVICE_DESCRIPTION_CUSTOMIZE_SUMMARY_PATTERN =
      "This class can be customized by passing in a custom instance of %s to create(). For"
          + " example:";

  private static final String SERVICE_DESCRIPTION_SUMMARY_PATTERN = "Service Description: %s";

  private static final String CREATE_METHOD_NO_ARG_PATTERN =
      "Constructs an instance of %s with default settings.";

  private static final String CREATE_METHOD_SETTINGS_ARG_PATTERN =
      "Constructs an instance of %s, using the given settings. The channels are"
          + " created based  on the settings passed in, or defaults for any settings that are"
          + " not set.";

  private static final String PROTECTED_CONSTRUCTOR_SETTINGS_ARG_PATTERN =
      "Constructs an instance of %s, using the given settings. This is protected so"
          + " that it is easy to make a subclass, but otherwise, the static factory methods"
          + " should be preferred.";

  // Comments.
  public static final CommentStatement GET_OPERATIONS_CLIENT_METHOD_COMMENT =
      toSimpleComment(
          "Returns the OperationsClient that can be used to query the status of a long-running"
              + " operation returned by another API method call.");

  public static List<CommentStatement> createClassHeaderComments(
      Map<String, List<String>> methodVariantsForClientHeader,
      Service service,
      String classMethodSampleCode,
      String credentialsSampleCode,
      String endpointSampleCode,
      String transportSampleCode,
      String primaryTransport,
      String secondaryTransport) {
    JavaDocComment.Builder classHeaderJavadocBuilder = JavaDocComment.builder();
    if (service.hasDescription()) {
      String descriptionComment =
          CommentFormatter.formatAsJavaDocComment(
              service.description(), SERVICE_DESCRIPTION_SUMMARY_PATTERN);
      classHeaderJavadocBuilder = classHeaderJavadocBuilder.addUnescapedComment(descriptionComment);
    }

    // Service introduction.
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_INTRO_STRING);
    classHeaderJavadocBuilder.addSampleCode(classMethodSampleCode);

    // API surface description.
    classHeaderJavadocBuilder.addParagraph(
        String.format(
            SERVICE_DESCRIPTION_CLOSE_PATTERN, ClassNames.getServiceClientClassName(service)));

    // Build the map of methods and descriptions
    Map<String, String> mapOfMethodsAndDescriptions =
        Collections.unmodifiableMap(
            service.methods().stream()
                .collect(
                    Collectors.toMap(
                        Method::getName,
                        method -> {
                          String description = method.getDescription();
                          return description != null ? description : "";
                        })));

    // Build a list of MethodAndVariants to create the table
    List<MethodAndVariants> methodAndVariantsList = new ArrayList<>();
    for (Map.Entry<String, String> method : mapOfMethodsAndDescriptions.entrySet()) {
      MethodAndVariants methodAndVariants =
          new MethodAndVariants(
              method.getKey(),
              method.getValue(),
              methodVariantsForClientHeader.get(method.getKey()));
      methodAndVariantsList.add(methodAndVariants);
    }

    classHeaderJavadocBuilder.addUnescapedComment(createTableOfMethods(methodAndVariantsList));
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_SURFACE_CODA_STRING);

    // Formatting resource names.
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_RESOURCE_NAMES_FORMATTING_STRING);

    // Customization examples.
    classHeaderJavadocBuilder.addParagraph(
        String.format(
            SERVICE_DESCRIPTION_CUSTOMIZE_SUMMARY_PATTERN,
            ClassNames.getServiceSettingsClassName(service)));
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_CREDENTIALS_SUMMARY_STRING);
    classHeaderJavadocBuilder.addSampleCode(credentialsSampleCode);
    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_ENDPOINT_SUMMARY_STRING);
    classHeaderJavadocBuilder.addSampleCode(endpointSampleCode);
    if (transportSampleCode != null) {
      classHeaderJavadocBuilder.addParagraph(
          String.format(
              SERVICE_DESCRIPTION_TRANSPORT_SUMMARY_STRING, secondaryTransport, primaryTransport));
      classHeaderJavadocBuilder.addSampleCode(transportSampleCode);
    }

    classHeaderJavadocBuilder.addParagraph(SERVICE_DESCRIPTION_SAMPLE_REFERENCE_STRING);

    if (service.isDeprecated()) {
      classHeaderJavadocBuilder.setDeprecated(CommentComposer.DEPRECATED_CLASS_STRING);
    }

    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_CLASS_COMMENT,
        CommentStatement.withComment(classHeaderJavadocBuilder.build()));
  }

  public static CommentStatement createCreateMethodStubArgComment(
      String serviceName, TypeNode settingsType) {
    return toSimpleComment(
        String.format(
            CREATE_METHOD_STUB_ARG_PATTERN, serviceName, settingsType.reference().name()));
  }

  public static List<CommentStatement> createRpcMethodHeaderComment(
      Method method, List<MethodArgument> methodArguments, Optional<String> sampleCodeOpt) {
    JavaDocComment.Builder methodJavadocBuilder = JavaDocComment.builder();

    if (method.hasDescription()) {
      String descriptionComment =
          CommentFormatter.formatAsJavaDocComment(method.description(), null);
      methodJavadocBuilder = methodJavadocBuilder.addUnescapedComment(descriptionComment);
    }

    if (sampleCodeOpt.isPresent()) {
      methodJavadocBuilder.addParagraph(METHOD_DESCRIPTION_SAMPLE_CODE_SUMMARY_STRING);
      methodJavadocBuilder.addSampleCode(sampleCodeOpt.get());
    }

    if (methodArguments.isEmpty()) {
      methodJavadocBuilder.addParam(
          "request", "The request object containing all of the parameters for the API call.");
    } else {
      for (MethodArgument argument : methodArguments) {
        // TODO(miraleung): Remove the newline replacement when we support CommonMark.
        String description =
            argument.field().hasDescription() ? argument.field().description() : EMPTY_STRING;
        methodJavadocBuilder.addParam(JavaStyle.toLowerCamelCase(argument.name()), description);
      }
    }

    methodJavadocBuilder.setThrows(API_EXCEPTION_TYPE_NAME, EXCEPTION_CONDITION);

    if (method.isDeprecated()) {
      methodJavadocBuilder.setDeprecated(CommentComposer.DEPRECATED_METHOD_STRING);
    }

    List<CommentStatement> comments = new ArrayList<>();
    comments.add(CommentComposer.AUTO_GENERATED_METHOD_COMMENT);
    if (!methodJavadocBuilder.emptyComments()) {
      comments.add(CommentStatement.withComment(methodJavadocBuilder.build()));
    }

    return comments;
  }

  private static String createTableOfMethods(List<MethodAndVariants> methodAndVariantsList) {
    String FLATTENED_METHODS =
        "<p>\"Flattened\" method variants have the fields of the request type converted into function parameters to enable multiple ways to call the same method.</p>\n\n";
    String REQUEST_OBJECT_METHODS =
        "<p>Request object method variants only takes one parameter, a request object, which must be constructed before the call.</p>\n\n";
    String CALLABLE_METHODS =
        "<p>Callable method variants take no parameters and returns an immutable API callable object, which can be used to initiate calls to the service.</p>\n\n";
    String ASYNC_METHODS =
        "<p>Methods that return long-running operations have \"Async\" method variants that return `OperationFuture` which is used to track polling of the service.</p>\n\n";

    StringBuilder tableBuilder = new StringBuilder();
    tableBuilder
        .append("<table>\n")
        .append("   <tr>\n")
        .append("     <th>Method</th>\n")
        .append("     <th>Description</th>\n")
        .append("     <th>Method Variants</th>\n");
    for (MethodAndVariants method : methodAndVariantsList) {
      tableBuilder
          .append("   <tr>\n")
          .append("     <td>")
          .append(method.method)
          .append("</td>\n")
          .append("     <td>")
          .append(method.description)
          .append("</td>\n")
          .append("     <td>\n");
      if (method.hasRequestObjectVariants) {
        tableBuilder
            .append("     " + REQUEST_OBJECT_METHODS + "     ")
            .append("<ul>\n")
            .append("     <li>")
            .append(String.join("\n     <li>", method.requestObjectVariants))
            .append("\n")
            .append("     </ul>")
            .append("\n\n");
      }
      if (method.hasFlattenedVariants) {
        tableBuilder
            .append("     " + FLATTENED_METHODS + "     ")
            .append("<ul>\n")
            .append("     <li>")
            .append(String.join("\n     <li>", method.flattenedVariants))
            .append("\n")
            .append("     </ul>")
            .append("\n\n");
      }
      if (method.hasAsyncVariants) {
        tableBuilder
            .append("     " + ASYNC_METHODS + "     ")
            .append("<ul>\n")
            .append("     <li>")
            .append(String.join("\n     <li>", method.asyncVariants))
            .append("\n")
            .append("     </ul>")
            .append("\n\n");
      }
      if (method.hasCallableVariants) {
        tableBuilder
            .append("     " + CALLABLE_METHODS + "     ")
            .append("<ul>\n")
            .append("     <li>")
            .append(String.join("\n     <li>", method.callableVariants))
            .append("\n")
            .append("     </ul>")
            .append("\n\n");
      }
      tableBuilder.append("   </td>\n").append("   </tr>\n");
    }
    tableBuilder.append(" </table>\n");
    return tableBuilder.toString();
  }

  public static class MethodAndVariants {
    String method;
    String description;
    boolean hasFlattenedVariants = false;
    boolean hasRequestObjectVariants;
    boolean hasCallableVariants;
    boolean hasAsyncVariants;

    List<String> flattenedVariants;
    List<String> requestObjectVariants;
    List<String> callableVariants;
    List<String> asyncVariants;

    public MethodAndVariants(String method, String description, List<String> methodVariants) {
      this.method = method;
      this.description = description;
      requestObjectVariants =
          methodVariants.stream().filter(s -> s.contains("request")).collect(Collectors.toList());
      hasRequestObjectVariants = methodVariants.removeAll(requestObjectVariants);
      // hasRequestObjectVariants = methodVariants.stream().anyMatch(s -> s.contains("request"));
      callableVariants =
          methodVariants.stream().filter(s -> s.contains("Callable")).collect(Collectors.toList());
      hasCallableVariants = methodVariants.removeAll(callableVariants);
      // hasCallableVariants = methodVariants.stream().anyMatch(s -> s.contains("Callable"));
      asyncVariants =
          methodVariants.stream().filter(s -> s.contains("Async")).collect(Collectors.toList());
      hasAsyncVariants = methodVariants.removeAll(asyncVariants);
      // hasAsyncVariants = methodVariants.stream().anyMatch(s -> s.contains("Async"));
      // Whatever is remaining should just be flattened variants
      flattenedVariants = methodVariants;
      if (!flattenedVariants.isEmpty()) {
        hasFlattenedVariants = true;
      }
    }
  }

  public static List<CommentStatement> createRpcMethodHeaderComment(
      Method method, Optional<String> sampleCodeOpt) {
    return createRpcMethodHeaderComment(method, Collections.emptyList(), sampleCodeOpt);
  }

  public static CommentStatement createMethodNoArgComment(String serviceName) {
    return toSimpleComment(String.format(CREATE_METHOD_NO_ARG_PATTERN, serviceName));
  }

  public static CommentStatement createProtectedCtorSettingsArgComment(String serviceName) {
    return toSimpleComment(String.format(PROTECTED_CONSTRUCTOR_SETTINGS_ARG_PATTERN, serviceName));
  }

  public static CommentStatement createMethodSettingsArgComment(String serviceName) {
    return toSimpleComment(String.format(CREATE_METHOD_SETTINGS_ARG_PATTERN, serviceName));
  }

  public static List<CommentStatement> createRpcCallableMethodHeaderComment(
      Method method, Optional<String> sampleCodeOpt) {
    JavaDocComment.Builder methodJavadocBuilder = JavaDocComment.builder();

    if (method.hasDescription()) {
      String descriptionComment =
          CommentFormatter.formatAsJavaDocComment(method.description(), null);
      methodJavadocBuilder = methodJavadocBuilder.addUnescapedComment(descriptionComment);
    }

    methodJavadocBuilder.addParagraph(METHOD_DESCRIPTION_SAMPLE_CODE_SUMMARY_STRING);
    if (sampleCodeOpt.isPresent()) {
      methodJavadocBuilder.addSampleCode(sampleCodeOpt.get());
    }

    if (method.isDeprecated()) {
      methodJavadocBuilder.setDeprecated(CommentComposer.DEPRECATED_METHOD_STRING);
    }

    return Arrays.asList(
        CommentComposer.AUTO_GENERATED_METHOD_COMMENT,
        CommentStatement.withComment(methodJavadocBuilder.build()));
  }

  private static CommentStatement toSimpleComment(String comment) {
    return CommentStatement.withComment(JavaDocComment.withComment(comment));
  }
}
