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

import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcCallableFactory;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.rpc.BatchingCallSettings;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.OperationCallSettings;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.PagedCallSettings;
import com.google.api.gax.rpc.ServerStreamingCallSettings;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.StreamingCallSettings;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Service;
import com.google.common.base.Preconditions;
import com.google.longrunning.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class GrpcServiceCallableFactoryClassComposer implements ClassComposer {
  private static final GrpcServiceCallableFactoryClassComposer INSTANCE =
      new GrpcServiceCallableFactoryClassComposer();
  private static final String DOT = ".";

  private GrpcServiceCallableFactoryClassComposer() {}

  public static GrpcServiceCallableFactoryClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> ignore) {
    Map<String, TypeNode> types = createTypes(service);
    String className = String.format("Grpc%sCallableFactory", service.name());
    GapicClass.Kind kind = Kind.STUB;
    String pakkage = String.format("%s.stub", service.pakkage());

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setHeaderCommentStatements(
                StubCommentComposer.createGrpcServiceCallableFactoryClassHeaderComments(
                    service.name()))
            .setAnnotations(createClassAnnotations(types))
            .setImplementsTypes(createClassImplements(types))
            .setName(className)
            .setMethods(createClassMethods(types))
            .setScope(ScopeNode.PUBLIC)
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<AnnotationNode> createClassAnnotations(Map<String, TypeNode> types) {
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(types.get("Generated"))
            .setDescription("by gapic-generator")
            .build());
  }

  private static List<TypeNode> createClassImplements(Map<String, TypeNode> types) {
    return Arrays.asList(types.get("GrpcStubCallableFactory"));
  }

  private static List<MethodDefinition> createClassMethods(Map<String, TypeNode> types) {
    return Arrays.asList(
        createUnaryCallableMethod(types),
        createPagedCallableMethod(types),
        createBatchingCallableMethod(types),
        createOperationCallableMethod(types),
        createBidiStreamingCallableMethod(types),
        createServerStreamingCallableMethod(types),
        createClientStreamingCallableMethod(types));
  }

  private static MethodDefinition createUnaryCallableMethod(Map<String, TypeNode> types) {
    String methodVariantName = "Unary";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        types,
        /*methodTemplateNames=*/ methodTemplateNames,
        /*returnCallableKindName=*/ methodVariantName,
        /*returnCallableTemplateNames=*/ methodTemplateNames,
        /*methodVariantName=*/ methodVariantName,
        /*grpcCallSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()),
        /*callSettingsVariantName=*/ methodVariantName,
        /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()));
  }

  private static MethodDefinition createPagedCallableMethod(Map<String, TypeNode> types) {
    String methodVariantName = "Paged";
    String requestTemplateName = "RequestT";
    String pagedResponseTemplateName = "PagedListResponseT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames =
        Arrays.asList(requestTemplateName, responseTemplateName, pagedResponseTemplateName);
    return createGenericCallableMethod(
        types,
        /*methodTemplateNames=*/ methodTemplateNames,
        /*returnCallableKindName=*/ "Unary",
        /*returnCallableTemplateNames=*/ Arrays.asList(
            requestTemplateName, pagedResponseTemplateName),
        /*methodVariantName=*/ methodVariantName,
        /*grpcCallSettingsTemplateObjects=*/ Arrays.asList(
            requestTemplateName, responseTemplateName),
        /*callSettingsVariantName=*/ methodVariantName,
        /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()));
  }

  private static MethodDefinition createBatchingCallableMethod(Map<String, TypeNode> types) {
    String methodVariantName = "Batching";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        types,
        /*methodTemplateNames=*/ methodTemplateNames,
        /*returnCallableKindName=*/ "Unary",
        /*returnCallableTemplateNames=*/ methodTemplateNames,
        /*methodVariantName=*/ methodVariantName,
        /*grpcCallSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()),
        /*callSettingsVariantName=*/ methodVariantName,
        /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()));
  }

  private static MethodDefinition createOperationCallableMethod(Map<String, TypeNode> types) {
    String methodVariantName = "Operation";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames =
        Arrays.asList(requestTemplateName, responseTemplateName, "MetadataT");
    return createGenericCallableMethod(
        types,
        /*methodTemplateNames=*/ methodTemplateNames,
        /*returnCallableKindName=*/ methodVariantName,
        /*returnCallableTemplateNames=*/ methodTemplateNames,
        /*methodVariantName=*/ methodVariantName,
        /*grpcCallSettingsTemplateObjects=*/ Arrays.asList(
            requestTemplateName, types.get("Operation")),
        /*callSettingsVariantName=*/ methodVariantName,
        /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()));
  }

  private static MethodDefinition createBidiStreamingCallableMethod(Map<String, TypeNode> types) {
    String methodVariantName = "BidiStreaming";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        types,
        /*methodTemplateNames=*/ methodTemplateNames,
        /*returnCallableKindName=*/ methodVariantName,
        /*returnCallableTemplateNames=*/ methodTemplateNames,
        /*methodVariantName=*/ methodVariantName,
        /*grpcCallSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()),
        /*callSettingsVariantName=*/ "Streaming",
        /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()));
  }

  private static MethodDefinition createServerStreamingCallableMethod(Map<String, TypeNode> types) {
    String methodVariantName = "ServerStreaming";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        types,
        /*methodTemplateNames=*/ methodTemplateNames,
        /*returnCallableKindName=*/ methodVariantName,
        /*returnCallableTemplateNames=*/ methodTemplateNames,
        /*methodVariantName=*/ methodVariantName,
        /*grpcCallSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()),
        /*callSettingsVariantName=*/ methodVariantName,
        /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()));
  }

  private static MethodDefinition createClientStreamingCallableMethod(Map<String, TypeNode> types) {
    String methodVariantName = "ClientStreaming";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        types,
        /*methodTemplateNames=*/ methodTemplateNames,
        /*returnCallableKindName=*/ methodVariantName,
        /*returnCallableTemplateNames=*/ methodTemplateNames,
        /*methodVariantName=*/ methodVariantName,
        /*grpcCallSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()),
        /*callSettingsVariantName=*/ "Streaming",
        /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()));
  }

  private static MethodDefinition createGenericCallableMethod(
      Map<String, TypeNode> types,
      List<String> methodTemplateNames,
      String returnCallableKindName,
      List<String> returnCallableTemplateNames,
      String methodVariantName,
      List<Object> grpcCallSettingsTemplateObjects,
      String callSettingsVariantName,
      List<Object> callSettingsTemplateObjects) {

    String methodName = String.format("create%sCallable", methodVariantName);
    String callSettingsTypeName = String.format("%sCallSettings", callSettingsVariantName);
    String returnTypeName = String.format("%sCallable", returnCallableKindName);
    String grpcCallSettingsTypeName = "GrpcCallSettings";
    boolean isOperationCallable = methodVariantName.equals("Operation");

    List<VariableExpr> arguments = new ArrayList<>();
    arguments.add(
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setName("grpcCallSettings")
                    .setType(types.get(grpcCallSettingsTypeName))
                    .build())
            .setIsDecl(true)
            .setTemplateObjects(grpcCallSettingsTemplateObjects)
            .build());
    arguments.add(
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setName("callSettings")
                    .setType(types.get(callSettingsTypeName))
                    .build())
            .setIsDecl(true)
            .setTemplateObjects(callSettingsTemplateObjects)
            .build());
    arguments.add(
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setName("clientContext")
                    .setType(types.get("ClientContext"))
                    .build())
            .setIsDecl(true)
            .build());
    if (isOperationCallable) {
      arguments.add(
          VariableExpr.builder()
              .setVariable(
                  Variable.builder()
                      .setName("operationsStub")
                      .setType(types.get("OperationsStub"))
                      .build())
              .setIsDecl(true)
              .build());
    }

    String grpcCallableFactoryTypeName = "GrpcCallableFactory";
    TypeNode grpcCallableFactoryType = types.get(grpcCallableFactoryTypeName);
    Preconditions.checkNotNull(
        grpcCallableFactoryType, String.format("Type %s not found", grpcCallableFactoryTypeName));

    TypeNode returnType = types.get(returnTypeName);
    MethodInvocationExpr returnExpr =
        MethodInvocationExpr.builder()
            .setMethodName(methodName)
            .setStaticReferenceType(grpcCallableFactoryType)
            .setArguments(
                arguments.stream()
                    .map(v -> v.toBuilder().setIsDecl(false).build())
                    .collect(Collectors.toList()))
            .setReturnType(returnType)
            .build();

    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setTemplateNames(methodTemplateNames)
        .setReturnType(returnType)
        .setReturnTemplateNames(returnCallableTemplateNames)
        .setName(methodName)
        .setArguments(arguments)
        .setReturnExpr(returnExpr)
        .build();
  }

  private static Map<String, TypeNode> createTypes(Service service) {
    List<Class> concreteClazzes =
        Arrays.asList(
            // Gax-java classes.
            BatchingCallSettings.class,
            BidiStreamingCallable.class,
            ClientContext.class,
            ClientStreamingCallable.class,
            OperationCallSettings.class,
            OperationCallable.class,
            PagedCallSettings.class,
            ServerStreamingCallSettings.class,
            ServerStreamingCallable.class,
            StreamingCallSettings.class,
            UnaryCallSettings.class,
            UnaryCallable.class,
            // gax-java gRPC classes.
            GrpcCallSettings.class,
            GrpcCallableFactory.class,
            GrpcStubCallableFactory.class,
            Generated.class,
            Operation.class,
            UnsupportedOperationException.class);
    Map<String, TypeNode> types =
        concreteClazzes.stream()
            .collect(
                Collectors.toMap(
                    c -> c.getSimpleName(),
                    c -> TypeNode.withReference(ConcreteReference.withClazz(c))));

    // Vapor dependency types.
    types.put(
        "OperationsStub",
        TypeNode.withReference(
            VaporReference.builder()
                .setName("OperationsStub")
                .setPakkage("com.google.longrunning.stub")
                .build()));
    return types;
  }
}
