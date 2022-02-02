package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.ListFeedsRequest;
import com.google.cloud.asset.v1.ListFeedsResponse;

public class ListFeedsCallableFutureCallListFeedsRequest {

  public static void main(String[] args) throws Exception {
    listFeedsCallableFutureCallListFeedsRequest();
  }

  public static void listFeedsCallableFutureCallListFeedsRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      ListFeedsRequest request =
          ListFeedsRequest.newBuilder().setParent("parent-995424086").build();
      ApiFuture<ListFeedsResponse> future =
          assetServiceClient.listFeedsCallable().futureCall(request);
      // Do something.
      ListFeedsResponse response = future.get();
    }
  }
}
