package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.Feed;
import com.google.cloud.asset.v1.FeedName;

public class GetFeedFeedNameName {

  public static void main(String[] args) throws Exception {
    getFeedFeedNameName();
  }

  public static void getFeedFeedNameName() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      FeedName name = FeedName.ofProjectFeedName("[PROJECT]", "[FEED]");
      Feed response = assetServiceClient.getFeed(name);
    }
  }
}
