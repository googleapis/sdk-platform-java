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

import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.StatusCode;
import com.google.common.base.Strings;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import javax.annotation.Nullable;

class ObservabilityUtils {

  enum ErrorType {
    CLIENT_TIMEOUT,
    CLIENT_CONNECTION_ERROR,
    CLIENT_REQUEST_ERROR,
    CLIENT_REQUEST_BODY_ERROR,
    CLIENT_RESPONSE_DECODE_ERROR,
    CLIENT_REDIRECT_ERROR,
    CLIENT_AUTHENTICATION_ERROR,
    CLIENT_UNKNOWN_ERROR,
    INTERNAL;

    @Override
    public String toString() {
      return name();
    }
  }

  private static final List<Class<? extends Throwable>> CLIENT_TIMEOUT_CLASSES =
      Arrays.asList(
          java.util.concurrent.TimeoutException.class, java.net.SocketTimeoutException.class);
  private static final List<String> CLIENT_TIMEOUT_NAMES =
      Collections.singletonList("WatchdogTimeoutException");

  private static final List<Class<? extends Throwable>> CLIENT_CONNECTION_ERROR_CLASSES =
      Arrays.asList(
          java.net.ConnectException.class,
          java.net.UnknownHostException.class,
          java.nio.channels.UnresolvedAddressException.class);
  private static final List<String> CLIENT_CONNECTION_ERROR_NAMES =
      Collections.singletonList("ConnectException");

  private static final List<String> CLIENT_AUTH_ERROR_SUBSTRINGS =
      Arrays.asList("CredentialsException", "AuthenticationException");

  private static final List<String> CLIENT_RESPONSE_DECODE_ERROR_SUBSTRINGS =
      Arrays.asList("ProtocolBufferParsingException", "DecodeException");

  private static final List<String> CLIENT_REDIRECT_ERROR_SUBSTRINGS =
      Collections.singletonList("RedirectException");

  private static final List<String> CLIENT_REQUEST_BODY_ERROR_SUBSTRINGS =
      Collections.singletonList("RequestBodyException");

  private static final List<String> CLIENT_REQUEST_ERROR_SUBSTRINGS =
      Collections.singletonList("RequestException");

  private static final List<String> CLIENT_UNKNOWN_ERROR_SUBSTRINGS =
      Collections.singletonList("UnknownClientException");

  /**
   * Extracts a low-cardinality string representing the specific classification of the error to be
   * used in the {@link ObservabilityAttributes#ERROR_TYPE_ATTRIBUTE} attribute.
   *
   * <p>This value is determined based on the following priority:
   *
   * <ol>
   *   <li><b>{@code google.rpc.ErrorInfo.reason}:</b> If the error response from the service
   *       includes {@code google.rpc.ErrorInfo} details, the reason field (e.g.,
   *       "RATE_LIMIT_EXCEEDED", "SERVICE_DISABLED") will be used. This offers the most precise
   *       error cause.
   *   <li><b>Specific Server Error Code:</b> If no {@code ErrorInfo.reason} is available, but a
   *       server error code was received:
   *       <ul>
   *         <li>For HTTP: The HTTP status code (e.g., "403", "503").
   *         <li>For gRPC: The gRPC status code name (e.g., "PERMISSION_DENIED", "UNAVAILABLE").
   *       </ul>
   *   <li><b>Client-Side Network/Operational Errors:</b> For errors occurring within the client
   *       library or network stack, mapping to specific enum representations from {@link
   *       ErrorType}:
   *       <ul>
   *         <li>{@code CLIENT_TIMEOUT}: A client-configured timeout was reached.
   *         <li>{@code CLIENT_CONNECTION_ERROR}: Failure to establish the network connection (DNS,
   *             TCP, TLS).
   *         <li>{@code CLIENT_REQUEST_ERROR}: Client-side issue forming or sending the request.
   *         <li>{@code CLIENT_REQUEST_BODY_ERROR}: Error streaming the request body.
   *         <li>{@code CLIENT_RESPONSE_DECODE_ERROR}: Client-side error decoding the response body.
   *         <li>{@code CLIENT_REDIRECT_ERROR}: Problem handling HTTP redirects.
   *         <li>{@code CLIENT_AUTHENTICATION_ERROR}: Error during credential acquisition or
   *             application.
   *         <li>{@code CLIENT_UNKNOWN_ERROR}: Other unclassified client-side network or protocol
   *             errors.
   *       </ul>
   *   <li><b>Language-specific error type:</b> The class or struct name of the exception or error
   *       if available. This must be low-cardinality, meaning it returns the short name of the
   *       exception class (e.g. {@code "IllegalStateException"}) rather than its message.
   *   <li><b>Internal Fallback:</b> If the error doesn't fit any of the above categories, {@code
   *       "INTERNAL"} will be used, indicating an unexpected issue within the client library's own
   *       logic.
   * </ol>
   *
   * @param error the Throwable from which to extract the error type string.
   * @return a low-cardinality string representing the specific error type, or {@code null} if the
   *     provided error is {@code null}.
   */
  static String extractErrorType(@Nullable Throwable error) {
    if (error == null) {
      return null;
    }

    // 1. & 2. Extract error info reason or server status code
    if (error instanceof ApiException) {
      String errorType = extractFromApiException((ApiException) error);
      if (errorType != null) {
        return errorType;
      }
    }

    // 3. Attempt client side error
    String clientError = getClientSideError(error);
    if (clientError != null) {
      return clientError;
    }

    // 4. Language-specific error type fallback
    String exceptionName = error.getClass().getSimpleName();
    if (exceptionName != null && !exceptionName.isEmpty()) {
      return exceptionName;
    }

    // 5. Internal Fallback
    return ErrorType.INTERNAL.toString();
  }

  @Nullable
  private static String extractFromApiException(ApiException apiException) {
    // 1. Check for ErrorInfo.reason
    String reason = apiException.getReason();
    if (!Strings.isNullOrEmpty(reason)) {
      return reason;
    }

    // 2. Specific Server Error Code
    if (apiException.getStatusCode() != null) {
      Object transportCode = apiException.getStatusCode().getTransportCode();
      if (transportCode instanceof Integer) {
        // HTTP Status Code
        return String.valueOf(transportCode);
      } else if (apiException.getStatusCode().getCode() != null) {
        // gRPC Status Code name
        return apiException.getStatusCode().getCode().name();
      }
    }
    return null;
  }

  @Nullable
  private static String getClientSideError(Throwable error) {
    if (isInstanceof(error, CLIENT_TIMEOUT_CLASSES)) {
      return ErrorType.CLIENT_TIMEOUT.toString();
    }
    if (isInstanceof(error, CLIENT_CONNECTION_ERROR_CLASSES)) {
      return ErrorType.CLIENT_CONNECTION_ERROR.toString();
    }

    String exceptionName = error.getClass().getSimpleName();

    if (CLIENT_TIMEOUT_NAMES.contains(exceptionName)) {
      return ErrorType.CLIENT_TIMEOUT.toString();
    }
    if (CLIENT_CONNECTION_ERROR_NAMES.contains(exceptionName)) {
      return ErrorType.CLIENT_CONNECTION_ERROR.toString();
    }
    if (nameContains(exceptionName, CLIENT_AUTH_ERROR_SUBSTRINGS)) {
      return ErrorType.CLIENT_AUTHENTICATION_ERROR.toString();
    }
    if (nameContains(exceptionName, CLIENT_RESPONSE_DECODE_ERROR_SUBSTRINGS)) {
      return ErrorType.CLIENT_RESPONSE_DECODE_ERROR.toString();
    }
    if (nameContains(exceptionName, CLIENT_REDIRECT_ERROR_SUBSTRINGS)) {
      return ErrorType.CLIENT_REDIRECT_ERROR.toString();
    }
    if (nameContains(exceptionName, CLIENT_REQUEST_BODY_ERROR_SUBSTRINGS)) {
      return ErrorType.CLIENT_REQUEST_BODY_ERROR.toString();
    }
    if (nameContains(exceptionName, CLIENT_REQUEST_ERROR_SUBSTRINGS)) {
      return ErrorType.CLIENT_REQUEST_ERROR.toString();
    }
    if (nameContains(exceptionName, CLIENT_UNKNOWN_ERROR_SUBSTRINGS)) {
      return ErrorType.CLIENT_UNKNOWN_ERROR.toString();
    }

    return null;
  }

  private static boolean isInstanceof(Throwable error, List<Class<? extends Throwable>> classes) {
    for (Class<? extends Throwable> clazz : classes) {
      if (clazz.isInstance(error)) {
        return true;
      }
    }
    return false;
  }

  private static boolean nameContains(String name, List<String> substrings) {
    for (String sub : substrings) {
      if (name.contains(sub)) {
        return true;
      }
    }
    return false;
  }

  /** Function to extract the status of the error as a string */
  static String extractStatus(@Nullable Throwable error) {
    final String statusString;

    if (error == null) {
      return StatusCode.Code.OK.toString();
    } else if (error instanceof CancellationException) {
      statusString = StatusCode.Code.CANCELLED.toString();
    } else if (error instanceof ApiException) {
      statusString = ((ApiException) error).getStatusCode().getCode().toString();
    } else {
      statusString = StatusCode.Code.UNKNOWN.toString();
    }

    return statusString;
  }

  static Attributes toOtelAttributes(Map<String, Object> attributes) {
    AttributesBuilder attributesBuilder = Attributes.builder();
    if (attributes == null) {
      return attributesBuilder.build();
    }
    attributes.forEach(
        (k, v) -> {
          if (v instanceof String) {
            attributesBuilder.put(k, (String) v);
          } else if (v instanceof Integer) {
            attributesBuilder.put(k, (long) (Integer) v);
          }
        });
    return attributesBuilder.build();
  }
}
