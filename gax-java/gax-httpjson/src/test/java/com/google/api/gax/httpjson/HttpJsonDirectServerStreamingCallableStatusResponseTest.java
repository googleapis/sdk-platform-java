/*
 * Copyright 2022 Google LLC
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google LLC nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.google.api.gax.httpjson;

import static org.mockito.Mockito.when;

import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.gax.httpjson.ApiMethodDescriptor.MethodType;
import com.google.api.gax.rpc.CancelledException;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.EndpointContext;
import com.google.api.gax.rpc.ServerStreamingCallSettings;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.StateCheckingResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.auth.Credentials;
import com.google.common.truth.ThrowableSubject;
import com.google.common.truth.Truth;
import com.google.protobuf.Any;
import com.google.protobuf.Field;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.TypeRegistry;
import com.google.protobuf.util.JsonFormat;
import com.google.rpc.Code;
import com.google.rpc.DebugInfo;
import com.google.rpc.ErrorDetailsProto;
import com.google.rpc.Status;
import com.google.type.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Basically copied from {@link HttpJsonDirectServerStreamingCallableTest}, but using {@link
 * MockHttpTransport} and {@link MockLowLevelHttpResponse} to test {@link Any} type in the server
 * streaming response.
 */
class HttpJsonDirectServerStreamingCallableStatusResponseTest {
  private static final Color DEFAULT_REQUEST = Color.newBuilder().setRed(0.5f).build();

  private static final Status STATUS_OK_1 =
      Status.newBuilder().setCode(Code.OK.getNumber()).setMessage("ok1").build();
  private static final Status STATUS_OK_2 =
      Status.newBuilder().setCode(Code.OK.getNumber()).setMessage("ok2").build();
  private static final Status STATUS_INVALID_ARGUMENT_WITH_DEBUG_INFO =
      Status.newBuilder()
          .setCode(Code.INVALID_ARGUMENT.getNumber())
          .addDetails(Any.pack(DebugInfo.newBuilder().setDetail("invalid key 'k1'").build()))
          .build();

  private ServerStreamingCallable<Color, Status> streamingCallable;
  private ManagedHttpJsonChannel channel;
  private ExecutorService executorService;
  private MockLowLevelHttpResponse mockLowLevelHttpResponse;

  @BeforeEach
  void initialize() throws IOException {
    executorService = Executors.newFixedThreadPool(2);

    this.mockLowLevelHttpResponse = Mockito.mock(MockLowLevelHttpResponse.class);
    MockHttpTransport mockHttpTransport =
        new MockHttpTransport.Builder().setLowLevelHttpResponse(mockLowLevelHttpResponse).build();
    channel =
        new ManagedHttpJsonInterceptorChannel(
            ManagedHttpJsonChannel.newBuilder()
                .setEndpoint("google.com:443")
                .setExecutor(executorService)
                .setHttpTransport(mockHttpTransport)
                .build(),
            new HttpJsonHeaderInterceptor(Collections.singletonMap("header-key", "headerValue")));

    EndpointContext endpointContext = Mockito.mock(EndpointContext.class);
    Mockito.doNothing()
        .when(endpointContext)
        .validateUniverseDomain(
            Mockito.any(Credentials.class), Mockito.any(HttpJsonStatusCode.class));
    ClientContext clientContext =
        ClientContext.newBuilder()
            .setTransportChannel(HttpJsonTransportChannel.create(channel))
            .setDefaultCallContext(
                HttpJsonCallContext.of(channel, HttpJsonCallOptions.DEFAULT)
                    .withEndpointContext(endpointContext))
            .build();

    ApiMethodDescriptor<Color, Status> methodServerStreamingRecognize =
        ApiMethodDescriptor.<Color, Status>newBuilder()
            .setFullMethodName("google.cloud.v1.Fake/ServerStreamingRecognize")
            .setHttpMethod("POST")
            .setRequestFormatter(
                ProtoMessageRequestFormatter.<Color>newBuilder()
                    .setPath(
                        "/fake/v1/recognize/{blue}",
                        request -> {
                          Map<String, String> fields = new HashMap<>();
                          ProtoRestSerializer<Field> serializer = ProtoRestSerializer.create();
                          serializer.putPathParam(fields, "blue", request.getBlue());
                          return fields;
                        })
                    .setQueryParamsExtractor(
                        request -> {
                          Map<String, List<String>> fields = new HashMap<>();
                          ProtoRestSerializer<Field> serializer = ProtoRestSerializer.create();
                          serializer.putQueryParam(fields, "red", request.getRed());
                          return fields;
                        })
                    .setRequestBodyExtractor(
                        request ->
                            ProtoRestSerializer.create()
                                .toBody(
                                    "*", request.toBuilder().clearBlue().clearRed().build(), false))
                    .build())
            .setResponseParser(
                ProtoMessageResponseParser.<Status>newBuilder()
                    .setDefaultInstance(Status.getDefaultInstance())
                    .build())
            .setType(MethodType.SERVER_STREAMING)
            .build();

    HttpJsonCallSettings<Color, Status> httpJsoncallSettings =
        HttpJsonCallSettings.<Color, Status>newBuilder()
            .setMethodDescriptor(methodServerStreamingRecognize)
            .setTypeRegistry(TypeRegistry.getEmptyTypeRegistry()) // no types registered
            .build();

    ServerStreamingCallSettings<Color, Status> streamingCallSettings =
        ServerStreamingCallSettings.<Color, Status>newBuilder().build();

    streamingCallable =
        HttpJsonCallableFactory.createServerStreamingCallable(
            httpJsoncallSettings, streamingCallSettings, clientContext);
  }

  @AfterEach
  void destroy() {
    executorService.shutdown();
    channel.shutdown();
  }

  @Test
  public void testOnSuccessfulResponse() throws IOException, InterruptedException {
    mockLowLevelHttpResponse(
        getTypeRegistryWithErrorDetailsProtoTypes(), 200, STATUS_OK_1, STATUS_OK_2);
    TestResponseObserver<Status> responseObserver = new TestResponseObserver<>();

    streamingCallable.call(DEFAULT_REQUEST, responseObserver);

    Truth.assertThat(responseObserver.await(60, TimeUnit.SECONDS)).isTrue();

    Truth.assertThat(responseObserver.responses).containsExactly(STATUS_OK_1, STATUS_OK_2);
  }

  // Due to the empty default registry, a response with Any value will throw a
  // InvalidProtobufMessageException that
  // cannot resolve the typeUrl.
  // https://github.com/googleapis/sdk-platform-java/issues/2237#issuecomment-2655697832
  @Test
  public void testOnErrorResponse() throws InterruptedException, IOException {
    mockLowLevelHttpResponse(
        getTypeRegistryWithErrorDetailsProtoTypes(), 200, STATUS_INVALID_ARGUMENT_WITH_DEBUG_INFO);

    TestResponseObserver<Status> responseObserver = new TestResponseObserver<>();

    streamingCallable.call(DEFAULT_REQUEST, responseObserver);

    Truth.assertThat(responseObserver.await(60, TimeUnit.SECONDS)).isTrue();

    // TODO: Add proto types within `com.google.grpc` to default TypeRegistry for server streaming
    // callable, to fix type resolution error in parsing `Any` value in com.google.grpc.Status. Once
    // fixed, this test will return STATUS_INVALID_ARGUMENT_WITH_DEBUG_INFO, not throwing an
    // InvalidProtocolBufferException.
    ThrowableSubject error = Truth.assertThat(responseObserver.error);
    error.isInstanceOf(CancelledException.class);
    error.hasMessageThat().contains("Exception in message delivery");

    ThrowableSubject cause1 = Truth.assertThat(responseObserver.error).hasCauseThat();
    cause1.isInstanceOf(HttpJsonStatusRuntimeException.class);
    cause1.hasMessageThat().contains("Exception in message delivery");

    ThrowableSubject cause2 = cause1.hasCauseThat();
    cause2.isInstanceOf(RestSerializationException.class);
    cause2.hasMessageThat().contains("Failed to parse response message");

    ThrowableSubject cause3 = cause2.hasCauseThat();
    cause3.isInstanceOf(InvalidProtocolBufferException.class);
    cause3
        .hasMessageThat()
        .contains("Cannot resolve type: type.googleapis.com/google.rpc.DebugInfo");
  }

  /**
   * Gets a {@link TypeRegistry} that includes protobuf types in {@link ErrorDetailsProto}. That
   * could be used to print JSON string for {@link Any} values such as {@link DebugInfo}.
   */
  public static TypeRegistry getTypeRegistryWithErrorDetailsProtoTypes() {
    return TypeRegistry.newBuilder()
        .add(ErrorDetailsProto.getDescriptor().getMessageTypes())
        .build();
  }

  /**
   * Mocks the response with the status code and streaming messages.
   *
   * @param typeRegistry {@link TypeRegistry} to serialize the messages. This needs to include the
   *     types of <i>typeUrls</i> of {@link Any} values.
   * @param statusCode HTTP status code. For example, 200 for success.
   * @param streamingMessages A list of protobuf messages to return as the server streaming
   *     response.
   */
  public void mockLowLevelHttpResponse(
      TypeRegistry typeRegistry, int statusCode, Message... streamingMessages) throws IOException {
    JsonFormat.Printer printer = JsonFormat.printer().usingTypeRegistry(typeRegistry);
    List<String> jsonStrings = new ArrayList<>();
    for (Message message : streamingMessages) {
      jsonStrings.add(printer.print(message));
    }

    StringBuilder response = new StringBuilder();
    response.append("[");
    for (int i = 0; i < jsonStrings.size(); i++) {
      if (i > 0) {
        response.append(",");
      }
      response.append(jsonStrings.get(i));
    }
    response.append("]");

    when(mockLowLevelHttpResponse.getStatusCode()).thenReturn(statusCode);
    when(mockLowLevelHttpResponse.getContent())
        .thenReturn(new ByteArrayInputStream(response.toString().getBytes(StandardCharsets.UTF_8)));
  }

  static class TestResponseObserver<ResT> extends StateCheckingResponseObserver<ResT> {
    private final CountDownLatch latch;

    volatile StreamController controller;
    volatile List<ResT> responses = new ArrayList<>();
    @Nullable volatile Throwable error; // null if no error
    volatile boolean completed;

    TestResponseObserver() {
      this.latch = new CountDownLatch(1);
    }

    @Override
    protected void onStartImpl(StreamController controller) {
      this.controller = controller;
    }

    @Override
    protected void onResponseImpl(ResT value) {
      responses.add(value);
      latch.countDown();
    }

    @Override
    protected void onErrorImpl(Throwable t) {
      error = t;
      latch.countDown();
    }

    @Override
    protected void onCompleteImpl() {
      completed = true;
      latch.countDown();
    }

    /** Waits for closing or completion. */
    public boolean await(long timeout, TimeUnit timeUnit) throws InterruptedException {
      return latch.await(timeout, timeUnit);
    }
  }
}
