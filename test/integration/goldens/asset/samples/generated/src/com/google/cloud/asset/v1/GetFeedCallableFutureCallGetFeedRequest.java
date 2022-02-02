package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.Feed;
import com.google.cloud.asset.v1.FeedName;
import com.google.cloud.asset.v1.GetFeedRequest;

public class GetFeedCallableFutureCallGetFeedRequest {

  public static void main(String[] args) throws Exception {
    getFeedCallableFutureCallGetFeedRequest();
  }

  public static void getFeedCallableFutureCallGetFeedRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      GetFeedRequest request =
          GetFeedRequest.newBuilder()
              .setName(FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString())
              .build();
      ApiFuture<Feed> future = assetServiceClient.getFeedCallable().futureCall(request);
      // Do something.
      Feed response = future.get();
    }
  }
}
