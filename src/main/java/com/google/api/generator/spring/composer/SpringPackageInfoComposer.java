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

package com.google.api.generator.spring.composer;

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.PackageInfoDefinition;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.composer.utils.ClassNames;
import com.google.api.generator.gapic.composer.utils.CommentFormatter;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicPackageInfo;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.spring.utils.Utils;
import com.google.common.base.Preconditions;
import javax.annotation.Generated;

public class SpringPackageInfoComposer {
  private static final String DIVIDER = "=======================";
  private static final String PACKAGE_INFO_TITLE_PATTERN =
      "Spring Boot auto-configurations for %s.";
  private static final String PACKAGE_INFO_DESCRIPTION =
      "The services with auto-configured service client beans provided are listed below.";
  private static final String SERVICE_DESCRIPTION_HEADER_PATTERN = "Service Description: %s";
  private static final String SPRING_USAGE_PATTERN =
      "You can inject an auto-configured service client bean to your Spring Boot application "
          + "with annotation-based field injection (e.g. {@code {@literal @}Autowired private %s %s;}) "
          + "or constructor injection, as shown in "
          + "https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.spring-beans-and-dependency-injection.";

  // TODO (emmwang): Is there value in a more verbose sample snippet, like the following?
  // @Component
  // public class MyComponent {
  //   @Autowired private EchoClient autoClient;
  // }
  //
  // @Component
  // public class MyComponent {
  //   private EchoClient autoClient;
  //
  //   public MyService(EchoClient echoClient) {
  //     this.autoClient = echoClient;
  //   }
  // }

  public static GapicPackageInfo generatePackageInfo(GapicContext context) {
    Preconditions.checkState(!context.services().isEmpty(), "No services found to generate");
    PackageInfoDefinition packageInfo =
        PackageInfoDefinition.builder()
            .setPakkage(Utils.getSpringPackageName(Utils.getPackageName(context)))
            .setHeaderCommentStatements(createPackageInfoJavadoc(context))
            .setAnnotations(
                AnnotationNode.builder()
                    .setType(TypeNode.withReference(ConcreteReference.withClazz(Generated.class)))
                    .setDescription("by gapic-generator-java")
                    .build())
            .build();
    return GapicPackageInfo.with(packageInfo);
  }

  private static CommentStatement createPackageInfoJavadoc(GapicContext context) {
    JavaDocComment.Builder javaDocCommentBuilder = JavaDocComment.builder();
    javaDocCommentBuilder =
        javaDocCommentBuilder.addComment(
            String.format(PACKAGE_INFO_TITLE_PATTERN, Utils.getLibName(context)));

    String firstClientName = ClassNames.getServiceClientClassName(context.services().get(0));
    javaDocCommentBuilder =
        javaDocCommentBuilder
            .addParagraph("")
            .addUnescapedComment(
                String.format(SPRING_USAGE_PATTERN, firstClientName, "auto" + firstClientName));

    javaDocCommentBuilder = javaDocCommentBuilder.addParagraph(PACKAGE_INFO_DESCRIPTION);

    for (Service service : context.services()) {
      String javaClientName = ClassNames.getServiceClientClassName(service);
      javaDocCommentBuilder =
          javaDocCommentBuilder.addParagraph(
              String.format("%s %s %s", DIVIDER, javaClientName, DIVIDER));

      // TODO (emmwang): decide whether to keep (and format) or remove service description
      if (service.hasDescription()) {
        //        javaDocCommentBuilder.addParagraph(
        //            String.format(SERVICE_DESCRIPTION_HEADER_PATTERN, service.description()));

        // An example where this is not sufficient: Secret Manager Service
        // Description contain bulleted items (*) that should be converted to list in Javadoc

        javaDocCommentBuilder =
            CommentFormatter.formatCommentForJavaDoc(
                service.description(), javaDocCommentBuilder, SERVICE_DESCRIPTION_HEADER_PATTERN);
      }
    }

    return CommentStatement.withComment(javaDocCommentBuilder.build());
  }
}
