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

import com.google.common.base.Stopwatch;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import org.threeten.bp.Duration;

import java.util.concurrent.TimeUnit;

import static io.opentelemetry.api.common.AttributeKey.stringKey;

public class OpenTelemetryMetricsTracer implements ApiTracer {
    protected Meter meter;

    private Stopwatch attemptTimer;

    private SpanName spanName;

    public OpenTelemetryMetricsTracer(Meter meter, SpanName spanName) {
        this.meter = meter;
        this.spanName = spanName;
    }

    @Override
    public Scope inScope() {
        return () -> {};
    }

    @Override
    public void operationSucceeded() {

    }

    @Override
    public void operationCancelled() {

    }

    @Override
    public void operationFailed(Throwable error) {

    }

    @Override
    public void connectionSelected(String id) {

    }

    @Override
    public void attemptStarted(int attemptNumber) {

    }

    @Override
    public void attemptStarted(Object request, int attemptNumber) {
        attemptTimer = Stopwatch.createStarted();
        LongCounter longCounter = meter.counterBuilder("attempt_count")
                .setDescription("Attempt Count")
                .setUnit("1")
                .build();
        longCounter.add(1);
    }

    @Override
    public void attemptSucceeded() {
        DoubleHistogram doubleHistogram = meter.histogramBuilder(attemptLatencyName() + "-attempt_latency")
                .setDescription("Duration of an individual operation attempt")
                .setUnit("ms")
                .build();
        Attributes attributes = Attributes.of(stringKey("method_name"), spanName.toString());
        doubleHistogram.record(attemptTimer.elapsed(TimeUnit.MILLISECONDS), attributes);
    }

    @Override
    public void attemptCancelled() {

    }

    @Override
    public void attemptFailed(Throwable error, Duration delay) {

    }

    @Override
    public void attemptFailedRetriesExhausted(Throwable error) {

    }

    @Override
    public void attemptPermanentFailure(Throwable error) {

    }

    @Override
    public void lroStartFailed(Throwable error) {

    }

    @Override
    public void lroStartSucceeded() {

    }

    @Override
    public void responseReceived() {

    }

    @Override
    public void requestSent() {

    }

    @Override
    public void batchRequestSent(long elementCount, long requestSize) {

    }
}
