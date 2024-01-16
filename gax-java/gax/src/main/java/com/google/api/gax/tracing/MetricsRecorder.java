package com.google.api.gax.tracing;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;
import java.util.Map;

public class MetricsRecorder {
  protected Meter meter;

  protected DoubleHistogram attemptLatencyRecorder;

  protected DoubleHistogram operationLatencyRecorder;
  protected LongHistogram retryCountRecorder;
  protected LongHistogram gfeLatencyRecorder;

  protected DoubleHistogram targetResolutionDelayRecorder;
  protected DoubleHistogram channelReadinessDelayRecorder;
  protected DoubleHistogram callSendDelayRecorder;
  protected LongCounter operationCountRecorder;

  protected LongCounter attemptCountRecorder;

  // is this unnecessary now ?
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
    this.retryCountRecorder =
        meter
            .histogramBuilder("retry_count")
            .setDescription("Number of additional attempts per operation after initial attempt")
            .setUnit("1")
            .ofLongs()
            .build();
    this.gfeLatencyRecorder =
        meter
            .histogramBuilder("gfe_latency")
            .setDescription("GFE latency")
            .setUnit("1")
            .ofLongs()
            .build();
    this.targetResolutionDelayRecorder =
        meter
            .histogramBuilder("target_resolution_delay")
            .setDescription("Delay caused by name resolution")
            .setUnit("ns")
            .build();
    this.channelReadinessDelayRecorder =
        meter
            .histogramBuilder("channel_readiness_delay")
            .setDescription("Delay caused by establishing connection")
            .setUnit("ns")
            .build();
    this.callSendDelayRecorder =
        meter
            .histogramBuilder("call_send_delay")
            .setDescription("Call send delay. (after the connection is ready)")
            .setUnit("ns")
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

  //In each of the record function, we are passing a hashmap of Attributes
  //nit - rename attributes to attributesMap
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


  // this function is to convert hashmap to Attributes
  private Attributes toOtelAttributes(Map<String, String> attributes) {
    AttributesBuilder attributesBuilder = Attributes.builder();
    attributes.forEach(attributesBuilder::put);
    return attributesBuilder.build();
  }
}
