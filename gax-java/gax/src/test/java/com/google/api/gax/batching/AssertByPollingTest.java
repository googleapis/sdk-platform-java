package com.google.api.gax.batching;

import static com.google.api.gax.batching.AssertByPolling.assertByPollingEvery;

import com.google.common.truth.Truth;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.Test;

public class AssertByPollingTest {

  @Test(expected = AssertionError.class)
  public void testFailsWhenTimeoutExceeded() throws InterruptedException {
    assertByPollingEvery(1, TimeUnit.MILLISECONDS)
        .withTimeout(2, TimeUnit.NANOSECONDS)
        .thatEventually(Assert::fail);
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

    assertByPollingEvery(1, TimeUnit.MILLISECONDS)
        .withTimeout(1, TimeUnit.NANOSECONDS)
        .thatEventually(succeedsAfter1ms);
  }

  @Test
  public void testSucceedsThirdTime() throws InterruptedException {
    AtomicInteger attempt = new AtomicInteger(1);
    AtomicInteger numFailures = new AtomicInteger(0);
    Runnable succeedsThirdTime =
        () -> {
          if (attempt.getAndIncrement() < 3) {
            numFailures.incrementAndGet();
            Assert.fail();
          }
        };

    assertByPollingEvery(1, TimeUnit.NANOSECONDS)
        .withTimeout(100, TimeUnit.MILLISECONDS)
        .thatEventually(succeedsThirdTime);

    Truth.assertThat(numFailures.get()).isEqualTo(2);
  }
}
