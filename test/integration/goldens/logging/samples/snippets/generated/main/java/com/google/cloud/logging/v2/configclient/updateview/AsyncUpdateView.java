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

// [START logging_v2_generated_configclient_updateview_async]
import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogView;
import com.google.logging.v2.UpdateViewRequest;
import com.google.protobuf.FieldMask;

public class AsyncUpdateView {

  public static void main(String[] args) throws Exception {
    asyncUpdateView();
  }

  public static void asyncUpdateView() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
    try (ConfigClient configClient = ConfigClient.create()) {
      UpdateViewRequest request =
          UpdateViewRequest.newBuilder()
              .setName("name3373707")
              .setView(LogView.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<LogView> future = configClient.updateViewCallable().futureCall(request);
      // Do something.
      LogView response = future.get();
    }
  }
}
// [END logging_v2_generated_configclient_updateview_async]
