package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.CreateFeedRequest;
import com.google.cloud.asset.v1.Feed;

public class CreateFeedCreateFeedRequestRequest {

  public static void main(String[] args) throws Exception {
    createFeedCreateFeedRequestRequest();
  }

  public static void createFeedCreateFeedRequestRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      CreateFeedRequest request =
          CreateFeedRequest.newBuilder()
              .setParent("parent-995424086")
              .setFeedId("feedId-1278410919")
              .setFeed(Feed.newBuilder().build())
              .build();
      Feed response = assetServiceClient.createFeed(request);
    }
  }
}
