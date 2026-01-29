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

package com.google.api.gax.tracing;

import com.google.api.core.BetaApi;
import com.google.api.core.InternalApi;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import java.util.Map;

/**
 * OpenTelemetry implementation of recording traces. This implementation collects the measurements
 * related to the lifecyle of an RPC.
 */
@BetaApi
@InternalApi
public class OpenTelemetryTracingRecorder implements TracingRecorder {
  private final Tracer tracer;

  public OpenTelemetryTracingRecorder(OpenTelemetry openTelemetry) {
    this.tracer = openTelemetry.getTracer("gax-java");
  }

  @Override
  public SpanHandle startSpan(String name, Map<String, String> attributes) {
    return startSpan(name, attributes, null);
  }

  @Override
  public SpanHandle startSpan(String name, Map<String, String> attributes, SpanHandle parent) {
    SpanBuilder spanBuilder =
        tracer.spanBuilder(name).setSpanKind(SpanKind.CLIENT); // Mark as a network-facing call

    if (attributes != null) {
      attributes.forEach((k, v) -> spanBuilder.setAttribute(k, v));
    }

    if (parent instanceof OtelSpanHandle) {
      spanBuilder.setParent(Context.current().with(((OtelSpanHandle) parent).span));
    }

    Span span = spanBuilder.startSpan();

    return new OtelSpanHandle(span);
  }

  @Override
  @SuppressWarnings("MustBeClosedChecker")
  public ApiTracer.Scope inScope(SpanHandle handle) {
    if (handle instanceof OtelSpanHandle) {
      Scope scope = ((OtelSpanHandle) handle).span.makeCurrent();
      return scope::close;
    }
    return () -> {};
  }

  private static class OtelSpanHandle implements SpanHandle {
    private final Span span;

    private OtelSpanHandle(Span span) {
      this.span = span;
    }

    @Override
    public void end() {
      span.end();
    }

    @Override
    public void recordError(Throwable error) {
      span.recordException(error);
      span.setAttribute(
          OpenTelemetryTracingTracer.ERROR_TYPE_ATTRIBUTE, TracerUtils.extractStatus(error));
      span.setStatus(StatusCode.ERROR);
    }

    @Override
    public void setAttribute(String key, String value) {
      span.setAttribute(key, value);
    }
  }
}
