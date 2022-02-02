package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.Feed;
import com.google.cloud.asset.v1.UpdateFeedRequest;
import com.google.protobuf.FieldMask;

public class UpdateFeedCallableFutureCallUpdateFeedRequest {

  public static void main(String[] args) throws Exception {
    updateFeedCallableFutureCallUpdateFeedRequest();
  }

  public static void updateFeedCallableFutureCallUpdateFeedRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      UpdateFeedRequest request =
          UpdateFeedRequest.newBuilder()
              .setFeed(Feed.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<Feed> future = assetServiceClient.updateFeedCallable().futureCall(request);
      // Do something.
      Feed response = future.get();
    }
  }
}
