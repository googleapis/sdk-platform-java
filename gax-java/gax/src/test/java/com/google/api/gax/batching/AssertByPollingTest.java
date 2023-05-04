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

import static com.google.api.gax.batching.AssertByPolling.assertByPolling;

import com.google.common.truth.Truth;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.Test;

public class AssertByPollingTest {

  @Test
  public void testFailsWhenTimeoutExceeded() {
    AssertionError error =
        Assert.assertThrows(
            AssertionError.class,
            () -> assertByPolling(Duration.ofNanos(2), () -> Truth.assertThat(1).isAtLeast(2)));

    Throwable cause = error.getCause();
    Truth.assertThat(cause).isInstanceOf(AssertionError.class);
    // Error provides original assertion failure that never came true.
    Truth.assertThat(cause.getMessage()).contains("expected to be at least");
  }

  @Test
  public void testImmediateSuccessSucceedsRegardlessOfTimeout() throws InterruptedException {
    Runnable succeedsAfter1ms =
        () -> {
          try {
            TimeUnit.MILLISECONDS.sleep(1);
          } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
          }
        };
    Duration timeout = Duration.ofNanos(0);
    assertByPolling(timeout, succeedsAfter1ms);
  }

  @Test
  public void testSucceedsAfterInitialFailure() throws InterruptedException {
    AtomicInteger attempt = new AtomicInteger(1);
    AtomicInteger numFailures = new AtomicInteger(0);
    Runnable succeedsSecondTime =
        () -> {
          if (attempt.getAndIncrement() < 2) {
            numFailures.incrementAndGet();
            Assert.fail();
          }
        };

    Duration timeout = Duration.ofMillis(300);
    assertByPolling(timeout, succeedsSecondTime);
    Truth.assertThat(numFailures.get()).isEqualTo(1);
  }
}
