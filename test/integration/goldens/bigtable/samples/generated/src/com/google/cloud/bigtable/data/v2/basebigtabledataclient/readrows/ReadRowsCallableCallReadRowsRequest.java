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

package com.google.cloud.bigtable.data.v2.samples;

// [START bigtable_v2_generated_basebigtabledataclient_readrows_callablecallreadrowsrequest]
import com.google.api.gax.rpc.ServerStream;
import com.google.bigtable.v2.ReadRowsRequest;
import com.google.bigtable.v2.ReadRowsResponse;
import com.google.bigtable.v2.RowFilter;
import com.google.bigtable.v2.RowSet;
import com.google.bigtable.v2.TableName;
import com.google.cloud.bigtable.data.v2.BaseBigtableDataClient;

public class ReadRowsCallableCallReadRowsRequest {

  public static void main(String[] args) throws Exception {
    readRowsCallableCallReadRowsRequest();
  }

  public static void readRowsCallableCallReadRowsRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (BaseBigtableDataClient baseBigtableDataClient = BaseBigtableDataClient.create()) {
      ReadRowsRequest request =
          ReadRowsRequest.newBuilder()
              .setTableName(TableName.of("[PROJECT]", "[INSTANCE]", "[TABLE]").toString())
              .setAppProfileId("appProfileId704923523")
              .setRows(RowSet.newBuilder().build())
              .setFilter(RowFilter.newBuilder().build())
              .setRowsLimit(-944199211)
              .build();
      ServerStream<ReadRowsResponse> stream =
          baseBigtableDataClient.readRowsCallable().call(request);
      for (ReadRowsResponse response : stream) {
        // Do something when a response is received.
      }
    }
  }
}
// [END bigtable_v2_generated_basebigtabledataclient_readrows_callablecallreadrowsrequest]
