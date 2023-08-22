/*
 * Copyright 2016 Google LLC
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
package com.google.api.gax.grpc;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.core.ListenableFutureToApiFuture;
import com.google.api.gax.rpc.ApiCallContext;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.gax.tracing.ApiTracer;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.stub.ClientCalls;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code GrpcDirectCallable} creates gRPC calls.
 *
 * <p>Package-private for internal use.
 */
class GrpcDirectCallable<RequestT, ResponseT> extends UnaryCallable<RequestT, ResponseT> {
  private final MethodDescriptor<RequestT, ResponseT> descriptor;
  private final boolean awaitTrailers;

  GrpcDirectCallable(MethodDescriptor<RequestT, ResponseT> descriptor, boolean awaitTrailers) {
    this.descriptor = Preconditions.checkNotNull(descriptor);
    this.awaitTrailers = awaitTrailers;
  }

  @Override
  public ApiFuture<ResponseT> futureCall(RequestT request, ApiCallContext inputContext) {
    Preconditions.checkNotNull(request);
    Preconditions.checkNotNull(inputContext);
    final GrpcResponseMetadata responseMetadata = new GrpcResponseMetadata();
    GrpcCallContext grpcCallContext = responseMetadata.addHandlers(inputContext);
    ClientCall<RequestT, ResponseT> clientCall =
        GrpcClientCalls.newCall(descriptor, grpcCallContext);
    GfeUnaryCallback<ResponseT> callback =
        new GfeUnaryCallback<ResponseT>(inputContext.getTracer(), responseMetadata);
    ApiFuture<ResponseT> future;
    if (awaitTrailers) {
      future = new ListenableFutureToApiFuture<>(ClientCalls.futureUnaryCall(clientCall, request));
    } else {
      future = GrpcClientCalls.eagerFutureUnaryCall(clientCall, request);
    }
    ApiFutures.addCallback(future, callback, MoreExecutors.directExecutor());
    return future;
  }

  @Override
  public String toString() {
    return String.format("direct(%s)", descriptor);
  }

  private static final Metadata.Key<String> SERVER_TIMING_HEADER_KEY =
      Metadata.Key.of("server-timing", Metadata.ASCII_STRING_MARSHALLER);

  private static final Pattern SERVER_TIMING_HEADER_PATTERN = Pattern.compile(".*dur=(?<dur>\\d+)");

  static class GfeUnaryCallback<ResponseT> implements ApiFutureCallback<ResponseT> {

    private final ApiTracer tracer;
    private final GrpcResponseMetadata responseMetadata;

    GfeUnaryCallback(ApiTracer tracer, GrpcResponseMetadata responseMetadata) {
      this.tracer = tracer;
      this.responseMetadata = responseMetadata;
    }

    @Override
    public void onFailure(Throwable throwable) {
      //      Util.recordMetricsFromMetadata(responseMetadata, tracer, throwable);
    }

    @Override
    public void onSuccess(ResponseT response) {
      Metadata metadata = responseMetadata.getMetadata();
      if (metadata == null) {
        return;
      }
      String allKeys = metadata.keys().stream().reduce((a, b) -> a + ", " + b).get();
      //      System.out.println(
      //          "************************ metadata size: "
      //              + metadata.keys().size()
      //              + ", all keys: "
      //              + allKeys);
      if (metadata.get(SERVER_TIMING_HEADER_KEY) == null) {
        return;
      }

      String durMetadata = metadata.get(SERVER_TIMING_HEADER_KEY);
      Matcher matcher = SERVER_TIMING_HEADER_PATTERN.matcher(durMetadata);
      // this should always be true
      if (matcher.find()) {
        long latency = Long.valueOf(matcher.group("dur"));
        tracer.recordGfeMetadata(latency);
      }
      //      System.out.println("GFE metadata: " + metadata.get(SERVER_TIMING_HEADER_KEY));
    }
  }
}
