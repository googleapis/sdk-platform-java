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

import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.ThrowExpr;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.longrunning.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class ServiceStubClassComposer implements ClassComposer {
  private static final ServiceStubClassComposer INSTANCE = new ServiceStubClassComposer();
  private static final String DOT = ".";

  private ServiceStubClassComposer() {}

  public static ServiceStubClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> messageTypes) {
    Map<String, TypeNode> types = createTypes(service, messageTypes);
    String className = String.format("%sStub", service.name());
    GapicClass.Kind kind = Kind.STUB;
    String pakkage = String.format("%s.stub", service.pakkage());

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations(types))
            .setIsAbstract(true)
            .setImplementsTypes(createClassImplements(types))
            .setName(className)
            .setMethods(createClassMethods(service, messageTypes, types))
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
      Service service, Map<String, Message> messageTypes, Map<String, TypeNode> types) {
    boolean hasLroClient = hasLroMethods(service);
    List<MethodDefinition> methods = new ArrayList<>();
    if (hasLroClient) {
      methods.add(createOperationsStubGetter(types));
    }
    methods.addAll(createCallableGetters(service, messageTypes, types));
    methods.addAll(createBackgroundResourceMethodOverrides());
    return methods;
  }

  private static List<MethodDefinition> createCallableGetters(
      Service service, Map<String, Message> messageTypes, Map<String, TypeNode> types) {
    // Use a traditional for-loop since the output cardinality is not necessarily 1:1 with that of
    // service.methods().
    List<MethodDefinition> javaMethods = new ArrayList<>();
    for (Method method : service.methods()) {
      if (method.hasLro()) {
        javaMethods.add(createOperationCallableGetter(method, types));
      }
      javaMethods.add(createCallableGetter(method, types));
    }
    return javaMethods;
  }

  private static MethodDefinition createOperationCallableGetter(
      Method method, Map<String, TypeNode> types) {
    return createCallableGetterHelper(method, types, true);
  }

  private static MethodDefinition createCallableGetter(Method method, Map<String, TypeNode> types) {
    return createCallableGetterHelper(method, types, false);
  }

  private static MethodDefinition createCallableGetterHelper(
      Method method, Map<String, TypeNode> types, boolean isLroCallable) {
    TypeNode returnType;
    switch (method.stream()) {
      case CLIENT:
        returnType = types.get("ClientStreamingCallable");
        break;
      case SERVER:
        returnType = types.get("ServerStreamingCallable");
        break;
      case BIDI:
        returnType = types.get("BidiStreamingCallable");
        break;
      case NONE:
        // Fall through.
      default:
        returnType = types.get(isLroCallable ? "OperationCallable" : "UnaryCallable");
    }

    String methodName =
        String.format(
            "%s%sCallable",
            JavaStyle.toLowerCamelCase(method.name()), (isLroCallable ? "Operation" : ""));
    List<Reference> genericRefs = new ArrayList<>();
    genericRefs.add(method.inputType().reference());
    if (method.hasLro()) {
      genericRefs.add(method.lro().responseType().reference());
      genericRefs.add(method.lro().metadataType().reference());
    } else {
      genericRefs.add(method.outputType().reference());
    }
    returnType = TypeNode.withReference(returnType.reference().copyAndSetGenerics(genericRefs));

    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(returnType)
        .setName(methodName)
        .setBody(createThrowUOEBody(methodName, types))
        .build();
  }

  private static MethodDefinition createOperationsStubGetter(Map<String, TypeNode> types) {
    String methodName = "getOperationsStub";
    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(types.get("OperationsStub"))
        .setName(methodName)
        .setBody(createThrowUOEBody(methodName, types))
        .build();
  }

  private static List<MethodDefinition> createBackgroundResourceMethodOverrides() {
    MethodDefinition closeMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setIsAbstract(true)
            .setReturnType(TypeNode.VOID)
            .setName("close")
            .build();
    return Arrays.asList(closeMethod);
  }

  private static boolean hasLroMethods(Service service) {
    for (Method method : service.methods()) {
      if (method.hasLro()) {
        return true;
      }
    }
    return false;
  }

  private static Map<String, TypeNode> createTypes(
      Service service, Map<String, Message> messageTypes) {
    List<Class> concreteClazzes =
        Arrays.asList(
            BackgroundResource.class,
            BidiStreamingCallable.class,
            ClientStreamingCallable.class,
            Generated.class,
            Operation.class,
            OperationCallable.class,
            ServerStreamingCallable.class,
            UnaryCallable.class,
            UnsupportedOperationException.class);
    Map<String, TypeNode> types =
        concreteClazzes.stream()
            .collect(
                Collectors.toMap(
                    c -> c.getSimpleName(),
                    c -> TypeNode.withReference(ConcreteReference.withClazz(c))));

    // Vapor message types.
    types.putAll(
        messageTypes.entrySet().stream()
            .collect(
                Collectors.toMap(
                    e -> e.getValue().name(),
                    e ->
                        TypeNode.withReference(
                            VaporReference.builder()
                                .setName(e.getValue().name())
                                .setPakkage(service.pakkage())
                                .build()))));

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

  private static List<Statement> createThrowUOEBody(
      String methodName, Map<String, TypeNode> types) {
    return Arrays.asList(
        ExprStatement.withExpr(
            ThrowExpr.builder()
                .setType(types.get("UnsupportedOperationException"))
                .setMessage(String.format("Not implemented: %s()", methodName))
                .build()));
  }
}
