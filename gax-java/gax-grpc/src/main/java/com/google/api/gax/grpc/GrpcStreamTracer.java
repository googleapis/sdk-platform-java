/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.api.gax.grpc;

import com.google.api.gax.tracing.ApiTracer;
import com.google.common.base.Stopwatch;
import io.grpc.Attributes;
import io.grpc.ClientStreamTracer;
import io.grpc.Metadata;
import java.util.concurrent.TimeUnit;

/**
 * Records the time a request is enqueued in a grpc channel queue. Its primary purpose is to measure
 * the transition time between asking gRPC to start an RPC and gRPC actually serializing that RPC.
 */
class GrpcStreamTracer extends ClientStreamTracer {

  private final Stopwatch stopwatch = Stopwatch.createUnstarted();
  private final ApiTracer tracer;

  public GrpcStreamTracer(ApiTracer tracer) {
    this.tracer = tracer;
    stopwatch.start();
  }

  @Override
  public void createPendingStream() {
    tracer.grpcTargetResolutionDelay(stopwatch.elapsed(TimeUnit.NANOSECONDS));
    stopwatch.reset();
    stopwatch.start();
  }

  @Override
  public void streamCreated(Attributes transportAttrs, Metadata headers) {
    tracer.grpcChannelReadinessDelay(stopwatch.elapsed(TimeUnit.NANOSECONDS));
    stopwatch.reset();
    stopwatch.start();
  }

  @Override
  public void outboundMessageSent(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
    tracer.grpcCallSendDelay(stopwatch.elapsed(TimeUnit.NANOSECONDS));
  }

  static class Factory extends ClientStreamTracer.Factory {

    private final ApiTracer tracer;

    Factory(ApiTracer tracer) {
      this.tracer = tracer;
    }

    @Override
    public ClientStreamTracer newClientStreamTracer(StreamInfo info, Metadata headers) {
      return new GrpcStreamTracer(tracer);
    }
  }
}
