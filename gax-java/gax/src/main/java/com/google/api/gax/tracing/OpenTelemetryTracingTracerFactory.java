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
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link ApiTracerFactory} to build instances of {@link OpenTelemetryTracingTracer}.
 *
 * <p>This class wraps the {@link TracingRecorder} and pass it to {@link
 * OpenTelemetryTracingTracer}. It will be used to record traces in {@link
 * OpenTelemetryTracingTracer}.
 *
 * <p>This class is expected to be initialized once during client initialization.
 */
@BetaApi
@InternalApi
public class OpenTelemetryTracingTracerFactory implements ApiTracerFactory {
  public static final String SERVICE_NAME_ATTRIBUTE = "gcp.client.service";
  public static final String PORT_ATTRIBUTE = "server.port";
  public static final String RPC_SYSTEM_ATTRIBUTE = "rpc.system";

  private final TracingRecorder tracingRecorder;

  /** Mapping of client attributes that are set for every TracingTracer at operation level */
  private final Map<String, String> operationAttributes;

  /** Mapping of client attributes that are set for every TracingTracer at attempt level */
  private final Map<String, String> attemptAttributes;

  /** Creates a TracingTracerFactory with no additional client level attributes. */
  public OpenTelemetryTracingTracerFactory(TracingRecorder tracingRecorder) {
    this(tracingRecorder, ImmutableMap.of(), ImmutableMap.of());
  }

  /**
   * Pass in a Map of client level attributes which will be added to every single TracingTracer
   * created from the ApiTracerFactory.
   */
  public OpenTelemetryTracingTracerFactory(
      TracingRecorder tracingRecorder,
      Map<String, String> operationAttributes,
      Map<String, String> attemptAttributes) {
    this.tracingRecorder = tracingRecorder;
    this.operationAttributes = ImmutableMap.copyOf(operationAttributes);
    this.attemptAttributes = ImmutableMap.copyOf(attemptAttributes);
  }

  @Override
  public ApiTracer newTracer(ApiTracer parent, SpanName spanName, OperationType operationType) {
    // TODO(diegomarquezp): use span names from design
    String operationSpanName =
        spanName.getClientName() + "." + spanName.getMethodName() + "/operation";
    String attemptSpanName = spanName.getClientName() + "/" + spanName.getMethodName() + "/attempt";

    OpenTelemetryTracingTracer tracingTracer =
        new OpenTelemetryTracingTracer(tracingRecorder, operationSpanName, attemptSpanName);
    tracingTracer.addOperationAttributes(operationAttributes);
    tracingTracer.addAttemptAttributes(attemptAttributes);
    return tracingTracer;
  }

  @Override
  public ApiTracerFactory withAttributes(
      Map<String, String> operationAttributes, Map<String, String> attemptAttributes) {
    Map<String, String> newOperationAttributes = new HashMap<>(this.operationAttributes);
    newOperationAttributes.putAll(operationAttributes);
    Map<String, String> newAttemptAttributes = new HashMap<>(this.attemptAttributes);
    newAttemptAttributes.putAll(attemptAttributes);
    return new OpenTelemetryTracingTracerFactory(
        tracingRecorder, newOperationAttributes, newAttemptAttributes);
  }
}
