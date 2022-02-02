package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AnalyzeIamPolicyRequest;
import com.google.cloud.asset.v1.AnalyzeIamPolicyResponse;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.IamPolicyAnalysisQuery;
import com.google.protobuf.Duration;

public class AnalyzeIamPolicyAnalyzeIamPolicyRequestRequest {

  public static void main(String[] args) throws Exception {
    analyzeIamPolicyAnalyzeIamPolicyRequestRequest();
  }

  public static void analyzeIamPolicyAnalyzeIamPolicyRequestRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      AnalyzeIamPolicyRequest request =
          AnalyzeIamPolicyRequest.newBuilder()
              .setAnalysisQuery(IamPolicyAnalysisQuery.newBuilder().build())
              .setExecutionTimeout(Duration.newBuilder().build())
              .build();
      AnalyzeIamPolicyResponse response = assetServiceClient.analyzeIamPolicy(request);
    }
  }
}
