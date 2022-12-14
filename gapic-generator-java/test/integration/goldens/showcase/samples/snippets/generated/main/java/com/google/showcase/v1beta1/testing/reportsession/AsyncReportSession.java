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

package com.google.showcase.v1beta1.samples;

// [START localhost7469_v1beta1_generated_Testing_ReportSession_async]
import com.google.api.core.ApiFuture;
import com.google.showcase.v1beta1.ReportSessionRequest;
import com.google.showcase.v1beta1.ReportSessionResponse;
import com.google.showcase.v1beta1.SessionName;
import com.google.showcase.v1beta1.TestingClient;

public class AsyncReportSession {

  public static void main(String[] args) throws Exception {
    asyncReportSession();
  }

  public static void asyncReportSession() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
    try (TestingClient testingClient = TestingClient.create()) {
      ReportSessionRequest request =
          ReportSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
      ApiFuture<ReportSessionResponse> future =
          testingClient.reportSessionCallable().futureCall(request);
      // Do something.
      ReportSessionResponse response = future.get();
    }
  }
}
// [END localhost7469_v1beta1_generated_Testing_ReportSession_async]
