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

/**
 * An implementation of {@link ApiTracer} that uses a {@link TraceRecorder} to record traces. This
 * implementation is agnostic to the specific {@link TraceRecorder} in order to allow extensions
 * that interact with other backends.
 */
@BetaApi
@InternalApi
public class AppCentricTracer implements ApiTracer {
  public static final String SERVER_ADDRESS_ATTRIBUTE = "server.address";
  public static final String LANGUAGE_ATTRIBUTE = "gcp.client.language";

  public static final String DEFAULT_LANGUAGE = "Java";

  private final TraceRecorder recorder;
  private final Map<String, String> attemptAttributes;
  private final String attemptSpanName;
  private final TraceRecorder.TraceSpan operationHandle;
  private TraceRecorder.TraceSpan attemptHandle;

  /**
   * Creates a new instance of {@code AppCentricTracer}.
   *
   * @param recorder the {@link TraceRecorder} to use for recording spans
   * @param operationSpanName the name of the long-lived operation span
   * @param attemptSpanName the name of the individual attempt spans
   * @param operationAttributes attributes to be added to the operation span
   * @param attemptAttributes attributes to be added to each attempt span
   */
  public AppCentricTracer(
      TraceRecorder recorder,
      String operationSpanName,
      String attemptSpanName,
      Map<String, String> operationAttributes,
      Map<String, String> attemptAttributes) {
    this.recorder = recorder;
    this.attemptSpanName = attemptSpanName;
    this.attemptAttributes = new HashMap<>(attemptAttributes);
    this.attemptAttributes.put(LANGUAGE_ATTRIBUTE, DEFAULT_LANGUAGE);

    // Start the long-lived operation span.
    this.operationHandle = recorder.createSpan(operationSpanName, operationAttributes);
  }

  @Override
  public void attemptStarted(Object request, int attemptNumber) {
    Map<String, String> attemptAttributes = new HashMap<>(this.attemptAttributes);
    // Start the specific attempt span with the operation span as parent
    this.attemptHandle = recorder.createSpan(attemptSpanName, attemptAttributes, operationHandle);
  }

  @Override
  public void attemptSucceeded() {
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
}
