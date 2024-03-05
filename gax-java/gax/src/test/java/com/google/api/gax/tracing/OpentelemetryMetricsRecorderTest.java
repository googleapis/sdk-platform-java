/*
 * Copyright 2024 Google LLC
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

import static org.junit.Assert.assertThrows;

import com.google.common.collect.ImmutableMap;
import com.google.common.truth.Truth;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.data.Data;
import io.opentelemetry.sdk.metrics.data.HistogramPointData;
import io.opentelemetry.sdk.metrics.data.LongPointData;
import io.opentelemetry.sdk.metrics.data.MetricData;
import io.opentelemetry.sdk.metrics.data.PointData;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.testing.exporter.InMemoryMetricExporter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OpentelemetryMetricsRecorderTest {
  private OpentelemetryMetricsRecorder otelMetricsRecorder;
  private static InMemoryMetricExporter exporter;
  private static PeriodicMetricReader periodicMetricReader;

  @BeforeClass
  public static void setUpBeforeClass() {
    exporter = InMemoryMetricExporter.create();
    periodicMetricReader = PeriodicMetricReader.create(exporter);
  }

  @Before
  public void setUp() {
    SdkMeterProvider sdkMeterProvider =
        SdkMeterProvider.builder().registerMetricReader(periodicMetricReader).build();
    OpenTelemetry openTelemetry =
        OpenTelemetrySdk.builder().setMeterProvider(sdkMeterProvider).build();
    otelMetricsRecorder = new OpentelemetryMetricsRecorder(openTelemetry, "MetricsRecorderTest");
  }

  @After
  public void cleanUp() {
    exporter.reset();
  }

  @AfterClass
  public static void cleanUpAfterClass() {
    exporter.shutdown();
  }

  private void verifyAttributesRecorded(
      Attributes pointDataAttributes, Map<String, String> attributes) {
    Map<AttributeKey<?>, Object> attributesMap = pointDataAttributes.asMap();
    for (Map.Entry<AttributeKey<?>, Object> entrySet : attributesMap.entrySet()) {
      String key = entrySet.getKey().getKey();
      String value = entrySet.getValue().toString();
      Truth.assertThat(attributes.containsKey(key)).isTrue();
      Truth.assertThat(attributes.get(key)).isEqualTo(value);
    }
  }

  private List<PointData> extractPointDataList() {
    List<MetricData> finishedMetricItems = exporter.getFinishedMetricItems();
    Truth.assertThat(finishedMetricItems.size()).isEqualTo(1);
    MetricData metricData = finishedMetricItems.get(0);
    Data<?> data = metricData.getData();
    return new ArrayList<>(data.getPoints());
  }

  @Test
  public void recordAttemptCount_singleAttempt() {
    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "OK",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    otelMetricsRecorder.recordAttemptCount(1, attributes);
    periodicMetricReader.forceFlush();

    List<PointData> pointData = extractPointDataList();
    Truth.assertThat(pointData.size()).isEqualTo(1);
    Truth.assertThat(pointData.get(0)).isInstanceOf(LongPointData.class);
    LongPointData longPointData = (LongPointData) pointData.get(0);
    Truth.assertThat(longPointData.getValue()).isEqualTo(1);

    Attributes pointDataAttributes = longPointData.getAttributes();
    verifyAttributesRecorded(pointDataAttributes, attributes);
  }

  @Test
  public void recordAttemptCount_multipleAttempt() {
    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "OK",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    otelMetricsRecorder.recordAttemptCount(1, attributes);
    otelMetricsRecorder.recordAttemptCount(1, attributes);
    otelMetricsRecorder.recordAttemptCount(1, attributes);
    periodicMetricReader.forceFlush();

    List<PointData> pointData = extractPointDataList();
    Truth.assertThat(pointData.size()).isEqualTo(1);
    Truth.assertThat(pointData.get(0)).isInstanceOf(LongPointData.class);
    LongPointData longPointData = (LongPointData) pointData.get(0);
    Truth.assertThat(longPointData.getValue()).isEqualTo(3);

    Attributes pointDataAttributes = longPointData.getAttributes();
    verifyAttributesRecorded(pointDataAttributes, attributes);
  }

  @Test
  public void recordAttemptLatency_singleAttempt() {
    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "NOT_FOUND",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    otelMetricsRecorder.recordAttemptLatency(1.1, attributes);
    periodicMetricReader.forceFlush();

    List<PointData> pointData = extractPointDataList();
    Truth.assertThat(pointData.size()).isEqualTo(1);
    Truth.assertThat(pointData.get(0)).isInstanceOf(HistogramPointData.class);
    HistogramPointData histogramPointData = (HistogramPointData) pointData.get(0);
    Truth.assertThat(histogramPointData.getCount()).isEqualTo(1);

    Attributes pointDataAttributes = histogramPointData.getAttributes();
    verifyAttributesRecorded(pointDataAttributes, attributes);
  }

  @Test
  public void recordAttemptLatency_multipleAttempts() {
    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "NOT_FOUND",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    otelMetricsRecorder.recordAttemptLatency(1.1, attributes);
    otelMetricsRecorder.recordAttemptLatency(1.2, attributes);
    otelMetricsRecorder.recordAttemptLatency(1.3, attributes);
    periodicMetricReader.forceFlush();

    List<PointData> pointData = extractPointDataList();
    Truth.assertThat(pointData.size()).isEqualTo(1);
    Truth.assertThat(pointData.get(0)).isInstanceOf(HistogramPointData.class);
    HistogramPointData histogramPointData = (HistogramPointData) pointData.get(0);
    Truth.assertThat(histogramPointData.getCount()).isEqualTo(3);

    Attributes pointDataAttributes = histogramPointData.getAttributes();
    verifyAttributesRecorded(pointDataAttributes, attributes);
  }

  @Test
  public void recordOperationCount() {
    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "OK",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    otelMetricsRecorder.recordOperationCount(1, attributes);
    periodicMetricReader.forceFlush();

    List<PointData> pointData = extractPointDataList();
    Truth.assertThat(pointData.size()).isEqualTo(1);
    Truth.assertThat(pointData.get(0)).isInstanceOf(LongPointData.class);
    LongPointData longPointData = (LongPointData) pointData.get(0);
    Truth.assertThat(longPointData.getValue()).isEqualTo(1);

    Attributes pointDataAttributes = longPointData.getAttributes();
    verifyAttributesRecorded(pointDataAttributes, attributes);
  }

  @Test
  public void recordOperationLatency() {
    Map<String, String> attributes =
        ImmutableMap.of(
            "status", "INVALID_ARGUMENT",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    otelMetricsRecorder.recordOperationLatency(1.7, attributes);
    periodicMetricReader.forceFlush();

    List<PointData> pointData = extractPointDataList();
    Truth.assertThat(pointData.size()).isEqualTo(1);
    Truth.assertThat(pointData.get(0)).isInstanceOf(HistogramPointData.class);
    HistogramPointData histogramPointData = (HistogramPointData) pointData.get(0);
    Truth.assertThat(histogramPointData.getCount()).isEqualTo(1);

    Attributes pointDataAttributes = histogramPointData.getAttributes();
    verifyAttributesRecorded(pointDataAttributes, attributes);
  }

  @Test
  public void toOtelAttributes_correctConversion() {
    ImmutableMap<String, String> attributes =
        ImmutableMap.of(
            "status", "OK",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    Attributes otelAttributes = otelMetricsRecorder.toOtelAttributes(attributes);

    Truth.assertThat(otelAttributes.get(AttributeKey.stringKey("status"))).isEqualTo("OK");
    Truth.assertThat(otelAttributes.get(AttributeKey.stringKey("method_name")))
        .isEqualTo("fake_service.fake_method");
    Truth.assertThat(otelAttributes.get(AttributeKey.stringKey("language"))).isEqualTo("Java");
  }

  @Test
  public void toOtelAttributes_nullInput() {
    NullPointerException exception =
        assertThrows(NullPointerException.class, () -> otelMetricsRecorder.toOtelAttributes(null));
    Truth.assertThat(exception).hasMessageThat().contains("Attributes map cannot be null");
  }
}
