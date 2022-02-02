package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AnalyzeIamPolicyLongrunningRequest;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.IamPolicyAnalysisOutputConfig;
import com.google.cloud.asset.v1.IamPolicyAnalysisQuery;
import com.google.longrunning.Operation;

public class AnalyzeIamPolicyLongrunningCallableFutureCallAnalyzeIamPolicyLongrunningRequest {

  public static void main(String[] args) throws Exception {
    analyzeIamPolicyLongrunningCallableFutureCallAnalyzeIamPolicyLongrunningRequest();
  }

  public static void
      analyzeIamPolicyLongrunningCallableFutureCallAnalyzeIamPolicyLongrunningRequest()
          throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      AnalyzeIamPolicyLongrunningRequest request =
          AnalyzeIamPolicyLongrunningRequest.newBuilder()
              .setAnalysisQuery(IamPolicyAnalysisQuery.newBuilder().build())
              .setOutputConfig(IamPolicyAnalysisOutputConfig.newBuilder().build())
              .build();
      ApiFuture<Operation> future =
          assetServiceClient.analyzeIamPolicyLongrunningCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
