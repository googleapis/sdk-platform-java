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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class JsonContextMapHandler extends Handler {

  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @Override
  public void publish(LogRecord record) {
    Object[] params = record.getParameters();

    ConsoleHandler consoleHandler = new ConsoleHandler();
    if (params != null && params.length > 0 && params[0] instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<String, String> contextMap = (Map<String, String>) params[0];

      // Create a map to hold all log data
      Map<String, Object> logData = new HashMap<>();
      logData.put("message", record.getMessage());
      logData.put("severity", record.getLevel().getName());
      logData.put("timestamp", record.getMillis());
      logData.put("logger", record.getLoggerName());

      // Add all context data to the top level
      logData.putAll(contextMap);

      // Convert to JSON
      String jsonLog = gson.toJson(logData);

      LogRecord jsonRecord = new LogRecord(record.getLevel(), jsonLog);
      consoleHandler.publish(jsonRecord);
    } else {
      // Handle cases where the context map is not present
      LogRecord messageRecord =
          new LogRecord(record.getLevel(), "Log message without context: " + record.getMessage());
      consoleHandler.publish(messageRecord);
    }
  }

  @Override
  public void flush() {}

  @Override
  public void close() throws SecurityException {}
}
