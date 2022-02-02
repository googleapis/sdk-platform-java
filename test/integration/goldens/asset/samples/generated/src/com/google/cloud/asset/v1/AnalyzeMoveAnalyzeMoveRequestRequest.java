package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AnalyzeMoveRequest;
import com.google.cloud.asset.v1.AnalyzeMoveResponse;
import com.google.cloud.asset.v1.AssetServiceClient;

public class AnalyzeMoveAnalyzeMoveRequestRequest {

  public static void main(String[] args) throws Exception {
    analyzeMoveAnalyzeMoveRequestRequest();
  }

  public static void analyzeMoveAnalyzeMoveRequestRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      AnalyzeMoveRequest request =
          AnalyzeMoveRequest.newBuilder()
              .setResource("resource-341064690")
              .setDestinationParent("destinationParent-1733659048")
              .build();
      AnalyzeMoveResponse response = assetServiceClient.analyzeMove(request);
    }
  }
}
