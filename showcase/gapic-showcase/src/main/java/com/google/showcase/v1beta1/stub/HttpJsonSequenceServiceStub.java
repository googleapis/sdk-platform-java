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

import static com.google.showcase.v1beta1.SequenceServiceClient.ListLocationsPagedResponse;

import com.google.api.core.BetaApi;
import com.google.api.core.InternalApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.httpjson.ApiMethodDescriptor;
import com.google.api.gax.httpjson.HttpJsonCallSettings;
import com.google.api.gax.httpjson.HttpJsonStubCallableFactory;
import com.google.api.gax.httpjson.ProtoMessageRequestFormatter;
import com.google.api.gax.httpjson.ProtoMessageResponseParser;
import com.google.api.gax.httpjson.ProtoRestSerializer;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.RequestParamsBuilder;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.protobuf.Empty;
import com.google.protobuf.TypeRegistry;
import com.google.showcase.v1beta1.AttemptSequenceRequest;
import com.google.showcase.v1beta1.AttemptStreamingSequenceRequest;
import com.google.showcase.v1beta1.AttemptStreamingSequenceResponse;
import com.google.showcase.v1beta1.CreateSequenceRequest;
import com.google.showcase.v1beta1.CreateStreamingSequenceRequest;
import com.google.showcase.v1beta1.GetSequenceReportRequest;
import com.google.showcase.v1beta1.GetStreamingSequenceReportRequest;
import com.google.showcase.v1beta1.Sequence;
import com.google.showcase.v1beta1.SequenceReport;
import com.google.showcase.v1beta1.StreamingSequence;
import com.google.showcase.v1beta1.StreamingSequenceReport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * REST stub implementation for the SequenceService service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class HttpJsonSequenceServiceStub extends SequenceServiceStub {
  private static final TypeRegistry typeRegistry = TypeRegistry.newBuilder().build();

  private static final ApiMethodDescriptor<CreateSequenceRequest, Sequence>
      createSequenceMethodDescriptor =
          ApiMethodDescriptor.<CreateSequenceRequest, Sequence>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.SequenceService/CreateSequence")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<CreateSequenceRequest>newBuilder()
                      .setPath(
                          "/v1beta1/sequences",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<CreateSequenceRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<CreateSequenceRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("sequence", request.getSequence(), false))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<Sequence>newBuilder()
                      .setDefaultInstance(Sequence.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<CreateStreamingSequenceRequest, StreamingSequence>
      createStreamingSequenceMethodDescriptor =
          ApiMethodDescriptor.<CreateStreamingSequenceRequest, StreamingSequence>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.SequenceService/CreateStreamingSequence")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<CreateStreamingSequenceRequest>newBuilder()
                      .setPath(
                          "/v1beta1/streamingSequences",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<CreateStreamingSequenceRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<CreateStreamingSequenceRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody(
                                      "streamingSequence", request.getStreamingSequence(), false))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<StreamingSequence>newBuilder()
                      .setDefaultInstance(StreamingSequence.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<GetSequenceReportRequest, SequenceReport>
      getSequenceReportMethodDescriptor =
          ApiMethodDescriptor.<GetSequenceReportRequest, SequenceReport>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.SequenceService/GetSequenceReport")
              .setHttpMethod("GET")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<GetSequenceReportRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=sequences/*/sequenceReport}",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<GetSequenceReportRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<GetSequenceReportRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<SequenceReport>newBuilder()
                      .setDefaultInstance(SequenceReport.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<
          GetStreamingSequenceReportRequest, StreamingSequenceReport>
      getStreamingSequenceReportMethodDescriptor =
          ApiMethodDescriptor
              .<GetStreamingSequenceReportRequest, StreamingSequenceReport>newBuilder()
              .setFullMethodName(
                  "google.showcase.v1beta1.SequenceService/GetStreamingSequenceReport")
              .setHttpMethod("GET")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<GetStreamingSequenceReportRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=streamingSequences/*/streamingSequenceReport}",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<GetStreamingSequenceReportRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<GetStreamingSequenceReportRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<StreamingSequenceReport>newBuilder()
                      .setDefaultInstance(StreamingSequenceReport.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<AttemptSequenceRequest, Empty>
      attemptSequenceMethodDescriptor =
          ApiMethodDescriptor.<AttemptSequenceRequest, Empty>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.SequenceService/AttemptSequence")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<AttemptSequenceRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=sequences/*}",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<AttemptSequenceRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<AttemptSequenceRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("*", request.toBuilder().clearName().build(), false))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<Empty>newBuilder()
                      .setDefaultInstance(Empty.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<
          AttemptStreamingSequenceRequest, AttemptStreamingSequenceResponse>
      attemptStreamingSequenceMethodDescriptor =
          ApiMethodDescriptor
              .<AttemptStreamingSequenceRequest, AttemptStreamingSequenceResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.SequenceService/AttemptStreamingSequence")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.SERVER_STREAMING)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<AttemptStreamingSequenceRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=streamingSequences/*}:stream",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<AttemptStreamingSequenceRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<AttemptStreamingSequenceRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("*", request.toBuilder().clearName().build(), false))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<AttemptStreamingSequenceResponse>newBuilder()
                      .setDefaultInstance(AttemptStreamingSequenceResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<ListLocationsRequest, ListLocationsResponse>
      listLocationsMethodDescriptor =
          ApiMethodDescriptor.<ListLocationsRequest, ListLocationsResponse>newBuilder()
              .setFullMethodName("google.cloud.location.Locations/ListLocations")
              .setHttpMethod("GET")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<ListLocationsRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=projects/*}/locations",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<ListLocationsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<ListLocationsRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<ListLocationsResponse>newBuilder()
                      .setDefaultInstance(ListLocationsResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<GetLocationRequest, Location>
      getLocationMethodDescriptor =
          ApiMethodDescriptor.<GetLocationRequest, Location>newBuilder()
              .setFullMethodName("google.cloud.location.Locations/GetLocation")
              .setHttpMethod("GET")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<GetLocationRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=projects/*/locations/*}",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<GetLocationRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<GetLocationRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<Location>newBuilder()
                      .setDefaultInstance(Location.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private final UnaryCallable<CreateSequenceRequest, Sequence> createSequenceCallable;
  private final UnaryCallable<CreateStreamingSequenceRequest, StreamingSequence>
      createStreamingSequenceCallable;
  private final UnaryCallable<GetSequenceReportRequest, SequenceReport> getSequenceReportCallable;
  private final UnaryCallable<GetStreamingSequenceReportRequest, StreamingSequenceReport>
      getStreamingSequenceReportCallable;
  private final UnaryCallable<AttemptSequenceRequest, Empty> attemptSequenceCallable;
  private final ServerStreamingCallable<
          AttemptStreamingSequenceRequest, AttemptStreamingSequenceResponse>
      attemptStreamingSequenceCallable;
  private final UnaryCallable<ListLocationsRequest, ListLocationsResponse> listLocationsCallable;
  private final UnaryCallable<ListLocationsRequest, ListLocationsPagedResponse>
      listLocationsPagedCallable;
  private final UnaryCallable<GetLocationRequest, Location> getLocationCallable;

  private final BackgroundResource backgroundResources;
  private final HttpJsonStubCallableFactory callableFactory;

  public static final HttpJsonSequenceServiceStub create(SequenceServiceStubSettings settings)
      throws IOException {
    return new HttpJsonSequenceServiceStub(settings, ClientContext.create(settings));
  }

  public static final HttpJsonSequenceServiceStub create(ClientContext clientContext)
      throws IOException {
    return new HttpJsonSequenceServiceStub(
        SequenceServiceStubSettings.newHttpJsonBuilder().build(), clientContext);
  }

  public static final HttpJsonSequenceServiceStub create(
      ClientContext clientContext, HttpJsonStubCallableFactory callableFactory) throws IOException {
    return new HttpJsonSequenceServiceStub(
        SequenceServiceStubSettings.newHttpJsonBuilder().build(), clientContext, callableFactory);
  }

  /**
   * Constructs an instance of HttpJsonSequenceServiceStub, using the given settings. This is
   * protected so that it is easy to make a subclass, but otherwise, the static factory methods
   * should be preferred.
   */
  protected HttpJsonSequenceServiceStub(
      SequenceServiceStubSettings settings, ClientContext clientContext) throws IOException {
    this(settings, clientContext, new HttpJsonSequenceServiceCallableFactory());
  }

  /**
   * Constructs an instance of HttpJsonSequenceServiceStub, using the given settings. This is
   * protected so that it is easy to make a subclass, but otherwise, the static factory methods
   * should be preferred.
   */
  protected HttpJsonSequenceServiceStub(
      SequenceServiceStubSettings settings,
      ClientContext clientContext,
      HttpJsonStubCallableFactory callableFactory)
      throws IOException {
    this.callableFactory = callableFactory;

    HttpJsonCallSettings<CreateSequenceRequest, Sequence> createSequenceTransportSettings =
        HttpJsonCallSettings.<CreateSequenceRequest, Sequence>newBuilder()
            .setMethodDescriptor(createSequenceMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<CreateStreamingSequenceRequest, StreamingSequence>
        createStreamingSequenceTransportSettings =
            HttpJsonCallSettings.<CreateStreamingSequenceRequest, StreamingSequence>newBuilder()
                .setMethodDescriptor(createStreamingSequenceMethodDescriptor)
                .setTypeRegistry(typeRegistry)
                .build();
    HttpJsonCallSettings<GetSequenceReportRequest, SequenceReport>
        getSequenceReportTransportSettings =
            HttpJsonCallSettings.<GetSequenceReportRequest, SequenceReport>newBuilder()
                .setMethodDescriptor(getSequenceReportMethodDescriptor)
                .setTypeRegistry(typeRegistry)
                .setParamsExtractor(
                    request -> {
                      RequestParamsBuilder builder = RequestParamsBuilder.create();
                      builder.add("name", String.valueOf(request.getName()));
                      return builder.build();
                    })
                .build();
    HttpJsonCallSettings<GetStreamingSequenceReportRequest, StreamingSequenceReport>
        getStreamingSequenceReportTransportSettings =
            HttpJsonCallSettings
                .<GetStreamingSequenceReportRequest, StreamingSequenceReport>newBuilder()
                .setMethodDescriptor(getStreamingSequenceReportMethodDescriptor)
                .setTypeRegistry(typeRegistry)
                .setParamsExtractor(
                    request -> {
                      RequestParamsBuilder builder = RequestParamsBuilder.create();
                      builder.add("name", String.valueOf(request.getName()));
                      return builder.build();
                    })
                .build();
    HttpJsonCallSettings<AttemptSequenceRequest, Empty> attemptSequenceTransportSettings =
        HttpJsonCallSettings.<AttemptSequenceRequest, Empty>newBuilder()
            .setMethodDescriptor(attemptSequenceMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .setParamsExtractor(
                request -> {
                  RequestParamsBuilder builder = RequestParamsBuilder.create();
                  builder.add("name", String.valueOf(request.getName()));
                  return builder.build();
                })
            .build();
    HttpJsonCallSettings<AttemptStreamingSequenceRequest, AttemptStreamingSequenceResponse>
        attemptStreamingSequenceTransportSettings =
            HttpJsonCallSettings
                .<AttemptStreamingSequenceRequest, AttemptStreamingSequenceResponse>newBuilder()
                .setMethodDescriptor(attemptStreamingSequenceMethodDescriptor)
                .setTypeRegistry(typeRegistry)
                .setParamsExtractor(
                    request -> {
                      RequestParamsBuilder builder = RequestParamsBuilder.create();
                      builder.add("name", String.valueOf(request.getName()));
                      return builder.build();
                    })
                .build();
    HttpJsonCallSettings<ListLocationsRequest, ListLocationsResponse>
        listLocationsTransportSettings =
            HttpJsonCallSettings.<ListLocationsRequest, ListLocationsResponse>newBuilder()
                .setMethodDescriptor(listLocationsMethodDescriptor)
                .setTypeRegistry(typeRegistry)
                .setParamsExtractor(
                    request -> {
                      RequestParamsBuilder builder = RequestParamsBuilder.create();
                      builder.add("name", String.valueOf(request.getName()));
                      return builder.build();
                    })
                .build();
    HttpJsonCallSettings<GetLocationRequest, Location> getLocationTransportSettings =
        HttpJsonCallSettings.<GetLocationRequest, Location>newBuilder()
            .setMethodDescriptor(getLocationMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .setParamsExtractor(
                request -> {
                  RequestParamsBuilder builder = RequestParamsBuilder.create();
                  builder.add("name", String.valueOf(request.getName()));
                  return builder.build();
                })
            .build();

    this.createSequenceCallable =
        callableFactory.createUnaryCallable(
            createSequenceTransportSettings, settings.createSequenceSettings(), clientContext);
    this.createStreamingSequenceCallable =
        callableFactory.createUnaryCallable(
            createStreamingSequenceTransportSettings,
            settings.createStreamingSequenceSettings(),
            clientContext);
    this.getSequenceReportCallable =
        callableFactory.createUnaryCallable(
            getSequenceReportTransportSettings,
            settings.getSequenceReportSettings(),
            clientContext);
    this.getStreamingSequenceReportCallable =
        callableFactory.createUnaryCallable(
            getStreamingSequenceReportTransportSettings,
            settings.getStreamingSequenceReportSettings(),
            clientContext);
    this.attemptSequenceCallable =
        callableFactory.createUnaryCallable(
            attemptSequenceTransportSettings, settings.attemptSequenceSettings(), clientContext);
    this.attemptStreamingSequenceCallable =
        callableFactory.createServerStreamingCallable(
            attemptStreamingSequenceTransportSettings,
            settings.attemptStreamingSequenceSettings(),
            clientContext);
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

  @InternalApi
  public static List<ApiMethodDescriptor> getMethodDescriptors() {
    List<ApiMethodDescriptor> methodDescriptors = new ArrayList<>();
    methodDescriptors.add(createSequenceMethodDescriptor);
    methodDescriptors.add(createStreamingSequenceMethodDescriptor);
    methodDescriptors.add(getSequenceReportMethodDescriptor);
    methodDescriptors.add(getStreamingSequenceReportMethodDescriptor);
    methodDescriptors.add(attemptSequenceMethodDescriptor);
    methodDescriptors.add(attemptStreamingSequenceMethodDescriptor);
    methodDescriptors.add(listLocationsMethodDescriptor);
    methodDescriptors.add(getLocationMethodDescriptor);
    return methodDescriptors;
  }

  @Override
  public UnaryCallable<CreateSequenceRequest, Sequence> createSequenceCallable() {
    return createSequenceCallable;
  }

  @Override
  public UnaryCallable<CreateStreamingSequenceRequest, StreamingSequence>
      createStreamingSequenceCallable() {
    return createStreamingSequenceCallable;
  }

  @Override
  public UnaryCallable<GetSequenceReportRequest, SequenceReport> getSequenceReportCallable() {
    return getSequenceReportCallable;
  }

  @Override
  public UnaryCallable<GetStreamingSequenceReportRequest, StreamingSequenceReport>
      getStreamingSequenceReportCallable() {
    return getStreamingSequenceReportCallable;
  }

  @Override
  public UnaryCallable<AttemptSequenceRequest, Empty> attemptSequenceCallable() {
    return attemptSequenceCallable;
  }

  @Override
  public ServerStreamingCallable<AttemptStreamingSequenceRequest, AttemptStreamingSequenceResponse>
      attemptStreamingSequenceCallable() {
    return attemptStreamingSequenceCallable;
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
