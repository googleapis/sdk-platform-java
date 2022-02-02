package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AnalyzeMoveRequest;
import com.google.cloud.asset.v1.AnalyzeMoveResponse;
import com.google.cloud.asset.v1.AssetServiceClient;

public class AnalyzeMoveCallableFutureCallAnalyzeMoveRequest {

  public static void main(String[] args) throws Exception {
    analyzeMoveCallableFutureCallAnalyzeMoveRequest();
  }

  public static void analyzeMoveCallableFutureCallAnalyzeMoveRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      AnalyzeMoveRequest request =
          AnalyzeMoveRequest.newBuilder()
              .setResource("resource-341064690")
              .setDestinationParent("destinationParent-1733659048")
              .build();
      ApiFuture<AnalyzeMoveResponse> future =
          assetServiceClient.analyzeMoveCallable().futureCall(request);
      // Do something.
      AnalyzeMoveResponse response = future.get();
    }
  }
}
