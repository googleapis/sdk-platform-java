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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

@Plugin(
    name = "SDKLoggingJsonLayout",
    category = Node.CATEGORY,
    elementType = Layout.ELEMENT_TYPE,
    printObject = true)
public class SDKLoggingJsonLayout extends AbstractStringLayout {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private static final String EMPTY_STRING = "";
  private static final String TIME_STAMP = "timestamp";
  private static final String LEVEL = "level";
  private static final String LOGGER_NAME = "logger_name";
  private static final String MESSAGE = "message";

  protected SDKLoggingJsonLayout(Charset charset) {
    super(charset);
  }

  @Override
  public String toSerializable(LogEvent event) {
    Map<String, Object> jsonMap = new HashMap<>();
    extractNonMdc(event, jsonMap);

    Map<String, String> mdcMap = event.getContextData().toMap();
    for (Map.Entry<String, String> entry : mdcMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (key == null || value == null) {
        continue;
      }

      try {
        jsonMap.put(key, convertToTreeNode(value));
      } catch (JsonProcessingException e) {
        // in case of conversion exception, just use String
        jsonMap.put(key, value);
      }
    }

    try {
      return objectMapper.writeValueAsString(jsonMap);
    } catch (JsonProcessingException e) {
      return EMPTY_STRING;
    }
  }

  private void extractNonMdc(LogEvent event, Map<String, Object> jsonMap) {
    jsonMap.put(TIME_STAMP, event.getTimeMillis());
    jsonMap.put(LEVEL, event.getLevel().toString());
    jsonMap.put(LOGGER_NAME, event.getLoggerName());
    jsonMap.put(MESSAGE, event.getMessage().getFormattedMessage());
  }

  private JsonNode convertToTreeNode(String jsonString) throws JsonProcessingException {
    return objectMapper.readTree(jsonString);
  }
}
