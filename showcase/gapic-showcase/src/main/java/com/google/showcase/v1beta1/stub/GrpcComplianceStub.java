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

import static com.google.showcase.v1beta1.ComplianceClient.ListLocationsPagedResponse;

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.common.collect.ImmutableMap;
import com.google.longrunning.stub.GrpcOperationsStub;
import com.google.showcase.v1beta1.EnumRequest;
import com.google.showcase.v1beta1.EnumResponse;
import com.google.showcase.v1beta1.RepeatRequest;
import com.google.showcase.v1beta1.RepeatResponse;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * gRPC stub implementation for the Compliance service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class GrpcComplianceStub extends ComplianceStub {
  private static final MethodDescriptor<RepeatRequest, RepeatResponse>
      repeatDataBodyMethodDescriptor =
          MethodDescriptor.<RepeatRequest, RepeatResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.Compliance/RepeatDataBody")
              .setRequestMarshaller(ProtoUtils.marshaller(RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(RepeatResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<RepeatRequest, RepeatResponse>
      repeatDataBodyInfoMethodDescriptor =
          MethodDescriptor.<RepeatRequest, RepeatResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.Compliance/RepeatDataBodyInfo")
              .setRequestMarshaller(ProtoUtils.marshaller(RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(RepeatResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<RepeatRequest, RepeatResponse>
      repeatDataQueryMethodDescriptor =
          MethodDescriptor.<RepeatRequest, RepeatResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.Compliance/RepeatDataQuery")
              .setRequestMarshaller(ProtoUtils.marshaller(RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(RepeatResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<RepeatRequest, RepeatResponse>
      repeatDataSimplePathMethodDescriptor =
          MethodDescriptor.<RepeatRequest, RepeatResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.Compliance/RepeatDataSimplePath")
              .setRequestMarshaller(ProtoUtils.marshaller(RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(RepeatResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<RepeatRequest, RepeatResponse>
      repeatDataPathResourceMethodDescriptor =
          MethodDescriptor.<RepeatRequest, RepeatResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.Compliance/RepeatDataPathResource")
              .setRequestMarshaller(ProtoUtils.marshaller(RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(RepeatResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<RepeatRequest, RepeatResponse>
      repeatDataPathTrailingResourceMethodDescriptor =
          MethodDescriptor.<RepeatRequest, RepeatResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(
                  "google.showcase.v1beta1.Compliance/RepeatDataPathTrailingResource")
              .setRequestMarshaller(ProtoUtils.marshaller(RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(RepeatResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<RepeatRequest, RepeatResponse>
      repeatDataBodyPutMethodDescriptor =
          MethodDescriptor.<RepeatRequest, RepeatResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.Compliance/RepeatDataBodyPut")
              .setRequestMarshaller(ProtoUtils.marshaller(RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(RepeatResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<RepeatRequest, RepeatResponse>
      repeatDataBodyPatchMethodDescriptor =
          MethodDescriptor.<RepeatRequest, RepeatResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.showcase.v1beta1.Compliance/RepeatDataBodyPatch")
              .setRequestMarshaller(ProtoUtils.marshaller(RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(RepeatResponse.getDefaultInstance()))
              .build();

  private static final MethodDescriptor<EnumRequest, EnumResponse> getEnumMethodDescriptor =
      MethodDescriptor.<EnumRequest, EnumResponse>newBuilder()
          .setType(MethodDescriptor.MethodType.UNARY)
          .setFullMethodName("google.showcase.v1beta1.Compliance/GetEnum")
          .setRequestMarshaller(ProtoUtils.marshaller(EnumRequest.getDefaultInstance()))
          .setResponseMarshaller(ProtoUtils.marshaller(EnumResponse.getDefaultInstance()))
          .build();

  private static final MethodDescriptor<EnumResponse, EnumResponse> verifyEnumMethodDescriptor =
      MethodDescriptor.<EnumResponse, EnumResponse>newBuilder()
          .setType(MethodDescriptor.MethodType.UNARY)
          .setFullMethodName("google.showcase.v1beta1.Compliance/VerifyEnum")
          .setRequestMarshaller(ProtoUtils.marshaller(EnumResponse.getDefaultInstance()))
          .setResponseMarshaller(ProtoUtils.marshaller(EnumResponse.getDefaultInstance()))
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

  private final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyCallable;
  private final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyInfoCallable;
  private final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataQueryCallable;
  private final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataSimplePathCallable;
  private final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataPathResourceCallable;
  private final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataPathTrailingResourceCallable;
  private final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyPutCallable;
  private final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyPatchCallable;
  private final UnaryCallable<EnumRequest, EnumResponse> getEnumCallable;
  private final UnaryCallable<EnumResponse, EnumResponse> verifyEnumCallable;
  private final UnaryCallable<ListLocationsRequest, ListLocationsResponse> listLocationsCallable;
  private final UnaryCallable<ListLocationsRequest, ListLocationsPagedResponse>
      listLocationsPagedCallable;
  private final UnaryCallable<GetLocationRequest, Location> getLocationCallable;

  private final BackgroundResource backgroundResources;
  private final GrpcOperationsStub operationsStub;
  private final GrpcStubCallableFactory callableFactory;

  public static final GrpcComplianceStub create(ComplianceStubSettings settings)
      throws IOException {
    return new GrpcComplianceStub(settings, ClientContext.create(settings));
  }

  public static final GrpcComplianceStub create(ClientContext clientContext) throws IOException {
    return new GrpcComplianceStub(ComplianceStubSettings.newBuilder().build(), clientContext);
  }

  public static final GrpcComplianceStub create(
      ClientContext clientContext, GrpcStubCallableFactory callableFactory) throws IOException {
    return new GrpcComplianceStub(
        ComplianceStubSettings.newBuilder().build(), clientContext, callableFactory);
  }

  /**
   * Constructs an instance of GrpcComplianceStub, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected GrpcComplianceStub(ComplianceStubSettings settings, ClientContext clientContext)
      throws IOException {
    this(settings, clientContext, new GrpcComplianceCallableFactory());
  }

  /**
   * Constructs an instance of GrpcComplianceStub, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected GrpcComplianceStub(
      ComplianceStubSettings settings,
      ClientContext clientContext,
      GrpcStubCallableFactory callableFactory)
      throws IOException {
    this.callableFactory = callableFactory;
    this.operationsStub = GrpcOperationsStub.create(clientContext, callableFactory);

    GrpcCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyTransportSettings =
        GrpcCallSettings.<RepeatRequest, RepeatResponse>newBuilder()
            .setMethodDescriptor(repeatDataBodyMethodDescriptor)
            .build();
    GrpcCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyInfoTransportSettings =
        GrpcCallSettings.<RepeatRequest, RepeatResponse>newBuilder()
            .setMethodDescriptor(repeatDataBodyInfoMethodDescriptor)
            .build();
    GrpcCallSettings<RepeatRequest, RepeatResponse> repeatDataQueryTransportSettings =
        GrpcCallSettings.<RepeatRequest, RepeatResponse>newBuilder()
            .setMethodDescriptor(repeatDataQueryMethodDescriptor)
            .build();
    GrpcCallSettings<RepeatRequest, RepeatResponse> repeatDataSimplePathTransportSettings =
        GrpcCallSettings.<RepeatRequest, RepeatResponse>newBuilder()
            .setMethodDescriptor(repeatDataSimplePathMethodDescriptor)
            .setParamsExtractor(
                request -> {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
                  params.put("info.f_bool", String.valueOf(request.getInfo().getFBool()));
                  params.put("info.f_double", String.valueOf(request.getInfo().getFDouble()));
                  params.put("info.f_int32", String.valueOf(request.getInfo().getFInt32()));
                  params.put("info.f_kingdom", String.valueOf(request.getInfo().getFKingdom()));
                  params.put("info.f_string", String.valueOf(request.getInfo().getFString()));
                  return params.build();
                })
            .build();
    GrpcCallSettings<RepeatRequest, RepeatResponse> repeatDataPathResourceTransportSettings =
        GrpcCallSettings.<RepeatRequest, RepeatResponse>newBuilder()
            .setMethodDescriptor(repeatDataPathResourceMethodDescriptor)
            .setParamsExtractor(
                request -> {
                  ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
                  params.put("info.f_bool", String.valueOf(request.getInfo().getFBool()));
                  params.put(
                      "info.f_child.f_string",
                      String.valueOf(request.getInfo().getFChild().getFString()));
                  params.put("info.f_string", String.valueOf(request.getInfo().getFString()));
                  return params.build();
                })
            .build();
    GrpcCallSettings<RepeatRequest, RepeatResponse>
        repeatDataPathTrailingResourceTransportSettings =
            GrpcCallSettings.<RepeatRequest, RepeatResponse>newBuilder()
                .setMethodDescriptor(repeatDataPathTrailingResourceMethodDescriptor)
                .setParamsExtractor(
                    request -> {
                      ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
                      params.put(
                          "info.f_child.f_string",
                          String.valueOf(request.getInfo().getFChild().getFString()));
                      params.put("info.f_string", String.valueOf(request.getInfo().getFString()));
                      return params.build();
                    })
                .build();
    GrpcCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyPutTransportSettings =
        GrpcCallSettings.<RepeatRequest, RepeatResponse>newBuilder()
            .setMethodDescriptor(repeatDataBodyPutMethodDescriptor)
            .build();
    GrpcCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyPatchTransportSettings =
        GrpcCallSettings.<RepeatRequest, RepeatResponse>newBuilder()
            .setMethodDescriptor(repeatDataBodyPatchMethodDescriptor)
            .build();
    GrpcCallSettings<EnumRequest, EnumResponse> getEnumTransportSettings =
        GrpcCallSettings.<EnumRequest, EnumResponse>newBuilder()
            .setMethodDescriptor(getEnumMethodDescriptor)
            .build();
    GrpcCallSettings<EnumResponse, EnumResponse> verifyEnumTransportSettings =
        GrpcCallSettings.<EnumResponse, EnumResponse>newBuilder()
            .setMethodDescriptor(verifyEnumMethodDescriptor)
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

    this.repeatDataBodyCallable =
        callableFactory.createUnaryCallable(
            repeatDataBodyTransportSettings, settings.repeatDataBodySettings(), clientContext);
    this.repeatDataBodyInfoCallable =
        callableFactory.createUnaryCallable(
            repeatDataBodyInfoTransportSettings,
            settings.repeatDataBodyInfoSettings(),
            clientContext);
    this.repeatDataQueryCallable =
        callableFactory.createUnaryCallable(
            repeatDataQueryTransportSettings, settings.repeatDataQuerySettings(), clientContext);
    this.repeatDataSimplePathCallable =
        callableFactory.createUnaryCallable(
            repeatDataSimplePathTransportSettings,
            settings.repeatDataSimplePathSettings(),
            clientContext);
    this.repeatDataPathResourceCallable =
        callableFactory.createUnaryCallable(
            repeatDataPathResourceTransportSettings,
            settings.repeatDataPathResourceSettings(),
            clientContext);
    this.repeatDataPathTrailingResourceCallable =
        callableFactory.createUnaryCallable(
            repeatDataPathTrailingResourceTransportSettings,
            settings.repeatDataPathTrailingResourceSettings(),
            clientContext);
    this.repeatDataBodyPutCallable =
        callableFactory.createUnaryCallable(
            repeatDataBodyPutTransportSettings,
            settings.repeatDataBodyPutSettings(),
            clientContext);
    this.repeatDataBodyPatchCallable =
        callableFactory.createUnaryCallable(
            repeatDataBodyPatchTransportSettings,
            settings.repeatDataBodyPatchSettings(),
            clientContext);
    this.getEnumCallable =
        callableFactory.createUnaryCallable(
            getEnumTransportSettings, settings.getEnumSettings(), clientContext);
    this.verifyEnumCallable =
        callableFactory.createUnaryCallable(
            verifyEnumTransportSettings, settings.verifyEnumSettings(), clientContext);
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
  public UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyCallable() {
    return repeatDataBodyCallable;
  }

  @Override
  public UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyInfoCallable() {
    return repeatDataBodyInfoCallable;
  }

  @Override
  public UnaryCallable<RepeatRequest, RepeatResponse> repeatDataQueryCallable() {
    return repeatDataQueryCallable;
  }

  @Override
  public UnaryCallable<RepeatRequest, RepeatResponse> repeatDataSimplePathCallable() {
    return repeatDataSimplePathCallable;
  }

  @Override
  public UnaryCallable<RepeatRequest, RepeatResponse> repeatDataPathResourceCallable() {
    return repeatDataPathResourceCallable;
  }

  @Override
  public UnaryCallable<RepeatRequest, RepeatResponse> repeatDataPathTrailingResourceCallable() {
    return repeatDataPathTrailingResourceCallable;
  }

  @Override
  public UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyPutCallable() {
    return repeatDataBodyPutCallable;
  }

  @Override
  public UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyPatchCallable() {
    return repeatDataBodyPatchCallable;
  }

  @Override
  public UnaryCallable<EnumRequest, EnumResponse> getEnumCallable() {
    return getEnumCallable;
  }

  @Override
  public UnaryCallable<EnumResponse, EnumResponse> verifyEnumCallable() {
    return verifyEnumCallable;
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
