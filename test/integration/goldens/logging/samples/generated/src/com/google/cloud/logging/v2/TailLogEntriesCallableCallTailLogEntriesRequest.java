package com.google.cloud.logging.v2.samples;

import com.google.api.gax.rpc.BidiStream;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.TailLogEntriesRequest;
import com.google.logging.v2.TailLogEntriesResponse;
import com.google.protobuf.Duration;
import java.util.ArrayList;

public class TailLogEntriesCallableCallTailLogEntriesRequest {

  public static void main(String[] args) throws Exception {
    tailLogEntriesCallableCallTailLogEntriesRequest();
  }

  public static void tailLogEntriesCallableCallTailLogEntriesRequest() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      BidiStream<TailLogEntriesRequest, TailLogEntriesResponse> bidiStream =
          loggingClient.tailLogEntriesCallable().call();
      TailLogEntriesRequest request =
          TailLogEntriesRequest.newBuilder()
              .addAllResourceNames(new ArrayList<String>())
              .setFilter("filter-1274492040")
              .setBufferWindow(Duration.newBuilder().build())
              .build();
      bidiStream.send(request);
      for (TailLogEntriesResponse response : bidiStream) {
        // Do something when a response is received.
      }
    }
  }
}
