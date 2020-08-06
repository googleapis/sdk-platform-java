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

import com.google.api.core.ApiFunction;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.LongrunningOperation;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Method.Stream;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.longrunning.Operation;
import com.google.rpc.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class ServiceClientClassComposer implements ClassComposer {
  private static final ServiceClientClassComposer INSTANCE = new ServiceClientClassComposer();
  private static final String DOT = ".";

  private ServiceClientClassComposer() {}

  public static ServiceClientClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> messageTypes) {
    Map<String, TypeNode> types = createTypes(service, messageTypes);
    String className = String.format("%sClient", service.name());
    GapicClass.Kind kind = Kind.MAIN;
    String pakkage = service.pakkage();
    boolean hasLroClient = hasLroMethods(service);

    // TODO(miraleung): Comments, methods, etc.

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setFileHeader(FileHeader.createApacheLicense())
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations(types))
            .setImplementsTypes(createClassImplements(types))
            .setName(className)
            .setStatements(createFieldDeclarations(service, types, hasLroClient))
            .setMethods(createClassMethods(service, messageTypes, types, hasLroClient))
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
    return Arrays.asList(types.get("BackgroundResource"));
  }

  private static List<MethodDefinition> createClassMethods(
      Service service,
      Map<String, Message> messageTypes,
      Map<String, TypeNode> types,
      boolean hasLroClient) {
    List<MethodDefinition> methods = new ArrayList<>();
    methods.addAll(createStaticCreatorMethods(service, types));
    // TODO(miraleung): Constructors when "this" and field accessors are checked in.
    methods.addAll(createGetterMethods(service, types, hasLroClient));
    methods.addAll(createServiceMethods(service, messageTypes, types));
    methods.addAll(createBackgroundResourceMethods(types));
    return methods;
  }

  private static boolean hasLroMethods(Service service) {
    for (Method method : service.methods()) {
      if (method.hasLro()) {
        return true;
      }
    }
    return false;
  }

  private static List<Statement> createFieldDeclarations(
      Service service, Map<String, TypeNode> types, boolean hasLroClient) {
    Map<String, TypeNode> fieldNameToTypes = new HashMap<>();
    fieldNameToTypes.put("settings", types.get(String.format("%sSettings", service.name())));
    fieldNameToTypes.put("stub", types.get(String.format("%sStub", service.name())));
    if (hasLroClient) {
      fieldNameToTypes.put("operationsClient", types.get("OperationsClient"));
    }

    return fieldNameToTypes.entrySet().stream()
        .map(
            e -> {
              String varName = e.getKey();
              TypeNode varType = e.getValue();
              Variable variable = Variable.builder().setName(varName).setType(varType).build();
              VariableExpr varExpr =
                  VariableExpr.builder()
                      .setVariable(variable)
                      .setScope(ScopeNode.PRIVATE)
                      .setIsFinal(true)
                      .setIsDecl(true)
                      .build();
              return ExprStatement.withExpr(varExpr);
            })
        .collect(Collectors.toList());
  }

  private static List<MethodDefinition> createStaticCreatorMethods(
      Service service, Map<String, TypeNode> types) {
    List<MethodDefinition> methods = new ArrayList<>();
    String thisClientName = String.format("%sClient", service.name());
    String settingsName = String.format("%sSettings", service.name());

    TypeNode settingsType = types.get(settingsName);
    Preconditions.checkNotNull(settingsType, String.format("Type %s not found", settingsName));

    MethodInvocationExpr newBuilderExpr =
        MethodInvocationExpr.builder()
            .setMethodName("newBuilder")
            .setStaticReferenceType(settingsType)
            .build();
    MethodInvocationExpr buildExpr =
        MethodInvocationExpr.builder()
            .setMethodName("builder")
            .setExprReferenceExpr(newBuilderExpr)
            .build();
    MethodInvocationExpr createMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setMethodName("create")
            .setArguments(Arrays.asList(buildExpr))
            .setReturnType(types.get(thisClientName))
            .build();

    MethodDefinition createMethodOne =
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setIsFinal(true)
            .setReturnType(types.get(thisClientName))
            .setName("create")
            .setArguments(
                Arrays.asList(
                    VariableExpr.builder()
                        .setVariable(
                            Variable.builder()
                                .setName("settings")
                                .setType(types.get(settingsName))
                                .build())
                        .setIsDecl(true)
                        .build()))
            .setThrowsExceptions(Arrays.asList(types.get("IOException")))
            .setReturnExpr(createMethodInvocationExpr)
            .build();
    methods.add(createMethodOne);

    // TODO(miraleung): Add the creators that rely on NewObjectExpr when that is checked in.

    return methods;
  }

  private static List<MethodDefinition> createGetterMethods(
      Service service, Map<String, TypeNode> types, boolean hasLroClient) {
    Map<String, TypeNode> methodNameToTypes = new HashMap<>();
    methodNameToTypes.put("getSettings", types.get(String.format("%sSettings", service.name())));
    methodNameToTypes.put("getStub", types.get(String.format("%sStub", service.name())));
    if (hasLroClient) {
      methodNameToTypes.put("getOperationsClient", types.get("OperationsClient"));
    }

    return methodNameToTypes.entrySet().stream()
        .map(
            e -> {
              String methodName = e.getKey();
              TypeNode methodReturnType = e.getValue();
              String returnVariableName = JavaStyle.toLowerCamelCase(methodName.substring(3));
              return MethodDefinition.builder()
                  .setScope(ScopeNode.PUBLIC)
                  .setName(methodName)
                  .setIsFinal(!methodName.equals("getStub"))
                  .setReturnType(methodReturnType)
                  .setReturnExpr(
                      VariableExpr.builder()
                          .setVariable(
                              Variable.builder()
                                  .setName(returnVariableName)
                                  .setType(methodReturnType)
                                  .build())
                          .build())
                  .build();
            })
        .collect(Collectors.toList());
  }

  private static List<MethodDefinition> createServiceMethods(
      Service service, Map<String, Message> messageTypes, Map<String, TypeNode> types) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    for (Method method : service.methods()) {
      if (method.stream().equals(Stream.NONE) && !method.hasLro()) {
        javaMethods.addAll(createMethodVariants(method, messageTypes, types));
      }
      if (method.hasLro()) {
        javaMethods.add(createLroAsyncMethod(service.name(), method, types));
        javaMethods.add(createLroCallable(service.name(), method, types));
      }
      javaMethods.add(createCallableMethod(service.name(), method, types));
    }
    return javaMethods;
  }

  private static TypeNode parseTypeFromArgumentName(
      String argumentName, Message inputMessage, Map<String, Message> messageTypes) {
    int dotIndex = argumentName.indexOf(DOT);
    // TODO(miraleung): Fake out resource names here.
    if (dotIndex < 1) {
      Field field = inputMessage.fieldMap().get(argumentName);
      Preconditions.checkNotNull(
          field, String.format("Field %s not found, %s", argumentName, inputMessage.fieldMap()));
      return field.type();
    }
    Preconditions.checkState(
        dotIndex < argumentName.length() - 1,
        String.format(
            "Invalid argument name found: dot cannot be at the end of name %s", argumentName));
    String firstFieldName = argumentName.substring(0, dotIndex);
    String remainingArgumentName = argumentName.substring(dotIndex + 1);
    // Must be a sub-message for a type's subfield to be valid.
    Field firstField = inputMessage.fieldMap().get(firstFieldName);
    TypeNode firstFieldType = firstField.type();
    String firstFieldTypeName = firstFieldType.reference().name();
    Message firstFieldMessage = messageTypes.get(firstFieldTypeName);
    return parseTypeFromArgumentName(remainingArgumentName, firstFieldMessage, messageTypes);
  }

  private static List<MethodDefinition> createMethodVariants(
      Method method, Map<String, Message> messageTypes, Map<String, TypeNode> types) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    String methodName = JavaStyle.toLowerCamelCase(method.name());
    TypeNode methodInputType = method.inputType();
    TypeNode methodOutputType = method.outputType();
    String methodInputTypeName = methodInputType.reference().name();

    Message inputMessage = messageTypes.get(methodInputTypeName);
    Preconditions.checkNotNull(
        inputMessage, String.format("Message %s not found", methodInputTypeName));

    for (List<String> signature : method.methodSignatures()) {
      // Get the argument list.
      List<VariableExpr> arguments =
          signature.stream()
              .map(
                  str -> {
                    TypeNode argumentType =
                        parseTypeFromArgumentName(str, inputMessage, messageTypes);
                    int lastDotIndex = str.lastIndexOf(DOT);
                    String argumentName = str.substring(lastDotIndex + 1);
                    return VariableExpr.builder()
                        .setVariable(
                            Variable.builder().setName(argumentName).setType(argumentType).build())
                        .setIsDecl(true)
                        .build();
                  })
              .collect(Collectors.toList());

      // Request proto builder.
      VariableExpr requestVarExpr =
          VariableExpr.builder()
              .setVariable(Variable.builder().setName("request").setType(methodInputType).build())
              .setIsDecl(true)
              .build();

      MethodInvocationExpr newBuilderExpr =
          MethodInvocationExpr.builder()
              .setMethodName("newBuilder")
              .setStaticReferenceType(methodInputType)
              .build();
      // TODO(miraleung): Handle nested arguments and setters here.
      for (String argument : signature) {
        TypeNode argumentType = parseTypeFromArgumentName(argument, inputMessage, messageTypes);
        int lastDotIndex = argument.lastIndexOf(DOT);
        String argumentName = argument.substring(lastDotIndex + 1);
        String setterMethodName =
            String.format(
                "set%s%s", argumentName.substring(0, 1).toUpperCase(), argumentName.substring(1));

        VariableExpr argVar =
            VariableExpr.builder()
                .setVariable(Variable.builder().setName(argumentName).setType(argumentType).build())
                .build();

        newBuilderExpr =
            MethodInvocationExpr.builder()
                .setMethodName(setterMethodName)
                .setArguments(Arrays.asList(argVar))
                .setExprReferenceExpr(newBuilderExpr)
                .build();
      }

      MethodInvocationExpr builderExpr =
          MethodInvocationExpr.builder()
              .setMethodName("build")
              .setExprReferenceExpr(newBuilderExpr)
              .setReturnType(methodInputType)
              .build();
      AssignmentExpr requestAssignmentExpr =
          AssignmentExpr.builder()
              .setVariableExpr(requestVarExpr)
              .setValueExpr(builderExpr)
              .build();
      List<Statement> statements = Arrays.asList(ExprStatement.withExpr(requestAssignmentExpr));

      // Return expression.
      MethodInvocationExpr returnExpr =
          MethodInvocationExpr.builder()
              .setMethodName(methodName)
              .setArguments(Arrays.asList(requestVarExpr.toBuilder().setIsDecl(false).build()))
              .setReturnType(methodOutputType)
              .build();

      javaMethods.add(
          MethodDefinition.builder()
              .setScope(ScopeNode.PUBLIC)
              .setIsFinal(true)
              .setReturnType(methodOutputType)
              .setName(methodName)
              .setArguments(arguments)
              .setBody(statements)
              .setReturnExpr(returnExpr)
              .build());
    }

    // Finally, construct the method that accepts a request proto.
    VariableExpr requestArgVarExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("request").setType(methodInputType).build())
            .setIsDecl(true)
            .build();

    MethodInvocationExpr methodReturnExpr =
        MethodInvocationExpr.builder()
            .setMethodName(String.format("%sCallable", methodName))
            .build();
    methodReturnExpr =
        MethodInvocationExpr.builder()
            .setMethodName("call")
            .setArguments(Arrays.asList(requestArgVarExpr.toBuilder().setIsDecl(false).build()))
            .setExprReferenceExpr(methodReturnExpr)
            .setReturnType(methodOutputType)
            .build();
    javaMethods.add(
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setIsFinal(true)
            .setReturnType(methodOutputType)
            .setName(methodName)
            .setArguments(Arrays.asList(requestArgVarExpr))
            .setReturnExpr(methodReturnExpr)
            .build());

    return javaMethods;
  }

  private static MethodDefinition createLroAsyncMethod(
      String serviceName, Method method, Map<String, TypeNode> types) {
    Preconditions.checkState(
        method.hasLro(), String.format("Method %s does not have an LRO", method.name()));
    String methodName = JavaStyle.toLowerCamelCase(method.name());
    TypeNode methodInputType = method.inputType();
    TypeNode methodOutputType = method.outputType();
    String methodInputTypeName = methodInputType.reference().name();
    LongrunningOperation lro = method.lro();

    VariableExpr argumentExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("request").setType(methodInputType).build())
            .setIsDecl(true)
            .build();

    TypeNode returnType =
        TypeNode.withReference(
            types
                .get("OperationFuture")
                .reference()
                .copyAndSetGenerics(
                    Arrays.asList(lro.responseType().reference(), lro.metadataType().reference())));
    MethodInvocationExpr returnExpr =
        MethodInvocationExpr.builder()
            .setMethodName(String.format("%sOperationCallable", methodName))
            .build();
    returnExpr =
        MethodInvocationExpr.builder()
            .setMethodName("futureCall")
            .setArguments(Arrays.asList(argumentExpr.toBuilder().setIsDecl(false).build()))
            .setExprReferenceExpr(returnExpr)
            .setReturnType(returnType)
            .build();

    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setIsFinal(true)
        .setReturnType(returnType)
        .setName(String.format("%sAsync", methodName))
        .setArguments(Arrays.asList(argumentExpr))
        .setReturnExpr(returnExpr)
        .build();
  }

  private static MethodDefinition createLroCallable(
      String serviceName, Method method, Map<String, TypeNode> types) {
    return createCallableMethod(serviceName, method, types, true);
  }

  private static MethodDefinition createCallableMethod(
      String serviceName, Method method, Map<String, TypeNode> types) {
    return createCallableMethod(serviceName, method, types, false);
  }

  private static MethodDefinition createCallableMethod(
      String serviceName, Method method, Map<String, TypeNode> types, boolean isLroCallable) {
    TypeNode rawCallableReturnType = null;
    if (isLroCallable) {
      rawCallableReturnType = types.get("OperationCallable");
    } else {
      switch (method.stream()) {
        case CLIENT:
          rawCallableReturnType = types.get("ClientStreamingCallable");
          break;
        case SERVER:
          rawCallableReturnType = types.get("ServerStreamingCallable");
          break;
        case BIDI:
          rawCallableReturnType = types.get("BidiStreamingCallable");
          break;
        case NONE:
          // Fall through.
        default:
          rawCallableReturnType = types.get("UnaryCallable");
      }
    }

    // Set generics.
    TypeNode returnType =
        TypeNode.withReference(
            rawCallableReturnType
                .reference()
                .copyAndSetGenerics(
                    isLroCallable
                        ? Arrays.asList(
                            method.inputType().reference(),
                            method.lro().responseType().reference(),
                            method.lro().metadataType().reference())
                        : Arrays.asList(
                            method.inputType().reference(), method.outputType().reference())));

    String rawMethodName = JavaStyle.toLowerCamelCase(method.name());
    String methodName =
        isLroCallable
            ? String.format("%sOperationCallabke", rawMethodName)
            : String.format("%sCallable", rawMethodName);
    TypeNode stubType = types.get(String.format("%sStub", serviceName));
    MethodInvocationExpr returnExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                VariableExpr.builder()
                    .setVariable(Variable.builder().setName("stub").setType(stubType).build())
                    .build())
            .setMethodName(methodName)
            .setReturnType(returnType)
            .build();

    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setIsFinal(true)
        .setName(methodName)
        .setReturnType(returnType)
        .setReturnExpr(returnExpr)
        .build();
  }

  private static List<MethodDefinition> createBackgroundResourceMethods(
      Map<String, TypeNode> types) {
    List<MethodDefinition> methods = new ArrayList<>();

    // TODO(miraleung): Fill out the body.
    MethodDefinition closeMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setName("close")
            .build();
    methods.add(closeMethod);

    // TODO(miraleung): Fill out the body.
    MethodDefinition shutdownMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setName("shutdown")
            .build();
    methods.add(shutdownMethod);

    // TODO(miraleung): Fill out the body.
    Expr placeholderReturnExpr =
        ValueExpr.withValue(
            PrimitiveValue.builder().setType(TypeNode.BOOLEAN).setValue("false").build());
    MethodDefinition isShutdownMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.BOOLEAN)
            .setName("isShutdown")
            .setReturnExpr(placeholderReturnExpr)
            .build();
    methods.add(isShutdownMethod);

    // TODO(miraleung): Fill out the body.
    MethodDefinition isTerminatedMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.BOOLEAN)
            .setName("isTerminated")
            .setReturnExpr(placeholderReturnExpr)
            .build();
    methods.add(isTerminatedMethod);

    // TODO(miraleung): Fill out the body.
    MethodDefinition shutdownNowMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setName("shutdownNow")
            .build();
    methods.add(shutdownNowMethod);

    // TODO(miraleung): Fill out the body.
    List<VariableExpr> arguments =
        Arrays.asList(
            VariableExpr.builder()
                .setVariable(createVariable("duration", TypeNode.LONG))
                .setIsDecl(true)
                .build(),
            VariableExpr.builder()
                .setVariable(createVariable("unit", types.get("TimeUnit")))
                .setIsDecl(true)
                .build());

    MethodDefinition awaitTerminationMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.BOOLEAN)
            .setName("awaitTermination")
            .setReturnExpr(placeholderReturnExpr)
            .setArguments(arguments)
            .setThrowsExceptions(Arrays.asList(types.get("InterruptedException")))
            .build();
    methods.add(awaitTerminationMethod);

    return methods;
  }

  private static Map<String, TypeNode> createTypes(
      Service service, Map<String, Message> messageTypes) {
    Map<String, TypeNode> types = new HashMap<>();
    types.putAll(createConcreteTypes());
    types.putAll(createVaporTypes(service));
    types.putAll(createProtoMessageTypes(service.pakkage(), messageTypes));
    return types;
  }

  private static Map<String, TypeNode> createConcreteTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            ApiFunction.class,
            ApiFuture.class,
            ApiFutures.class,
            BackgroundResource.class,
            BidiStreamingCallable.class,
            ClientStreamingCallable.class,
            Generated.class,
            InterruptedException.class,
            IOException.class,
            MoreExecutors.class,
            Operation.class,
            OperationFuture.class,
            OperationCallable.class,
            ServerStreamingCallable.class,
            Status.class,
            TimeUnit.class,
            UnaryCallable.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }

  private static Map<String, TypeNode> createVaporTypes(Service service) {
    // Client stub types.
    Map<String, TypeNode> types =
        Arrays.asList("%sStub", "%sStubSettings").stream()
            .collect(
                Collectors.toMap(
                    t -> String.format(t, service.name()),
                    t ->
                        TypeNode.withReference(
                            VaporReference.builder()
                                .setName(String.format(t, service.name()))
                                .setPakkage(String.format("%s.stub", service.pakkage()))
                                .build())));
    // Client ServiceClient and ServiceSettings types.
    types.putAll(
        Arrays.asList("%sClient", "%sSettings").stream()
            .collect(
                Collectors.toMap(
                    t -> String.format(t, service.name()),
                    t ->
                        TypeNode.withReference(
                            VaporReference.builder()
                                .setName(String.format(t, service.name()))
                                .setPakkage(service.pakkage())
                                .build()))));
    // LRO Gapic-generated types.
    types.put(
        "OperationsClient",
        TypeNode.withReference(
            VaporReference.builder()
                .setName("OperationsClient")
                .setPakkage("com.google.longrunning")
                .build()));
    return types;
  }

  private static Map<String, TypeNode> createProtoMessageTypes(
      String pakkage, Map<String, Message> messageTypes) {
    // Vapor message types.
    return messageTypes.entrySet().stream()
        .collect(
            Collectors.toMap(
                e -> e.getValue().name(),
                e ->
                    TypeNode.withReference(
                        VaporReference.builder()
                            .setName(e.getValue().name())
                            .setPakkage(pakkage)
                            .build())));
  }

  private static Variable createVariable(String name, TypeNode type) {
    return Variable.builder().setName(name).setType(type).build();
  }
}
