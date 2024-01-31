package com.google.api.gax.tracing;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.google.common.collect.ImmutableMap;
import com.google.common.truth.Truth;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.DoubleHistogramBuilder;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongCounterBuilder;
import io.opentelemetry.api.metrics.Meter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

@RunWith(JUnit4.class)
public class OpentelemetryMetricsRecorderTest {

  // stricter way of testing for early detection of unused stubs and argument mismatches
  @Rule
  public final MockitoRule mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

  private OpentelemetryMetricsRecorder otelMetricsRecorder;

  @Mock private Meter meter;
  @Mock private LongCounter attemptCountRecorder;
  @Mock private LongCounterBuilder attemptCountRecorderBuilder;
  @Mock private DoubleHistogramBuilder attemptLatencyRecorderBuilder;
  @Mock private DoubleHistogram attemptLatencyRecorder;
  @Mock private DoubleHistogram operationLatencyRecorder;
  @Mock private DoubleHistogramBuilder operationLatencyRecorderBuilder;
  @Mock private LongCounter operationCountRecorder;
  @Mock private LongCounterBuilder operationCountRecorderBuilder;

  @Before
  public void setUp() {

    meter = Mockito.mock(Meter.class);

    // setup mocks for all the recorders using chained mocking
    setupAttemptCountRecorder();
    setupAttemptLatencyRecorder();
    setupOperationLatencyRecorder();
    setupOperationCountRecorder();

    otelMetricsRecorder = new OpentelemetryMetricsRecorder(meter);
  }

  @Test
  public void testAttemptCountRecorder_recordsAttributes() {

    ImmutableMap<String, String> attributes =
        ImmutableMap.of(
            "status", "OK",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    Attributes otelAttributes = otelMetricsRecorder.toOtelAttributes(attributes);
    otelMetricsRecorder.recordAttemptCount(1, attributes);

    verify(attemptCountRecorder).add(1, otelAttributes);
    verifyNoMoreInteractions(attemptCountRecorder);
  }

  @Test
  public void testAttemptLatencyRecorder_recordsAttributes() {

    ImmutableMap<String, String> attributes =
        ImmutableMap.of(
            "status", "NOT_FOUND",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    Attributes otelAttributes = otelMetricsRecorder.toOtelAttributes(attributes);
    otelMetricsRecorder.recordAttemptLatency(1.1, attributes);

    verify(attemptLatencyRecorder).record(1.1, otelAttributes);
    verifyNoMoreInteractions(attemptLatencyRecorder);
  }

  @Test
  public void testOperationCountRecorder_recordsAttributes() {

    ImmutableMap<String, String> attributes =
        ImmutableMap.of(
            "status", "OK",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    Attributes otelAttributes = otelMetricsRecorder.toOtelAttributes(attributes);
    otelMetricsRecorder.recordOperationCount(1, attributes);

    verify(operationCountRecorder).add(1, otelAttributes);
    verifyNoMoreInteractions(operationCountRecorder);
  }

  @Test
  public void testOperationLatencyRecorder_recordsAttributes() {

    ImmutableMap<String, String> attributes =
        ImmutableMap.of(
            "status", "INVALID_ARGUMENT",
            "method_name", "fake_service.fake_method",
            "language", "Java");

    Attributes otelAttributes = otelMetricsRecorder.toOtelAttributes(attributes);
    otelMetricsRecorder.recordOperationLatency(1.7, attributes);

    verify(operationLatencyRecorder).record(1.7, otelAttributes);
    verifyNoMoreInteractions(operationLatencyRecorder);
  }

  @Test
  public void testToOtelAttributes_correctConversion() {

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
  public void testToOtelAttributes_nullInput() {

    Throwable thrown =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              otelMetricsRecorder.toOtelAttributes(null);
            });
    Truth.assertThat(thrown).hasMessageThat().contains("Input attributes map cannot be null");
  }

  /// this is a potential candidate for test in the future when we enforce non-null values in the
  // attributes map
  // will remove this before merging the PR
  // @Test
  // public void testToOtelAttributes_nullKeyValuePair() {
  //
  //
  //   Map<String, String> attributes = new HashMap<>();
  //   attributes.put("status", "OK");
  //   attributes.put("method_name", "fake_service.fake_method");
  //   attributes.put("language", "Java");
  //   // try to insert a key-value pair with a null value
  //   attributes.put("fakeDatabaseId", null);
  //
  //   Attributes otelAttributes = otelMetricsRecorder.toOtelAttributes(attributes);
  //
  //   //only 3 attributes should be added to the Attributes object
  //   Truth.assertThat(otelAttributes.get(AttributeKey.stringKey("status"))).isEqualTo("OK");
  //
  // Truth.assertThat(otelAttributes.get(AttributeKey.stringKey("method_name"))).isEqualTo("fake_service.fake_method");
  //   Truth.assertThat(otelAttributes.get(AttributeKey.stringKey("language"))).isEqualTo("Java");
  //
  //   // attributes should only have 3 entries since the 4th attribute value is null
  //   Truth.assertThat(otelAttributes.size()).isEqualTo(3);
  // }

  private void setupAttemptCountRecorder() {

    attemptCountRecorderBuilder = Mockito.mock(LongCounterBuilder.class);
    attemptCountRecorder = Mockito.mock(LongCounter.class);

    // Configure chained mocking for AttemptCountRecorder
    Mockito.when(meter.counterBuilder("attempt_count")).thenReturn(attemptCountRecorderBuilder);
    Mockito.when(attemptCountRecorderBuilder.setDescription("Count of Attempts"))
        .thenReturn(attemptCountRecorderBuilder);
    Mockito.when(attemptCountRecorderBuilder.setUnit("1")).thenReturn(attemptCountRecorderBuilder);
    Mockito.when(attemptCountRecorderBuilder.build()).thenReturn(attemptCountRecorder);
  }

  private void setupOperationCountRecorder() {

    operationCountRecorderBuilder = Mockito.mock(LongCounterBuilder.class);
    operationCountRecorder = Mockito.mock(LongCounter.class);

    // Configure chained mocking for operationCountRecorder
    Mockito.when(meter.counterBuilder("operation_count")).thenReturn(operationCountRecorderBuilder);
    Mockito.when(operationCountRecorderBuilder.setDescription("Count of Operations"))
        .thenReturn(operationCountRecorderBuilder);
    Mockito.when(operationCountRecorderBuilder.setUnit("1"))
        .thenReturn(operationCountRecorderBuilder);
    Mockito.when(operationCountRecorderBuilder.build()).thenReturn(operationCountRecorder);
  }

  private void setupAttemptLatencyRecorder() {

    attemptLatencyRecorderBuilder = Mockito.mock(DoubleHistogramBuilder.class);
    attemptLatencyRecorder = Mockito.mock(DoubleHistogram.class);

    // Configure chained mocking for attemptLatencyRecorder
    Mockito.when(meter.histogramBuilder("attempt_latency"))
        .thenReturn(attemptLatencyRecorderBuilder);
    Mockito.when(attemptLatencyRecorderBuilder.setDescription("Duration of an individual attempt"))
        .thenReturn(attemptLatencyRecorderBuilder);
    Mockito.when(attemptLatencyRecorderBuilder.setUnit("ms"))
        .thenReturn(attemptLatencyRecorderBuilder);
    Mockito.when(attemptLatencyRecorderBuilder.build()).thenReturn(attemptLatencyRecorder);
  }

  private void setupOperationLatencyRecorder() {

    operationLatencyRecorderBuilder = Mockito.mock(DoubleHistogramBuilder.class);
    operationLatencyRecorder = Mockito.mock(DoubleHistogram.class);

    // Configure chained mocking for operationLatencyRecorder
    Mockito.when(meter.histogramBuilder("operation_latency"))
        .thenReturn(operationLatencyRecorderBuilder);
    Mockito.when(
            operationLatencyRecorderBuilder.setDescription(
                "Total time until final operation success or failure, including retries and backoff."))
        .thenReturn(operationLatencyRecorderBuilder);
    Mockito.when(operationLatencyRecorderBuilder.setUnit("ms"))
        .thenReturn(operationLatencyRecorderBuilder);
    Mockito.when(operationLatencyRecorderBuilder.build()).thenReturn(operationLatencyRecorder);
  }
}
