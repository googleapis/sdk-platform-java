package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AnalyzeIamPolicyLongrunningRequest;
import com.google.cloud.asset.v1.AnalyzeIamPolicyLongrunningResponse;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.IamPolicyAnalysisOutputConfig;
import com.google.cloud.asset.v1.IamPolicyAnalysisQuery;

public class AnalyzeIamPolicyLongrunningAsyncAnalyzeIamPolicyLongrunningRequestGet {

  public static void main(String[] args) throws Exception {
    analyzeIamPolicyLongrunningAsyncAnalyzeIamPolicyLongrunningRequestGet();
  }

  public static void analyzeIamPolicyLongrunningAsyncAnalyzeIamPolicyLongrunningRequestGet()
      throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      AnalyzeIamPolicyLongrunningRequest request =
          AnalyzeIamPolicyLongrunningRequest.newBuilder()
              .setAnalysisQuery(IamPolicyAnalysisQuery.newBuilder().build())
              .setOutputConfig(IamPolicyAnalysisOutputConfig.newBuilder().build())
              .build();
      AnalyzeIamPolicyLongrunningResponse response =
          assetServiceClient.analyzeIamPolicyLongrunningAsync(request).get();
    }
  }
}
