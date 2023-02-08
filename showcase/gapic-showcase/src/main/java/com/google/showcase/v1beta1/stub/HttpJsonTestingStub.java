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

import static com.google.showcase.v1beta1.TestingClient.ListSessionsPagedResponse;
import static com.google.showcase.v1beta1.TestingClient.ListTestsPagedResponse;

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
import com.google.showcase.v1beta1.CreateSessionRequest;
import com.google.showcase.v1beta1.DeleteSessionRequest;
import com.google.showcase.v1beta1.DeleteTestRequest;
import com.google.showcase.v1beta1.GetSessionRequest;
import com.google.showcase.v1beta1.ListSessionsRequest;
import com.google.showcase.v1beta1.ListSessionsResponse;
import com.google.showcase.v1beta1.ListTestsRequest;
import com.google.showcase.v1beta1.ListTestsResponse;
import com.google.showcase.v1beta1.ReportSessionRequest;
import com.google.showcase.v1beta1.ReportSessionResponse;
import com.google.showcase.v1beta1.Session;
import com.google.showcase.v1beta1.VerifyTestRequest;
import com.google.showcase.v1beta1.VerifyTestResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * REST stub implementation for the Testing service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class HttpJsonTestingStub extends TestingStub {
  private static final TypeRegistry typeRegistry = TypeRegistry.newBuilder().build();

  private static final ApiMethodDescriptor<CreateSessionRequest, Session>
      createSessionMethodDescriptor =
          ApiMethodDescriptor.<CreateSessionRequest, Session>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Testing/CreateSession")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<CreateSessionRequest>newBuilder()
                      .setPath(
                          "/v1beta1/sessions",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<CreateSessionRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<CreateSessionRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("session", request.getSession(), false))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<Session>newBuilder()
                      .setDefaultInstance(Session.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<GetSessionRequest, Session> getSessionMethodDescriptor =
      ApiMethodDescriptor.<GetSessionRequest, Session>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Testing/GetSession")
          .setHttpMethod("GET")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<GetSessionRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{name=sessions/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<GetSessionRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "name", request.getName());
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<GetSessionRequest> serializer =
                            ProtoRestSerializer.create();
                        return fields;
                      })
                  .setRequestBodyExtractor(request -> null)
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Session>newBuilder()
                  .setDefaultInstance(Session.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<ListSessionsRequest, ListSessionsResponse>
      listSessionsMethodDescriptor =
          ApiMethodDescriptor.<ListSessionsRequest, ListSessionsResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Testing/ListSessions")
              .setHttpMethod("GET")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<ListSessionsRequest>newBuilder()
                      .setPath(
                          "/v1beta1/sessions",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<ListSessionsRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<ListSessionsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "pageSize", request.getPageSize());
                            serializer.putQueryParam(fields, "pageToken", request.getPageToken());
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<ListSessionsResponse>newBuilder()
                      .setDefaultInstance(ListSessionsResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<DeleteSessionRequest, Empty>
      deleteSessionMethodDescriptor =
          ApiMethodDescriptor.<DeleteSessionRequest, Empty>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Testing/DeleteSession")
              .setHttpMethod("DELETE")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<DeleteSessionRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=sessions/*}",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<DeleteSessionRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<DeleteSessionRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<Empty>newBuilder()
                      .setDefaultInstance(Empty.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<ReportSessionRequest, ReportSessionResponse>
      reportSessionMethodDescriptor =
          ApiMethodDescriptor.<ReportSessionRequest, ReportSessionResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Testing/ReportSession")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<ReportSessionRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=sessions/*}:report",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<ReportSessionRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<ReportSessionRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<ReportSessionResponse>newBuilder()
                      .setDefaultInstance(ReportSessionResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<ListTestsRequest, ListTestsResponse>
      listTestsMethodDescriptor =
          ApiMethodDescriptor.<ListTestsRequest, ListTestsResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Testing/ListTests")
              .setHttpMethod("GET")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<ListTestsRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{parent=sessions/*}/tests",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<ListTestsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "parent", request.getParent());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<ListTestsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "pageSize", request.getPageSize());
                            serializer.putQueryParam(fields, "pageToken", request.getPageToken());
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<ListTestsResponse>newBuilder()
                      .setDefaultInstance(ListTestsResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<DeleteTestRequest, Empty> deleteTestMethodDescriptor =
      ApiMethodDescriptor.<DeleteTestRequest, Empty>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Testing/DeleteTest")
          .setHttpMethod("DELETE")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<DeleteTestRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{name=sessions/*/tests/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<DeleteTestRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "name", request.getName());
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<DeleteTestRequest> serializer =
                            ProtoRestSerializer.create();
                        return fields;
                      })
                  .setRequestBodyExtractor(request -> null)
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Empty>newBuilder()
                  .setDefaultInstance(Empty.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<VerifyTestRequest, VerifyTestResponse>
      verifyTestMethodDescriptor =
          ApiMethodDescriptor.<VerifyTestRequest, VerifyTestResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Testing/VerifyTest")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<VerifyTestRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=sessions/*/tests/*}:check",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<VerifyTestRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<VerifyTestRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "answer", request.getAnswer());
                            serializer.putQueryParam(fields, "answers", request.getAnswersList());
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<VerifyTestResponse>newBuilder()
                      .setDefaultInstance(VerifyTestResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private final UnaryCallable<CreateSessionRequest, Session> createSessionCallable;
  private final UnaryCallable<GetSessionRequest, Session> getSessionCallable;
  private final UnaryCallable<ListSessionsRequest, ListSessionsResponse> listSessionsCallable;
  private final UnaryCallable<ListSessionsRequest, ListSessionsPagedResponse>
      listSessionsPagedCallable;
  private final UnaryCallable<DeleteSessionRequest, Empty> deleteSessionCallable;
  private final UnaryCallable<ReportSessionRequest, ReportSessionResponse> reportSessionCallable;
  private final UnaryCallable<ListTestsRequest, ListTestsResponse> listTestsCallable;
  private final UnaryCallable<ListTestsRequest, ListTestsPagedResponse> listTestsPagedCallable;
  private final UnaryCallable<DeleteTestRequest, Empty> deleteTestCallable;
  private final UnaryCallable<VerifyTestRequest, VerifyTestResponse> verifyTestCallable;

  private final BackgroundResource backgroundResources;
  private final HttpJsonStubCallableFactory callableFactory;

  public static final HttpJsonTestingStub create(TestingStubSettings settings) throws IOException {
    return new HttpJsonTestingStub(settings, ClientContext.create(settings));
  }

  public static final HttpJsonTestingStub create(ClientContext clientContext) throws IOException {
    return new HttpJsonTestingStub(TestingStubSettings.newHttpJsonBuilder().build(), clientContext);
  }

  public static final HttpJsonTestingStub create(
      ClientContext clientContext, HttpJsonStubCallableFactory callableFactory) throws IOException {
    return new HttpJsonTestingStub(
        TestingStubSettings.newHttpJsonBuilder().build(), clientContext, callableFactory);
  }

  /**
   * Constructs an instance of HttpJsonTestingStub, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected HttpJsonTestingStub(TestingStubSettings settings, ClientContext clientContext)
      throws IOException {
    this(settings, clientContext, new HttpJsonTestingCallableFactory());
  }

  /**
   * Constructs an instance of HttpJsonTestingStub, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected HttpJsonTestingStub(
      TestingStubSettings settings,
      ClientContext clientContext,
      HttpJsonStubCallableFactory callableFactory)
      throws IOException {
    this.callableFactory = callableFactory;

    HttpJsonCallSettings<CreateSessionRequest, Session> createSessionTransportSettings =
        HttpJsonCallSettings.<CreateSessionRequest, Session>newBuilder()
            .setMethodDescriptor(createSessionMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<GetSessionRequest, Session> getSessionTransportSettings =
        HttpJsonCallSettings.<GetSessionRequest, Session>newBuilder()
            .setMethodDescriptor(getSessionMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<ListSessionsRequest, ListSessionsResponse> listSessionsTransportSettings =
        HttpJsonCallSettings.<ListSessionsRequest, ListSessionsResponse>newBuilder()
            .setMethodDescriptor(listSessionsMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<DeleteSessionRequest, Empty> deleteSessionTransportSettings =
        HttpJsonCallSettings.<DeleteSessionRequest, Empty>newBuilder()
            .setMethodDescriptor(deleteSessionMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<ReportSessionRequest, ReportSessionResponse>
        reportSessionTransportSettings =
            HttpJsonCallSettings.<ReportSessionRequest, ReportSessionResponse>newBuilder()
                .setMethodDescriptor(reportSessionMethodDescriptor)
                .setTypeRegistry(typeRegistry)
                .build();
    HttpJsonCallSettings<ListTestsRequest, ListTestsResponse> listTestsTransportSettings =
        HttpJsonCallSettings.<ListTestsRequest, ListTestsResponse>newBuilder()
            .setMethodDescriptor(listTestsMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<DeleteTestRequest, Empty> deleteTestTransportSettings =
        HttpJsonCallSettings.<DeleteTestRequest, Empty>newBuilder()
            .setMethodDescriptor(deleteTestMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<VerifyTestRequest, VerifyTestResponse> verifyTestTransportSettings =
        HttpJsonCallSettings.<VerifyTestRequest, VerifyTestResponse>newBuilder()
            .setMethodDescriptor(verifyTestMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();

    this.createSessionCallable =
        callableFactory.createUnaryCallable(
            createSessionTransportSettings, settings.createSessionSettings(), clientContext);
    this.getSessionCallable =
        callableFactory.createUnaryCallable(
            getSessionTransportSettings, settings.getSessionSettings(), clientContext);
    this.listSessionsCallable =
        callableFactory.createUnaryCallable(
            listSessionsTransportSettings, settings.listSessionsSettings(), clientContext);
    this.listSessionsPagedCallable =
        callableFactory.createPagedCallable(
            listSessionsTransportSettings, settings.listSessionsSettings(), clientContext);
    this.deleteSessionCallable =
        callableFactory.createUnaryCallable(
            deleteSessionTransportSettings, settings.deleteSessionSettings(), clientContext);
    this.reportSessionCallable =
        callableFactory.createUnaryCallable(
            reportSessionTransportSettings, settings.reportSessionSettings(), clientContext);
    this.listTestsCallable =
        callableFactory.createUnaryCallable(
            listTestsTransportSettings, settings.listTestsSettings(), clientContext);
    this.listTestsPagedCallable =
        callableFactory.createPagedCallable(
            listTestsTransportSettings, settings.listTestsSettings(), clientContext);
    this.deleteTestCallable =
        callableFactory.createUnaryCallable(
            deleteTestTransportSettings, settings.deleteTestSettings(), clientContext);
    this.verifyTestCallable =
        callableFactory.createUnaryCallable(
            verifyTestTransportSettings, settings.verifyTestSettings(), clientContext);

    this.backgroundResources =
        new BackgroundResourceAggregation(clientContext.getBackgroundResources());
  }

  @InternalApi
  public static List<ApiMethodDescriptor> getMethodDescriptors() {
    List<ApiMethodDescriptor> methodDescriptors = new ArrayList<>();
    methodDescriptors.add(createSessionMethodDescriptor);
    methodDescriptors.add(getSessionMethodDescriptor);
    methodDescriptors.add(listSessionsMethodDescriptor);
    methodDescriptors.add(deleteSessionMethodDescriptor);
    methodDescriptors.add(reportSessionMethodDescriptor);
    methodDescriptors.add(listTestsMethodDescriptor);
    methodDescriptors.add(deleteTestMethodDescriptor);
    methodDescriptors.add(verifyTestMethodDescriptor);
    return methodDescriptors;
  }

  @Override
  public UnaryCallable<CreateSessionRequest, Session> createSessionCallable() {
    return createSessionCallable;
  }

  @Override
  public UnaryCallable<GetSessionRequest, Session> getSessionCallable() {
    return getSessionCallable;
  }

  @Override
  public UnaryCallable<ListSessionsRequest, ListSessionsResponse> listSessionsCallable() {
    return listSessionsCallable;
  }

  @Override
  public UnaryCallable<ListSessionsRequest, ListSessionsPagedResponse> listSessionsPagedCallable() {
    return listSessionsPagedCallable;
  }

  @Override
  public UnaryCallable<DeleteSessionRequest, Empty> deleteSessionCallable() {
    return deleteSessionCallable;
  }

  @Override
  public UnaryCallable<ReportSessionRequest, ReportSessionResponse> reportSessionCallable() {
    return reportSessionCallable;
  }

  @Override
  public UnaryCallable<ListTestsRequest, ListTestsResponse> listTestsCallable() {
    return listTestsCallable;
  }

  @Override
  public UnaryCallable<ListTestsRequest, ListTestsPagedResponse> listTestsPagedCallable() {
    return listTestsPagedCallable;
  }

  @Override
  public UnaryCallable<DeleteTestRequest, Empty> deleteTestCallable() {
    return deleteTestCallable;
  }

  @Override
  public UnaryCallable<VerifyTestRequest, VerifyTestResponse> verifyTestCallable() {
    return verifyTestCallable;
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
