/*
 * Copyright 2023 Google LLC
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

import static com.google.common.truth.Truth.assertThat;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponseException;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.core.SettableApiFuture;
import com.google.api.gax.httpjson.testing.FakeHttpJsonCallable;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.ErrorDetails;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.testing.FakeCallContext;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.rpc.BadRequest;
import com.google.rpc.ErrorInfo;
import java.util.concurrent.ExecutionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class HttpJsonExceptionCallableTest {

  private HttpJsonExceptionCallable<Integer, Integer> callable;

  private FakeHttpJsonCallable<Integer, Integer> innerCallable;

  private FakeCallContext callContext;

  @Before
  public void setUp() {
    innerCallable = new FakeHttpJsonCallable<>();
    callable =
        new HttpJsonExceptionCallable<>(
            innerCallable, ImmutableSet.<StatusCode.Code>of(StatusCode.Code.UNAVAILABLE));
    callContext = FakeCallContext.createDefault();
  }

  @Test
  public void testApiExceptionWithDetails() throws Exception {
    String errorResponse =
        "{\"error\":{\"code\":400,\"message\":\"Invalid argument\",\"status\":\"INVALID_ARGUMENT\",\"details\":[{\"@type\":\"type.googleapis.com/google.rpc.ErrorInfo\",\"reason\":\"INVALID_ARGUMENT\",\"domain\":\"googleapis.com\",\"metadata\":{\"service\":\"translate.googleapis.com\"}},{\"@type\":\"type.googleapis.com/google.rpc.BadRequest\",\"fieldViolations\":[{\"field\":\"field\",\"description\":\"description\"}]}]}}";

    HttpResponseException httpResponseException =
        new HttpResponseException.Builder(400, "Bad Request", new HttpHeaders())
            .setContent(errorResponse)
            .build();

    innerCallable.setResponse(null);
    innerCallable.setApiException(httpResponseException);

    final SettableApiFuture<Integer> future = SettableApiFuture.create();
    ApiFutures.addCallback(
        callable.futureCall(1, callContext),
        new ApiFutureCallback<Integer>() {
          @Override
          public void onSuccess(Integer result) {
            future.set(result);
          }

          @Override
          public void onFailure(Throwable t) {
            future.setException(t);
          }
        },
        MoreExecutors.directExecutor());

    try {
      future.get();
      Assert.fail("show have thrown an exception");
    } catch (InterruptedException | ExecutionException e) {
      ApiException thrown = (ApiException) e.getCause();
      assertThat(thrown.getMessage())
          .isEqualTo(
              "com.google.api.client.http.HttpResponseException: 400 Bad Request\n"
                  + errorResponse);
      ErrorDetails errorDetails = thrown.getErrorDetails();
      assertThat(errorDetails).isNotNull();
      ErrorInfo errorInfo = errorDetails.getErrorInfo();
      assertThat(errorInfo).isNotNull();
      assertThat(errorInfo.getReason()).isEqualTo("INVALID_ARGUMENT");
      assertThat(errorInfo.getDomain()).isEqualTo("googleapis.com");
      assertThat(errorInfo.getMetadataMap()).containsEntry("service", "translate.googleapis.com");
      BadRequest badRequest = errorDetails.getBadRequest();
      assertThat(badRequest).isNotNull();
      assertThat(badRequest.getFieldViolationsCount()).isEqualTo(1);
      assertThat(badRequest.getFieldViolations(0).getField()).isEqualTo("field");
      assertThat(badRequest.getFieldViolations(0).getDescription()).isEqualTo("description");
    }
  }
}
