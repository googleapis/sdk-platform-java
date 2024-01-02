package com.google.api.gax.tracing;

import com.google.common.base.Stopwatch;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;

public class MetricsRecorder {
  public static final String STATUS_ATTRIBUTE = "status";
  protected Meter meter;

  private Stopwatch attemptTimer;

  private final Stopwatch operationTimer = Stopwatch.createStarted();

  protected DoubleHistogram attemptLatencyRecorder;

  protected DoubleHistogram operationLatencyRecorder;
  protected LongHistogram retryCountRecorder;
  protected LongHistogram gfeLatencyRecorder;

  protected DoubleHistogram targetResolutionDelayRecorder;
  protected DoubleHistogram channelReadinessDelayRecorder;
  protected DoubleHistogram callSendDelayRecorder;
  protected LongCounter operationCountRecorder;

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
  }

  public void recordAttemptLatency(double attemptLatency) {
    attemptLatencyRecorder.record(attemptLatency);
  }
}
