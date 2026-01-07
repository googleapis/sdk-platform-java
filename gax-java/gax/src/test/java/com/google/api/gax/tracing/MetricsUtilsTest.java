/*
 * Copyright 2026 Google LLC
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

package com.google.api.gax.tracing;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class MetricsUtilsTest {

  @Test
  void testIsMetricsEnabled_defaultToFalse() {
    // Verifies the default behavior when the environment variable is not set (null).
    assertThat(MetricsUtils.isMetricsEnabled(null)).isFalse();
  }

  @Test
  void testIsMetricsEnabled_true() {
    assertThat(MetricsUtils.isMetricsEnabled("true")).isTrue();
    assertThat(MetricsUtils.isMetricsEnabled("TRUE")).isTrue();
  }

  @Test
  void testIsMetricsEnabled_false() {
    assertThat(MetricsUtils.isMetricsEnabled("false")).isFalse();
    assertThat(MetricsUtils.isMetricsEnabled("random")).isFalse();
    assertThat(MetricsUtils.isMetricsEnabled("")).isFalse();
  }
}
