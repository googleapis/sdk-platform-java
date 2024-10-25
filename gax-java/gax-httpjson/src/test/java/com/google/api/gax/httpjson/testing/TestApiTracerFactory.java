package com.google.api.gax.httpjson.testing;

import com.google.api.gax.tracing.ApiTracer;
import com.google.api.gax.tracing.ApiTracerFactory;
import com.google.api.gax.tracing.SpanName;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Produces a {@link TestApiTracer}, which keeps count of the attempts made and attempts
 * made-and-failed. It also keeps count of the operations failed and when the retries have been
 * exhausted.
 */
public class TestApiTracerFactory implements ApiTracerFactory {
  private final AtomicInteger tracerAttempts = new AtomicInteger();
  private final AtomicInteger tracerAttemptsFailed = new AtomicInteger();
  private final AtomicBoolean tracerOperationFailed = new AtomicBoolean(false);
  private final AtomicBoolean tracerFailedRetriesExhausted = new AtomicBoolean(false);
  private final TestApiTracer instance =
      new TestApiTracer(
          tracerAttempts,
          tracerAttemptsFailed,
          tracerOperationFailed,
          tracerFailedRetriesExhausted);

  public AtomicInteger getTracerAttempts() {
    return tracerAttempts;
  }

  public AtomicInteger getTracerAttemptsFailed() {
    return tracerAttemptsFailed;
  }

  public AtomicBoolean getTracerOperationFailed() {
    return tracerOperationFailed;
  }

  public AtomicBoolean getTracerFailedRetriesExhausted() {
    return tracerFailedRetriesExhausted;
  }

  @Override
  public ApiTracer newTracer(ApiTracer parent, SpanName spanName, OperationType operationType) {
    return instance;
  }
}
