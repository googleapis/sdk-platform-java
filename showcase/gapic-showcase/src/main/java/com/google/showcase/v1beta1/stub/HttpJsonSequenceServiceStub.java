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
import com.google.api.gax.rpc.UnaryCallable;
import com.google.protobuf.Empty;
import com.google.protobuf.TypeRegistry;
import com.google.showcase.v1beta1.AttemptSequenceRequest;
import com.google.showcase.v1beta1.CreateSequenceRequest;
import com.google.showcase.v1beta1.GetSequenceReportRequest;
import com.google.showcase.v1beta1.Sequence;
import com.google.showcase.v1beta1.SequenceReport;
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
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("sequence", request.getSequence(), true))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<Sequence>newBuilder()
                      .setDefaultInstance(Sequence.getDefaultInstance())
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
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
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
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("*", request.toBuilder().clearName().build(), true))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<Empty>newBuilder()
                      .setDefaultInstance(Empty.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private final UnaryCallable<CreateSequenceRequest, Sequence> createSequenceCallable;
  private final UnaryCallable<GetSequenceReportRequest, SequenceReport> getSequenceReportCallable;
  private final UnaryCallable<AttemptSequenceRequest, Empty> attemptSequenceCallable;

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
    HttpJsonCallSettings<GetSequenceReportRequest, SequenceReport>
        getSequenceReportTransportSettings =
            HttpJsonCallSettings.<GetSequenceReportRequest, SequenceReport>newBuilder()
                .setMethodDescriptor(getSequenceReportMethodDescriptor)
                .setTypeRegistry(typeRegistry)
                .build();
    HttpJsonCallSettings<AttemptSequenceRequest, Empty> attemptSequenceTransportSettings =
        HttpJsonCallSettings.<AttemptSequenceRequest, Empty>newBuilder()
            .setMethodDescriptor(attemptSequenceMethodDescriptor)
            .setTypeRegistry(typeRegistry)
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

  @InternalApi
  public static List<ApiMethodDescriptor> getMethodDescriptors() {
    List<ApiMethodDescriptor> methodDescriptors = new ArrayList<>();
    methodDescriptors.add(createSequenceMethodDescriptor);
    methodDescriptors.add(getSequenceReportMethodDescriptor);
    methodDescriptors.add(attemptSequenceMethodDescriptor);
    return methodDescriptors;
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
