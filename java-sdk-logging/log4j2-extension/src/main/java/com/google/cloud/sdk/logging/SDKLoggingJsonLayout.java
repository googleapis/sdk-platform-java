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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

@Plugin(
    name = "SDKLoggingJsonLayout",
    category = Node.CATEGORY,
    elementType = Layout.ELEMENT_TYPE,
    printObject = true)
public final class SDKLoggingJsonLayout extends AbstractStringLayout {

  private final Gson gson = new Gson();
  private static final String TIME_STAMP = "timestamp";
  private static final String LEVEL = "level";
  private static final String LOGGER_NAME = "logger_name";
  private static final String THREAD_NAME = "thread_name";
  private static final String THREAD_ID = "thread_ID";
  private static final String MESSAGE = "message";

  private SDKLoggingJsonLayout(final Builder builder) {
    super(builder.getCharset());
  }

  @Override
  public String toSerializable(LogEvent event) {
    // use LinkedHashMap to fix iteration order.
    Map<String, Object> jsonMap = new LinkedHashMap<>();
    extractNonMdc(event, jsonMap);

    Map<String, String> mdcMap = new LinkedHashMap<>(event.getContextData().toMap());
    for (Map.Entry<String, String> entry : mdcMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (key == null || value == null) {
        continue;
      }

      try {
        jsonMap.put(key, toJsonElement(value));
      } catch (JsonParseException e) {
        // in case of conversion exception, just use String
        jsonMap.put(key, value);
      }
    }

    return String.format("%s\n", gson.toJson(jsonMap));
  }

  private void extractNonMdc(LogEvent event, Map<String, Object> jsonMap) {
    jsonMap.put(TIME_STAMP, event.getTimeMillis());
    jsonMap.put(LEVEL, event.getLevel().toString());
    jsonMap.put(LOGGER_NAME, event.getLoggerName());
    jsonMap.put(THREAD_NAME, event.getThreadName());
    jsonMap.put(THREAD_ID, event.getThreadId());
    jsonMap.put(MESSAGE, event.getMessage().getFormattedMessage());
  }

  private JsonElement toJsonElement(String jsonString) throws JsonParseException {
    return JsonParser.parseString(jsonString);
  }

  @PluginBuilderFactory
  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder
      implements org.apache.logging.log4j.core.util.Builder<SDKLoggingJsonLayout> {

    @PluginBuilderAttribute private Charset charset = StandardCharsets.UTF_8;

    public Charset getCharset() {
      return charset;
    }

    public Builder setCharset(final Charset charset) {
      this.charset = charset;
      return this;
    }

    @Override
    public SDKLoggingJsonLayout build() {
      return new SDKLoggingJsonLayout(this);
    }
  }
}
