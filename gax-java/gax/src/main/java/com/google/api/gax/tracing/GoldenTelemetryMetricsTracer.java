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

import static com.google.api.gax.tracing.ObservabilityAttributes.RPC_RESPONSE_STATUS_ATTRIBUTE;
import static com.google.api.gax.tracing.ObservabilityUtils.toOtelAttributes;

import com.google.api.gax.rpc.StatusCode;
import com.google.common.base.Stopwatch;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.Meter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class GoldenTelemetryMetricsTracer implements ApiTracer {

  static final String CLIENT_REQUEST_DURATION_METRIC_NAME = "gcp.client.request.duration";
  static final String CLIENT_REQUEST_DURATION_METRIC_DESCRIPTION =
      "Measures the total time taken for a logical client request, including any retries, backoff, and pre/post-processing";
  static final String OPERATION_FINISHED_STATUS_MESSAGE =
          "Operation has already been completed";

  private final Stopwatch clientRequestTimer = Stopwatch.createStarted();
  private final AtomicBoolean clientRequestFinished;
  final DoubleHistogram clientRequestDurationRecorder;
  private final Map<String, String> attributes = new HashMap<>();

  /**
   * Creates the following instruments for the following metrics:
   *
   * <ul>
   *   <li>Client Request Duration: Histogram
   * </ul>
   *
   * @param openTelemetry OpenTelemetry
   * @param apiTracerContext ApiTracerContext
   */
  public GoldenTelemetryMetricsTracer(
      OpenTelemetry openTelemetry, ApiTracerContext apiTracerContext) {
    this.clientRequestFinished = new AtomicBoolean();
    Meter meter =
        openTelemetry.meterBuilder(apiTracerContext.libraryMetadata().artifactName()).build();

    this.clientRequestDurationRecorder =
        meter
            .histogramBuilder(CLIENT_REQUEST_DURATION_METRIC_NAME)
            .setDescription(CLIENT_REQUEST_DURATION_METRIC_DESCRIPTION)
            .setUnit("s")
            .build();
  }

  @Override
  public void operationSucceeded() {
    if (clientRequestFinished.getAndSet(true)) {
      throw new IllegalStateException(OPERATION_FINISHED_STATUS_MESSAGE);
    }
    attributes.put(RPC_RESPONSE_STATUS_ATTRIBUTE, StatusCode.Code.OK.toString());
    clientRequestDurationRecorder.record(
        clientRequestTimer.elapsed(TimeUnit.SECONDS), toOtelAttributes(attributes));
  }

  @Override
  public void operationCancelled() {
    if (clientRequestFinished.getAndSet(true)) {
      throw new IllegalStateException(OPERATION_FINISHED_STATUS_MESSAGE);
    }
    attributes.put(RPC_RESPONSE_STATUS_ATTRIBUTE, StatusCode.Code.CANCELLED.toString());
    clientRequestDurationRecorder.record(
        clientRequestTimer.elapsed(TimeUnit.SECONDS), toOtelAttributes(attributes));
  }

  @Override
  public void operationFailed(Throwable error) {
    if (clientRequestFinished.getAndSet(true)) {
      throw new IllegalStateException(OPERATION_FINISHED_STATUS_MESSAGE);
    }
    attributes.put(RPC_RESPONSE_STATUS_ATTRIBUTE, ObservabilityUtils.extractStatus(error));
    clientRequestDurationRecorder.record(
        clientRequestTimer.elapsed(TimeUnit.SECONDS), toOtelAttributes(attributes));
  }
}
