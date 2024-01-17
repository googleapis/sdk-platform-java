package com.google.api.gax.tracing;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;

import java.util.Map;

public class MetricsRecorder {
    protected Meter meter;

    protected DoubleHistogram attemptLatencyRecorder;

    protected DoubleHistogram operationLatencyRecorder;

    protected LongCounter operationCountRecorder;

    protected LongCounter attemptCountRecorder;

    protected Attributes attributes;

    public MetricsRecorder(Meter meter) {
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

    private Attributes toOtelAttributes(Map<String, String> attributes) {
        AttributesBuilder attributesBuilder = Attributes.builder();
        attributes.forEach(attributesBuilder::put);
        return attributesBuilder.build();
    }
}