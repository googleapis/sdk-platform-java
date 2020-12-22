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
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.ThrowExpr;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.composer.comment.StubCommentComposer;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.composer.utils.ClassNames;
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
  private static final String PAGED_RESPONSE_TYPE_NAME_PATTERN = "%sPagedResponse";

  private ServiceStubClassComposer() {}

  public static ServiceStubClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> messageTypes) {
    TypeStore typeStore = createTypes(service, messageTypes);
    String className = ClassNames.getServiceStubClassName(service);
    GapicClass.Kind kind = Kind.STUB;
    String pakkage = String.format("%s.stub", service.pakkage());

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setHeaderCommentStatements(
                StubCommentComposer.createServiceStubClassHeaderComments(service.name()))
            .setAnnotations(createClassAnnotations(typeStore))
            .setIsAbstract(true)
            .setImplementsTypes(createClassImplements(typeStore))
            .setName(className)
            .setMethods(createClassMethods(service, messageTypes, typeStore))
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
    return Arrays.asList(typeStore.get("BackgroundResource"));
  }

  private static List<MethodDefinition> createClassMethods(
      Service service, Map<String, Message> messageTypes, TypeStore typeStore) {
    boolean hasLroClient = hasLroMethods(service);
    List<MethodDefinition> methods = new ArrayList<>();
    if (hasLroClient) {
      methods.add(createOperationsStubGetter(typeStore));
    }
    methods.addAll(createCallableGetters(service, messageTypes, typeStore));
    methods.addAll(createBackgroundResourceMethodOverrides());
    return methods;
  }

  private static List<MethodDefinition> createCallableGetters(
      Service service, Map<String, Message> messageTypes, TypeStore typeStore) {
    // Use a traditional for-loop since the output cardinality is not necessarily 1:1 with that of
    // service.methods().
    List<MethodDefinition> javaMethods = new ArrayList<>();
    for (Method method : service.methods()) {
      if (method.hasLro()) {
        javaMethods.add(createOperationCallableGetter(method, typeStore));
      }
      if (method.isPaged()) {
        javaMethods.add(createPagedCallableGetter(method, typeStore));
      }
      javaMethods.add(createCallableGetter(method, typeStore));
    }
    return javaMethods;
  }

  private static MethodDefinition createOperationCallableGetter(
      Method method, TypeStore typeStore) {
    return createCallableGetterHelper(method, typeStore, true, false);
  }

  private static MethodDefinition createPagedCallableGetter(Method method, TypeStore typeStore) {
    return createCallableGetterHelper(method, typeStore, false, true);
  }

  private static MethodDefinition createCallableGetter(Method method, TypeStore typeStore) {
    return createCallableGetterHelper(method, typeStore, false, false);
  }

  private static MethodDefinition createCallableGetterHelper(
      Method method, TypeStore typeStore, boolean isLroCallable, boolean isPaged) {
    TypeNode returnType;
    switch (method.stream()) {
      case CLIENT:
        returnType = typeStore.get("ClientStreamingCallable");
        break;
      case SERVER:
        returnType = typeStore.get("ServerStreamingCallable");
        break;
      case BIDI:
        returnType = typeStore.get("BidiStreamingCallable");
        break;
      case NONE:
        // Fall through.
      default:
        returnType = typeStore.get(isLroCallable ? "OperationCallable" : "UnaryCallable");
    }

    String methodName =
        String.format(
            "%s%sCallable",
            JavaStyle.toLowerCamelCase(method.name()),
            (isLroCallable ? "Operation" : isPaged ? "Paged" : ""));
    List<Reference> genericRefs = new ArrayList<>();
    genericRefs.add(method.inputType().reference());
    if (method.hasLro() && isLroCallable) {
      genericRefs.add(method.lro().responseType().reference());
      genericRefs.add(method.lro().metadataType().reference());
    } else if (isPaged) {
      genericRefs.add(
          typeStore
              .get(String.format(PAGED_RESPONSE_TYPE_NAME_PATTERN, method.name()))
              .reference());
    } else {
      genericRefs.add(method.outputType().reference());
    }
    returnType = TypeNode.withReference(returnType.reference().copyAndSetGenerics(genericRefs));

    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(returnType)
        .setName(methodName)
        .setBody(createThrowUOEBody(methodName, typeStore))
        .build();
  }

  private static MethodDefinition createOperationsStubGetter(TypeStore typeStore) {
    String methodName = "getOperationsStub";
    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(typeStore.get("OperationsStub"))
        .setName(methodName)
        .setBody(createThrowUOEBody(methodName, typeStore))
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

  private static TypeStore createTypes(Service service, Map<String, Message> messageTypes) {
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
    TypeStore typeStore = new TypeStore(concreteClazzes);
    typeStore.putMessageTypes(service.pakkage(), messageTypes);

    // Vapor dependency typeStore.
    typeStore.put("com.google.longrunning.stub", "OperationsStub");

    // Pagination types.
    typeStore.putAll(
        service.pakkage(),
        service.methods().stream()
            .filter(m -> m.isPaged())
            .map(m -> String.format(PAGED_RESPONSE_TYPE_NAME_PATTERN, m.name()))
            .collect(Collectors.toList()),
        true,
        ClassNames.getServiceClientClassName(service));

    return typeStore;
  }

  private static List<Statement> createThrowUOEBody(String methodName, TypeStore typeStore) {
    return Arrays.asList(
        ExprStatement.withExpr(
            ThrowExpr.builder()
                .setType(typeStore.get("UnsupportedOperationException"))
                .setMessageExpr(String.format("Not implemented: %s()", methodName))
                .build()));
  }

  private static String getClientClassName(Service service) {
    return String.format("%sClient", service.overriddenName());
  }
}
