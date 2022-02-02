package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.FeedName;
import com.google.protobuf.Empty;

public class DeleteFeedStringName {

  public static void main(String[] args) throws Exception {
    deleteFeedStringName();
  }

  public static void deleteFeedStringName() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      String name = FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString();
      assetServiceClient.deleteFeed(name);
    }
  }
}
