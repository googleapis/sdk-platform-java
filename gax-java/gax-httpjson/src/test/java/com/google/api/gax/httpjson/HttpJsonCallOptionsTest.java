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
package com.google.api.gax.httpjson;

import static com.google.api.gax.util.TimeConversionTestUtils.testDurationMethod;
import static com.google.api.gax.util.TimeConversionTestUtils.testInstantMethod;

import org.junit.jupiter.api.Test;

public class HttpJsonCallOptionsTest {
  private final HttpJsonCallOptions.Builder OPTIONS_BUILDER = HttpJsonCallOptions.newBuilder();

  @Test
  public void testDeadline() {
    final long millis = 3;
    testInstantMethod(
        millis,
        jt -> OPTIONS_BUILDER.setDeadlineInstant(jt),
        tt -> OPTIONS_BUILDER.setDeadline(tt),
        c -> c.build().getDeadlineInstant(),
        c -> c.build().getDeadline());
  }

  @Test
  public void testTimeout() {
    final long millis = 3;
    testDurationMethod(
        millis,
        jt -> OPTIONS_BUILDER.setTimeoutDuration(jt),
        tt -> OPTIONS_BUILDER.setTimeout(tt),
        c -> c.build().getTimeoutDuration(),
        c -> c.build().getTimeout());
  }
}
