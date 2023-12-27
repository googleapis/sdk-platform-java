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

import static io.opentelemetry.api.common.AttributeKey.stringKey;

import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.StatusCode;
import com.google.common.base.Stopwatch;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.threeten.bp.Duration;

public class OpenTelemetryMetricsTracer implements ApiTracer {
  public static final String STATUS_ATTRIBUTE = "status";
  protected Meter meter;

  private Stopwatch attemptTimer;

  private final Stopwatch operationTimer = Stopwatch.createStarted();

  private final SpanName spanName;

  protected DoubleHistogram attemptLatencyRecorder;
  protected LongCounter attemptCountRecorder;

  protected DoubleHistogram operationLatencyRecorder;
  protected LongHistogram retryCountRecorder;
  protected LongHistogram gfeLatencyRecorder;

  protected DoubleHistogram targetResolutionDelayRecorder;
  protected DoubleHistogram channelReadinessDelayRecorder;
  protected DoubleHistogram callSendDelayRecorder;
  protected LongCounter operationCountRecorder;

  protected Attributes attributes;

  Map<String, String> operationLatencyLabels = new HashMap<>();

  public OpenTelemetryMetricsTracer(Meter meter, SpanName spanName) {
    this.meter = meter;
    this.spanName = spanName;
    this.attemptLatencyRecorder =
        meter
            .histogramBuilder(attemptLatencyName())
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
    this.attributes = Attributes.of(stringKey("method_name"), spanName.toString());
  }

  @Override
  public Scope inScope() {
    return () -> {};
  }

  @Override
  public void operationSucceeded() {}

  // This is just one way to add labels, we could have another layer of abstraction(a separate
  // class) just for get/set labels because the logic is generic.
  public void addOperationLatencyLabels(String key, String value) {
    operationLatencyLabels.put(key, value);
  }

  @Override
  public void operationSucceeded(Object response) {
    AttributesBuilder attributesBuilder = Attributes.builder();
    attributesBuilder.putAll(attributes);
    attributesBuilder.put(STATUS_ATTRIBUTE, StatusCode.Code.OK.toString());
    operationLatencyLabels.forEach((key, value) -> attributesBuilder.put(stringKey(key), value));
    Attributes allAttributes = attributesBuilder.build();
    operationLatencyRecorder.record(operationTimer.elapsed(TimeUnit.MILLISECONDS), allAttributes);
    // increment operation-count by 1
    operationCountRecorder.add(1, allAttributes);
  }

  @Override
  public void operationCancelled() {
    // we should capture the attributes in cancelled operations as well, to be consistent with
    // opencensus implementation
    AttributesBuilder attributesBuilder = Attributes.builder();
    attributesBuilder.putAll(attributes);
    attributesBuilder.put(STATUS_ATTRIBUTE, StatusCode.Code.CANCELLED.toString());

    operationLatencyLabels.forEach((key, value) -> attributesBuilder.put(stringKey(key), value));
    Attributes allAttributes = attributesBuilder.build();
    operationLatencyRecorder.record(operationTimer.elapsed(TimeUnit.MILLISECONDS), allAttributes);

    // increment operation-count by 1
    operationCountRecorder.add(1, allAttributes);
  }

  @Override
  public void operationFailed(Throwable error) {

    // we should capture the attributes in failed operations, to be consistent with opencensus
    // implementation
    AttributesBuilder attributesBuilder = Attributes.builder();
    attributesBuilder.putAll(attributes);
    attributesBuilder.put(STATUS_ATTRIBUTE, extractStatus(error));

    // imo, entries in the operationLatencyLabels map should be added to the builder.
    operationLatencyLabels.forEach((key, value) -> attributesBuilder.put(stringKey(key), value));
    Attributes allAttributes = attributesBuilder.build();
    operationLatencyRecorder.record(operationTimer.elapsed(TimeUnit.MILLISECONDS), allAttributes);

    // increment operation-count by 1
    operationCountRecorder.add(1, allAttributes);
  }

  @Override
  public void attemptStarted(int attemptNumber) {}

  @Override
  public void attemptStarted(Object request, int attemptNumber) {
    attemptTimer = Stopwatch.createStarted();
  }

  @Override
  public void attemptSucceeded() {}

  @Override
  public void attemptSucceeded(Object response) {
    Attributes newAttributes =
        attributes.toBuilder().put(STATUS_ATTRIBUTE, StatusCode.Code.OK.toString()).build();
    attemptLatencyRecorder.record(attemptTimer.elapsed(TimeUnit.MILLISECONDS), newAttributes);
    attemptCountRecorder.add(1, newAttributes);
  }

  @Override
  public void attemptCancelled() {
    Attributes newAttributes =
        attributes.toBuilder().put(STATUS_ATTRIBUTE, StatusCode.Code.CANCELLED.toString()).build();
    attemptLatencyRecorder.record(attemptTimer.elapsed(TimeUnit.MILLISECONDS), newAttributes);
    attemptCountRecorder.add(1, newAttributes);
  }

  @Override
  public void attemptFailed(Throwable error, Duration delay) {
    Attributes newAttributes =
        attributes.toBuilder().put(STATUS_ATTRIBUTE, extractStatus(error)).build();
    attemptLatencyRecorder.record(attemptTimer.elapsed(TimeUnit.MILLISECONDS), newAttributes);
    attemptCountRecorder.add(1, newAttributes);
  }

  @Override
  public void attemptFailedRetriesExhausted(Throwable error) {}

  @Override
  public void attemptPermanentFailure(Throwable error) {}

  @Override
  public void retryCount(int count) {
    retryCountRecorder.record(count, attributes);
  }

  @Override
  public void grpcTargetResolutionDelay(long elapsed) {
    this.targetResolutionDelayRecorder.record(elapsed, attributes);
  }

  @Override
  public void grpcChannelReadinessDelay(long elapsed) {
    this.channelReadinessDelayRecorder.record(elapsed, attributes);
  }

  @Override
  public void grpcCallSendDelay(long elapsed) {
    this.callSendDelayRecorder.record(elapsed, attributes);
  }

  @Override
  public void recordGfeMetadata(long latency) {
    this.gfeLatencyRecorder.record(latency);
  }

  static String extractStatus(@Nullable Throwable error) {
    final String statusString;

    if (error == null) {
      return StatusCode.Code.OK.toString();
    } else if (error instanceof CancellationException) {
      statusString = StatusCode.Code.CANCELLED.toString();
    } else if (error instanceof ApiException) {
      statusString = ((ApiException) error).getStatusCode().getCode().toString();
    } else {
      statusString = StatusCode.Code.UNKNOWN.toString();
    }

    return statusString;
  }
}
