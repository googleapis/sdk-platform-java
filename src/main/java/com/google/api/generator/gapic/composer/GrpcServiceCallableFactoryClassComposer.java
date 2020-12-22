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
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.comment.StubCommentComposer;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.composer.utils.ClassNames;
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
    TypeStore typeStore = createTypes(service);
    String className = ClassNames.getGrpcServiceCallableFactoryClassName(service);
    GapicClass.Kind kind = Kind.STUB;
    String pakkage = String.format("%s.stub", service.pakkage());

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setHeaderCommentStatements(
                StubCommentComposer.createGrpcServiceCallableFactoryClassHeaderComments(
                    service.name()))
            .setAnnotations(createClassAnnotations(typeStore))
            .setImplementsTypes(createClassImplements(typeStore))
            .setName(className)
            .setMethods(createClassMethods(typeStore))
            .setScope(ScopeNode.PUBLIC)
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<AnnotationNode> createClassAnnotations(TypeStore typeStore) {
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(typeStore.get("Generated"))
            .setDescription("by gapic-generator")
            .build());
  }

  private static List<TypeNode> createClassImplements(TypeStore typeStore) {
    return Arrays.asList(typeStore.get("GrpcStubCallableFactory"));
  }

  private static List<MethodDefinition> createClassMethods(TypeStore typeStore) {
    return Arrays.asList(
        createUnaryCallableMethod(typeStore),
        createPagedCallableMethod(typeStore),
        createBatchingCallableMethod(typeStore),
        createOperationCallableMethod(typeStore),
        createBidiStreamingCallableMethod(typeStore),
        createServerStreamingCallableMethod(typeStore),
        createClientStreamingCallableMethod(typeStore));
  }

  private static MethodDefinition createUnaryCallableMethod(TypeStore typeStore) {
    String methodVariantName = "Unary";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        typeStore,
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

  private static MethodDefinition createPagedCallableMethod(TypeStore typeStore) {
    String methodVariantName = "Paged";
    String requestTemplateName = "RequestT";
    String pagedResponseTemplateName = "PagedListResponseT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames =
        Arrays.asList(requestTemplateName, responseTemplateName, pagedResponseTemplateName);
    return createGenericCallableMethod(
        typeStore,
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

  private static MethodDefinition createBatchingCallableMethod(TypeStore typeStore) {
    String methodVariantName = "Batching";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        typeStore,
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

  private static MethodDefinition createOperationCallableMethod(TypeStore typeStore) {
    String methodVariantName = "Operation";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames =
        Arrays.asList(requestTemplateName, responseTemplateName, "MetadataT");
    return createGenericCallableMethod(
        typeStore,
        /*methodTemplateNames=*/ methodTemplateNames,
        /*returnCallableKindName=*/ methodVariantName,
        /*returnCallableTemplateNames=*/ methodTemplateNames,
        /*methodVariantName=*/ methodVariantName,
        /*grpcCallSettingsTemplateObjects=*/ Arrays.asList(
            requestTemplateName, typeStore.get("Operation")),
        /*callSettingsVariantName=*/ methodVariantName,
        /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()));
  }

  private static MethodDefinition createBidiStreamingCallableMethod(TypeStore typeStore) {
    String methodVariantName = "BidiStreaming";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        typeStore,
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

  private static MethodDefinition createServerStreamingCallableMethod(TypeStore typeStore) {
    String methodVariantName = "ServerStreaming";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        typeStore,
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

  private static MethodDefinition createClientStreamingCallableMethod(TypeStore typeStore) {
    String methodVariantName = "ClientStreaming";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames = Arrays.asList(requestTemplateName, responseTemplateName);
    return createGenericCallableMethod(
        typeStore,
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
      TypeStore typeStore,
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
                    .setType(typeStore.get(grpcCallSettingsTypeName))
                    .build())
            .setIsDecl(true)
            .setTemplateObjects(grpcCallSettingsTemplateObjects)
            .build());
    arguments.add(
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setName("callSettings")
                    .setType(typeStore.get(callSettingsTypeName))
                    .build())
            .setIsDecl(true)
            .setTemplateObjects(callSettingsTemplateObjects)
            .build());
    arguments.add(
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setName("clientContext")
                    .setType(typeStore.get("ClientContext"))
                    .build())
            .setIsDecl(true)
            .build());
    if (isOperationCallable) {
      arguments.add(
          VariableExpr.builder()
              .setVariable(
                  Variable.builder()
                      .setName("operationsStub")
                      .setType(typeStore.get("OperationsStub"))
                      .build())
              .setIsDecl(true)
              .build());
    }

    String grpcCallableFactoryTypeName = "GrpcCallableFactory";
    TypeNode grpcCallableFactoryType = typeStore.get(grpcCallableFactoryTypeName);
    Preconditions.checkNotNull(
        grpcCallableFactoryType, String.format("Type %s not found", grpcCallableFactoryTypeName));

    TypeNode returnType = typeStore.get(returnTypeName);
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

  private static TypeStore createTypes(Service service) {
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
    TypeStore typeStore = new TypeStore(concreteClazzes);
    typeStore.put("com.google.longrunning.stub", "OperationsStub");
    return typeStore;
  }
}
