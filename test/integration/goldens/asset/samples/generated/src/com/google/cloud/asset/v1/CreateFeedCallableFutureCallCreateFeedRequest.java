package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.CreateFeedRequest;
import com.google.cloud.asset.v1.Feed;

public class CreateFeedCallableFutureCallCreateFeedRequest {

  public static void main(String[] args) throws Exception {
    createFeedCallableFutureCallCreateFeedRequest();
  }

  public static void createFeedCallableFutureCallCreateFeedRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      CreateFeedRequest request =
          CreateFeedRequest.newBuilder()
              .setParent("parent-995424086")
              .setFeedId("feedId-1278410919")
              .setFeed(Feed.newBuilder().build())
              .build();
      ApiFuture<Feed> future = assetServiceClient.createFeedCallable().futureCall(request);
      // Do something.
      Feed response = future.get();
    }
  }
}
