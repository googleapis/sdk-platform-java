package com.google.api.gax.tracing;

import io.opentelemetry.api.metrics.Meter;

public class OpenTelemetryClientMetricsTracer implements ClientMetricsTracer {

    private Meter meter;
    public OpenTelemetryClientMetricsTracer(Meter meter) {
        this.meter = meter;
    }

    @Override
    public void recordCurrentChannelSize(int channelSize) {
        meter
                .gaugeBuilder(channelSizeName())
                .setDescription("Channel Size")
                .setUnit("1")
                .ofLongs()
                .buildWithCallback(measurement -> measurement.record(channelSize));
    }
}
