package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.Feed;
import com.google.cloud.asset.v1.FeedName;

public class GetFeedStringName {

  public static void main(String[] args) throws Exception {
    getFeedStringName();
  }

  public static void getFeedStringName() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      String name = FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString();
      Feed response = assetServiceClient.getFeed(name);
    }
  }
}
