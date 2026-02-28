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
import com.google.api.gax.core.GaxProperties;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.Meter;
import java.util.Map;

@BetaApi
@InternalApi
public class AppCentricMetricsRecorder implements MetricsRecorder {

  public static final String GAX_METER_NAME = "gax-java";
  private final DoubleHistogram requestDurationRecorder;
  private final String serviceName;

  public AppCentricMetricsRecorder(OpenTelemetry openTelemetry, String serviceName) {

    Meter meter =
        openTelemetry
            .meterBuilder(GAX_METER_NAME)
            .setInstrumentationVersion(GaxProperties.getGaxVersion())
            .build();
    this.serviceName = serviceName;
    this.requestDurationRecorder =
        meter
            .histogramBuilder("gcp.client.request.duration")
            .setDescription(
                "Measures the total time taken for a logical client request (T3 span), including any retries, backoff, and pre/post-processing.")
            .setUnit("s")
            .build();
  }

  /**
   * Record the latency for the entire operation. This is the latency for the entire RPC, including
   * all the retry attempts
   *
   * @param operationLatency Operation Latency in ms
   * @param attributes Map of the attributes to store
   */
  @Override
  public void recordOperationLatency(double operationLatency, Map<String, String> attributes) {
    requestDurationRecorder.record(operationLatency, toOtelAttributes(attributes));
  }

  @VisibleForTesting
  Attributes toOtelAttributes(Map<String, String> attributes) {
    Preconditions.checkNotNull(attributes, "Attributes map cannot be null");
    AttributesBuilder attributesBuilder = Attributes.builder();
    attributes.forEach(attributesBuilder::put);
    attributesBuilder.put("gcp.client.service", serviceName);
    return attributesBuilder.build();
  }
}
