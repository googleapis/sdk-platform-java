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
