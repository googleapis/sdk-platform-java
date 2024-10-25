package com.google.api.gax.httpjson.testing;

import com.google.api.gax.tracing.BaseApiTracer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.threeten.bp.Duration;

/**
 * Test tracer that keeps count of different events. See {@link TestApiTracerFactory} for more
 * details.
 */
public class TestApiTracer extends BaseApiTracer {

  private final AtomicInteger tracerAttempts;
  private final AtomicInteger tracerAttemptsFailed;
  private final AtomicBoolean tracerOperationFailed;
  private final AtomicBoolean tracerFailedRetriesExhausted;

  public TestApiTracer(
      AtomicInteger tracerAttempts,
      AtomicInteger tracerAttemptsFailed,
      AtomicBoolean tracerOperationFailed,
      AtomicBoolean tracerFailedRetriesExhausted) {
    this.tracerAttempts = tracerAttempts;
    this.tracerAttemptsFailed = tracerAttemptsFailed;
    this.tracerOperationFailed = tracerOperationFailed;
    this.tracerFailedRetriesExhausted = tracerFailedRetriesExhausted;
  }

  @Override
  public void attemptFailed(Throwable error, Duration delay) {
    tracerAttemptsFailed.incrementAndGet();
    super.attemptFailed(error, delay);
  }

  @Override
  public void attemptStarted(int attemptNumber) {
    tracerAttempts.incrementAndGet();
    super.attemptStarted(attemptNumber);
  }

  @Override
  public void operationFailed(Throwable error) {
    tracerOperationFailed.set(true);
    super.operationFailed(error);
  }

  @Override
  public void attemptFailedRetriesExhausted(Throwable error) {
    tracerFailedRetriesExhausted.set(true);
    super.attemptFailedRetriesExhausted(error);
  }
};
