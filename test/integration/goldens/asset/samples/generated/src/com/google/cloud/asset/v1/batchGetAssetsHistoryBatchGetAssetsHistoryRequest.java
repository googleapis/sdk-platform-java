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
package com.google.cloud.asset.v1.samples;

// [START asset_v1_generated_assetserviceclient_batchgetassetshistory_batchgetassetshistoryrequest]
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.BatchGetAssetsHistoryRequest;
import com.google.cloud.asset.v1.BatchGetAssetsHistoryResponse;
import com.google.cloud.asset.v1.ContentType;
import com.google.cloud.asset.v1.FeedName;
import com.google.cloud.asset.v1.TimeWindow;
import java.util.ArrayList;

public class BatchGetAssetsHistoryBatchGetAssetsHistoryRequest {

  public static void main(String[] args) throws Exception {
    batchGetAssetsHistoryBatchGetAssetsHistoryRequest();
  }

  public static void batchGetAssetsHistoryBatchGetAssetsHistoryRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      BatchGetAssetsHistoryRequest request =
          BatchGetAssetsHistoryRequest.newBuilder()
              .setParent(FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString())
              .addAllAssetNames(new ArrayList<String>())
              .setContentType(ContentType.forNumber(0))
              .setReadTimeWindow(TimeWindow.newBuilder().build())
              .addAllRelationshipTypes(new ArrayList<String>())
              .build();
      BatchGetAssetsHistoryResponse response = assetServiceClient.batchGetAssetsHistory(request);
    }
  }
}
// [END asset_v1_generated_assetserviceclient_batchgetassetshistory_batchgetassetshistoryrequest]