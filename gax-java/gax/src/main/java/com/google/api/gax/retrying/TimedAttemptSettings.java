/*
 * Copyright 2017 Google LLC
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
package com.google.api.gax.retrying;

import static com.google.api.gax.util.TimeConversionUtils.toJavaTimeDuration;
import static com.google.api.gax.util.TimeConversionUtils.toThreetenDuration;

import com.google.api.core.ApiClock;
import com.google.auto.value.AutoValue;

/** Timed attempt execution settings. Defines time-specific properties of a retry attempt. */
@AutoValue
public abstract class TimedAttemptSettings {

  /** Returns global (attempt-independent) retry settings. */
  public abstract RetrySettings getGlobalSettings();

  /** Backport of {@link #getRetryDelayDuration()} */
  public abstract org.threeten.bp.Duration getRetryDelay();

  /**
   * Returns the calculated retry delay. Note that the actual delay used for retry scheduling may be
   * different (randomized, based on this value).
   */
  public final java.time.Duration getRetryDelayDuration() {
    return toJavaTimeDuration(getRetryDelay());
  }

  /** Backport of {@link #getRpcTimeoutDuration()} */
  public abstract org.threeten.bp.Duration getRpcTimeout();

  /** Returns rpc timeout used for this attempt. */
  public final java.time.Duration getRpcTimeoutDuration() {
    return toJavaTimeDuration(getRpcTimeout());
  }

  /** Backport of {@link #getRandomizedRetryDelayDuration()} */
  public abstract org.threeten.bp.Duration getRandomizedRetryDelay();

  /**
   * Returns randomized attempt delay. By default this value is calculated based on the {@code
   * retryDelay} value, and is used as the actual attempt execution delay.
   */
  public final java.time.Duration getRandomizedRetryDelayDuration() {
    return toJavaTimeDuration(getRandomizedRetryDelay());
  }

  /**
   * The attempt count. It is a zero-based value (first attempt will have this value set to 0). For
   * streamed RPCs this will be reset after every successful message.
   */
  public abstract int getAttemptCount();

  /**
   * The overall attempt count. It is a zero-based value (first attempt will have this value set to
   * 0). This will be the sum of all attempt counts for a streaming RPC and will be equal to {@link
   * #getAttemptCount()} for unary RPCs.
   */
  public abstract int getOverallAttemptCount();

  /**
   * The start time of the first attempt. Note that this value is dependent on the actual {@link
   * ApiClock} used during the process.
   */
  public abstract long getFirstAttemptStartTimeNanos();

  public abstract Builder toBuilder();

  public static Builder newBuilder() {
    return new AutoValue_TimedAttemptSettings.Builder().setOverallAttemptCount(0);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    /** Sets global (attempt-independent) retry settings. */
    public abstract Builder setGlobalSettings(RetrySettings value);

    /**
     * Backport of {@link #setRetryDelay(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public abstract Builder setRetryDelay(org.threeten.bp.Duration value);

    /**
     * Sets the calculated retry delay. Note that the actual delay used for retry scheduling may be
     * different (randomized, based on this value).
     */
    public final Builder setRetryDelay(java.time.Duration value) {
      return setRetryDelay(toThreetenDuration(value));
    }

    /**
     * Backport of {@link #setRpcTimeout(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public abstract Builder setRpcTimeout(org.threeten.bp.Duration value);

    /** Sets rpc timeout used for this attempt. */
    public final Builder setRpcTimeout(java.time.Duration value){
      return setRpcTimeout(toThreetenDuration(value));
    }

    /**
     * Backport of {@link #setRandomizedRetryDelay(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public abstract Builder setRandomizedRetryDelay(org.threeten.bp.Duration value);

    /**
     * Sets randomized attempt delay. By default this value is calculated based on the {@code
     * retryDelay} value, and is used as the actual attempt execution delay.
     */
    public final Builder setRandomizedRetryDelay(java.time.Duration value) {
      return setRandomizedRetryDelay(toThreetenDuration(value));
    }

    /**
     * Set the attempt count. It is a zero-based value (first attempt will have this value set to
     * 0).
     */
    public abstract Builder setAttemptCount(int value);

    /**
     * Set the overall attempt count. It is a zero-based value (first attempt will have this value
     * set to 0).
     */
    public abstract Builder setOverallAttemptCount(int value);

    /**
     * Set the start time of the first attempt. Note that this value is dependent on the actual
     * {@link ApiClock} used during the process.
     */
    public abstract Builder setFirstAttemptStartTimeNanos(long value);

    public abstract TimedAttemptSettings build();
  }
}
