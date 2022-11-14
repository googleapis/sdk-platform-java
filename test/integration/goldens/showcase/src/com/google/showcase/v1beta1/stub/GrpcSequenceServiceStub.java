/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.showcase.v1beta1.stub;

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.common.collect.ImmutableMap;
import com.google.longrunning.stub.GrpcOperationsStub;
import com.google.protobuf.Empty;
import com.google.showcase.v1beta1.AttemptSequenceRequest;
import com.google.showcase.v1beta1.CreateSequenceRequest;
import com.google.showcase.v1beta1.GetSequenceReportRequest;
import com.google.showcase.v1beta1.Sequence;
import com.google.showcase.v1beta1.SequenceReport;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * gRPC stub implementation for the SequenceService service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class GrpcSequenceServiceStub extends SequenceServiceStub {
  private static final MethodDescriptor<CreateSequenceRequest, Sequence>
      createSequenceMethodDescriptor =
          MethodDescriptor.<CreateSequenceRequest, Sequence>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.SequenceService/CreateSequence")
              .setRequestMarshaller(
                  ProtoUtils.marshaller(CreateSequenceRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(Sequence.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<GetSequenceReportRequest, SequenceReport>
      getSequenceReportMethodDescriptor =
          MethodDescriptor.<GetSequenceReportRequest, SequenceReport>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.SequenceService/GetSequenceReport")
              .setRequestMarshaller(
                  ProtoUtils.marshaller(GetSequenceReportRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(SequenceReport.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<AttemptSequenceRequest, Empty>
      attemptSequenceMethodDescriptor =
          MethodDescriptor.<AttemptSequenceRequest, Empty>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.SequenceService/AttemptSequence")
              .setRequestMarshaller(
                  ProtoUtils.marshaller(AttemptSequenceRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(Empty.getDefaultInstance()))
              .build();

  private final UnaryCallable<CreateSequenceRequest, Sequence> createSequenceCallable;
  private final UnaryCallable<GetSequenceReportRequest, SequenceReport> getSequenceReportCallable;
  private final UnaryCallable<AttemptSequenceRequest, Empty> attemptSequenceCallable;

  private final BackgroundResource backgroundResources;
  private final GrpcOperationsStub operationsStub;
  private final GrpcStubCallableFactory callableFactory;

  public static final GrpcSequenceServiceStub create(SequenceServiceStubSettings settings)
      throws IOException {
    return new GrpcSequenceServiceStub(settings, ClientContext.create(settings));
  }

  public static final GrpcSequenceServiceStub create(ClientContext clientContext)
      throws IOException {
    return new GrpcSequenceServiceStub(
        SequenceServiceStubSettings.newBuilder().build(), clientContext);
  }

  public static final GrpcSequenceServiceStub create(
      ClientContext clientContext, GrpcStubCallableFactory callableFactory) throws IOException {
    return new GrpcSequenceServiceStub(
        SequenceServiceStubSettings.newBuilder().build(), clientContext, callableFactory);
  }

  /**
   * Constructs an instance of GrpcSequenceServiceStub, using the given settings. This is protected
   * so that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected GrpcSequenceServiceStub(
      SequenceServiceStubSettings settings, ClientContext clientContext) throws IOException {
    this(settings, clientContext, new GrpcSequenceServiceCallableFactory());
  }

  /**
   * Constructs an instance of GrpcSequenceServiceStub, using the given settings. This is protected
   * so that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected GrpcSequenceServiceStub(
      SequenceServiceStubSettings settings,
      ClientContext clientContext,
      GrpcStubCallableFactory callableFactory)
      throws IOException {
    this.callableFactory = callableFactory;
    this.operationsStub = GrpcOperationsStub.create(clientContext, callableFactory);

    GrpcCallSettings<CreateSequenceRequest, Sequence> createSequenceTransportSettings =
        GrpcCallSettings.<CreateSequenceRequest, Sequence>newBuilder()
            .setMethodDescriptor(createSequenceMethodDescriptor)
            .build();
    GrpcCallSettings<GetSequenceReportRequest, SequenceReport> getSequenceReportTransportSettings =
        GrpcCallSettings.<GetSequenceReportRequest, SequenceReport>newBuilder()
            .setMethodDescriptor(getSequenceReportMethodDescriptor)
            .setParamsExtractor(
                request -> {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
                  params.put("name", String.valueOf(request.getName()));
                  return params.build();
                })
            .build();
    GrpcCallSettings<AttemptSequenceRequest, Empty> attemptSequenceTransportSettings =
        GrpcCallSettings.<AttemptSequenceRequest, Empty>newBuilder()
            .setMethodDescriptor(attemptSequenceMethodDescriptor)
            .setParamsExtractor(
                request -> {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
                  params.put("name", String.valueOf(request.getName()));
                  return params.build();
                })
            .build();

    this.createSequenceCallable =
        callableFactory.createUnaryCallable(
            createSequenceTransportSettings, settings.createSequenceSettings(), clientContext);
    this.getSequenceReportCallable =
        callableFactory.createUnaryCallable(
            getSequenceReportTransportSettings,
            settings.getSequenceReportSettings(),
            clientContext);
    this.attemptSequenceCallable =
        callableFactory.createUnaryCallable(
            attemptSequenceTransportSettings, settings.attemptSequenceSettings(), clientContext);

    this.backgroundResources =
        new BackgroundResourceAggregation(clientContext.getBackgroundResources());
  }

  public GrpcOperationsStub getOperationsStub() {
    return operationsStub;
  }

  @Override
  public UnaryCallable<CreateSequenceRequest, Sequence> createSequenceCallable() {
    return createSequenceCallable;
  }

  @Override
  public UnaryCallable<GetSequenceReportRequest, SequenceReport> getSequenceReportCallable() {
    return getSequenceReportCallable;
  }

  @Override
  public UnaryCallable<AttemptSequenceRequest, Empty> attemptSequenceCallable() {
    return attemptSequenceCallable;
  }

  @Override
  public final void close() {
    try {
      backgroundResources.close();
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new IllegalStateException("Failed to close resource", e);
    }
  }

  @Override
  public void shutdown() {
    backgroundResources.shutdown();
  }

  @Override
  public boolean isShutdown() {
    return backgroundResources.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return backgroundResources.isTerminated();
  }

  @Override
  public void shutdownNow() {
    backgroundResources.shutdownNow();
  }

  @Override
  public boolean awaitTermination(long duration, TimeUnit unit) throws InterruptedException {
    return backgroundResources.awaitTermination(duration, unit);
  }
}
