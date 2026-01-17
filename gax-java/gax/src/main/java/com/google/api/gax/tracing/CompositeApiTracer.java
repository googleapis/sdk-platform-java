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

import com.google.api.core.InternalApi;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;

/** A composite implementation of {@link ApiTracer} that broadcasts events to multiple tracers. */
@InternalApi
public class CompositeApiTracer implements ApiTracer {
  private final List<ApiTracer> tracers;

  public CompositeApiTracer(List<ApiTracer> tracers) {
    this.tracers = ImmutableList.copyOf(tracers);
  }

  @Override
  public Scope inScope() {
    List<Scope> scopes = tracers.stream().map(ApiTracer::inScope).collect(Collectors.toList());
    return () -> scopes.forEach(Scope::close);
  }

  @Override
  public void operationSucceeded() {
    tracers.forEach(ApiTracer::operationSucceeded);
  }

  @Override
  public void operationCancelled() {
    tracers.forEach(ApiTracer::operationCancelled);
  }

  @Override
  public void operationFailed(Throwable error) {
    tracers.forEach(t -> t.operationFailed(error));
  }

  @Override
  public void connectionSelected(String id) {
    tracers.forEach(t -> t.connectionSelected(id));
  }

  @Override
  public void attemptStarted(int attemptNumber) {
    tracers.forEach(t -> t.attemptStarted(attemptNumber));
  }

  @Override
  public void attemptStarted(Object request, int attemptNumber) {
    tracers.forEach(t -> t.attemptStarted(request, attemptNumber));
  }

  @Override
  public void attemptSucceeded() {
    tracers.forEach(ApiTracer::attemptSucceeded);
  }

  @Override
  public void attemptCancelled() {
    tracers.forEach(ApiTracer::attemptCancelled);
  }

  @Override
  public void attemptFailedDuration(Throwable error, java.time.Duration delay) {
    tracers.forEach(t -> t.attemptFailedDuration(error, delay));
  }

  @Override
  public void attemptFailedRetriesExhausted(Throwable error) {
    tracers.forEach(t -> t.attemptFailedRetriesExhausted(error));
  }

  @Override
  public void attemptPermanentFailure(Throwable error) {
    tracers.forEach(t -> t.attemptPermanentFailure(error));
  }

  @Override
  public void lroStartFailed(Throwable error) {
    tracers.forEach(t -> t.lroStartFailed(error));
  }

  @Override
  public void lroStartSucceeded() {
    tracers.forEach(ApiTracer::lroStartSucceeded);
  }

  @Override
  public void responseReceived() {
    tracers.forEach(ApiTracer::responseReceived);
  }

  @Override
  public void requestSent() {
    tracers.forEach(ApiTracer::requestSent);
  }

  @Override
  public void batchRequestSent(long elementCount, long requestSize) {
    tracers.forEach(t -> t.batchRequestSent(elementCount, requestSize));
  }
}
