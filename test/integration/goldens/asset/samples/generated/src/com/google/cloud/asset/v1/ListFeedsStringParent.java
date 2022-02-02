package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.ListFeedsResponse;

public class ListFeedsStringParent {

  public static void main(String[] args) throws Exception {
    listFeedsStringParent();
  }

  public static void listFeedsStringParent() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      String parent = "parent-995424086";
      ListFeedsResponse response = assetServiceClient.listFeeds(parent);
    }
  }
}
