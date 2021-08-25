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

import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcCallableFactory;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.grpc.ProtoOperationTransformers;
import com.google.api.generator.gapic.composer.common.TransportContext;
import com.google.api.generator.gapic.composer.utils.ClassNames;
import com.google.api.generator.gapic.model.Transport;
import com.google.longrunning.OperationsClient;
import com.google.longrunning.stub.GrpcOperationsStub;
import com.google.longrunning.stub.OperationsStub;
import io.grpc.MethodDescriptor;

public abstract class GrpcContext extends TransportContext {
  private static final TransportContext INSTANCE =
      GrpcContext.builder()
          .setClassNames(new ClassNames("Grpc"))
          .setTransport(Transport.GRPC)
          .setTransportName("gRPC")
          // For grpc.GrpcServiceStubClassComposer
          .setCallSettingsClass(GrpcCallSettings.class)
          .setStubCallableFactoryType(classToType(GrpcStubCallableFactory.class))
          .setMethodDescriptorClass(MethodDescriptor.class)
          .setTransportOperationsStubType(classToType(GrpcOperationsStub.class))
          .setTransportOperationsStubName("operationsStub")
          // For grpc.ServiceSettingsClassComposer
          .setInstantiatingChannelProviderClass(InstantiatingGrpcChannelProvider.Builder.class)
          .setDefaultTransportProviderBuilderName("defaultGrpcTransportProviderBuilder")
          // For grpc.ServiceStubSettingsClassComposer
          .setTransportChannelType(classToType(GrpcTransportChannel.class))
          .setTransportGetterName("getGrpcTransportName")
          // For grpc.GrpcServiceCallableFactoryClassComposer
          .setTransportCallSettingsType(classToType(GrpcCallSettings.class))
          .setTransportCallableFactoryType(classToType(GrpcCallableFactory.class))
          .setOperationsStubType(classToType(OperationsStub.class))
          .setTransportCallSettingsName("grpcCallSettings")
          // For RetrySettingsComposer
          .setOperationResponseTransformerType(
              classToType(ProtoOperationTransformers.ResponseTransformer.class))
          .setOperationMetadataTransformerType(
              classToType(ProtoOperationTransformers.MetadataTransformer.class))
          // For ServiceClientClassComposer
          .setOperationsClientType(classToType(OperationsClient.class))
          .setOperationsClientName("operationsClient")
          .build();

  public static TransportContext instance() {
    return INSTANCE;
  }
}
