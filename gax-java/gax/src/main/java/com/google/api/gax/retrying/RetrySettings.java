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
package com.google.api.gax.retrying;

import com.google.api.core.BetaApi;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import java.io.Serializable;

/**
 * Holds the parameters for <b>retry</b> or <b>poll</b> logic with jitter, timeout and exponential
 * backoff. Actual implementation of the logic is elsewhere.
 *
 * <p>The intent of these settings is to be used with a call to a remote server, which could either
 * fail (and return an error code) or not respond (and cause a timeout). When there is a failure or
 * timeout, the logic should keep trying until the total timeout has passed.
 *
 * <p>The "total timeout" and "max attempts" settings have ultimate control over how long the logic
 * should keep trying the remote call until it gives up completely. The remote call will be retried
 * until one of those thresholds is crossed. To avoid unbounded rpc calls, it is required to
 * configure one of those settings. If both are 0, then retries will be disabled. The other settings
 * are considered more advanced.
 *
 * <p>Retry delay and timeout start at specific values, and are tracked separately from each other.
 * The very first call (before any retries) will use the initial timeout.
 *
 * <p>If the last remote call is a failure, then the retrier will wait for the current retry delay
 * before attempting another call, and then the retry delay will be multiplied by the retry delay
 * multiplier for the next failure. The timeout will not be affected, except in the case where the
 * timeout would result in a deadline past the total timeout; in that circumstance, a new timeout
 * value is computed which will terminate the call when the total time is up.
 *
 * <p>If the last remote call is a timeout, then the retrier will compute a new timeout and make
 * another call. The new timeout is computed by multiplying the current timeout by the timeout
 * multiplier, but if that results in a deadline after the total timeout, then a new timeout value
 * is computed which will terminate the call when the total time is up.
 *
 * <p>Server streaming RPCs interpret RPC timeouts a bit differently. For server streaming RPCs, the
 * RPC timeout gets converted into a wait timeout {@link
 * com.google.api.gax.rpc.ApiCallContext#withStreamWaitTimeout(java.time.Duration)}.
 */
@AutoValue
public abstract class RetrySettings implements Serializable {

  private static final long serialVersionUID = 8258475264439710899L;

  /** Backport of {@link #getTotalTimeoutDuration()} */
  public final org.threeten.bp.Duration getTotalTimeout() {
    return org.threeten.bp.Duration.ofNanos(getTotalTimeoutDuration().toNanos());
  }

  /**
   * TotalTimeout has ultimate control over how long the logic should keep trying the remote call
   * until it gives up completely. The higher the total timeout, the more retries can be attempted.
   * The default value is {@code Duration.ZERO}.
   */
  public abstract java.time.Duration getTotalTimeoutDuration();

  /** Backport of {@link #getInitialRetryDelayDuration()} */
  public final org.threeten.bp.Duration getInitialRetryDelay() {
    return org.threeten.bp.Duration.ofNanos(getInitialRetryDelayDuration().toNanos());
  }

  /**
   * InitialRetryDelay controls the delay before the first retry. Subsequent retries will use this
   * value adjusted according to the RetryDelayMultiplier. The default value is {@code
   * Duration.ZERO}.
   */
  public abstract java.time.Duration getInitialRetryDelayDuration();

  /**
   * RetryDelayMultiplier controls the change in retry delay. The retry delay of the previous call
   * is multiplied by the RetryDelayMultiplier to calculate the retry delay for the next call. The
   * default value is {@code 1.0}.
   */
  public abstract double getRetryDelayMultiplier();

  /** Backport of {@link #getMaxRetryDelayDuration()} */
  public final org.threeten.bp.Duration getMaxRetryDelay() {
    return org.threeten.bp.Duration.ofNanos(getMaxRetryDelayDuration().toNanos());
  }

  /**
   * MaxRetryDelay puts a limit on the value of the retry delay, so that the RetryDelayMultiplier
   * can't increase the retry delay higher than this amount. The default value is {@code
   * Duration.ZERO}.
   */
  public abstract java.time.Duration getMaxRetryDelayDuration();

  /**
   * MaxAttempts defines the maximum number of attempts to perform. The default value is {@code 0}.
   * If this value is greater than 0, and the number of attempts reaches this limit, the logic will
   * give up retrying even if the total retry time is still lower than TotalTimeout.
   */
  public abstract int getMaxAttempts();

  /**
   * Jitter determines if the delay time should be randomized. In most cases, if jitter is set to
   * {@code true} the actual delay time is calculated in the following way:
   *
   * <pre>{@code actualDelay = rand_between(0, min(maxRetryDelay, delay))}</pre>
   *
   * The default value is {@code true}.
   *
   * @deprecated Retries always jitter.
   */
  @Deprecated
  @VisibleForTesting
  public abstract boolean isJittered();

  /** Backport of {@link #getInitialRpcTimeoutDuration()} */
  public final org.threeten.bp.Duration getInitialRpcTimeout() {
    return org.threeten.bp.Duration.ofNanos(getInitialRpcTimeoutDuration().toNanos());
  }

  /**
   * InitialRpcTimeout controls the timeout for the initial RPC. Subsequent calls will use this
   * value adjusted according to the RpcTimeoutMultiplier. The default value is {@code
   * Duration.ZERO}.
   */
  public abstract java.time.Duration getInitialRpcTimeoutDuration();

  /**
   * RpcTimeoutMultiplier controls the change in RPC timeout. The timeout of the previous call is
   * multiplied by the RpcTimeoutMultiplier to calculate the timeout for the next call. The default
   * value is {@code 1.0}.
   */
  public abstract double getRpcTimeoutMultiplier();

  /** Backport of {@link #getMaxRpcTimeoutDuration()} */
  public final org.threeten.bp.Duration getMaxRpcTimeout() {
    return org.threeten.bp.Duration.ofNanos(getMaxRpcTimeoutDuration().toNanos());
  }

  /**
   * MaxRpcTimeout puts a limit on the value of the RPC timeout, so that the RpcTimeoutMultiplier
   * can't increase the RPC timeout higher than this amount. The default value is {@code
   * Duration.ZERO}.
   */
  public abstract java.time.Duration getMaxRpcTimeoutDuration();

  public static Builder newBuilder() {
    return new AutoValue_RetrySettings.Builder()
        .setTotalTimeout(java.time.Duration.ZERO)
        .setInitialRetryDelay(java.time.Duration.ZERO)
        .setRetryDelayMultiplier(1.0)
        .setMaxRetryDelay(java.time.Duration.ZERO)
        .setMaxAttempts(0)
        .setJittered(true)
        .setInitialRpcTimeout(java.time.Duration.ZERO)
        .setRpcTimeoutMultiplier(1.0)
        .setMaxRpcTimeout(java.time.Duration.ZERO);
  }

  public abstract Builder toBuilder();

  /**
   * A base builder class for {@link RetrySettings}. See the class documentation of {@link
   * RetrySettings} for a description of the different values that can be set.
   */
  @AutoValue.Builder
  public abstract static class Builder {

    /**
     * Overload of {@link #setTotalTimeout(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public final Builder setTotalTimeout(org.threeten.bp.Duration totalTimeout) {
      return setTotalTimeoutDuration(java.time.Duration.ofNanos(totalTimeout.toNanos()));
    }

    /**
     * Overload of {@link #setTotalTimeout(java.time.Duration)} using {@link
     * org.threeten.bp.Duration} This is a convenience public method to keep name conformity
     */
    public final Builder setTotalTimeout(java.time.Duration totalTimeout) {
      return setTotalTimeoutDuration(java.time.Duration.ofNanos(totalTimeout.toNanos()));
    }

    /**
     * TotalTimeout has ultimate control over how long the logic should keep trying the remote call
     * until it gives up completely. The higher the total timeout, the more retries can be
     * attempted. The default value is {@code Duration.ZERO}.
     */
    public abstract Builder setTotalTimeoutDuration(java.time.Duration totalTimeout);

    /**
     * Overload of {@link #setInitialRetryDelay(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public final Builder setInitialRetryDelay(org.threeten.bp.Duration initialDelay) {
      return setInitialRetryDelay(java.time.Duration.ofNanos(initialDelay.toNanos()));
    }

    /**
     * Overload of {@link #setInitialRetryDelay(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public final Builder setInitialRetryDelay(java.time.Duration initialDelay) {
      return setInitialRetryDelayDuration(initialDelay);
    }

    /**
     * InitialRetryDelay controls the delay before the first retry. Subsequent retries will use this
     * value adjusted according to the RetryDelayMultiplier. The default value is {@code
     * Duration.ZERO}.
     */
    public abstract Builder setInitialRetryDelayDuration(java.time.Duration initialDelay);

    /**
     * RetryDelayMultiplier controls the change in retry delay. The retry delay of the previous call
     * is multiplied by the RetryDelayMultiplier to calculate the retry delay for the next call. The
     * default value is {@code 1.0}.
     */
    public abstract Builder setRetryDelayMultiplier(double multiplier);

    /**
     * Overload of {@link #setMaxRetryDelayDuration(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public final Builder setMaxRetryDelay(org.threeten.bp.Duration maxDelay) {
      return setMaxRetryDelayDuration(java.time.Duration.ofNanos(maxDelay.toNanos()));
    }

    /**
     * Overload of {@link #setMaxRetryDelayDuration(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public final Builder setMaxRetryDelay(java.time.Duration maxDelay) {
      return setMaxRetryDelayDuration(maxDelay);
    }

    /**
     * MaxRetryDelay puts a limit on the value of the retry delay, so that the RetryDelayMultiplier
     * can't increase the retry delay higher than this amount. The default value is {@code
     * Duration.ZERO}.
     */
    abstract Builder setMaxRetryDelayDuration(java.time.Duration maxDelay);

    /**
     * MaxAttempts defines the maximum number of attempts to perform. The default value is {@code
     * 0}. If this value is greater than 0, and the number of attempts reaches this limit, the logic
     * will give up retrying even if the total retry time is still lower than TotalTimeout.
     */
    public abstract Builder setMaxAttempts(int maxAttempts);

    /**
     * Jitter determines if the delay time should be randomized. If jitter is set to {@code true}
     * the actual delay time is calculated in the following way:
     *
     * <pre>{@code actualDelay = rand_between(0, min(maxRetryDelay, exponentialDelay))}</pre>
     *
     * The default value is {@code true}, and this method will be a no-op soon.
     *
     * @deprecated Retries always jitter.
     */
    @Deprecated
    @VisibleForTesting
    public abstract Builder setJittered(boolean jittered);

    /**
     * Overload of {@link #setInitialRpcTimeoutDuration(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public final Builder setInitialRpcTimeout(org.threeten.bp.Duration initialTimeout) {
      return setInitialRpcTimeoutDuration(java.time.Duration.ofNanos(initialTimeout.toNanos()));
    }

    /**
     * Overload of {@link #setInitialRpcTimeoutDuration(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public final Builder setInitialRpcTimeout(java.time.Duration initialTimeout) {
      return setInitialRpcTimeoutDuration(initialTimeout);
    }

    /**
     * InitialRpcTimeout controls the timeout for the initial RPC. Subsequent calls will use this
     * value adjusted according to the RpcTimeoutMultiplier. The default value is {@code
     * Duration.ZERO}.
     */
    abstract Builder setInitialRpcTimeoutDuration(java.time.Duration initialTimeout);

    /**
     * See the class documentation of {@link RetrySettings} for a description of what this value
     * does. The default value is {@code 1.0}.
     */
    public abstract Builder setRpcTimeoutMultiplier(double multiplier);

    /**
     * Overload of {@link #setMaxRpcTimeoutDuration(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    public final Builder setMaxRpcTimeout(org.threeten.bp.Duration maxTimeout) {
      return setMaxRpcTimeoutDuration(java.time.Duration.ofNanos(maxTimeout.toNanos()));
    }

    /**
     * Overload of {@link #setMaxRpcTimeoutDuration(java.time.Duration)} using {@link
     * java.time..Duration} This is a convenience public method to keep name conformity
     */
    public final Builder setMaxRpcTimeout(java.time.Duration maxTimeout) {
      return setMaxRpcTimeoutDuration(maxTimeout);
    }

    /**
     * MaxRpcTimeout puts a limit on the value of the RPC timeout, so that the RpcTimeoutMultiplier
     * can't increase the RPC timeout higher than this amount. The default value is {@code
     * Duration.ZERO}.
     */
    abstract Builder setMaxRpcTimeoutDuration(java.time.Duration maxTimeout);

    /** Backport of {@link #getTotalTimeoutDuration()} */
    public final org.threeten.bp.Duration getTotalTimeout() {
      return org.threeten.bp.Duration.ofNanos(getTotalTimeoutDuration().toNanos());
    }

    /**
     * TotalTimeout has ultimate control over how long the logic should keep trying the remote call
     * until it gives up completely. The higher the total timeout, the more retries can be
     * attempted. The default value is {@code Duration.ZERO}.
     */
    public abstract java.time.Duration getTotalTimeoutDuration();

    /** Backport of {@link #getInitialRetryDelay()} */
    public final org.threeten.bp.Duration getInitialRetryDelay() {
      return org.threeten.bp.Duration.ofNanos(getInitialRetryDelayDuration().toNanos());
    }

    /**
     * InitialRetryDelay controls the delay before the first retry. Subsequent retries will use this
     * value adjusted according to the RetryDelayMultiplier. The default value is {@code
     * Duration.ZERO}.
     */
    public abstract java.time.Duration getInitialRetryDelayDuration();

    /**
     * RetryDelayMultiplier controls the change in retry delay. The retry delay of the previous call
     * is multiplied by the RetryDelayMultiplier to calculate the retry delay for the next call. The
     * default value is {@code 1.0}.
     */
    public abstract double getRetryDelayMultiplier();

    /**
     * MaxAttempts defines the maximum number of attempts to perform. The default value is {@code
     * 0}. If this value is greater than 0, and the number of attempts reaches this limit, the logic
     * will give up retrying even if the total retry time is still lower than TotalTimeout.
     */
    public abstract int getMaxAttempts();

    /**
     * Jitter determines if the delay time should be randomized. In most cases, if jitter is set to
     * {@code true} the actual delay time is calculated in the following way:
     *
     * <pre>{@code actualDelay = rand_between(0, min(maxRetryDelay, exponentialDelay))}</pre>
     *
     * The default value is {@code true}.
     */
    public abstract boolean isJittered();

    /** Backport of {@link #getMaxRetryDelayDuration()} */
    public final org.threeten.bp.Duration getMaxRetryDelay() {
      return org.threeten.bp.Duration.ofNanos(getMaxRetryDelayDuration().toNanos());
    }

    /**
     * MaxRetryDelay puts a limit on the value of the retry delay, so that the RetryDelayMultiplier
     * can't increase the retry delay higher than this amount. The default value is {@code
     * Duration.ZERO}.
     */
    public abstract java.time.Duration getMaxRetryDelayDuration();

    /** Backport of {@link #getInitialRpcTimeoutDuration()} */
    public final org.threeten.bp.Duration getInitialRpcTimeout() {
      return org.threeten.bp.Duration.ofNanos((getInitialRpcTimeoutDuration().toNanos()));
    }

    /**
     * InitialRpcTimeout controls the timeout for the initial RPC. Subsequent calls will use this
     * value adjusted according to the RpcTimeoutMultiplier. The default value is {@code
     * Duration.ZERO}.
     */
    public abstract java.time.Duration getInitialRpcTimeoutDuration();

    /**
     * See the class documentation of {@link RetrySettings} for a description of what this value
     * does. The default value is {@code 1.0}.
     */
    public abstract double getRpcTimeoutMultiplier();

    /** Backport of {@link #getMaxRpcTimeoutDuration()} */
    public final org.threeten.bp.Duration getMaxRpcTimeout() {
      return org.threeten.bp.Duration.ofNanos((getMaxRpcTimeoutDuration().toNanos()));
    }

    /**
     * MaxRpcTimeout puts a limit on the value of the RPC timeout, so that the RpcTimeoutMultiplier
     * can't increase the RPC timeout higher than this amount.
     */
    public abstract java.time.Duration getMaxRpcTimeoutDuration();

    /**
     * Overload of {@link #setLogicalTimeout(java.time.Duration)} using {@link
     * org.threeten.bp.Duration}
     */
    @BetaApi
    public Builder setLogicalTimeout(org.threeten.bp.Duration timeout) {
      return setLogicalTimeout(java.time.Duration.ofNanos(timeout.toNanos()));
    }

    /**
     * Configures the timeout settings with the given timeout such that the logical call will take
     * no longer than the given timeout and each RPC attempt will use only the time remaining in the
     * logical call as a timeout.
     *
     * <p>Using this method in conjunction with individual {@link RetrySettings} timeout field
     * setters is not advised, because only the order in which they are invoked determines which
     * setter is respected.
     */
    @BetaApi
    public Builder setLogicalTimeout(java.time.Duration timeout) {
      return setRpcTimeoutMultiplier(1)
          .setInitialRpcTimeout(timeout)
          .setMaxRpcTimeout(timeout)
          .setTotalTimeout(timeout);
    }

    abstract RetrySettings autoBuild();

    public RetrySettings build() {
      RetrySettings params = autoBuild();
      if (params.getTotalTimeout().toMillis() < 0) {
        throw new IllegalStateException("total timeout must not be negative");
      }
      if (params.getInitialRetryDelay().toMillis() < 0) {
        throw new IllegalStateException("initial retry delay must not be negative");
      }
      if (params.getRetryDelayMultiplier() < 1.0) {
        throw new IllegalStateException("retry delay multiplier must be at least 1");
      }
      if (params.getMaxRetryDelay().compareTo(params.getInitialRetryDelay()) < 0) {
        throw new IllegalStateException("max retry delay must not be shorter than initial delay");
      }
      if (params.getMaxAttempts() < 0) {
        throw new IllegalStateException("max attempts must be non-negative");
      }
      if (params.getInitialRpcTimeout().toMillis() < 0) {
        throw new IllegalStateException("initial rpc timeout must not be negative");
      }
      if (params.getMaxRpcTimeout().compareTo(params.getInitialRpcTimeout()) < 0) {
        throw new IllegalStateException("max rpc timeout must not be shorter than initial timeout");
      }
      if (params.getRpcTimeoutMultiplier() < 1.0) {
        throw new IllegalStateException("rpc timeout multiplier must be at least 1");
      }
      return params;
    }

    public RetrySettings.Builder merge(RetrySettings.Builder newSettings) {
      if (newSettings.getTotalTimeout() != null) {
        setTotalTimeout(newSettings.getTotalTimeout());
      }
      if (newSettings.getInitialRetryDelay() != null) {
        setInitialRetryDelay(newSettings.getInitialRetryDelay());
      }
      if (newSettings.getRetryDelayMultiplier() >= 1) {
        setRetryDelayMultiplier(newSettings.getRetryDelayMultiplier());
      }
      if (newSettings.getMaxRetryDelay() != null) {
        setMaxRetryDelay(newSettings.getMaxRetryDelay());
      }
      setMaxAttempts(newSettings.getMaxAttempts());
      setJittered(newSettings.isJittered());
      if (newSettings.getInitialRpcTimeout() != null) {
        setInitialRpcTimeout(newSettings.getInitialRpcTimeout());
      }
      if (newSettings.getRpcTimeoutMultiplier() >= 1) {
        setRpcTimeoutMultiplier(newSettings.getRpcTimeoutMultiplier());
      }
      if (newSettings.getMaxRpcTimeout() != null) {
        setMaxRpcTimeout(newSettings.getMaxRpcTimeout());
      }
      return this;
    }
  }
}
