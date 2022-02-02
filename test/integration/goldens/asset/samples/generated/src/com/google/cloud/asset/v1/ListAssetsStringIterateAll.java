package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.Asset;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.FeedName;

public class ListAssetsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listAssetsStringIterateAll();
  }

  public static void listAssetsStringIterateAll() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      String parent = FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString();
      for (Asset element : assetServiceClient.listAssets(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
