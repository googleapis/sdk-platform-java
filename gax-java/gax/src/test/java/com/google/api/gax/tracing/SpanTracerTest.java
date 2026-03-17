/*
 * Copyright 2026 Google LLC
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
package com.google.api.gax.tracing;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.ErrorDetails;
import com.google.api.gax.rpc.StatusCode;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonSyntaxException;
import com.google.protobuf.Any;
import com.google.rpc.ErrorInfo;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpanTracerTest {
  @Mock private TraceManager recorder;
  @Mock private TraceManager.Span attemptHandle;
  private SpanTracer tracer;
  private static final String ATTEMPT_SPAN_NAME = "Service/Method/attempt";

  @BeforeEach
  void setUp() {
    tracer = new SpanTracer(recorder, ApiTracerContext.empty(), ATTEMPT_SPAN_NAME);
  }

  @Test
  void testAttemptLifecycle_startsAndEndsAttemptSpan() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);
    tracer.attemptStarted(new Object(), 1);
    tracer.attemptSucceeded();

    verify(attemptHandle).end();
  }

  @Test
  void testAttemptStarted_includesLanguageAttribute() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    ArgumentCaptor<Map<String, Object>> attributesCaptor = ArgumentCaptor.forClass(Map.class);
    verify(recorder).createSpan(eq(ATTEMPT_SPAN_NAME), attributesCaptor.capture());

    assertThat(attributesCaptor.getValue())
        .containsEntry(SpanTracer.LANGUAGE_ATTRIBUTE, SpanTracer.DEFAULT_LANGUAGE);
  }

  @Test
  void testAttemptFailed_errorInfoReason() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    ErrorInfo errorInfo = ErrorInfo.newBuilder().setReason("RATE_LIMIT_EXCEEDED").build();
    ErrorDetails errorDetails =
        ErrorDetails.builder().setRawErrorMessages(ImmutableList.of(Any.pack(errorInfo))).build();
    Throwable cause = new Throwable("message");

    ApiException apiException =
        new ApiException(
            cause,
            new StatusCode() {
              @Override
              public Code getCode() {
                return Code.UNAVAILABLE;
              }

              @Override
              public Object getTransportCode() {
                return null;
              }
            },
            true,
            errorDetails);

    tracer.attemptFailedRetriesExhausted(apiException);

    verify(attemptHandle)
        .addAttribute(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE, "RATE_LIMIT_EXCEEDED");
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_specificServerErrorCodeGrpc() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    ApiException apiException =
        new ApiException(
            "message",
            null,
            new StatusCode() {
              @Override
              public Code getCode() {
                return Code.PERMISSION_DENIED;
              }

              @Override
              public Object getTransportCode() {
                return "PERMISSION_DENIED";
              }
            },
            true);

    tracer.attemptFailedRetriesExhausted(apiException);

    verify(attemptHandle)
        .addAttribute(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE, "PERMISSION_DENIED");
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_specificServerErrorCodeHttp() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    ApiException apiException =
        new ApiException(
            "message",
            null,
            new StatusCode() {
              @Override
              public Code getCode() {
                return Code.PERMISSION_DENIED;
              }

              @Override
              public Object getTransportCode() {
                return 403;
              }
            },
            true);

    tracer.attemptFailedRetriesExhausted(apiException);

    verify(attemptHandle).addAttribute(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE, "403");
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_clientTimeout() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new SocketTimeoutException());

    verify(attemptHandle)
        .addAttribute(
            ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE,
            ErrorTypeUtil.ErrorType.CLIENT_TIMEOUT.toString());
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_clientConnectionError() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new ConnectException("connection failed"));

    verify(attemptHandle)
        .addAttribute(
            ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE,
            ErrorTypeUtil.ErrorType.CLIENT_CONNECTION_ERROR.toString());
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_clientAuthenticationError() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new TestGoogleAuthException());

    verify(attemptHandle)
        .addAttribute(
            ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE,
            ErrorTypeUtil.ErrorType.CLIENT_AUTHENTICATION_ERROR.toString());
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_clientResponseDecodeError() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new JsonSyntaxException("bad json"));

    verify(attemptHandle)
        .addAttribute(
            ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE,
            ErrorTypeUtil.ErrorType.CLIENT_RESPONSE_DECODE_ERROR.toString());
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_clientRedirectError() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new RedirectException("redirect failed"));

    verify(attemptHandle)
        .addAttribute(
            ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE,
            ErrorTypeUtil.ErrorType.CLIENT_REDIRECT_ERROR.toString());
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_clientRequestBodyError() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new TestRestSerializationException());

    verify(attemptHandle)
        .addAttribute(
            ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE,
            ErrorTypeUtil.ErrorType.CLIENT_REQUEST_BODY_ERROR.toString());
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_clientRequestError() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new IllegalArgumentException());

    verify(attemptHandle)
        .addAttribute(
            ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE,
            ErrorTypeUtil.ErrorType.CLIENT_REQUEST_ERROR.toString());
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_clientUnknownError() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new UnknownClientException());

    verify(attemptHandle)
        .addAttribute(
            ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE,
            ErrorTypeUtil.ErrorType.CLIENT_UNKNOWN_ERROR.toString());
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_languageSpecificFallback() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new IllegalStateException("illegal state"));

    verify(attemptHandle)
        .addAttribute(ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE, "IllegalStateException");
    verify(attemptHandle).end();
  }

  @Test
  void testAttemptFailed_internalFallback() {
    when(recorder.createSpan(eq(ATTEMPT_SPAN_NAME), anyMap())).thenReturn(attemptHandle);

    tracer.attemptStarted(new Object(), 1);

    tracer.attemptFailedRetriesExhausted(new Throwable() {});

    // For an anonymous inner class Throwable, getSimpleName() is empty string, which triggers the
    // fallback
    verify(attemptHandle)
        .addAttribute(
            ObservabilityAttributes.ERROR_TYPE_ATTRIBUTE,
            ErrorTypeUtil.ErrorType.INTERNAL.toString());
    verify(attemptHandle).end();
  }

  private static class TestGoogleAuthException extends RuntimeException {}

  private static class RedirectException extends RuntimeException {
    public RedirectException(String message) {
      super(message);
    }
  }

  private static class TestRestSerializationException extends RuntimeException {}

  private static class UnknownClientException extends RuntimeException {}
}
