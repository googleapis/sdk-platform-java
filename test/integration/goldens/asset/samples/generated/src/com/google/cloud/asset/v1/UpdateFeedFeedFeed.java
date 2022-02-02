package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.Feed;

public class UpdateFeedFeedFeed {

  public static void main(String[] args) throws Exception {
    updateFeedFeedFeed();
  }

  public static void updateFeedFeedFeed() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      Feed feed = Feed.newBuilder().build();
      Feed response = assetServiceClient.updateFeed(feed);
    }
  }
}
