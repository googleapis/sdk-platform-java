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
package com.google.cloud.redis.v1beta1.samples;

// [START redis_v1beta1_generated_cloudredisclient_create_cloudredissettings1]
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.CloudRedisSettings;
import com.google.cloud.redis.v1beta1.myCredentials;

public class CreateCloudRedisSettings1 {

  public static void main(String[] args) throws Exception {
    createCloudRedisSettings1();
  }

  public static void createCloudRedisSettings1() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    CloudRedisSettings cloudRedisSettings =
        CloudRedisSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    CloudRedisClient cloudRedisClient = CloudRedisClient.create(cloudRedisSettings);
  }
}
// [END redis_v1beta1_generated_cloudredisclient_create_cloudredissettings1]