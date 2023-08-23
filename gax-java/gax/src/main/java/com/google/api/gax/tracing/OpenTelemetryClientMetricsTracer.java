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
