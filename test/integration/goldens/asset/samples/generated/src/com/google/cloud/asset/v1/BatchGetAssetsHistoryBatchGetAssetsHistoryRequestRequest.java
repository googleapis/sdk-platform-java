package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.BatchGetAssetsHistoryRequest;
import com.google.cloud.asset.v1.BatchGetAssetsHistoryResponse;
import com.google.cloud.asset.v1.ContentType;
import com.google.cloud.asset.v1.FeedName;
import com.google.cloud.asset.v1.TimeWindow;
import java.util.ArrayList;

public class BatchGetAssetsHistoryBatchGetAssetsHistoryRequestRequest {

  public static void main(String[] args) throws Exception {
    batchGetAssetsHistoryBatchGetAssetsHistoryRequestRequest();
  }

  public static void batchGetAssetsHistoryBatchGetAssetsHistoryRequestRequest() throws Exception {
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
