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

package com.google.api.generator.gapic.composer;

import static junit.framework.Assert.assertEquals;

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.model.Transport;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class SpringAutoConfigClassComposerTest {
  private ServiceDescriptor echoService;
  private FileDescriptor echoFileDescriptor;

  @Before
  public void setUp() {
    echoFileDescriptor = EchoOuterClass.getDescriptor();
    echoService = echoFileDescriptor.getServices().get(0);
    assertEquals(echoService.getName(), "Echo");
  }

  @Test
  public void writeMethodDefinition_constructor() {
    // To be deleted, please ignore: practice AST grammar here.
    JavaWriterVisitor writerVisitor = new JavaWriterVisitor();

    // header
    List<CommentStatement> fileHeader =
        Arrays.asList(CommentStatement.withComment(BlockComment.withComment("Apache License")));
    // annotation types
    TypeNode beanAnnotationType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("Bean")
                .setPakkage("org.springframework.context.annotation")
                .build());
    TypeNode conditionalOnMissingBeanType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ConditionalOnMissingBean")
                .setPakkage("org.springframework.boot.autoconfigure.condition")
                .build());
    TypeNode conditionalOnPropertyType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ConditionalOnProperty")
                .setPakkage("org.springframework.boot.autoconfigure.condition")
                .build());
    // AnnotationNode description only accepts String for now. need to extend to params
    AnnotationNode conditionalOnPropertyNode =
        AnnotationNode.builder()
            .setType(conditionalOnPropertyType)
            .setDescription("value = \"spring.cloud.gcp.language.enabled\", matchIfMissing = false")
            .build();
    // client type
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("LanguageServiceClient")
                .setPakkage("com.google.cloud.language.v1")
                .build());
    // build expressions
    MethodInvocationExpr lhsExpr =
        MethodInvocationExpr.builder()
            .setMethodName("create")
            .setStaticReferenceType(clientType)
            .setReturnType(clientType)
            .build();

    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("client").setType(clientType).build())
            .setIsDecl(true)
            .build();

    // VariableExpr returnArg = VariableExpr.builder()
    //     .setVariable(createVariable("client", clientType))
    //     .build();

    MethodInvocationExpr expr =
        MethodInvocationExpr.builder()
            .setMethodName("languageServiceClientCreate")
            .setReturnType(clientType)
            .build();

    // AssignmentExpr assignmentExpr = AssignmentExpr.builder().setVariableExpr(variableExpr)
    //     .setValueExpr(expr).build();

    MethodDefinition beanMethod =
        MethodDefinition.builder()
            .setName("languageServiceClient")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(clientType)
            .setAnnotations(
                Arrays.asList(
                    AnnotationNode.withType(beanAnnotationType),
                    AnnotationNode.withType(conditionalOnMissingBeanType)))
            .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
            .setReturnExpr(lhsExpr)
            // .setBody(
            //     Arrays.asList(ExprStatement.withExpr(assignmentExpr)))
            .build();

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setFileHeader(fileHeader)
            .setPackageString("com.google.example.library.v1")
            .setName("LibraryServiceStub")
            .setScope(ScopeNode.PUBLIC)
            .setMethods(Arrays.asList(beanMethod))
            .setAnnotations(Arrays.asList(conditionalOnPropertyNode))
            .build();

    classDef.accept(writerVisitor);
    System.out.println("HERE STARTS OUTPUT: ");
    System.out.println(writerVisitor.write());
    // Assert.assertEquals("public LibrarySettings() {\n}\n\n", writerVisitor.write());
  }

  @Test
  public void generateAutoConfigClasses() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);

    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    List<Service> services =
        Parser.parseService(
            echoFileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    GapicContext context =
        GapicContext.builder()
            .setMessages(messageTypes)
            .setResourceNames(resourceNames)
            .setServices(services)
            .setHelperResourceNames(outputResourceNames)
            .setTransport(Transport.GRPC)
            .build();

    Service echoProtoService = services.get(0);
    GapicClass clazz = SpringAutoConfigClassComposer.instance().generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  private static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1.demo;\n"
          + "\n"
          + "import com.google.showcase.v1beta1.EchoClient;\n"
          + "import java.io.IOException;\n"
          + "import javax.annotation.Generated;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;\n"
          + "import org.springframework.context.annotation.Bean;\n"
          + "\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "@ConditionalOnProperty(\"value = \\\"spring.cloud.gcp.language.enabled\\\", matchIfMissing = false\")\n"
          + "public class EchoDemo {\n"
          + "\n"
          + "  @Bean\n"
          + "  @ConditionalOnMissingBean\n"
          + "  public EchoClient echoClient() throws IOException {\n"
          + "    return EchoClient.create();\n"
          + "  }\n"
          + "}\n";
}
