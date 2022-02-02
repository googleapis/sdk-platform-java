package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.DeleteFeedRequest;
import com.google.cloud.asset.v1.FeedName;
import com.google.protobuf.Empty;

public class DeleteFeedDeleteFeedRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteFeedDeleteFeedRequestRequest();
  }

  public static void deleteFeedDeleteFeedRequestRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      DeleteFeedRequest request =
          DeleteFeedRequest.newBuilder()
              .setName(FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString())
              .build();
      assetServiceClient.deleteFeed(request);
    }
  }
}
