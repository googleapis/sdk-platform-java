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

package com.google.api.gax.logging;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonParser;
import java.util.Map;
import org.junit.jupiter.api.Test;

class LogDataTest {

  @Test
  void toMapResponse_correctlyConvertsData() {
    LogData logData =
        LogData.builder()
            .serviceName("MyService")
            .rpcName("myMethod")
            .requestHeaders("fake header")
            .requestId("abcd")
            .responsePayload(JsonParser.parseString("{\"key\": \"value\"}"))
            .build();

    Map<String, String> expectedMap =
        ImmutableMap.of(
            "serviceName", "MyService",
            "rpcName", "myMethod",
            "response.payload", "{\"key\":\"value\"}",
            "requestId", "abcd");

    assertThat(logData.toMapResponse()).isEqualTo(expectedMap);
  }

  @Test
  void toMapRequest_correctlyConvertsData() {
    LogData logData =
        LogData.builder()
            .serviceName("MyService")
            .rpcName("myMethod")
            .requestHeaders("fake header")
            .requestId("abcd")
            .httpUrl("url")
            .responsePayload(JsonParser.parseString("{\"key\": \"value\"}"))
            .build();

    Map<String, String> expectedMap =
        ImmutableMap.of(
            "serviceName", "MyService",
            "rpcName", "myMethod",
            "request.headers", "fake header",
            "requestId", "abcd",
            "request.url", "url");

    assertThat(logData.toMapRequest()).isEqualTo(expectedMap);
  }
}
