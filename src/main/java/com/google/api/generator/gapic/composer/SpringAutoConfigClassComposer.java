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

import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.composer.common.ClassComposer;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.common.base.CaseFormat;
import com.google.longrunning.Operation;
import com.google.longrunning.stub.GrpcOperationsStub;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class SpringAutoConfigClassComposer implements ClassComposer {
  private static final String CLASS_NAME_PATTERN = "%sDemo";
  private static final String OPERATIONS_STUB_MEMBER_NAME = "operationsStub";
  private static final String BACKGROUND_RESOURCES_MEMBER_NAME = "backgroundResources";

  private static final SpringAutoConfigClassComposer INSTANCE = new SpringAutoConfigClassComposer();

  private static final Map<String, TypeNode> staticTypes = createStaticTypes();

  private SpringAutoConfigClassComposer() {}

  public static SpringAutoConfigClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(GapicContext ignore, Service service) {
    String packageName = service.pakkage() + ".demo";
    Map<String, TypeNode> types = createDynamicTypes(service, packageName);
    String className = getThisClassName(service.name());
    GapicClass.Kind kind = Kind.MAIN;

    // header -- not used, add later
    List<CommentStatement> fileHeader =
        Arrays.asList(CommentStatement.withComment(BlockComment.withComment("Apache License")));

    ClassDefinition classDef =
        ClassDefinition.builder()
            // .setFileHeader(fileHeader)
            .setPackageString(packageName)
            .setName(className)
            .setScope(ScopeNode.PUBLIC)
            .setMethods(Arrays.asList(createBeanMethod(service)))
            .setAnnotations(createClassAnnotations(service.name()))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<AnnotationNode> createClassAnnotations(String serviceName) {
    TypeNode conditionalOnPropertyType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ConditionalOnProperty")
                .setPakkage("org.springframework.boot.autoconfigure.condition")
                .build());
    TypeNode conditionalOnClass =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ConditionalOnClass")
                .setPakkage("org.springframework.boot.autoconfigure.condition")
                .build());

    // TODO: AnnotationNode description only accepts String for now. need to extend to params
    // and classes.
    AnnotationNode conditionalOnPropertyNode =
        AnnotationNode.builder()
            .setType(conditionalOnPropertyType)
            .setDescription("value = \"spring.cloud.gcp.language.enabled\", matchIfMissing = false")
            .build();
    AnnotationNode conditionalOnClassNode =
        AnnotationNode.builder()
            .setType(conditionalOnClass)
            .setDescription("value = " + serviceName) // need to produce XXX.class
            .build();
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(staticTypes.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build(),
        conditionalOnPropertyNode,
        conditionalOnClassNode);
  }

  private static MethodDefinition createBeanMethod(Service service) {
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
    // client type
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(service.name() + "Client")
                .setPakkage(service.pakkage())
                .build());
    // build expressions
    MethodInvocationExpr lhsExpr =
        MethodInvocationExpr.builder()
            // TODO: might need to use newBuilder().build() if options needed.
            // read more in client composer:
            // src/main/java/com/google/api/generator/gapic/composer/common/AbstractServiceClientClassComposer.java#L277-L292
            .setMethodName("create")
            .setStaticReferenceType(clientType)
            .setReturnType(clientType)
            .build();

    String methodName =
        CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, service.name()) + "Client";
    return MethodDefinition.builder()
        .setName(methodName)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(clientType)
        .setAnnotations(
            Arrays.asList(
                AnnotationNode.withType(beanAnnotationType),
                AnnotationNode.withType(conditionalOnMissingBeanType)))
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
        .setReturnExpr(lhsExpr)
        .build();
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            BackgroundResource.class,
            BackgroundResourceAggregation.class,
            BidiStreamingCallable.class,
            ClientContext.class,
            ClientStreamingCallable.class,
            Generated.class,
            GrpcCallSettings.class,
            GrpcOperationsStub.class,
            GrpcStubCallableFactory.class,
            InterruptedException.class,
            IOException.class,
            MethodDescriptor.class,
            Operation.class,
            OperationCallable.class,
            ProtoUtils.class,
            ServerStreamingCallable.class,
            TimeUnit.class,
            UnaryCallable.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }

  private static Map<String, TypeNode> createDynamicTypes(Service service, String stubPakkage) {
    return Arrays.asList(CLASS_NAME_PATTERN).stream()
        .collect(
            Collectors.toMap(
                p -> String.format(p, service.name()),
                p ->
                    TypeNode.withReference(
                        VaporReference.builder()
                            .setName(String.format(p, service.name()))
                            .setPakkage(stubPakkage)
                            .build())));
  }

  private static String getThisClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }
}
