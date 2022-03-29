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

package com.google.cloud.bigtable.data.v2.samples;

// [START bigtable_v2_generated_basebigtabledataclient_samplerowkeys_async]
import com.google.api.gax.rpc.ServerStream;
import com.google.bigtable.v2.SampleRowKeysRequest;
import com.google.bigtable.v2.SampleRowKeysResponse;
import com.google.bigtable.v2.TableName;
import com.google.cloud.bigtable.data.v2.BaseBigtableDataClient;

public class AsyncSampleRowKeys {

  public static void main(String[] args) throws Exception {
    asyncSampleRowKeys();
  }

  public static void asyncSampleRowKeys() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (BaseBigtableDataClient baseBigtableDataClient = BaseBigtableDataClient.create()) {
      SampleRowKeysRequest request =
          SampleRowKeysRequest.newBuilder()
              .setTableName(TableName.of("[PROJECT]", "[INSTANCE]", "[TABLE]").toString())
              .setAppProfileId("appProfileId704923523")
              .build();
      ServerStream<SampleRowKeysResponse> stream =
          baseBigtableDataClient.sampleRowKeysCallable().call(request);
      for (SampleRowKeysResponse response : stream) {
        // Do something when a response is received.
      }
    }
  }
}
// [END bigtable_v2_generated_basebigtabledataclient_samplerowkeys_async]
