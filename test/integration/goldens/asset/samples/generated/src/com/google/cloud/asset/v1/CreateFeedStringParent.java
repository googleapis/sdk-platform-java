package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.Feed;

public class CreateFeedStringParent {

  public static void main(String[] args) throws Exception {
    createFeedStringParent();
  }

  public static void createFeedStringParent() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      String parent = "parent-995424086";
      Feed response = assetServiceClient.createFeed(parent);
    }
  }
}
