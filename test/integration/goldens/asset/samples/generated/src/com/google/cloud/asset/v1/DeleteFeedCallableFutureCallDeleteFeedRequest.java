package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.DeleteFeedRequest;
import com.google.cloud.asset.v1.FeedName;
import com.google.protobuf.Empty;

public class DeleteFeedCallableFutureCallDeleteFeedRequest {

  public static void main(String[] args) throws Exception {
    deleteFeedCallableFutureCallDeleteFeedRequest();
  }

  public static void deleteFeedCallableFutureCallDeleteFeedRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      DeleteFeedRequest request =
          DeleteFeedRequest.newBuilder()
              .setName(FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString())
              .build();
      ApiFuture<Empty> future = assetServiceClient.deleteFeedCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
