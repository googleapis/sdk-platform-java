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
import com.google.api.gax.rpc.WatchdogTimeoutException;
import com.google.common.base.Strings;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.channels.UnresolvedAddressException;
import javax.annotation.Nullable;
import javax.net.ssl.SSLHandshakeException;

public class ErrorTypeUtil {

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
   *         <li>{@code CLIENT_UNKNOWN_ERROR}: For all other errors unknown to the client.
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
  public static String extractErrorType(@Nullable Throwable error) {
    if (error == null) {
      // No information about the error; we default to INTERNAL.
      return ErrorType.INTERNAL.toString();
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
    if (!Strings.isNullOrEmpty(exceptionName)) {
      return exceptionName;
    }

    // 5. Internal Fallback
    return ErrorType.INTERNAL.toString();
  }

  /**
   * Extracts the error type from an ApiException. This method prioritizes the ErrorInfo reason,
   * then the transport-specific status code (HTTP or gRPC).
   *
   * @param apiException The ApiException to extract the error type from.
   * @return A string representing the error type, or null if no specific type can be determined.
   */
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

  /**
   * Determines the client-side error type based on the provided Throwable. This method checks for
   * various network and client-specific exceptions.
   *
   * @param error The Throwable to analyze.
   * @return A string representing the client-side error type, or null if not matched.
   */
  @Nullable
  private static String getClientSideError(Throwable error) {
    if (isClientTimeout(error)) {
      return ErrorType.CLIENT_TIMEOUT.toString();
    }
    if (isClientConnectionError(error)) {
      return ErrorType.CLIENT_CONNECTION_ERROR.toString();
    }
    if (isClientAuthenticationError(error)) {
      return ErrorType.CLIENT_AUTHENTICATION_ERROR.toString();
    }
    if (isClientResponseDecodeError(error)) {
      return ErrorType.CLIENT_RESPONSE_DECODE_ERROR.toString();
    }
    if (isClientRedirectError(error)) {
      return ErrorType.CLIENT_REDIRECT_ERROR.toString();
    }
    // This covers CLIENT_REQUEST_ERROR for general illegal arguments in client requests.
    if (error instanceof IllegalArgumentException) {
      return ErrorType.CLIENT_REQUEST_ERROR.toString();
    }
    if (isRequestBodyError(error)) {
      return ErrorType.CLIENT_REQUEST_BODY_ERROR.toString();
    }
    if (isClientUnknownError(error)) {
      return ErrorType.CLIENT_UNKNOWN_ERROR.toString();
    }
    return null;
  }

  /**
   * Checks if the given Throwable represents a client-side timeout error. This includes socket
   * timeouts and GAX-specific watchdog timeouts.
   *
   * @param e The Throwable to check.
   * @return true if the error is a client timeout, false otherwise.
   */
  private static boolean isClientTimeout(Throwable e) {
    return e instanceof SocketTimeoutException || e instanceof WatchdogTimeoutException;
  }

  /**
   * Checks if the given Throwable represents a client-side connection error. This includes issues
   * with establishing connections, unknown hosts, SSL handshakes, and unresolved addresses.
   *
   * @param e The Throwable to check.
   * @return true if the error is a client connection error, false otherwise.
   */
  private static boolean isClientConnectionError(Throwable e) {
    return e instanceof ConnectException
        || e instanceof UnknownHostException
        || e instanceof SSLHandshakeException
        || e instanceof UnresolvedAddressException;
  }

  /**
   * Checks if the given Throwable represents a client-side response decoding error. This is
   * identified by exceptions related to JSON or Gson parsing, either directly or as a cause.
   *
   * @param e The Throwable to check.
   * @return true if the error is a client response decode error, false otherwise.
   */
  private static boolean isClientResponseDecodeError(Throwable e) {
    return e.getClass().getName().contains("Json")
        || e.getClass().getName().contains("Gson")
        || (e.getCause() != null && e.getCause().getClass().getName().contains("Gson"));
  }

  /**
   * Checks if the given Throwable represents a client-side redirect error. This is identified by
   * the presence of "redirect" in the exception message.
   *
   * @param e The Throwable to check.
   * @return true if the error is a client redirect error, false otherwise.
   */
  private static boolean isClientRedirectError(Throwable e) {
    return e.getMessage() != null && e.getMessage().contains("redirect");
  }

  /**
   * Checks if the given Throwable represents a client-side authentication error. This is identified
   * by exceptions related to the auth library.
   *
   * @param e The Throwable to check.
   * @return true if the error is a client authentication error, false otherwise.
   */
  private static boolean isClientAuthenticationError(Throwable e) {
    return e.getClass().getName().contains("GoogleAuthException");
  }

  /**
   * Checks if the given Throwable represents a client-side request body error. This is specifically
   * mapped to RestSerializationException from httpjson, which indicates issues during the
   * serialization of the request body for REST calls.
   *
   * @param e The Throwable to check.
   * @return true if the error is a client request body error, false otherwise.
   */
  private static boolean isRequestBodyError(Throwable e) {
    return e.getClass().getName().contains("RestSerializationException");
  }

  /**
   * Checks if the given Throwable represents an unknown client-side error. This is a general
   * fallback for exceptions whose class name contains "unknown", indicating an unclassified
   * client-side issue.
   *
   * @param e The Throwable to check.
   * @return true if the error is an unknown client error, false otherwise.
   */
  private static boolean isClientUnknownError(Throwable e) {
    return e.getClass().getName().toLowerCase().contains("unknown");
  }
}
