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
import java.util.HashMap;
import java.util.Map;

@BetaApi
@InternalApi
public class OpenTelemetryTracingTracer extends BaseApiTracer {
  public static final String LANGUAGE_ATTRIBUTE = "gcp.client.language";
  public static final String DEFAULT_LANGUAGE = "Java";

  private final TracingRecorder recorder;
  private final Map<String, String> operationAttributes;
  private final Map<String, String> attemptAttributes;
  private final String attemptSpanName;
  private final TracingRecorder.SpanHandle operationHandle;
  private TracingRecorder.SpanHandle attemptHandle;

  public OpenTelemetryTracingTracer(
      TracingRecorder recorder, String operationSpanName, String attemptSpanName) {
    this.recorder = recorder;
    this.attemptSpanName = attemptSpanName;
    this.operationAttributes = new HashMap<>();
    this.attemptAttributes = new HashMap<>();
    this.attemptAttributes.put(LANGUAGE_ATTRIBUTE, DEFAULT_LANGUAGE);
    this.operationAttributes.put("method", operationSpanName);

    // Start the long-lived operation span
    this.operationHandle = recorder.startSpan(operationSpanName, operationAttributes);
  }

  @Override
  public Scope inScope() {
    // If an attempt is in progress, make it current so downstream spans are its children.
    // Otherwise, make the operation span current.
    if (attemptHandle != null) {
      return recorder.inScope(attemptHandle);
    }
    return recorder.inScope(operationHandle);
  }

  @Override
  public void attemptStarted(Object request, int attemptNumber) {
    Map<String, String> attemptAttributes = new HashMap<>(this.attemptAttributes);
    attemptAttributes.put("attemptNumber", String.valueOf(attemptNumber));

    // Start the specific attempt span with the operation span as parent
    this.attemptHandle = recorder.startSpan(attemptSpanName, attemptAttributes, operationHandle);
  }

  @Override
  public void attemptSucceeded() {
    endAttempt();
  }

  @Override
  public void attemptCancelled() {
    endAttempt();
  }

  @Override
  public void attemptFailedDuration(Throwable error, java.time.Duration delay) {
    if (attemptHandle != null) {
      attemptHandle.recordError(error);
    }
    endAttempt();
  }

  @Override
  public void attemptFailedRetriesExhausted(Throwable error) {
    if (attemptHandle != null) {
      attemptHandle.recordError(error);
    }
    endAttempt();
  }

  @Override
  public void attemptPermanentFailure(Throwable error) {
    if (attemptHandle != null) {
      attemptHandle.recordError(error);
    }
    endAttempt();
  }

  private void endAttempt() {
    if (attemptHandle != null) {
      attemptHandle.end();
      attemptHandle = null;
    }
  }

  @Override
  public void operationSucceeded() {
    operationHandle.end();
  }

  @Override
  public void operationCancelled() {
    operationHandle.end();
  }

  @Override
  public void operationFailed(Throwable error) {
    operationHandle.recordError(error);
    operationHandle.end();
  }

  public void addOperationAttributes(Map<String, String> attributes) {
    this.operationAttributes.putAll(attributes);
    if (operationHandle != null) {
      attributes.forEach((k, v) -> operationHandle.setAttribute(k, v));
    }
  }

  public void addAttemptAttributes(Map<String, String> attributes) {
    this.attemptAttributes.putAll(attributes);
    if (attemptHandle != null) {
      attributes.forEach((k, v) -> attemptHandle.setAttribute(k, v));
    }
  }
}
