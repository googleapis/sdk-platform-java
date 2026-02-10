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
import com.google.common.annotations.VisibleForTesting;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link ApiTracerFactory} to build instances of {@link TracingTracer}.
 *
 * <p>This class wraps the {@link TracingRecorder} and pass it to {@link TracingTracer}. It will be
 * used to record traces in {@link TracingTracer}.
 *
 * <p>This class is expected to be initialized once during client initialization.
 */
@BetaApi
@InternalApi
public class TracingTracerFactory implements ApiTracerFactory {
  private final TracingRecorder tracingRecorder;

  /** Mapping of client attributes that are set for every TracingTracer at operation level */
  private final Map<String, String> operationAttributes;

  /** Mapping of client attributes that are set for every TracingTracer at attempt level */
  private final Map<String, String> attemptAttributes;

  /** Creates a TracingTracerFactory */
  public TracingTracerFactory(TracingRecorder tracingRecorder) {
    this(tracingRecorder, new HashMap<>(), new HashMap<>());
  }

  /**
   * Pass in a Map of client level attributes which will be added to every single TracingTracer
   * created from the ApiTracerFactory. This is package private since span attributes are determined
   * internally.
   */
  @VisibleForTesting
  TracingTracerFactory(
      TracingRecorder tracingRecorder,
      Map<String, String> operationAttributes,
      Map<String, String> attemptAttributes) {
    this.tracingRecorder = tracingRecorder;

    this.operationAttributes = new HashMap<>(operationAttributes);
    this.attemptAttributes = new HashMap<>(attemptAttributes);
  }

  @Override
  public ApiTracer newTracer(ApiTracer parent, SpanName spanName, OperationType operationType) {
    // TODO(diegomarquezp): these are placeholders for span names and will be adjusted as the
    // feature is developed.
    String operationSpanName =
        spanName.getClientName() + "." + spanName.getMethodName() + "/operation";
    String attemptSpanName = spanName.getClientName() + "/" + spanName.getMethodName() + "/attempt";

    TracingTracer tracingTracer =
        new TracingTracer(
            tracingRecorder,
            operationSpanName,
            attemptSpanName,
            this.operationAttributes,
            this.attemptAttributes);
    return tracingTracer;
  }

  @Override
  public ApiTracerFactory withContext(ApiTracerContext context) {
    Map<String, String> newAttemptAttributes = new HashMap<>(this.attemptAttributes);
    if (context.getEndpointContext() != null) {
      newAttemptAttributes.put(
          TracingTracer.SERVER_ADDRESS_ATTRIBUTE,
          context.getEndpointContext().resolvedServerAddress());
    }
    return new TracingTracerFactory(tracingRecorder, operationAttributes, newAttemptAttributes);
  }
}
