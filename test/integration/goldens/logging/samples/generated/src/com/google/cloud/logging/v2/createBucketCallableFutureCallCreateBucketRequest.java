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

// [START 1.0_10_generated_configClient_createBucket_callableFutureCallCreateBucketRequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.CreateBucketRequest;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;

public class CreateBucketCallableFutureCallCreateBucketRequest {

  public static void main(String[] args) throws Exception {
    createBucketCallableFutureCallCreateBucketRequest();
  }

  public static void createBucketCallableFutureCallCreateBucketRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (ConfigClient configClient = ConfigClient.create()) {
      CreateBucketRequest request =
          CreateBucketRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setBucketId("bucketId-1603305307")
              .setBucket(LogBucket.newBuilder().build())
              .build();
      ApiFuture<LogBucket> future = configClient.createBucketCallable().futureCall(request);
      // Do something.
      LogBucket response = future.get();
    }
  }
}
// [END 1.0_10_generated_configClient_createBucket_callableFutureCallCreateBucketRequest]