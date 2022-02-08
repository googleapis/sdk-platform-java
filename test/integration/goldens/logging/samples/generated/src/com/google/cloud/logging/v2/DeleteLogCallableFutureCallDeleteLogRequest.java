/*
 * Copyright 2021 Google LLC
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

// [START 10_10_generated_loggingClient_deleteLogCallable_futureCallDeleteLogRequest]
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
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
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
// [END 10_10_generated_loggingClient_deleteLogCallable_futureCallDeleteLogRequest]