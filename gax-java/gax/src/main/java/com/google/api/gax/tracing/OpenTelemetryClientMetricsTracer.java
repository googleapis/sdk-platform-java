package com.google.api.gax.tracing;

import io.opentelemetry.api.metrics.LongGaugeBuilder;
import io.opentelemetry.api.metrics.Meter;

public class OpenTelemetryClientMetricsTracer implements ClientMetricsTracer {

  private final LongGaugeBuilder longGaugeBuilder;
  private Meter meter;

  public OpenTelemetryClientMetricsTracer(Meter meter) {
    this.meter = meter;
    longGaugeBuilder =
        meter.gaugeBuilder(channelSizeName()).setDescription("Channel Size").setUnit("1").ofLongs();
  }

  @Override
  public void recordCurrentChannelSize(int channelSize) {
    longGaugeBuilder.buildWithCallback(
        observableLongMeasurement -> observableLongMeasurement.record(channelSize));
  }
}
