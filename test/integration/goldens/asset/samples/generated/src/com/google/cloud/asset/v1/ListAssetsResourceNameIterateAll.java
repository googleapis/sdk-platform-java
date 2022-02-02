package com.google.cloud.asset.v1.samples;

import com.google.api.resourcenames.ResourceName;
import com.google.cloud.asset.v1.Asset;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.FeedName;

public class ListAssetsResourceNameIterateAll {

  public static void main(String[] args) throws Exception {
    listAssetsResourceNameIterateAll();
  }

  public static void listAssetsResourceNameIterateAll() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      ResourceName parent = FeedName.ofProjectFeedName("[PROJECT]", "[FEED]");
      for (Asset element : assetServiceClient.listAssets(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
