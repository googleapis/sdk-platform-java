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

package com.google.cloud.redis.v1beta1.samples;

// [START redis_v1beta1_generated_cloudredisclient_failoverinstance_callablefuturecallfailoverinstancerequest_sync]
import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.FailoverInstanceRequest;
import com.google.cloud.redis.v1beta1.InstanceName;
import com.google.longrunning.Operation;

public class FailoverInstanceCallableFutureCallFailoverInstanceRequest {

  public static void main(String[] args) throws Exception {
    failoverInstanceCallableFutureCallFailoverInstanceRequest();
  }

  public static void failoverInstanceCallableFutureCallFailoverInstanceRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      FailoverInstanceRequest request =
          FailoverInstanceRequest.newBuilder()
              .setName(InstanceName.of("[PROJECT]", "[LOCATION]", "[INSTANCE]").toString())
              .build();
      ApiFuture<Operation> future = cloudRedisClient.failoverInstanceCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
// [END redis_v1beta1_generated_cloudredisclient_failoverinstance_callablefuturecallfailoverinstancerequest_sync]
