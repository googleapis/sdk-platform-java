package com.google.api.gax.batching;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Blocks the current thread to poll the given assertion until it's successful or the timeout is
 * exceeded. Expected usage:
 *
 * <pre>{@code
 * assertByPollingEvery(10, MILLISECONDS)
 *     .withTimeout(2, SECONDS)
 *     .thatEventually(() -> assertThat(...));
 * }</pre>
 */
public class AssertByPolling {
  public static Builder assertByPollingEvery(long pollingInterval, TimeUnit timeUnit) {
    Preconditions.checkArgument(pollingInterval > 0, "pollingTime must be greater than 0");
    Objects.requireNonNull(timeUnit, "Polling TimeUnit must not be null");
    return new Builder(pollingInterval, timeUnit);
  }

  public static class Builder {
    private final long pollingInterval;
    private final TimeUnit pollingUnit;

    private Builder(long pollingInterval, TimeUnit pollingUnit) {
      Preconditions.checkArgument(pollingInterval > 0, "pollingTime must be greater than 0");
      this.pollingInterval = pollingInterval;
      this.pollingUnit = Objects.requireNonNull(pollingUnit, "TimeUnit must not be null");
    }

    public PollingConfiguration withTimeout(long timeout, TimeUnit timeoutUnit) {
      Preconditions.checkArgument(timeout > 0, "timeout must be greater than 0");
      Objects.requireNonNull(timeoutUnit, "TimeUnit must not be null");
      return new PollingConfiguration(pollingInterval, pollingUnit, timeout, timeoutUnit);
    }
  }

  public static class PollingConfiguration {
    private final long pollingInterval;
    private final TimeUnit pollingUnit;
    private final long timeout;
    private final TimeUnit timeoutUnit;

    private PollingConfiguration(
        long pollingInterval, TimeUnit pollingUnit, long timeout, TimeUnit timeoutUnit) {
      this.pollingInterval = pollingInterval;
      this.pollingUnit = pollingUnit;
      this.timeout = timeout;
      this.timeoutUnit = timeoutUnit;
    }

    /** @param assertion must throw an AssertionError when indicating failure */
    public void thatEventually(Runnable assertion) throws InterruptedException {
      Stopwatch stopwatch = Stopwatch.createStarted();
      while (true) {
        try {
          assertion.run();
          return; // Success

        } catch (AssertionError err) {
          if (stopwatch.elapsed(timeoutUnit) < timeout) {
            pollingUnit.sleep(pollingInterval);
          } else {
            throw new AssertionError("Timeout waiting for successful assertion.", err);
          }
        }
      }
    }
  }
}
