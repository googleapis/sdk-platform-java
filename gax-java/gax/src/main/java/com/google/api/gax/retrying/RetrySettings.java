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
import org.threeten.bp.Duration;

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
 * com.google.api.gax.rpc.ApiCallContext#withStreamWaitTimeout(Duration)}.
 *
 * <p>In Cloud Client Libraries, Retry and LRO Retry Settings may be configured for each RPC in a
 * service. These values are chosen by the service teams and may be found by looking at the
 * {Service}StubSettings.java file in each library. The default values listed below for each
 * configuration are the default values for the RetrySettings class if there are no RPC specific
 * configurations from the Service Team.
 */
@AutoValue
public abstract class RetrySettings implements Serializable {

  private static final long serialVersionUID = 8258475264439710899L;

  /**
   * TotalTimeout has ultimate control over how long the logic should keep trying the remote call
   * until it gives up completely. The higher the total timeout, the more retries and polls can be
   * attempted. If this value is {@code Duration.ZERO}, then the logic will instead use the number
   * of attempts to determine retries. In the event that both maxAttempts and totalTimeout values
   * are both 0, the logic will not retry. If this value is non-{@code Duration.ZERO}, and the retry
   * duration has reaches the timeout value, the logic will give up retrying even the number of
   * attempts is lower than the maxAttempts value.
   *
   * <p>If there are no configurations, Retries have the default timeout value of {@code
   * Duration.ZERO} and LROs have a default total timeout value of {@code Duration.ofMillis(300000)}
   * (5 minutes).
   */
  public abstract Duration getTotalTimeout();

  /**
   * InitialRetryDelay controls the delay before the first retry/ poll. Subsequent retries and polls
   * will use this value adjusted according to the RetryDelayMultiplier.
   *
   * <p>If there are no configurations, Retries have the default initial retry delay value of {@code
   * Duration.ZERO} and LROs have a default initial poll delay value of {@code
   * Duration.ofMillis(5000)} (5 seconds).
   */
  public abstract Duration getInitialRetryDelay();

  /**
   * RetryDelayMultiplier controls the change in delay before the next retry or poll. The retry
   * delay of the previous call is multiplied by the RetryDelayMultiplier to calculate the retry
   * delay for the next call.
   *
   * <p>If there are no configurations, Retries have the default retry delay multiplier value of
   * {@code 1.0} and LROs have a default retry delay multiplier of {@code 1.5}.
   */
  public abstract double getRetryDelayMultiplier();

  /**
   * MaxRetryDelay puts a limit on the value of the retry delay, so that the RetryDelayMultiplier
   * can't increase the retry delay higher than this amount.
   *
   * <p>If there are no configurations, Retries have the default max retry delay value of {@code
   * Duration.ZERO} and LROs have a default max poll retry delay value of {@code
   * Duration.ofMillis(45000)} (45 seconds).
   */
  public abstract Duration getMaxRetryDelay();

  /**
   * MaxAttempts defines the maximum number of retry attempts to perform. If this value is set to 0,
   * the logic will instead use the totalTimeout value to determine retries. In the event that both
   * the maxAttempts and totalTimeout values are both 0, the logic will not retry. If this value is
   * greater than 0, and the number of attempts exceeds this limit, the logic will give up retrying
   * even if the total retry time is still lower than totalTimeout.
   *
   * <p>If there are no configurations, Retries and LROs have the default max attempt value of
   * {@code 0}. LRO polling does not use this value by default.
   *
   * <p>The first RPC invocation will be considered attempt #0. Subsequent calls (retries) will
   * increment the number of attempts and the number of attempts will not exceed this value.
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

  /**
   * InitialRpcTimeout controls the timeout for the initial RPC. Subsequent calls will use this
   * value adjusted according to the RpcTimeoutMultiplier. RPC Timeout value of {@code
   * Duration.ZERO} allows the RPC to continue indefinitely (until it hits a Connect Timeout or the
   * connection has been terminated).
   *
   * <p>{@link #getTotalTimeout()} caps how long the logic should keep trying the RPC until it gives
   * up completely. If {@link #getTotalTimeout()} is set, initialRpcTimeout should be <=
   * totalTimeout.
   *
   * <p>If there are no configurations, Retries have the default initial RPC timeout value of {@code
   * Duration.ZERO}. LRO polling does not use the Initial RPC Timeout value.
   */
  public abstract Duration getInitialRpcTimeout();

  /**
   * RpcTimeoutMultiplier controls the change in RPC timeout. The timeout of the previous call is
   * multiplied by the RpcTimeoutMultiplier to calculate the timeout for the next call.
   *
   * <p>If there are no configurations, Retries have the default RPC Timeout Multiplier value of
   * {@code 1.0}. LRO polling does not use the RPC Timeout Multiplier value.
   */
  public abstract double getRpcTimeoutMultiplier();

  /**
   * MaxRpcTimeout puts a limit on the value of the RPC timeout, so that the RpcTimeoutMultiplier
   * can't increase the RPC timeout higher than this amount.
   *
   * <p>If there are no configurations, Retries have the default Max RPC Timeout value of {@code
   * Duration.ZERO}. LRO polling does not use the Max RPC Timeout value.
   */
  public abstract Duration getMaxRpcTimeout();

  public static Builder newBuilder() {
    return new AutoValue_RetrySettings.Builder()
        .setTotalTimeout(Duration.ZERO)
        .setInitialRetryDelay(Duration.ZERO)
        .setRetryDelayMultiplier(1.0)
        .setMaxRetryDelay(Duration.ZERO)
        .setMaxAttempts(0)
        .setJittered(true)
        .setInitialRpcTimeout(Duration.ZERO)
        .setRpcTimeoutMultiplier(1.0)
        .setMaxRpcTimeout(Duration.ZERO);
  }

  public abstract Builder toBuilder();

  /**
   * A base builder class for {@link RetrySettings}. See the class documentation of {@link
   * RetrySettings} for a description of the different values that can be set.
   */
  @AutoValue.Builder
  public abstract static class Builder {

    /**
     * TotalTimeout has ultimate control over how long the logic should keep trying the remote call
     * until it gives up completely. The higher the total timeout, the more retries and polls can be
     * attempted. If this value is {@code Duration.ZERO}, then the logic will instead use the number
     * of attempts to determine retries. In the event that both maxAttempts and totalTimeout values
     * are both 0, the logic will not retry. If this value is non-{@code Duration.ZERO}, and the
     * retry duration has reaches the timeout value, the logic will give up retrying even the number
     * of attempts is lower than the maxAttempts value.
     *
     * <p>If there are no configurations, Retries have the default timeout value of {@code
     * Duration.ZERO} and LROs have a default total timeout value of {@code
     * Duration.ofMillis(300000)} (5 minutes).
     */
    public abstract Builder setTotalTimeout(Duration totalTimeout);

    /**
     * InitialRetryDelay controls the delay before the first retry/ poll. Subsequent retries and
     * polls will use this value adjusted according to the RetryDelayMultiplier.
     *
     * <p>If there are no configurations, Retries have the default initial retry delay value of
     * {@code Duration.ZERO} and LROs have a default initial poll delay value of {@code
     * Duration.ofMillis(5000)} (5 seconds).
     */
    public abstract Builder setInitialRetryDelay(Duration initialDelay);

    /**
     * RetryDelayMultiplier controls the change in delay before the next retry or poll. The retry
     * delay of the previous call is multiplied by the RetryDelayMultiplier to calculate the retry
     * delay for the next call.
     *
     * <p>If there are no configurations, Retries have the default retry delay multiplier value of
     * {@code 1.0} and LROs have a default retry delay multiplier of {@code 1.5}.
     */
    public abstract Builder setRetryDelayMultiplier(double multiplier);

    /**
     * MaxRetryDelay puts a limit on the value of the retry delay, so that the RetryDelayMultiplier
     * can't increase the retry delay higher than this amount.
     *
     * <p>If there are no configurations, Retries have the default max retry delay value of {@code
     * Duration.ZERO} and LROs have a default max poll retry delay value of {@code
     * Duration.ofMillis(45000)} (45 seconds).
     */
    public abstract Builder setMaxRetryDelay(Duration maxDelay);

    /**
     * MaxAttempts defines the maximum number of retry attempts to perform. If this value is set to
     * 0, the logic will instead use the totalTimeout value to determine retries. In the event that
     * both the maxAttempts and totalTimeout values are both 0, the logic will not retry. If this
     * value is greater than 0, and the number of attempts exceeds this limit, the logic will give
     * up retrying even if the total retry time is still lower than totalTimeout.
     *
     * <p>If there are no configurations, Retries and LROs have the default max attempt value of
     * {@code 0}. LRO polling does not use this value by default.
     *
     * <p>The first RPC invocation will be considered attempt #0. Subsequent calls (retries) will
     * increment the number of attempts and the number of attempts will not exceed this value.
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
     * InitialRpcTimeout controls the timeout for the initial RPC. Subsequent calls will use this
     * value adjusted according to the RpcTimeoutMultiplier. RPC Timeout value of {@code
     * Duration.ZERO} allows the RPC to continue indefinitely (until it hits a Connect Timeout or
     * the connection has been terminated).
     *
     * <p>{@link #getTotalTimeout()} caps how long the logic should keep trying the RPC until it
     * gives up completely. If {@link #getTotalTimeout()} is set, initialRpcTimeout should be <=
     * totalTimeout.
     *
     * <p>If there are no configurations, Retries have the default initial RPC timeout value of
     * {@code Duration.ZERO}. LRO polling does not use the Initial RPC Timeout value.
     */
    public abstract Builder setInitialRpcTimeout(Duration initialTimeout);

    /**
     * RpcTimeoutMultiplier controls the change in RPC timeout. The timeout of the previous call is
     * multiplied by the RpcTimeoutMultiplier to calculate the timeout for the next call.
     *
     * <p>If there are no configurations, Retries have the default RPC Timeout Multiplier value of
     * {@code 1.0}. LRO polling does not use the RPC Timeout Multiplier value.
     */
    public abstract Builder setRpcTimeoutMultiplier(double multiplier);

    /**
     * MaxRpcTimeout puts a limit on the value of the RPC timeout, so that the RpcTimeoutMultiplier
     * can't increase the RPC timeout higher than this amount.
     *
     * <p>If there are no configurations, Retries have the default Max RPC Timeout value of {@code
     * Duration.ZERO}. LRO polling does not use the Max RPC Timeout value.
     */
    public abstract Builder setMaxRpcTimeout(Duration maxTimeout);

    /**
     * TotalTimeout has ultimate control over how long the logic should keep trying the remote call
     * until it gives up completely. The higher the total timeout, the more retries and polls can be
     * attempted. If this value is {@code Duration.ZERO}, then the logic will instead use the number
     * of attempts to determine retries. In the event that both maxAttempts and totalTimeout values
     * are both 0, the logic will not retry. If this value is non-{@code Duration.ZERO}, and the
     * retry duration has reaches the timeout value, the logic will give up retrying even the number
     * of attempts is lower than the maxAttempts value.
     *
     * <p>If there are no configurations, Retries have the default timeout value of {@code
     * Duration.ZERO} and LROs have a default total timeout value of {@code
     * Duration.ofMillis(300000)} (5 minutes).
     */
    public abstract Duration getTotalTimeout();

    /**
     * InitialRetryDelay controls the delay before the first retry/ poll. Subsequent retries and
     * polls will use this value adjusted according to the RetryDelayMultiplier.
     *
     * <p>If there are no configurations, Retries have the default initial retry delay value of
     * {@code Duration.ZERO} and LROs have a default initial poll delay value of {@code
     * Duration.ofMillis(5000)} (5 seconds).
     */
    public abstract Duration getInitialRetryDelay();

    /**
     * RetryDelayMultiplier controls the change in delay before the next retry or poll. The retry
     * delay of the previous call is multiplied by the RetryDelayMultiplier to calculate the retry
     * delay for the next call.
     *
     * <p>If there are no configurations, Retries have the default retry delay multiplier value of
     * {@code 1.0} and LROs have a default retry delay multiplier of {@code 1.5}.
     */
    public abstract double getRetryDelayMultiplier();

    /**
     * MaxAttempts defines the maximum number of retry attempts to perform. If this value is set to
     * 0, the logic will instead use the totalTimeout value to determine retries. In the event that
     * both the maxAttempts and totalTimeout values are both 0, the logic will not retry. If this
     * value is greater than 0, and the number of attempts exceeds this limit, the logic will give
     * up retrying even if the total retry time is still lower than totalTimeout.
     *
     * <p>If there are no configurations, Retries and LROs have the default max attempt value of
     * {@code 0}. LRO polling does not use this value by default.
     *
     * <p>The first RPC invocation will be considered attempt #0. Subsequent calls (retries) will
     * increment the number of attempts and the number of attempts will not exceed this value.
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

    /**
     * MaxRetryDelay puts a limit on the value of the retry delay, so that the RetryDelayMultiplier
     * can't increase the retry delay higher than this amount.
     *
     * <p>If there are no configurations, Retries have the default max retry delay value of {@code
     * Duration.ZERO} and LROs have a default max poll retry delay value of {@code
     * Duration.ofMillis(45000)} (45 seconds).
     */
    public abstract Duration getMaxRetryDelay();

    /**
     * InitialRpcTimeout controls the timeout for the initial RPC. Subsequent calls will use this
     * value adjusted according to the RpcTimeoutMultiplier. RPC Timeout value of {@code
     * Duration.ZERO} allows the RPC to continue indefinitely (until it hits a Connect Timeout or
     * the connection has been terminated).
     *
     * <p>{@link #getTotalTimeout()} caps how long the logic should keep trying the RPC until it
     * gives up completely. If {@link #getTotalTimeout()} is set, initialRpcTimeout should be <=
     * totalTimeout.
     *
     * <p>If there are no configurations, Retries have the default initial RPC timeout value of
     * {@code Duration.ZERO}. LRO polling does not use the Initial RPC Timeout value.
     */
    public abstract Duration getInitialRpcTimeout();

    /**
     * RpcTimeoutMultiplier controls the change in RPC timeout. The timeout of the previous call is
     * multiplied by the RpcTimeoutMultiplier to calculate the timeout for the next call.
     *
     * <p>If there are no configurations, Retries have the default RPC Timeout Multiplier value of
     * {@code 1.0}. LRO polling does not use the RPC Timeout Multiplier value.
     */
    public abstract double getRpcTimeoutMultiplier();

    /**
     * MaxRpcTimeout puts a limit on the value of the RPC timeout, so that the RpcTimeoutMultiplier
     * can't increase the RPC timeout higher than this amount.
     *
     * <p>If there are no configurations, Retries have the default Max RPC Timeout value of {@code
     * Duration.ZERO}. LRO polling does not use the Max RPC Timeout value.
     */
    public abstract Duration getMaxRpcTimeout();

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
    public Builder setLogicalTimeout(Duration timeout) {
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
