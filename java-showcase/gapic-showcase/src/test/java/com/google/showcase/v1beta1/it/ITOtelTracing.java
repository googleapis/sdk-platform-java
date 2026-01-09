/*
 * Copyright 2026 Google LLC
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

package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.gax.tracing.ApiTracer;
import com.google.api.gax.tracing.ApiTracerFactory;
import com.google.api.gax.tracing.BaseApiTracer;
import com.google.api.gax.tracing.SpanName;
import com.google.api.gax.tracing.TracingRecorder;
import com.google.api.gax.tracing.TracingTracer;
import com.google.api.gax.tracing.TracingTracerFactory;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class ITOtelTracing {

  private static class DummyTracingRecorder implements TracingRecorder {
    private boolean lowLevelSpanHit = false;

    @Override
    public void recordLowLevelNetworkSpan(Map<String, String> attributes) {
      lowLevelSpanHit = true;
    }

    public boolean wasLowLevelSpanHit() {
      return lowLevelSpanHit;
    }
  }

  @Test
  void testTracingFeatureFlag() {
    System.setProperty("GOOGLE_CLOUD_ENABLE_TRACING", "false");
    TracingTracerFactory factory = new TracingTracerFactory(new DummyTracingRecorder());
    ApiTracer tracer = factory.newTracer(BaseApiTracer.getInstance(), SpanName.of("EchoClient", "Echo"), ApiTracerFactory.OperationType.Unary);
    assertThat(tracer).isNotInstanceOf(TracingTracer.class);

    System.setProperty("GOOGLE_CLOUD_ENABLE_TRACING", "true");
    tracer = factory.newTracer(BaseApiTracer.getInstance(), SpanName.of("EchoClient", "Echo"), ApiTracerFactory.OperationType.Unary);
    assertThat(tracer).isInstanceOf(TracingTracer.class);
    System.clearProperty("GOOGLE_CLOUD_ENABLE_TRACING");
  }

  @Test
  void testTracingTracer_recordsLowLevelNetworkSpan() throws Exception {
    System.setProperty("GOOGLE_CLOUD_ENABLE_TRACING", "true");
    DummyTracingRecorder recorder = new DummyTracingRecorder();
    TracingTracerFactory factory = new TracingTracerFactory(recorder);
    try (EchoClient client = TestClientInitializer.createGrpcEchoClientOpentelemetry(factory)) {
      client.echo(EchoRequest.newBuilder().setContent("test").build());
      assertThat(recorder.wasLowLevelSpanHit()).isTrue();
    } finally {
      System.clearProperty("GOOGLE_CLOUD_ENABLE_TRACING");
    }
  }
}