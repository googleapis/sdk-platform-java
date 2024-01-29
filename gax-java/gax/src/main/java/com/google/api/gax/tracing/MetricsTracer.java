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

package com.google.api.gax.tracing;

import com.google.api.core.BetaApi;
import com.google.api.core.InternalApi;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.StatusCode;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.threeten.bp.Duration;

/**
 * This class computes generic metrics that can be observed in the lifecycle of an RPC operation.
 * The responsibility of recording metrics should delegate to {@link MetricsRecorder}, hence this
 * class should not have any knowledge about the observability framework used for metrics recording.
 */
@BetaApi
@InternalApi
public class MetricsTracer implements ApiTracer {

  private static final String STATUS_ATTRIBUTE = "status";

  private Stopwatch attemptTimer;

  private final Stopwatch operationTimer = Stopwatch.createStarted();

  private final Map<String, String> attributes = new HashMap<>();

  private MetricsRecorder metricsRecorder;

  public MetricsTracer(MethodName methodName, MetricsRecorder metricsRecorder) {
    this.attributes.put("method_name", methodName.toString());
    this.metricsRecorder = metricsRecorder;
  }

  /**
   * Signals that the overall operation has finished successfully. The tracer is now considered
   * closed and should no longer be used. Successful operation adds "OK" value to the status
   * attribute key.
   */
  @Override
  public void operationSucceeded() {
    attributes.put(STATUS_ATTRIBUTE, StatusCode.Code.OK.toString());
    metricsRecorder.recordOperationLatency(
        operationTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordOperationCount(1, attributes);
  }

  /**
   * Signals that the operation was cancelled by the user. The tracer is now considered closed and
   * should no longer be used. Cancelled operation adds "CANCELLED" value to the status attribute
   * key.
   */
  @Override
  public void operationCancelled() {
    attributes.put(STATUS_ATTRIBUTE, StatusCode.Code.CANCELLED.toString());
    metricsRecorder.recordOperationLatency(
        operationTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordOperationCount(1, attributes);
  }

  /**
   * Signals that the operation was cancelled by the user. The tracer is now considered closed and
   * should no longer be used. Failed operation extracts the error from the throwable and adds it to
   * the status attribute key.
   */
  @Override
  public void operationFailed(Throwable error) {
    attributes.put(STATUS_ATTRIBUTE, extractStatus(error));
    metricsRecorder.recordOperationLatency(
        operationTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordOperationCount(1, attributes);
  }

  /**
   * Adds an annotation that an attempt is about to start with additional information from the
   * request. In general this should occur at the very start of the operation. The attemptNumber is
   * zero based. So the initial attempt will be 0. When the attempt starts, the attemptTimer starts
   * the stopwatch.
   *
   * @param attemptNumber the zero based sequential attempt number.
   * @param request request of this attempt.
   */
  @Override
  public void attemptStarted(Object request, int attemptNumber) {
    attemptTimer = Stopwatch.createStarted();
  }

  /**
   * Adds an annotation that the attempt succeeded. Successful attempt add "OK" value to the status
   * attribute key.
   */
  @Override
  public void attemptSucceeded() {

    attributes.put(STATUS_ATTRIBUTE, StatusCode.Code.OK.toString());
    metricsRecorder.recordAttemptLatency(attemptTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordAttemptCount(1, attributes);
  }

  /**
   * Add an annotation that the attempt was cancelled by the user. Cancelled attempt add "CANCELLED"
   * to the status attribute key.
   */
  @Override
  public void attemptCancelled() {

    attributes.put(STATUS_ATTRIBUTE, StatusCode.Code.CANCELLED.toString());
    metricsRecorder.recordAttemptLatency(attemptTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordAttemptCount(1, attributes);
  }

  /**
   * Adds an annotation that the attempt failed, but another attempt will be made after the delay.
   *
   * @param error the transient error that caused the attempt to fail.
   * @param delay the amount of time to wait before the next attempt will start.
   *     <p>Failed attempt extracts the error from the throwable and adds it to the status attribute
   *     key.
   */
  @Override
  public void attemptFailed(Throwable error, Duration delay) {

    attributes.put(STATUS_ATTRIBUTE, extractStatus(error));
    metricsRecorder.recordAttemptLatency(attemptTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordAttemptCount(1, attributes);
  }

  /**
   * Adds an annotation that the attempt failed and that no further attempts will be made because
   * retry limits have been reached. This extracts the error from the throwable and adds it to the
   * status attribute key.
   *
   * @param error the last error received before retries were exhausted.
   */
  @Override
  public void attemptFailedRetriesExhausted(Throwable error) {

    attributes.put(STATUS_ATTRIBUTE, extractStatus(error));
    metricsRecorder.recordAttemptLatency(attemptTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordAttemptCount(1, attributes);
  }

  /**
   * Adds an annotation that the attempt failed and that no further attempts will be made because
   * the last error was not retryable. This extracts the error from the throwable and adds it to the
   * status attribute key.
   *
   * @param error the error that caused the final attempt to fail.
   */
  @Override
  public void attemptPermanentFailure(Throwable error) {

    attributes.put(STATUS_ATTRIBUTE, extractStatus(error));
    metricsRecorder.recordAttemptLatency(attemptTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    metricsRecorder.recordAttemptCount(1, attributes);
  }

  /** Function to extract the status of the error as a string */
  @VisibleForTesting
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

  /**
   * Add attributes that will be attached to all metrics. This is expected to be called by
   * handwritten client teams to add additional attributes that are not supposed be collected by
   * Gax.
   */
  public void addAttributes(String key, String value) {
    attributes.put(key, value);
  };

  /** This method is only used for testing */
  Map<String, String> getAttributes() {
    return attributes;
  }
}
