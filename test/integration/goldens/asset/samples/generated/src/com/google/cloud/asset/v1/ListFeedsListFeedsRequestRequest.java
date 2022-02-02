package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.ListFeedsRequest;
import com.google.cloud.asset.v1.ListFeedsResponse;

public class ListFeedsListFeedsRequestRequest {

  public static void main(String[] args) throws Exception {
    listFeedsListFeedsRequestRequest();
  }

  public static void listFeedsListFeedsRequestRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      ListFeedsRequest request =
          ListFeedsRequest.newBuilder().setParent("parent-995424086").build();
      ListFeedsResponse response = assetServiceClient.listFeeds(request);
    }
  }
}
