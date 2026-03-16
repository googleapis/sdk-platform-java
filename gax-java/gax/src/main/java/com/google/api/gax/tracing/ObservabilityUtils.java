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
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
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

    if (error instanceof ApiException) {
      ApiException apiException = (ApiException) error;

      // 1. Check for ErrorInfo.reason
      String reason = apiException.getReason();
      if (reason != null && !reason.isEmpty()) {
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
    }

    // 3. Client-Side Network/Operational Errors
    String exceptionName = error.getClass().getSimpleName();

    if (error instanceof java.util.concurrent.TimeoutException
        || error instanceof java.net.SocketTimeoutException
        || exceptionName.equals("WatchdogTimeoutException")) {
      return ErrorType.CLIENT_TIMEOUT.toString();
    }

    if (error instanceof java.net.ConnectException
        || error instanceof java.net.UnknownHostException
        || error instanceof java.nio.channels.UnresolvedAddressException
        || exceptionName.equals("ConnectException")) {
      return ErrorType.CLIENT_CONNECTION_ERROR.toString();
    }

    if (exceptionName.contains("CredentialsException")
        || exceptionName.contains("AuthenticationException")) {
      return ErrorType.CLIENT_AUTHENTICATION_ERROR.toString();
    }

    if (exceptionName.contains("ProtocolBufferParsingException")
        || exceptionName.contains("DecodeException")) {
      return ErrorType.CLIENT_RESPONSE_DECODE_ERROR.toString();
    }

    if (exceptionName.contains("RedirectException")) {
      return ErrorType.CLIENT_REDIRECT_ERROR.toString();
    }

    if (exceptionName.contains("RequestBodyException")) {
      return ErrorType.CLIENT_REQUEST_BODY_ERROR.toString();
    }

    if (exceptionName.contains("RequestException")) {
      return ErrorType.CLIENT_REQUEST_ERROR.toString();
    }

    if (exceptionName.contains("UnknownClientException")) {
      return ErrorType.CLIENT_UNKNOWN_ERROR.toString();
    }

    // 4. Language-specific error type fallback
    if (exceptionName != null && !exceptionName.isEmpty()) {
      return exceptionName;
    }

    // 5. Internal Fallback
    return ErrorType.INTERNAL.toString();
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
