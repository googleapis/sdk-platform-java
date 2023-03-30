/*
 * Copyright 2023 Google LLC
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
