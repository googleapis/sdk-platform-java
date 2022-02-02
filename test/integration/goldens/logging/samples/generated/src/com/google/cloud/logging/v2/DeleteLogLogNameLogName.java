package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.LogName;
import com.google.protobuf.Empty;

public class DeleteLogLogNameLogName {

  public static void main(String[] args) throws Exception {
    deleteLogLogNameLogName();
  }

  public static void deleteLogLogNameLogName() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      LogName logName = LogName.ofProjectLogName("[PROJECT]", "[LOG]");
      loggingClient.deleteLog(logName);
    }
  }
}
