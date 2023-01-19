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

// [START localhost7469_v1beta1_generated_Wicked_BrainstormEvilPlans_async]
import com.google.api.gax.rpc.BidiStream;
import com.google.showcase.v1beta1.EvilRequest;
import com.google.showcase.v1beta1.EvilResponse;
import com.google.showcase.v1beta1.WickedClient;

public class AsyncBrainstormEvilPlans {

  public static void main(String[] args) throws Exception {
    asyncBrainstormEvilPlans();
  }

  public static void asyncBrainstormEvilPlans() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
    try (WickedClient wickedClient = WickedClient.create()) {
      BidiStream<EvilRequest, EvilResponse> bidiStream =
          wickedClient.brainstormEvilPlansCallable().call();
      EvilRequest request =
          EvilRequest.newBuilder().setMaliciousIdea("maliciousIdea712541645").build();
      bidiStream.send(request);
      for (EvilResponse response : bidiStream) {
        // Do something when a response is received.
      }
    }
  }
}
// [END localhost7469_v1beta1_generated_Wicked_BrainstormEvilPlans_async]
