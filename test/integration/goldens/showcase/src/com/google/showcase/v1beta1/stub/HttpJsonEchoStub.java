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

import static com.google.showcase.v1beta1.EchoClient.PagedExpandLegacyMappedPagedResponse;
import static com.google.showcase.v1beta1.EchoClient.PagedExpandPagedResponse;

import com.google.api.core.BetaApi;
import com.google.api.core.InternalApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.httpjson.ApiMethodDescriptor;
import com.google.api.gax.httpjson.HttpJsonCallSettings;
import com.google.api.gax.httpjson.HttpJsonOperationSnapshot;
import com.google.api.gax.httpjson.HttpJsonStubCallableFactory;
import com.google.api.gax.httpjson.ProtoMessageRequestFormatter;
import com.google.api.gax.httpjson.ProtoMessageResponseParser;
import com.google.api.gax.httpjson.ProtoRestSerializer;
import com.google.api.gax.httpjson.longrunning.stub.HttpJsonOperationsStub;
import com.google.api.gax.longrunning.OperationSnapshot;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.longrunning.Operation;
import com.google.protobuf.TypeRegistry;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.BlockResponse;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.ExpandRequest;
import com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse;
import com.google.showcase.v1beta1.PagedExpandLegacyRequest;
import com.google.showcase.v1beta1.PagedExpandRequest;
import com.google.showcase.v1beta1.PagedExpandResponse;
import com.google.showcase.v1beta1.WaitMetadata;
import com.google.showcase.v1beta1.WaitRequest;
import com.google.showcase.v1beta1.WaitResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * REST stub implementation for the Echo service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class HttpJsonEchoStub extends EchoStub {
  private static final TypeRegistry typeRegistry =
      TypeRegistry.newBuilder()
          .add(WaitResponse.getDescriptor())
          .add(WaitMetadata.getDescriptor())
          .build();

  private static final ApiMethodDescriptor<EchoRequest, EchoResponse> echoMethodDescriptor =
      ApiMethodDescriptor.<EchoRequest, EchoResponse>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Echo/Echo")
          .setHttpMethod("POST")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<EchoRequest>newBuilder()
                  .setPath(
                      "/v1beta1/echo:echo",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<EchoRequest> serializer = ProtoRestSerializer.create();
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<EchoRequest> serializer = ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create()
                              .toBody("*", request.toBuilder().build(), true))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<EchoResponse>newBuilder()
                  .setDefaultInstance(EchoResponse.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<ExpandRequest, EchoResponse> expandMethodDescriptor =
      ApiMethodDescriptor.<ExpandRequest, EchoResponse>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Echo/Expand")
          .setHttpMethod("POST")
          .setType(ApiMethodDescriptor.MethodType.SERVER_STREAMING)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<ExpandRequest>newBuilder()
                  .setPath(
                      "/v1beta1/echo:expand",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<ExpandRequest> serializer =
                            ProtoRestSerializer.create();
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<ExpandRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create()
                              .toBody("*", request.toBuilder().build(), true))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<EchoResponse>newBuilder()
                  .setDefaultInstance(EchoResponse.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<PagedExpandRequest, PagedExpandResponse>
      pagedExpandMethodDescriptor =
          ApiMethodDescriptor.<PagedExpandRequest, PagedExpandResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Echo/PagedExpand")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<PagedExpandRequest>newBuilder()
                      .setPath(
                          "/v1beta1/echo:pagedExpand",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<PagedExpandRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<PagedExpandRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("*", request.toBuilder().build(), true))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<PagedExpandResponse>newBuilder()
                      .setDefaultInstance(PagedExpandResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<PagedExpandLegacyRequest, PagedExpandResponse>
      pagedExpandLegacyMethodDescriptor =
          ApiMethodDescriptor.<PagedExpandLegacyRequest, PagedExpandResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Echo/PagedExpandLegacy")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<PagedExpandLegacyRequest>newBuilder()
                      .setPath(
                          "/v1beta1/echo:pagedExpandLegacy",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<PagedExpandLegacyRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<PagedExpandLegacyRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("*", request.toBuilder().build(), true))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<PagedExpandResponse>newBuilder()
                      .setDefaultInstance(PagedExpandResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<PagedExpandRequest, PagedExpandLegacyMappedResponse>
      pagedExpandLegacyMappedMethodDescriptor =
          ApiMethodDescriptor.<PagedExpandRequest, PagedExpandLegacyMappedResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Echo/PagedExpandLegacyMapped")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<PagedExpandRequest>newBuilder()
                      .setPath(
                          "/v1beta1/echo:pagedExpandLegacyMapped",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<PagedExpandRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<PagedExpandRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("*", request.toBuilder().build(), true))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<PagedExpandLegacyMappedResponse>newBuilder()
                      .setDefaultInstance(PagedExpandLegacyMappedResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<WaitRequest, Operation> waitMethodDescriptor =
      ApiMethodDescriptor.<WaitRequest, Operation>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Echo/Wait")
          .setHttpMethod("POST")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<WaitRequest>newBuilder()
                  .setPath(
                      "/v1beta1/echo:wait",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<WaitRequest> serializer = ProtoRestSerializer.create();
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<WaitRequest> serializer = ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create()
                              .toBody("*", request.toBuilder().build(), true))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Operation>newBuilder()
                  .setDefaultInstance(Operation.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .setOperationSnapshotFactory(
              (WaitRequest request, Operation response) ->
                  HttpJsonOperationSnapshot.create(response))
          .build();

  private static final ApiMethodDescriptor<BlockRequest, BlockResponse> blockMethodDescriptor =
      ApiMethodDescriptor.<BlockRequest, BlockResponse>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Echo/Block")
          .setHttpMethod("POST")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<BlockRequest>newBuilder()
                  .setPath(
                      "/v1beta1/echo:block",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<BlockRequest> serializer = ProtoRestSerializer.create();
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<BlockRequest> serializer = ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create()
                              .toBody("*", request.toBuilder().build(), true))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<BlockResponse>newBuilder()
                  .setDefaultInstance(BlockResponse.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private final UnaryCallable<EchoRequest, EchoResponse> echoCallable;
  private final ServerStreamingCallable<ExpandRequest, EchoResponse> expandCallable;
  private final UnaryCallable<PagedExpandRequest, PagedExpandResponse> pagedExpandCallable;
  private final UnaryCallable<PagedExpandRequest, PagedExpandPagedResponse>
      pagedExpandPagedCallable;
  private final UnaryCallable<PagedExpandLegacyRequest, PagedExpandResponse>
      pagedExpandLegacyCallable;
  private final UnaryCallable<PagedExpandRequest, PagedExpandLegacyMappedResponse>
      pagedExpandLegacyMappedCallable;
  private final UnaryCallable<PagedExpandRequest, PagedExpandLegacyMappedPagedResponse>
      pagedExpandLegacyMappedPagedCallable;
  private final UnaryCallable<WaitRequest, Operation> waitCallable;
  private final OperationCallable<WaitRequest, WaitResponse, WaitMetadata> waitOperationCallable;
  private final UnaryCallable<BlockRequest, BlockResponse> blockCallable;

  private final BackgroundResource backgroundResources;
  private final HttpJsonOperationsStub httpJsonOperationsStub;
  private final HttpJsonStubCallableFactory callableFactory;

  public static final HttpJsonEchoStub create(EchoStubSettings settings) throws IOException {
    return new HttpJsonEchoStub(settings, ClientContext.create(settings));
  }

  public static final HttpJsonEchoStub create(ClientContext clientContext) throws IOException {
    return new HttpJsonEchoStub(EchoStubSettings.newHttpJsonBuilder().build(), clientContext);
  }

  public static final HttpJsonEchoStub create(
      ClientContext clientContext, HttpJsonStubCallableFactory callableFactory) throws IOException {
    return new HttpJsonEchoStub(
        EchoStubSettings.newHttpJsonBuilder().build(), clientContext, callableFactory);
  }

  /**
   * Constructs an instance of HttpJsonEchoStub, using the given settings. This is protected so that
   * it is easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected HttpJsonEchoStub(EchoStubSettings settings, ClientContext clientContext)
      throws IOException {
    this(settings, clientContext, new HttpJsonEchoCallableFactory());
  }

  /**
   * Constructs an instance of HttpJsonEchoStub, using the given settings. This is protected so that
   * it is easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected HttpJsonEchoStub(
      EchoStubSettings settings,
      ClientContext clientContext,
      HttpJsonStubCallableFactory callableFactory)
      throws IOException {
    this.callableFactory = callableFactory;
    this.httpJsonOperationsStub =
        HttpJsonOperationsStub.create(clientContext, callableFactory, typeRegistry);

    HttpJsonCallSettings<EchoRequest, EchoResponse> echoTransportSettings =
        HttpJsonCallSettings.<EchoRequest, EchoResponse>newBuilder()
            .setMethodDescriptor(echoMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<ExpandRequest, EchoResponse> expandTransportSettings =
        HttpJsonCallSettings.<ExpandRequest, EchoResponse>newBuilder()
            .setMethodDescriptor(expandMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<PagedExpandRequest, PagedExpandResponse> pagedExpandTransportSettings =
        HttpJsonCallSettings.<PagedExpandRequest, PagedExpandResponse>newBuilder()
            .setMethodDescriptor(pagedExpandMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<PagedExpandLegacyRequest, PagedExpandResponse>
        pagedExpandLegacyTransportSettings =
            HttpJsonCallSettings.<PagedExpandLegacyRequest, PagedExpandResponse>newBuilder()
                .setMethodDescriptor(pagedExpandLegacyMethodDescriptor)
                .setTypeRegistry(typeRegistry)
                .build();
    HttpJsonCallSettings<PagedExpandRequest, PagedExpandLegacyMappedResponse>
        pagedExpandLegacyMappedTransportSettings =
            HttpJsonCallSettings.<PagedExpandRequest, PagedExpandLegacyMappedResponse>newBuilder()
                .setMethodDescriptor(pagedExpandLegacyMappedMethodDescriptor)
                .setTypeRegistry(typeRegistry)
                .build();
    HttpJsonCallSettings<WaitRequest, Operation> waitTransportSettings =
        HttpJsonCallSettings.<WaitRequest, Operation>newBuilder()
            .setMethodDescriptor(waitMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<BlockRequest, BlockResponse> blockTransportSettings =
        HttpJsonCallSettings.<BlockRequest, BlockResponse>newBuilder()
            .setMethodDescriptor(blockMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();

    this.echoCallable =
        callableFactory.createUnaryCallable(
            echoTransportSettings, settings.echoSettings(), clientContext);
    this.expandCallable =
        callableFactory.createServerStreamingCallable(
            expandTransportSettings, settings.expandSettings(), clientContext);
    this.pagedExpandCallable =
        callableFactory.createUnaryCallable(
            pagedExpandTransportSettings, settings.pagedExpandSettings(), clientContext);
    this.pagedExpandPagedCallable =
        callableFactory.createPagedCallable(
            pagedExpandTransportSettings, settings.pagedExpandSettings(), clientContext);
    this.pagedExpandLegacyCallable =
        callableFactory.createUnaryCallable(
            pagedExpandLegacyTransportSettings,
            settings.pagedExpandLegacySettings(),
            clientContext);
    this.pagedExpandLegacyMappedCallable =
        callableFactory.createUnaryCallable(
            pagedExpandLegacyMappedTransportSettings,
            settings.pagedExpandLegacyMappedSettings(),
            clientContext);
    this.pagedExpandLegacyMappedPagedCallable =
        callableFactory.createPagedCallable(
            pagedExpandLegacyMappedTransportSettings,
            settings.pagedExpandLegacyMappedSettings(),
            clientContext);
    this.waitCallable =
        callableFactory.createUnaryCallable(
            waitTransportSettings, settings.waitSettings(), clientContext);
    this.waitOperationCallable =
        callableFactory.createOperationCallable(
            waitTransportSettings,
            settings.waitOperationSettings(),
            clientContext,
            httpJsonOperationsStub);
    this.blockCallable =
        callableFactory.createUnaryCallable(
            blockTransportSettings, settings.blockSettings(), clientContext);

    this.backgroundResources =
        new BackgroundResourceAggregation(clientContext.getBackgroundResources());
  }

  @InternalApi
  public static List<ApiMethodDescriptor> getMethodDescriptors() {
    List<ApiMethodDescriptor> methodDescriptors = new ArrayList<>();
    methodDescriptors.add(echoMethodDescriptor);
    methodDescriptors.add(expandMethodDescriptor);
    methodDescriptors.add(pagedExpandMethodDescriptor);
    methodDescriptors.add(pagedExpandLegacyMethodDescriptor);
    methodDescriptors.add(pagedExpandLegacyMappedMethodDescriptor);
    methodDescriptors.add(waitMethodDescriptor);
    methodDescriptors.add(blockMethodDescriptor);
    return methodDescriptors;
  }

  public HttpJsonOperationsStub getHttpJsonOperationsStub() {
    return httpJsonOperationsStub;
  }

  @Override
  public UnaryCallable<EchoRequest, EchoResponse> echoCallable() {
    return echoCallable;
  }

  @Override
  public ServerStreamingCallable<ExpandRequest, EchoResponse> expandCallable() {
    return expandCallable;
  }

  @Override
  public UnaryCallable<PagedExpandRequest, PagedExpandResponse> pagedExpandCallable() {
    return pagedExpandCallable;
  }

  @Override
  public UnaryCallable<PagedExpandRequest, PagedExpandPagedResponse> pagedExpandPagedCallable() {
    return pagedExpandPagedCallable;
  }

  @Override
  public UnaryCallable<PagedExpandLegacyRequest, PagedExpandResponse> pagedExpandLegacyCallable() {
    return pagedExpandLegacyCallable;
  }

  @Override
  public UnaryCallable<PagedExpandRequest, PagedExpandLegacyMappedResponse>
      pagedExpandLegacyMappedCallable() {
    return pagedExpandLegacyMappedCallable;
  }

  @Override
  public UnaryCallable<PagedExpandRequest, PagedExpandLegacyMappedPagedResponse>
      pagedExpandLegacyMappedPagedCallable() {
    return pagedExpandLegacyMappedPagedCallable;
  }

  @Override
  public UnaryCallable<WaitRequest, Operation> waitCallable() {
    return waitCallable;
  }

  @Override
  public OperationCallable<WaitRequest, WaitResponse, WaitMetadata> waitOperationCallable() {
    return waitOperationCallable;
  }

  @Override
  public UnaryCallable<BlockRequest, BlockResponse> blockCallable() {
    return blockCallable;
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
