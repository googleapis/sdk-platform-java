package com.google.api.gax.tracing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.api.gax.tracing.ApiTracerFactory.OperationType;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class MetricsTracerFactoryTest {
  @Mock private MetricsRecorder metricsRecorder;
  @Mock private ApiTracer parent;
  private SpanName spanName;
  private MetricsTracerFactory metricsTracerFactory;

  @Before
  public void setUp() {
    // Create an instance of MetricsTracerFactory with the mocked MetricsRecorder
    metricsTracerFactory = new MetricsTracerFactory(metricsRecorder);

    spanName = mock(SpanName.class);
    when(spanName.getClientName()).thenReturn("testService");
    when(spanName.getMethodName()).thenReturn("testMethod");
  }

  @Test
  public void testNewTracer_notNull() {
    // Call the newTracer method
    ApiTracer apiTracer = metricsTracerFactory.newTracer(parent, spanName, OperationType.Unary);

    // Assert that the apiTracer created has expected type and not null
    assertTrue(apiTracer instanceof MetricsTracer);
    assertNotNull(apiTracer);
  }

  @Test
  public void testNewTracer_HasCorrectParameters()
      throws NoSuchFieldException, IllegalAccessException {

    // Call the newTracer method
    ApiTracer apiTracer = metricsTracerFactory.newTracer(parent, spanName, OperationType.Unary);

    // Assert that the apiTracer created has expected type and not null
    assertTrue(apiTracer instanceof MetricsTracer);
    assertNotNull(apiTracer);

    // Validating "attributes" map created during initialization has correct parameters.
    // Use reflection to access the private field
    Field attributesMap = MetricsTracer.class.getDeclaredField("attributes");
    attributesMap.setAccessible(true);

    // Get the value of the private field and verify it
    Map<String, String> attributes = (Map<String, String>) attributesMap.get(apiTracer);
    assertNotNull(attributes);
    assertEquals("testService.testMethod", attributes.get("method_name"));
  }
}
