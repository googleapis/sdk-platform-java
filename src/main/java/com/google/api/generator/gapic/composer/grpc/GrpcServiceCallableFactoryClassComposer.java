// Copyright 2021 Google LLC
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

package com.google.api.generator.gapic.composer.grpc;

import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.composer.common.AbstractServiceCallableFactoryClassComposer;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.longrunning.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GrpcServiceCallableFactoryClassComposer
    extends AbstractServiceCallableFactoryClassComposer {
  private static final GrpcServiceCallableFactoryClassComposer INSTANCE =
      new GrpcServiceCallableFactoryClassComposer();
  private static final TypeNode OPERATION_TYPE =
      TypeNode.withReference(ConcreteReference.withClazz(Operation.class));

  protected GrpcServiceCallableFactoryClassComposer() {
    super(GrpcContext.instance());
  }

  public static GrpcServiceCallableFactoryClassComposer instance() {
    return INSTANCE;
  }

  @Override
  protected List<TypeNode> createClassImplements(TypeStore typeStore) {
    return Arrays.asList(getTransportContext().stubCallableFactoryType());
  }

  protected List<MethodDefinition> createClassMethods(TypeStore typeStore) {
    List<MethodDefinition> classMethods = new ArrayList<>(super.createClassMethods(typeStore));
    classMethods.addAll(
        Arrays.asList(
            createBidiStreamingCallableMethod(typeStore),
            createServerStreamingCallableMethod(typeStore),
            createClientStreamingCallableMethod(typeStore)));
    return classMethods;
  }

  protected MethodDefinition createUnaryCallableMethod(TypeStore typeStore) {
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

  protected MethodDefinition createPagedCallableMethod(TypeStore typeStore) {
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

  @Override
  protected MethodDefinition createOperationCallableMethod(TypeStore typeStore) {
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
        /*grpcCallSettingsTemplateObjects=*/ Arrays.asList(requestTemplateName, OPERATION_TYPE),
        /*callSettingsVariantName=*/ methodVariantName,
        /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
            .map(n -> (Object) n)
            .collect(Collectors.toList()));
  }

  private MethodDefinition createBidiStreamingCallableMethod(TypeStore typeStore) {
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

  private MethodDefinition createServerStreamingCallableMethod(TypeStore typeStore) {
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

  private MethodDefinition createClientStreamingCallableMethod(TypeStore typeStore) {
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
}
