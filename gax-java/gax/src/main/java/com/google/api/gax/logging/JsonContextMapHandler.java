package com.google.api.gax.logging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class JsonContextMapHandler extends Handler {

  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @Override
  public void publish(LogRecord record) {
    Object[] params = record.getParameters();
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
      System.out.println(jsonLog);
    } else {
      // Handle cases where the context map is not present
      System.out.println("Log message without context: " + record.getMessage());
    }
  }

  @Override
  public void flush() {}

  @Override
  public void close() throws SecurityException {}
}
