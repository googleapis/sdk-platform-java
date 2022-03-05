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

// [START logging_v2_generated_configclient_deleteview_callablefuturecalldeleteviewrequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.DeleteViewRequest;
import com.google.logging.v2.LogViewName;
import com.google.protobuf.Empty;

public class DeleteViewCallableFutureCallDeleteViewRequest {

  public static void main(String[] args) throws Exception {
    deleteViewCallableFutureCallDeleteViewRequest();
  }

  public static void deleteViewCallableFutureCallDeleteViewRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (ConfigClient configClient = ConfigClient.create()) {
      DeleteViewRequest request =
          DeleteViewRequest.newBuilder()
              .setName(
                  LogViewName.ofProjectLocationBucketViewName(
                          "[PROJECT]", "[LOCATION]", "[BUCKET]", "[VIEW]")
                      .toString())
              .build();
      ApiFuture<Empty> future = configClient.deleteViewCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
// [END logging_v2_generated_configclient_deleteview_callablefuturecalldeleteviewrequest]
