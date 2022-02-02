package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.Feed;
import com.google.cloud.asset.v1.FeedName;
import com.google.cloud.asset.v1.GetFeedRequest;

public class GetFeedGetFeedRequestRequest {

  public static void main(String[] args) throws Exception {
    getFeedGetFeedRequestRequest();
  }

  public static void getFeedGetFeedRequestRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      GetFeedRequest request =
          GetFeedRequest.newBuilder()
              .setName(FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString())
              .build();
      Feed response = assetServiceClient.getFeed(request);
    }
  }
}
