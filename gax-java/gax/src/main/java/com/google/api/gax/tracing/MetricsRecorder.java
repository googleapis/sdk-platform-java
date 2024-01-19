package com.google.api.gax.tracing;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import java.util.Map;

interface MetricsRecorder {

  default void recordAttemptLatency(double attemptLatency, Map<String, String> attributes) {}

  default void recordAttemptCount(long count, Map<String, String> attributes) {}

  default void recordOperationLatency(double operationLatency, Map<String, String> attributes) {}

  default void recordOperationCount(long count, Map<String, String> attributes) {}

}
