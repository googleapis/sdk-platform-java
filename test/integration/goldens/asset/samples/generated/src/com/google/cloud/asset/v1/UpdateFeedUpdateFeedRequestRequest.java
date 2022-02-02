package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.Feed;
import com.google.cloud.asset.v1.UpdateFeedRequest;
import com.google.protobuf.FieldMask;

public class UpdateFeedUpdateFeedRequestRequest {

  public static void main(String[] args) throws Exception {
    updateFeedUpdateFeedRequestRequest();
  }

  public static void updateFeedUpdateFeedRequestRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      UpdateFeedRequest request =
          UpdateFeedRequest.newBuilder()
              .setFeed(Feed.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      Feed response = assetServiceClient.updateFeed(request);
    }
  }
}
