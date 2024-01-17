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
package com.google.api.gax.rpc;

import com.google.api.gax.retrying.BasicResultRetryAlgorithm;
import com.google.api.gax.retrying.RetryingContext;
import com.google.api.gax.retrying.TimedAttemptSettings;
import com.google.protobuf.util.Durations;
import com.google.rpc.RetryInfo;
import javax.annotation.Nullable;
import org.threeten.bp.Duration;

/**
 * This retry algorithm checks the additional {@link ErrorDetails} of an exception. If the {@link
 * ErrorDetails} has a {@link RetryInfo} field, use the retry delay to set the wait time between
 * attempts.
 */
class RetryInfoRetryAlgorithm<ResponseT> extends BasicResultRetryAlgorithm<ResponseT> {

  @Override
  public TimedAttemptSettings createNextAttempt(
      Throwable prevThrowable, ResponseT prevResponse, TimedAttemptSettings prevSettings) {
    Duration retryDelay = extractRetryDelay(prevThrowable);
    if (retryDelay != null) {
      return prevSettings
          .toBuilder()
          .setRandomizedRetryDelay(retryDelay)
          .setAttemptCount(prevSettings.getAttemptCount() + 1)
          .setOverallAttemptCount(prevSettings.getOverallAttemptCount() + 1)
          .build();
    }
    return null;
  }

  /**
   * If previousThrowable has a {@link RetryInfo} field: always returns true.
   *
   * <p>Otherwise returns true if previousThrowable is an {@link
   * com.google.api.gax.rpc.ApiException} that is retryable.
   */
  @Override
  public boolean shouldRetry(Throwable previousThrowable, ResponseT previousResponse) {
    return shouldRetry(null, previousThrowable, previousResponse);
  }

  /**
   * If previousThrowable has a {@link RetryInfo} field: always returns true.
   *
   * <p>Otherwise check if {@link RetryingContext#getRetryableCodes()} is not null: Returns true if
   * the status code of previousThrowable is in the list of retryable code of the {@link
   * RetryingContext}.
   *
   * <p>Otherwise it returns the result of {@link #shouldRetry(Throwable, Object)}.
   */
  @Override
  public boolean shouldRetry(
      @Nullable RetryingContext context, Throwable previousThrowable, ResponseT previousResponse) {
    if (extractRetryDelay(previousThrowable) != null) {
      // First check if server wants us to retry
      return true;
    }
    if (context != null && context.getRetryableCodes() != null) {
      // Ignore the isRetryable() value of the throwable if the RetryingContext has a specific list
      // of codes that should be retried.
      return ((previousThrowable instanceof ApiException)
          && context
              .getRetryableCodes()
              .contains(((ApiException) previousThrowable).getStatusCode().getCode()));
    }
    // Server didn't have retry information and there's no retry context, use the local status
    // code config.
    return previousThrowable instanceof ApiException
        && ((ApiException) previousThrowable).isRetryable();
  }

  @Nullable
  private Duration extractRetryDelay(@Nullable Throwable throwable) {
    if (throwable == null) {
      return null;
    }
    if (!(throwable instanceof ApiException)) {
      return null;
    }
    ApiException exception = (ApiException) throwable;
    if (exception.getErrorDetails() == null) {
      return null;
    }
    if (exception.getErrorDetails().getRetryInfo() == null) {
      return null;
    }
    RetryInfo retryInfo = exception.getErrorDetails().getRetryInfo();
    return Duration.ofMillis(Durations.toMillis(retryInfo.getRetryDelay()));
  }
}
