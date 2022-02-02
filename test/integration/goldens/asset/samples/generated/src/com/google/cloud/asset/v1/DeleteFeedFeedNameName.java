package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.FeedName;
import com.google.protobuf.Empty;

public class DeleteFeedFeedNameName {

  public static void main(String[] args) throws Exception {
    deleteFeedFeedNameName();
  }

  public static void deleteFeedFeedNameName() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      FeedName name = FeedName.ofProjectFeedName("[PROJECT]", "[FEED]");
      assetServiceClient.deleteFeed(name);
    }
  }
}
