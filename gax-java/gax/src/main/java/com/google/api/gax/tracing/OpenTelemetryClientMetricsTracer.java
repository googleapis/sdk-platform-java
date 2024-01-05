/*
 * Copyright 2019 Google LLC
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

import io.opentelemetry.api.metrics.LongGaugeBuilder;
import io.opentelemetry.api.metrics.Meter;

public class OpenTelemetryClientMetricsTracer implements ClientMetricsTracer {

  private final LongGaugeBuilder channelSizeRecorder;
  private final LongGaugeBuilder threadCountRecorder;
  private Meter meter;

  public OpenTelemetryClientMetricsTracer(Meter meter) {
    this.meter = meter;
    channelSizeRecorder =
        meter.gaugeBuilder(channelSizeName()).setDescription("Channel Size").setUnit("1").ofLongs();
    threadCountRecorder =
        meter
            .gaugeBuilder("client_thread_count")
            .setDescription("Current Thread Count created by Client")
            .setUnit("1")
            .ofLongs();
  }

  @Override
  public void recordCurrentChannelSize(int channelSize) {
    channelSizeRecorder.buildWithCallback(
        observableLongMeasurement -> observableLongMeasurement.record(channelSize));
  }

  @Override
  public void recordGaxThread(int threadCount) {
    threadCountRecorder.buildWithCallback(
        observableLongMeasurement -> observableLongMeasurement.record(threadCount));
  }
}
