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

// [START bigtable_v2_generated_basebigtabledataclient_checkandmutaterow_callablefuturecallcheckandmutaterowrequest]
import com.google.api.core.ApiFuture;
import com.google.bigtable.v2.CheckAndMutateRowRequest;
import com.google.bigtable.v2.CheckAndMutateRowResponse;
import com.google.bigtable.v2.Mutation;
import com.google.bigtable.v2.RowFilter;
import com.google.bigtable.v2.TableName;
import com.google.cloud.bigtable.data.v2.BaseBigtableDataClient;
import com.google.protobuf.ByteString;
import java.util.ArrayList;

public class CheckAndMutateRowCallableFutureCallCheckAndMutateRowRequest {

  public static void main(String[] args) throws Exception {
    checkAndMutateRowCallableFutureCallCheckAndMutateRowRequest();
  }

  public static void checkAndMutateRowCallableFutureCallCheckAndMutateRowRequest()
      throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (BaseBigtableDataClient baseBigtableDataClient = BaseBigtableDataClient.create()) {
      CheckAndMutateRowRequest request =
          CheckAndMutateRowRequest.newBuilder()
              .setTableName(TableName.of("[PROJECT]", "[INSTANCE]", "[TABLE]").toString())
              .setAppProfileId("appProfileId704923523")
              .setRowKey(ByteString.EMPTY)
              .setPredicateFilter(RowFilter.newBuilder().build())
              .addAllTrueMutations(new ArrayList<Mutation>())
              .addAllFalseMutations(new ArrayList<Mutation>())
              .build();
      ApiFuture<CheckAndMutateRowResponse> future =
          baseBigtableDataClient.checkAndMutateRowCallable().futureCall(request);
      // Do something.
      CheckAndMutateRowResponse response = future.get();
    }
  }
}
// [END bigtable_v2_generated_basebigtabledataclient_checkandmutaterow_callablefuturecallcheckandmutaterowrequest]