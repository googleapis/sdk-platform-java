/*
 * Copyright 2024 Google LLC
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BatchingSettingsTest {

  private static final BatchingSettings.Builder SETTINGS_BUILDER = BatchingSettings.newBuilder();

  @Test
  public void testDelayThreshold() {
    final long millis = 123;
    final long millis2 = 345;
    java.time.Duration jtd1 = java.time.Duration.ofMillis(millis);
    org.threeten.bp.Duration ttd1 = org.threeten.bp.Duration.ofMillis(millis);
    java.time.Duration jtd2 = java.time.Duration.ofMillis(millis2);
    org.threeten.bp.Duration ttd2 = org.threeten.bp.Duration.ofMillis(millis2);
    BatchingSettings jtSettings = SETTINGS_BUILDER.setDelayThresholdDuration(jtd1).build();
    BatchingSettings ttSettings = SETTINGS_BUILDER.setDelayThreshold(ttd2).build();

    assertEquals(jtd1, jtSettings.getDelayThresholdDuration());
    assertEquals(ttd1, jtSettings.getDelayThreshold());
    assertEquals(jtd2, ttSettings.getDelayThresholdDuration());
    assertEquals(ttd2, ttSettings.getDelayThreshold());

    //    testDurationMethod(
    //        123l,
    //        jt -> SETTINGS_BUILDER.setDelayThresholdDuration(jt).build(),
    //        tt -> SETTINGS_BUILDER.setDelayThreshold(tt).build(),
    //        o -> o.getDelayThresholdDuration(),
    //        o -> o.getDelayThreshold());
  }
}
