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

import com.google.api.core.InternalApi;
import com.google.api.gax.core.GaxProperties;
import io.opencensus.trace.Tracer;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.Meter;

/**
 * A {@link ApiTracerFactory} to build instances of {@link OpencensusTracer}.
 *
 * <p>This class wraps the {@link Tracer} provided by Opencensus in {@code Tracing.getTracer()}. It
 * will be used to create new spans and wrap them in {@link OpencensusTracer} defined in gax.
 *
 * <p>This class is thread safe.
 */
@InternalApi("For google-cloud-java client use only")
public class OpenTelemetryMetricsFactory implements ApiTracerFactory {
  protected Meter meter;

  protected MetricsRecorder metricsRecorder;

  public OpenTelemetryMetricsFactory(
      OpenTelemetry openTelemetry, String libraryName, String libraryVersion) {
    meter =
        openTelemetry
            .meterBuilder("gax")
            .setInstrumentationVersion(GaxProperties.getGaxVersion())
            .build();
    metricsRecorder = new MetricsRecorder(meter);
  }

  @Override
  public ApiTracer newTracer(ApiTracer parent, SpanName spanName, OperationType operationType) {
    return new OpenTelemetryMetricsTracer(meter, spanName, metricsRecorder);
  }

  @Override
  public ClientMetricsTracer newClientMetricsTracer() {
    return new OpenTelemetryClientMetricsTracer(meter);
  }
}
