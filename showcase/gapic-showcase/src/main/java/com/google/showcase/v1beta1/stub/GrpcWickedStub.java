/*
 * Copyright 2023 Google LLC
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

import static com.google.showcase.v1beta1.WickedClient.ListLocationsPagedResponse;

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.common.collect.ImmutableMap;
import com.google.longrunning.stub.GrpcOperationsStub;
import com.google.showcase.v1beta1.EvilRequest;
import com.google.showcase.v1beta1.EvilResponse;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * gRPC stub implementation for the Wicked service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class GrpcWickedStub extends WickedStub {
  private static final MethodDescriptor<EvilRequest, EvilResponse> craftEvilPlanMethodDescriptor =
      MethodDescriptor.<EvilRequest, EvilResponse>newBuilder()
          .setType(MethodDescriptor.MethodType.UNARY)
          .setFullMethodName("google.showcase.v1beta1.Wicked/CraftEvilPlan")
          .setRequestMarshaller(ProtoUtils.marshaller(EvilRequest.getDefaultInstance()))
          .setResponseMarshaller(ProtoUtils.marshaller(EvilResponse.getDefaultInstance()))
          .build();

  private static final MethodDescriptor<EvilRequest, EvilResponse>
      brainstormEvilPlansMethodDescriptor =
          MethodDescriptor.<EvilRequest, EvilResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName("google.showcase.v1beta1.Wicked/BrainstormEvilPlans")
              .setRequestMarshaller(ProtoUtils.marshaller(EvilRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(EvilResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<EvilRequest, EvilResponse>
      persuadeEvilPlanMethodDescriptor =
          MethodDescriptor.<EvilRequest, EvilResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName("google.showcase.v1beta1.Wicked/PersuadeEvilPlan")
              .setRequestMarshaller(ProtoUtils.marshaller(EvilRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(EvilResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<ListLocationsRequest, ListLocationsResponse>
      listLocationsMethodDescriptor =
          MethodDescriptor.<ListLocationsRequest, ListLocationsResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.cloud.location.Locations/ListLocations")
              .setRequestMarshaller(
                  ProtoUtils.marshaller(ListLocationsRequest.getDefaultInstance()))
              .setResponseMarshaller(
                  ProtoUtils.marshaller(ListLocationsResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<GetLocationRequest, Location> getLocationMethodDescriptor =
      MethodDescriptor.<GetLocationRequest, Location>newBuilder()
          .setType(MethodDescriptor.MethodType.UNARY)
          .setFullMethodName("google.cloud.location.Locations/GetLocation")
          .setRequestMarshaller(ProtoUtils.marshaller(GetLocationRequest.getDefaultInstance()))
          .setResponseMarshaller(ProtoUtils.marshaller(Location.getDefaultInstance()))
          .build();

  private final UnaryCallable<EvilRequest, EvilResponse> craftEvilPlanCallable;
  private final BidiStreamingCallable<EvilRequest, EvilResponse> brainstormEvilPlansCallable;
  private final ClientStreamingCallable<EvilRequest, EvilResponse> persuadeEvilPlanCallable;
  private final UnaryCallable<ListLocationsRequest, ListLocationsResponse> listLocationsCallable;
  private final UnaryCallable<ListLocationsRequest, ListLocationsPagedResponse>
      listLocationsPagedCallable;
  private final UnaryCallable<GetLocationRequest, Location> getLocationCallable;

  private final BackgroundResource backgroundResources;
  private final GrpcOperationsStub operationsStub;
  private final GrpcStubCallableFactory callableFactory;

  public static final GrpcWickedStub create(WickedStubSettings settings) throws IOException {
    return new GrpcWickedStub(settings, ClientContext.create(settings));
  }

  public static final GrpcWickedStub create(ClientContext clientContext) throws IOException {
    return new GrpcWickedStub(WickedStubSettings.newBuilder().build(), clientContext);
  }

  public static final GrpcWickedStub create(
      ClientContext clientContext, GrpcStubCallableFactory callableFactory) throws IOException {
    return new GrpcWickedStub(
        WickedStubSettings.newBuilder().build(), clientContext, callableFactory);
  }

  /**
   * Constructs an instance of GrpcWickedStub, using the given settings. This is protected so that
   * it is easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected GrpcWickedStub(WickedStubSettings settings, ClientContext clientContext)
      throws IOException {
    this(settings, clientContext, new GrpcWickedCallableFactory());
  }

  /**
   * Constructs an instance of GrpcWickedStub, using the given settings. This is protected so that
   * it is easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected GrpcWickedStub(
      WickedStubSettings settings,
      ClientContext clientContext,
      GrpcStubCallableFactory callableFactory)
      throws IOException {
    this.callableFactory = callableFactory;
    this.operationsStub = GrpcOperationsStub.create(clientContext, callableFactory);

    GrpcCallSettings<EvilRequest, EvilResponse> craftEvilPlanTransportSettings =
        GrpcCallSettings.<EvilRequest, EvilResponse>newBuilder()
            .setMethodDescriptor(craftEvilPlanMethodDescriptor)
            .build();
    GrpcCallSettings<EvilRequest, EvilResponse> brainstormEvilPlansTransportSettings =
        GrpcCallSettings.<EvilRequest, EvilResponse>newBuilder()
            .setMethodDescriptor(brainstormEvilPlansMethodDescriptor)
            .build();
    GrpcCallSettings<EvilRequest, EvilResponse> persuadeEvilPlanTransportSettings =
        GrpcCallSettings.<EvilRequest, EvilResponse>newBuilder()
            .setMethodDescriptor(persuadeEvilPlanMethodDescriptor)
            .build();
    GrpcCallSettings<ListLocationsRequest, ListLocationsResponse> listLocationsTransportSettings =
        GrpcCallSettings.<ListLocationsRequest, ListLocationsResponse>newBuilder()
            .setMethodDescriptor(listLocationsMethodDescriptor)
            .setParamsExtractor(
                request -> {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
                  params.put("name", String.valueOf(request.getName()));
                  return params.build();
                })
            .build();
    GrpcCallSettings<GetLocationRequest, Location> getLocationTransportSettings =
        GrpcCallSettings.<GetLocationRequest, Location>newBuilder()
            .setMethodDescriptor(getLocationMethodDescriptor)
            .setParamsExtractor(
                request -> {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
                  params.put("name", String.valueOf(request.getName()));
                  return params.build();
                })
            .build();

    this.craftEvilPlanCallable =
        callableFactory.createUnaryCallable(
            craftEvilPlanTransportSettings, settings.craftEvilPlanSettings(), clientContext);
    this.brainstormEvilPlansCallable =
        callableFactory.createBidiStreamingCallable(
            brainstormEvilPlansTransportSettings,
            settings.brainstormEvilPlansSettings(),
            clientContext);
    this.persuadeEvilPlanCallable =
        callableFactory.createClientStreamingCallable(
            persuadeEvilPlanTransportSettings, settings.persuadeEvilPlanSettings(), clientContext);
    this.listLocationsCallable =
        callableFactory.createUnaryCallable(
            listLocationsTransportSettings, settings.listLocationsSettings(), clientContext);
    this.listLocationsPagedCallable =
        callableFactory.createPagedCallable(
            listLocationsTransportSettings, settings.listLocationsSettings(), clientContext);
    this.getLocationCallable =
        callableFactory.createUnaryCallable(
            getLocationTransportSettings, settings.getLocationSettings(), clientContext);

    this.backgroundResources =
        new BackgroundResourceAggregation(clientContext.getBackgroundResources());
  }

  public GrpcOperationsStub getOperationsStub() {
    return operationsStub;
  }

  @Override
  public UnaryCallable<EvilRequest, EvilResponse> craftEvilPlanCallable() {
    return craftEvilPlanCallable;
  }

  @Override
  public BidiStreamingCallable<EvilRequest, EvilResponse> brainstormEvilPlansCallable() {
    return brainstormEvilPlansCallable;
  }

  @Override
  public ClientStreamingCallable<EvilRequest, EvilResponse> persuadeEvilPlanCallable() {
    return persuadeEvilPlanCallable;
  }

  @Override
  public UnaryCallable<ListLocationsRequest, ListLocationsResponse> listLocationsCallable() {
    return listLocationsCallable;
  }

  @Override
  public UnaryCallable<ListLocationsRequest, ListLocationsPagedResponse>
      listLocationsPagedCallable() {
    return listLocationsPagedCallable;
  }

  @Override
  public UnaryCallable<GetLocationRequest, Location> getLocationCallable() {
    return getLocationCallable;
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
