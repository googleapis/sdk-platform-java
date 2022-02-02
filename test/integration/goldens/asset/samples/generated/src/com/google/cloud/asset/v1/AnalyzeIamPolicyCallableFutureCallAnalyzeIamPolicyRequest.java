package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AnalyzeIamPolicyRequest;
import com.google.cloud.asset.v1.AnalyzeIamPolicyResponse;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.IamPolicyAnalysisQuery;
import com.google.protobuf.Duration;

public class AnalyzeIamPolicyCallableFutureCallAnalyzeIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    analyzeIamPolicyCallableFutureCallAnalyzeIamPolicyRequest();
  }

  public static void analyzeIamPolicyCallableFutureCallAnalyzeIamPolicyRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      AnalyzeIamPolicyRequest request =
          AnalyzeIamPolicyRequest.newBuilder()
              .setAnalysisQuery(IamPolicyAnalysisQuery.newBuilder().build())
              .setExecutionTimeout(Duration.newBuilder().build())
              .build();
      ApiFuture<AnalyzeIamPolicyResponse> future =
          assetServiceClient.analyzeIamPolicyCallable().futureCall(request);
      // Do something.
      AnalyzeIamPolicyResponse response = future.get();
    }
  }
}
