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

package com.google.api.gax.tracing;

import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.StatusCode;
import com.google.common.base.Stopwatch;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.threeten.bp.Duration;

public class MetricsTracer implements ApiTracer {
  public static final String STATUS_ATTRIBUTE = "status";

  private Stopwatch attemptTimer;

  private final Stopwatch operationTimer = Stopwatch.createStarted();

  private final Map<String, String> attributes = new HashMap<>();

  protected MetricsRecorder metricsRecorder;

  public MetricsTracer(SpanName spanName, MetricsRecorder metricsRecorder) {
    this.attributes.put("method_name", spanName.toString());
    this.metricsRecorder = metricsRecorder;
  }

  @Override
  public Scope inScope() {
    return () -> {};
  }

  @Override
  public void operationSucceeded() {}

  @Override
  public void operationSucceeded(Object response) {
    attributes.put(STATUS_ATTRIBUTE, StatusCode.Code.OK.toString());
    metricsRecorder.recordOperationLatency(
        operationTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordOperationCount(1, attributes);
  }

  @Override
  public void operationCancelled() {}

  @Override
  public void operationFailed(Throwable error) {
    attributes.put(STATUS_ATTRIBUTE, extractStatus(error));
    metricsRecorder.recordOperationLatency(
        operationTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordOperationCount(1, attributes);
  }

  @Override
  public void attemptStarted(int attemptNumber) {}

  @Override
  public void attemptStarted(Object request, int attemptNumber) {
    attemptTimer = Stopwatch.createStarted();
  }

  @Override
  public void attemptSucceeded() {}

  @Override
  public void attemptSucceeded(Object response) {
    attributes.put(STATUS_ATTRIBUTE, StatusCode.Code.OK.toString());
    metricsRecorder.recordAttemptLatency(attemptTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordAttemptCount(1, attributes);
  }

  @Override
  public void attemptCancelled() {}

  @Override
  public void attemptFailed(Throwable error, Duration delay) {
    attributes.put(STATUS_ATTRIBUTE, extractStatus(error));
    metricsRecorder.recordAttemptLatency(attemptTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordAttemptCount(1, attributes);
  }

  @Override
  public void attemptFailedRetriesExhausted(Throwable error) {}

  @Override
  public void attemptPermanentFailure(Throwable error) {}

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

  public void addAdditionalAttributes(String key, String value) {
    attributes.put(key, value);
  }
}
