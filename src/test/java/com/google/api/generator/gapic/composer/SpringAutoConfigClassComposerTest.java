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
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.LambdaExpr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.RelationalOperationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.ThisObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

    // members
    TypeNode credentialsProvider =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("CredentialsProvider")
                .setPakkage("com.google.api.gax.core")
                .build());
    TypeNode thisClassType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("LibraryServiceStub")
                .setPakkage("com.google.example.library.v1")
                .build());
    TypeNode clientProperties =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("LanguageProperties")
                .setPakkage("com.google.example.library.v1")
                .build());
    // import com.google.cloud.spring.core.GcpProjectIdProvider;

    TypeNode gcpProjectIdProvider =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("GcpProjectIdProvider")
                .setPakkage("com.google.cloud.spring.core")
                .build());
    // TypeNode thisClassType =
    // TypeNode thisClassType = typeStore.get(ClassNames.getServiceSettingsClassName(service));

    Variable variable =
        Variable.builder().setName("credentialsProvider").setType(credentialsProvider).build();
    VariableExpr varExpr =
        VariableExpr.builder()
            .setVariable(variable)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();
    ExprStatement statement1 = ExprStatement.withExpr(varExpr);

    // private final LanguageProperties clientProperties;
    Variable propertiesVar =
        Variable.builder().setName("clientProperties").setType(clientProperties).build();
    VariableExpr propertiesVarExpr =
        VariableExpr.builder()
            .setVariable(propertiesVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();
    ExprStatement statement2 = ExprStatement.withExpr(propertiesVarExpr);

    // private final GcpProjectIdProvider projectIdProvider;
    Variable projectIdProviderVar =
        Variable.builder().setName("projectIdProvider").setType(gcpProjectIdProvider).build();
    VariableExpr projectIdProviderVarExpr =
        VariableExpr.builder()
            .setVariable(projectIdProviderVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();
    ExprStatement statement3 = ExprStatement.withExpr(projectIdProviderVarExpr);

    /// constructor
    VariableExpr credentialsProviderBuilderVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("coreCredentialsProvider")
                .setType(credentialsProvider)
                .build());

    VariableExpr coreProjectIdProviderVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("coreProjectIdProvider")
                .setType(gcpProjectIdProvider)
                .build());

    List<Expr> ctorAssignmentExprs = new ArrayList<>();
    Expr thisExpr = ValueExpr.withValue(ThisObjectValue.withType(thisClassType));
    ctorAssignmentExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.withVariable(projectIdProviderVar)
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(coreProjectIdProviderVarExpr)
            .build());

    /**
     * if (clientProperties.getProjectId() != null) { this.projectIdProvider =
     * clientProperties::getProjectId; } else { this.projectIdProvider = coreProjectIdProvider; }
     */

    // expr: clientProperties.getProjectId() != null
    MethodInvocationExpr getProjectIdExpr =
        MethodInvocationExpr.builder()
            .setMethodName("getProjectId")
            .setExprReferenceExpr(VariableExpr.withVariable(propertiesVar).toBuilder().build())
            // .setStaticReferenceType(clientType)
            .setReturnType(credentialsProvider) // fake it
            .build();
    RelationalOperationExpr notEqualSentence =
        RelationalOperationExpr.notEqualToWithExprs(getProjectIdExpr, ValueExpr.createNullExpr());

    // this.projectIdProvider = () -> clientProperties.getProjectId();
    LambdaExpr lambdaExpr =
        LambdaExpr.builder().setReturnExpr(getProjectIdExpr).setType(gcpProjectIdProvider).build();

    // this.projectIdProvider = () -> clientProperties.getProjectId();
    AssignmentExpr projectIdProviderAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.withVariable(projectIdProviderVar)
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(lambdaExpr)
            .build();

    IfStatement ifStatement =
        IfStatement.builder()
            .setConditionExpr(notEqualSentence)
            .setBody(Arrays.asList(ExprStatement.withExpr(projectIdProviderAssignExpr)))
            .setElseBody(
                ctorAssignmentExprs.stream()
                    .map(e -> ExprStatement.withExpr(e))
                    .collect(Collectors.toList()))
            .build();

    MethodDefinition constructor =
        MethodDefinition.constructorBuilder()
            .setScope(ScopeNode.PROTECTED)
            .setReturnType(thisClassType)
            .setArguments(
                Arrays.asList(
                    credentialsProviderBuilderVarExpr.toBuilder().setIsDecl(true).build(),
                    coreProjectIdProviderVarExpr.toBuilder().setIsDecl(true).build()))
            // .setThrowsExceptions(Arrays.asList(FIXED_TYPESTORE.get("IOException")))
            .setBody(Arrays.asList(ifStatement))
            .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
            // ctorAssignmentExprs.stream()
            //     .map(e -> ExprStatement.withExpr(e))
            //     .collect(Collectors.toList()))
            .build();
    /// end constructor

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setFileHeader(fileHeader)
            .setPackageString("com.google.example.library.v1")
            .setName("LibraryServiceStub")
            .setScope(ScopeNode.PUBLIC)
            .setStatements(Arrays.asList(statement1, statement2, statement3))
            .setMethods(Arrays.asList(constructor, beanMethod))
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
    System.out.println(visitor.write());
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  protected static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1.spring;\n"
          + "\n"
          + "import com.google.api.gax.core.CredentialsProvider;\n"
          + "import com.google.cloud.spring.core.Credentials;\n"
          + "import com.google.cloud.spring.core.DefaultCredentialsProvider;\n"
          + "import com.google.cloud.spring.core.GcpProjectIdProvider;\n"
          + "import com.google.showcase.v1beta1.EchoClient;\n"
          + "import java.io.IOException;\n"
          + "import javax.annotation.Generated;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;\n"
          + "import org.springframework.context.annotation.Bean;\n"
          + "\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "@ConditionalOnProperty(\"value = \\\"spring.cloud.gcp.language.enabled\\\", matchIfMissing = false\")\n"
          + "@ConditionalOnClass(\"value = Echo\")\n"
          + "public class EchoSpringAutoConfiguration {\n"
          + "  private final CredentialsProvider credentialsProvider;\n"
          + "  private final EchoProperties clientProperties;\n"
          + "  private final GcpProjectIdProvider projectIdProvider;\n"
          + "\n"
          + "  protected EchoSpringAutoConfiguration(\n"
          + "      CredentialsProvider coreCredentialsProvider,\n"
          + "      GcpProjectIdProvider coreProjectIdProvider,\n"
          + "      EchoProperties clientProperties)\n"
          + "      throws IOException {\n"
          + "    this.clientProperties = clientProperties;\n"
          + "    if (clientProperties.getCredentials().hasKey()) {\n"
          + "      this.credentialsProvider =\n"
          + "          ((CredentialsProvider) new DefaultCredentialsProvider(clientProperties));\n"
          + "    } else {\n"
          + "      this.credentialsProvider = ((CredentialsProvider) coreProjectIdProvider);\n"
          + "    }\n"
          + "    if (clientProperties.getProjectId() != null) {\n"
          + "      this.projectIdProvider = () -> clientProperties.getProjectId();\n"
          + "    } else {\n"
          + "      this.projectIdProvider = coreProjectIdProvider;\n"
          + "    }\n"
          + "  }\n"
          + "\n"
          + "  @Bean\n"
          + "  @ConditionalOnMissingBean\n"
          + "  public EchoClient echoClient() throws IOException {\n"
          + "    return EchoClient.create();\n"
          + "  }\n"
          + "}\n";
}
