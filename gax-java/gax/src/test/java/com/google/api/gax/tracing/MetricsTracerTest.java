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

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.google.api.gax.retrying.ServerStreamingAttemptException;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.DeadlineExceededException;
import com.google.api.gax.rpc.NotFoundException;
import com.google.api.gax.rpc.StatusCode.Code;
import com.google.api.gax.rpc.testing.FakeStatusCode;
import com.google.api.gax.tracing.ApiTracerFactory.OperationType;
import com.google.cloud.opentelemetry.metric.GoogleCloudMetricExporter;
import com.google.cloud.opentelemetry.metric.MetricConfiguration;
import com.google.cloud.opentelemetry.metric.MetricDescriptorStrategy;
import com.google.common.collect.ImmutableMap;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.Span;
import io.opencensus.trace.Status;
import io.opencensus.trace.Status.CanonicalCode;
import io.opencensus.trace.Tracer;

import java.util.HashMap;
import java.util.Map;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.exporter.prometheus.PrometheusHttpServer;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.threeten.bp.Duration;

@RunWith(JUnit4.class)
public class MetricsTracerTest {
    @Rule
    public final MockitoRule mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);
    private MetricsTracer metricsTracer;
    @Mock
    private MetricsRecorder metricsRecorder;

    @Before
    public void setUp() {
        metricsTracer = new MetricsTracer(SpanName.of("FakeClient", "FakeMethod"), metricsRecorder);
    }

    // Ask Blake - how can we verify record{attempt/operation}Latency methods? we would need a timer for that?
    @Test
    public void testSuccessExample() {
        // initialize mock-request & mock-response
        Object mockRequest = new Object();
        Object mockResponse = new Object();

        //Attempt #1
        metricsTracer.attemptStarted(mockRequest, 1);
        metricsTracer.attemptSucceeded(mockResponse);
        metricsTracer.operationSucceeded(mockResponse);

        long count = 1;
        Map<String, String> attributes = ImmutableMap.of(
                "status", "OK",
                "method_name", "FakeClient.FakeMethod");

        verify(metricsRecorder).recordAttemptCount(count, attributes);
        verify(metricsRecorder).recordOperationCount(count,attributes);
    }

    @Test
    public void testFailureExample() {
        // initialize mock-request
        Object mockRequest = new Object();

        //Attempt #1
        metricsTracer.attemptStarted(mockRequest, 1);
        ApiException error0 =
                new NotFoundException("invalid argument", null, new FakeStatusCode(Code.INVALID_ARGUMENT), false);
        metricsTracer.attemptFailed(error0, Duration.ofMillis(2));
        metricsTracer.operationFailed(error0);

        long count = 1;
        Map<String, String> attributes = ImmutableMap.of(
                "status", "INVALID_ARGUMENT",
                "method_name", "FakeClient.FakeMethod");

        verify(metricsRecorder).recordAttemptCount(count, attributes);
        verify(metricsRecorder).recordOperationCount(count,attributes);

        //cannot do this because we have more interactions with the mock (latency functions)
        // verifyNoMoreInteractions(metricsRecorder);
    }

    @Test
    public void testCancelledExample() {
        // initialize mock-request
        Object mockRequest = new Object();

        //Attempt #1
        metricsTracer.attemptStarted(mockRequest, 1);
        metricsTracer.attemptCancelled();
        metricsTracer.operationCancelled();

        long count = 1;
        Map<String, String> attributes = ImmutableMap.of(
                "status", "CANCELLED",
                "method_name", "FakeClient.FakeMethod");

        verify(metricsRecorder).recordAttemptCount(count, attributes);
        verify(metricsRecorder).recordOperationCount(count,attributes);
    }

    @Test
    public void testAttemptFailedRetriesExhaustedExample() {
        // initialize mock-request
        Object mockRequest = new Object();

        //Attempt #1
        metricsTracer.attemptStarted(mockRequest, 1);
        ApiException error0 =
                new DeadlineExceededException(
                        "deadline exceeded", null, new FakeStatusCode(Code.DEADLINE_EXCEEDED), false);
        metricsTracer.attemptFailedRetriesExhausted(error0);
        metricsTracer.operationFailed(error0);

        long count = 1;
        Map<String, String> attributes = ImmutableMap.of(
                "status", "DEADLINE_EXCEEDED",
                "method_name", "FakeClient.FakeMethod");

        verify(metricsRecorder).recordAttemptCount(count, attributes);
        verify(metricsRecorder).recordOperationCount(count,attributes);

        //cannot do this because we have more interactions with the mock (latency functions)
        // verifyNoMoreInteractions(metricsRecorder);
    }

    @Test
    public void testAttemptPermanentFailureExample() {
        // initialize mock-request
        Object mockRequest = new Object();

        //Attempt #1
        metricsTracer.attemptStarted(mockRequest, 1);

        ApiException error0 =
                new NotFoundException("not found", null, new FakeStatusCode(Code.NOT_FOUND), false);

        metricsTracer.attemptFailedRetriesExhausted(error0);
        metricsTracer.operationFailed(error0);

        long count = 1;
        Map<String, String> attributes = ImmutableMap.of(
                "status", "NOT_FOUND",
                "method_name", "FakeClient.FakeMethod");

        verify(metricsRecorder).recordAttemptCount(count, attributes);
        verify(metricsRecorder).recordOperationCount(count,attributes);

        //cannot do this because we have more interactions with the mock (latency functions)
        // verifyNoMoreInteractions(metricsRecorder);
    }

    @Test
    public void testTwoAttemptsFirstFailSecondSuccess() {
        // initialize mock-request & mock-response
        Object mockRequest = new Object();
        Object mockResponse = new Object();

        //Attempt #1
        metricsTracer.attemptStarted(mockRequest, 1);
        ApiException error0 =
                new NotFoundException("not found", null, new FakeStatusCode(Code.NOT_FOUND), false);
        metricsTracer.attemptFailed(error0, Duration.ofMillis(2));

        //Attempt #2
        metricsTracer.attemptStarted(mockRequest, 2);
        metricsTracer.attemptSucceeded(mockResponse);
//        metricsTracer.operationSucceeded(mockResponse);

        long count = 1;
        Map<String, String> failedAttributes = ImmutableMap.of(
                "status", "NOT_FOUND",
                "method_name", "FakeClient.FakeMethod");

//        Map<String, String> successAttributes = ImmutableMap.of(
//                "status", "OK",
//                "method_name", "FakeClient.FakeMethod");

        verify(metricsRecorder).recordAttemptCount(count, failedAttributes);

//        verify(metricsRecorder).recordAttemptCount(count,successAttributes);
//        verify(metricsRecorder).recordOperationCount(count, successAttributes);

    }


}