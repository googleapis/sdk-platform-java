package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.DeleteLogRequest;
import com.google.logging.v2.LogName;
import com.google.protobuf.Empty;

public class DeleteLogDeleteLogRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteLogDeleteLogRequestRequest();
  }

  public static void deleteLogDeleteLogRequestRequest() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      DeleteLogRequest request =
          DeleteLogRequest.newBuilder()
              .setLogName(LogName.ofProjectLogName("[PROJECT]", "[LOG]").toString())
              .build();
      loggingClient.deleteLog(request);
    }
  }
}
