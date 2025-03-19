/*
 * Copyright 2025 Google LLC
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

package com.google.cloud.sdk.logging;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SDKLoggingJsonLayoutTest {
  private final SDKLoggingJsonLayout sdkLoggingJsonLayout =
      SDKLoggingJsonLayout.newBuilder().build();
  private final LogEvent logEvent = mock(LogEvent.class);
  private final ReadOnlyStringMap map = mock(ReadOnlyStringMap.class);
  private final Map<String, String> mdcMap = new HashMap<>();

  @BeforeEach
  void init() {
    when(logEvent.getTimeMillis()).thenReturn(10000L);
    when(logEvent.getLevel()).thenReturn(Level.DEBUG);
    when(logEvent.getLoggerName()).thenReturn("com.example.Example");
    when(logEvent.getMessage()).thenReturn(new SimpleMessage("example message"));
    when(logEvent.getContextData()).thenReturn(map);
    when(map.toMap()).thenReturn(mdcMap);
  }

  @Test
  void testToSerializableContainsNonMdcContents() {
    assertEquals(
        "{\"timestamp\":10000,\"level\":\"DEBUG\",\"logger_name\":\"com.example.Example\",\"message\":\"example message\"}\n",
        sdkLoggingJsonLayout.toSerializable(logEvent));
  }

  @Test
  void testToSerializableSkipNullKey() {
    mdcMap.put(null, "example value");
    String logString = sdkLoggingJsonLayout.toSerializable(logEvent);
    assertFalse(logString.contains("example value"));
  }

  @Test
  void testToSerializableSkipNullValue() {
    mdcMap.put("example key", null);
    String logString = sdkLoggingJsonLayout.toSerializable(logEvent);
    assertFalse(logString.contains("example key"));
  }

  @Test
  void testToSerializableInvalidJsonValueWriteString() {
    // the last colon is invalid.
    mdcMap.put("example key", "{key:value,jsonKey:{nestedKey:nestedValue,}}");
    assertEquals(
        "{\"timestamp\":10000,\"level\":\"DEBUG\",\"logger_name\":\"com.example.Example\",\"message\":\"example message\",\"example key\":\"{key:value,jsonKey:{nestedKey:nestedValue,}}\"}\n",
        sdkLoggingJsonLayout.toSerializable(logEvent));
  }

  @Test
  void testToSerializableValidJsonValueWriteJson() {
    // the last colon is invalid.
    mdcMap.put("example key", "{key:value,jsonKey:{nestedKey:nestedValue}}");
    String log = sdkLoggingJsonLayout.toSerializable(logEvent);
    assertEquals(
        "{\"timestamp\":10000,\"level\":\"DEBUG\",\"logger_name\":\"com.example.Example\",\"message\":\"example message\",\"example key\":{\"key\":\"value\",\"jsonKey\":{\"nestedKey\":\"nestedValue\"}}}\n",
        log);
    assertDoesNotThrow(() -> JsonParser.parseString(log));
  }
}
