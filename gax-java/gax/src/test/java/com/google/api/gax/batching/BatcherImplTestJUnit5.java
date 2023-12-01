/*
 * Copyright 2019 Google LLC
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

import com.google.api.gax.batching.FlowController.LimitExceededBehavior;
import com.google.api.gax.rpc.ApiCallContext;
import com.google.api.gax.rpc.testing.FakeBatchableApi.LabeledIntList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.threeten.bp.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static com.google.api.gax.rpc.testing.FakeBatchableApi.SQUARER_BATCHING_DESC_V2;
import static com.google.api.gax.rpc.testing.FakeBatchableApi.callLabeledIntSquarer;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.mockito.Mockito.when;

public class BatcherImplTestJUnit5 {

  private static final ScheduledExecutorService EXECUTOR =
      Executors.newSingleThreadScheduledExecutor();

  private Batcher<Integer, Integer> underTest;
  private final LabeledIntList labeledIntList = new LabeledIntList("Default");
  private final BatchingSettings batchingSettings =
      BatchingSettings.newBuilder()
          .setElementCountThreshold(1000L)
          .setRequestByteThreshold(1000L)
          .setDelayThreshold(Duration.ofSeconds(1000))
          .build();

  @AfterEach
  public void tearDown() throws InterruptedException {
    if (underTest != null) {
      try {
        // Close the batcher to avoid warnings of orphaned batchers
        underTest.close();
      } catch (BatchingException ignored) {
        // Some tests intentionally inject failures into mutations
      }
    }
  }

  @AfterAll
  public static void tearDownExecutor() throws InterruptedException {
    EXECUTOR.shutdown();
    EXECUTOR.awaitTermination(100, TimeUnit.MILLISECONDS);
  }

  @Test
  public void testThrottlingBlocking() throws Exception {
    BatchingSettings settings =
        BatchingSettings.newBuilder()
            .setElementCountThreshold(1L)
            .setRequestByteThreshold(1L)
            .build();
    FlowController flowController =
        new FlowController(
            FlowControlSettings.newBuilder()
                .setLimitExceededBehavior(LimitExceededBehavior.Block)
                .setMaxOutstandingElementCount(1L)
                .build());
    ExecutorService executor = Executors.newFixedThreadPool(2);

    ApiCallContext callContext = Mockito.mock(ApiCallContext.class);
    ArgumentCaptor<ApiCallContext.Key<Long>> key =
        ArgumentCaptor.forClass(ApiCallContext.Key.class);
    ArgumentCaptor<Long> value = ArgumentCaptor.forClass(Long.class);
    when(callContext.withOption(key.capture(), value.capture())).thenReturn(callContext);
    long throttledTime = 50;

    try (final Batcher<Integer, Integer> batcher =
        new BatcherImpl<>(
            SQUARER_BATCHING_DESC_V2,
            callLabeledIntSquarer,
            labeledIntList,
            settings,
            EXECUTOR,
            flowController,
            callContext)) {
      flowController.reserve(1, 1);
      List<Thread> batcherAddThreadHolder = Collections.synchronizedList(new ArrayList<>());
      Future future =
          executor.submit(
              new Runnable() {
                @Override
                public void run() {
                  batcherAddThreadHolder.add(Thread.currentThread());
                  batcher.add(1);
                }
              });

      // Wait until batcher.add blocks (Thread.State.WAITING) and the batcher starts the
      // stopwatch for total_throttled_time. Without this proper waiting, the
      // Thread.sleep(throttledTime) below may start before the stopwatch starts,
      // resulting in a shorter total_throttled_time at the verification of throttledTime
      // at the end of the test.
      // https://github.com/googleapis/sdk-platform-java/issues/1193
      do {
        Thread.sleep(10);
      } while (batcherAddThreadHolder.isEmpty()
          || batcherAddThreadHolder.get(0).getState() != Thread.State.WAITING);

      executor.submit(
          () -> {
            try {
              Thread.sleep(throttledTime);
              flowController.release(1, 1);
            } catch (InterruptedException e) {
            }
          });

      try {
        future.get(10, TimeUnit.MILLISECONDS);
        assertWithMessage("adding elements to batcher should be blocked by FlowControlled").fail();
      } catch (TimeoutException e) {
        // expected
      }

      try {
        future.get(3, TimeUnit.SECONDS);
      } catch (TimeoutException e) {
        assertWithMessage("adding elements to batcher should not be blocked").fail();
      }

      // Mockito recommends using verify() as the ONLY way to interact with Argument
      // captors - otherwise it may incur in unexpected behaviour.
      // The callContext.withOption method is called by BatcherImpl.sendOutstanding() method via
      // BatcherImpl$PushCurrentBatchRunnable in another thread. Technically, there's no guarantee
      // that the thread calls the withOption method within a certain timeframe. 1000 ms just works
      // fine to prevent false positives.
      // https://github.com/googleapis/sdk-platform-java/issues/1193
      Mockito.verify(callContext, Mockito.timeout(1000)).withOption(key.capture(), value.capture());

      // Verify that throttled time is recorded in ApiCallContext
      assertThat(key.getValue()).isSameInstanceAs(Batcher.THROTTLED_TIME_KEY);
      // Because this test waited for throttledTime before flowController.release() method,
      // the recorded total_throttled_time should be higher than or equal to that.
      assertThat(value.getValue()).isAtLeast(throttledTime);
    } finally {
      executor.shutdownNow();
    }
  }


  private BatcherImpl<Integer, Integer, LabeledIntList, List<Integer>> createDefaultBatcherImpl(
      BatchingSettings settings, FlowController flowController) {
    return new BatcherImpl<>(
        SQUARER_BATCHING_DESC_V2,
        callLabeledIntSquarer,
        labeledIntList,
        settings,
        EXECUTOR,
        flowController);
  }
}
