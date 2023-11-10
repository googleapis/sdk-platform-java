package com.google.api.gax.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;

public class FakeLogHandler extends Handler {
  List<LogRecord> records = new ArrayList<>();

  @Override
  public void publish(LogRecord record) {
    records.add(record);
  }

  @Override
  public void flush() {}

  @Override
  public void close() throws SecurityException {}

  public List<String> getAllMessages() {
    return records.stream().map(LogRecord::getMessage).collect(Collectors.toList());
  }
}
