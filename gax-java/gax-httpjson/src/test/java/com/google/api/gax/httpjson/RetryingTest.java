/*
 * Copyright 2017 Google LLC
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
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.gax.core.FakeApiClock;
import com.google.api.gax.core.RecordingScheduler;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.ApiCallContext;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.ApiExceptionFactory;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.FailedPreconditionException;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.gax.rpc.UnavailableException;
import com.google.api.gax.rpc.UnknownException;
import com.google.api.gax.rpc.testing.FakeCallContext;
import com.google.api.gax.rpc.testing.FakeCallableFactory;
import com.google.api.gax.rpc.testing.FakeChannel;
import com.google.api.gax.rpc.testing.FakeStatusCode;
import com.google.api.gax.rpc.testing.FakeTransportChannel;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.protobuf.TypeRegistry;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.threeten.bp.Duration;

@RunWith(JUnit4.class)
public class RetryingTest {

  @SuppressWarnings("unchecked")
  private final UnaryCallable<Integer, Integer> callInt = Mockito.mock(UnaryCallable.class);

  private final ApiMethodDescriptor<Integer, Integer> FAKE_METHOD_DESCRIPTOR_FOR_REQUEST_MUTATOR =
      ApiMethodDescriptor.newBuilder()
          .setFullMethodName("google.cloud.v1.Fake/FakeMethodForRequestMutator")
          .setHttpMethod(HttpMethods.POST)
          .setRequestFormatter(Mockito.mock(HttpRequestFormatter.class))
          .setResponseParser(Mockito.mock(HttpResponseParser.class))
          .build();

  private final Integer initialRequest = 1;
  private final Integer modifiedRequest = 0;

  private final HttpJsonCallSettings<Integer, Integer> httpJsonCallSettings =
      HttpJsonCallSettings.<Integer, Integer>newBuilder()
          .setRequestMutator(request -> modifiedRequest)
          .setMethodDescriptor(FAKE_METHOD_DESCRIPTOR_FOR_REQUEST_MUTATOR)
          .setTypeRegistry(TypeRegistry.newBuilder().build())
          .build();

  private RecordingScheduler executor;
  private FakeApiClock fakeClock;
  private ClientContext clientContext;
  private static final int HTTP_CODE_PRECONDITION_FAILED = 412;

  private HttpResponseException HTTP_SERVICE_UNAVAILABLE_EXCEPTION =
      new HttpResponseException.Builder(
              HttpStatusCodes.STATUS_CODE_SERVICE_UNAVAILABLE,
              "server unavailable",
              new HttpHeaders())
          .build();

  private static final RetrySettings FAST_RETRY_SETTINGS =
      RetrySettings.newBuilder()
          .setInitialRetryDelay(Duration.ofMillis(2L))
          .setRetryDelayMultiplier(1)
          .setMaxRetryDelay(Duration.ofMillis(2L))
          .setInitialRpcTimeout(Duration.ofMillis(2L))
          .setRpcTimeoutMultiplier(1)
          .setMaxRpcTimeout(Duration.ofMillis(2L))
          .setTotalTimeout(Duration.ofMillis(10L))
          .build();

  @Before
  public void resetClock() {
    fakeClock = new FakeApiClock(System.nanoTime());
    executor = RecordingScheduler.create(fakeClock);
    clientContext =
        ClientContext.newBuilder()
            .setExecutor(executor)
            .setClock(fakeClock)
            .setDefaultCallContext(FakeCallContext.createDefault())
            .setTransportChannel(FakeTransportChannel.create(new FakeChannel()))
            .build();
  }

  @After
  public void teardown() {
    executor.shutdownNow();
  }

  @Test
  public void retry() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of(Code.UNAVAILABLE);
    FakeStatusCode fakeStatusCode = FakeStatusCode.of(Code.UNAVAILABLE);
    UnavailableException unavailableException =
        new UnavailableException(null, fakeStatusCode, true);
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFuture(2));

    UnaryCallSettings<Integer, Integer> callSettings =
        createSettings(retryable, FAST_RETRY_SETTINGS);
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    assertThat(callable.call(initialRequest)).isEqualTo(2);

    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getAllValues()).containsExactly(1, 1, 1, 1).inOrder();
  }

  @Test
  public void retryTotalTimeoutExceeded() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of(Code.UNAVAILABLE);
    HttpResponseException httpResponseException =
        new HttpResponseException.Builder(
                HttpStatusCodes.STATUS_CODE_SERVICE_UNAVAILABLE,
                "server unavailable",
                new HttpHeaders())
            .build();
    ApiException apiException =
        ApiExceptionFactory.createException(
            "foobar",
            httpResponseException,
            HttpJsonStatusCode.of(Code.FAILED_PRECONDITION),
            false);
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(apiException))
        .thenReturn(ApiFutures.<Integer>immediateFuture(2));

    RetrySettings retrySettings =
        FAST_RETRY_SETTINGS
            .toBuilder()
            .setInitialRetryDelay(Duration.ofMillis(Integer.MAX_VALUE))
            .setMaxRetryDelay(Duration.ofMillis(Integer.MAX_VALUE))
            .build();
    UnaryCallSettings<Integer, Integer> callSettings = createSettings(retryable, retrySettings);
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    assertThrows(ApiException.class, () -> callable.call(initialRequest));
    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getAllValues()).containsExactly(1);
  }

  @Test
  public void retryMaxAttemptsExceeded() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of(Code.UNAVAILABLE);
    FakeStatusCode fakeStatusCode = FakeStatusCode.of(Code.UNAVAILABLE);
    UnavailableException unavailableException =
        new UnavailableException(null, fakeStatusCode, true);
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFuture(2));

    RetrySettings retrySettings = FAST_RETRY_SETTINGS.toBuilder().setMaxAttempts(2).build();
    UnaryCallSettings<Integer, Integer> callSettings = createSettings(retryable, retrySettings);
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    assertThrows(ApiException.class, () -> callable.call(initialRequest));
    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getAllValues()).containsExactly(1, 1).inOrder();
  }

  @Test
  public void retryWithinMaxAttempts() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of(Code.UNAVAILABLE);
    FakeStatusCode fakeStatusCode = FakeStatusCode.of(Code.UNAVAILABLE);
    UnavailableException unavailableException =
        new UnavailableException(null, fakeStatusCode, true);
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFuture(2));

    RetrySettings retrySettings = FAST_RETRY_SETTINGS.toBuilder().setMaxAttempts(3).build();
    UnaryCallSettings<Integer, Integer> callSettings = createSettings(retryable, retrySettings);
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    assertThat(callable.call(initialRequest)).isEqualTo(2);
    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getAllValues()).containsExactly(1, 1, 1).inOrder();
  }

  @Test
  public void retryOnStatusUnknown() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of(Code.UNKNOWN);
    FakeStatusCode fakeStatusCode = FakeStatusCode.of(Code.UNAVAILABLE);
    UnavailableException unavailableException =
        new UnavailableException(null, fakeStatusCode, true);
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException))
        .thenReturn(ApiFutures.<Integer>immediateFuture(2));
    UnaryCallSettings<Integer, Integer> callSettings =
        createSettings(retryable, FAST_RETRY_SETTINGS);
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    assertThat(callable.call(initialRequest)).isEqualTo(2);
    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getAllValues()).containsExactly(1, 1, 1, 1).inOrder();
  }

  @Test
  public void retryOnUnexpectedException() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of(Code.UNKNOWN);
    Throwable throwable = new RuntimeException("foobar");
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(throwable));
    UnaryCallSettings<Integer, Integer> callSettings =
        createSettings(retryable, FAST_RETRY_SETTINGS);
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    ApiException exception = assertThrows(ApiException.class, () -> callable.call(initialRequest));
    assertThat(exception).hasCauseThat().isSameInstanceAs(throwable);
    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getAllValues()).containsExactly(1).inOrder();
  }

  @Test
  public void retryNoRecover() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of(Code.UNAVAILABLE);
    HttpResponseException httpResponseException =
        new HttpResponseException.Builder(
                HTTP_CODE_PRECONDITION_FAILED, "foobar", new HttpHeaders())
            .build();
    ApiException apiException =
        ApiExceptionFactory.createException(
            "foobar",
            httpResponseException,
            HttpJsonStatusCode.of(Code.FAILED_PRECONDITION),
            false);
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(apiException))
        .thenReturn(ApiFutures.<Integer>immediateFuture(2));
    UnaryCallSettings<Integer, Integer> callSettings =
        createSettings(retryable, FAST_RETRY_SETTINGS);
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    ApiException exception = assertThrows(ApiException.class, () -> callable.call(initialRequest));
    assertThat(exception).isSameInstanceAs(apiException);
    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getAllValues()).containsExactly(1);
  }

  @Test
  public void retryKeepFailing() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of(Code.UNAVAILABLE);
    FakeStatusCode fakeStatusCode = FakeStatusCode.of(Code.UNAVAILABLE);
    UnavailableException unavailableException =
        new UnavailableException("Unavailable", null, fakeStatusCode, true);
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(unavailableException));
    UnaryCallSettings<Integer, Integer> callSettings =
        createSettings(retryable, FAST_RETRY_SETTINGS);
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    // Need to advance time inside the call.
    ApiFuture<Integer> future = callable.futureCall(initialRequest);

    UncheckedExecutionException exception =
        assertThrows(UncheckedExecutionException.class, () -> Futures.getUnchecked(future));
    assertThat(exception).hasCauseThat().isInstanceOf(ApiException.class);
    assertThat(exception.getMessage()).contains("Unavailable");
    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getValue()).isEqualTo(1);
  }

  @Test
  public void testKnownStatusCode() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of(Code.UNAVAILABLE);
    FakeStatusCode fakeStatusCode = FakeStatusCode.of(Code.FAILED_PRECONDITION);
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(
            ApiFutures.<Integer>immediateFailedFuture(
                new FailedPreconditionException("known", null, fakeStatusCode, false)));
    UnaryCallSettings<Integer, Integer> callSettings =
        UnaryCallSettings.<Integer, Integer>newUnaryCallSettingsBuilder()
            .setRetryableCodes(retryable)
            .build();
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    ApiException exception =
        assertThrows(FailedPreconditionException.class, () -> callable.call(initialRequest));
    assertThat(exception.getStatusCode().getCode().toString()).isEqualTo(fakeStatusCode.toString());
    assertThat(exception.getMessage()).isEqualTo("known");
    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getAllValues()).containsExactly(1).inOrder();
  }

  @Test
  public void testUnknownStatusCode() {
    ImmutableSet<StatusCode.Code> retryable = ImmutableSet.of();
    Mockito.when(callInt.futureCall((Integer) any(), (ApiCallContext) any()))
        .thenReturn(ApiFutures.<Integer>immediateFailedFuture(new RuntimeException("unknown")));
    UnaryCallSettings<Integer, Integer> callSettings =
        UnaryCallSettings.<Integer, Integer>newUnaryCallSettingsBuilder()
            .setRetryableCodes(retryable)
            .build();
    UnaryCallable<Integer, Integer> callable =
        FakeCallableFactory.createUnaryCallable(callInt, callSettings, clientContext);
    UnknownException exception =
        assertThrows(UnknownException.class, () -> callable.call(initialRequest));
    assertThat(exception).hasMessageThat().isEqualTo("java.lang.RuntimeException: unknown");
    // Capture the argument passed to futureCall
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    verify(callInt, atLeastOnce()).futureCall(argumentCaptor.capture(), any(ApiCallContext.class));
    assertThat(argumentCaptor.getAllValues()).containsExactly(1).inOrder();
  }

  public static UnaryCallSettings<Integer, Integer> createSettings(
      Set<StatusCode.Code> retryableCodes, RetrySettings retrySettings) {
    return UnaryCallSettings.<Integer, Integer>newUnaryCallSettingsBuilder()
        .setRetryableCodes(retryableCodes)
        .setRetrySettings(retrySettings)
        .build();
  }
}
