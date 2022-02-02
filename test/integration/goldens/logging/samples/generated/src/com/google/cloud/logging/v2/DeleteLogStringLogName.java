package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.LogName;
import com.google.protobuf.Empty;

public class DeleteLogStringLogName {

  public static void main(String[] args) throws Exception {
    deleteLogStringLogName();
  }

  public static void deleteLogStringLogName() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      String logName = LogName.ofProjectLogName("[PROJECT]", "[LOG]").toString();
      loggingClient.deleteLog(logName);
    }
  }
}
