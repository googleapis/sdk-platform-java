package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.DeleteLogRequest;
import com.google.logging.v2.LogName;
import com.google.protobuf.Empty;

public class DeleteLogCallableFutureCallDeleteLogRequest {

  public static void main(String[] args) throws Exception {
    deleteLogCallableFutureCallDeleteLogRequest();
  }

  public static void deleteLogCallableFutureCallDeleteLogRequest() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      DeleteLogRequest request =
          DeleteLogRequest.newBuilder()
              .setLogName(LogName.ofProjectLogName("[PROJECT]", "[LOG]").toString())
              .build();
      ApiFuture<Empty> future = loggingClient.deleteLogCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
