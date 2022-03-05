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

// [START bigtable_v2_generated_basebigtabledataclient_mutaterow_stringbytestringlistmutation]
import com.google.bigtable.v2.MutateRowResponse;
import com.google.bigtable.v2.Mutation;
import com.google.bigtable.v2.TableName;
import com.google.cloud.bigtable.data.v2.BaseBigtableDataClient;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;

public class MutateRowStringByteStringListMutation {

  public static void main(String[] args) throws Exception {
    mutateRowStringByteStringListMutation();
  }

  public static void mutateRowStringByteStringListMutation() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (BaseBigtableDataClient baseBigtableDataClient = BaseBigtableDataClient.create()) {
      String tableName = TableName.of("[PROJECT]", "[INSTANCE]", "[TABLE]").toString();
      ByteString rowKey = ByteString.EMPTY;
      List<Mutation> mutations = new ArrayList<>();
      MutateRowResponse response = baseBigtableDataClient.mutateRow(tableName, rowKey, mutations);
    }
  }
}
// [END bigtable_v2_generated_basebigtabledataclient_mutaterow_stringbytestringlistmutation]
