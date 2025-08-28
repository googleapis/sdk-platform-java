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

import com.google.api.client.http.HttpResponseException;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.ApiExceptionFactory;
import com.google.api.gax.rpc.ErrorDetails;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.common.collect.ImmutableSet;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import java.util.Set;
import java.util.concurrent.CancellationException;

class HttpJsonApiExceptionFactory {
  private final Set<Code> retryableCodes;

  HttpJsonApiExceptionFactory(Set<Code> retryableCodes) {
    this.retryableCodes = ImmutableSet.copyOf(retryableCodes);
  }

  ApiException create(Throwable throwable) {
    if (throwable instanceof HttpResponseException) {
      return createApiException((HttpResponseException) throwable);
    } else if (throwable instanceof HttpJsonStatusRuntimeException) {
      HttpJsonStatusRuntimeException e = (HttpJsonStatusRuntimeException) throwable;
      StatusCode statusCode = HttpJsonStatusCode.of(e.getStatusCode());
      boolean canRetry = retryableCodes.contains(statusCode.getCode());
      return ApiExceptionFactory.createException(e, statusCode, canRetry);
    } else if (throwable instanceof CancellationException) {
      return ApiExceptionFactory.createException(
          throwable, HttpJsonStatusCode.of(Code.CANCELLED), false);
    } else if (throwable instanceof ApiException) {
      return (ApiException) throwable;
    } else {
      // Do not retry on unknown throwable, even when UNKNOWN is in retryableCodes
      return ApiExceptionFactory.createException(
          throwable, HttpJsonStatusCode.of(StatusCode.Code.UNKNOWN), false);
    }
  }

  private ApiException createApiException(HttpResponseException e) {
    StatusCode statusCode = HttpJsonStatusCode.of(e.getStatusCode());
    boolean canRetry = retryableCodes.contains(statusCode.getCode());
    String message = e.getStatusMessage();
    if (message == null) {
      return ApiExceptionFactory.createException(e, statusCode, canRetry);
    }
    if (e.getContent() == null) {
      return ApiExceptionFactory.createException(message, e, statusCode, canRetry);
    }

    com.google.rpc.Status.Builder statusBuilder = com.google.rpc.Status.newBuilder();
    try {
      JsonFormat.parser().merge(e.getContent(), statusBuilder);
    } catch (InvalidProtocolBufferException ex) {
      // Ignore parsing error, return an exception with raw message from the HTTP response.
      return ApiExceptionFactory.createException(message, e, statusCode, canRetry);
    }

    com.google.rpc.Status status = statusBuilder.build();
    ErrorDetails.Builder errorDetailsBuilder =
        ErrorDetails.builder().setRawErrorMessages(status.getDetailsList());

    return ApiExceptionFactory.createException(
        e, statusCode, canRetry, errorDetailsBuilder.build());
  }
}
