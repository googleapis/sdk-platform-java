/*
 * Copyright 2024 Google LLC
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

package com.google.api.gax.grpc;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import com.google.api.gax.grpc.testing.FakeMethodDescriptor;
import com.google.api.gax.logging.LogData;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptors;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GrpcLoggingInterceptorTest {
  @Mock private Channel channel;

  @Mock private ClientCall<String, Integer> call;

  private static final MethodDescriptor<String, Integer> method = FakeMethodDescriptor.create();

  private static final Logger LOGGER = LoggerFactory.getLogger(GrpcLoggingInterceptorTest.class);
  /** Sets up mocks. */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    when(channel.newCall(Mockito.<MethodDescriptor<String, Integer>>any(), any(CallOptions.class)))
        .thenReturn(call);
  }

  @Test
  void testInterceptor_basic() {
    GrpcLoggingInterceptor interceptor = new GrpcLoggingInterceptor();
    Channel intercepted = ClientInterceptors.intercept(channel, interceptor);
    @SuppressWarnings("unchecked")
    ClientCall.Listener<Integer> listener = mock(ClientCall.Listener.class);
    ClientCall<String, Integer> interceptedCall = intercepted.newCall(method, CallOptions.DEFAULT);
    // Simulate starting the call
    interceptedCall.start(listener, new Metadata());
    // Verify that the underlying call's start() method is invoked
    verify(call).start(any(ClientCall.Listener.class), any(Metadata.class));

    // Simulate sending a message
    String requestMessage = "test request";
    interceptedCall.sendMessage(requestMessage);
    // Verify that the underlying call's sendMessage() method is invoked
    verify(call).sendMessage(requestMessage);
  }

  @Test
  void testInterceptor_responseListener() {
    GrpcLoggingInterceptor interceptor = spy(new GrpcLoggingInterceptor());
    Channel intercepted = ClientInterceptors.intercept(channel, interceptor);
    @SuppressWarnings("unchecked")
    ClientCall.Listener<Integer> listener = mock(ClientCall.Listener.class);
    ClientCall<String, Integer> interceptedCall = intercepted.newCall(method, CallOptions.DEFAULT);
    interceptedCall.start(listener, new Metadata());

    // Simulate respond interceptor calls
    Metadata responseHeaders = new Metadata();
    responseHeaders.put(
        Metadata.Key.of("test-header", Metadata.ASCII_STRING_MARSHALLER), "header-value");
    interceptor.currentListener.onHeaders(responseHeaders);

    interceptor.currentListener.onMessage(null);

    Status status = Status.OK;
    interceptor.currentListener.onClose(status, new Metadata());

    // --- Verify that the response listener's methods were called ---
    verify(interceptor)
        .recordResponseHeaders(eq(responseHeaders), any(LogData.Builder.class), any(Logger.class));
    verify(interceptor).recordResponsePayload(any(), any(LogData.Builder.class), any(Logger.class));
    verify(interceptor).logResponse(eq(status), any(LogData.Builder.class), any(Logger.class));
  }

  @Test
  void testLogRequestInfo() {

    TestAppender testAppender = setupTestLogger(GrpcLoggingInterceptorTest.class);
    GrpcLoggingInterceptor interceptor = new GrpcLoggingInterceptor();
    LogData.Builder logData =
        LogData.builder().serviceName("FakeClient").rpcName("FakeClient/fake-method");
    interceptor.logRequest(method, logData, LOGGER);

    Assertions.assertEquals(1, testAppender.events.size());
    assertEquals(Level.INFO, testAppender.events.get(0).getLevel());
    assertEquals(
        "{\"serviceName\":\"FakeClient\",\"message\":\"Sending gRPC request\",\"rpcName\":\"FakeClient/fake-method\"}",
        testAppender.events.get(0).getMessage());
    testAppender.stop();
  }

  @Test
  void TestLogResponseInfo() {
    TestAppender testAppender = setupTestLogger(GrpcLoggingInterceptorTest.class);
    GrpcLoggingInterceptor interceptor = new GrpcLoggingInterceptor();
    interceptor.logResponse(Status.CANCELLED, LogData.builder(), LOGGER);

    Assertions.assertEquals(1, testAppender.events.size());
    assertEquals(Level.INFO, testAppender.events.get(0).getLevel());
    assertEquals(
        "{\"response.status\":\"CANCELLED\",\"message\":\"Received Grpc response\"}",
        testAppender.events.get(0).getMessage());
    testAppender.stop();
  }

  private TestAppender setupTestLogger(Class<?> clazz) {
    TestAppender testAppender = new TestAppender();
    testAppender.start();
    Logger logger = LoggerFactory.getLogger(clazz);
    ((ch.qos.logback.classic.Logger) logger).addAppender(testAppender);
    return testAppender;
  }

  @Test
  void testExecuteWithTryCatch_NoException() {
    Runnable action = Mockito.mock(Runnable.class);
    GrpcLoggingInterceptor.executeWithTryCatch(action);
    // Verify that the action was executed
    Mockito.verify(action, Mockito.times(1)).run();
  }

  @Test
  void testExecuteWithTryCatch_WithException() {
    Runnable action = Mockito.mock(Runnable.class);
    Mockito.doThrow(new RuntimeException("Test Exception")).when(action).run();
    GrpcLoggingInterceptor.executeWithTryCatch(action);
    // Verify that the action was executed (despite the exception)
    Mockito.verify(action, Mockito.times(1)).run();
    // No exception should be thrown by executeWithTryCatch
  }

  @Test
  void testExecuteWithTryCatch_WithNoSuchMethodError() {
    Runnable action = Mockito.mock(Runnable.class);
    Mockito.doThrow(new NoSuchMethodError("Test Error")).when(action).run();
    GrpcLoggingInterceptor.executeWithTryCatch(action);
    // Verify that the action was executed (despite the error)
    Mockito.verify(action, Mockito.times(1)).run();
    // No error should be thrown by executeWithTryCatch
  }
}
