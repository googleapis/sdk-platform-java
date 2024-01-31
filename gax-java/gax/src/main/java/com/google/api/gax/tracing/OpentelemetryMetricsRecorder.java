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

import com.google.common.annotations.VisibleForTesting;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import java.util.Map;

public class OpentelemetryMetricsRecorder implements MetricsRecorder {

  private Meter meter;

  private DoubleHistogram attemptLatencyRecorder;

  private DoubleHistogram operationLatencyRecorder;

  private LongCounter operationCountRecorder;

  private LongCounter attemptCountRecorder;

  private Attributes attributes;

  public OpentelemetryMetricsRecorder(Meter meter) {
    this.meter = meter;
    this.attemptLatencyRecorder =
        meter
            .histogramBuilder("attempt_latency")
            .setDescription("Duration of an individual attempt")
            .setUnit("ms")
            .build();
    this.operationLatencyRecorder =
        meter
            .histogramBuilder("operation_latency")
            .setDescription(
                "Total time until final operation success or failure, including retries and backoff.")
            .setUnit("ms")
            .build();
    this.operationCountRecorder =
        meter
            .counterBuilder("operation_count")
            .setDescription("Count of Operations")
            .setUnit("1")
            .build();
    this.attemptCountRecorder =
        meter
            .counterBuilder("attempt_count")
            .setDescription("Count of Attempts")
            .setUnit("1")
            .build();
  }

  public void recordAttemptLatency(double attemptLatency, Map<String, String> attributes) {
    attemptLatencyRecorder.record(attemptLatency, toOtelAttributes(attributes));
  }

  public void recordAttemptCount(long count, Map<String, String> attributes) {
    attemptCountRecorder.add(count, toOtelAttributes(attributes));
  }

  public void recordOperationLatency(double operationLatency, Map<String, String> attributes) {
    operationLatencyRecorder.record(operationLatency, toOtelAttributes(attributes));
  }

  public void recordOperationCount(long count, Map<String, String> attributes) {
    operationCountRecorder.add(count, toOtelAttributes(attributes));
  }

  @VisibleForTesting
  Attributes toOtelAttributes(Map<String, String> attributes) {

    if (attributes == null) {
      throw new IllegalArgumentException("Input attributes map cannot be null");
    }

    AttributesBuilder attributesBuilder = Attributes.builder();
    attributes.forEach(attributesBuilder::put);
    return attributesBuilder.build();
  }
}
