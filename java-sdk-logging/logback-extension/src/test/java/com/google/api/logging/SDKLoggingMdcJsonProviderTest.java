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

package com.google.api.logging;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SDKLoggingMdcJsonProviderTest {

  private SDKLoggingMdcJsonProvider provider = new SDKLoggingMdcJsonProvider();
  private JsonGenerator generator = mock(JsonGenerator.class);
  private ILoggingEvent event = mock(ILoggingEvent.class);
  private Map<String, String> mdc;

  @BeforeEach
  void init() {
    mdc = new HashMap<>();
    when(event.getMDCPropertyMap()).thenReturn(mdc);
  }

  @AfterEach
  void post() {
    mdc.clear();
  }

  @Test
  void testWriteNullMdcMap() throws IOException {
    when(event.getMDCPropertyMap()).thenReturn(null);
    provider.writeTo(generator, event);
    verify(generator, never()).writeFieldName(anyString());
    verify(generator, never()).writeTree(any(JsonNode.class));
  }

  @Test
  void testWriteEmptyMdcMap() throws IOException {
    provider.writeTo(generator, event);
    verify(generator, never()).writeFieldName(anyString());
    verify(generator, never()).writeTree(any(JsonNode.class));
  }

  @Test
  void testWriteValidJsonStringToJsonTree() throws IOException {
    mdc.put(
        "json1",
        "{\n"
            + "  \"@version\": \"1\",\n"
            + "  \"textPayload\": \"Received response\",\n"
            + "  \"response.payload\": {\n"
            + "    \"name\": \"example\",\n"
            + "    \"state\": \"ACTIVE\"\n"
            + "  }\n"
            + "}");

    provider.writeTo(generator, event);
    verify(generator).writeFieldName("json1");
    verify(generator).writeTree(any(JsonNode.class));
  }

  @Test
  void testWriteInvalidJsonStringToString() throws IOException {
    mdc.put(
        "json1",
        "{\n"
            + "  \"@version\": \"1\",\n"
            + "  \"textPayload\": \"Received response\",\n"
            + "  \"response.payload\": {\n"
            + "    \"name\": \"example\",\n"
            + "    \"state\": \"ACTIVE\",\n" // the last semicolon is redundant.
            + "  }\n"
            + "}");

    provider.writeTo(generator, event);
    verify(generator).writeFieldName("json1");
    verify(generator).writeObject(anyString());
    // should not write tree node because the json string is invalid.
    verify(generator, never()).writeTree(any(JsonNode.class));
  }

  @Test
  void testWriteNullThrowsException() {
    mdc.put("json1", null);

    assertThrows(IllegalArgumentException.class, () -> provider.writeTo(generator, event));
  }
}
