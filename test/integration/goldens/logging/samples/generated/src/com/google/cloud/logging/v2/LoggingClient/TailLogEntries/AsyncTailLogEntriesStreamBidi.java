/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.logging.v2.samples;

// [START logging_v2_generated_loggingclient_taillogentries_streambidi_async]
import com.google.api.gax.rpc.BidiStream;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.TailLogEntriesRequest;
import com.google.logging.v2.TailLogEntriesResponse;
import com.google.protobuf.Duration;
import java.util.ArrayList;

public class AsyncTailLogEntriesStreamBidi {

  public static void main(String[] args) throws Exception {
    asyncTailLogEntriesStreamBidi();
  }

  public static void asyncTailLogEntriesStreamBidi() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
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
// [END logging_v2_generated_loggingclient_taillogentries_streambidi_async]
