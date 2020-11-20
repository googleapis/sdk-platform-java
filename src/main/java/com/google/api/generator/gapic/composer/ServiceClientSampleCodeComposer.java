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

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.samplecode.SampleCodeJavaFormatter;
import com.google.api.generator.gapic.composer.samplecode.SampleCodeWriter;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Method.Stream;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceClientSampleCodeComposer {

  private static final String PAGED_RESPONSE_TYPE_NAME_PATTERN = "%sPagedResponse";
  // TODO(summerji): Add unit tests for ServiceClientSampleCodeComposer.
  // TODO(summerji): Refactor signatures as sample code context.

  public static String composeClassHeaderMethodSampleCode(
      Service service, Map<String, ResourceName> resourceNames) {
    // Use the first pure unary RPC method's sample code as showcase, if no such method exists, use
    // the first method in the service's methods list.
    Map<String, TypeNode> types = createDynamicTypes(service);
    TypeNode clientType = types.get(getClientName(service.name()));
    Method method =
        service.methods().stream()
            .filter(m -> m.stream() == Stream.NONE && !m.hasLro() && !m.isPaged())
            .findFirst()
            .orElse(service.methods().get(0));
    // If variant method signatures exists, use the first one.
    List<MethodArgument> arguments =
        method.methodSignatures().isEmpty()
            ? Collections.emptyList()
            : method.methodSignatures().get(0);
    if (method.stream() == Stream.NONE) {
      return SampleCodeWriter.write(
          SampleCodeHelperComposer.composeRpcMethodSampleCode(
              method, arguments, clientType, resourceNames));
    }

    TypeNode rawCallableReturnType = null;
    if (method.hasLro()) {
      rawCallableReturnType = types.get("OperationCallable");
    } else if (method.stream() == Stream.CLIENT) {
      rawCallableReturnType = types.get("ClientStreamingCallable");
    } else if (method.stream() == Stream.SERVER) {
      rawCallableReturnType = types.get("ServerStreamingCallable");
    } else if (method.stream() == Stream.BIDI) {
      rawCallableReturnType = types.get("BidiStreamingCallable");
    } else {
      rawCallableReturnType = types.get("UnaryCallable");
    }

    // Set generics.
    TypeNode returnType =
        TypeNode.withReference(
            rawCallableReturnType
                .reference()
                .copyAndSetGenerics(getGenericsForCallable(method, types)));
    return SampleCodeWriter.write(
        SampleCodeHelperComposer.composeRpcCallableMethodSampleCode(
            method, clientType, returnType, resourceNames));
  }

  public static String composeClassHeaderCredentialsSampleCode(
      TypeNode clientType, TypeNode settingsType) {
    // Initialize clientSettings with builder() method.
    // e.g. EchoSettings echoSettings =
    // EchoSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create("myCredentials")).build();
    String settingsName = JavaStyle.toLowerCamelCase(settingsType.reference().name());
    String clientName = JavaStyle.toLowerCamelCase(clientType.reference().name());
    VariableExpr settingsVarExpr = createVariableExpr(settingsName, settingsType);
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsType)
            .setMethodName("newBuilder")
            .build();
    TypeNode fixedCredentialProvideType =
        TypeNode.withReference(ConcreteReference.withClazz(FixedCredentialsProvider.class));
    MethodInvocationExpr credentialArgExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(fixedCredentialProvideType)
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue("myCredentials")))
            .setMethodName("create")
            .build();
    MethodInvocationExpr credentialsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderMethodExpr)
            .setArguments(credentialArgExpr)
            .setMethodName("setCredentialsProvider")
            .build();
    MethodInvocationExpr buildMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(credentialsMethodExpr)
            .setReturnType(settingsType)
            .setMethodName("build")
            .build();
    Expr initSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    // Initialized client with create() method.
    // e.g. EchoClient echoClient = EchoClient.create(echoSettings);
    VariableExpr clientVarExpr = createVariableExpr(clientName, clientType);
    MethodInvocationExpr createMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(clientType)
            .setArguments(settingsVarExpr)
            .setMethodName("create")
            .setReturnType(clientType)
            .build();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();

    return writeSampleCode(Arrays.asList(initSettingsVarExpr, initClientVarExpr));
  }

  public static String composeClassHeaderEndpointSampleCode(
      TypeNode clientType, TypeNode settingsType) {
    // Initialize client settings with builder() method.
    // e.g. EchoSettings echoSettings = EchoSettings.newBuilder().setEndpoint("myEndpoint").build();
    String settingsName = JavaStyle.toLowerCamelCase(settingsType.reference().name());
    String clientName = JavaStyle.toLowerCamelCase(clientType.reference().name());
    VariableExpr settingsVarExpr = createVariableExpr(settingsName, settingsType);
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsType)
            .setMethodName("newBuilder")
            .build();
    MethodInvocationExpr credentialsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderMethodExpr)
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue("myEndpoint")))
            .setMethodName("setEndpoint")
            .build();
    MethodInvocationExpr buildMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(credentialsMethodExpr)
            .setReturnType(settingsType)
            .setMethodName("build")
            .build();

    Expr initSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    // Initialize client with create() method.
    // e.g. EchoClient echoClient = EchoClient.create(echoSettings);
    VariableExpr clientVarExpr = createVariableExpr(clientName, clientType);
    MethodInvocationExpr createMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(clientType)
            .setArguments(settingsVarExpr)
            .setMethodName("create")
            .setReturnType(clientType)
            .build();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();

    return writeSampleCode(Arrays.asList(initSettingsVarExpr, initClientVarExpr));
  }

  public static String composeRpcMethodHeaderSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    return SampleCodeWriter.write(
        SampleCodeHelperComposer.composeRpcMethodSampleCode(
            method, arguments, clientType, resourceNames));
  }

  public static String composeRpcCallableMethodHeaderSampleCode(
      Method method,
      TypeNode clientType,
      TypeNode returnType,
      Map<String, ResourceName> resourceNames) {
    return SampleCodeWriter.write(
        SampleCodeHelperComposer.composeRpcCallableMethodSampleCode(
            method, clientType, returnType, resourceNames));
  }

  // ======================================== Helpers ==========================================//
  // TODO(summerji): Use writeSampleCode method in new class once PR#499 merged.
  private static String writeSampleCode(List<Expr> exprs) {
    List<Statement> statements =
        exprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    for (Statement statement : statements) {
      statement.accept(visitor);
    }
    return SampleCodeJavaFormatter.format(visitor.write());
  }

  private static VariableExpr createVariableExpr(String variableName, TypeNode type) {
    return VariableExpr.withVariable(
        Variable.builder().setName(variableName).setType(type).build());
  }

  private static List<Reference> getGenericsForCallable(
      Method method, Map<String, TypeNode> types) {
    if (method.hasLro()) {
      return Arrays.asList(
          method.inputType().reference(),
          method.lro().responseType().reference(),
          method.lro().metadataType().reference());
    }
    if (method.isPaged()) {
      return Arrays.asList(
          method.inputType().reference(),
          types.get(String.format(PAGED_RESPONSE_TYPE_NAME_PATTERN, method.name())).reference());
    }
    return Arrays.asList(method.inputType().reference(), method.outputType().reference());
  }

  private static Map<String, TypeNode> createDynamicTypes(Service service) {
    Map<String, TypeNode> dynamicTypes = new HashMap<>();
    dynamicTypes.putAll(createConcreteTypes());
    dynamicTypes.put(
        getClientName(service.name()),
        TypeNode.withReference(
            VaporReference.builder()
                .setName(getClientName(service.name()))
                .setPakkage(service.pakkage())
                .build()));
    // Pagination types.
    dynamicTypes.putAll(
        service.methods().stream()
            .filter(m -> m.isPaged())
            .collect(
                Collectors.toMap(
                    m -> String.format(PAGED_RESPONSE_TYPE_NAME_PATTERN, m.name()),
                    m ->
                        TypeNode.withReference(
                            VaporReference.builder()
                                .setName(String.format(PAGED_RESPONSE_TYPE_NAME_PATTERN, m.name()))
                                .setPakkage(service.pakkage())
                                .setEnclosingClassNames(getClientName(service.name()))
                                .setIsStaticImport(true)
                                .build()))));
    return dynamicTypes;
  }

  private static Map<String, TypeNode> createConcreteTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            BidiStreamingCallable.class,
            ClientStreamingCallable.class,
            ServerStreamingCallable.class,
            OperationCallable.class,
            UnaryCallable.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }

  private static String getClientName(String serviceName) {
    return String.format("%sClient", serviceName);
  }
}
