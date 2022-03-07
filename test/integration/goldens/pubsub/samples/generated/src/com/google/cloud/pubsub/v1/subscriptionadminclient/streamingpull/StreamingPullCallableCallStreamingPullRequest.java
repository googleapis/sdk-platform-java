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

package com.google.cloud.pubsub.v1.samples;

// [START pubsub_v1_generated_subscriptionadminclient_streamingpull_callablecallstreamingpullrequest_sync]
import com.google.api.gax.rpc.BidiStream;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.StreamingPullRequest;
import com.google.pubsub.v1.StreamingPullResponse;
import com.google.pubsub.v1.SubscriptionName;
import java.util.ArrayList;

public class StreamingPullCallableCallStreamingPullRequest {

  public static void main(String[] args) throws Exception {
    streamingPullCallableCallStreamingPullRequest();
  }

  public static void streamingPullCallableCallStreamingPullRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      BidiStream<StreamingPullRequest, StreamingPullResponse> bidiStream =
          subscriptionAdminClient.streamingPullCallable().call();
      StreamingPullRequest request =
          StreamingPullRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .addAllAckIds(new ArrayList<String>())
              .addAllModifyDeadlineSeconds(new ArrayList<Integer>())
              .addAllModifyDeadlineAckIds(new ArrayList<String>())
              .setStreamAckDeadlineSeconds(1875467245)
              .setClientId("clientId908408390")
              .setMaxOutstandingMessages(-1315266996)
              .setMaxOutstandingBytes(-2103098517)
              .build();
      bidiStream.send(request);
      for (StreamingPullResponse response : bidiStream) {
        // Do something when a response is received.
      }
    }
  }
}
// [END pubsub_v1_generated_subscriptionadminclient_streamingpull_callablecallstreamingpullrequest_sync]
